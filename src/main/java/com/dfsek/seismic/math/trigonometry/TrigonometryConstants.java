package com.dfsek.seismic.math.trigonometry;

import java.math.BigDecimal;


public class TrigonometryConstants {
    /**
     * The {@code double} value that is closer than any other to
     * <i>pi</i> (&pi;), the ratio of the circumference of a circle to
     * its diameter.
     */
    public static final double PI = 3.141592653589793115997963468544185161590576171875;

    /**
     * The {@code double} value that is closer than any other superior value to
     * <i>pi</i> (&pi;), the ratio of the circumference of a circle to
     * its diameter.
     *
     * @see TrigonometryConstants#PI
     */
    public static final double PI_SUP = Double.longBitsToDouble(Double.doubleToRawLongBits(TrigonometryConstants.PI) + 1);

    /**
     * The {@code double} value that is closer than any other to
     * <i>tau</i> (&tau;), the ratio of the circumference of a circle
     * to its radius.
     * <p>
     * The value of <i>pi</i> is one half that of <i>tau</i>; in other
     * words, <i>tau</i> is double <i>pi</i>.
     *
     * @see TrigonometryConstants#PI
     */
    public static final double TAU = BigDecimal.valueOf(TrigonometryConstants.PI).multiply(BigDecimal.valueOf(2)).doubleValue();

    /**
     * The {@code double} value that is closer than any other superior value to
     * <i>tau</i> (&tau;), the ratio of the circumference of a circle
     * to its radius.
     * <p>
     * The value of <i>pi</i> is one half that of <i>tau</i>; in other
     * words, <i>tau</i> is double <i>pi</i>.
     *
     * @see TrigonometryConstants#TAU
     */
    public static final double TAU_SUP = Double.longBitsToDouble(Double.doubleToRawLongBits(TrigonometryConstants.TAU) + 1);

    /**
     * The {@code double} value that is closer than any other value to half of
     * <i>pi</i> (&pi;), the ratio of the circumference of a circle to
     * its diameter.
     *
     * @see TrigonometryConstants#PI
     */
    @SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
    public static final double HALF_PI = BigDecimal.valueOf(PI).divide(BigDecimal.valueOf(2)).doubleValue();

    /**
     * The {@code double} value that is closer than any other superior value to half of
     * <i>pi</i> (&pi;), the ratio of the circumference of a circle to
     * its diameter.
     *
     * @see TrigonometryConstants#HALF_PI
     */
    public static final double HALF_PI_SUP = Double.longBitsToDouble(Double.doubleToRawLongBits(TrigonometryConstants.HALF_PI) + 1);

    //Polynomial defs for atan2 taken from https://mazzo.li/posts/vectorized-atan2.html
    protected static final double a1 = 0.99997726;
    protected static final double a3 = -0.33262347;
    protected static final double a5 = 0.19354346;
    protected static final double a7 = -0.11643287;
    protected static final double a9 = 0.05265332;
    protected static final double a11 = -0.01172120;

}
