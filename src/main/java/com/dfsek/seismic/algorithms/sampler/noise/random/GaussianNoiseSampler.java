/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.random;


import com.dfsek.seismic.algorithms.sampler.noise.NoiseFunction;


/**
 * NoiseSampler implementation to provide random, normally distributed (Gaussian) noise.
 */
public class GaussianNoiseSampler extends NoiseFunction {
    private final WhiteNoiseSampler whiteNoiseSampler; // Back with a white noise sampler.

    public GaussianNoiseSampler(double frequency, long salt) {
        super(frequency, salt);
        whiteNoiseSampler = new WhiteNoiseSampler(frequency, salt);
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        double v1, v2, s;
        do {
            v1 = whiteNoiseSampler.getSample(seed++, x, y);
            v2 = whiteNoiseSampler.getSample(seed++, x, y);
            s = v1 * v1 + v2 * v2;
        } while(s >= 1 || s == 0);
        double multiplier = Math.sqrt(-2 * Math.log(s) / s);
        return v1 * multiplier;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y, double z) {
        double v1, v2, s;
        do {
            v1 = whiteNoiseSampler.getSample(seed++, x, y, z);
            v2 = whiteNoiseSampler.getSample(seed++, x, y, z);
            s = v1 * v1 + v2 * v2;
        } while(s >= 1 || s == 0);
        double multiplier = Math.sqrt(-2 * Math.log(s) / s);
        return v1 * multiplier;
    }
}
