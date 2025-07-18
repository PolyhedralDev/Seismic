/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise;

/**
 * Sampler3D implementation that returns a constant.
 */
public class ConstantSampler extends NoiseFunction {
    private final double constant;

    public ConstantSampler(double constant) {
        super(0, 0);
        this.constant = constant;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        return constant;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y, double z) {
        return constant;
    }
}
