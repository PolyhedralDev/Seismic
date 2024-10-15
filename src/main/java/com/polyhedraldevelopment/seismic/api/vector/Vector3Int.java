package com.polyhedraldevelopment.seismic.api.vector;

import org.jetbrains.annotations.NotNull;


public interface Vector3Int<T extends Vector3Int<T, U>, U extends Vector3<?, ?>> extends Vector<T> {
    /**
     * Returns the X coordinate of the vector.
     *
     * @return the X coordinate
     */
    int getX();

    /**
     * Returns the Y coordinate of the vector.
     *
     * @return the Y coordinate
     */
    int getY();

    /**
     * Returns the Z coordinate of the vector.
     *
     * @return the Z coordinate
     */
    int getZ();

    /**
     * Converts the vector to a floating-point vector.
     *
     * @return the floating-point vector
     */
    @NotNull U toFloat();

    /**
     * Interface for mutable 3D integer vectors.
     *
     * @param <T> the type of the vector
     */
    interface Mutable<T extends Vector3Int<T, U>, U extends Vector3<?, ?>> extends Vector.Mutable<T>, Vector3Int<T, U> {

        /**
         * Sets the X, Y, and Z coordinates of the vector.
         *
         * @param x the X coordinate to set
         * @param y the Y coordinate to set
         * @param z the Z coordinate to set
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> set(int x, int y, int z);

        /**
         * Sets the X coordinate of the vector.
         *
         * @param x the X coordinate to set
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> setX(int x);

        /**
         * Sets the Y coordinate of the vector.
         *
         * @param y the Y coordinate to set
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> setY(int y);

        /**
         * Sets the Z coordinate of the vector.
         *
         * @param z the Z coordinate to set
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> setZ(int z);

        /**
         * Adds the specified X, Y, and Z coordinates to the vector.
         *
         * @param x the X coordinate to add
         * @param y the Y coordinate to add
         * @param z the Z coordinate to add
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> add(int x, int y, int z);

        /**
         * Adds the specified vector to this vector.
         *
         * @param vector the vector to add
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> add(@NotNull Vector3Int<?, ?> vector);

        /**
         * Adds the specified scalar to the X, Y, and Z coordinates of the vector.
         *
         * @param scalar the scalar to add
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> addScalar(int scalar);

        /**
         * Subtracts the specified X, Y, and Z coordinates from the vector.
         *
         * @param x the X coordinate to subtract
         * @param y the Y coordinate to subtract
         * @param z the Z coordinate to subtract
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> sub(int x, int y, int z);

        /**
         * Subtracts the specified vector from this vector.
         *
         * @param vector the vector to subtract
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> sub(@NotNull Vector3Int<?, ?> vector);

        /**
         * Subtracts the specified scalar from the X, Y, and Z coordinates of the vector.
         *
         * @param scalar the scalar to subtract
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> subScalar(int scalar);

        /**
         * Multiplies the vector by the specified X, Y, and Z coordinates.
         *
         * @param x the X coordinate to multiply by
         * @param y the Y coordinate to multiply by
         * @param z the Z coordinate to multiply by
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> mul(int x, int y, int z);

        /**
         * Multiplies the vector by the specified vector.
         *
         * @param vector the vector to multiply by
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> mul(@NotNull Vector3Int<?, ?> vector);

        /**
         * Multiplies the vector by the specified scalar.
         *
         * @param scalar the scalar to multiply by
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> mulScalar(int scalar);

        /**
         * Divides the vector by the specified X, Y, and Z coordinates.
         *
         * @param x the X coordinate to divide by
         * @param y the Y coordinate to divide by
         * @param z the Z coordinate to divide by
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> div(int x, int y, int z);

        /**
         * Divides the vector by the specified vector.
         *
         * @param vector the vector to divide by
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> div(@NotNull Vector3Int<?, ?> vector);

        /**
         * Divides the vector by the specified scalar.
         *
         * @param scalar the scalar to divide by
         * @return the updated vector
         */
        @NotNull Vector3Int.Mutable<T, U> divScalar(int scalar);
    }
}
