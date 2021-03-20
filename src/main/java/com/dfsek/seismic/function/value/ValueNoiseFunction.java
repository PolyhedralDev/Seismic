package com.dfsek.seismic.function.value;


import com.dfsek.seismic.function.NoiseFunction;

import static com.dfsek.seismic.util.MathHelper.hash;

public abstract class ValueNoiseFunction extends NoiseFunction {
    public ValueNoiseFunction(int seed) {
        super(seed);
    }

    protected static double valCoord(int seed, int xPrimed, int yPrimed) {
        int hash = hash(seed, xPrimed, yPrimed);

        hash *= hash;
        hash ^= hash << 19;
        return hash * (1 / 2147483648.0);
    }

    protected static double valCoord(int seed, int xPrimed, int yPrimed, int zPrimed) {
        int hash = hash(seed, xPrimed, yPrimed, zPrimed);

        hash *= hash;
        hash ^= hash << 19;
        return hash * (1 / 2147483648.0);
    }
}
