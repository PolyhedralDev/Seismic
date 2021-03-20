package com.dfsek.seismic.normalizer;

import com.dfsek.seismic.NoiseSampler;
import net.jafama.FastMath;

public class ClampNormalizer extends Normalizer {
    private final double min;
    private final double max;

    public ClampNormalizer(NoiseSampler sampler, double min, double max) {
        super(sampler);
        this.min = min;
        this.max = max;
    }

    @Override
    public double normalize(double in) {
        return FastMath.max(FastMath.min(in, max), min);
    }
}
