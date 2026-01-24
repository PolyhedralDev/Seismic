/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise;

import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;


/**
 * Sampler3D implementation that returns a constant.
 */
public class ConstantSampler implements Sampler {
    private final double constant;

    public ConstantSampler(double constant) {
        this.constant = constant;
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return constant;
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return constant;
    }

    @Override
    public Sampler frequency(double frequency) {
        return this;
    }

    @Override
    public Sampler frequency(double frequencyX, double frequencyY, double frequencyZ) {
        return this;
    }

    @Override
    public CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int zSlot, int max) {
        return b.loadConstant(constant);
    }

    @Override
    public CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int ySlot, int zSlot, int max) {
        return b.loadConstant(constant);
    }
}
