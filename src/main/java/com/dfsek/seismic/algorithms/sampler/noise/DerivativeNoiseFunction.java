package com.dfsek.seismic.algorithms.sampler.noise;


import com.dfsek.seismic.type.sampler.DerivativeSampler;


public abstract class DerivativeNoiseFunction extends NoiseFunction implements DerivativeSampler {
    public DerivativeNoiseFunction(double frequency, long salt) {
        super(frequency, salt);
    }

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
