package com.polyhedraldevelopment.seismic.algorithms.normalizer;


import com.polyhedraldevelopment.seismic.api.Sampler;


public class ProbabilityNormalizer extends Normalizer {
    public ProbabilityNormalizer(Sampler sampler) {
        super(sampler);
    }

    @Override
    public double normalize(double in) {
        return (in + 1) / 2;
    }
}
