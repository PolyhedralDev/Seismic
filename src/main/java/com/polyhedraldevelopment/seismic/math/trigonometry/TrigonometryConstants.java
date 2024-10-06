package com.polyhedraldevelopment.seismic.math.trigonometry;

public class TrigonometryConstants {
    /**
     * The {@code double} value that is closer than any other to
     * <i>pi</i> (&pi;), the ratio of the circumference of a circle to
     * its diameter.
     */
    public static final double PI = Math.PI;

    /**
     * The {@code double} value that is closer than any other superior value to
     * <i>pi</i> (&pi;), the ratio of the circumference of a circle to
     * its diameter.
     */
    public static final double PI_SUP = Double.longBitsToDouble(Double.doubleToRawLongBits(PI) + 1);

    /**
     * The {@code double} value that is closer than any other to
     * <i>tau</i> (&tau;), the ratio of the circumference of a circle
     * to its radius.
     *
     * @apiNote The value of <i>pi</i> is one half that of <i>tau</i>; in other
     *     words, <i>tau</i> is double <i>pi</i> .
     */
    public static final double TAU = Math.TAU;

    /**
     * The {@code double} value that is closer than any other superior value to
     * <i>tau</i> (&tau;), the ratio of the circumference of a circle
     * to its radius.
     *
     * @apiNote The value of <i>pi</i> is one half that of <i>tau</i>; in other
     *     words, <i>tau</i> is double <i>pi</i> .
     */
    public static final double TAU_SUP = Double.longBitsToDouble(Double.doubleToRawLongBits(TAU) + 1);
}
