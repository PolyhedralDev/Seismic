package com.dfsek.seismic.algorithms.sampler;


import com.dfsek.seismic.algorithms.sampler.compiler.MaxS;
import com.dfsek.seismic.math.range.Range;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.Label;
import java.lang.constant.ClassDesc;
import java.util.Objects;


public class LinearHeightmapSampler implements Sampler {
    private final Sampler sampler;
    private final double scale;
    private final double base;
    private final Range samplerRange;

    public LinearHeightmapSampler(Sampler sampler, double scale, double base) {
        this.sampler = sampler;
        this.scale = scale;
        this.base = base;
        this.samplerRange = sampler.range().mul(scale);
    }


    @Override
    public double getSample(long seed, double x, double y) {
        return getSample(seed, x, 0, y);
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        double primed = -y + base;
        if(primed > samplerRange.max()) return samplerRange.max();
        if(primed < samplerRange.min()) return samplerRange.min();
        return primed + sampler.getSample(seed, x, y, z) * scale;
    }

    @Override
    public Range range() {
        return Range.infinity();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof LinearHeightmapSampler that)) return false;
        return Double.compare(scale, that.scale) == 0 && Double.compare(base, that.base) == 0 && Objects.equals(sampler, that.sampler);
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int ySlot, int zSlot, MaxS max) {
        Label minL = b.newLabel();
        Label maxL = b.newLabel();
        Label endL = b.newLabel();

        return b.loadConstant(base)
            .dload(ySlot)
            .dsub()
            .dup2()
            .dup2()
            .loadConstant(samplerRange.max())
            .dcmpg()
            .iconst_1()
            .if_icmpeq(maxL)
            .loadConstant(0D)
            .goto_(endL)
            .labelBinding(maxL)
            .loadConstant(samplerRange.max())
            .labelBinding(endL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sampler, scale, base);
    }
}
