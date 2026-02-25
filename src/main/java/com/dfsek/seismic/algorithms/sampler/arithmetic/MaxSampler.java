package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.math.range.Range;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.Opcode;
import java.lang.classfile.instruction.InvokeInstruction;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.CD_Double;
import static java.lang.constant.ConstantDescs.CD_double;


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

    @Override
    public CodeBuilder operator(CodeBuilder b) {
        return b.invokestatic(ClassDesc.of("java.lang.Math"), "max", MethodTypeDesc.of(CD_double, CD_double, CD_double));
    }
    @Override
    public Range range() {
        return left.range().max(right.range());
    }
}
