package com.dfsek.seismic.util;

import net.jafama.FastMath;

/**
 * Math operations used by noise function implementations.
 */
@SuppressWarnings("ManualMinMaxCalculation")
public final class MathHelper {
    // Hashing
    public static final int PRIME_X = 501125321;
    public static final int PRIME_Y = 1136930381;
    public static final int PRIME_Z = 1720413743;
    static final int precision = 100;
    static final int modulus = 360 * precision; // sin/cos precision
    static final double[] sin = new double[360 * 100]; // lookup table for sin/cos

    static {
        for(int i = 0; i < sin.length; i++) { // Generate sin/cos lookup table
            sin[i] = (float) Math.sin((double) (i) / (precision));
        }
    }

    /**
     * Faster implementation of {@link Math#floor}.
     *
     * @param f A decimal value.
     * @return The floor of the input, as an integer.
     */
    public static int fastFloor(double f) {
        return f >= 0 ? (int) f : (int) f - 1;
    }

    /**
     * Faster implementation of {@link Math#ceil}.
     *
     * @param f A decimal value.
     * @return The floor of the input, as an integer.
     */
    public static int fastCeil(double f) {
        int i = (int) f;
        if(i < f) i++;
        return i;
    }

    public static int hash(int seed, int xPrimed, int yPrimed, int zPrimed) {
        int hash = seed ^ xPrimed ^ yPrimed ^ zPrimed;
        hash *= 0x27d4eb2d;
        return hash;
    }

    public static int hash(int seed, int xPrimed, int yPrimed) {
        int hash = seed ^ xPrimed ^ yPrimed;
        hash *= 0x27d4eb2d;
        return hash;
    }

    /**
     * Faster implementation of {@link Math#round}.
     *
     * @param f A decimal value.
     * @return The rounded value of the input, as an integer.
     */
    public static int fastRound(double f) {
        return f >= 0 ? (int) (f + 0.5f) : (int) (f - 0.5);
    }

    public static double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }

    public static double interpHermite(double t) {
        return t * t * (3 - 2 * t);
    }

    public static double interpQuintic(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    public static double cubicLerp(double a, double b, double c, double d, double t) {
        double p = (d - c) - (a - b);
        return t * t * t * p + t * t * ((a - b) - p) + t * (c - a) + b;
    }

    public static double fastMin(double a, double b) {
        return a < b ? a : b;
    }

    public static double fastMax(double a, double b) {
        return a > b ? a : b;
    }

    public static double fastAbs(double f) {
        return f < 0 ? -f : f;
    }

    public static double fastSqrt(double f) {
        return FastMath.sqrt(f);
    }

    /**
     * Murmur64 hashing function
     *
     * @param h Input value
     * @return Hashed value
     */
    public static long murmur64(long h) {
        h ^= h >>> 33;
        h *= 0xff51afd7ed558ccdL;
        h ^= h >>> 33;
        h *= 0xc4ceb9fe1a85ec53L;
        h ^= h >>> 33;
        return h;
    }

    private static double sinLookup(int a) {
        return a >= 0 ? sin[a % (modulus)] : -sin[-a % (modulus)];
    }

    /**
     * Fast computation of {@code sin} using a lookup table.
     *
     * @param a An angle, in radians.
     * @return The sine of the angle.
     */
    public static double fastSin(double a) {
        return sinLookup((int) (a * precision + 0.5f));
    }

    /**
     * Fast computation of {@code cos} using a lookup table.
     *
     * @param a An angle, in radians.
     * @return The cosine of the angle.
     */
    public static double fastCos(double a) {
        return sinLookup((int) ((a + Math.PI / 2) * precision + 0.5f));
    }

    /**
     * Compute the value in a normally distributed data set that has probability p.
     *
     * @param p     Probability of value
     * @param mu    Mean of data
     * @param sigma Standard deviation of data
     * @return Value corresponding to input probability
     */
    public static double normalInverse(double p, double mu, double sigma) {
        if(p < 0 || p > 1)
            throw new IllegalArgumentException("Probability must be in range [0, 1]");
        if(sigma < 0)
            throw new IllegalArgumentException("Standard deviation must be positive.");
        if(p == 0)
            return Double.NEGATIVE_INFINITY;
        if(p == 1)
            return Double.POSITIVE_INFINITY;
        if(sigma == 0)
            return mu;
        double q, r, val;

        q = p - 0.5;

        if(FastMath.abs(q) <= .425) {
            r = .180625 - q * q;
            val =
                    q * (((((((r * 2509.0809287301226727 +
                            33430.575583588128105) * r + 67265.770927008700853) * r +
                            45921.953931549871457) * r + 13731.693765509461125) * r +
                            1971.5909503065514427) * r + 133.14166789178437745) * r +
                            3.387132872796366608)
                            / (((((((r * 5226.495278852854561 +
                            28729.085735721942674) * r + 39307.89580009271061) * r +
                            21213.794301586595867) * r + 5394.1960214247511077) * r +
                            687.1870074920579083) * r + 42.313330701600911252) * r + 1);
        } else {
            if(q > 0) {
                r = 1 - p;
            } else {
                r = p;
            }

            r = FastMath.sqrt(-FastMath.log(r));

            if(r <= 5) {
                r += -1.6;
                val = (((((((r * 7.7454501427834140764e-4 +
                        .0227238449892691845833) * r + .24178072517745061177) *
                        r + 1.27045825245236838258) * r +
                        3.64784832476320460504) * r + 5.7694972214606914055) *
                        r + 4.6303378461565452959) * r +
                        1.42343711074968357734)
                        / (((((((r *
                        1.05075007164441684324e-9 + 5.475938084995344946e-4) *
                        r + .0151986665636164571966) * r +
                        .14810397642748007459) * r + .68976733498510000455) *
                        r + 1.6763848301838038494) * r +
                        2.05319162663775882187) * r + 1);
            } else {
                r += -5;
                val = (((((((r * 2.01033439929228813265e-7 +
                        2.71155556874348757815e-5) * r +
                        .0012426609473880784386) * r + .026532189526576123093) *
                        r + .29656057182850489123) * r +
                        1.7848265399172913358) * r + 5.4637849111641143699) *
                        r + 6.6579046435011037772)
                        / (((((((r *
                        2.04426310338993978564e-15 + 1.4215117583164458887e-7) *
                        r + 1.8463183175100546818e-5) * r +
                        7.868691311456132591e-4) * r + .0148753612908506148525)
                        * r + .13692988092273580531) * r +
                        .59983220655588793769) * r + 1);
            }

            if(q < 0.0) {
                val = -val;
            }
        }

        return mu + sigma * val;
    }
}
