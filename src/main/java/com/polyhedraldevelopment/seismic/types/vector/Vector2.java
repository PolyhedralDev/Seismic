package com.polyhedraldevelopment.seismic.types.vector;

import com.polyhedraldevelopment.seismic.math.floatingpoint.FloatingPointFunctions;

public class Vector2 {
    private static final Vector2 ZERO = new Vector2(0, 0);
    private static final Vector2 UNIT = new Vector2(0, 1);
    protected double x, z;

    /**
     * Create a vector with a given X and Z component
     *
     * @param x X component
     * @param z Z component
     */
    private Vector2(double x, double z) {
        this.x = x;
        this.z = z;
    }

    /**
     * Returns a vector with both components set to zero.
     *
     * @return the zero vector
     */
    public static Vector2 zero() {
        return ZERO;
    }

    /**
     * Returns a unit vector.
     *
     * @return the unit vector
     */
    public static Vector2 unit() {
        return UNIT;
    }

    /**
     * Creates a new vector with the specified X and Z components.
     *
     * @param x the X component
     * @param z the Z component
     * @return the new vector
     */
    public static Vector2 of(double x, double z) {
        return new Vector2(x, z);
    }

    /**
     * Get the length of this vector.
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Get the squared length of this vector.
     *
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        return x * x + z * z;
    }

    /**
     * Get the distance from this vector to another.
     *
     * @param other another vector
     * @return the distance between vectors
     */
    public double distance(Vector2 other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Get the squared distance between two vectors.
     *
     * @param other another vector
     * @return the squared distance
     */
    public double distanceSquared(Vector2 other) {
        double dx = other.getX() - x;
        double dz = other.getZ() - z;
        return dx * dx + dz * dz;
    }

    /**
     * Extrudes this vector to a 3D vector with the specified Y component.
     *
     * @param y the Y component
     * @return the new 3D vector
     */
    public Vector3 extrude(double y) {
        return Vector3.of(this.x, y, this.z);
    }

    /**
     * Get the X component.
     *
     * @return the X component
     */
    public double getX() {
        return x;
    }

    /**
     * Get the Z component.
     *
     * @return the Z component
     */
    public double getZ() {
        return z;
    }

    /**
     * Get the block X coordinate (floored value of X).
     *
     * @return the block X coordinate
     */
    public int getBlockX() {
        return (int) Math.floor(x);
    }

    /**
     * Get the block Z coordinate (floored value of Z).
     *
     * @return the block Z coordinate
     */
    public int getBlockZ() {
        return (int) Math.floor(z);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + Double.hashCode(x);
        hash = 31 * hash + Double.hashCode(z);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2 other)) return false;
        return FloatingPointFunctions.equals(this.x, other.x) && FloatingPointFunctions.equals(this.z, other.z);
    }

    /**
     * Converts this vector to a mutable vector.
     *
     * @return the mutable vector
     */
    public Mutable mutable() {
        return new Mutable(x, z);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + z + ")";
    }

    public static class Mutable extends Vector2 {

        private Mutable(double x, double z) {
            super(x, z);
        }

        /**
         * Get the X component.
         *
         * @return the X component
         */
        public double getX() {
            return x;
        }

        /**
         * Set the X component.
         *
         * @param x the new X component
         * @return this vector for chaining
         */
        public Mutable setX(double x) {
            this.x = x;
            return this;
        }

        /**
         * Get the Z component.
         *
         * @return the Z component
         */
        public double getZ() {
            return z;
        }

        /**
         * Set the Z component.
         *
         * @param z the new Z component
         * @return this vector for chaining
         */
        public Mutable setZ(double z) {
            this.z = z;
            return this;
        }

        /**
         * Converts this mutable vector to an immutable vector.
         *
         * @return the immutable vector
         */
        public Vector2 immutable() {
            return Vector2.of(x, z);
        }

        /**
         * Get the length of this vector.
         *
         * @return the length of the vector
         */
        public double length() {
            return Math.sqrt(lengthSquared());
        }

        /**
         * Get the squared length of this vector.
         *
         * @return the squared length of the vector
         */
        public double lengthSquared() {
            return x * x + z * z;
        }

        /**
         * Adds the specified X and Z components to this vector.
         *
         * @param x the X component to add
         * @param z the Z component to add
         * @return this vector for chaining
         */
        public Mutable add(double x, double z) {
            this.x += x;
            this.z += z;
            return this;
        }

        /**
         * Multiplies the X and Z components by a value.
         *
         * @param m the value to multiply
         * @return this vector for chaining
         */
        public Mutable multiply(double m) {
            x *= m;
            z *= m;
            return this;
        }

        /**
         * Adds another vector to this vector.
         *
         * @param other the vector to add
         * @return this vector for chaining
         */
        public Mutable add(Vector2 other) {
            x += other.getX();
            z += other.getZ();
            return this;
        }

        /**
         * Subtracts another vector from this vector.
         *
         * @param other the vector to subtract
         * @return this vector for chaining
         */
        public Mutable subtract(Vector2 other) {
            x -= other.getX();
            z -= other.getZ();
            return this;
        }

        /**
         * Normalizes this vector to length 1.
         *
         * @return this vector for chaining
         */
        public Mutable normalize() {
            divide(length());
            return this;
        }

        /**
         * Divides the X and Z components by a value.
         *
         * @param d the divisor
         * @return this vector for chaining
         */
        public Mutable divide(double d) {
            x /= d;
            z /= d;
            return this;
        }
    }
}