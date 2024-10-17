/*
 * Copyright (c) 2022 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.normalizer;


import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.type.sampler.Sampler;


public class PosterizationNormalizer extends Normalizer {
    private final double stepSize;

    public PosterizationNormalizer(Sampler sampler, int steps) {
        super(sampler);
        this.stepSize = 2.0 / (steps - 1);
    }

    @Override
    public double normalize(double in) {
        return FloatingPointFunctions.round((in + 1) / stepSize) * stepSize - 1;
    }
}
