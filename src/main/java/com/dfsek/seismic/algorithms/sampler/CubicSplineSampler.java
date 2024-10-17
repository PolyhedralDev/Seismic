package com.dfsek.seismic.algorithms.sampler;

import com.dfsek.seismic.type.CubicSpline;
import com.dfsek.seismic.type.sampler.Sampler;


public class CubicSplineSampler implements Sampler {
    private final Sampler sampler;

    private final CubicSpline spline;

    public CubicSplineSampler(Sampler sampler, CubicSpline spline) {
        this.sampler = sampler;
        this.spline = spline;
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return spline.apply(sampler.getSample(seed, x, y));
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return spline.apply(sampler.getSample(seed, x, y, z));
    }
}
