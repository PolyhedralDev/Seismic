/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.fractal;

import com.dfsek.seismic.algorithms.sampler.noise.DerivativeNoiseFunction;
import com.dfsek.seismic.type.sampler.Sampler;


public abstract class FractalNoiseFunction extends DerivativeNoiseFunction {
    protected final Sampler input;
    protected final double fractalBounding;
    protected final int octaves;
    protected final double gain;
    protected final double lacunarity;
    protected final double weightedStrength;

    public FractalNoiseFunction(long salt, Sampler input, double gain, double lacunarity, double weightedStrength, int octaves) {
        super(1, salt);
        this.input = input;
        this.gain = gain;
        this.lacunarity = lacunarity;
        this.weightedStrength = weightedStrength;
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
    public double[] getNoiseDerivativeRaw(long seed, double x, double y) {
        throw new UnsupportedOperationException("Implementation failed to check or set isDifferentiable correctly");
    }

    @Override
    public double[] getNoiseDerivativeRaw(long seed, double x, double y, double z) {
        throw new UnsupportedOperationException("Implementation failed to check or set isDifferentiable correctly");
    }
}
