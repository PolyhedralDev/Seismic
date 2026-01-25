package com.dfsek.seismic.algorithms.sampler.normalizer;


import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.CD_double;


public class LinearMapNormalizer extends Normalizer {

    private final double aFrom;

    private final double aTo;

    private final double bFrom;

    private final double bTo;

    public LinearMapNormalizer(Sampler sampler, double aFrom, double aTo, double bFrom, double bTo) {
        super(sampler);
        this.aFrom = aFrom;
        this.aTo = aTo;
        this.bFrom = bFrom;
        this.bTo = bTo;
    }

    @Override
    public double normalize(double in) {
        return (in - aFrom) * (aTo - bTo) / (aFrom - bFrom) + aTo;
    }
    @Override
    public CodeBuilder operator(CodeBuilder b) {
        return b.loadConstant(aFrom)
            .dsub()
            .loadConstant((aTo - bTo) / (aFrom - bFrom))
            .dmul()
            .loadConstant(aTo)
            .dadd();
    }
}
