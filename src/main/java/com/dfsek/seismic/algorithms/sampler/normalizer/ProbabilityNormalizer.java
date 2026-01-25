package com.dfsek.seismic.algorithms.sampler.normalizer;


import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;


public class ProbabilityNormalizer extends Normalizer {
    public ProbabilityNormalizer(Sampler sampler) {
        super(sampler);
    }

    @Override
    public double normalize(double in) {
        return (in + 1) / 2;
    }

    @Override
    public CodeBuilder operator(CodeBuilder b) {
        return b.loadConstant(1D)
            .dadd()
            .loadConstant(2D)
            .ddiv();
    }
}
