/*
 * Copyright (c) 2020-2024 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.polyhedraldevelopment.seismic.algorithms.normalizer;


import com.polyhedraldevelopment.seismic.api.Sampler;


public abstract class Normalizer implements Sampler {
    private final Sampler sampler;

    public Normalizer(Sampler sampler) {
        this.sampler = sampler;
    }

    public abstract double normalize(double in);

    @Override
    public double getSample(long seed, double x, double y) {
        return normalize(sampler.getSample(seed, x, y));
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return normalize(sampler.getSample(seed, x, y, z));
    }
}
