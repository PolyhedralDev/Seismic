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
     * @param n a value.
     *
     * @return the rounded value.
     */
    public static int round(double n) {
        return n < 0 ? (int) (n - 0.5) : (int) (n + 0.5);
    }

    /**
     * Returns the largest (closest to negative infinity) {@code int} value that is less than or equal to the {@code double} argument.
     *
     * @param n a value.
     *
     * @return floored value.
     */
    public static int floor(double n) {
        int ni = (int) n;
        return n < ni ? ni - 1 : ni;
    }

    /**
     * Returns the smallest (closest to positive infinity) {@code int} value that is less than or equal to the {@code double} argument.
     *
     * @param n a value.
     *
     * @return ceilinged value.
     */
    public static int ceil(double n) {
        int ni = (int) n;
        return n > ni ? ni + 1 : ni;
    }

    /**
     * Returns the fractional part of a {@code double} value.
     *
     * @param n a value.
     *
     * @return the fractional part of the input value.
     */
    public static double getFraction(double n) {
        return n - Math.floor(n);
    }


    /**
     * Returns the modulus of two {@code double} values.
     *
     * @param a the dividend.
     * @param b the divisor.
     *
     * @return the modulus
     */
    public static double modulus(double a, double b) {
        return (a %= b) + (a < 0.0D ? b : 0.0D); //adding 0.0 will convert -0.0 to +0.0.
    }
}
