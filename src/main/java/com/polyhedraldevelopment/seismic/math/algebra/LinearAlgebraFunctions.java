package com.polyhedraldevelopment.seismic.math.algebra;

public class LinearAlgebraFunctions {
    /**
     * Calculates the dot product of two 2D vectors.
     *
     * @param x1 the x-coordinate of the first vector.
     * @param y1 the y-coordinate of the first vector.
     * @param x2 the x-coordinate of the second vector.
     * @param y2 the y-coordinate of the second vector.
     *
     * @return the dot product of the two vectors.
     */
    public static double dotProduct(double x1, double y1, double x2, double y2) {
        return x1 * x2 + y1 * y2;
    }
}
