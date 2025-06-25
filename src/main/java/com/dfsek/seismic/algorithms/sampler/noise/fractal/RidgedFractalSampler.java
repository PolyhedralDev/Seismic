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


public class RidgedFractalSampler extends FractalNoiseFunction {

    public RidgedFractalSampler(Sampler input) {
        super(input);
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        double sum = 0;
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double noise = Math.abs(input.getSample(seed++, x, y));
            sum += (noise * -2 + 1) * amp;
            amp *= InterpolationFunctions.lerp(1.0, 1 - noise, weightedStrength);

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
            double noise = Math.abs(input.getSample(seed++, x, y, z));
            sum += (noise * -2 + 1) * amp;
            amp *= InterpolationFunctions.lerp(1.0, 1 - noise, weightedStrength);

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
        double[] sum = {0, 0, 0};
        double amp = fractalBounding;
        double negative2Amp = amp * -2;

        for(int i = 0; i < octaves; i++) {
            // This should only be called after `input` is verified as a `DerivativeNoiseSampler`
            // so this should be a safe cast
            double[] noise = ((DerivativeSampler) input).getSampleDerivative(seed++, x, y);
            noise[0] = Math.abs(noise[0]);
            noise[1] = Math.abs(noise[1]);
            noise[2] = Math.abs(noise[2]);

            sum[0] += (noise[0] * -2 + 1) * amp;

            // Directional derivative of each octave can be subject to the same addition and product
            // as per derivative sum and product rules in order to produce the correct final derivative
            sum[1] += noise[1] * negative2Amp;
            sum[2] += noise[2] * negative2Amp;

            amp *= InterpolationFunctions.lerp(1.0, 1 - noise[0], weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            amp *= gain;
            negative2Amp = amp * -2;
        }

        return sum;
    }

    @Override
    public double[] getNoiseDerivativeRaw(long seed, double x, double y, double z) {
        double[] sum = {0, 0, 0, 0};
        double amp = fractalBounding;
        double negative2Amp = amp * -2;

        for(int i = 0; i < octaves; i++) {
            double[] noise = ((DerivativeSampler) input).getSampleDerivative(seed++, x, y, z);
            noise[0] = Math.abs(noise[0]);
            noise[1] = Math.abs(noise[1]);
            noise[2] = Math.abs(noise[2]);
            noise[3] = Math.abs(noise[3]);

            sum[0] += (noise[0] * -2 + 1) * amp;

            // See comment in 2D version
            sum[1] += noise[1] * negative2Amp;
            sum[2] += noise[2] * negative2Amp;
            sum[3] += noise[3] * negative2Amp;

            amp *= InterpolationFunctions.lerp(1.0, 1 - noise[0], weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            amp *= gain;
            negative2Amp = amp * -2;
        }

        return sum;
    }
}
