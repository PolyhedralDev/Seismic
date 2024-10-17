package com.polyhedraldevelopment.seismic.type.vector;

import com.polyhedraldevelopment.seismic.math.algebra.AlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.algebra.LinearAlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.floatingpoint.FloatingPointFunctions;
import com.polyhedraldevelopment.seismic.type.DistanceFunction;
import com.polyhedraldevelopment.seismic.type.Rotation;
import org.jetbrains.annotations.NotNull;


public class Vector2Int {
    private static final Vector2Int ZERO = new Vector2Int(0, 0);
    private static final Vector2Int UNIT = new Vector2Int(0, 1);
    protected int x, z;

    Vector2Int(int x, int z) {
        this.x = x;
        this.z = z;
    }

    /**
     * Creates a new instance of `Vector2IntImpl` with the specified x and z coordinates.
     *
     * @param x the x-coordinate of the vector
     * @param z the z-coordinate of the vector
     *
     * @return a new `Vector2IntImpl` instance with the specified coordinates
     */
    public static Vector2Int of(int x, int z) {
        return new Vector2Int(x, z);
    }

    /**
     * Returns a vector with all components set to zero.
     *
     * @return a zero vector
     */
    public static @NotNull Vector2Int zero() {
        return Vector2Int.ZERO;
    }

    /**
     * Returns a unit vector (a vector with a length of 1).
     *
     * @return a unit vector
     */
    public static @NotNull Vector2Int unit() {
        return Vector2Int.UNIT;
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
     * Returns the Z coordinate of the vector.
     *
     * @return the Z coordinate
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Extrudes the vector to a 3D vector by adding the specified Y coordinate.
     *
     * @param y the Y coordinate to add
     *
     * @return the extruded 3D vector
     */
    public @NotNull Vector3Int extrude(int y) {
        return new Vector3Int(this.x, y, this.z);
    }

    /**
     * Converts the vector to a floating-point vector.
     *
     * @return the floating-point vector
     */
    public @NotNull Vector2 toFloat() {
        return new Vector2(this.x, this.z);
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
     * @param vector the vector to calculate the distance to
     *
     * @return the distance between the two vectors
     */
    public double distance(@NotNull DistanceFunction distanceFunction, @NotNull Vector2Int vector) {
        double dx = vector.x - x;
        double dz = vector.z - z;
        return distanceFunction.getDistance(dx, dz);
    }

    /**
     * Returns the inverse of the distance between this vector and the specified vector.
     *
     * @param distanceFunction the distance function to use
     * @param vector the vector to calculate the distance to
     *
     * @return the inverse of the distance between the two vectors
     */
    public double inverseDistance(@NotNull DistanceFunction distanceFunction, @NotNull Vector2Int vector) {
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
    public double dot(@NotNull Vector2Int vector) {
        return LinearAlgebraFunctions.dotProduct(this.x, this.z, vector.x, vector.z);
    }

    /**
     * Creates and returns a copy of this vector.
     *
     * @return a copy of this vector
     */
    public @NotNull Vector2Int copy() {
        return new Vector2Int(this.x, this.z);
    }

    /**
     * Creates and returns a mutable copy of this vector.
     *
     * @return a mutable copy of this vector
     */
    public @NotNull Vector2Int.Mutable mutable() {
        return new Vector2Int.Mutable(this.x, this.z);
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
        return (31 * x) + z;
    }


    public boolean equals(Object obj) {
        if(!(obj instanceof Vector2Int other)) return false;
        return FloatingPointFunctions.equals(this.x, other.x) && FloatingPointFunctions.equals(this.z, other.z);
    }


    public String toString() {
        return "(" + x + ", " + z + ")";
    }

    public static class Mutable extends Vector2Int {

        private Mutable(int x, int z) {
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
        public static Vector2Int.Mutable of(int x, int z) {
            return new Vector2Int.Mutable(x, z);
        }

        /**
         * Sets the X and Z coordinates of the vector.
         *
         * @param x the X coordinate to set
         * @param z the Z coordinate to set
         *
         * @return the updated vector
         */
        public @NotNull Vector2Int.Mutable set(int x, int z) {
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
        public @NotNull Vector2Int.Mutable setX(int x) {
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
        public @NotNull Vector2Int.Mutable setZ(int z) {
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
        public @NotNull Vector2Int.Mutable add(int x, int z) {
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
        public @NotNull Vector2Int.Mutable add(@NotNull Vector2Int vector) {
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
        public @NotNull Vector2Int.Mutable addScalar(int scalar) {
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
        public @NotNull Vector2Int.Mutable sub(int x, int z) {
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
        public @NotNull Vector2Int.Mutable sub(@NotNull Vector2Int vector) {
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
        public @NotNull Vector2Int.Mutable subScalar(int scalar) {
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
        public @NotNull Vector2Int.Mutable mul(int x, int z) {
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
        public @NotNull Vector2Int.Mutable mul(@NotNull Vector2Int vector) {
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
        public @NotNull Vector2Int.Mutable mulScalar(int scalar) {
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
        public @NotNull Vector2Int.Mutable div(int x, int z) {
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
        public @NotNull Vector2Int.Mutable div(@NotNull Vector2Int vector) {
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
        public @NotNull Vector2Int.Mutable divScalar(int scalar) {
            this.x /= scalar;
            this.z /= scalar;
            return this;
        }

        /**
         * Normalizes the vector (makes it a unit vector).
         *
         * @return the normalized vector
         */
        public @NotNull Vector2Int.Mutable normalize() {
            double length = this.length(DistanceFunction.Euclidean);
            if(length != 0) {
                this.x /= (int) length;
                this.z /= (int) length;
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
        public @NotNull Vector2Int.Mutable rotate(@NotNull Rotation rotation) {
            switch(rotation) {
                case CW_90 -> {
                    int tempX = this.x;
                    this.x = this.z;
                    this.z = -tempX;
                }
                case CCW_90 -> {
                    int tempX = this.x;
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
        public @NotNull Vector2Int.Mutable rotate(int angle) {
            double angleCos = Math.cos(angle);
            double angleSin = Math.sin(angle);

            double newX = angleCos * this.x - angleSin * this.z;
            double newZ = angleSin * this.x + angleCos * this.z;

            return this.set((int) newX, (int) newZ);
        }

        /**
         * Creates and returns an immutable copy of this vector.
         *
         * @return an immutable copy of this vector
         */
        public @NotNull Vector2Int immutable() {
            return new Vector2Int(this.x, this.z);
        }
    }
}
