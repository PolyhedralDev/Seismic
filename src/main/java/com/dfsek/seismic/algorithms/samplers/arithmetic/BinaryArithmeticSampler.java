package com.dfsek.seismic.algorithms.samplers.arithmetic;

import com.dfsek.seismic.algorithms.samplers.NoiseSampler;


public abstract class BinaryArithmeticSampler implements NoiseSampler {
    private final NoiseSampler left;
    private final NoiseSampler right;

    protected BinaryArithmeticSampler(NoiseSampler left, NoiseSampler right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double getNoise(long seed, double x, double y) {
        return operate(left.getNoise(seed, x, y), right.getNoise(seed, x, y));
    }

    @Override
    public double getNoise(long seed, double x, double y, double z) {
        return operate(left.getNoise(seed, x, y, z), right.getNoise(seed, x, y, z));
    }

    public abstract double operate(double left, double right);
}
