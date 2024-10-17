package com.polyhedraldevelopment.seismic.algorithms.sampler;


import com.polyhedraldevelopment.seismic.type.sampler.Sampler;


public class LinearHeightmapSampler implements Sampler {
    private final Sampler sampler;
    private final double scale;
    private final double base;

    public LinearHeightmapSampler(Sampler sampler, double scale, double base) {
        this.sampler = sampler;
        this.scale = scale;
        this.base = base;
    }


    @Override
    public double getSample(long seed, double x, double y) {
        return getSample(seed, x, 0, y);
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return -y + base + sampler.getSample(seed, x, y, z) * scale;
    }
}
