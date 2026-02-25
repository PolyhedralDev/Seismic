/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.simplex;


import com.dfsek.seismic.algorithms.hashing.HashingFunctions;
import com.dfsek.seismic.algorithms.sampler.compiler.MaxS;
import com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;
import com.dfsek.seismic.math.range.Range;
import com.dfsek.seismic.type.sampler.DerivativeSampler;
import com.dfsek.seismic.util.UnsafeUtils;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.CD_double;
import static java.lang.constant.ConstantDescs.CD_long;


/**
 * Abstract NoiseSampler implementation for simplex-style noise functions.
 */
public abstract class SimplexStyleSampler implements DerivativeSampler {
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
    protected static final long DOUBLE_ARRAY_SHIFT = UnsafeUtils.DOUBLE_ARRAY_SHIFT;

    protected static int gradCoordIndex(int seed, int xPrimed, int yPrimed) {
        int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed);
        hash ^= hash >> 15;
        hash &= 127 << 1;

        return hash;
    }

    protected static double gradCoord(double[] grads, int seed, int xPrimed, int yPrimed, double xd, double yd) {
        long index = SimplexStyleSampler.gradCoordIndex(seed, xPrimed, yPrimed);

        double xg = UnsafeUtils.UNSAFE.getDouble(grads,
            DOUBLE_ARRAY_BASE + (index << DOUBLE_ARRAY_SHIFT));

        double yg = UnsafeUtils.UNSAFE.getDouble(grads,
            DOUBLE_ARRAY_BASE + ((index | 1) << DOUBLE_ARRAY_SHIFT));

        return ArithmeticFunctions.fma(xd, xg, yd * yg);
    }


    protected static int gradCoordIndex(int seed, int xPrimed, int yPrimed, int zPrimed) {
        int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed, zPrimed);
        hash ^= hash >> 15;
        hash &= 63 << 2;

        return hash;
    }

    protected static double gradCoord(double[] grads, int seed, int xPrimed, int yPrimed, int zPrimed, double xd, double yd, double zd) {
        long index = SimplexStyleSampler.gradCoordIndex(seed, xPrimed, yPrimed, zPrimed);

        double xg = UnsafeUtils.UNSAFE.getDouble(grads,
            DOUBLE_ARRAY_BASE + (index << DOUBLE_ARRAY_SHIFT));

        double yg = UnsafeUtils.UNSAFE.getDouble(grads,
            DOUBLE_ARRAY_BASE + ((index | 1) << DOUBLE_ARRAY_SHIFT));

        double zg = UnsafeUtils.UNSAFE.getDouble(grads,
            DOUBLE_ARRAY_BASE + ((index | 2) << DOUBLE_ARRAY_SHIFT));

        return ArithmeticFunctions.fma(xd, xg, ArithmeticFunctions.fma(yd, yg, zd * zg));
    }

    @Override
    public double[] getSampleDerivative(long seed, double x, double y) {
        throw new UnsupportedOperationException("Implementation failed to check or set isDifferentiable correctly");
    }

    @Override
    public double[] getSampleDerivative(long seed, double x, double y, double z) {
        throw new UnsupportedOperationException("Implementation failed to check or set isDifferentiable correctly");
    }

    @Override
    public boolean isDifferentiable() {
        return false;
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int zSlot, MaxS max) {
        return b
            .lload(seedSlot)
            .dload(xSlot)
            .dload(zSlot)
            .invokestatic(
                ClassDesc.of(getClass().getName()), "simplex", MethodTypeDesc.of(CD_double, CD_long, CD_double, CD_double));
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int ySlot, int zSlot, MaxS max) {
        return b
            .lload(seedSlot)
            .dload(xSlot)
            .dload(ySlot)
            .dload(zSlot)
            .invokestatic(ClassDesc.of(getClass().getName()), "simplex", MethodTypeDesc.of(CD_double, CD_long, CD_double, CD_double, CD_double));
    }

    @Override
    public Range range() {
        return Range.one();
    }
}
