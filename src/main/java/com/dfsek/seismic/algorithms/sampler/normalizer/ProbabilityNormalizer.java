package com.dfsek.seismic.algorithms.sampler.normalizer;


import com.dfsek.seismic.type.sampler.Sampler;


public class ProbabilityNormalizer extends Normalizer {
    public ProbabilityNormalizer(Sampler sampler) {
        super(sampler);
    }

    @Override
    public double normalize(double in) {
        return (in + 1) / 2;
    }
}
