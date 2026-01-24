package com.dfsek.seismic.algorithms.sampler;


import com.dfsek.seismic.type.sampler.Sampler;

import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof TranslateSampler that)) return false;
        return Double.compare(dx, that.dx) == 0 && Double.compare(dy, that.dy) == 0 && Double.compare(dz, that.dz) == 0 && Objects.equals(
            sampler, that.sampler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sampler, dx, dy, dz);
    }
}
