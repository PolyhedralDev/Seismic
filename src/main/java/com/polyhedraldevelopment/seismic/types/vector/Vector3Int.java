package com.polyhedraldevelopment.seismic.types.vector;

public class Vector3Int {
    private static final Vector3Int ZERO = new Vector3Int(0, 0, 0);
    private static final Vector3Int UNIT = new Vector3Int(0, 1, 0);
    protected int x, y, z;

    protected Vector3Int(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the unit vector.
     *
     * @return the unit vector
     */
    public static Vector3Int unit() {
        return UNIT;
    }

    /**
     * Returns the zero vector.
     *
     * @return the zero vector
     */
    public static Vector3Int zero() {
        return ZERO;
    }

    /**
     * Creates a new Vector3Int with the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return the new Vector3Int
     */
    public static Vector3Int of(int x, int y, int z) {
        return new Vector3Int(x, y, z);
    }

    /**
     * Creates a new Vector3Int by adding the specified coordinates to the origin vector.
     *
     * @param origin the origin vector
     * @param x the x-coordinate to add
     * @param y the y-coordinate to add
     * @param z the z-coordinate to add
     * @return the new Vector3Int
     */
    public static Vector3Int of(Vector3Int origin, int x, int y, int z) {
        return new Vector3Int(origin.x + x, origin.y + y, origin.z + z);
    }

    /**
     * Returns the x-coordinate.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the z-coordinate.
     *
     * @return the z-coordinate
     */
    public int getZ() {
        return z;
    }

    /**
     * Converts this vector to a mutable vector.
     *
     * @return the mutable vector
     */
    public Mutable mutable() {
        return new Mutable(x, y, z);
    }

    /**
     * Converts this vector to a Vector3.
     *
     * @return the Vector3
     */
    public Vector3 toVector3() {
        return Vector3.of(x, y, z);
    }

    /**
     * Converts this vector to a mutable Vector3.
     *
     * @return the mutable Vector3
     */
    public Vector3.Mutable toVector3Mutable() {
        return Vector3.Mutable.of(x, y, z);
    }

    public static class Mutable extends Vector3Int {
        protected Mutable(int x, int y, int z) {
            super(x, y, z);
        }

        /**
         * Returns the x-coordinate.
         *
         * @return the x-coordinate
         */
        public int getX() {
            return x;
        }

        /**
         * Sets the x-coordinate.
         *
         * @param x the new x-coordinate
         */
        public void setX(int x) {
            this.x = x;
        }

        /**
         * Returns the y-coordinate.
         *
         * @return the y-coordinate
         */
        public int getY() {
            return y;
        }

        /**
         * Sets the y-coordinate.
         *
         * @param y the new y-coordinate
         */
        public void setY(int y) {
            this.y = y;
        }

        /**
         * Returns the z-coordinate.
         *
         * @return the z-coordinate
         */
        public int getZ() {
            return z;
        }

        /**
         * Sets the z-coordinate.
         *
         * @param z the new z-coordinate
         */
        public void setZ(int z) {
            this.z = z;
        }

        /**
         * Converts this mutable vector to an immutable vector.
         *
         * @return the immutable vector
         */
        public Vector3Int immutable() {
            return Vector3Int.of(x, y, z);
        }

        /**
         * Adds the specified coordinates to this vector.
         *
         * @param x the x-coordinate to add
         * @param y the y-coordinate to add
         * @param z the z-coordinate to add
         * @return this vector after addition
         */
        public Mutable add(int x, int y, int z) {
            this.x += x;
            this.y += y;
            this.z += z;
            return this;
        }

        /**
         * Converts this vector to a Vector3.
         *
         * @return the Vector3
         */
        public Vector3 toVector3() {
            return Vector3.of(x, y, z);
        }
    }
}