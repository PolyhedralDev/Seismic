/*
 * Copyright (c) 2020-2024 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.normalizer;


import com.dfsek.seismic.type.sampler.Sampler;


public class ClampNormalizer extends Normalizer {
    private final double min;
    private final double max;

    public ClampNormalizer(Sampler sampler, double min, double max) {
        super(sampler);
        this.min = min;
        this.max = max;
    }

    @Override
    public double normalize(double in) {
        return Math.max(Math.min(in, max), min);
    }
}
