/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.polyhedraldevelopment.seismic.algorithms.samplers;


public class DomainWarpedSampler implements NoiseSampler {
    private final NoiseSampler function;
    private final NoiseSampler warp;
    private final double amplitude;

    public DomainWarpedSampler(NoiseSampler function, NoiseSampler warp, double amplitude) {
        this.function = function;
        this.warp = warp;
        this.amplitude = amplitude;
    }

    @Override
    public double getNoise(long seed, double x, double y) {
        return function.getNoise(seed++,
            x + warp.getNoise(seed++, x, y) * amplitude,
            y + warp.getNoise(seed, x, y) * amplitude
        );
    }

    @Override
    public double getNoise(long seed, double x, double y, double z) {
        return function.getNoise(seed++,
            x + warp.getNoise(seed++, x, y, z) * amplitude,
            y + warp.getNoise(seed++, x, y, z) * amplitude,
            z + warp.getNoise(seed, x, y, z) * amplitude
        );
    }
}
