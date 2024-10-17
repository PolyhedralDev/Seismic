/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.polyhedraldevelopment.seismic.algorithms.sampler.noise.value;

import com.polyhedraldevelopment.seismic.algorithms.sampler.noise.NoiseFunction;
import com.polyhedraldevelopment.seismic.math.floatingpoint.FloatingPointFunctions;
import com.polyhedraldevelopment.seismic.math.numericanalysis.interpolation.InterpolationFunctions;


public class ValueSampler extends ValueStyleNoise {
    @Override
    public double getNoiseRaw(long sl, double x, double y) {
        int seed = (int) sl;
        int x0 = FloatingPointFunctions.floor(x);
        int y0 = FloatingPointFunctions.floor(y);

        double xs = InterpolationFunctions.easingHermiteInterpolation(x - x0);
        double ys = InterpolationFunctions.easingHermiteInterpolation(y - y0);

        x0 *= NoiseFunction.PRIME_X;
        y0 *= NoiseFunction.PRIME_Y;
        int x1 = x0 + NoiseFunction.PRIME_X;
        int y1 = y0 + NoiseFunction.PRIME_Y;

        double xf0 = InterpolationFunctions.lerp(xs, ValueStyleNoise.valCoord(seed, x0, y0), ValueStyleNoise.valCoord(seed, x1, y0));
        double xf1 = InterpolationFunctions.lerp(xs, ValueStyleNoise.valCoord(seed, x0, y1), ValueStyleNoise.valCoord(seed, x1, y1));

        return InterpolationFunctions.lerp(ys, xf0, xf1);
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y, double z) {
        int seed = (int) sl;
        int x0 = FloatingPointFunctions.floor(x);
        int y0 = FloatingPointFunctions.floor(y);
        int z0 = FloatingPointFunctions.floor(z);

        double xs = InterpolationFunctions.easingHermiteInterpolation(x - x0);
        double ys = InterpolationFunctions.easingHermiteInterpolation(y - y0);
        double zs = InterpolationFunctions.easingHermiteInterpolation(z - z0);

        x0 *= NoiseFunction.PRIME_X;
        y0 *= NoiseFunction.PRIME_Y;
        z0 *= NoiseFunction.PRIME_Z;
        int x1 = x0 + NoiseFunction.PRIME_X;
        int y1 = y0 + NoiseFunction.PRIME_Y;
        int z1 = z0 + NoiseFunction.PRIME_Z;

        double xf00 = InterpolationFunctions.lerp(xs, ValueStyleNoise.valCoord(seed, x0, y0, z0),
            ValueStyleNoise.valCoord(seed, x1, y0, z0));
        double xf10 = InterpolationFunctions.lerp(xs, ValueStyleNoise.valCoord(seed, x0, y1, z0),
            ValueStyleNoise.valCoord(seed, x1, y1, z0));
        double xf01 = InterpolationFunctions.lerp(xs, ValueStyleNoise.valCoord(seed, x0, y0, z1),
            ValueStyleNoise.valCoord(seed, x1, y0, z1));
        double xf11 = InterpolationFunctions.lerp(xs, ValueStyleNoise.valCoord(seed, x0, y1, z1),
            ValueStyleNoise.valCoord(seed, x1, y1, z1));

        double yf0 = InterpolationFunctions.lerp(ys, xf00, xf10);
        double yf1 = InterpolationFunctions.lerp(ys, xf01, xf11);

        return InterpolationFunctions.lerp(zs, yf0, yf1);
    }

}
