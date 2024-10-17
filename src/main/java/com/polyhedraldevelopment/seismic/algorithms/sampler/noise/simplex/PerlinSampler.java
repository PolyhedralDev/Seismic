/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.polyhedraldevelopment.seismic.algorithms.sampler.noise.simplex;

import com.polyhedraldevelopment.seismic.algorithms.sampler.noise.NoiseFunction;
import com.polyhedraldevelopment.seismic.math.numericanalysis.interpolation.InterpolationFunctions;


/**
 * NoiseSampler implementation to provide Perlin Noise.
 */
public class PerlinSampler extends SimplexStyleSampler {
    @Override
    public double getNoiseRaw(long sl, double x, double y) {
        int seed = (int) sl;
        int x0 = (int) Math.floor(x);
        int y0 = (int) Math.floor(y);

        double xd0 = x - x0;
        double yd0 = y - y0;
        double xd1 = xd0 - 1;
        double yd1 = yd0 - 1;

        double xs = InterpolationFunctions.easingQuinticInterpolation(xd0);
        double ys = InterpolationFunctions.easingQuinticInterpolation(yd0);

        x0 *= NoiseFunction.PRIME_X;
        y0 *= NoiseFunction.PRIME_Y;
        int x1 = x0 + NoiseFunction.PRIME_X;
        int y1 = y0 + NoiseFunction.PRIME_Y;

        double xf0 = InterpolationFunctions.lerp(xs, SimplexStyleSampler.gradCoord(seed, x0, y0, xd0, yd0), SimplexStyleSampler.gradCoord(seed, x1, y0, xd1, yd0));
        double xf1 = InterpolationFunctions.lerp(xs, SimplexStyleSampler.gradCoord(seed, x0, y1, xd0, yd1), SimplexStyleSampler.gradCoord(seed, x1, y1, xd1, yd1));

        return InterpolationFunctions.lerp(ys, xf0, xf1) * 1.4247691104677813;
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y, double z) {
        int seed = (int) sl;
        int x0 = (int) Math.floor(x);
        int y0 = (int) Math.floor(y);
        int z0 = (int) Math.floor(z);

        double xd0 = x - x0;
        double yd0 = y - y0;
        double zd0 = z - z0;
        double xd1 = xd0 - 1;
        double yd1 = yd0 - 1;
        double zd1 = zd0 - 1;

        double xs = InterpolationFunctions.easingQuinticInterpolation(xd0);
        double ys = InterpolationFunctions.easingQuinticInterpolation(yd0);
        double zs = InterpolationFunctions.easingQuinticInterpolation(zd0);

        x0 *= NoiseFunction.PRIME_X;
        y0 *= NoiseFunction.PRIME_Y;
        z0 *= NoiseFunction.PRIME_Z;
        int x1 = x0 + NoiseFunction.PRIME_X;
        int y1 = y0 + NoiseFunction.PRIME_Y;
        int z1 = z0 + NoiseFunction.PRIME_Z;

        double xf00 = InterpolationFunctions.lerp(xs, SimplexStyleSampler.gradCoord(seed, x0, y0, z0, xd0, yd0, zd0),
            SimplexStyleSampler.gradCoord(seed, x1, y0, z0, xd1, yd0, zd0));
        double xf10 = InterpolationFunctions.lerp(xs, SimplexStyleSampler.gradCoord(seed, x0, y1, z0, xd0, yd1, zd0),
            SimplexStyleSampler.gradCoord(seed, x1, y1, z0, xd1, yd1, zd0));
        double xf01 = InterpolationFunctions.lerp(xs, SimplexStyleSampler.gradCoord(seed, x0, y0, z1, xd0, yd0, zd1),
            SimplexStyleSampler.gradCoord(seed, x1, y0, z1, xd1, yd0, zd1));
        double xf11 = InterpolationFunctions.lerp(xs, SimplexStyleSampler.gradCoord(seed, x0, y1, z1, xd0, yd1, zd1),
            SimplexStyleSampler.gradCoord(seed, x1, y1, z1, xd1, yd1, zd1));

        double yf0 = InterpolationFunctions.lerp(ys, xf00, xf10);
        double yf1 = InterpolationFunctions.lerp(ys, xf01, xf11);

        return InterpolationFunctions.lerp(zs, yf0, yf1) * 0.964921414852142333984375;
    }
}
