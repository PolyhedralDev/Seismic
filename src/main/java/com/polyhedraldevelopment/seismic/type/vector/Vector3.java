package com.polyhedraldevelopment.seismic.type.vector;

import com.polyhedraldevelopment.seismic.type.Rotation;
import com.polyhedraldevelopment.seismic.math.algebra.AlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.algebra.LinearAlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.floatingpoint.FloatingPointFunctions;
import org.jetbrains.annotations.NotNull;


public class Vector3 {
    private static final Vector3 ZERO = new Vector3(0, 0, 0);
    private static final Vector3 UNIT = new Vector3(0, 1, 0);
    protected double x, y, z;

    Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new instance of `Vector3Impl` with the specified x, y, and z coordinates.
     *
     * @param x the x-coordinate of the vector
     * @param y the y-coordinate of the vector
     * @param z the z-coordinate of the vector
     * @return a new `Vector3Impl` instance with the specified coordinates
     */
    public static Vector3 of(double x, double y, double z) {
        return new Vector3(x, y, z);
    }

    /**
     * Returns the X coordinate of the vector.
     *
     * @return the X coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the Y coordinate of the vector.
     *
     * @return the Y coordinate
     */
    public double getY() {
        return this.y;
    }

    /**
     * Returns the Z coordinate of the vector.
     *
     * @return the Z coordinate
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Returns the floor value of the X coordinate.
     *
     * @return the floor value of the X coordinate
     */
    public int getFloorX() {
        return (int) Math.floor(this.x);
    }

    /**
     * Returns the floor value of the Y coordinate.
     *
     * @return the floor value of the Y coordinate
     */
    public int getFloorY() {
        return (int) Math.floor(this.y);
    }

    /**
     * Returns the floor value of the Z coordinate.
     *
     * @return the floor value of the Z coordinate
     */
    public int getFloorZ() {
        return (int) Math.floor(this.z);
    }

    /**
     * Extrudes the vector to a 2D vector by removing the Y coordinate.
     *
     * @return the flattened 2D vector
     */
    public @NotNull Vector2 flatten() {
        return new Vector2(this.x, this.z);
    }

    /**
     * Converts the vector to an integer vector.
     *
     * @return the integer vector
     */
    public @NotNull Vector3Int toInt() {
        return new Vector3Int(getFloorX(), getFloorY(), getFloorZ());
    }

    /**
     * Returns a vector with all components set to zero.
     *
     * @return a zero vector
     */
    public @NotNull Vector3 zero() {
        return ZERO;
    }

    /**
     * Returns a unit vector (a vector with a length of 1).
     *
     * @return a unit vector
     */
    public @NotNull Vector3 unit() {
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
        return AlgebraFunctions.invSqrt(lengthSquared());
    }

    /**
     * Returns the squared inverse length of the vector.
     *
     * @return the squared inverse length of the vector
     */
    public double inverseLengthSquared() {
        return 1.0 / lengthSquared();
    }

    /**
     * Returns the distance between this vector and the specified vector.
     *
     * @param vector the vector to calculate the distance to
     * @return the distance between the two vectors
     */
    public double distance(@NotNull Vector3 vector) {
        return Math.sqrt(distanceSquared(vector));
    }

    /**
     * Returns the squared distance between this vector and the specified vector.
     *
     * @param vector the vector to calculate the squared distance to
     * @return the squared distance between the two vectors
     */
    public double distanceSquared(@NotNull Vector3 vector) {
        double dx = this.x - vector.x;
        double dy = this.y - vector.y;
        double dz = this.z - vector.z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculates the dot product of this vector and the specified vector.
     *
     * @param vector the vector to dot with
     * @return the dot product of the two vectors
     */
    public double dot(@NotNull Vector3 vector) {
        return LinearAlgebraFunctions.dotProduct(this.x, this.y, this.z, vector.x, vector.y, vector.z);
    }

    /**
     * Creates and returns a copy of this vector.
     *
     * @return a copy of this vector
     */
    public @NotNull Vector3 copy() {
        return new Vector3(this.x, this.y, this.z);
    }

    /**
     * Creates and returns a mutable copy of this vector.
     *
     * @return a mutable copy of this vector
     */
    public @NotNull Vector3.Mutable mutable() {
        return new Vector3.Mutable(this.x, this.y, this.z);
    }

    
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Long.hashCode(Double.doubleToLongBits(this.x));
        hash = 79 * hash + Long.hashCode(Double.doubleToLongBits(this.y));
        hash = 79 * hash + Long.hashCode(Double.doubleToLongBits(this.z));
        return hash;
    }

    
    public boolean equals(Object obj) {
        if(!(obj instanceof Vector3 other)) return false;
        return FloatingPointFunctions.equals(x, other.x) && FloatingPointFunctions.equals(y, other.y) &&
               FloatingPointFunctions.equals(z, other.z);
    }

    
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }


    public static class Mutable extends Vector3 {

        /**
         * Creates a new instance of `Mutable` with the specified x, y, and z coordinates.
         *
         * @param x the x-coordinate of the vector
         * @param y the y-coordinate of the vector
         * @param z the z-coordinate of the vector
         * @return a new `Mutable` instance with the specified coordinates
         */
        public static Vector3.Mutable of(double x, double y, double z) {
            return new Vector3.Mutable(x, y, z);
        }

        private Mutable(double x, double y, double z) {
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
        public @NotNull Vector3.Mutable set(double x, double y, double z) {
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
        public @NotNull Vector3.Mutable setX(double x) {
            this.x = x;
            return this;
        }

        /**
         * Sets the Y coordinate of the vector.
         *
         * @param y the Y coordinate to set
         * @return the updated vector
         */
        public @NotNull Vector3.Mutable setY(double y) {
            this.y = y;
            return this;
        }

        /**
         * Sets the Z coordinate of the vector.
         *
         * @param z the Z coordinate to set
         * @return the updated vector
         */
        public @NotNull Vector3.Mutable setZ(double z) {
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
        public @NotNull Vector3.Mutable add(double x, double y, double z) {
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
        public @NotNull Vector3.Mutable add(@NotNull Vector3 vector) {
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
        public @NotNull Vector3.Mutable addScalar(double scalar) {
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
        public @NotNull Vector3.Mutable sub(double x, double y, double z) {
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
        public @NotNull Vector3.Mutable sub(@NotNull Vector3 vector) {
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
        public @NotNull Vector3.Mutable subScalar(double scalar) {
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
        public @NotNull Vector3.Mutable mul(double x, double y, double z) {
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
        public @NotNull Vector3.Mutable mul(@NotNull Vector3 vector) {
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
        public @NotNull Vector3.Mutable mulScalar(double scalar) {
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
        public @NotNull Vector3.Mutable div(double x, double y, double z) {
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
        public @NotNull Vector3.Mutable div(@NotNull Vector3 vector) {
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
        public @NotNull Vector3.Mutable divScalar(double scalar) {
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
        public @NotNull Vector3.Mutable normalize() {
            double length = length();
            if (length != 0) {
                this.x /= length;
                this.y /= length;
                this.z /= length;
            }
            return this;
        }


        //TODO PROPER ROTATION
        /**
         * Rotates the vector by the specified rotation.
         *
         * @param rotation the rotation to apply
         * @return the rotated vector
         */
        public @NotNull Vector3.Mutable rotate(@NotNull Rotation rotation) {
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
         * Creates and returns an immutable copy of this vector.
         *
         * @return an immutable copy of this vector
         */
        public @NotNull Vector3 immutable() {
            return new Vector3(this.x, this.y, this.z);
        }
    }
}
