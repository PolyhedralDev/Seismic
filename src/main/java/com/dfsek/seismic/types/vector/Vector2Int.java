package com.dfsek.seismic.types.vector;

import com.dfsek.seismic.types.Rotation;

public class Vector2Int {
    private static final Vector2Int ZERO = new Vector2Int(0, 0);
    private static final Vector2Int UNIT = new Vector2Int(0, 1);
    protected int x, z;

    protected Vector2Int(int x, int z) {
        this.x = x;
        this.z = z;
    }

    /**
     * Returns a vector with both components set to zero.
     *
     * @return the zero vector
     */
    public static Vector2Int zero() {
        return ZERO;
    }

    /**
     * Returns a unit vector.
     *
     * @return the unit vector
     */
    public static Vector2Int unit() {
        return UNIT;
    }

    /**
     * Creates a new vector with the specified X and Z components.
     *
     * @param x the X component
     * @param z the Z component
     * @return the new vector
     */
    public static Vector2Int of(int x, int z) {
        return new Vector2Int(x, z);
    }

    /**
     * Get the X component.
     *
     * @return the X component
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Z component.
     *
     * @return the Z component
     */
    public int getZ() {
        return z;
    }

    /**
     * Converts this vector to a 3D vector with the specified Y component.
     *
     * @param y the Y component
     * @return the new 3D vector
     */
    public Vector3Int toVector3(int y) {
        return new Vector3Int(x, y, z);
    }

    /**
     * Converts this vector to a mutable vector.
     *
     * @return the mutable vector
     */
    public Mutable mutable() {
        return new Mutable(x, z);
    }

    /**
     * Rotates this vector by the specified rotation.
     *
     * @param rotation the rotation to apply
     * @return the rotated vector
     */
    public Vector2Int rotate(Rotation rotation) {
        return switch(rotation) {
            case CW_90 -> of(z, -x);
            case CCW_90 -> of(-z, x);
            case CW_180 -> of(-x, -z);
            default -> this;
        };
    }

    @Override
    public int hashCode() {
        return (31 * x) + z;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vector2Int that) {
            return this.x == that.x && this.z == that.z;
        }
        return false;
    }

    public static class Mutable extends Vector2Int {

        protected Mutable(int x, int z) {
            super(x, z);
        }

        /**
         * Get the Z component.
         *
         * @return the Z component
         */
        public int getZ() {
            return z;
        }

        /**
         * Set the Z component.
         *
         * @param z the new Z component
         */
        public void setZ(int z) {
            this.z = z;
        }

        /**
         * Get the X component.
         *
         * @return the X component
         */
        public int getX() {
            return x;
        }

        /**
         * Set the X component.
         *
         * @param x the new X component
         */
        public void setX(int x) {
            this.x = x;
        }

        /**
         * Converts this mutable vector to an immutable vector.
         *
         * @return the immutable vector
         */
        public Vector2Int immutable() {
            return new Vector2Int(x, z);
        }
    }
}