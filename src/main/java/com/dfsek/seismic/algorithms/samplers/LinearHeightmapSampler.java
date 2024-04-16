package com.dfsek.seismic.algorithms.samplers;


public class LinearHeightmapSampler implements NoiseSampler {
    private final NoiseSampler sampler;
    private final double scale;
    private final double base;

    public LinearHeightmapSampler(NoiseSampler sampler, double scale, double base) {
        this.sampler = sampler;
        this.scale = scale;
        this.base = base;
    }


    @Override
    public double getNoise(long seed, double x, double y) {
        return getNoise(seed, x, 0, y);
    }

    @Override
    public double getNoise(long seed, double x, double y, double z) {
        return -y + base + sampler.getNoise(seed, x, y, z) * scale;
    }
}
