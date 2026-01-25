/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.random;

import com.dfsek.seismic.algorithms.hashing.HashingFunctions;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.CD_double;
import static java.lang.constant.ConstantDescs.CD_long;


/**
 * NoiseSampler implementation to produce random, uniformly distributed (white) noise.
 */
public class WhiteNoiseSampler implements Sampler {
    private static final long POSITIVE_POW1 = 0b10000000000L << 52;
    private static final WhiteNoiseSampler INSTANCE = new WhiteNoiseSampler();
    // Bits that when applied to the exponent/sign section of a double, produce a positive number with a power of 1.

    private WhiteNoiseSampler() {
    }

    public static Sampler instance() {
        return INSTANCE;
    }

    public static long randomBits(long seed, double x, double y, double z) {
        long hashX = Double.doubleToRawLongBits(x) ^ seed;
        long hashZ = Double.doubleToRawLongBits(y) ^ seed;
        long hash = (((hashX ^ (hashX >>> 32)) + ((hashZ ^ (hashZ >>> 32)) << 32)) ^ seed) + Double.doubleToRawLongBits(z);
        return HashingFunctions.murmur64(hash);
    }

    public static long randomBits(long seed, double x, double y) {
        long hashX = Double.doubleToRawLongBits(x) ^ seed;
        long hashZ = Double.doubleToRawLongBits(y) ^ seed;
        long hash = ((hashX ^ (hashX >>> 32)) + ((hashZ ^ (hashZ >>> 32)) << 32)) ^ seed;
        return HashingFunctions.murmur64(hash);
    }

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
        return Double.longBitsToDouble((HashingFunctions.murmur64(seed) & 0x000fffffffffffffL) | POSITIVE_POW1) - 3;
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return WhiteNoiseSampler.getNoiseUnmapped(seed, x, y) - 3;
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return WhiteNoiseSampler.getNoiseUnmapped(seed, x, y, z) - 3;
    }

    @Override
    public CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int zSlot, int max) {
        return b
            .dload(seedSlot)
            .dload(xSlot)
            .dload(zSlot)
            .invokestatic(ClassDesc.of(WhiteNoiseSampler.class.getName()), "getNoiseUnmapped", MethodTypeDesc.of(CD_double, CD_long, CD_double, CD_double))
            .loadConstant(3.0D)
            .dsub();
    }

    @Override
    public CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int ySlot, int zSlot, int max) {
        return b
            .dload(seedSlot)
            .dload(xSlot)
            .dload(ySlot)
            .dload(zSlot)
            .invokestatic(ClassDesc.of(WhiteNoiseSampler.class.getName()), "getNoiseUnmapped", MethodTypeDesc.of(CD_double, CD_long, CD_double, CD_double, CD_double))
            .loadConstant(3.0D)
            .dsub();
    }
}
