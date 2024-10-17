package com.dfsek.seismic.algorithms.hashing;

public class HashingFunctions {
    /**
     * Returns a {@code long} hash code of a string.
     *
     * @param s a string.
     *
     * @return the hashcode of the string.
     */
    public static long hashString(String s) {
        if(s == null) {
            return 0;
        }

        long hash = 0;
        for(char c : s.toCharArray()) {
            hash = 31L * hash + c;
        }
        return hash;
    }

    /**
     * Computes a hash code for the given seed and primed coordinates (x, y).
     * The hash code is computed by XORing the seed and the primed coordinates, and then multiplying the result by a prime number.
     *
     * @param seed    the seed for the hash function.
     * @param xPrimed the primed x-coordinate.
     * @param yPrimed the primed y-coordinate.
     *
     * @return the computed hash code.
     */
    public static int hashPrimeCoords(int seed, int xPrimed, int yPrimed) {
        int hash = seed ^ xPrimed ^ yPrimed;
        hash *= 0x27d4eb2d;
        return hash;
    }

    /**
     * Computes a hash code for the given seed and primed coordinates (x, y, z).
     *
     * @param seed    the seed for the hash function.
     * @param xPrimed the primed x-coordinate.
     * @param yPrimed the primed y-coordinate.
     * @param zPrimed the primed z-coordinate.
     *
     * @return the computed hash code.
     */
    public static int hashPrimeCoords(int seed, int xPrimed, int yPrimed, int zPrimed) {
        int hash = seed ^ xPrimed ^ yPrimed ^ zPrimed;
        hash *= 0x27d4eb2d;
        return hash;
    }


    /**
     * Returns the Murmur64 hash of a {@code long} value.
     *
     * @param h a value.
     *
     * @return Murmur64 hash.
     */
    public static long murmur64(long h) {
        h ^= h >>> 33;
        h *= 0xff51afd7ed558ccdL;
        h ^= h >>> 33;
        h *= 0xc4ceb9fe1a85ec53L;
        h ^= h >>> 33;
        return h;
    }
}
