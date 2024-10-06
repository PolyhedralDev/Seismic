/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.samplers.noise.fractal;

import com.dfsek.seismic.algorithms.samplers.NoiseSampler;
import com.dfsek.seismic.math.numericanalysis.interpolation.InterpolationFunctions;


public class BrownianMotionSampler extends FractalNoiseFunction {
    public BrownianMotionSampler(NoiseSampler input) {
        super(input);
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        double sum = 0;
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double noise = input.getNoise(seed++, x, y);
            sum += noise * amp;
            amp *= InterpolationFunctions.lerp(weightedStrength, 1.0, Math.min(noise + 1, 2) * 0.5);

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
            double noise = input.getNoise(seed++, x, y, z);
            sum += noise * amp;
            amp *= InterpolationFunctions.lerp(weightedStrength, 1.0, (noise + 1) * 0.5);

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            amp *= gain;
        }

        return sum;
    }
}
