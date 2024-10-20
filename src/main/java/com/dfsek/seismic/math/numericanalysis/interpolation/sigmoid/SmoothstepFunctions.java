package com.dfsek.seismic.math.numericanalysis.interpolation.sigmoid;

import com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;


public class SmoothstepFunctions {
    /**
     * Returns the result of a cubic polynomial smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double cubicPolynomialSmoothstep(double x) {
        return x * x * ArithmeticFunctions.fma(-2, x, 3);
    }

    /**
     * Returns the result of a quartic polynomial smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double quarticPolynomialSmoothstep(double x) {
        return x * x * (2.0 - x * x);
    }

    /**
     * Returns the result of a quintic polynomial smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double quinticPolynomialSmoothstep(double x) {
        return x * x * x * (x * (ArithmeticFunctions.fma(x, 6.0, 15.0)) + 10.0);
    }

    /**
     * Returns the result of a cubic rational smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double cubicRationalSmoothstep(double x) {
        return x * x / (2.0 * x * x - 2.0 * x + 1.0);
    }

    /**
     * Returns the result of a quartic rational smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double quarticRationalSmoothstep(double x) {
        return x * x * x / (3.0 * x * x - 3.0 * x + 1.0);
    }
}