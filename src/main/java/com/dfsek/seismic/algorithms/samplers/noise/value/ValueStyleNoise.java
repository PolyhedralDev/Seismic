/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.samplers.noise.value;


import  com.dfsek.seismic.algorithms.hashing.HashingFunctions;
import  com.dfsek.seismic.algorithms.samplers.noise.NoiseFunction;

public abstract class ValueStyleNoise extends NoiseFunction {

    protected static double valCoord(int seed, int xPrimed, int yPrimed) {
        int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed);

        hash *= hash;
        hash ^= hash << 19;
        return hash * (1 / 2147483648.0);
    }

    protected static double valCoord(int seed, int xPrimed, int yPrimed, int zPrimed) {
        int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed, zPrimed);

        hash *= hash;
        hash ^= hash << 19;
        return hash * (1 / 2147483648.0);
    }
}
