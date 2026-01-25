/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.fractal;

import com.dfsek.seismic.algorithms.sampler.arithmetic.FrequencySampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.SaltSampler;
import com.dfsek.seismic.algorithms.sampler.compiler.MaxS;
import com.dfsek.seismic.algorithms.sampler.noise.ConstantSampler;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.type.sampler.DerivativeSampler;
import com.dfsek.seismic.type.sampler.Sampler;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.util.Objects;


public class BrownianMotionSampler extends FractalNoiseFunction {
    private final Sampler built;

    public static Sampler of(Sampler input, double gain, double lacunarity, double weightedStrength, int octaves) {
        if(octaves == 0) return Sampler.zero();
        if(octaves == 1) return input;
        if(FloatingPointFunctions.equals(weightedStrength, 0)) return new BrownianMotionSampler(input, gain, lacunarity, octaves);
        return new WeightedBrownianMotionSampler(input, gain, lacunarity, weightedStrength, octaves);
    }

    private BrownianMotionSampler(Sampler input, double gain, double lacunarity, int octaves) {
        super(input, gain, lacunarity, octaves);

        /*
         *         double sum = 0;
         *         double amp = fractalBounding;
         *
         *         for(int i = 0; i < octaves; i++) {
         *             double noise = input.getSample(seed++, x, y, z);
         *             sum += noise * amp;
         *
         *             x *= lacunarity;
         *             y *= lacunarity;
         *             z *= lacunarity;
         *             amp *= gain;
         *         }
         *
         *         return sum;
         */

        Sampler built = Sampler.zero();

        double amp = fractalBounding;
        double freq = 1.0;
        for(int i = 0; i < octaves; i++) {
            built = built.plus(SaltSampler.salt(i, FrequencySampler.frequency(freq, input).mul(new ConstantSampler(amp))));
            amp *= gain;
            freq *= lacunarity;
        }
        this.built = built;
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return built.getSample(seed, x, y);
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return built.getSample(seed, x, y, z);
    }

    @Override
    public boolean isDifferentiable() {
        return DerivativeSampler.isDifferentiable(input);
    }

    @Override
    public double[] getSampleDerivative(long seed, double x, double y) {
        double[] sum = { 0, 0, 0 };
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            // This should only be called after `input` is verified as a `DerivativeNoiseSampler`
            // so this should be a safe cast
            double[] noise = ((DerivativeSampler) input).getSampleDerivative(seed++, x, y);
            sum[0] += noise[0] * amp;

            // Directional derivative of each octave can be subject to the same addition and product
            // as per derivative sum and product rules in order to produce the correct final derivative
            sum[1] += noise[1] * amp;
            sum[2] += noise[2] * amp;

            x *= lacunarity;
            y *= lacunarity;
            amp *= gain;
        }

        return sum;
    }

    @Override
    public double[] getSampleDerivative(long seed, double x, double y, double z) {
        double[] sum = { 0, 0, 0, 0 };
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double[] noise = ((DerivativeSampler) input).getSampleDerivative(seed++, x, y, z);
            sum[0] += noise[0] * amp;

            // See comment in 2D version
            sum[1] += noise[1] * amp;
            sum[2] += noise[2] * amp;
            sum[3] += noise[3] * amp;

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            amp *= gain;
        }

        return sum;
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int zSlot, MaxS max) {
        return built.build(b, clazz, seedSlot, xSlot, zSlot, max);
    }

    @Override
    public CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int ySlot, int zSlot, MaxS max) {
        return built.build(b, clazz, seedSlot, xSlot, ySlot, zSlot, max);
    }

    @Override
    public int lvSize2() {
        return 0;
    }

    @Override
    public int lvSize3() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof BrownianMotionSampler that)) return false;
        if(!super.equals(o)) return false;
        return Objects.equals(built, that.built);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), built);
    }
}
