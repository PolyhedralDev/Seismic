/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler;


import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.type.sampler.Sampler;
import com.dfsek.seismic.type.vector.Vector2Int;

import java.lang.classfile.CodeBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


public class KernelSampler implements Sampler {
    private final double[][] kernel;
    private final Sampler in;

    public KernelSampler(double[][] kernel, Sampler in) {
        this.kernel = kernel;
        this.in = in;
    }

    @Override
    public double getSample(long seed, double x, double y) {
        double accumulator = 0;

        for(int kx = 0; kx < kernel.length; kx++) {
            for(int ky = 0; ky < kernel[kx].length; ky++) {
                double k = kernel[kx][ky];
                if(k != 0) {
                    accumulator += in.getSample(seed, x + kx, y + ky) * k;
                }
            }
        }

        return accumulator;
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        double accumulator = 0;

        for(int kx = 0; kx < kernel.length; kx++) {
            for(int ky = 0; ky < kernel[kx].length; ky++) {
                double k = kernel[kx][ky];
                if(k != 0) {
                    accumulator += in.getSample(seed, x + kx, y, z + ky) * k;
                }
            }
        }

        return accumulator;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof KernelSampler that)) return false;
        return Objects.deepEquals(kernel, that.kernel) && Objects.equals(in, that.in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(kernel), in);
    }

    @Override
    public CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int ySlot, int zSlot, int max) {
        int accumulatorSlot = max;
        max += 2;
        b
            .loadConstant(0d)
            .dstore(accumulatorSlot);

        Set<Vector2Int> requiredCells = new HashSet<>();

        for(int kx = 0; kx < kernel.length; kx++) {
            for(int ky = 0; ky < kernel[kx].length; ky++) {
                double k = kernel[kx][ky];
                if(!FloatingPointFunctions.equals(0, k)) {
                    requiredCells.add(Vector2Int.of(kx, ky));
                }
            }
        }
        Set<Integer> requiredX = requiredCells.stream().map(Vector2Int::getX).collect(Collectors.toSet());
        Set<Integer> requiredZ = requiredCells.stream().map(Vector2Int::getZ).collect(Collectors.toSet());

        Map<Integer, Integer> xVals = new HashMap<>();
        Map<Integer, Integer> zVals = new HashMap<>();

        for(Integer x : requiredX) {
            if(x == 0) {
                xVals.put(x, xSlot);
                continue;
            }
            xVals.put(x, max);
            b.loadConstant((double) x)
                .dload(xSlot)
                .dadd()
                .dstore(max);
            max+=2;
        }

        for(Integer z : requiredZ) {
            if(z == 0) {
                zVals.put(z, zSlot);
                continue;
            }
            zVals.put(z, max);
            b.loadConstant((double) z)
                .dload(zSlot)
                .dadd()
                .dstore(max);
            max+=2;
        }

        for(int kx = 0; kx < kernel.length; kx++) {
            for(int ky = 0; ky < kernel[kx].length; ky++) {
                double k = kernel[kx][ky];
                if(!FloatingPointFunctions.equals(0, k)) {
                    in.build(b, seedSlot, xVals.get(kx), ySlot, zVals.get(ky), max)
                        .loadConstant(k)
                        .dmul()
                        .dload(accumulatorSlot)
                        .dadd()
                        .dstore(accumulatorSlot);
                }
            }
        }

        return b.dload(accumulatorSlot);
    }
}
