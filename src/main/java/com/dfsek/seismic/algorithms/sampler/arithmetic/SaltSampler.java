package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.type.sampler.Sampler;


public class SaltSampler implements Sampler {
    private final long salt;
    private final Sampler in;

    private SaltSampler(long salt, Sampler in) {
        this.salt = salt;
        this.in = in;
    }

    public static SaltSampler salt(long salt, Sampler in) {
        if(in instanceof SaltSampler s) return new SaltSampler(s.salt + salt, s.in);
        return new SaltSampler(salt, in);
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return in.getSample(seed + salt, x, y);
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return in.getSample(seed + salt, x, y, z);
    }
}
