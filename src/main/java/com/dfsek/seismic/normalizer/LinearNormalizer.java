package com.dfsek.seismic.normalizer;


import com.dfsek.seismic.NoiseSampler;

/**
 * Normalizer to linearly scale data's range.
 */
public class LinearNormalizer extends Normalizer {
    private final double min;
    private final double max;

    public LinearNormalizer(NoiseSampler sampler, double min, double max) {
        super(sampler);
        this.min = min;
        this.max = max;
    }

    @Override
    public double normalize(double in) {
        return (in - min) * (2 / (max - min)) - 1;
    }
}
