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
     * Undefined behavior for x <= 0.
     *
     * @param x a value.
     *
     * @return the power of 10 for the given long value.
     */
    public static long pow10(long x) {
        return IntegerConstants.POW10TABLE[(int) x];
    }

    /**
     * Returns the smallest (closest to negative infinity) long value that is greater than or equal to the logarithm base 2 of the argument
     * and is equal to a mathematical integer.
     * Undefined behavior for x <= 0.
     *
     * @param x a value
     *
     * @return log2 of the value ceilinged.
     */
    public static long log2Ceil(long x) {
        return IntegerConstants.LONGSIZE - Long.numberOfLeadingZeros(x);
    }

    /**
     * Returns the largest (closest to positive infinity) long value that is less than or equal to the logarithm base 2 of the argument and
     * is equal to a mathematical integer.
     * Undefined behavior for x <= 0.
     *
     * @param x a value
     *
     * @return log2 of the value floored.
     */
    public static long log2Floor(long x) {
        return (IntegerConstants.LONGSIZE - 1) - Long.numberOfLeadingZeros(x);
    }

    /**
     * Returns the smallest (closest to negative infinity) long value that is greater than or equal to the logarithm base 10 of the argument
     * and is equal to a mathematical integer.
     * Undefined behavior for x <= 0.
     *
     * @param x a value
     *
     * @return ceilinged log10 of the value.
     */
    public static long log10Ceil(long x) {
        long minDigits = ((Long.numberOfLeadingZeros(x) | (-IntegerConstants.LONGSIZE)) * -1233 >> 12);
        return minDigits + (IntegerFunctions.pow10(minDigits) <= x ? 1 : 0);
    }

    /**
     * Returns the largest (closest to positive infinity) long value that is less than or equal to the logarithm base 10 of the argument and
     * is equal to a mathematical integer.
     * Undefined behavior for x <= 0.
     *
     * @param x a value
     *
     * @return floored log10 of the value.
     */
    public static long log10Floor(long x) {
        long minDigits = ((Long.numberOfLeadingZeros(x) | (-IntegerConstants.LONGSIZE)) * -1233 >> 12);
        return minDigits + (IntegerFunctions.pow10(minDigits) <= x ? 0 : -1);
    }
}
