package com.dfsek.seismic.type.vector;

import com.dfsek.seismic.math.algebra.LinearAlgebraFunctions;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.math.trigonometry.TrigonometryFunctions;
import com.dfsek.seismic.type.DistanceFunction;
import com.dfsek.seismic.type.Rotation;
import org.jetbrains.annotations.NotNull;


public class Vector3Int {
    private static final Vector3Int ZERO = new Vector3Int(0, 0, 0);
    private static final Vector3Int UNIT = new Vector3Int(0, 1, 0);
    protected int x, y, z;

    Vector3Int(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new instance of `Vector3IntImpl` with the specified x, y, and z coordinates.
     *
     * @param x the x-coordinate of the vector
     * @param y the y-coordinate of the vector
     * @param z the z-coordinate of the vector
     *
     * @return a new `Vector3IntImpl` instance with the specified coordinates
     */
    public static Vector3Int of(int x, int y, int z) {
        return new Vector3Int(x, y, z);
    }

    /**
     * Returns a vector with all components set to zero.
     *
     * @return a zero vector
     */
    public static @NotNull Vector3Int zero() {
        return Vector3Int.ZERO;
    }

    /**
     * Returns a unit vector (a vector with a length of 1).
     *
     * @return a unit vector
     */
    public static @NotNull Vector3Int unit() {
        return Vector3Int.UNIT;
    }

    /**
     * Returns the X coordinate of the vector.
     *
     * @return the X coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the Y coordinate of the vector.
     *
     * @return the Y coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Returns the Z coordinate of the vector.
     *
     * @return the Z coordinate
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Extrudes the vector to a 2D vector by removing the Y coordinate.
     *
     * @return the flattened 2D vector
     */
    public @NotNull Vector2Int flatten() {
        return new Vector2Int(this.x, this.z);
    }

    /**
     * Converts the vector to a floating-point vector.
     *
     * @return the floating-point vector
     */
    public @NotNull Vector3 toFloat() {
        return new Vector3(this.x, this.y, this.z);
    }

    /**
     * Returns the length (magnitude) of the vector.
     *
     * @param distanceFunction the distance function to use
     *
     * @return the length of the vector
     */
    public double length(@NotNull DistanceFunction distanceFunction) {
        return distanceFunction.getDistance(x, y, z);
    }

    /**
     * Returns the inverse of the length of the vector.
     *
     * @param distanceFunction the distance function to use
     *
     * @return the inverse length of the vector
     */
    public double inverseLength(@NotNull DistanceFunction distanceFunction) {
        return distanceFunction.getInverseDistance(x, y, z);
    }

    /**
     * Returns the distance between this vector and the specified vector.
     *
     * @param distanceFunction the distance function to use
     * @param vector the vector to calculate the distance to
     *
     * @return the distance between the two vectors
     */
    public double distance(@NotNull DistanceFunction distanceFunction, @NotNull Vector3Int vector) {
        double dx = vector.x - x;
        double dy = vector.y - y;
        double dz = vector.z - z;
        return distanceFunction.getDistance(dx, dy, dz);
    }

    /**
     * Returns the inverse of the distance between this vector and the specified vector.
     *
     * @param distanceFunction the distance function to use
     * @param vector the vector to calculate the distance to
     *
     * @return the inverse of the distance between the two vectors
     */
    public double inverseDistance(@NotNull DistanceFunction distanceFunction, @NotNull Vector3Int vector) {
        double dx = vector.x - x;
        double dy = vector.y - y;
        double dz = vector.z - z;
        return distanceFunction.getInverseDistance(dx, y, dz);
    }

    /**
     * Calculates the dot product of this vector and the specified vector.
     *
     * @param vector the vector to dot with
     *
     * @return the dot product of the two vectors
     */
    public double dot(@NotNull Vector3Int vector) {
        return LinearAlgebraFunctions.dotProduct(this.x, this.y, this.z, vector.x, vector.y, vector.z);
    }

    /**
     * Creates and returns a copy of this vector.
     *
     * @return a copy of this vector
     */
    public @NotNull Vector3Int copy() {
        return new Vector3Int(this.x, this.y, this.z);
    }

    /**
     * Creates and returns a mutable copy of this vector.
     *
     * @return a mutable copy of this vector
     */
    public @NotNull Vector3Int.Mutable mutable() {
        return new Vector3Int.Mutable(this.x, this.y, this.z);
    }

    /**
     * Returns if the vector is normalized.
     *
     * @return whether the vector is normalized
     */
    public boolean isNormalized() {
        return FloatingPointFunctions.equals(this.length(DistanceFunction.EuclideanSq), 1);
    }


    public int hashCode() {
        return (31 * x) + (31 * y) + z;
    }


    public boolean equals(Object obj) {
        if(!(obj instanceof Vector3Int other)) return false;
        return FloatingPointFunctions.equals(x, other.x) && FloatingPointFunctions.equals(y, other.y) &&
               FloatingPointFunctions.equals(z, other.z);
    }


    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public static class Mutable extends Vector3Int {
        private Mutable(int x, int y, int z) {
            super(x, y, z);
        }

        /**
         * Creates a new instance of `Mutable` with the specified x, y, and z coordinates.
         *
         * @param x the x-coordinate of the vector
         * @param y the y-coordinate of the vector
         * @param z the z-coordinate of the vector
         *
         * @return a new `Mutable` instance with the specified coordinates
         */
        public static Vector3Int.Mutable of(int x, int y, int z) {
            return new Vector3Int.Mutable(x, y, z);
        }

        /**
         * Sets the X, Y, and Z coordinates of the vector.
         *
         * @param x the X coordinate to set
         * @param y the Y coordinate to set
         * @param z the Z coordinate to set
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable set(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        /**
         * Sets the X coordinate of the vector.
         *
         * @param x the X coordinate to set
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable setX(int x) {
            this.x = x;
            return this;
        }

        /**
         * Sets the Y coordinate of the vector.
         *
         * @param y the Y coordinate to set
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable setY(int y) {
            this.y = y;
            return this;
        }

        /**
         * Sets the Z coordinate of the vector.
         *
         * @param z the Z coordinate to set
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable setZ(int z) {
            this.z = z;
            return this;
        }

        /**
         * Adds the specified X, Y, and Z coordinates to the vector.
         *
         * @param x the X coordinate to add
         * @param y the Y coordinate to add
         * @param z the Z coordinate to add
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable add(int x, int y, int z) {
            this.x += x;
            this.y += y;
            this.z += z;
            return this;
        }

        /**
         * Adds the specified vector to this vector.
         *
         * @param vector the vector to add
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable add(@NotNull Vector3Int vector) {
            this.x += vector.x;
            this.y += vector.y;
            this.z += vector.z;
            return this;
        }

        /**
         * Adds the specified scalar to the X, Y, and Z coordinates of the vector.
         *
         * @param scalar the scalar to add
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable addScalar(int scalar) {
            this.x += scalar;
            this.y += scalar;
            this.z += scalar;
            return this;
        }

        /**
         * Subtracts the specified X, Y, and Z coordinates from the vector.
         *
         * @param x the X coordinate to subtract
         * @param y the Y coordinate to subtract
         * @param z the Z coordinate to subtract
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable sub(int x, int y, int z) {
            this.x -= x;
            this.y -= y;
            this.z -= z;
            return this;
        }

        /**
         * Subtracts the specified vector from this vector.
         *
         * @param vector the vector to subtract
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable sub(@NotNull Vector3Int vector) {
            this.x -= vector.x;
            this.y -= vector.y;
            this.z -= vector.z;
            return this;
        }

        /**
         * Subtracts the specified scalar from the X, Y, and Z coordinates of the vector.
         *
         * @param scalar the scalar to subtract
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable subScalar(int scalar) {
            this.x -= scalar;
            this.y -= scalar;
            this.z -= scalar;
            return this;
        }

        /**
         * Multiplies the vector by the specified X, Y, and Z coordinates.
         *
         * @param x the X coordinate to multiply by
         * @param y the Y coordinate to multiply by
         * @param z the Z coordinate to multiply by
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable mul(int x, int y, int z) {
            this.x *= x;
            this.y *= y;
            this.z *= z;
            return this;
        }

        /**
         * Multiplies the vector by the specified vector.
         *
         * @param vector the vector to multiply by
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable mul(@NotNull Vector3Int vector) {
            this.x *= vector.x;
            this.y *= vector.y;
            this.z *= vector.z;
            return this;
        }

        /**
         * Multiplies the vector by the specified scalar.
         *
         * @param scalar the scalar to multiply by
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable mulScalar(int scalar) {
            this.x *= scalar;
            this.y *= scalar;
            this.z *= scalar;
            return this;
        }

        /**
         * Divides the vector by the specified X, Y, and Z coordinates.
         *
         * @param x the X coordinate to divide by
         * @param y the Y coordinate to divide by
         * @param z the Z coordinate to divide by
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable div(int x, int y, int z) {
            this.x /= x;
            this.y /= y;
            this.z /= z;
            return this;
        }

        /**
         * Divides the vector by the specified vector.
         *
         * @param vector the vector to divide by
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable div(@NotNull Vector3Int vector) {
            this.x /= vector.x;
            this.y /= vector.y;
            this.z /= vector.z;
            return this;
        }

        /**
         * Divides the vector by the specified scalar.
         *
         * @param scalar the scalar to divide by
         *
         * @return the updated vector
         */
        public @NotNull Vector3Int.Mutable divScalar(int scalar) {
            this.x /= scalar;
            this.y /= scalar;
            this.z /= scalar;
            return this;
        }

        /**
         * Normalizes the vector (makes it a unit vector).
         *
         * @return the normalized vector
         */
        public @NotNull Vector3Int.Mutable normalize() {
            double length = length(DistanceFunction.Euclidean);
            if(length != 0) {
                this.x /= (int) length;
                this.y /= (int) length;
                this.z /= (int) length;
            }
            return this;
        }

        /**
         * Rotates the vector around a given arbitrary axis in 3D space.
         *
         * @param axis     the axis to rotate the vector around
         * @param rotation the angle to rotate the vector around the axis
         *
         * @return the same vector
         */
        public @NotNull Mutable rotateAroundAxis(@NotNull Vector3 axis, Rotation rotation) {
            return rotateAroundAxis(axis, Math.toRadians(rotation.getDegrees()));

        }

        /**
         * Rotates the vector around a given arbitrary axis in 3D space.
         *
         * @param axis  the axis to rotate the vector around
         * @param angle the angle to rotate the vector around the axis
         *
         * @return the same vector
         */
        public @NotNull Mutable rotateAroundAxis(@NotNull Vector3 axis, double angle) {
            return rotateAroundNonUnitAxis(axis.isNormalized() ? axis : axis.mutable().normalize().immutable(), angle);
        }

        /**
         * Rotates the vector around a given arbitrary axis in 3D space.
         *
         * @param axis     the axis to rotate the vector around
         * @param rotation the angle to rotate the vector around the axis
         *
         * @return the same vector
         */
        public @NotNull Mutable rotateAroundNonUnitAxis(@NotNull Vector3 axis, Rotation rotation) {
            return rotateAroundNonUnitAxis(axis, Math.toRadians(rotation.getDegrees()));
        }

        /**
         * Rotates the vector around a given arbitrary axis in 3D space.
         *
         * @param axis  the axis to rotate the vector around
         * @param angle the angle to rotate the vector around the axis
         *
         * @return the same vector
         */
        public @NotNull Mutable rotateAroundNonUnitAxis(@NotNull Vector3 axis, double angle) {
            double x = getX(), y = getY(), z = getZ();
            double x2 = axis.x, y2 = axis.y, z2 = axis.z;

            double cosTheta = TrigonometryFunctions.cos(angle);
            double sinTheta = TrigonometryFunctions.sin(angle);
            double dotProduct = axis.dot(this.toFloat());

            double xPrime = x2 * dotProduct * (1d - cosTheta)
                            + x * cosTheta
                            + (-z2 * y + y2 * z) * sinTheta;
            double yPrime = y2 * dotProduct * (1d - cosTheta)
                            + y * cosTheta
                            + (z2 * x - x2 * z) * sinTheta;
            double zPrime = z2 * dotProduct * (1d - cosTheta)
                            + z * cosTheta
                            + (-y2 * x + x2 * y) * sinTheta;

            return setX((int) xPrime).setY((int) yPrime).setZ((int) zPrime);
        }

        /**
         * Rotates the vector around the X axis.
         *
         * @param rotation the rotation to apply
         *
         * @return the same vector
         */
        public @NotNull Mutable rotateAroundX(Rotation rotation) {
            return rotateAroundX(Math.toRadians(rotation.getDegrees()));
        }

        /**
         * Rotates the vector around the X axis.
         *
         * @param angle the angle to rotate the vector about (in radians)
         *
         * @return the same vector
         */
        public @NotNull Mutable rotateAroundX(double angle) {
            double angleCos = TrigonometryFunctions.cos(angle);
            double angleSin = TrigonometryFunctions.sin(angle);

            double y = angleCos * getY() - angleSin * getZ();
            double z = angleSin * getY() + angleCos * getZ();
            return setY((int) y).setZ((int) z);
        }

        /**
         * Rotates the vector around the Y axis.
         *
         * @param rotation the rotation to apply
         *
         * @return the same vector
         */
        public @NotNull Vector3Int.Mutable rotateAroundY(Rotation rotation) {
            return rotateAroundY(Math.toRadians(rotation.getDegrees()));
        }

        /**
         * Rotates the vector around the Y axis.
         *
         * @param angle the angle to rotate the vector about (in radians)
         *
         * @return the same vector
         */
        public @NotNull Vector3Int.Mutable rotateAroundY(double angle) {
            double angleCos = TrigonometryFunctions.cos(angle);
            double angleSin = TrigonometryFunctions.sin(angle);

            double x = angleCos * getX() + angleSin * getZ();
            double z = -angleSin * getX() + angleCos * getZ();
            return setX((int) x).setZ((int) z);
        }

        /**
         * Rotates the vector around the Z axis.
         *
         * @param rotation the rotation to apply
         *
         * @return the same vector
         */
        public @NotNull Mutable rotateAroundZ(Rotation rotation) {
            return rotateAroundZ(Math.toRadians(rotation.getDegrees()));

        }

        /**
         * Rotates the vector around the Z axis.
         *
         * @param angle the angle to rotate the vector about (in radians)
         *
         * @return the same vector
         */
        public @NotNull Vector3Int.Mutable rotateAroundZ(double angle) {
            double angleCos = TrigonometryFunctions.cos(angle);
            double angleSin = TrigonometryFunctions.sin(angle);

            double x = angleCos * getX() - angleSin * getY();
            double y = angleSin * getX() + angleCos * getY();
            return setX((int) x).setY((int) y);
        }

        /**
         * Creates and returns an immutable copy of this vector.
         *
         * @return an immutable copy of this vector
         */
        public @NotNull Vector3Int immutable() {
            return new Vector3Int(this.x, this.y, this.z);
        }
    }
}
