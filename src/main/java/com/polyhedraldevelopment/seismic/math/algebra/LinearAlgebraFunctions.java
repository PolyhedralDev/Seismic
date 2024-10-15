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

    /**
     * Calculates the dot product of two 2D vectors with integer coordinates.
     *
     * @param x1 the x-coordinate of the first vector.
     * @param y1 the y-coordinate of the first vector.
     * @param x2 the x-coordinate of the second vector.
     * @param y2 the y-coordinate of the second vector.
     *
     * @return the dot product of the two vectors.
     */
    public static int dotProduct(int x1, int y1, int x2, int y2) {
        return x1 * x2 + y1 * y2;
    }

    /**
     * Calculates the dot product of two 3D vectors with double coordinates.
     *
     * @param x1 the x-coordinate of the first vector.
     * @param y1 the y-coordinate of the first vector.
     * @param z1 the z-coordinate of the first vector.
     * @param x2 the x-coordinate of the second vector.
     * @param y2 the y-coordinate of the second vector.
     * @param z2 the z-coordinate of the second vector.
     *
     * @return the dot product of the two vectors.
     */
    public static double dotProduct(double x1, double y1, double z1, double x2, double y2, double z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    /**
     * Calculates the dot product of two 3D vectors with integer coordinates.
     *
     * @param x1 the x-coordinate of the first vector.
     * @param y1 the y-coordinate of the first vector.
     * @param z1 the z-coordinate of the first vector.
     * @param x2 the x-coordinate of the second vector.
     * @param y2 the y-coordinate of the second vector.
     * @param z2 the z-coordinate of the second vector.
     *
     * @return the dot product of the two vectors.
     */
    public static int dotProduct(int x1, int y1, int z1, int x2, int y2, int z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }
}
