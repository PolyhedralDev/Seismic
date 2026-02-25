/*
 * Copyright (c) 2022 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.normalizer;


import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.math.range.Range;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.CD_double;


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

    @Override
    public CodeBuilder operator(CodeBuilder b) {
        return b
            .loadConstant(1D)
            .dup2()
            .dadd()
            .loadConstant(stepSize)
            .dup2()
            .ddiv()
            .invokestatic(ClassDesc.of(FloatingPointFunctions.class.getName()), "round", MethodTypeDesc.of(CD_double, CD_double))
            .dmul()
            .ddiv();
    }

    @Override
    public Range range() {
        return sampler.range();
    }
}
