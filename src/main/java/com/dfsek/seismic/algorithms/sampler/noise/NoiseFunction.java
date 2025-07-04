/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise;

import com.dfsek.seismic.type.sampler.Sampler;


public abstract class NoiseFunction implements Sampler {
    /**
     * The prime number used for the x-coordinate in noise generation.
     */
    public static final int PRIME_X = 501125321;

    /**
     * The prime number used for the y-coordinate in noise generation.
     */
    public static final int PRIME_Y = 1136930381;

    /**
     * The prime number used for the z-coordinate in noise generation.
     */
    public static final int PRIME_Z = 1720413743;

    protected final double frequency;
    protected final long salt;

    public NoiseFunction(double frequency, long salt) {
        this.frequency = frequency;
        this.salt = salt;
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return getNoiseRaw(seed + salt, x * frequency, y * frequency);
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return getNoiseRaw(seed + salt, x * frequency, y * frequency, z * frequency);
    }

    public abstract double getNoiseRaw(long seed, double x, double y);

    public abstract double getNoiseRaw(long seed, double x, double y, double z);
}
