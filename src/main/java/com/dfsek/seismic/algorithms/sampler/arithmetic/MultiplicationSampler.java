package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.algorithms.sampler.noise.ConstantSampler;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.Opcode;
import java.lang.classfile.instruction.OperatorInstruction;


public class MultiplicationSampler extends BinaryArithmeticSampler {
    private MultiplicationSampler(Sampler left, Sampler right) {
        super(left, right);
    }

    public static Sampler of(Sampler left, Sampler right) {
        if(left instanceof ConstantSampler c && FloatingPointFunctions.equals(c.constant(), 1)) return right;
        if(right instanceof ConstantSampler c && FloatingPointFunctions.equals(c.constant(), 1)) return left;
        if(left instanceof ConstantSampler c && FloatingPointFunctions.equals(c.constant(), 0)) return Sampler.zero();
        if(right instanceof ConstantSampler c && FloatingPointFunctions.equals(c.constant(), 0)) return Sampler.zero();
        return new MultiplicationSampler(left, right);
    }

    @Override
    public double operate(double left, double right) {
        return left * right;
    }

    @Override
    public double[] operateDerivative(double[] left, double[] right) {
        int dimensions = left.length;
        double[] out = new double[dimensions];
        out[0] = left[0] * right[0];
        for(int i = 1; i < dimensions; i++) {
            out[i] = left[i] * right[0] + left[0] * right[i];
        }
        return out;
    }

    @Override
    public CodeBuilder operator(CodeBuilder b) {
        return b.dmul();
    }
}
