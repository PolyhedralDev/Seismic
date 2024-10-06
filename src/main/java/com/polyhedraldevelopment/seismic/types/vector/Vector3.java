package com.polyhedraldevelopment.seismic.types.vector;

import com.polyhedraldevelopment.seismic.math.algebra.AlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.floatingpoint.FloatingPointFunctions;
import com.polyhedraldevelopment.seismic.math.trigonometry.TrigonometryFunctions;
import org.jetbrains.annotations.NotNull;


public class Vector3 {
    private static final Vector3 ZERO = new Vector3(0, 0, 0);
    private static final Vector3 UNIT = new Vector3(0, 1, 0);
    protected double x, y, z;

    private Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns a vector with all components set to zero.
     *
     * @return the zero vector
     */
    public static Vector3 zero() {
        return ZERO;
    }

    /**
     * Returns a unit vector.
     *
     * @return the unit vector
     */
    public static Vector3 unit() {
        return UNIT;
    }

    /**
     * Creates a new vector with the specified X, Y, and Z components.
     *
     * @param x the X component
     * @param y the Y component
     * @param z the Z component
     *
     * @return the new vector
     */
    public static Vector3 of(double x, double y, double z) {
        return new Vector3(x, y, z);
    }

    /**
     * Get the squared length of this vector.
     *
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        return x * x + y * y + z * z;
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
     * Get the inverse length of this vector.
     *
     * @return the inverse length of the vector
     */
    public double inverseLength() {
        return AlgebraFunctions.invSqrt(lengthSquared());
    }

