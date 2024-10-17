package com.dfsek.seismic.type.sampler;

import com.dfsek.seismic.type.vector.Vector2;
import com.dfsek.seismic.type.vector.Vector2Int;
import com.dfsek.seismic.type.vector.Vector3;
import com.dfsek.seismic.type.vector.Vector3Int;
import org.jetbrains.annotations.NotNull;


/**
 * A NoiseSampler which additionally may provide a 1st directional derivative
 */
public interface DerivativeSampler extends Sampler {

    static boolean isDifferentiable(Sampler sampler) {
        return sampler instanceof DerivativeSampler dSampler && dSampler.isDifferentiable();
    }

    /**
     * Samplers may or may not be able to provide a derivative depending on what
     * inputs they take, this method signals whether this is the case.
     *
     * @return If the noise sampler provides a derivative or not
     */
    boolean isDifferentiable();

    /**
     * Get 3D noise at the given {@code Vector3} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector3 the coordinates.
     *
     * @return 4 element array, in index order: 3D noise value, partial x derivative, partial y derivative, partial z derivative
     */
    default double[] getSampleDerivative(long seed, @NotNull Vector3 vector3) {
        return getSampleDerivative(seed, vector3.getX(), vector3.getY(), vector3.getZ());
    }

    /**
     * Get 3D noise at the given {@code Vector3Int} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector3 the coordinates.
     *
     * @return 4 element array, in index order: 3D noise value, partial x derivative, partial y derivative, partial z derivative
     */
    default double[] getSampleDerivative(long seed, @NotNull Vector3Int vector3) {
        return getSampleDerivative(seed, vector3.getX(), vector3.getY(), vector3.getZ());
    }

    /**
     * Get 2D noise at the given {@code Vector2} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector2 the coordinates.
     *
     * @return 3 element array, in index order: 2D noise value, partial x derivative, partial y derivative
     */
    default double[] getSampleDerivative(long seed, @NotNull Vector2 vector2) {
        return getSampleDerivative(seed, vector2.getX(), vector2.getZ());
    }

    /**
     * Get 2D noise at the given {@code Vector2Int} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector2 the coordinates.
     *
     * @return 3 element array, in index order: 2D noise value, partial x derivative, partial y derivative
     */
    default double[] getSampleDerivative(long seed, @NotNull Vector2Int vector2) {
        return getSampleDerivative(seed, vector2.getX(), vector2.getZ());
    }

    /**
     * Derivative return version of standard 2D noise evaluation
     *
     * @param seed a seed.
     * @param x    X coordinate.
     * @param y    Y coordinate.
     *
     * @return 3 element array, in index order: 2D noise value, partial x derivative, partial y derivative
     */
    double[] getSampleDerivative(long seed, double x, double y);

    /**
     * Derivative return version of standard 3D noise evaluation
     *
     * @param seed a seed.
     * @param x    X coordinate.
     * @param y    Y coordinate.
     * @param z    Z coordinate
     *
     * @return 4 element array, in index order: 3D noise value, partial x derivative, partial y derivative, partial z derivative
     */
    double[] getSampleDerivative(long seed, double x, double y, double z);
}
