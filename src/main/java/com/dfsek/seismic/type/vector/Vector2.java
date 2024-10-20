package com.dfsek.seismic.type.vector;

import com.dfsek.seismic.math.algebra.LinearAlgebraFunctions;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.type.DistanceFunction;
import com.dfsek.seismic.type.Rotation;
import org.jetbrains.annotations.NotNull;


public class Vector2 {
    private static final Vector2
        ZERO = new Vector2(0, 0);
    private static final Vector2
        UNIT = new Vector2(0, 1);
    protected double x, z;

    Vector2(double x, double z) {
        this.x = x;
        this.z = z;
    }

    /**
     * Creates a new instance of `Vector2Impl` with the specified x and z coordinates.
     *
     * @param x the x-coordinate of the vector
     * @param z the z-coordinate of the vector
     *
     * @return a new `Vector2Impl` instance with the specified coordinates
     */
    public static Vector2 of(double x, double z) {
        return new Vector2(x, z);
    }

    /**
     * Returns a vector with all components set to zero.
     *
     * @return a zero vector
     */
    public static @NotNull Vector2 zero() {
        return Vector2.ZERO;
    }

    /**
     * Returns a unit vector (a vector with a length of 1).
     *
     * @return a unit vector
     */
    public static @NotNull Vector2 unit() {
        return Vector2.UNIT;
    }

    /**
     * Returns the X coordinate of the vector.
     *
     * @return the X coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the Z coordinate of the vector.
     *
     * @return the Z coordinate
     */
    public double getZ() {
        return z;
    }

    /**
     * Returns the floor value of the X coordinate.
     *
     * @return the floor value of the X coordinate
     */
    public int getFloorX() {
        return FloatingPointFunctions.floor(x);
    }

    /**
     * Returns the floor value of the Z coordinate.
     *
     * @return the floor value of the Z coordinate
     */
    public int getFloorZ() {
        return FloatingPointFunctions.floor(z);
    }

    /**
     * Extrudes the vector to a 3D vector by adding the specified Y coordinate.
     *
     * @param y the Y coordinate to add
     *
     * @return the extruded 3D vector
     */
    public @NotNull Vector3 extrude(double y) {
        return new Vector3(x, y, z);
    }

    /**
     * Converts the vector to an integer vector.
     *
     * @return the integer vector
     */
    public @NotNull Vector2Int toInt() {
        return new Vector2Int(getFloorX(), getFloorZ());
    }

    /**
     * Returns the length (magnitude) of the vector.
     *
     * @param distanceFunction the distance function to use
     *
     * @return the length of the vector
     */
    public double length(@NotNull DistanceFunction distanceFunction) {
        return distanceFunction.getDistance(x, z);
    }

    /**
     * Returns the inverse of the length of the vector.
     *
     * @param distanceFunction the distance function to use
     *
     * @return the inverse length of the vector
     */
    public double inverseLength(@NotNull DistanceFunction distanceFunction) {
        return distanceFunction.getInverseDistance(x, z);
    }

    /**
     * Returns the distance between this vector and the specified vector.
     *
     * @param distanceFunction the distance function to use
     * @param vector           the vector to calculate the distance to
     *
     * @return the distance between the two vectors
     */
    public double distance(@NotNull DistanceFunction distanceFunction, @NotNull Vector2 vector) {
        double dx = vector.x - x;
        double dz = vector.z - z;
        return distanceFunction.getDistance(dx, dz);
    }

    /**
     * Returns the inverse of the distance between this vector and the specified vector.
     *
     * @param distanceFunction the distance function to use
     * @param vector           the vector to calculate the distance to
     *
     * @return the inverse of the distance between the two vectors
     */
    public double inverseDistance(@NotNull DistanceFunction distanceFunction, @NotNull Vector2 vector) {
        double dx = vector.x - x;
        double dz = vector.z - z;
        return distanceFunction.getInverseDistance(dx, dz);
    }

    /**
     * Calculates the dot product of this vector and the specified vector.
     *
     * @param vector the vector to dot with
     *
     * @return the dot product of the two vectors
     */
    public double dot(@NotNull Vector2 vector) {
        return LinearAlgebraFunctions.dotProduct(x, z, vector.x, vector.z);
    }

    /**
     * Creates and returns a copy of this vector.
     *
     * @return a copy of this vector
     */
    public @NotNull Vector2 copy() {
        return new Vector2(x, z);
    }

    /**
     * Creates and returns a mutable copy of this vector.
     *
     * @return a mutable copy of this vector
     */
    public @NotNull Vector2.Mutable mutable() {
        return new Mutable(x, z);
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
        int hash = 17;
        hash = 31 * hash + Double.hashCode(x);
        hash = 31 * hash + Double.hashCode(z);
        return hash;
    }


    public boolean equals(Object obj) {
        if(!(obj instanceof Vector2 other)) return false;
        return FloatingPointFunctions.equals(this.x, other.x) && FloatingPointFunctions.equals(this.z, other.z);
    }


    public String toString() {
        return "(" + x + ", " + z + ")";
    }

    public static class Mutable extends Vector2 {

        private Mutable(double x, double z) {
            super(x, z);
        }

