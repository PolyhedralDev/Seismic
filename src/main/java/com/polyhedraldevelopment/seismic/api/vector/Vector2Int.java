package com.polyhedraldevelopment.seismic.api.vector;

import org.jetbrains.annotations.NotNull;


public interface Vector2Int<T extends Vector2Int<T, U, V>, U extends Vector3Int<?, ?>, V extends Vector2<?, ?, ?>> extends Vector<T> {
    /**
     * Returns the X coordinate of the vector.
     *
     * @return the X coordinate
     */
    int getX();

    /**
     * Returns the Z coordinate of the vector.
     *
     * @return the Z coordinate
     */
    int getZ();

    /**
     * Extrudes the vector to a 3D vector by adding the specified Y coordinate.
     *
     * @param y the Y coordinate to add
     * @return the extruded 3D vector
     */
    @NotNull U extrude(int y);

    /**
     * Converts the vector to a floating-point vector.
     *
     * @return the floating-point vector
     */
    @NotNull V toFloat();

    /**
     * Interface for mutable 2D integer vectors.
     *
     * @param <T> the type of the vector
     */
    interface Mutable<T extends Vector2Int<T, U, V>, U extends Vector3Int<?, ?>, V extends Vector2<?, ?, ?>> extends Vector.Mutable<T>, Vector2Int<T, U, V> {

        /**
         * Sets the X and Z coordinates of the vector.
         *
         * @param x the X coordinate to set
         * @param z the Z coordinate to set
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> set(int x, int z);

        /**
         * Sets the X coordinate of the vector.
         *
         * @param x the X coordinate to set
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> setX(int x);

        /**
         * Sets the Z coordinate of the vector.
         *
         * @param z the Z coordinate to set
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> setZ(int z);

        /**
         * Adds the specified X and Z coordinates to the vector.
         *
         * @param x the X coordinate to add
         * @param z the Z coordinate to add
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> add(int x, int z);

        /**
         * Adds the specified vector to this vector.
         *
         * @param vector the vector to add
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> add(@NotNull Vector2Int<?, ?, ?> vector);

        /**
         * Adds the specified scalar to both the X and Z coordinates of the vector.
         *
         * @param scalar the scalar to add
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> addScalar(int scalar);

        /**
         * Subtracts the specified X and Z coordinates from the vector.
         *
         * @param x the X coordinate to subtract
         * @param z the Z coordinate to subtract
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> sub(int x, int z);

        /**
         * Subtracts the specified vector from this vector.
         *
         * @param vector the vector to subtract
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> sub(@NotNull Vector2Int<?, ?, ?> vector);

        /**
         * Subtracts the specified scalar from both the X and Z coordinates of the vector.
         *
         * @param scalar the scalar to subtract
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> subScalar(int scalar);

        /**
         * Multiplies the vector by the specified X and Z coordinates.
         *
         * @param x the X coordinate to multiply by
         * @param z the Z coordinate to multiply by
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> mul(int x, int z);

        /**
         * Multiplies the vector by the specified vector.
         *
         * @param vector the vector to multiply by
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> mul(@NotNull Vector2Int<?, ?, ?> vector);

        /**
         * Multiplies the vector by the specified scalar.
         *
         * @param scalar the scalar to multiply by
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> mulScalar(int scalar);

        /**
         * Divides the vector by the specified X and Z coordinates.
         *
         * @param x the X coordinate to divide by
         * @param z the Z coordinate to divide by
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> div(int x, int z);

        /**
         * Divides the vector by the specified vector.
         *
         * @param vector the vector to divide by
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> div(@NotNull Vector2Int<?, ?, ?> vector);

        /**
         * Divides the vector by the specified scalar.
         *
         * @param scalar the scalar to divide by
         * @return the updated vector
         */
        @NotNull Vector2Int.Mutable<T, U, V> divScalar(int scalar);
    }
}
