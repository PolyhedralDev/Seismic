package com.dfsek.seismic.algorithms.samplers.noise;


import com.dfsek.seismic.math.algebra.LinearAlgebraFunctions;
import com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;
import com.dfsek.seismic.math.exponential.ExponentialFunctions;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.math.trigonometry.TrigonometryConstants;
import com.dfsek.seismic.math.trigonometry.TrigonometryFunctions;


public class PseudoErosion extends NoiseFunction {
    private static final double HASH_X = 0.3183099f;
    private static final double HASH_Y = 0.3678794f;

    private double erosionTiles = 4.0;
    private int erosionOctaves = 5;
    private double erosionGain = 0.5;
    private double erosionLacunarity = 2.0;
    private double erosionSlopeStrength = 3.0;
    private double erosionBranchStrength = 3.0;
    private double erosionStrength = 0.04;
    private double heightTiles = 1;
    private int heightOctaves = 3;
    private double heightAmp = 0.50;
    private double heightGain = 0.1;
    private double heightLacunarity = 2.0;

    public static double hash(double x, double y) {
        double xx = ArithmeticFunctions.fma(x, HASH_X, HASH_Y);
        double yy = ArithmeticFunctions.fma(y, HASH_Y, HASH_X);

        // Swapped the components here
        return 16 * (xx * yy * (xx + yy));
    }

    public static double hashX(double n) {
        // Swapped the components here
        double nx = HASH_X * n;
        return -1.0f + 2.0f * FloatingPointFunctions.getFraction(nx);
    }

    public static double hashY(double n) {
        double ny = HASH_Y * n;
        return -1.0f + 2.0f * FloatingPointFunctions.getFraction(ny);
    }

    public static double[] erosion(double px, double py, double dirx, double diry) {
        double ipx = FloatingPointFunctions.floor(px);
        double ipy = FloatingPointFunctions.floor(py);
        double fpx = px - ipx;
        double fpy = py - ipy;
        double vax = 0.0f;
        double vay = 0.0f;
        double vaz = 0.0f;
        double wt = 0.0f;

        for(int i = -2; i <= 1; i++) {
            for(int j = -2; j <= 1; j++) {
                double ox = i;
                double oy = j;
                double n = hash(ipx - ox, ipy - oy);
                double hx = hashX(n) * 0.5f;
                double hy = hashY(n) * 0.5f;
                double ppx = fpx + ox - hx;
                double ppy = fpy + oy - hy;
                double d = ppx * ppx + ppy * ppy;
                double w = ExponentialFunctions.exp(-d * 2.0);
                wt += w;
                double mag = (ppx * dirx + ppy * diry) * TrigonometryConstants.TAU;
                double magsin = (-TrigonometryFunctions.sin(mag) * w);
                vax += TrigonometryFunctions.cos(mag) * w;
                vay += magsin * (ppx + dirx);
                vaz += magsin * (ppy + diry);
            }
        }

        vax /= wt;
        vay /= wt;
        vaz /= wt;

        return new double[]{ vax, vay, vaz };
    }

    public static double[] noised(double px, double py) {
        double ix = FloatingPointFunctions.floor(px);
        double iy = FloatingPointFunctions.floor(py);
        double fx = px - ix;
        double fy = py - iy;

        double ux = fx * fx * fx * (fx * (fx * 6.0f - 15.0f) + 10.0f);
        double uy = fy * fy * fy * (fy * (fy * 6.0f - 15.0f) + 10.0f);
        double dux = fx * fx * 30.0f * (fx * (fx - 2.0f) + 1.0f);
        double duy = fy * fy * 30.0f * (fy * (fy - 2.0f) + 1.0f);

        double gan = hash(ix, iy);
        double gax = hashX(gan);
        double gay = hashY(gan);

        double gbn = hash(ix + 1, iy);
        double gbx = hashX(gbn);
        double gby = hashY(gbn);

        double gcn = hash(ix, iy + 1);
        double gcx = hashX(gcn);
        double gcy = hashY(gcn);

        double gdn = hash(ix + 1, iy + 1);
        double gdx = hashX(gdn);
        double gdy = hashY(gdn);

        double va = LinearAlgebraFunctions.dotProduct(gax, gay, fx, fy);
        double vb = LinearAlgebraFunctions.dotProduct(gbx, gby, fx - 1, fy);
        double vc = LinearAlgebraFunctions.dotProduct(gcx, gcy, fx, fy - 1);
        double vd = LinearAlgebraFunctions.dotProduct(gdx, gdy, fx - 1, fy - 1);

        double u2x =
            gax + (gbx - gax) * ux + (gcx - gax) * uy + (gax - gbx - gcx + gdx) * ux * uy + dux * (uy * (va - vb - vc + vd) + vb - va);
        double u2y =
            gay + (gby - gay) * ux + (gcy - gay) * uy + (gay - gby - gcy + gdy) * ux * uy + duy * (ux * (va - vb - vc + vd) + vc - va);

        return new double[]{ va + ux * (vb - va) + uy * (vc - va) + ux * uy * (va - vb - vc + vd), u2x, u2y };
    }

