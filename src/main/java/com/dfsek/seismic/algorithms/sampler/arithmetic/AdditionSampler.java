package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.algorithms.sampler.noise.ConstantSampler;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.math.range.Range;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;


public class AdditionSampler extends BinaryArithmeticSampler {
    private AdditionSampler(Sampler left, Sampler right) {
        super(left, right);
    }

    public static Sampler of(Sampler left, Sampler right) {
        if(left instanceof ConstantSampler c && FloatingPointFunctions.equals(c.constant(), 0)) return right;
        if(right instanceof ConstantSampler c && FloatingPointFunctions.equals(c.constant(), 0)) return left;
        if(left.equals(right)) return MultiplicationSampler.of(left, new ConstantSampler(2));
        return new AdditionSampler(left, right);
    }

    @Override
    public double operate(double left, double right) {
        return left + right;
    }

    @Override
    public double[] operateDerivative(double[] left, double[] right) {
        int dimensions = left.length;
        double[] out = new double[dimensions];
        for(int i = 0; i < dimensions; i++) {
            out[i] = left[i] + right[i];
        }
        return out;
    }

    @Override
    public CodeBuilder operator(CodeBuilder b) {
        return b.dadd();
    }

    @Override
    public Range range() {
        return left.range().add(right.range());
    }
}
