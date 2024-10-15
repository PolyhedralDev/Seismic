package com.polyhedraldevelopment.seismic.api.vector;


import com.polyhedraldevelopment.seismic.api.vector.Vector2Int.Mutable;
import org.jetbrains.annotations.NotNull;


public interface Vector2<T extends Vector2<T, U, V>, U extends Vector3<?, ?>, V extends Vector2Int<?, ?, ?>> extends Vector<T> {
    /**
     * Returns the X coordinate of the vector.
     *
     * @return the X coordinate
     */
    double getX();

    /**
     * Returns the Z coordinate of the vector.
     *
     * @return the Z coordinate
     */
    double getZ();

    /**
     * Returns the floor value of the X coordinate.
     *
     * @return the floor value of the X coordinate
     */
    int getFloorX();

    /**
     * Returns the floor value of the Z coordinate.
     *
     * @return the floor value of the Z coordinate
     */
    int getFloorZ();

    /**
     * Extrudes the vector to a 3D vector by adding the specified Y coordinate.
     *
     * @param y the Y coordinate to add
     * @return the extruded 3D vector
     */
    @NotNull U extrude(double y);

    /**
     * Converts the vector to an integer vector.
     *
     * @return the integer vector
     */
    @NotNull V toInt();

    /**
     * Interface for mutable 2D vectors.
     *
     * @param <T> the type of the vector
     */
    interface Mutable<T extends Vector2<T, U, V>, U extends Vector3<?, ?>, V extends Vector2Int<?, ?, ?>> extends Vector.Mutable<T>, Vector2<T, U, V> {

        /**
         * Sets the X and Z coordinates of the vector.
         *
         * @param x the X coordinate to set
         * @param z the Z coordinate to set
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> set(double x, double z);

        /**
         * Sets the X coordinate of the vector.
         *
         * @param x the X coordinate to set
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> setX(double x);

        /**
         * Sets the Z coordinate of the vector.
         *
         * @param z the Z coordinate to set
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> setZ(double z);

        /**
         * Adds the specified X and Z coordinates to the vector.
         *
         * @param x the X coordinate to add
         * @param z the Z coordinate to add
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> add(double x, double z);

        /**
         * Adds the specified vector to this vector.
         *
         * @param vector the vector to add
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> add(@NotNull Vector2<?, ?, ?> vector);

        /**
         * Adds the specified scalar to both the X and Z coordinates of the vector.
         *
         * @param scalar the scalar to add
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> addScalar(double scalar);

        /**
         * Subtracts the specified X and Z coordinates from the vector.
         *
         * @param x the X coordinate to subtract
         * @param z the Z coordinate to subtract
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> sub(double x, double z);

        /**
         * Subtracts the specified vector from this vector.
         *
         * @param vector the vector to subtract
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> sub(@NotNull Vector2<?, ?, ?> vector);

        /**
         * Subtracts the specified scalar from both the X and Z coordinates of the vector.
         *
         * @param scalar the scalar to subtract
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> subScalar(double scalar);

        /**
         * Multiplies the vector by the specified X and Z coordinates.
         *
         * @param x the X coordinate to multiply by
         * @param z the Z coordinate to multiply by
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> mul(double x, double z);

        /**
         * Multiplies the vector by the specified vector.
         *
         * @param vector the vector to multiply by
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> mul(@NotNull Vector2<?, ?, ?> vector);

        /**
         * Multiplies the vector by the specified scalar.
         *
         * @param scalar the scalar to multiply by
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> mulScalar(double scalar);

        /**
         * Divides the vector by the specified X and Z coordinates.
         *
         * @param x the X coordinate to divide by
         * @param z the Z coordinate to divide by
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> div(double x, double z);

        /**
         * Divides the vector by the specified vector.
         *
         * @param vector the vector to divide by
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> div(@NotNull Vector2<?, ?, ?> vector);

        /**
         * Divides the vector by the specified scalar.
         *
         * @param scalar the scalar to divide by
         * @return the updated vector
         */
        @NotNull Vector2.Mutable<T, U, V> divScalar(double scalar);
    }
}
