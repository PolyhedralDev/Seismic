package com.polyhedraldevelopment.seismic.math.numericanalysis.interpolation;

public class SmoothstepFunctions {
    /**
     * Returns the result of a cubic polynomial smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double cubicPolynomialSmoothstep(double x) {
        return x * x * (3 - 2 * x);
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
        return x * x * x * (x * (x * 6.0 - 15.0) + 10.0);
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