package com.dfsek.seismic.function;


import com.dfsek.seismic.NoiseSampler;

/**
 * Abstract class containing boilerplate that standard noise functions require.
 */
public abstract class NoiseFunction implements NoiseSampler {
    protected double frequency = 0.02d;
    protected int seed;

    /**
     * Instantiate a noise function with the given seed.
     *
     * @param seed Seed to use.
     */
    public NoiseFunction(int seed) {
        this.seed = seed;
    }

    /**
     * Instantiate a noise function with the default seed.
     */
    public NoiseFunction() {
        this.seed = 2403;
    }

    /**
     * Set the seed of this noise function.
     *
     * @param seed Seed to use.
     */
    public void setSeed(int seed) {
        this.seed = seed;
    }

    /**
     * Get the frequency of this noise function.
     *
     * @return Frequency.
     */
    public double getFrequency() {
        return frequency;
    }

    /**
     * Set the frequency of this noise function.
     *
     * @param frequency Frequency to use.
     */
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    @Override
    public double getNoise(double x, double y) {
        return getNoiseSeeded(seed, x, y);
    }

    @Override
    public double getNoise(double x, double y, double z) {
        return getNoiseSeeded(seed, x, y, z);
    }

    @Override
    public double getNoiseSeeded(int seed, double x, double y) {
        return getNoiseRaw(seed, x * frequency, y * frequency);
    }

    @Override
    public double getNoiseSeeded(int seed, double x, double y, double z) {
        return getNoiseRaw(seed, x * frequency, y * frequency, z * frequency);
    }

    protected abstract double getNoiseRaw(int seed, double x, double y);

    protected abstract double getNoiseRaw(int seed, double x, double y, double z);
}
