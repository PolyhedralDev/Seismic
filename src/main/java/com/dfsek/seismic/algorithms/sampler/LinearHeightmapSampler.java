package com.dfsek.seismic.algorithms.sampler;


import com.dfsek.seismic.math.range.Range;
import com.dfsek.seismic.type.sampler.Sampler;

import java.util.Objects;


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

    @Override
    public Range range() {
        return Range.infinity();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof LinearHeightmapSampler that)) return false;
        return Double.compare(scale, that.scale) == 0 && Double.compare(base, that.base) == 0 && Objects.equals(sampler, that.sampler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sampler, scale, base);
    }
}
