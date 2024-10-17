package com.polyhedraldevelopment.seismic.algorithms.sampler;


import com.polyhedraldevelopment.seismic.type.sampler.Sampler;

public class TranslateSampler implements Sampler {

    private final Sampler sampler;
    private final double dx, dy, dz;

    public TranslateSampler(Sampler sampler, double dx, double dy, double dz) {
        this.sampler = sampler;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return sampler.getSample(seed, x - dx, y - dz);
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return sampler.getSample(seed, x - dx, y - dy, z - dz);
    }
}
