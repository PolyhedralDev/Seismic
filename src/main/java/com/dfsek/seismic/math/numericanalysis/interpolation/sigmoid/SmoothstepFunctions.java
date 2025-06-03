package com.dfsek.seismic.math.numericanalysis.interpolation.sigmoid;

import com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;
import com.dfsek.seismic.math.trigonometry.TrigonometryConstants;
import com.dfsek.seismic.math.trigonometry.TrigonometryFunctions;


public class SmoothstepFunctions {
    /**
     * Returns the result of a cubic polynomial smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double cubicPolynomialSmoothstep(double x) {
        return x * x * (3.0-2.0*x);
    }

    /**
     * Returns the result of the inverse cubic polynomial smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double invCubicPolynomialSmoothstep(double x) {
        return 0.5 - TrigonometryFunctions.sin(Math.asin(1.0 - 2.0 * x) / 3.0);
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
     * Returns the result of the inverse quartic polynomial smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double invQuarticPolynomialSmoothstep(double x) {
        return Math.sqrt(1.0 - Math.sqrt(1.0 - x));
    }

    /**
     * Returns the result of a quintic polynomial smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double quinticPolynomialSmoothstep(double x) {
        return x * x * x * ArithmeticFunctions.fma(x, ArithmeticFunctions.fma(x, 6.0, -15.0),  10.0);
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
     * Returns the result of the inverse cubic rational smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double invCubicRationalSmoothstep(double x) {
        double a = Math.pow(x, 1.0 / 3.0);
        double b = Math.pow(1.0 - x, 1.0 / 3.0);
        return a / (a + b);
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

    /**
     * Returns the result of the inverse quartic rational smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double invQuarticRationalSmoothstep(double x) {
        return (x - Math.sqrt(x * (1.0 - x))) / (2.0 * x - 1.0);
    }

    /**
     * Returns the result of a rational smoothstep function with a given exponent.
     *
     * @param x the interpolation parameter.
     * @param n the exponent.
     *
     * @return the interpolated value.
     */
    public static double rationalSmoothstep(double x, double n) {
        return Math.pow(x, n) / (Math.pow(x, n) + Math.pow(1.0 - x, n));
    }

    /**
     * Returns the result of the inverse rational smoothstep function with a given exponent.
     *
     * @param x the interpolation parameter.
     * @param n the exponent.
     *
     * @return the interpolated value.
     */
    public static double invRationalSmoothstep(double x, double n) {
        return SmoothstepFunctions.rationalSmoothstep(x, 1.0 / n);
    }

    /**
     * Returns the result of a piecewise quadratic smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double piecewiseQuadraticSmoothstep(double x) {
        return (x < 0.5) ? 2.0 * x * x : 2.0 * x * (2.0 - x) - 1.0;
    }

    /**
     * Returns the result of the inverse piecewise quadratic smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double invPiecewiseQuadraticSmoothstep(double x) {
        return (x < 0.5) ? Math.sqrt(0.5 * x) : 1.0 - Math.sqrt(0.5 - 0.5 * x);
    }

    /**
     * Returns the result of a piecewise polynomial smoothstep function with a given exponent.
     *
     * @param x the interpolation parameter.
     * @param n the exponent.
     *
     * @return the interpolated value.
     */
    public static double piecewisePolynomialSmoothstep(double x, double n) {
        return (x < 0.5) ? 0.5 * Math.pow(2.0 * x, n) : 1.0 - 0.5 * Math.pow(2.0 * (1.0 - x), n);
    }

    /**
     * Returns the result of the inverse piecewise polynomial smoothstep function with a given exponent.
     *
     * @param x the interpolation parameter.
     * @param n the exponent.
     *
     * @return the interpolated value.
     */
    public static double invPiecewisePolynomialSmoothstep(double x, double n) {
        return (x < 0.5) ? 0.5 * Math.pow(2.0 * x, 1.0 / n) : 1.0 - 0.5 * Math.pow(2.0 * (1.0 - x), 1.0 / n);
    }

    /**
     * Returns the result of a trigonometric smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double trigonometricSmoothstep(double x) {
        return 0.5 - 0.5 * TrigonometryFunctions.cos(TrigonometryConstants.PI * x);
    }

    /**
     * Returns the result of the inverse trigonometric smoothstep function.
     *
     * @param x the interpolation parameter.
     *
     * @return the interpolated value.
     */
    public static double invTrigonometricSmoothstep(double x) {
        return Math.acos(1.0 - 2.0 * x) / TrigonometryConstants.PI;
    }
}