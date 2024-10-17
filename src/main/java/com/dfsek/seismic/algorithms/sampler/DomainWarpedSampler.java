/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler;


import com.dfsek.seismic.type.sampler.Sampler;


public class DomainWarpedSampler implements Sampler {
    private final Sampler function;
    private final Sampler warp;
    private final double amplitude;

    public DomainWarpedSampler(Sampler function, Sampler warp, double amplitude) {
        this.function = function;
        this.warp = warp;
        this.amplitude = amplitude;
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return function.getSample(seed++,
            x + warp.getSample(seed++, x, y) * amplitude,
            y + warp.getSample(seed, x, y) * amplitude
        );
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return function.getSample(seed++,
            x + warp.getSample(seed++, x, y, z) * amplitude,
            y + warp.getSample(seed++, x, y, z) * amplitude,
            z + warp.getSample(seed, x, y, z) * amplitude
        );
    }
}
