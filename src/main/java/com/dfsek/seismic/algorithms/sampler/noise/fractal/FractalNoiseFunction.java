/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.fractal;

import com.dfsek.seismic.math.range.Range;
import com.dfsek.seismic.type.sampler.DerivativeSampler;
import com.dfsek.seismic.type.sampler.Sampler;

import java.util.Objects;


public abstract class FractalNoiseFunction implements DerivativeSampler {
    protected final Sampler input;
    protected final double fractalBounding;
    protected final int octaves;
    protected final double gain;
    protected final double lacunarity;

    public FractalNoiseFunction(Sampler input, double gain, double lacunarity, int octaves) {
        this.input = input;
        this.gain = gain;
        this.lacunarity = lacunarity;
        this.octaves = octaves;

        double gainAbs = Math.abs(this.gain);
        double amp = gainAbs;
        double ampFractal = 1.0;
        for(int i = 1; i < octaves; i++) {
            ampFractal += amp;
            amp *= gainAbs;
        }
        fractalBounding = 1 / ampFractal;
    }

    @Override
    public boolean isDifferentiable() {
        return false;
    }

    @Override
    public double[] getSampleDerivative(long seed, double x, double y) {
        throw new UnsupportedOperationException("Implementation failed to check or set isDifferentiable correctly");
    }

    @Override
    public double[] getSampleDerivative(long seed, double x, double y, double z) {
        throw new UnsupportedOperationException("Implementation failed to check or set isDifferentiable correctly");
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof FractalNoiseFunction that)) return false;
        return Double.compare(fractalBounding, that.fractalBounding) == 0 && octaves == that.octaves && Double.compare(gain, that.gain) ==
                                                                                                        0 && Double.compare(lacunarity,
            that.lacunarity) == 0 && Objects.equals(input, that.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, fractalBounding, octaves, gain, lacunarity);
    }

    // fractal bounding ensures noise remains in [0,1]
    @Override
    public Range range() {
        return Range.one();
    }
}
