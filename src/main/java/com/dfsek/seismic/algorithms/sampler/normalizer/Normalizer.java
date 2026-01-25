/*
 * Copyright (c) 2020-2024 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.normalizer;


import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;


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

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int zSlot, int max) {
        return sampler.build(b, clazz, seedSlot, xSlot, zSlot, max);
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int ySlot, int zSlot, int max) {
        return sampler.build(b, clazz, seedSlot, xSlot, ySlot, zSlot, max);
    }

    public abstract CodeBuilder operator(CodeBuilder b);
}
