package com.dfsek.seismic.algorithms.sampler.noise;

import com.dfsek.seismic.algorithms.hashing.HashingFunctions;
import com.dfsek.seismic.math.algebra.LinearAlgebraFunctions;
import com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.math.normalization.NormalizationFunctions;
import com.dfsek.seismic.math.numericanalysis.interpolation.sigmoid.SmoothstepFunctions;
import com.dfsek.seismic.math.trigonometry.TrigonometryConstants;
import com.dfsek.seismic.math.trigonometry.TrigonometryFunctions;
import com.dfsek.seismic.type.sampler.DerivativeSampler;


public class PseudoErosionSampler extends NoiseFunction {
    private static final double HASH_X = 0.3183099f;
    private static final double HASH_Y = 0.3678794f;
    public final double gain;
    public final double lacunarity;
    public final double slopeStrength;
    public final double branchStrength;
    public final double erosionStrength;
    private final int octaves;
    private final double erosionFrequency;
    private final DerivativeSampler sampler;
    private final boolean slopeMask;
    private final double slopeMaskFullSq;
    private final double slopeMaskNoneSq;
    private final double jitter;
    private final double maxCellDistSq;
    private final double maxCellDistSqRecip;
    private final boolean averageErosionImpulses;

    public PseudoErosionSampler(double frequency, long salt, int octaves, double gain, double lacunarity, double slopeStrength,
                                double branchStrength,
                                double erosionStrength, double erosionFrequency, DerivativeSampler sampler,
                                boolean slopeMask, double slopeMaskFull, double slopeMaskNone, double jitterModifier,
                                boolean averageErosionImpulses) {
        super(frequency, salt);
        this.octaves = octaves;
        this.gain = gain;
        this.lacunarity = lacunarity;
        this.slopeStrength = slopeStrength;
        this.branchStrength = branchStrength;
        this.erosionStrength = erosionStrength;
        this.erosionFrequency = erosionFrequency;
        this.sampler = sampler;
        this.slopeMask = slopeMask;
        // Square these values and maintain sign since they're compared to a
        // squared value, otherwise a sqrt would need to be used
        this.slopeMaskFullSq = slopeMaskFull * slopeMaskFull * Math.signum(slopeMaskFull);
        this.slopeMaskNoneSq = slopeMaskNone * slopeMaskNone * Math.signum((slopeMaskNone));
        this.jitter = 0.43701595 * jitterModifier;
        this.averageErosionImpulses = averageErosionImpulses;
        this.maxCellDistSq = 1 + jitter * jitter;
        this.maxCellDistSqRecip = 1 / maxCellDistSq;
    }

    public static double hashX(double seed, double n) {
        // Swapped the components here
        double nx = PseudoErosionSampler.HASH_X * n * seed;
        return ArithmeticFunctions.fma(2.0f, FloatingPointFunctions.getFraction(nx), -1.0f);
    }

    public static double hashY(double seed, double n) {
        double ny = PseudoErosionSampler.HASH_Y * n * seed;
        return ArithmeticFunctions.fma(2.0f, FloatingPointFunctions.getFraction(ny), -1.0f);
    }

    public double[] erosion(int seed, double x, double y, double dirX, double dirY) {
        int gridX = FloatingPointFunctions.floor(x);
        int gridY = FloatingPointFunctions.floor(y);
        double noise = 0.0f;
        double dirOutX = 0.0f;
        double dirOutY = 0.0f;
        double cumAmp = 0.0f;

        for(int cellX = gridX - 1; cellX <= gridX + 1; cellX++) {
            for(int cellY = gridY - 1; cellY <= gridY + 1; cellY++) {
                double cellHash = HashingFunctions.hashPrimeCoords(seed, cellX, cellY);
                double cellOffsetX = PseudoErosionSampler.hashX(seed, cellHash) * jitter;
                double cellOffsetY = PseudoErosionSampler.hashY(seed, cellHash) * jitter;
                double cellOriginDeltaX = (x - cellX) + cellOffsetX;
                double cellOriginDeltaY = (y - cellY) + cellOffsetY;
                double cellOriginDistSq = ArithmeticFunctions.fma(cellOriginDeltaX, cellOriginDeltaX, cellOriginDeltaY * cellOriginDeltaY);
                if(cellOriginDistSq > maxCellDistSq) continue; // Skip calculating cells too far away
                double ampTmp = (cellOriginDistSq * maxCellDistSqRecip) - 1;
                double amp = ampTmp * ampTmp; // Decrease cell amplitude further away
                cumAmp += amp;
                double directionalStrength = LinearAlgebraFunctions.dotProduct(cellOriginDeltaX, cellOriginDeltaY, dirX, dirY) *
                                             TrigonometryConstants.TAU;
                noise += TrigonometryFunctions.cos(directionalStrength) * amp;
                double sinAngle = TrigonometryFunctions.sin(directionalStrength) * amp;
                dirOutX -= sinAngle * (cellOriginDeltaX + dirX);
                dirOutY -= sinAngle * (cellOriginDeltaY + dirY);
            }
        }
        if(averageErosionImpulses && cumAmp != 0) {
            noise /= cumAmp;
            dirOutX /= cumAmp;
            dirOutY /= cumAmp;
        }
        return new double[]{ noise, dirOutX, dirOutY };
    }

    public double heightMap(long seed, double x, double y) {
        double[] sample = sampler.getSampleDerivative(seed, x, y);
        double height = sample[0];
        double heightDirX = sample[1];
        double heightDirY = sample[2];

        // Take the curl of the normal to get the gradient facing down the slope
        double baseDirX = heightDirY * slopeStrength;
        double baseDirY = -heightDirX * slopeStrength;

        double erosion = 0.0f;
        double dirX = 0.0f;
        double dirY = 0.0f;
        double amp = 1.0f;
        double cumAmp = 0.0f;
        double freq = 1.0f;

        // Stack erosion octaves
        for(int i = 0; i < octaves; i++) {
            double[] erosionResult = erosion((int) seed,
                x * freq * erosionFrequency,
                y * freq * erosionFrequency,
                ArithmeticFunctions.fma(dirY, branchStrength, baseDirX),
                baseDirY - dirX * branchStrength);
            erosion = ArithmeticFunctions.fma(erosionResult[0], amp, erosion);
            dirX = ArithmeticFunctions.fma(erosionResult[1], amp * freq, dirX);
            dirY = ArithmeticFunctions.fma(erosionResult[2], amp * freq, dirY);
            cumAmp += amp;
            amp *= gain;
            freq *= lacunarity;
        }

        // Normalize erosion noise
        erosion /= cumAmp;
        // [-1, 1] -> [0, 1]
        erosion = erosion * 0.5F + 0.5F;

        // Without masking, erosion noise in areas with small gradients tend to produce mounds,
        // this reduces erosion amplitude towards smaller gradients to avoid this
        if(slopeMask) {
            double dirMagSq = LinearAlgebraFunctions.dotProduct(baseDirX, baseDirY, baseDirX, baseDirY);
            double flatness = SmoothstepFunctions.cubicPolynomialSmoothstep(
                NormalizationFunctions.normalizeToRange(slopeMaskNoneSq, slopeMaskFullSq, dirMagSq));
            erosion *= flatness;
        }

        return height + (erosion - height) * erosionStrength;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        return heightMap(seed, x, y);
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y, double z) {
        return getNoiseRaw(seed, x, z);
    }
}