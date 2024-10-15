package com.polyhedraldevelopment.seismic.algorithms.sampler.noise;


import com.polyhedraldevelopment.seismic.api.DerivativeSampler;


public abstract class DerivativeNoiseFunction extends NoiseFunction implements DerivativeSampler {
    @Override
    public boolean isDifferentiable() {
        return true;
    }

    @Override
    public double[] getSampleDerivative(long seed, double x, double y) {
        return getNoiseDerivativeRaw(seed + salt, x * frequency, y * frequency);
    }

    @Override
    public double[] getSampleDerivative(long seed, double x, double y, double z) {
        return getNoiseDerivativeRaw(seed + salt, x * frequency, y * frequency, z * frequency);
    }

    public abstract double[] getNoiseDerivativeRaw(long seed, double x, double y);

    public abstract double[] getNoiseDerivativeRaw(long seed, double x, double y, double z);
}
