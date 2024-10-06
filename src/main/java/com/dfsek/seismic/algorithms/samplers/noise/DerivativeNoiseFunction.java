package com.dfsek.seismic.algorithms.samplers.noise;


import com.dfsek.seismic.algorithms.samplers.DerivativeNoiseSampler;

public abstract class DerivativeNoiseFunction extends NoiseFunction implements DerivativeNoiseSampler {
    @Override
    public boolean isDifferentiable() {
        return true;
    }

    @Override
    public double[] noised(long seed, double x, double y) {
        return getNoiseDerivativeRaw(seed + salt, x * frequency, y * frequency);
    }

    @Override
    public double[] noised(long seed, double x, double y, double z) {
        return getNoiseDerivativeRaw(seed + salt, x * frequency, y * frequency, z * frequency);
    }

    public abstract double[] getNoiseDerivativeRaw(long seed, double x, double y);

    public abstract double[] getNoiseDerivativeRaw(long seed, double x, double y, double z);
}
