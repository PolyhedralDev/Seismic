package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.util.Objects;


public class FrequencySampler implements Sampler {
    private final double frequencyX;
    private final double frequencyY;
    private final double frequencyZ;
    private final Sampler in;

    private FrequencySampler(double frequencyX, double frequencyY, double frequencyZ, Sampler in) {
        this.frequencyX = frequencyX;
        this.frequencyY = frequencyY;
        this.frequencyZ = frequencyZ;
        this.in = in;
    }

    public static Sampler frequency(double frequencyX, double frequencyY, double frequencyZ, Sampler in) {
        if(FloatingPointFunctions.equals(frequencyX, 1) && FloatingPointFunctions.equals(frequencyY, 1) && FloatingPointFunctions.equals(
            frequencyZ, 1)) return in;
        return new FrequencySampler(frequencyX, frequencyY, frequencyZ, in);
    }

    public static Sampler frequency(double frequency, Sampler in) {
        if(FloatingPointFunctions.equals(frequency, 1)) return in;
        if(FloatingPointFunctions.equals(frequency, 0)) return Sampler.zero();
        return new FrequencySampler(frequency, in);
    }

    private FrequencySampler(double frequency, Sampler in) {
        this(frequency, frequency, frequency, in);
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return in.getSample(seed, x * frequencyX, y * frequencyY);
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return in.getSample(seed, x * frequencyX, y * frequencyY, z * frequencyZ);
    }

    @Override
    public Sampler frequency(double frequency) {
        double newX = frequencyX * frequency;
        double newY = frequencyY * frequency;
        double newZ = frequencyZ * frequency;
        if(FloatingPointFunctions.equals(newX, 1) && FloatingPointFunctions.equals(newY, 1) && FloatingPointFunctions.equals(newZ, 1))
            return in;
        return new FrequencySampler(newX, newY, newZ, in);
    }

    @Override
    public Sampler frequency(double frequencyX, double frequencyY, double frequencyZ) {
        double newX = frequencyX * this.frequencyX;
        double newY = frequencyY * this.frequencyY;
        double newZ = frequencyZ * this.frequencyZ;
        if(FloatingPointFunctions.equals(newX, 1) && FloatingPointFunctions.equals(newY, 1) && FloatingPointFunctions.equals(newZ, 1))
            return in;
        return new FrequencySampler(newX, newY, newZ, in);
    }

    @Override
    public double frequencyX() {
        return frequencyX;
    }

    @Override
    public double frequencyZ() {
        return frequencyY;
    }

    @Override
    public double frequencyY() {
        return frequencyZ;
    }

    @Override
    public CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int zSlot, int max) {
        int xSlot_ = max;
        max += 2;
        int zSlot_ = max;
        max += 2;
        if(FloatingPointFunctions.equals(frequencyX, frequencyZ)) {
            return in.build(b
                .dload(xSlot)
                .loadConstant(frequencyX)
                .dmul()
                .dstore(xSlot_)
                .dload(zSlot)
                .loadConstant(frequencyZ)
                .dmul()
                .dstore(zSlot_), seedSlot, xSlot_, zSlot_, max);
        } else {
            return in.build(b
                .dload(xSlot)
                .loadConstant(frequencyX)
                .dup2()
                .dmul()
                .dstore(xSlot_)
                .loadConstant(frequencyZ)
                .dmul()
                .dstore(zSlot_), seedSlot, xSlot_, zSlot_, max);
        }
    }

    @Override
    public CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int ySlot, int zSlot, int max) {
        int xSlot_ = max;
        max += 2;
        int ySlot_ = max;
        max += 2;
        int zSlot_ = max;
        max += 2;
        if(FloatingPointFunctions.equals(frequencyX, frequencyZ) && FloatingPointFunctions.equals(frequencyY, frequencyZ)) {
            return in.build(b
                .dload(xSlot)
                .loadConstant(frequencyX)
                .dmul()
                .dstore(xSlot_)
                .dload(ySlot)
                .loadConstant(frequencyY)
                .dmul()
                .dstore(ySlot_)
                .dload(zSlot)
                .loadConstant(frequencyZ)
                .dmul()
                .dstore(zSlot_), seedSlot, xSlot_, ySlot_, zSlot_, max);
        } else {
            return in.build(b
                .dload(xSlot)
                .loadConstant(frequencyX)
                .dup2()
                .dup2()
                .dmul()
                .dstore(xSlot_)
                .loadConstant(frequencyY)
                .dmul()
                .dstore(ySlot_)
                .loadConstant(frequencyZ)
                .dmul()
                .dstore(zSlot_), seedSlot, xSlot_, ySlot_, zSlot_, max);
        }
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof FrequencySampler that)) return false;
        return Double.compare(frequencyX, that.frequencyX) == 0 && Double.compare(frequencyY, that.frequencyY) == 0 && Double.compare(
            frequencyZ, that.frequencyZ) == 0 && Objects.equals(in, that.in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frequencyX, frequencyY, frequencyZ, in);
    }
}
