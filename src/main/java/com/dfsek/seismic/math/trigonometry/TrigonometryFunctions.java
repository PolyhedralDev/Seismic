package com.dfsek.seismic.math.trigonometry;

import com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;


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
            (int) ((angle + TrigonometryUtils.PI_OVER_2) * TrigonometryUtils.radianToIndex ) & 0xFFFF);
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
}
