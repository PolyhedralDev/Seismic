package com.dfsek.seismic.algorithms.samplers;


public class TranslateSampler implements NoiseSampler {

    private final NoiseSampler sampler;
    private final double dx, dy, dz;

    public TranslateSampler(NoiseSampler sampler, double dx, double dy, double dz) {
        this.sampler = sampler;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    @Override
    public double getNoise(long seed, double x, double y) {
        return sampler.getNoise(seed, x - dx, y - dz);
    }

    @Override
    public double getNoise(long seed, double x, double y, double z) {
        return sampler.getNoise(seed, x - dx, y - dy, z - dz);
    }
}
