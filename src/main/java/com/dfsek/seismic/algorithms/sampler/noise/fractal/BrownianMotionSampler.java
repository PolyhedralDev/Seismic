/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.fractal;

import com.dfsek.seismic.math.numericanalysis.interpolation.InterpolationFunctions;
import com.dfsek.seismic.type.sampler.DerivativeSampler;
import com.dfsek.seismic.type.sampler.Sampler;


public class BrownianMotionSampler extends FractalNoiseFunction {
    public BrownianMotionSampler(Sampler input) {
        super(input);
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        double sum = 0;
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double noise = input.getSample(seed++, x, y);
            sum += noise * amp;
            amp *= InterpolationFunctions.lerp(1.0, Math.min(noise + 1, 2) * 0.5, weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            amp *= gain;
        }

        return sum;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y, double z) {
        double sum = 0;
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double noise = input.getSample(seed++, x, y, z);
            sum += noise * amp;
            amp *= InterpolationFunctions.lerp(1.0, (noise + 1) * 0.5, weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            amp *= gain;
        }

        return sum;
    }

    @Override
    public boolean isDifferentiable() {
        return DerivativeSampler.isDifferentiable(input);
    }

    @Override
    public double[] getNoiseDerivativeRaw(long seed, double x, double y) {
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

            amp *= InterpolationFunctions.lerp(1.0, Math.min(noise[0] + 1, 2) * 0.5, weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            amp *= gain;
        }

        return sum;
    }

    @Override
    public double[] getNoiseDerivativeRaw(long seed, double x, double y, double z) {
        double[] sum = { 0, 0, 0, 0 };
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double[] noise = ((DerivativeSampler) input).getSampleDerivative(seed++, x, y, z);
            sum[0] += noise[0] * amp;

            // See comment in 2D version
            sum[1] += noise[1] * amp;
            sum[2] += noise[2] * amp;
            sum[3] += noise[3] * amp;

            amp *= InterpolationFunctions.lerp(1.0, (noise[0] + 1) * 0.5, weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            amp *= gain;
        }

        return sum;
    }
}
