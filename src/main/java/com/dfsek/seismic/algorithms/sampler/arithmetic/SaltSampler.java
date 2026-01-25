package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.util.Objects;


public class SaltSampler implements Sampler {
    private final long salt;
    private final Sampler in;

    private SaltSampler(long salt, Sampler in) {
        this.salt = salt;
        this.in = in;
    }

    public static Sampler salt(long salt, Sampler in) {
        if(salt == 0) return in;
        if(in instanceof SaltSampler s) return new SaltSampler(s.salt + salt, s.in);
        return new SaltSampler(salt, in);
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return in.getSample(seed + salt, x, y);
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return in.getSample(seed + salt, x, y, z);
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int zSlot, int max) {
        return in.build(b.lload(seedSlot)
            .loadConstant(salt)
            .ladd()
            .lstore(max), clazz, max, xSlot, zSlot, max + 2);
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int ySlot, int zSlot, int max) {
        return in.build(b.lload(seedSlot)
            .loadConstant(salt)
            .ladd()
            .lstore(max), clazz, max, xSlot, ySlot, zSlot, max + 2);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof SaltSampler that)) return false;
        return salt == that.salt && Objects.equals(in, that.in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salt, in);
    }
}
