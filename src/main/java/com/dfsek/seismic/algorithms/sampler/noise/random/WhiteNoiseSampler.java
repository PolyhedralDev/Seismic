/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.random;

import com.dfsek.seismic.algorithms.hashing.HashingFunctions;
import com.dfsek.seismic.algorithms.sampler.noise.NoiseFunction;


/**
 * NoiseSampler implementation to produce random, uniformly distributed (white) noise.
 */
public class WhiteNoiseSampler extends NoiseFunction {
    private static final long POSITIVE_POW1 = 0b01111111111L << 52;
    // Bits that when applied to the exponent/sign section of a double, produce a positive number with a power of 1.

    public WhiteNoiseSampler() {
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
                    WhiteNoiseSampler.POSITIVE_POW1; // Sign and exponent
        return Double.longBitsToDouble(base);
    }

    public static double getNoiseUnmapped(long seed, double x, double y) {
        long base = (WhiteNoiseSampler.randomBits(seed, x, y) & 0x000fffffffffffffL) | WhiteNoiseSampler.POSITIVE_POW1; // Sign and exponent
        return Double.longBitsToDouble(base);
    }

    public double getNoiseRaw(long seed) {
        return (Double.longBitsToDouble((HashingFunctions.murmur64(seed) & 0x000fffffffffffffL) | WhiteNoiseSampler.POSITIVE_POW1) - 1.5) *
               2;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        return (WhiteNoiseSampler.getNoiseUnmapped(seed, x, y) - 1.5) * 2;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y, double z) {
        return (WhiteNoiseSampler.getNoiseUnmapped(seed, x, y, z) - 1.5) * 2;
    }
}
