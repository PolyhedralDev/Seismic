package com.dfsek.seismic.math.trigonometry;

public class TrigonometryConstants {
    /**
     * The {@code double} value that is closer than any other to
     * <i>pi</i> (&pi;), the ratio of the circumference of a circle to
     * its diameter.
     */
    public static final double PI = Math.PI;
    public static final double HALF_PI = PI / 2;

    /**
     * The {@code double} value that is closer than any other superior value to
     * <i>pi</i> (&pi;), the ratio of the circumference of a circle to
     * its diameter.
     */
    public static final double PI_SUP = Double.longBitsToDouble(Double.doubleToRawLongBits(TrigonometryConstants.PI) + 1);


    /**
     * The {@code double} value that is closer than any other to
     * <i>tau</i> (&tau;), the ratio of the circumference of a circle
     * to its radius.
     * <p>
     * The value of <i>pi</i> is one half that of <i>tau</i>; in other
     * words, <i>tau</i> is double <i>pi</i> .
     */
    public static final double TAU = Math.TAU;

    /**
     * The {@code double} value that is closer than any other superior value to
     * <i>tau</i> (&tau;), the ratio of the circumference of a circle
     * to its radius.
     * <p>
     * The value of <i>pi</i> is one half that of <i>tau</i>; in other
     * words, <i>tau</i> is double <i>pi</i> .
     */
    public static final double TAU_SUP = Double.longBitsToDouble(Double.doubleToRawLongBits(TrigonometryConstants.TAU) + 1);

    /**
     * Polynomial defs for atan2 taken from <a href="https://mazzo.li/posts/vectorized-atan2.html">https://mazzo.li/</a>
     */
    public static final double a1 = 0.99997726;
    public static final double a3 = -0.33262347;
    public static final double a5 = 0.19354346;
    public static final double a7 = -0.11643287;
    public static final double a9 = 0.05265332;
    public static final double a11 = -0.01172120;

}
