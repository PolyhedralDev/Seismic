package com.polyhedraldevelopment.seismic.algorithms.normalizer;


import com.polyhedraldevelopment.seismic.type.sampler.Sampler;


public class ScaleNormalizer extends Normalizer {
    private final double scale;

    public ScaleNormalizer(Sampler sampler, double scale) {
        super(sampler);
        this.scale = scale;
    }

    @Override
    public double normalize(double in) {
        return in * scale;
    }
}
