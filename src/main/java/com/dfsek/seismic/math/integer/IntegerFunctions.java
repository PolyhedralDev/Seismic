package com.dfsek.seismic.math.integer;

public class IntegerFunctions {
    /**
     * Returns the squashed value of the first and last integers into a single long value.
     *
     * @param first the first integer.
     * @param last  the last integer.
     *
     * @return the squashed long value.
     */
    public static long squash(int first, int last) {
        return (((long) first) << 32) | (last & 0xffffffffL);
    }

    /**
     * Returns the power of 10 for the given long value.
     * <p>
     * Undefined behavior for <i>x</i> &le; <i>0</i>.
     *
     * @param x a value.
     *
     * @return the power of 10 for the given long value.
     */
    public static long iPow10(long x) {
        return IntegerConstants.POW10TABLE[(int) x];
    }

    /**
     * Returns the result of raising the base to the power of the exponent.
     * <p>
     * Undefined behavior for <i>yd</i> &lt; <i>0</i>.
     *
     * @param x the base value.
     * @param y the exponent value.
     *
     * @return the result of raising the base to the power of the exponent.
     */
    public static int iPow(long x, long y) {
        long result = 1;
        while(y > 0) {
            if((y & 1) == 0) {
                x *= x;
                y >>>= 1;
            } else {
                result *= x;
                y--;
            }
        }
        return (int) result;
    }

    /**
     * Returns the smallest (closest to negative infinity) long value that is greater than or equal to the logarithm base 2 of the argument
     * and is equal to a mathematical integer.
     * <p>
     * Undefined behavior for <i>x</i> &le; <i>0</i>.
     *
     * @param x a value.
     *
     * @return ceilinged log2 of the value.
     */
    public static long iLog2Ceil(long x) {
        return IntegerConstants.LONG_SIZE - Long.numberOfLeadingZeros(x);
    }

    /**
     * Returns the largest (closest to positive infinity) long value that is less than or equal to the logarithm base 2 of the argument
     * and is equal to a mathematical integer.
     * <p>
     * Undefined behavior for <i>x</i> &le; <i>0</i>.
     *
     * @param x a value.
     *
     * @return log2 of the value.
     */
    public static long iLog2(long x) {
        return (IntegerConstants.LONG_SIZE - 1) - Long.numberOfLeadingZeros(x);
    }

    /**
     * Returns the smallest (closest to negative infinity) long value that is greater than or equal to the logarithm base 10 of the argument
     * and is equal to a mathematical integer.
     * <p>
     * Undefined behavior for <i>x</i> &le; <i>0</i>.
     *
     * @param x a value.
     *
     * @return ceilinged log10 of the value.
     */
    public static long iLog10Ceil(long x) {
        long minDigits = ((Long.numberOfLeadingZeros(x) | (-IntegerConstants.LONG_SIZE)) * -1233 >> 12);
        return minDigits + (IntegerFunctions.iPow10(minDigits) <= x ? 1 : 0);
    }

    /**
     * Returns the largest (closest to positive infinity) long value that is less than or equal to the logarithm base 10 of the argument
     * and is equal to a mathematical integer.
     * <p>
     * Undefined behavior for <i>x</i> &le; <i>0</i>.
     *
     * @param x a value.
     *
     * @return log10 of the value.
     */
    public static long iLog10(long x) {
        long minDigits = ((Long.numberOfLeadingZeros(x) | (-IntegerConstants.LONG_SIZE)) * -1233 >> 12);
        return minDigits + (IntegerFunctions.iPow10(minDigits) <= x ? 0 : -1);
    }

    /**
     * Returns the power of 10 for the given double value.
     * <p>
     * Undefined behavior for <i>x</i> &le; <i>0</i>.
     *
     * @param x a value.
     *
     * @return the power of 10 for the given double value.
     */
    public static double iPow10(double x) {
        return (double) IntegerFunctions.iPow10((long) x);
    }

    /**
     * Returns the result of raising the base to the power of the exponent.
     * <p>
     * Undefined behavior for <i>yd</i> &lt; <i>0</i>.
     *
     * @param x  the base value.
     * @param yd the exponent value.
     *
     * @return the result of raising the base to the power of the exponent.
     */
    public static double iPow(double x, double yd) {
        long y = (long) yd;
        double result = 1;
        while(y > 0) {
            if((y & 1) == 0) {
                x *= x;
                y >>>= 1;
            } else {
                result *= x;
                y--;
            }
        }
        return result;
    }

    /**
     * Returns the smallest (closest to negative infinity) double value that is greater than or equal to the logarithm base 2 of the
     * argument
     * and is equal to a mathematical integer.
     * <p>
     * Undefined behavior for <i>x</i> &le; <i>0</i>.
     *
     * @param x a value.
     *
     * @return ceilinged log2 of the value.
     */
    public static double iLog2Ceil(double x) {
        return (double) IntegerFunctions.iLog2Ceil((long) x);
    }

    /**
     * Returns the largest (closest to positive infinity) double value that is less than or equal to the logarithm base 2 of the argument
     * and is equal to a mathematical integer.
     * <p>
     * Undefined behavior for <i>x</i> &le; <i>0</i>.
     *
     * @param x a value.
     *
     * @return log2 of the value.
     */
    public static double iLog2(double x) {
        return (double) IntegerFunctions.iLog2((long) x);
    }

    /**
     * Returns the smallest (closest to negative infinity) double value that is greater than or equal to the logarithm base 10 of the
     * argument
     * and is equal to a mathematical integer.
     * <p>
     * Undefined behavior for <i>x</i> &le; <i>0</i>.
     *
     * @param x a value.
     *
     * @return ceilinged log10 of the value.
     */
    public static double iLog10Ceil(double x) {
        return (double) IntegerFunctions.iLog10Ceil((long) x);
    }

    /**
     * Returns the largest (closest to positive infinity) double value that is less than or equal to the logarithm base 10 of the argument
     * and is equal to a mathematical integer.
     * <p>
     * Undefined behavior for <i>x</i> &le; <i>0</i>.
     *
     * @param x a value.
     *
     * @return log10 of the value.
     */
    public static double iLog10(double x) {
        return (double) IntegerFunctions.iLog10((long) x);
    }
}
