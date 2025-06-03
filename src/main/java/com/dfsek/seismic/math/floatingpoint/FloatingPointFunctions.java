package com.dfsek.seismic.math.floatingpoint;

public class FloatingPointFunctions {
    /**
     * Returns whether two {@code double} values are equal within a certain <i>epsilon</i> (&epsilon;),
     * {@value FloatingPointConstants#EPSILON}.
     *
     * @param a a value.
     * @param b a value.
     *
     * @return Whether these values are equal.
     */
    public static boolean equals(double a, double b) {
        return FloatingPointFunctions.equalsWithinEpsilon(a, b, FloatingPointConstants.EPSILON);
    }

    /**
     * Returns whether two {@code double} values are equal within a certain <i>epsilon</i> (&epsilon;),
     * {@code epsilon}.
     *
     * @param a       a value.
     * @param b       a value.
     * @param epsilon the epsilon value.
     *
     * @return Whether these values are equal.
     */
    public static boolean equalsWithinEpsilon(double a, double b, double epsilon) {
        return a == b || Math.abs(a - b) < epsilon;
    }

    /**
     * Returns a rounded {@code double} value to the nearest {@code int} value.
     *
     * @param x a value.
     *
     * @return the rounded value.
     */
    public static int round(double x) {
        return x >= 0 ? (int) (x + 0.5f) : (int) (x - 0.5);
    }

    /**
     * Returns the largest (closest to negative infinity) {@code int} value that is less than or equal to the {@code double} argument.
     *
     * @param x a value.
     *
     * @return floored value.
     */
    public static int floor(double x) {
        return x >= 0 ? (int) x : (int) x - 1;
    }

    /**
     * Returns the smallest (closest to positive infinity) {@code int} value that is less than or equal to the {@code double} argument.
     *
     * @param x a value.
     *
     * @return ceilinged value.
     */
    public static int ceil(double x) {
        return x >= 0 ? (int) x + 1 : (int) x;
    }

    /**
     * Returns the fractional part of a {@code double} value.
     *
     * @param x a value.
     *
     * @return the fractional part of the input value.
     */
    public static double getFraction(double x) {
        return x - Math.floor(x);
    }
}
