/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.random;


import com.dfsek.seismic.math.range.Range;
import com.dfsek.seismic.type.sampler.Sampler;


/**
 * NoiseSampler implementation to provide random, normally distributed (Gaussian) noise.
 */
public class GaussianNoiseSampler implements Sampler {

    public GaussianNoiseSampler() {
    }

    public static double random(long seed, double x, double y) {
        Sampler whiteNoiseSampler = WhiteNoiseSampler.instance();
        double v1, v2, s;
        do {
            v1 = whiteNoiseSampler.getSample(seed++, x, y);
            v2 = whiteNoiseSampler.getSample(seed++, x, y);
            s = v1 * v1 + v2 * v2;
        } while(s >= 1 || s == 0);
        double multiplier = Math.sqrt(-2 * Math.log(s) / s);
        return v1 * multiplier;
    }

    public static double random(long seed, double x, double y, double z) {
        Sampler whiteNoiseSampler = WhiteNoiseSampler.instance();
        double v1, v2, s;
        do {
            v1 = whiteNoiseSampler.getSample(seed++, x, y, z);
            v2 = whiteNoiseSampler.getSample(seed++, x, y, z);
            s = v1 * v1 + v2 * v2;
        } while(s >= 1 || s == 0);
        double multiplier = Math.sqrt(-2 * Math.log(s) / s);
        return v1 * multiplier;
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return random(seed, x, y);
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return random(seed, x, y, z);
    }
    @Override
    public Range range() {
        return Range.one();
    }
}
