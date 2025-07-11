/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.simplex;

import com.dfsek.seismic.algorithms.sampler.noise.NoiseFunction;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.math.numericanalysis.interpolation.InterpolationFunctions;
import com.dfsek.seismic.math.numericanalysis.interpolation.sigmoid.SmoothstepFunctions;


/**
 * NoiseSampler implementation to provide Perlin Noise.
 */
public class PerlinSampler extends SimplexStyleSampler {
    public PerlinSampler(double frequency, long salt) {
        super(frequency, salt);
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y) {
        int seed = (int) sl;
        int x0 = FloatingPointFunctions.floor(x);
        int y0 = FloatingPointFunctions.floor(y);

        double xd0 = x - x0;
        double yd0 = y - y0;
        double xd1 = xd0 - 1;
        double yd1 = yd0 - 1;

        double xs = SmoothstepFunctions.quinticPolynomialSmoothstep(xd0);
        double ys = SmoothstepFunctions.quinticPolynomialSmoothstep(yd0);

        x0 *= NoiseFunction.PRIME_X;
        y0 *= NoiseFunction.PRIME_Y;
        int x1 = x0 + NoiseFunction.PRIME_X;
        int y1 = y0 + NoiseFunction.PRIME_Y;

        return InterpolationFunctions.biLerp(
            SimplexStyleSampler.gradCoord(seed, x0, y0, xd0, yd0),
            SimplexStyleSampler.gradCoord(seed, x1, y0, xd1, yd0),
            SimplexStyleSampler.gradCoord(seed, x0, y1, xd0, yd1),
            SimplexStyleSampler.gradCoord(seed, x1, y1, xd1, yd1),
            xs, ys
        ) * 1.4247691104677813;
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y, double z) {
        int seed = (int) sl;
        int x0 = FloatingPointFunctions.floor(x);
        int y0 = FloatingPointFunctions.floor(y);
        int z0 = FloatingPointFunctions.floor(z);

        double xd0 = x - x0;
        double yd0 = y - y0;
        double zd0 = z - z0;
        double xd1 = xd0 - 1;
        double yd1 = yd0 - 1;
        double zd1 = zd0 - 1;

        double xs = SmoothstepFunctions.quinticPolynomialSmoothstep(xd0);
        double ys = SmoothstepFunctions.quinticPolynomialSmoothstep(yd0);
        double zs = SmoothstepFunctions.quinticPolynomialSmoothstep(zd0);

        x0 *= NoiseFunction.PRIME_X;
        y0 *= NoiseFunction.PRIME_Y;
        z0 *= NoiseFunction.PRIME_Z;
        int x1 = x0 + NoiseFunction.PRIME_X;
        int y1 = y0 + NoiseFunction.PRIME_Y;
        int z1 = z0 + NoiseFunction.PRIME_Z;

        return InterpolationFunctions.triLerp(
            SimplexStyleSampler.gradCoord(seed, x0, y0, z0, xd0, yd0, zd0),
            SimplexStyleSampler.gradCoord(seed, x1, y0, z0, xd1, yd0, zd0),
            SimplexStyleSampler.gradCoord(seed, x0, y1, z0, xd0, yd1, zd0),
            SimplexStyleSampler.gradCoord(seed, x1, y1, z0, xd1, yd1, zd0),
            SimplexStyleSampler.gradCoord(seed, x0, y0, z1, xd0, yd0, zd1),
            SimplexStyleSampler.gradCoord(seed, x1, y0, z1, xd1, yd0, zd1),
            SimplexStyleSampler.gradCoord(seed, x0, y1, z1, xd0, yd1, zd1),
            SimplexStyleSampler.gradCoord(seed, x1, y1, z1, xd1, yd1, zd1),
            xs, ys, zs
        ) * 0.964921414852142333984375;
    }
}
