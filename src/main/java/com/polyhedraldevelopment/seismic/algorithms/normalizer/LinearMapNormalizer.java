package com.polyhedraldevelopment.seismic.algorithms.normalizer;


import com.polyhedraldevelopment.seismic.type.sampler.Sampler;


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
}
