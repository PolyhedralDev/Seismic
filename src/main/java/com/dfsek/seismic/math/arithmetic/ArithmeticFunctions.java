package com.dfsek.seismic.math.arithmetic;

import com.dfsek.seismic.util.VMConstants;


public class ArithmeticFunctions {
    /**
     * Returns the fused multiply add of the three arguments; that is,
     * returns the product of the first two arguments summed
     * with the third argument.
     * <p>
     * On supported hardware, {@code a * b + c} is implemented as a single
     * instruction that is faster and more accurate than the two-step sequence.
     * In contrast, if {@code a * b + c} is evaluated as a regular
     * floating-point expression, two rounding errors are involved,
     * the first for the multiply operation, the second for the
     * addition operation.
     *
     * @param a a value.
     * @param b a value.
     * @param c a value.
     *
     * @return (< i > a < / i > & times ; < i > b < / i > + < i > c < / i >).
     */
    public static double fma(double a, double b, double c) {
        if(VMConstants.HAS_FAST_SCALAR_FMA) {
            return Math.fma(a, b, c);
        } else {
            return a * b + c;
        }
    }
}
