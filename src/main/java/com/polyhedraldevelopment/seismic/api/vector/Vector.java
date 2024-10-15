package com.polyhedraldevelopment.seismic.api.vector;

import com.polyhedraldevelopment.seismic.api.type.Rotation;
import org.jetbrains.annotations.NotNull;


public interface Vector<T extends Vector<T>> {
    /**
     * Returns a vector with all components set to zero.
     *
     * @return a zero vector
     */
    @NotNull T  zero();

    /**
     * Returns a unit vector (a vector with a length of 1).
     *
     * @return a unit vector
     */
    @NotNull T unit();

    /**
     * Returns the length (magnitude) of the vector.
     *
     * @return the length of the vector
     */
    double length();

    /**
     * Returns the squared length of the vector.
     * This is more efficient than calculating the length directly.
     *
     * @return the squared length of the vector
     */
    double lengthSquared();

    /**
     * Returns the inverse of the length of the vector.
     *
     * @return the inverse length of the vector
     */
    double inverseLength();

    /**
     * Returns the squared inverse length of the vector.
     *
     * @return the squared inverse length of the vector
     */
    double inverseLengthSquared();

    /**
     * Returns the distance between this vector and the specified vector.
     *
     * @param vector the vector to calculate the distance to
     * @return the distance between the two vectors
     */
    double distance(@NotNull T vector);

    /**
     * Returns the squared distance between this vector and the specified vector.
     *
     * @param vector the vector to calculate the squared distance to
     * @return the squared distance between the two vectors
     */
    double distanceSquared(@NotNull T vector);

    /**
     * Calculates the dot product of this vector and the specified vector.
     *
     * @param vector the vector to dot with
     * @return the dot product of the two vectors
     */
    double dot(@NotNull T vector);

    /**
     * Creates and returns a copy of this vector.
     *
     * @return a copy of this vector
     */
    @NotNull T copy();

    /**
     * Returns a mutable version of this vector.
     *
     * @return a mutable version of this vector
     */
    @NotNull Mutable<T> mutable();

    /**
     * Interface for mutable vectors.
     *
     * @param <T> the type of the vector
     */
    interface Mutable<T extends Vector<T>> extends Vector<T> {

        /**
         * Normalizes the vector (makes it a unit vector).
         *
         * @return the normalized vector
         */
        @NotNull Mutable<T> normalize();

        /**
         * Rotates the vector by the specified rotation.
         *
         * @param rotation the rotation to apply
         * @return the rotated vector
         */
        @NotNull Mutable<T> rotate(@NotNull Rotation rotation);

        /**
         * Returns an immutable version of this vector.
         *
         * @return an immutable version of this vector
         */
        @NotNull T immutable();
    }
}
