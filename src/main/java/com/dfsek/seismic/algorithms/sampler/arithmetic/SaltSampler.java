package com.dfsek.seismic.algorithms.sampler.arithmetic;

import com.dfsek.seismic.algorithms.sampler.compiler.MaxS;
import com.dfsek.seismic.math.range.Range;
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
        if(in instanceof SaltSampler s) return salt(s.salt + salt, s.in);
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
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int zSlot, MaxS max) {
        return in.build(b.lload(seedSlot)
            .loadConstant(salt)
            .ladd()
            .lstore(max.max()), clazz, max.max(), xSlot, zSlot, max.store2());
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int ySlot, int zSlot, MaxS max) {
        return in.build(b.lload(seedSlot)
            .loadConstant(salt)
            .ladd()
            .lstore(max.max()), clazz, max.max(), xSlot, ySlot, zSlot, max.store2());
    }

    @Override
    public int lvSize2() {
        return 2;
    }

    @Override
    public int lvSize3() {
        return 2;
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

    @Override
    public Range range() {
        return in.range();
    }
}
