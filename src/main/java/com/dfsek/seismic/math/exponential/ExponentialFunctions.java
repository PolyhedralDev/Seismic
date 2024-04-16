package com.dfsek.seismic.math.exponential;

public class ExponentialFunctions {
    /**
     * Calculates the exponential of a given value using a specific formula.
     *
     * @param val the value for which to calculate the exponential
     * @return the calculated exponential of the input value
     */
    public static double exp(double val) {
        final long tmp = (long) (1512775 * val + 1072632447);
        return Double.longBitsToDouble(tmp << 32);
    }
}
