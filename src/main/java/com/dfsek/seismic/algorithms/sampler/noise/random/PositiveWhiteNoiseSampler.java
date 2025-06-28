/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.random;


import com.dfsek.seismic.algorithms.hashing.HashingFunctions;


/**
 * NoiseSampler implementation to produce random, uniformly distributed (white) noise.
 */
public class PositiveWhiteNoiseSampler extends WhiteNoiseSampler {
    private static final long POSITIVE_POW1 = 0b01111111111L << 52;

    public PositiveWhiteNoiseSampler(double frequency, long salt) {
        super(frequency, salt);
    }
    // Bits that when applied to the exponent/sign section of a double, produce a positive number with a power of 1.

    public double getNoiseRaw(long seed) {
        return (Double.longBitsToDouble((HashingFunctions.murmur64(seed) & 0x000fffffffffffffL) | PositiveWhiteNoiseSampler.POSITIVE_POW1) -
                1.5) * 2;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        return (WhiteNoiseSampler.getNoiseUnmapped(seed, x, y) - 1);
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y, double z) {
        return (WhiteNoiseSampler.getNoiseUnmapped(seed, x, y, z) - 1);
    }
}
