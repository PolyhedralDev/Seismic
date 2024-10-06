package com.polyhedraldevelopment.seismic.algorithms.samplers.arithmetic;

import com.polyhedraldevelopment.seismic.algorithms.samplers.NoiseSampler;


public class MinSampler extends BinaryArithmeticSampler {
    public MinSampler(NoiseSampler left, NoiseSampler right) {
        super(left, right);
    }

    @Override
    public double operate(double left, double right) {
        return Math.min(left, right);
    }
}
