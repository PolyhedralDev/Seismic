package com.dfsek.seismic;

/**
 * Provides 2D, 3D, and 4D noise values.
 * <p>
 * Samplers are assumed to be:
 * <ul>
 *     <li> Stateless: Getting noise values with {@link #getNoise}
 *     shall return the same value for the same coordinate and seed.
 *     <li> Random-Access: Getting noise values at any point shall take
 *     the same amount of time, regardless of distance from the origin.
 *     <li> Bounded from {@code [-1, 1]}: Samplers <i>should</i> maintain
 *     output bounding in the range {@code [-1, 1]}. <b>this behavior
 *     is not guaranteed.</b>
 */
public interface NoiseSampler4 extends NoiseSampler {

    /**
     * Get 3D noise at the given coordinates, using the given seed.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param z Z coordinate
     * @param w W coordinate
     * @return 3D noise value at coordinates.
     */
    double getNoiseSeeded(int seed, double x, double y, double z, double w);

    /**
     * Get 4D noise at the given coordinates.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param z Z coordinate
     * @param w W coordinate
     * @return 3D noise value at coordinates.
     */
    double getNoise(double x, double y, double z, double w);
}