        /**
         * Creates a new instance of `Mutable` with the specified x and z coordinates.
         *
         * @param x the x-coordinate of the vector
         * @param z the z-coordinate of the vector
         *
         * @return a new `Mutable` instance with the specified coordinates
         */
        public static Vector2.Mutable of(double x, double z) {
            return new Vector2.Mutable(x, z);
        }

        /**
         * Sets the X and Z coordinates of the vector.
         *
         * @param x the X coordinate to set
         * @param z the Z coordinate to set
         *
         * @return the updated vector
         */
        public @NotNull Vector2.Mutable set(double x, double z) {
            this.x = x;
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
        public @NotNull Vector2.Mutable setX(double x) {
            this.x = x;
            return this;
        }

        /**
         * Sets the Z coordinate of the vector.
         *
         * @param z the Z coordinate to set
         *
         * @return the updated vector
         */
        public @NotNull Vector2.Mutable setZ(double z) {
            this.z = z;
            return this;
        }

        /**
         * Adds the specified X and Z coordinates to the vector.
         *
         * @param x the X coordinate to add
         * @param z the Z coordinate to add
         *
         * @return the updated vector
         */
        public @NotNull Vector2.Mutable add(double x, double z) {
            this.x += x;
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
        public @NotNull Vector2.Mutable add(@NotNull Vector2 vector) {
            this.x += vector.x;
            this.z += vector.z;
            return this;
        }

        /**
         * Adds the specified scalar to both the X and Z coordinates of the vector.
         *
         * @param scalar the scalar to add
         *
         * @return the updated vector
         */
        public @NotNull Vector2.Mutable addScalar(double scalar) {
            this.x += scalar;
            this.z += scalar;
            return this;
        }

        /**
         * Subtracts the specified X and Z coordinates from the vector.
         *
         * @param x the X coordinate to subtract
         * @param z the Z coordinate to subtract
         *
         * @return the updated vector
         */
        public @NotNull Vector2.Mutable sub(double x, double z) {
            this.x -= x;
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
        public @NotNull Vector2.Mutable sub(@NotNull Vector2 vector) {
            this.x -= vector.x;
            this.z -= vector.z;
            return this;
        }

        /**
         * Subtracts the specified scalar from both the X and Z coordinates of the vector.
         *
         * @param scalar the scalar to subtract
         *
         * @return the updated vector
         */
        public @NotNull Vector2.Mutable subScalar(double scalar) {
            this.x -= scalar;
            this.z -= scalar;
            return this;
        }

        /**
         * Multiplies the vector by the specified X and Z coordinates.
         *
         * @param x the X coordinate to multiply by
         * @param z the Z coordinate to multiply by
         *
         * @return the updated vector
         */
        public @NotNull Vector2.Mutable mul(double x, double z) {
            this.x *= x;
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
        public @NotNull Vector2.Mutable mul(@NotNull Vector2 vector) {
            this.x *= vector.x;
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
        public @NotNull Vector2.Mutable mulScalar(double scalar) {
            this.x *= scalar;
            this.z *= scalar;
            return this;
        }

        /**
         * Divides the vector by the specified X and Z coordinates.
         *
         * @param x the X coordinate to divide by
         * @param z the Z coordinate to divide by
         *
         * @return the updated vector
         */
        public @NotNull Vector2.Mutable div(double x, double z) {
            this.x /= x;
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
        public @NotNull Vector2.Mutable div(@NotNull Vector2 vector) {
            this.x /= vector.x;
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
        public @NotNull Vector2.Mutable divScalar(double scalar) {
            this.x /= scalar;
            this.z /= scalar;
            return this;
        }

        /**
         * Normalizes the vector (makes it a unit vector).
         *
         * @return the normalized vector
         */
        public @NotNull Vector2.Mutable normalize() {
            double length = this.length(DistanceFunction.Euclidean);
            if(length != 0) {
                this.x /= length;
                this.z /= length;
            }
            return this;
        }

        /**
         * Rotates the vector by the specified rotation.
         *
         * @param rotation the rotation to apply
         *
         * @return the rotated vector
         */
        public @NotNull Vector2.Mutable rotate(@NotNull Rotation rotation) {
            switch(rotation) {
                case CW_90 -> {
                    double tempX = this.x;
                    this.x = this.z;
                    this.z = -tempX;
                }
                case CCW_90 -> {
                    double tempX = this.x;
                    this.x = -this.z;
                    this.z = tempX;
                }
                case CW_180 -> {
                    this.x *= -1;
                    this.z *= -1;
                }
            }
            return this;
        }

        /**
         * Rotates the vector by the specified rotation.
         *
         * @param angle the angle to rotate the vector about (in radians)
         *
         * @return the rotated vector
         */
        public @NotNull Vector2.Mutable rotate(double angle) {
            double angleCos = Math.cos(angle);
            double angleSin = Math.sin(angle);

            double newX = angleCos * this.x - angleSin * this.z;
            double newZ = angleSin * this.x + angleCos * this.z;

            return this.set(newX, newZ);
        }

        /**
         * Creates and returns an immutable copy of this vector.
         *
         * @return an immutable copy of this vector
         */
        public @NotNull Vector2 immutable() {
            return new Vector2(this.x, this.z);
        }
    }
}
