/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler;


import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;


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

    @Override
    public CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int zSlot, int max) {
        int seedX = max;
        max += 2;
        int seedY = max;
        max += 2;
        return function.build(warp.build(warp.build(b
                    .lload(seedSlot)
                    .dup2()
                    .loadConstant(1L)
                    .ladd()
                    .lstore(seedX)
                    .loadConstant(2L)
                    .ladd()
                    .lstore(seedY), seedX, xSlot, zSlot, max)
                .dload(zSlot)
                .dadd()
                .dstore(seedX), seedY, xSlot, zSlot, max)
            .dload(xSlot)
            .dadd()
            .dstore(seedY), seedSlot, seedX, seedY, max);

    }

    @Override
    public CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int ySlot, int zSlot, int max) {
        int seedX = max;
        max += 2;
        int seedY = max;
        max += 2;
        int seedZ = max;
        max += 2;
        return function.build(
            warp.build(
                    warp.build(
                            warp.build(b
                                    .lload(seedSlot)
                                    .dup2()
                                    .dup2()
                                    .loadConstant(1L)
                                    .ladd()
                                    .lstore(seedX)
                                    .loadConstant(2L)
                                    .ladd()
                                    .lstore(seedY)
                                    .loadConstant(3L)
                                    .ladd()
                                    .lstore(seedZ), seedX, xSlot, ySlot, zSlot, max)
                                .dload(xSlot)
                                .dadd()
                                .dstore(seedX), seedY, xSlot, ySlot, zSlot, max)
                        .dload(ySlot)
                        .dadd()
                        .dstore(seedY), seedZ, xSlot, ySlot, zSlot, max)
                .dload(zSlot)
                .dadd()
                .dstore(seedZ), seedSlot, seedX, seedY, seedZ, max);
    }
}
