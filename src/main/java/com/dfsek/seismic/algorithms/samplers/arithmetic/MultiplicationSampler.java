package com.dfsek.seismic.algorithms.samplers.arithmetic;

import  com.dfsek.seismic.algorithms.samplers.NoiseSampler;


public class MultiplicationSampler extends BinaryArithmeticSampler {
    public MultiplicationSampler(NoiseSampler left, NoiseSampler right) {
        super(left, right);
    }

    @Override
    public double operate(double left, double right) {
        return left * right;
    }
}