    /**
     * Get the distance between this vector and another.
     *
     * @param o the other vector
     *
     * @return the distance
     */
    public double distance(@NotNull Vector3 o) {
        return Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2) + Math.pow(z - o.getZ(), 2));
    }

    /**
     * Get the squared distance between this vector and another.
     *
     * @param o the other vector
     *
     * @return the squared distance
     */
    public double distanceSquared(@NotNull Vector3 o) {
        return Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2) + Math.pow(z - o.getZ(), 2);
    }

    /**
     * Calculates the dot product of this vector with another.
     *
     * @param other the other vector
     *
     * @return the dot product
     */
    public double dot(@NotNull Vector3 other) {
        return x * other.getX() + y * other.getY() + z * other.getZ();
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
     * Get the X component.
     *
     * @return the X component
     */
    public double getX() {
        return x;
    }

    /**
     * Get the Y component.
     *
     * @return the Y component
     */
    public double getY() {
        return y;
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
     * Get the block Y coordinate (floored value of Y).
     *
     * @return the block Y coordinate
     */
    public int getBlockY() {
        return (int) Math.floor(y);
    }

    /**
     * Get the block Z coordinate (floored value of Z).
     *
     * @return the block Z coordinate
     */
    public int getBlockZ() {
        return (int) Math.floor(z);
    }

    /**
     * Returns if a vector is normalized.
     *
     * @return whether the vector is normalized
     */
    public boolean isNormalized() {
        return FloatingPointFunctions.equals(this.lengthSquared(), 1);
    }

    /**
     * Returns a hash code for this vector.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }

    /**
     * Checks to see if two objects are equal.
     *
     * @param obj the object to compare to
     *
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Vector3 other)) return false;
        return FloatingPointFunctions.equals(x, other.getX()) && FloatingPointFunctions.equals(y, other.getY()) &&
               FloatingPointFunctions.equals(z, other.getZ());
    }

    /**
     * Converts this vector to an integer vector.
     *
     * @return the integer vector
     */
    public Vector3Int toInt() {
        return Vector3Int.of(getBlockX(), getBlockY(), getBlockZ());
    }

    /**
     * Converts this vector to a mutable vector.
     *
     * @return the mutable vector
     */
    public Mutable mutable() {
        return new Mutable(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }

    public static class Mutable extends Vector3 {
        private Mutable(double x, double y, double z) {
            super(x, y, z);
        }

        /**
         * Creates a new mutable vector with the specified X, Y, and Z components.
         *
         * @param x the X component
         * @param y the Y component
         * @param z the Z component
         *
         * @return the new mutable vector
         */
        public static Mutable of(double x, double y, double z) {
            return new Mutable(x, y, z);
        }

        /**
         * Converts this mutable vector to an immutable vector.
         *
         * @return the immutable vector
         */
        public Vector3 immutable() {
            return Vector3.of(x, y, z);
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
         *
         * @return this vector for chaining
         */
        public Mutable setZ(double z) {
            this.z = z;
            return this;
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
         *
         * @return this vector for chaining
         */
        public Mutable setX(double x) {
            this.x = x;
            return this;
        }

        /**
         * Get the Y component.
         *
         * @return the Y component
         */
        public double getY() {
            return y;
        }

        /**
         * Set the Y component.
         *
         * @param y the new Y component
         *
         * @return this vector for chaining
         */
        public Mutable setY(double y) {
            this.y = y;
            return this;
        }

        /**
         * Get the squared length of this vector.
         *
         * @return the squared length of the vector
         */
        public double lengthSquared() {
            return x * x + y * y + z * z;
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
         * Get the inverse length of this vector.
         *
         * @return the inverse length of the vector
         */
        public double inverseLength() {
            return AlgebraFunctions.invSqrt(lengthSquared());
        }

        /**
         * Normalizes this vector to length 1.
         *
         * @return this vector for chaining
         */
        public Mutable normalize() {
            return this.multiply(this.inverseLength());
        }

        /**
         * Subtracts the specified X, Y, and Z components from this vector.
         *
         * @param x the X component to subtract
         * @param y the Y component to subtract
         * @param z the Z component to subtract
         *
         * @return this vector for chaining
         */
        public Mutable subtract(int x, int y, int z) {
            this.x -= x;
            this.y -= y;
            this.z -= z;
            return this;
        }

        /**
         * Calculates the dot product of this vector with another.
         *
         * @param other the other vector
         *
         * @return the dot product
         */
        public double dot(@NotNull Vector3 other) {
            return x * other.getX() + y * other.getY() + z * other.getZ();
        }

        /**
         * Subtracts another vector from this vector.
         *
         * @param end the vector to subtract
         *
         * @return this vector for chaining
         */
        public Mutable subtract(Vector3 end) {
            x -= end.getX();
            y -= end.getY();
            z -= end.getZ();
            return this;
        }

        /**
         * Multiplies the X, Y, and Z components by a value.
         *
         * @param m the value to multiply
         *
         * @return this vector for chaining
         */
        public Mutable multiply(double m) {
            x *= m;
            y *= m;
            z *= m;
            return this;
        }

        /**
         * Adds the specified X, Y, and Z components to this vector.
         *
         * @param x the X component to add
         * @param y the Y component to add
         * @param z the Z component to add
         *
         * @return this vector for chaining
         */
        public Mutable add(double x, double y, double z) {
            this.x += x;
            this.y += y;
            this.z += z;
            return this;
        }

        /**
         * Adds another vector to this vector.
         *
         * @param other the vector to add
         *
         * @return this vector for chaining
         */
        public Mutable add(Vector3 other) {
            this.x += other.getX();
            this.y += other.getY();
            this.z += other.getZ();
            return this;
        }

        /**
         * Adds an integer vector to this vector.
         *
         * @param other the integer vector to add
         *
         * @return this vector for chaining
         */
        public Mutable add(Vector3Int other) {
            this.x += other.getX();
            this.y += other.getY();
            this.z += other.getZ();
            return this;
        }

        /**
         * Adds a 2D vector to this vector.
         *
         * @param other the 2D vector to add
         *
         * @return this vector for chaining
         */
        public Mutable add(Vector2 other) {
            this.x += other.getX();
            this.z += other.getZ();
            return this;
        }

        /**
         * Rotates the vector around a given arbitrary axis in 3D space.
         *
         * @param axis  the axis to rotate the vector around
         * @param angle the angle to rotate the vector around the axis
         *
         * @return the same vector
         *
         * @throws IllegalArgumentException if the provided axis vector instance is null
         */
        @NotNull
        public Mutable rotateAroundAxis(@NotNull Vector3 axis, double angle) throws IllegalArgumentException {
            return rotateAroundNonUnitAxis(axis.isNormalized() ? axis : axis.mutable().normalize().immutable(), angle);
        }

        /**
         * Rotates the vector around a given arbitrary axis in 3D space.
         *
         * @param axis  the axis to rotate the vector around
         * @param angle the angle to rotate the vector around the axis
         *
         * @return the same vector
         *
         * @throws IllegalArgumentException if the provided axis vector instance is null
         */
        @NotNull
        public Mutable rotateAroundNonUnitAxis(@NotNull Vector3 axis, double angle) throws IllegalArgumentException {
            double x = getX(), y = getY(), z = getZ();
            double x2 = axis.getX(), y2 = axis.getY(), z2 = axis.getZ();

            double cosTheta = TrigonometryFunctions.cos(angle);
            double sinTheta = TrigonometryFunctions.sin(angle);
            double dotProduct = this.dot(axis);

            double xPrime = x2 * dotProduct * (1d - cosTheta)
                            + x * cosTheta
                            + (-z2 * y + y2 * z) * sinTheta;
            double yPrime = y2 * dotProduct * (1d - cosTheta)
                            + y * cosTheta
                            + (z2 * x - x2 * z) * sinTheta;
            double zPrime = z2 * dotProduct * (1d - cosTheta)
                            + z * cosTheta
                            + (-y2 * x + x2 * y) * sinTheta;

            return setX(xPrime).setY(yPrime).setZ(zPrime);
        }

        /**
         * Rotates the vector around the X axis.
         *
         * @param angle the angle to rotate the vector about (in radians)
         *
         * @return the same vector
         */
        @NotNull
        public Mutable rotateAroundX(double angle) {
            double angleCos = TrigonometryFunctions.cos(angle);
            double angleSin = TrigonometryFunctions.sin(angle);

            double y = angleCos * getY() - angleSin * getZ();
            double z = angleSin * getY() + angleCos * getZ();
            return setY(y).setZ(z);
        }

        /**
         * Rotates the vector around the Y axis.
         *
         * @param angle the angle to rotate the vector about (in radians)
         *
         * @return the same vector
         */
        @NotNull
        public Mutable rotateAroundY(double angle) {
            double angleCos = TrigonometryFunctions.cos(angle);
            double angleSin = TrigonometryFunctions.sin(angle);

            double x = angleCos * getX() + angleSin * getZ();
            double z = -angleSin * getX() + angleCos * getZ();
            return setX(x).setZ(z);
        }

        /**
         * Rotates the vector around the Z axis.
         *
         * @param angle the angle to rotate the vector about (in radians)
         *
         * @return the same vector
         */
        @NotNull
        public Mutable rotateAroundZ(double angle) {
            double angleCos = TrigonometryFunctions.cos(angle);
            double angleSin = TrigonometryFunctions.sin(angle);

            double x = angleCos * getX() - angleSin * getY();
            double y = angleSin * getX() + angleCos * getY();
            return setX(x).setY(y);
        }

        @Override
        public int hashCode() {
            int hash = 13;
            hash = 79 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
            hash = 79 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
            hash = 79 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
            return hash;
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
         * Get the block Y coordinate (floored value of Y).
         *
         * @return the block Y coordinate
         */
        public int getBlockY() {
            return (int) Math.floor(y);
        }

        /**
         * Get the block Z coordinate (floored value of Z).
         *
         * @return the block Z coordinate
         */
        public int getBlockZ() {
            return (int) Math.floor(z);
        }
    }
}