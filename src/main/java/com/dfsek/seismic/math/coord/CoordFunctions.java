package com.dfsek.seismic.math.coord;

import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;


public class CoordFunctions {
    /**
     * Converts an absolute {@code int} coordinate to a region coordinate.
     *
     * @param absolute the absolute coordinate.
     * @return the region coordinate.
     */
    public static int absoluteToRegion(int absolute) {
        return absolute >> 9;
    }

    /**
     * Converts an absolute {@code long} coordinate to a region coordinate.
     *
     * @param absolute the absolute coordinate.
     * @return the region coordinate.
     */
    public static long absoluteToRegion(long absolute) {
        return absolute >> 9;
    }

    /**
     * Converts an absolute {@code double} coordinate to a region coordinate.
     *
     * @param absolute the absolute coordinate.
     * @return the region coordinate.
     */
    public static int absoluteToRegion(double absolute) {
        return FloatingPointFunctions.floor(absolute / 512.0);
    }

    /**
     * Converts an absolute {@code int} coordinate to a chunk coordinate.
     *
     * @param absolute the absolute coordinate.
     * @return the chunk coordinate.
     */
    public static int absoluteToChunk(int absolute) {
        return absolute >> 4;
    }

    /**
     * Converts an absolute {@code long} coordinate to a chunk coordinate.
     *
     * @param absolute the absolute coordinate.
     * @return the chunk coordinate.
     */
    public static long absoluteToChunk(long absolute) {
        return absolute >> 4;
    }

    /**
     * Converts an absolute {@code double} coordinate to a chunk coordinate.
     *
     * @param absolute the absolute coordinate.
     * @return the chunk coordinate.
     */
    public static int absoluteToChunk(double absolute) {
        return FloatingPointFunctions.floor(absolute / 16.0);
    }

    /**
     * Converts an absolute {@code int} coordinate to a chunk-relative coordinate.
     *
     * @param absolute the absolute coordinate.
     * @return the chunk-relative coordinate.
     */
    public static int absoluteToChunkRelative(int absolute) {
        return absolute % 16;
    }

    /**
     * Converts an absolute {@code long} coordinate to a chunk-relative coordinate.
     *
     * @param absolute the absolute coordinate.
     * @return the chunk-relative coordinate.
     */
    public static long absoluteToChunkRelative(long absolute) {
        return absolute % 16;
    }

    /**
     * Converts an absolute {@code double} coordinate to a chunk-relative coordinate.
     *
     * @param absolute the absolute coordinate.
     * @return the chunk-relative coordinate.
     */
    public static double absoluteToChunkRelative(double absolute) {
        return absolute % 16;
    }

    /**
     * Converts a region {@code int} coordinate to an absolute coordinate.
     *
     * @param region the region coordinate.
     * @return the absolute coordinate.
     */
    public static int regionToAbsolute(int region) {
        return region << 9;
    }

    /**
     * Converts a region {@code long} coordinate to an absolute coordinate.
     *
     * @param region the region coordinate.
     * @return the absolute coordinate.
     */
    public static long regionToAbsolute(long region) {
        return region << 9;
    }

    /**
     * Converts region and chunk {@code int} coordinates to an absolute coordinate.
     *
     * @param region the region coordinate.
     * @param chunk the chunk coordinate.
     * @return the absolute coordinate.
     */
    public static int regionAndChunkToAbsolute(int region, int chunk) {
        return (region << 9) + (chunk << 4);
    }

    /**
     * Converts region and chunk {@code long} coordinates to an absolute coordinate.
     *
     * @param region the region coordinate.
     * @param chunk the chunk coordinate.
     * @return the absolute coordinate.
     */
    public static long regionAndChunkToAbsolute(long region, long chunk) {
        return (region << 9) + (chunk << 4);
    }

    /**
     * Converts region, chunk, and relative {@code int} coordinates to an absolute coordinate.
     *
     * @param region the region coordinate.
     * @param chunk the chunk coordinate.
     * @param relative the relative coordinate.
     * @return the absolute coordinate.
     */
    public static int regionChunkAndRelativeToAbsolute(int region, int chunk, int relative) {
        return (region << 9) + (chunk << 4) + relative;
    }

    /**
     * Converts region, chunk, and relative {@code long} coordinates to an absolute coordinate.
     *
     * @param region the region coordinate.
     * @param chunk the chunk coordinate.
     * @param relative the relative coordinate.
     * @return the absolute coordinate.
     */
    public static long regionChunkAndRelativeToAbsolute(long region, long chunk, long relative) {
        return (region << 9) + (chunk << 4) + relative;
    }

    /**
     * Converts a chunk {@code int} coordinate to an absolute coordinate.
     *
     * @param chunk the chunk coordinate.
     * @return the absolute coordinate.
     */
    public static int chunkToAbsolute(int chunk) {
        return chunk << 4;
    }

    /**
     * Converts a chunk {@code long} coordinate to an absolute coordinate.
     *
     * @param chunk the chunk coordinate.
     * @return the absolute coordinate.
     */
    public static long chunkToAbsolute(long chunk) {
        return chunk << 4;
    }

    /**
     * Converts chunk and relative {@code int} coordinates to an absolute coordinate.
     *
     * @param chunk the chunk coordinate.
     * @param relative the relative coordinate.
     * @return the absolute coordinate.
     */
    public static int chunkAndRelativeToAbsolute(int chunk, int relative) {
        return (chunk << 4) + relative;
    }

    /**
     * Converts chunk and relative {@code long} coordinates to an absolute coordinate.
     *
     * @param chunk the chunk coordinate.
     * @param relative the relative coordinate.
     * @return the absolute coordinate.
     */
    public static long chunkAndRelativeToAbsolute(long chunk, long relative) {
        return (chunk << 4) + relative;
    }
}
