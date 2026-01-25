package com.dfsek.seismic.algorithms.sampler.normalizer;


import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;


public class ScaleNormalizer extends Normalizer {
    private final double scale;

    public static Sampler of(Sampler sampler, double scale) {
        if(sampler instanceof ScaleNormalizer s) return of(s.sampler, scale * s.scale);
        if(FloatingPointFunctions.equals(scale, 1)) return sampler;
        if(FloatingPointFunctions.equals(scale, 0)) return Sampler.zero();
        return new ScaleNormalizer(sampler, scale);
    }

    private ScaleNormalizer(Sampler sampler, double scale) {
        super(sampler);
        this.scale = scale;
    }

    @Override
    public double normalize(double in) {
        return in * scale;
    }

    @Override
    public CodeBuilder operator(CodeBuilder b) {
        return b.loadConstant(scale).dmul();
    }
}
