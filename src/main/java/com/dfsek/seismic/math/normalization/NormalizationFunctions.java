package com.dfsek.seismic.math.normalization;

public class NormalizationFunctions {
    /**
     * Returns the normalized index based on the size.
     *
     * @param val  the value to be normalized.
     * @param size the size to normalize the value against.
     * @return the normalized index.
     */
    public static int normalizeIndex(double val, int size) {
        return Math.max(Math.min((int) Math.floor(((val + 1D) / 2D) * size), size - 1), 0);
    }

    /**
     * Returns the clamped value to range of [-1, 1].
     *
     * @param in the value to clamp.
     * @return the clamped value.
     */
    public static double clamp(double in) {
        return Math.min(Math.max(in, -1), 1);
    }

    /**
     * Returns the clamped value to range of [{@code min}, {@code max}].
     *
     * @param min the minimum value.
     * @param max the maximum value.
     * @param i   the input value.
     * @return the clamped value.
     */
    public static double clamp(double min, double max, double i) {
        return Math.max(Math.min(i, max), min);
    }

    /**
     * Returns the clamped value to range of [{@code min}, {@code max}].
     *
     * @param min the minimum value.
     * @param max the maximum value.
     * @param i   the input value.
     * @return the clamped value.
     */
    public static int clamp(int min, int max, int i) {
        return Math.max(Math.min(i, max), min);
    }
}
