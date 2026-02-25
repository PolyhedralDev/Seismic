/*
 * Copyright (c) 2020-2024 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.normalizer;


import com.dfsek.seismic.math.range.Range;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.CD_double;


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

    @Override
    public CodeBuilder operator(CodeBuilder b) {
        return b.loadConstant(max)
            .invokestatic(ClassDesc.of("java.lang.Math"), "min", MethodTypeDesc.of(CD_double, CD_double, CD_double))
            .loadConstant(min)
            .invokestatic(ClassDesc.of("java.lang.Math"), "max", MethodTypeDesc.of(CD_double, CD_double, CD_double));
    }

    @Override
    public Range range() {
        return new Range(min, max);
    }
}
