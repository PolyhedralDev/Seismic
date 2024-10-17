package com.polyhedraldevelopment.seismic.type.vector;

import com.polyhedraldevelopment.seismic.type.Rotation;
import com.polyhedraldevelopment.seismic.math.algebra.AlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.algebra.LinearAlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.floatingpoint.FloatingPointFunctions;
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
     * @return a new `Vector3IntImpl` instance with the specified coordinates
     */
    public static Vector3Int of(int x, int y, int z) {
        return new Vector3Int(x, y, z);
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
        return new Vector3((double) this.x, (double) this.y, (double) this.z);
    }

    /**
     * Returns a vector with all components set to zero.
     *
     * @return a zero vector
     */
    public @NotNull Vector3Int zero() {
        return ZERO;
    }

    /**
     * Returns a unit vector (a vector with a length of 1).
     *
     * @return a unit vector
     */
    public @NotNull Vector3Int unit() {
        return UNIT;
    }

    /**
     * Returns the length (magnitude) of the vector.
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns the squared length of the vector.
     * This is more efficient than calculating the length directly.
     *
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Returns the inverse of the length of the vector.
     *
     * @return the inverse length of the vector
     */
    public double inverseLength() {
        return 1.0 / length();
    }

    /**
     * Returns the squared inverse length of the vector.
     *
     * @return the squared inverse length of the vector
     */
    public double inverseLengthSquared() {
        return AlgebraFunctions.invSqrt(lengthSquared());
    }

    /**
     * Returns the distance between this vector and the specified vector.
     *
     * @param vector the vector to calculate the distance to
     * @return the distance between the two vectors
     */
    public double distance(@NotNull Vector3Int vector) {
        return Math.sqrt(distanceSquared(vector));
    }

    /**
     * Returns the squared distance between this vector and the specified vector.
     *
     * @param vector the vector to calculate the squared distance to
     * @return the squared distance between the two vectors
     */
    public double distanceSquared(@NotNull Vector3Int vector) {
        int dx = this.x - vector.x;
        int dy = this.y - vector.y;
        int dz = this.z - vector.z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculates the dot product of this vector and the specified vector.
     *
     * @param vector the vector to dot with
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
        /**
         * Creates a new instance of `Mutable` with the specified x, y, and z coordinates.
         *
         * @param x the x-coordinate of the vector
         * @param y the y-coordinate of the vector
         * @param z the z-coordinate of the vector
         * @return a new `Mutable` instance with the specified coordinates
         */
        public static Vector3Int.Mutable of(int x, int y, int z) {
            return new Vector3Int.Mutable(x, y, z);
        }

        private Mutable(int x, int y, int z) {
            super(x, y, z);
        }

        /**
         * Sets the X, Y, and Z coordinates of the vector.
         *
         * @param x the X coordinate to set
         * @param y the Y coordinate to set
         * @param z the Z coordinate to set
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
            double length = length();
            if (length != 0) {
                this.x /= length;
                this.y /= length;
                this.z /= length;
            }
            return this;
        }


        //TODO PROPER ROT
        /**
         * Rotates the vector by the specified rotation.
         *
         * @param rotation the rotation to apply
         * @return the rotated vector
         */
        public @NotNull Vector3Int.Mutable rotate(@NotNull Rotation rotation) {
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
         * Creates and returns an immutable copy of this vector.
         *
         * @return an immutable copy of this vector
         */
        public @NotNull Vector3Int immutable() {
            return new Vector3Int(this.x, this.y, this.z);
        }
    }
}
