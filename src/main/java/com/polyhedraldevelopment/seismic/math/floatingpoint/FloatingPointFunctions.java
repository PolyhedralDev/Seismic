package com.polyhedraldevelopment.seismic.math.floatingpoint;

import com.polyhedraldevelopment.seismic.util.VMConstants;


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
        return equals(a, b, FloatingPointConstants.EPSILON);
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
    public static boolean equals(double a, double b, double epsilon) {
        return a == b || Math.abs(a - b) < epsilon;
    }

    /**
     * Returns a rounded {@code double} value to the nearest {@code long} value.
     *
     * @param x a value.
     *
     * @return the rounded value.
     */
    public static long round(double x) {
        if(VMConstants.HAS_FAST_SCALAR_ROUND) {
            return Math.round(x);
        }
        return x >= 0 ? (long) (x + 0.5f) : (long) (x - 0.5);
    }

    /**
     * Returns the largest (closest to negative infinity) {@code long} value that is less than or equal to the {@code double} argument.
     *
     * @param x a value.
     *
     * @return floored value.
     */
    public static long floor(double x) {
        if(VMConstants.HAS_FAST_SCALAR_FLOOR) {
            return (long) Math.floor(x);
        }
        return x >= 0 ? (long) x : (long) x - 1;
    }

    /**
     * Returns the smallest (closest to positive infinity) {@code long} value that is less than or equal to the {@code double} argument.
     *
     * @param x a value.
     *
     * @return ceilinged value.
     */
    public static long ceil(double x) {
        if(VMConstants.HAS_FAST_SCALAR_CEIL) {
            return (long) Math.ceil(x);
        }
        long i = (long) x;
        if(i < x) i++;
        return i;
    }

    /**
     * Returns the fractional part of a {@code double} value.
     *
     * @param x a value.
     *
     * @return the fractional part of the input value.
     */
    public static double getFraction(double x) {
        return x - floor(x);
    }
}
