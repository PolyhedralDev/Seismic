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
import com.dfsek.seismic.math.numericanalysis.interpolation.sigmoid.SmoothstepFunctions;


public class ValueSampler extends ValueStyleNoise {
    public ValueSampler(double frequency, long salt) {
        super(frequency, salt);
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y) {
        int seed = (int) sl;
        int x0 = FloatingPointFunctions.floor(x);
        int y0 = FloatingPointFunctions.floor(y);

        double xs = SmoothstepFunctions.cubicPolynomialSmoothstep(x - x0);
        double ys = SmoothstepFunctions.cubicPolynomialSmoothstep(y - y0);

        x0 *= NoiseFunction.PRIME_X;
        y0 *= NoiseFunction.PRIME_Y;
        int x1 = x0 + NoiseFunction.PRIME_X;
        int y1 = y0 + NoiseFunction.PRIME_Y;

        return InterpolationFunctions.biLerp(
            ValueStyleNoise.valCoord(seed, x0, y0),
            ValueStyleNoise.valCoord(seed, x1, y0),
            ValueStyleNoise.valCoord(seed, x0, y1),
            ValueStyleNoise.valCoord(seed, x1, y1),
            xs, ys
        );
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y, double z) {
        int seed = (int) sl;
        int x0 = FloatingPointFunctions.floor(x);
        int y0 = FloatingPointFunctions.floor(y);
        int z0 = FloatingPointFunctions.floor(z);

        double xs = SmoothstepFunctions.cubicPolynomialSmoothstep(-x0);
        double ys = SmoothstepFunctions.cubicPolynomialSmoothstep(y - y0);
        double zs = SmoothstepFunctions.cubicPolynomialSmoothstep(z - z0);

        x0 *= NoiseFunction.PRIME_X;
        y0 *= NoiseFunction.PRIME_Y;
        z0 *= NoiseFunction.PRIME_Z;
        int x1 = x0 + NoiseFunction.PRIME_X;
        int y1 = y0 + NoiseFunction.PRIME_Y;
        int z1 = z0 + NoiseFunction.PRIME_Z;

        return InterpolationFunctions.triLerp(
            ValueStyleNoise.valCoord(seed, x0, y0, z0),
            ValueStyleNoise.valCoord(seed, x1, y0, z0),
            ValueStyleNoise.valCoord(seed, x0, y1, z0),
            ValueStyleNoise.valCoord(seed, x1, y1, z0),
            ValueStyleNoise.valCoord(seed, x0, y0, z1),
            ValueStyleNoise.valCoord(seed, x1, y0, z1),
            ValueStyleNoise.valCoord(seed, x0, y1, z1),
            ValueStyleNoise.valCoord(seed, x1, y1, z1),
            xs, ys, zs
        );
    }

}
