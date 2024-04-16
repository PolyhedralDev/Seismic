/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.samplers.noise.value;

import  com.dfsek.seismic.math.numericanalysis.NumericAnalysisFunctions;


public class ValueSampler extends ValueStyleNoise {
    @Override
    public double getNoiseRaw(long sl, double x, double y) {
        int seed = (int) sl;
        int x0 = (int) Math.floor(x);
        int y0 = (int) Math.floor(y);

        double xs = NumericAnalysisFunctions.easingHermiteInterpolation(x - x0);
        double ys = NumericAnalysisFunctions.easingHermiteInterpolation(y - y0);

        x0 *= PRIME_X;
        y0 *= PRIME_Y;
        int x1 = x0 + PRIME_X;
        int y1 = y0 + PRIME_Y;

        double xf0 = NumericAnalysisFunctions.lerp(xs, valCoord(seed, x0, y0), valCoord(seed, x1, y0));
        double xf1 = NumericAnalysisFunctions.lerp(xs, valCoord(seed, x0, y1), valCoord(seed, x1, y1));

        return NumericAnalysisFunctions.lerp(ys, xf0, xf1);
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y, double z) {
        int seed = (int) sl;
        int x0 = (int) Math.floor(x);
        int y0 = (int) Math.floor(y);
        int z0 = (int) Math.floor(z);

        double xs = NumericAnalysisFunctions.easingHermiteInterpolation(x - x0);
        double ys = NumericAnalysisFunctions.easingHermiteInterpolation(y - y0);
        double zs = NumericAnalysisFunctions.easingHermiteInterpolation(z - z0);

        x0 *= PRIME_X;
        y0 *= PRIME_Y;
        z0 *= PRIME_Z;
        int x1 = x0 + PRIME_X;
        int y1 = y0 + PRIME_Y;
        int z1 = z0 + PRIME_Z;

        double xf00 = NumericAnalysisFunctions.lerp(xs, valCoord(seed, x0, y0, z0), valCoord(seed, x1, y0, z0));
        double xf10 = NumericAnalysisFunctions.lerp(xs, valCoord(seed, x0, y1, z0), valCoord(seed, x1, y1, z0));
        double xf01 = NumericAnalysisFunctions.lerp(xs, valCoord(seed, x0, y0, z1), valCoord(seed, x1, y0, z1));
        double xf11 = NumericAnalysisFunctions.lerp(xs, valCoord(seed, x0, y1, z1), valCoord(seed, x1, y1, z1));

        double yf0 = NumericAnalysisFunctions.lerp(ys, xf00, xf10);
        double yf1 = NumericAnalysisFunctions.lerp(ys, xf01, xf11);

        return NumericAnalysisFunctions.lerp(zs, yf0, yf1);
    }

}
