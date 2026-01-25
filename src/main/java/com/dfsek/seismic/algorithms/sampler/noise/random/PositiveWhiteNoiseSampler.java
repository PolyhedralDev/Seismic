/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.random;


import com.dfsek.seismic.algorithms.hashing.HashingFunctions;
import com.dfsek.seismic.algorithms.sampler.compiler.MaxS;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.CD_double;
import static java.lang.constant.ConstantDescs.CD_long;


/**
 * NoiseSampler implementation to produce random, uniformly distributed (white) noise.
 */
public class PositiveWhiteNoiseSampler implements Sampler {
    private static final long POSITIVE_POW1 = 0b01111111111L << 52;
    private static final PositiveWhiteNoiseSampler INSTANCE = new PositiveWhiteNoiseSampler();

    public static PositiveWhiteNoiseSampler instance() {
        return INSTANCE;
    }

    private PositiveWhiteNoiseSampler() {
    }
    // Bits that when applied to the exponent/sign section of a double, produce a positive number with a power of 1.

    public static double getNoiseUnmapped(long seed, double x, double y, double z) {
        long base = ((WhiteNoiseSampler.randomBits(seed, x, y, z)) & 0x000fffffffffffffL) |
                    POSITIVE_POW1; // Sign and exponent
        return Double.longBitsToDouble(base);
    }

    public static double getNoiseUnmapped(long seed, double x, double y) {
        long base = (WhiteNoiseSampler.randomBits(seed, x, y) & 0x000fffffffffffffL) | POSITIVE_POW1; // Sign and exponent
        return Double.longBitsToDouble(base);
    }

    public double getNoiseRaw(long seed) {
        return (Double.longBitsToDouble((HashingFunctions.murmur64(seed) & 0x000fffffffffffffL) | POSITIVE_POW1) -
                1);
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return (getNoiseUnmapped(seed, x, y) - 1);
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return (getNoiseUnmapped(seed, x, y, z) - 1);
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int zSlot, MaxS max) {
        return b
            .dload(seedSlot)
            .dload(xSlot)
            .dload(zSlot)
            .invokestatic(ClassDesc.of(PositiveWhiteNoiseSampler.class.getName()), "getNoiseUnmapped", MethodTypeDesc.of(CD_double, CD_long, CD_double, CD_double))
            .loadConstant(1.0D)
            .dsub();
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int ySlot, int zSlot, MaxS max) {
        return b
            .dload(seedSlot)
            .dload(xSlot)
            .dload(ySlot)
            .dload(zSlot)
            .invokestatic(ClassDesc.of(PositiveWhiteNoiseSampler.class.getName()), "getNoiseUnmapped", MethodTypeDesc.of(CD_double, CD_long, CD_double, CD_double, CD_double))
            .loadConstant(1.0D)
            .dsub();
    }
}
