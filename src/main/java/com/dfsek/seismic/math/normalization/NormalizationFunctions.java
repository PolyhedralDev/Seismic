package com.dfsek.seismic.math.normalization;

import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;


public class NormalizationFunctions {
    /**
     * Returns the normalized index based on the size.
     *
     * @param val  the value to be normalized.
     * @param size the size to normalize the value against.
     *
     * @return the normalized index.
     */
    public static int normalizeIndex(double val, int size) {
        return Math.max(Math.min(FloatingPointFunctions.floor(((val + 1D) / 2D) * size), size - 1), 0);
    }

    /**
     * Returns the clamped value to range of [-1, 1].
     *
     * @param in the value to clamp.
     *
     * @return the clamped value.
     */
    public static double clamp(double in) {
        return Math.min(Math.max(in, -1), 1);
    }

    /**
     * Returns the clamped value to range of [{@code min}, {@code max}].
     *
     * @param i   the input value.
     * @param min the minimum value.
     * @param max the maximum value.
     *
     * @return the clamped value.
     */
    public static double clamp(double i, double min, double max) {
        return Math.max(Math.min(i, max), min);
    }

    /**
     * Returns the clamped value to range of [{@code min}, {@code max}].
     *
     * @param i   the input value.
     * @param min the minimum value.
     * @param max the maximum value.
     *
     * @return the clamped value.
     */
    public static int clamp(int i, int min, int max) {
        return Math.max(Math.min(i, max), min);
    }

    /**
     * Returns the normalized value to the range of [0, 1].
     *
     * @param x     the value to be normalized.
     * @param edge0 the lower bound of the range.
     * @param edge1 the upper bound of the range.
     *
     * @return the normalized value.
     */
    public static double normalizeToRange(double edge0, double edge1, double x) {
        // Scale, bias and saturate x to 0..1 range
        return NormalizationFunctions.clamp((x - edge0) / (edge1 - edge0), 0.0f, 1.0f);
    }
}
