/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.value;


import com.dfsek.seismic.algorithms.sampler.noise.NoiseFunction;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.math.numericanalysis.interpolation.InterpolationFunctions;


public class ValueCubicSampler extends ValueStyleNoise {
    public ValueCubicSampler(double frequency, long salt) {
        super(frequency, salt);
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y) {
        int seed = (int) sl;
        int x1 = FloatingPointFunctions.floor(x);
        int y1 = FloatingPointFunctions.floor(y);

        double xs = x - x1;
        double ys = y - y1;

        x1 *= NoiseFunction.PRIME_X;
        y1 *= NoiseFunction.PRIME_Y;
        int x0 = x1 - NoiseFunction.PRIME_X;
        int y0 = y1 - NoiseFunction.PRIME_Y;
        int x2 = x1 + NoiseFunction.PRIME_X;
        int y2 = y1 + NoiseFunction.PRIME_Y;
        int x3 = x1 + (NoiseFunction.PRIME_X << 1);
        int y3 = y1 + (NoiseFunction.PRIME_Y << 1);

        return InterpolationFunctions.biCubicLerp(
            ValueStyleNoise.valCoord(seed, x0, y0), ValueStyleNoise.valCoord(seed, x1, y0), ValueStyleNoise.valCoord(seed, x2, y0),
            ValueStyleNoise.valCoord(seed, x3, y0),
            ValueStyleNoise.valCoord(seed, x0, y1), ValueStyleNoise.valCoord(seed, x1, y1), ValueStyleNoise.valCoord(seed, x2, y1),
            ValueStyleNoise.valCoord(seed, x3, y1),
            ValueStyleNoise.valCoord(seed, x0, y2), ValueStyleNoise.valCoord(seed, x1, y2), ValueStyleNoise.valCoord(seed, x2, y2),
            ValueStyleNoise.valCoord(seed, x3, y2),
            ValueStyleNoise.valCoord(seed, x0, y3), ValueStyleNoise.valCoord(seed, x1, y3), ValueStyleNoise.valCoord(seed, x2, y3),
            ValueStyleNoise.valCoord(seed, x3, y3),
            xs, ys
        ) * (1 / (1.5 * 1.5));
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y, double z) {
        int seed = (int) sl;
        int x1 = FloatingPointFunctions.floor(x);
        int y1 = FloatingPointFunctions.floor(y);
        int z1 = FloatingPointFunctions.floor(z);

        double xs = x - x1;
        double ys = y - y1;
        double zs = z - z1;

        x1 *= NoiseFunction.PRIME_X;
        y1 *= NoiseFunction.PRIME_Y;
        z1 *= NoiseFunction.PRIME_Z;

        int x0 = x1 - NoiseFunction.PRIME_X;
        int y0 = y1 - NoiseFunction.PRIME_Y;
        int z0 = z1 - NoiseFunction.PRIME_Z;
        int x2 = x1 + NoiseFunction.PRIME_X;
        int y2 = y1 + NoiseFunction.PRIME_Y;
        int z2 = z1 + NoiseFunction.PRIME_Z;
        int x3 = x1 + (NoiseFunction.PRIME_X << 1);
        int y3 = y1 + (NoiseFunction.PRIME_Y << 1);
        int z3 = z1 + (NoiseFunction.PRIME_Z << 1);

        return InterpolationFunctions.triCubicLerp(
            // z0
            ValueStyleNoise.valCoord(seed, x0, y0, z0), ValueStyleNoise.valCoord(seed, x1, y0, z0),
            ValueStyleNoise.valCoord(seed, x2, y0, z0), ValueStyleNoise.valCoord(seed, x3, y0, z0),
            ValueStyleNoise.valCoord(seed, x0, y1, z0), ValueStyleNoise.valCoord(seed, x1, y1, z0),
            ValueStyleNoise.valCoord(seed, x2, y1, z0), ValueStyleNoise.valCoord(seed, x3, y1, z0),
            ValueStyleNoise.valCoord(seed, x0, y2, z0), ValueStyleNoise.valCoord(seed, x1, y2, z0),
            ValueStyleNoise.valCoord(seed, x2, y2, z0), ValueStyleNoise.valCoord(seed, x3, y2, z0),
            ValueStyleNoise.valCoord(seed, x0, y3, z0), ValueStyleNoise.valCoord(seed, x1, y3, z0),
            ValueStyleNoise.valCoord(seed, x2, y3, z0), ValueStyleNoise.valCoord(seed, x3, y3, z0),
            // z1
            ValueStyleNoise.valCoord(seed, x0, y0, z1), ValueStyleNoise.valCoord(seed, x1, y0, z1),
            ValueStyleNoise.valCoord(seed, x2, y0, z1), ValueStyleNoise.valCoord(seed, x3, y0, z1),
            ValueStyleNoise.valCoord(seed, x0, y1, z1), ValueStyleNoise.valCoord(seed, x1, y1, z1),
            ValueStyleNoise.valCoord(seed, x2, y1, z1), ValueStyleNoise.valCoord(seed, x3, y1, z1),
            ValueStyleNoise.valCoord(seed, x0, y2, z1), ValueStyleNoise.valCoord(seed, x1, y2, z1),
            ValueStyleNoise.valCoord(seed, x2, y2, z1), ValueStyleNoise.valCoord(seed, x3, y2, z1),
            ValueStyleNoise.valCoord(seed, x0, y3, z1), ValueStyleNoise.valCoord(seed, x1, y3, z1),
            ValueStyleNoise.valCoord(seed, x2, y3, z1), ValueStyleNoise.valCoord(seed, x3, y3, z1),
            // z2
            ValueStyleNoise.valCoord(seed, x0, y0, z2), ValueStyleNoise.valCoord(seed, x1, y0, z2),
            ValueStyleNoise.valCoord(seed, x2, y0, z2), ValueStyleNoise.valCoord(seed, x3, y0, z2),
            ValueStyleNoise.valCoord(seed, x0, y1, z2), ValueStyleNoise.valCoord(seed, x1, y1, z2),
            ValueStyleNoise.valCoord(seed, x2, y1, z2), ValueStyleNoise.valCoord(seed, x3, y1, z2),
            ValueStyleNoise.valCoord(seed, x0, y2, z2), ValueStyleNoise.valCoord(seed, x1, y2, z2),
            ValueStyleNoise.valCoord(seed, x2, y2, z2), ValueStyleNoise.valCoord(seed, x3, y2, z2),
            ValueStyleNoise.valCoord(seed, x0, y3, z2), ValueStyleNoise.valCoord(seed, x1, y3, z2),
            ValueStyleNoise.valCoord(seed, x2, y3, z2), ValueStyleNoise.valCoord(seed, x3, y3, z2),
            // z3
            ValueStyleNoise.valCoord(seed, x0, y0, z3), ValueStyleNoise.valCoord(seed, x1, y0, z3),
            ValueStyleNoise.valCoord(seed, x2, y0, z3), ValueStyleNoise.valCoord(seed, x3, y0, z3),
            ValueStyleNoise.valCoord(seed, x0, y1, z3), ValueStyleNoise.valCoord(seed, x1, y1, z3),
            ValueStyleNoise.valCoord(seed, x2, y1, z3), ValueStyleNoise.valCoord(seed, x3, y1, z3),
            ValueStyleNoise.valCoord(seed, x0, y2, z3), ValueStyleNoise.valCoord(seed, x1, y2, z3),
            ValueStyleNoise.valCoord(seed, x2, y2, z3), ValueStyleNoise.valCoord(seed, x3, y2, z3),
            ValueStyleNoise.valCoord(seed, x0, y3, z3), ValueStyleNoise.valCoord(seed, x1, y3, z3),
            ValueStyleNoise.valCoord(seed, x2, y3, z3), ValueStyleNoise.valCoord(seed, x3, y3, z3),
            xs, ys, zs
        ) * (1 / (1.5 * 1.5 * 1.5));
    }
}
