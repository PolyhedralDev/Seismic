package com.polyhedraldevelopment.seismic.api.vector;

import org.jetbrains.annotations.NotNull;


public interface Vector3<T extends Vector3<T, U>, U extends Vector3Int<?, ?>> extends Vector<T> {
    /**
     * Returns the X coordinate of the vector.
     *
     * @return the X coordinate
     */
    double getX();

    /**
     * Returns the Y coordinate of the vector.
     *
     * @return the Y coordinate
     */
    double getY();

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
     * Returns the floor value of the Y coordinate.
     *
     * @return the floor value of the Y coordinate
     */
    int getFloorY();

    /**
     * Returns the floor value of the Z coordinate.
     *
     * @return the floor value of the Z coordinate
     */
    int getFloorZ();

    /**
     * Converts the vector to an integer vector.
     *
     * @return the integer vector
     */
    @NotNull U toInt();

    /**
     * Interface for mutable 3D vectors.
     *
     * @param <T> the type of the vector
     */
    interface Mutable<T extends Vector3<T, U>, U extends Vector3Int<?, ?>> extends Vector.Mutable<T>, Vector3<T, U> {

        /**
         * Sets the X, Y, and Z coordinates of the vector.
         *
         * @param x the X coordinate to set
         * @param y the Y coordinate to set
         * @param z the Z coordinate to set
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> set(double x, double y, double z);

        /**
         * Sets the X coordinate of the vector.
         *
         * @param x the X coordinate to set
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> setX(double x);

        /**
         * Sets the Y coordinate of the vector.
         *
         * @param y the Y coordinate to set
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> setY(double y);

        /**
         * Sets the Z coordinate of the vector.
         *
         * @param z the Z coordinate to set
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> setZ(double z);

        /**
         * Adds the specified X, Y, and Z coordinates to the vector.
         *
         * @param x the X coordinate to add
         * @param y the Y coordinate to add
         * @param z the Z coordinate to add
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> add(double x, double y, double z);

        /**
         * Adds the specified vector to this vector.
         *
         * @param vector the vector to add
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> add(@NotNull Vector3<?, ?> vector);

        /**
         * Adds the specified scalar to the X, Y, and Z coordinates of the vector.
         *
         * @param scalar the scalar to add
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> addScalar(double scalar);

        /**
         * Subtracts the specified X, Y, and Z coordinates from the vector.
         *
         * @param x the X coordinate to subtract
         * @param y the Y coordinate to subtract
         * @param z the Z coordinate to subtract
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> sub(double x, double y, double z);

        /**
         * Subtracts the specified vector from this vector.
         *
         * @param vector the vector to subtract
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> sub(@NotNull Vector3<?, ?> vector);

        /**
         * Subtracts the specified scalar from the X, Y, and Z coordinates of the vector.
         *
         * @param scalar the scalar to subtract
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> subScalar(double scalar);

        /**
         * Multiplies the vector by the specified X, Y, and Z coordinates.
         *
         * @param x the X coordinate to multiply by
         * @param y the Y coordinate to multiply by
         * @param z the Z coordinate to multiply by
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> mul(double x, double y, double z);

        /**
         * Multiplies the vector by the specified vector.
         *
         * @param vector the vector to multiply by
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> mul(@NotNull Vector3<?, ?> vector);

        /**
         * Multiplies the vector by the specified scalar.
         *
         * @param scalar the scalar to multiply by
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> mulScalar(double scalar);

        /**
         * Divides the vector by the specified X, Y, and Z coordinates.
         *
         * @param x the X coordinate to divide by
         * @param y the Y coordinate to divide by
         * @param z the Z coordinate to divide by
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> div(double x, double y, double z);

        /**
         * Divides the vector by the specified vector.
         *
         * @param vector the vector to divide by
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> div(@NotNull Vector3<?, ?> vector);

        /**
         * Divides the vector by the specified scalar.
         *
         * @param scalar the scalar to divide by
         * @return the updated vector
         */
        @NotNull Vector3.Mutable<T, U> divScalar(double scalar);
    }
}
