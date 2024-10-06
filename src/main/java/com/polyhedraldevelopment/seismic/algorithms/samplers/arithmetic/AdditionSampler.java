package com.polyhedraldevelopment.seismic.algorithms.samplers.arithmetic;

import com.polyhedraldevelopment.seismic.algorithms.samplers.NoiseSampler;


public class AdditionSampler extends BinaryArithmeticSampler {
    public AdditionSampler(NoiseSampler left, NoiseSampler right) {
        super(left, right);
    }

    @Override
    public double operate(double left, double right) {
        return left + right;
    }
}
