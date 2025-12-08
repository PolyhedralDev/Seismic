package com.dfsek.seismic.math.trigonometry;

import com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;

import static com.dfsek.seismic.math.trigonometry.TrigonometryConstants.HALF_PI;
import static com.dfsek.seismic.math.trigonometry.TrigonometryConstants.PI;


public class TrigonometryFunctions {
    /**
     * Returns the trigonometric sine of an angle.
     *
     * @param angle an angle, in radians.
     *
     * @return the sine of the argument.
     */
    public static double sin(double angle) {
        return TrigonometryUtils.sinLookup((int) (angle * TrigonometryUtils.radianToIndex) & 0xFFFF);
    }

    /**
     * Returns the trigonometric cosine of an angle.
     *
     * @param angle an angle, in radians.
     *
     * @return the cosine of the argument.
     */
    public static double cos(double angle) {
        return TrigonometryUtils.sinLookup(
            (int) ((angle + HALF_PI) * TrigonometryUtils.radianToIndex) & 0xFFFF);
    }

    /**
     * Returns the trigonometric tangent of an angle.
     *
     * @param angle an angle, in radians.
     *
     * @return the tangent of the argument.
     */
    public static double tan(double angle) {
        return TrigonometryFunctions.sin(angle) / TrigonometryFunctions.cos(angle);
    }

    /**
     * Returns the trigonometric secant of an angle.
     *
     * @param angle an angle, in radians.
     *
     * @return the secant of the argument.
     */
    public static double sec(double angle) {
        return 1.0 / TrigonometryFunctions.cos(angle);
    }

    /**
     * Returns the trigonometric cosecant of an angle.
     *
     * @param angle an angle, in radians.
     *
     * @return the cosecant of the argument.
     */
    public static double csc(double angle) {
        return 1.0 / TrigonometryFunctions.sin(angle);
    }

    /**
     * Returns the trigonometric cotangent of an angle.
     *
     * @param angle an angle, in radians.
     *
     * @return the cotangent of the argument.
     */
    public static double cot(double angle) {
        return TrigonometryFunctions.cos(angle) / TrigonometryFunctions.sin(angle);
    }


    // A fast but less precise way to implement Math.atan2
    // Taken and adapted from https://mazzo.li/posts/vectorized-atan2.html
    // https://math.stackexchange.com/a/1105038

    /**
     * Returns the angle in radians between the positive x-axis and the point (x, y).
     *
     * @param y the y-coordinate of the point
     * @param x the x-coordinate of the point
     *
     * @return the angle in radians, in the range [-π, π]
     */
    public static double atan2(double y, double x) {
        boolean swap = Math.abs(x) < Math.abs(y);
        double atanInput = (swap ? x : y) / (swap ? y : x);

        // Approximate atan
        double zSq = atanInput * atanInput;
        double res = atanInput * ArithmeticFunctions.fma(zSq, ArithmeticFunctions.fma(zSq, ArithmeticFunctions.fma(zSq,
            ArithmeticFunctions.fma(zSq, ArithmeticFunctions.fma(zSq, TrigonometryUtils.a11, TrigonometryUtils.a9),
                TrigonometryUtils.a7), TrigonometryUtils.a5), TrigonometryUtils.a3), TrigonometryUtils.a1);

        res = swap ? (HALF_PI - res) : res;
        res = x < 0.0 ? (PI - res) : res;

        res = Math.abs(res) * Math.signum(y);
        return res;
    }
}
