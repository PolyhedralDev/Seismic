package com.dfsek.seismic.algorithms.sampler.normalizer;

import com.dfsek.seismic.type.CubicSpline;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;


public class CubicSplineNormalizer extends Normalizer {
    private final CubicSpline spline;

    public CubicSplineNormalizer(Sampler sampler, CubicSpline spline) {
        super(sampler);
        this.spline = spline;
    }

    @Override
    public double normalize(double in) {
        return spline.apply(in);
    }

    @Override
    public CodeBuilder operator(CodeBuilder b) {
        throw new UnsupportedOperationException("");
    }
}