    public void setErosionTiles(double erosionTiles) {
        this.erosionTiles = erosionTiles;
    }

    public void setErosionOctaves(int erosionOctaves) {
        this.erosionOctaves = erosionOctaves;
    }

    public void setErosionGain(double erosionGain) {
        this.erosionGain = erosionGain;
    }

    public void setErosionLacunarity(double erosionLacunarity) {
        this.erosionLacunarity = erosionLacunarity;
    }

    public void setErosionSlopeStrength(double erosionSlopeStrength) {
        this.erosionSlopeStrength = erosionSlopeStrength;
    }

    public void setErosionBranchStrength(double erosionBranchStrength) {
        this.erosionBranchStrength = erosionBranchStrength;
    }

    public void setErosionStrength(double erosionStrength) {
        this.erosionStrength = erosionStrength;
    }

    public void setHeightTiles(double heightTiles) {
        this.heightTiles = heightTiles;
    }

    public void setHeightOctaves(int heightOctaves) {
        this.heightOctaves = heightOctaves;
    }

    public void setHeightAmp(double heightAmp) {
        this.heightAmp = heightAmp;
    }

    public void setHeightGain(double heightGain) {
        this.heightGain = heightGain;
    }

    public void setHeightLacunarity(double heightLacunarity) {
        this.heightLacunarity = heightLacunarity;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        double px = x * heightTiles;
        double py = y * heightTiles;

        // FBM terrain
        double nx = 0.0f;
        double ny = 0.0f;
        double nz = 0.0f;
        double nf = 1.0f;
        double na = heightAmp;
        for(int i = 0; i < heightOctaves; i++) {
            double[] noise = noised(px * nf, py * nf);
            nx += noise[0] * na;
            ny += noise[1] * na * nf;
            nz += noise[2] * na * nf;
            na *= heightGain;
            nf *= heightLacunarity;
        }

        // [-1, 1] -> [0, 1]
        nx = nx * 0.5f + 0.5f;

        // Take the curl of the normal to get the gradient facing down the slope
        double dirx = nz * erosionSlopeStrength;
        double diry = -ny * erosionSlopeStrength;

        // Now we compute another fbm type noise
        // erosion is a type of noise with a strong directionality
        // we pass in the direction based on the slope of the terrain
        // erosion also returns the slope. we add that to a running total
        // so that the direction of successive layers are based on the
        // past layers
        double hx = 0.0f;
        double hy = 0.0f;
        double hz = 0.0f;

        double a = 0.5f;
        double f = 1.0f;

        int octaves = erosionOctaves;

        for(int i = 0; i < octaves; i++) {
            double[] erosionResult = erosion(px * (erosionTiles * f), py * (erosionTiles * f), dirx + hz * erosionBranchStrength,
                diry - hy * erosionBranchStrength);
            hx += erosionResult[0] * a;
            hy += erosionResult[1] * a * f;
            hz += erosionResult[2] * a * f;
            a *= erosionGain;
            f *= erosionLacunarity;
        }

        return nx + (hx - 0.5f) * erosionStrength;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y, double z) {
        return getNoiseRaw(seed, x, y);
    }
}