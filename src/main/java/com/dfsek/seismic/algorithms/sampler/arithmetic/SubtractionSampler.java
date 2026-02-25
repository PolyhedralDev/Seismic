package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.math.range.Range;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.Opcode;
import java.lang.classfile.instruction.OperatorInstruction;


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

    @Override
    public CodeBuilder operator(CodeBuilder b) {
        return b.dsub();
    }
    @Override
    public Range range() {
        return left.range().sub(right.range());
    }
}
