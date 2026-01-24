/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler;


import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.util.Arrays;
import java.util.Objects;


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
        int xSlot_ = max;
        max += 2;
        int zSlot_ = max;
        max += 2;
        int accumulatorSlot = max;
        max += 2;
        b
            .loadConstant(0d)
            .dstore(accumulatorSlot);

        for(int kx = 0; kx < kernel.length; kx++) {
            for(int ky = 0; ky < kernel[kx].length; ky++) {
                double k = kernel[kx][ky];
                if(k != 0) {
                    b.loadConstant((double) kx)
                        .dload(xSlot)
                        .dadd()
                        .dstore(xSlot_)
                        .loadConstant((double) ky)
                        .dload(zSlot)
                        .dadd()
                        .dstore(zSlot_);
                    in.build(b, seedSlot, xSlot_, ySlot, zSlot_, max)
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
