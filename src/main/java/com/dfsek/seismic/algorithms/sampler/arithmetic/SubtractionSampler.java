package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.type.sampler.Sampler;


public class SubtractionSampler extends BinaryArithmeticSampler {
    public SubtractionSampler(Sampler left, Sampler right) {
        super(left, right);
    }

    @Override
    public double operate(double left, double right) {
        return left - right;
    }

    @Override
    public double[] operateDerivative(double[] left, double[] right) {
        int dimensions = left.length;
        double[] out = new double[dimensions];
        for(int i = 0; i < dimensions; i++) {
            out[i] = left[i] - right[i];
        }
        return out;
    }
}
