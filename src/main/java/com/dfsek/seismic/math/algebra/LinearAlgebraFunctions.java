package com.dfsek.seismic.math.algebra;


import static com.dfsek.seismic.math.algebra.LinearAlgebraUtils.HYPOT_FACTOR;
import static com.dfsek.seismic.math.algebra.LinearAlgebraUtils.HYPOT_MAX_MAG;
import static com.dfsek.seismic.math.algebra.LinearAlgebraUtils.hypot_NaN;


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

    /**
     * Returns {@code sqrt(x*x + y*y)}.
     *
     * @param x a coordinate
     * @param y a coordinate
     *
     * @return the non-negative Euclidean norm, {@code sqrt(x*x + y*y)}
     */
    public static double hypot(double x, double y) {
        x = Math.abs(x);
        y = Math.abs(y);
        // Ensuring x <= y.
        if(y < x) {
            double a = x;
            x = y;
            y = a;
        } else if(!(y >= x)) { // Testing if we have some NaN.
            return hypot_NaN(x, y);
        }

        if(y - x == y) {
            // x too small to subtract from y.
            return y;
        } else {
            double factor;
            if(y > HYPOT_MAX_MAG) {
                // y is too large: scaling down.
                x *= (1 / HYPOT_FACTOR);
                y *= (1 / HYPOT_FACTOR);
                factor = HYPOT_FACTOR;
            } else if(x < (1 / HYPOT_MAX_MAG)) {
                // x is too small: scaling up.
                x *= HYPOT_FACTOR;
                y *= HYPOT_FACTOR;
                factor = (1 / HYPOT_FACTOR);
            } else {
                factor = 1.0;
            }
            return factor * Math.sqrt(x * x + y * y);
        }
    }

    /**
     * Returns {@code sqrt(x*x + y*y + z*z)}.
     *
     * @param x a coordinate
     * @param y a coordinate
     * @param z a coordinate
     *
     * @return the non-negative Euclidean norm, {@code sqrt(x*x + y*y + z*z)}
     */
    public static double hypot(double x, double y, double z) {
        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);
        /*
         * Considering that z magnitude is the most likely to be the smaller,
         * hence ensuring z <= y <= x, and not x <= y <= z, for less swaps.
         */
        // Ensuring z <= y.
        if(z > y) {
            // y < z: swapping y and z
            double a = z;
            z = y;
            y = a;
        } else if(!(z <= y)) { // Testing if y or z is NaN.
            return hypot_NaN(x, y, z);
        }
        // Ensuring y <= x.
        if(z > x) {
            // x < z <= y: moving x
            double oldZ = z;
            z = x;
            double oldY = y;
            y = oldZ;
            x = oldY;
        } else if(y > x) {
            // z <= x < y: swapping x and y
            double a = y;
            y = x;
            x = a;
        } else if(x != x) { // Testing if x is NaN.
            return hypot_NaN(x, y, z);
        }

        if(x - y == x) {
            // y, hence z, too small to subtract from x.
            return x;
        } else if(y - z == y) {
            // z too small to subtract from y, hence x.
            double factor;
            if(x > HYPOT_MAX_MAG) {
                // x is too large: scaling down.
                x *= (1 / HYPOT_FACTOR);
                y *= (1 / HYPOT_FACTOR);
                factor = HYPOT_FACTOR;
            } else if(y < (1 / HYPOT_MAX_MAG)) {
                // y is too small: scaling up.
                x *= HYPOT_FACTOR;
                y *= HYPOT_FACTOR;
                factor = (1 / HYPOT_FACTOR);
            } else {
                factor = 1.0;
            }
            return factor * Math.sqrt(x * x + y * y);
        } else {
            double factor;
            if(x > HYPOT_MAX_MAG) {
                // x is too large: scaling down.
                x *= (1 / HYPOT_FACTOR);
                y *= (1 / HYPOT_FACTOR);
                z *= (1 / HYPOT_FACTOR);
                factor = HYPOT_FACTOR;
            } else if(z < (1 / HYPOT_MAX_MAG)) {
                // z is too small: scaling up.
                x *= HYPOT_FACTOR;
                y *= HYPOT_FACTOR;
                z *= HYPOT_FACTOR;
                factor = (1 / HYPOT_FACTOR);
            } else {
                factor = 1.0;
            }
            // Adding smaller magnitudes together first.
            return factor * Math.sqrt(x * x + (y * y + z * z));
        }
    }
}
