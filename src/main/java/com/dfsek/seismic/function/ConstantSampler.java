package com.dfsek.seismic.function;

/**
 * NoiseSampler implementation that returns a constant.
 */
public class ConstantSampler extends NoiseFunction {
    private final double constant;

    /**
     * Instantiate a ConstantSampler with a constant value.
     *
     * @param constant Constant value.
     */
    public ConstantSampler(double constant) {
        super(0);
        this.constant = constant;
    }

    /**
     * Instantiate a ConstantSampler with a value of 0.
     */
    public ConstantSampler() {
        super(0);
        this.constant = 0;
    }

    @Override
    public double getNoiseRaw(int seed, double x, double y) {
        return constant;
    }

    @Override
    public double getNoiseRaw(int seed, double x, double y, double z) {
        return constant;
    }
}
