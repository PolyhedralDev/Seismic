package com.dfsek.seismic.mutator.fractal;


import com.dfsek.seismic.NoiseSampler;

import static com.dfsek.seismic.util.MathHelper.fastAbs;
import static com.dfsek.seismic.util.MathHelper.lerp;

public class RidgedFractalSampler extends FractalNoiseFunction {

    public RidgedFractalSampler(int seed, NoiseSampler input) {
        super(seed, input);
    }

    @Override
    public double getNoiseRaw(int seed, double x, double y) {
        double sum = 0;
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double noise = fastAbs(input.getNoiseSeeded(seed++, x, y));
            sum += (noise * -2 + 1) * amp;
            amp *= lerp(1.0, 1 - noise, weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            amp *= gain;
        }

        return sum;
    }

    @Override
    public double getNoiseRaw(int seed, double x, double y, double z) {
        double sum = 0;
        double amp = fractalBounding;

        for(int i = 0; i < octaves; i++) {
            double noise = fastAbs(input.getNoiseSeeded(seed++, x, y, z));
            sum += (noise * -2 + 1) * amp;
            amp *= lerp(1.0, 1 - noise, weightedStrength);

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            amp *= gain;
        }

        return sum;
    }
}
