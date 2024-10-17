/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.polyhedraldevelopment.seismic.algorithms.sampler.noise.fractal;

import com.polyhedraldevelopment.seismic.math.numericanalysis.interpolation.InterpolationFunctions;
import com.polyhedraldevelopment.seismic.type.sampler.Sampler;


public class PingPongSampler extends FractalNoiseFunction {
    private double pingPongStrength = 2.0;

    public PingPongSampler(Sampler input) {
        super(input);
    }


    private static double pingPong(double t) {
        t -= (int) (t * 0.5f) << 1;
        return t < 1 ? t : 2 - t;
    }

    public void setPingPongStrength(double strength) {
        this.pingPongStrength = strength;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        double sum = 0;
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double noise = PingPongSampler.pingPong((input.getSample(seed++, x, y) + 1) * pingPongStrength);
            sum += (noise - 0.5) * 2 * amp;
            amp *= InterpolationFunctions.lerp(weightedStrength, 1.0, noise);

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
            double noise = PingPongSampler.pingPong((input.getSample(seed++, x, y, z) + 1) * pingPongStrength);
            sum += (noise - 0.5) * 2 * amp;
            amp *= InterpolationFunctions.lerp(weightedStrength, 1.0, noise);

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            amp *= gain;
        }

        return sum;
    }
}
