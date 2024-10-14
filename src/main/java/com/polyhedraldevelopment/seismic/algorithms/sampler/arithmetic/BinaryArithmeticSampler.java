package com.polyhedraldevelopment.seismic.algorithms.sampler.arithmetic;

import com.polyhedraldevelopment.seismic.algorithms.sampler.DerivativeSampler;
import com.polyhedraldevelopment.seismic.algorithms.sampler.Sampler;


public abstract class BinaryArithmeticSampler implements DerivativeSampler {
    private final Sampler left;
    private final Sampler right;

    protected BinaryArithmeticSampler(Sampler left, Sampler right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public boolean isDifferentiable() {
        return DerivativeSampler.isDifferentiable(left) && DerivativeSampler.isDifferentiable(right);
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return operate(left.getSample(seed, x, y), right.getSample(seed, x, y));
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return operate(left.getSample(seed, x, y, z), right.getSample(seed, x, y, z));
    }


    @Override
    public double[] getSampleDerivative(long seed, double x, double y) {
        return operateDerivative(((DerivativeSampler) left).getSampleDerivative(seed, x, y),
            ((DerivativeSampler) right).getSampleDerivative(seed, x, y));
    }

    @Override
    public double[] getSampleDerivative(long seed, double x, double y, double z) {
        return operateDerivative(((DerivativeSampler) left).getSampleDerivative(seed, x, y, z),
            ((DerivativeSampler) right).getSampleDerivative(seed, x, y, z));
    }

    public abstract double operate(double left, double right);

    public abstract double[] operateDerivative(double[] left, double[] right);
}
