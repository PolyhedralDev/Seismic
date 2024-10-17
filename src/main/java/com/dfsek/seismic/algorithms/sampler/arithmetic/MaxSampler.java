package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.type.sampler.Sampler;


public class MaxSampler extends BinaryArithmeticSampler {
    public MaxSampler(Sampler left, Sampler right) {
        super(left, right);
    }

    @Override
    public double operate(double left, double right) {
        return Math.max(left, right);
    }

    @Override
    public double[] operateDerivative(double[] left, double[] right) {
        double leftValue = left[0];
        double rightValue = right[0];
        return leftValue > rightValue ? left : right;
    }
}
