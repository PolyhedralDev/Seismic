package com.dfsek.seismic.function.random;


import com.dfsek.seismic.NoiseSampler4;
import com.dfsek.seismic.function.NoiseFunction;

import static com.dfsek.seismic.util.MathHelper.murmur64;

/**
 * NoiseSampler implementation to produce random, uniformly distributed (white) noise.
 *
 * @author dfsek
 */
public class WhiteNoiseSampler extends NoiseFunction implements NoiseSampler4 {
    private static final long POSITIVE_POW1 = 0b01111111111L << 52; // Bits that when applied to the exponent/sign section of a double, produce a positive number with a power of 1.

    public WhiteNoiseSampler(int seed) {
        super(seed);
    }

    public double getNoiseRaw(long seed) {
        return (Double.longBitsToDouble((murmur64(seed) & 0x000fffffffffffffL) | POSITIVE_POW1) - 1.5) * 2;
    }

    @Override
    public double getNoiseRaw(int seed, double x, double y) {
        return (getNoiseUnmapped(seed, x, y) - 1.5) * 2;
    }

    @Override
    public double getNoiseRaw(int seed, double x, double y, double z) {
        return (getNoiseUnmapped(seed, x, y, z) - 1.5) * 2;
    }

    public double getNoiseUnmapped(int seed, double x, double y, double z, double w) {
        long base = ((randomBits(seed, x, y, z, w)) & 0x000fffffffffffffL) | POSITIVE_POW1; // Sign and exponent
        return Double.longBitsToDouble(base);
    }

    public double getNoiseUnmapped(int seed, double x, double y, double z) {
        long base = ((randomBits(seed, x, y, z)) & 0x000fffffffffffffL) | POSITIVE_POW1; // Sign and exponent
        return Double.longBitsToDouble(base);
    }

    public double getNoiseUnmapped(int seed, double x, double y) {
        long base = (randomBits(seed, x, y) & 0x000fffffffffffffL) | POSITIVE_POW1; // Sign and exponent
        return Double.longBitsToDouble(base);
    }

    public long randomBits(int seed, double x, double y, double z, double w) {
        long hashX = Double.doubleToRawLongBits(x) ^ seed;
        long hashZ = Double.doubleToRawLongBits(y) ^ seed;
        long hash = (((hashX ^ (hashX >>> 32)) + ((hashZ ^ (hashZ >>> 32)) << 32)) ^ seed) + (Double.doubleToRawLongBits(z) ^ Double.doubleToRawLongBits(w));
        return murmur64(hash);
    }

    public long randomBits(int seed, double x, double y, double z) {
        long hashX = Double.doubleToRawLongBits(x) ^ seed;
        long hashZ = Double.doubleToRawLongBits(y) ^ seed;
        long hash = (((hashX ^ (hashX >>> 32)) + ((hashZ ^ (hashZ >>> 32)) << 32)) ^ seed) + Double.doubleToRawLongBits(z);
        return murmur64(hash);
    }

    public long randomBits(int seed, double x, double y) {
        long hashX = Double.doubleToRawLongBits(x) ^ seed;
        long hashZ = Double.doubleToRawLongBits(y) ^ seed;
        long hash = ((hashX ^ (hashX >>> 32)) + ((hashZ ^ (hashZ >>> 32)) << 32)) ^ seed;
        return murmur64(hash);
    }

    @Override
    public double getNoiseSeeded(int seed, double x, double y, double z, double w) {
        return (getNoiseUnmapped(seed, x, y, z, w) - 1.5) * 2;
    }

    @Override
    public double getNoise(double x, double y, double z, double w) {
        return (getNoiseUnmapped(seed, x, y, z, w) - 1.5) * 2;
    }
}
