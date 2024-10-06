/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.polyhedraldevelopment.seismic.algorithms.samplers.noise.simplex;


import com.polyhedraldevelopment.seismic.algorithms.hashing.HashingFunctions;
import com.polyhedraldevelopment.seismic.algorithms.samplers.noise.DerivativeNoiseFunction;


/**
 * Abstract NoiseSampler implementation for simplex-style noise functions.
 */
public abstract class SimplexStyleSampler extends DerivativeNoiseFunction {
    protected static final double[] GRADIENTS_2D = {
        0.130526192220052d, 0.99144486137381d, 0.38268343236509d, 0.923879532511287d, 0.608761429008721d, 0.793353340291235d,
        0.793353340291235d, 0.608761429008721d, 0.923879532511287d, 0.38268343236509d, 0.99144486137381d, 0.130526192220051d,
        0.99144486137381d, -0.130526192220051d, 0.923879532511287d, -0.38268343236509d, 0.793353340291235d, -0.60876142900872d,
        0.608761429008721d, -0.793353340291235d, 0.38268343236509d, -0.923879532511287d, 0.130526192220052d, -0.99144486137381d,
        -0.130526192220052d, -0.99144486137381d, -0.38268343236509d, -0.923879532511287d, -0.608761429008721d, -0.793353340291235d,
        -0.793353340291235d, -0.608761429008721d, -0.923879532511287d, -0.38268343236509d, -0.99144486137381d, -0.130526192220052d,
        -0.99144486137381d, 0.130526192220051d, -0.923879532511287d, 0.38268343236509d, -0.793353340291235d, 0.608761429008721d,
        -0.608761429008721d, 0.793353340291235d, -0.38268343236509d, 0.923879532511287d, -0.130526192220052d, 0.99144486137381d,
        0.130526192220052d, 0.99144486137381d, 0.38268343236509d, 0.923879532511287d, 0.608761429008721d, 0.793353340291235d,
        0.793353340291235d, 0.608761429008721d, 0.923879532511287d, 0.38268343236509d, 0.99144486137381d, 0.130526192220051d,
        0.99144486137381d, -0.130526192220051d, 0.923879532511287d, -0.38268343236509d, 0.793353340291235d, -0.60876142900872d,
        0.608761429008721d, -0.793353340291235d, 0.38268343236509d, -0.923879532511287d, 0.130526192220052d, -0.99144486137381d,
        -0.130526192220052d, -0.99144486137381d, -0.38268343236509d, -0.923879532511287d, -0.608761429008721d, -0.793353340291235d,
        -0.793353340291235d, -0.608761429008721d, -0.923879532511287d, -0.38268343236509d, -0.99144486137381d, -0.130526192220052d,
        -0.99144486137381d, 0.130526192220051d, -0.923879532511287d, 0.38268343236509d, -0.793353340291235d, 0.608761429008721d,
        -0.608761429008721d, 0.793353340291235d, -0.38268343236509d, 0.923879532511287d, -0.130526192220052d, 0.99144486137381d,
        0.130526192220052d, 0.99144486137381d, 0.38268343236509d, 0.923879532511287d, 0.608761429008721d, 0.793353340291235d,
        0.793353340291235d, 0.608761429008721d, 0.923879532511287d, 0.38268343236509d, 0.99144486137381d, 0.130526192220051d,
        0.99144486137381d, -0.130526192220051d, 0.923879532511287d, -0.38268343236509d, 0.793353340291235d, -0.60876142900872d,
        0.608761429008721d, -0.793353340291235d, 0.38268343236509d, -0.923879532511287d, 0.130526192220052d, -0.99144486137381d,
        -0.130526192220052d, -0.99144486137381d, -0.38268343236509d, -0.923879532511287d, -0.608761429008721d, -0.793353340291235d,
        -0.793353340291235d, -0.608761429008721d, -0.923879532511287d, -0.38268343236509d, -0.99144486137381d, -0.130526192220052d,
        -0.99144486137381d, 0.130526192220051d, -0.923879532511287d, 0.38268343236509d, -0.793353340291235d, 0.608761429008721d,
        -0.608761429008721d, 0.793353340291235d, -0.38268343236509d, 0.923879532511287d, -0.130526192220052d, 0.99144486137381d,
        0.130526192220052d, 0.99144486137381d, 0.38268343236509d, 0.923879532511287d, 0.608761429008721d, 0.793353340291235d,
        0.793353340291235d, 0.608761429008721d, 0.923879532511287d, 0.38268343236509d, 0.99144486137381d, 0.130526192220051d,
        0.99144486137381d, -0.130526192220051d, 0.923879532511287d, -0.38268343236509d, 0.793353340291235d, -0.60876142900872d,
        0.608761429008721d, -0.793353340291235d, 0.38268343236509d, -0.923879532511287d, 0.130526192220052d, -0.99144486137381d,
        -0.130526192220052d, -0.99144486137381d, -0.38268343236509d, -0.923879532511287d, -0.608761429008721d, -0.793353340291235d,
        -0.793353340291235d, -0.608761429008721d, -0.923879532511287d, -0.38268343236509d, -0.99144486137381d, -0.130526192220052d,
        -0.99144486137381d, 0.130526192220051d, -0.923879532511287d, 0.38268343236509d, -0.793353340291235d, 0.608761429008721d,
        -0.608761429008721d, 0.793353340291235d, -0.38268343236509d, 0.923879532511287d, -0.130526192220052d, 0.99144486137381d,
        0.130526192220052d, 0.99144486137381d, 0.38268343236509d, 0.923879532511287d, 0.608761429008721d, 0.793353340291235d,
        0.793353340291235d, 0.608761429008721d, 0.923879532511287d, 0.38268343236509d, 0.99144486137381d, 0.130526192220051d,
        0.99144486137381d, -0.130526192220051d, 0.923879532511287d, -0.38268343236509d, 0.793353340291235d, -0.60876142900872d,
        0.608761429008721d, -0.793353340291235d, 0.38268343236509d, -0.923879532511287d, 0.130526192220052d, -0.99144486137381d,
        -0.130526192220052d, -0.99144486137381d, -0.38268343236509d, -0.923879532511287d, -0.608761429008721d, -0.793353340291235d,
        -0.793353340291235d, -0.608761429008721d, -0.923879532511287d, -0.38268343236509d, -0.99144486137381d, -0.130526192220052d,
        -0.99144486137381d, 0.130526192220051d, -0.923879532511287d, 0.38268343236509d, -0.793353340291235d, 0.608761429008721d,
        -0.608761429008721d, 0.793353340291235d, -0.38268343236509d, 0.923879532511287d, -0.130526192220052d, 0.99144486137381d,
        0.38268343236509d, 0.923879532511287d, 0.923879532511287d, 0.38268343236509d, 0.923879532511287d, -0.38268343236509d,
        0.38268343236509d, -0.923879532511287d, -0.38268343236509d, -0.923879532511287d, -0.923879532511287d, -0.38268343236509d,
        -0.923879532511287d, 0.38268343236509d, -0.38268343236509d, 0.923879532511287d,
        };

