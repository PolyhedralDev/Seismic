package com.polyhedraldevelopment.seismic.math.algebra;

public class AlgebraFunctions {
    /**
     * Returns one over positive square root of a
     * {@code double} value.
     *
     * @param a a value.
     *
     * @return the positive inverse square root of {@code a}.
     */
    public static double invSqrt(double a) {
        double halfX = 0.5d * a;
        long i = Double.doubleToLongBits(a); // evil floating point bit level hacking
        i = 0x5fe6eb50c7b537a9L - (i >> 1); // what the fuck?
        double y = Double.longBitsToDouble(i);
        y *= (1.5d - halfX * y * y); // 1st newtonian iteration
        // y *= (1.5d - halfX * y * y); // 2nd newtonian iteration, this can be removed

        return y;
    }
}
