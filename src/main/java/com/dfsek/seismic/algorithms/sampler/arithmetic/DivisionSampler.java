package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.type.sampler.Sampler;


public class DivisionSampler extends BinaryArithmeticSampler {
    public DivisionSampler(Sampler left, Sampler right) {
        super(left, right);
    }

    @Override
    public double operate(double left, double right) {
        return left / right;
    }

    @Override
    public double[] operateDerivative(double[] left, double[] right) {
        int dimensions = left.length;
        double[] out = new double[dimensions];
        out[0] = left[0] / right[0];
        double r2 = right[0] * right[0];
        for(int i = 1; i < dimensions; i++) {
            out[i] = (left[i] * right[0] - left[0] * right[i]) / (r2);
        }
        return out;
    }
}
