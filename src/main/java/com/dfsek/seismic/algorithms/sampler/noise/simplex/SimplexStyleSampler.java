/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.simplex;


import com.dfsek.seismic.algorithms.hashing.HashingFunctions;
import com.dfsek.seismic.algorithms.sampler.noise.DerivativeNoiseFunction;
import com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;
import com.dfsek.seismic.util.UnsafeUtils;


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

    protected static final long DOUBLE_ARRAY_BASE = UnsafeUtils.DOUBLE_ARRAY_BASE;
    protected static final int DOUBLE_ARRAY_SHIFT = UnsafeUtils.DOUBLE_ARRAY_SHIFT;

    public SimplexStyleSampler(double frequency, long salt) {
        super(frequency, salt);
    }

    protected static int gradCoordIndex(int seed, int xPrimed, int yPrimed) {
        int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed);
        hash ^= hash >> 15;
        hash &= 127 << 1;

        return hash;
    }

    protected static double gradCoord(double[] grads, int seed, int xPrimed, int yPrimed, double xd, double yd) {
        int index = SimplexStyleSampler.gradCoordIndex(seed, xPrimed, yPrimed);

        double xg = UnsafeUtils.UNSAFE.getDouble(grads,
            DOUBLE_ARRAY_BASE + (((long) index) << DOUBLE_ARRAY_SHIFT));

        double yg = UnsafeUtils.UNSAFE.getDouble(grads,
            DOUBLE_ARRAY_BASE + (((long) (index | 1)) << DOUBLE_ARRAY_SHIFT));

        return ArithmeticFunctions.fma(xd, xg, yd * yg);
    }


    protected static int gradCoordIndex(int seed, int xPrimed, int yPrimed, int zPrimed) {
        int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed, zPrimed);
        hash ^= hash >> 15;
        hash &= 63 << 2;

        return hash;
    }

    protected static double gradCoord(double[] grads, int seed, int xPrimed, int yPrimed, int zPrimed, double xd, double yd, double zd) {
        int index = SimplexStyleSampler.gradCoordIndex(seed, xPrimed, yPrimed, zPrimed);

        double xg = UnsafeUtils.UNSAFE.getDouble(grads,
            DOUBLE_ARRAY_BASE + (((long) index) << DOUBLE_ARRAY_SHIFT));

        double yg = UnsafeUtils.UNSAFE.getDouble(grads,
            DOUBLE_ARRAY_BASE + (((long) (index | 1)) << DOUBLE_ARRAY_SHIFT));

        double zg = UnsafeUtils.UNSAFE.getDouble(grads,
            DOUBLE_ARRAY_BASE + (((long) (index | 2)) << DOUBLE_ARRAY_SHIFT));

        return ArithmeticFunctions.fma(xd, xg, ArithmeticFunctions.fma(yd, yg, zd * zg));
    }

    @Override
    public double[] getNoiseDerivativeRaw(long seed, double x, double y) {
        throw new UnsupportedOperationException("Implementation failed to check or set isDifferentiable correctly");
    }

    @Override
    public double[] getNoiseDerivativeRaw(long seed, double x, double y, double z) {
        throw new UnsupportedOperationException("Implementation failed to check or set isDifferentiable correctly");
    }

    @Override
    public boolean isDifferentiable() {
        return false;
    }
}
