package com.dfsek.seismic.math.algebra;

public class LinearAlgebraUtils {
    /**
     * For using sqrt, to avoid overflow/underflow, we want values magnitude in
     * [1/sqrt(Double.MAX_VALUE/n),sqrt(Double.MAX_VALUE/n)],
     * n being the number of arguments.
     * <p>
     * sqrt(Double.MAX_VALUE/2) = 9.480751908109176E153
     * and
     * sqrt(Double.MAX_VALUE/3) = 7.741001517595157E153
     * so
     * 2^511 = 6.7039039649712985E153
     * works for both.
     */
    static final double HYPOT_MAX_MAG = StrictMath.pow(2, 511);

    /**
     * Large enough to get a value's magnitude back into [2^-511,2^511]
     * from Double.MIN_VALUE or Double.MAX_VALUE, and small enough
     * not to get it across that range (considering a 2*53 tolerance
     * due to only checking magnitude of min/max value, and scaling
     * all values together).
     */
    static final double HYPOT_FACTOR = StrictMath.pow(2, 750);


    protected static double hypot_NaN(double xAbs, double yAbs) {
        if((xAbs == Double.POSITIVE_INFINITY) || (yAbs == Double.POSITIVE_INFINITY)) {
            return Double.POSITIVE_INFINITY;
        } else {
            return Double.NaN;
        }
    }

    protected static double hypot_NaN(double xAbs, double yAbs, double zAbs) {
        if((xAbs == Double.POSITIVE_INFINITY) || (yAbs == Double.POSITIVE_INFINITY) || (zAbs == Double.POSITIVE_INFINITY)) {
            return Double.POSITIVE_INFINITY;
        } else {
            return Double.NaN;
        }
    }
}