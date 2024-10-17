package com.polyhedraldevelopment.seismic.algorithms.sampler.arithmetic;

import com.polyhedraldevelopment.seismic.type.sampler.Sampler;


public class MinSampler extends BinaryArithmeticSampler {
    public MinSampler(Sampler left, Sampler right) {
        super(left, right);
    }

    @Override
    public double operate(double left, double right) {
        return Math.min(left, right);
    }

    @Override
    public double[] operateDerivative(double[] left, double[] right) {
        double leftValue = left[0];
        double rightValue = right[0];
        return leftValue < rightValue ? left : right;
    }
}
