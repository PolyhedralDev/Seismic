/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.fractal;

import com.dfsek.seismic.math.numericanalysis.interpolation.InterpolationFunctions;
import com.dfsek.seismic.type.sampler.DerivativeSampler;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.CD_double;


public class WeightedBrownianMotionSampler extends FractalNoiseFunction {
    protected final double weightedStrength;

    public WeightedBrownianMotionSampler(Sampler input, double gain, double lacunarity, double weightedStrength, int octaves) {
        super(input, gain, lacunarity, octaves);
        this.weightedStrength = weightedStrength;
    }

    @Override
    public double getSample(long seed, double x, double y) {
        double sum = 0;
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double noise = input.getSample(seed++, x, y);
            sum += noise * amp;
            amp *= InterpolationFunctions.lerp(1.0, Math.min(noise + 1, 2) * 0.5, weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            amp *= gain;
        }

        return sum;
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        double sum = 0;
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double noise = input.getSample(seed++, x, y, z);
            sum += noise * amp;
            amp *= InterpolationFunctions.lerp(1.0, (noise + 1) * 0.5, weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            amp *= gain;
        }

        return sum;
    }

    @Override
    public boolean isDifferentiable() {
        return DerivativeSampler.isDifferentiable(input);
    }

    @Override
    public double[] getSampleDerivative(long seed, double x, double y) {
        double[] sum = { 0, 0, 0 };
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            // This should only be called after `input` is verified as a `DerivativeNoiseSampler`
            // so this should be a safe cast
            double[] noise = ((DerivativeSampler) input).getSampleDerivative(seed++, x, y);
            sum[0] += noise[0] * amp;

            // Directional derivative of each octave can be subject to the same addition and product
            // as per derivative sum and product rules in order to produce the correct final derivative
            sum[1] += noise[1] * amp;
            sum[2] += noise[2] * amp;

            amp *= InterpolationFunctions.lerp(1.0, Math.min(noise[0] + 1, 2) * 0.5, weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            amp *= gain;
        }

        return sum;
    }

    @Override
    public double[] getSampleDerivative(long seed, double x, double y, double z) {
        double[] sum = { 0, 0, 0, 0 };
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double[] noise = ((DerivativeSampler) input).getSampleDerivative(seed++, x, y, z);
            sum[0] += noise[0] * amp;

            // See comment in 2D version
            sum[1] += noise[1] * amp;
            sum[2] += noise[2] * amp;
            sum[3] += noise[3] * amp;

            amp *= InterpolationFunctions.lerp(1.0, (noise[0] + 1) * 0.5, weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            amp *= gain;
        }

        return sum;
    }

    // cant be clever with this one, wont be able to benefit from abstract optimisations :(
    @Override
    public CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int ySlot, int zSlot, int max) {
        int xSlot_ = max;
        max += 2;
        int ySlot_ = max;
        max += 2;
        int zSlot_ = max;
        max += 2;
        int seedSlot_ = max;
        max += 2;
        int noiseSlot = max;
        max += 2;
        int sumSlot = max;
        max += 2;
        int ampSlot = max;
        max += 2;

        b.dload(xSlot)
            .dstore(xSlot_)
            .dload(ySlot)
            .dstore(ySlot_)
            .dload(zSlot)
            .dstore(zSlot_)
            .lload(seedSlot)
            .lstore(seedSlot_)
            .loadConstant(fractalBounding)
            .dup2() // for weighted strength app
            .dup2() // for aggregate
            .dstore(ampSlot);
        input.build(b, seedSlot, xSlot, ySlot, zSlot, max)
            .dup2()
            .dstore(noiseSlot)
            .dmul() // noise * amp
            .dstore(sumSlot);

        for(int i = 1; i < octaves; i++) {

            b.loadConstant(1L)
                .lload(seedSlot_)
                .ladd()
                .lstore(seedSlot_)
                .loadConstant(1d)
                .dup2()
                .dload(noiseSlot)
                .dadd()
                .loadConstant(0.5d)
                .dmul()
                .loadConstant(weightedStrength)
                .invokestatic(ClassDesc.of(InterpolationFunctions.class.getName()), "lerp", MethodTypeDesc.of(CD_double, CD_double, CD_double, CD_double))
                .dmul()
                .loadConstant(gain)
                .dmul()
                .dstore(ampSlot)
                .loadConstant(lacunarity)
                .dup2()
                .dup2()
                .dload(xSlot_)
                .dmul()
                .dstore(xSlot_)
                .dload(ySlot_)
                .dmul()
                .dstore(ySlot_)
                .dload(zSlot_)
                .dmul()
                .dstore(zSlot_)
                .dload(ampSlot)
                .dup2();

            input.build(b, seedSlot_, xSlot_, ySlot_, zSlot_, max)
                .dmul()
                .dload(sumSlot)
                .dadd()
                .dstore(sumSlot);
        }

        return b.dload(sumSlot);
    }
}
