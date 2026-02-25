package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.algorithms.sampler.compiler.MaxS;
import com.dfsek.seismic.algorithms.sampler.compiler.Node;
import com.dfsek.seismic.type.sampler.DerivativeSampler;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.util.Objects;
import java.util.stream.Stream;


public abstract class BinaryArithmeticSampler implements DerivativeSampler {
    protected final Sampler left;
    protected final Sampler right;

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

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int zSlot, MaxS max) {
        left.build(b, clazz, seedSlot, xSlot, zSlot, max);
        right.build(b, clazz, seedSlot, xSlot, zSlot, max);
        return operator(b);
    }

    @Override
    public Stream<Node> flatten() {
        return Stream.of(left, right, this);
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int ySlot, int zSlot, MaxS max) {
        left.build(b, clazz, seedSlot, xSlot, ySlot, zSlot, max);
        right.build(b, clazz, seedSlot, xSlot, ySlot, zSlot, max);
        return operator(b);
    }

    @Override
    public int lvSize2() {
        return 0;
    }

    @Override
    public int lvSize3() {
        return 0;
    }

    public abstract CodeBuilder operator(CodeBuilder b);

    @Override
    public int hashCode() {
        return Objects.hash(left, right, getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BinaryArithmeticSampler that) return this.left.equals(that.left) && this.right.equals(that.right) &&
                                                               this.getClass().equals(that.getClass());
        return false;
    }
}