    protected static final double[] GRADIENTS_3D = {
        0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0,
        1, 0, 1, 0, -1, 0, 1, 0, 1, 0, -1, 0, -1, 0, -1, 0,
        1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 0,
        0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0,
        1, 0, 1, 0, -1, 0, 1, 0, 1, 0, -1, 0, -1, 0, -1, 0,
        1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 0,
        0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0,
        1, 0, 1, 0, -1, 0, 1, 0, 1, 0, -1, 0, -1, 0, -1, 0,
        1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 0,
        0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0,
        1, 0, 1, 0, -1, 0, 1, 0, 1, 0, -1, 0, -1, 0, -1, 0,
        1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 0,
        0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0,
        1, 0, 1, 0, -1, 0, 1, 0, 1, 0, -1, 0, -1, 0, -1, 0,
        1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 0,
        1, 1, 0, 0, 0, -1, 1, 0, -1, 1, 0, 0, 0, -1, -1, 0
    };

    protected static int gradCoordIndex(int seed, int xPrimed, int yPrimed) {
        int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed);
        hash ^= hash >> 15;
        hash &= 127 << 1;

        return hash;
    }

    protected static double gradCoord(int seed, int xPrimed, int yPrimed, double xd, double yd) {
        int index = gradCoordIndex(seed, xPrimed, yPrimed);

        double xg = GRADIENTS_2D[index];
        double yg = GRADIENTS_2D[index | 1];

        return xd * xg + yd * yg;
    }

    protected static int gradCoordIndex(int seed, int xPrimed, int yPrimed, int zPrimed) {
        int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed, zPrimed);
        hash ^= hash >> 15;
        hash &= 63 << 2;

        return hash;
    }

    protected static double gradCoord(int seed, int xPrimed, int yPrimed, int zPrimed, double xd, double yd, double zd) {
        int index = gradCoordIndex(seed, xPrimed, yPrimed, zPrimed);

        double xg = GRADIENTS_3D[index];
        double yg = GRADIENTS_3D[index | 1];
        double zg = GRADIENTS_3D[index | 2];

        return xd * xg + yd * yg + zd * zg;
    }

    @Override
    public double[] getNoiseDerivativeRaw(long seed, double x, double y) {
        return new double[]{ 0, 0, 0 };
    }

    @Override
    public double[] getNoiseDerivativeRaw(long seed, double x, double y, double z) {
        return new double[]{ 0, 0, 0, 0 };
    }

    @Override
    public boolean isDifferentiable() {
        return false;
    }
}