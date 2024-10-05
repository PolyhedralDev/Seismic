/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the common/api directory.
 */

package com.dfsek.seismic.algorithms.samplers;


import  com.dfsek.seismic.types.vector.Vector2;
import  com.dfsek.seismic.types.vector.Vector2Int;
import  com.dfsek.seismic.types.vector.Vector3;
import  com.dfsek.seismic.types.vector.Vector3Int;

public interface NoiseSampler {
    static NoiseSampler zero() {
        return new NoiseSampler() {
            @Override
            public double getNoise(long seed, double x, double y) {
                return 0;
            }

            @Override
            public double getNoise(long seed, double x, double y, double z) {
                return 0;
            }
        };
    }

    /**
     * Get 3D noise at the given {@code Vector3} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector3 the coordinates.
     * @return 3D noise value at coordinates.
     */
    default double getNoise(long seed, Vector3 vector3) {
        return getNoise(seed, vector3.getX(), vector3.getY(), vector3.getZ());
    }

    /**
     * Get 3D noise at the given {@code Vector3Int} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector3 the coordinates.
     * @return 3D noise value at coordinates.
     */
    default double getNoise(long seed, Vector3Int vector3) {
        return getNoise(seed, vector3.getX(), vector3.getY(), vector3.getZ());
    }

    /**
     * Get 2D noise at the given {@code Vector2} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector2 the coordinates.
     * @return 2D noise value at coordinates.
     */
    default double getNoise(long seed, Vector2 vector2) {
        return getNoise(seed, vector2.getX(), vector2.getZ());
    }

    /**
     * Get 2D noise at the given {@code Vector2Int} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector2 the coordinates.
     * @return 2D noise value at coordinates.
     */
    default double getNoise(long seed, Vector2Int vector2) {
        return getNoise(seed, vector2.getX(), vector2.getZ());
    }

    /**
     * Get 2D noise at the given {@code double} coordinates, using the given seed.
     *
     * @param seed a seed.
     * @param x    X coordinate.
     * @param y    Y coordinate.
     * @return 3D noise value at coordinates.
     */
    double getNoise(long seed, double x, double y);

    /**
     * Get 2D noise at the given {@code int} coordinates, using the given seed.
     *
     * @param seed a seed.
     * @param x    X coordinate.
     * @param y    Y coordinate.
     * @return 2D noise value at coordinates.
     */
    default double getNoise(long seed, int x, int y) {
        return getNoise(seed, (double) x, y);
    }

    /**
     * Get 3D noise at the given {@code double} coordinates, using the given seed.
     *
     * @param seed a seed.
     * @param x    X coordinate.
     * @param y    Y coordinate.
     * @param z    Z coordinate
     * @return 3D noise value at coordinates.
     */
    double getNoise(long seed, double x, double y, double z);

    /**
     * Get 3D noise at the given {@code int} coordinates, using the given seed.
     *
     * @param seed a seed.
     * @param x    X coordinate.
     * @param y    Y coordinate.
     * @param z    Z coordinate
     * @return 3D noise value at coordinates.
     */
    default double getNoise(long seed, int x, int y, int z) {
        return getNoise(seed, (double) x, y, z);
    }
}
