package com.polyhedraldevelopment.seismic.type.vector;

import com.polyhedraldevelopment.seismic.api.type.Rotation;
import com.polyhedraldevelopment.seismic.api.vector.Vector;
import com.polyhedraldevelopment.seismic.api.vector.Vector3Int;
import com.polyhedraldevelopment.seismic.math.algebra.AlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.algebra.LinearAlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.floatingpoint.FloatingPointFunctions;
import org.jetbrains.annotations.NotNull;


public class Vector3IntImpl implements Vector3Int<Vector3IntImpl, Vector3Impl> {
    private static final Vector3IntImpl ZERO = new Vector3IntImpl(0, 0, 0);
    private static final Vector3IntImpl UNIT = new Vector3IntImpl(0, 1, 0);
    protected int x, y, z;

    Vector3IntImpl(int x, int y, int z) {
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
    public static Vector3IntImpl of(int x, int y, int z) {
        return new Vector3IntImpl(x, y, z);
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    @Override
    public @NotNull Vector3Impl toFloat() {
        return new Vector3Impl((double) this.x, (double) this.y, (double) this.z);
    }

    @Override
    public @NotNull Vector3IntImpl zero() {
        return ZERO;
    }

    @Override
    public @NotNull Vector3IntImpl unit() {
        return UNIT;
    }

    @Override
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    @Override
    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    @Override
    public double inverseLength() {
        return 1.0 / length();
    }

    @Override
    public double inverseLengthSquared() {
        return AlgebraFunctions.invSqrt(lengthSquared());
    }

    @Override
    public double distance(@NotNull Vector3IntImpl vector) {
        return Math.sqrt(distanceSquared(vector));
    }

    @Override
    public double distanceSquared(@NotNull Vector3IntImpl vector) {
        int dx = this.x - vector.x;
        int dy = this.y - vector.y;
        int dz = this.z - vector.z;
        return dx * dx + dy * dy + dz * dz;
    }

    @Override
    public double dot(@NotNull Vector3IntImpl vector) {
        return LinearAlgebraFunctions.dotProduct(this.x, this.y, this.z, vector.x, vector.y, vector.z);
    }

    @Override
    public @NotNull Vector3IntImpl copy() {
        return new Vector3IntImpl(this.x, this.y, this.z);
    }

    @Override
    public @NotNull Vector.Mutable<Vector3IntImpl> mutable() {
        return new Vector3IntImpl.Mutable(this.x, this.y, this.z);
    }

    @Override
    public int hashCode() {
        return (31 * x) + (31 * y) + z;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Vector3Int<?, ?> other)) return false;
        return FloatingPointFunctions.equals(x, other.getX()) && FloatingPointFunctions.equals(y, other.getY()) &&
               FloatingPointFunctions.equals(z, other.getZ());
    }


    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public static class Mutable extends Vector3IntImpl implements Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> {
        /**
         * Creates a new instance of `Mutable` with the specified x, y, and z coordinates.
         *
         * @param x the x-coordinate of the vector
         * @param y the y-coordinate of the vector
         * @param z the z-coordinate of the vector
         * @return a new `Mutable` instance with the specified coordinates
         */
        public static Vector3IntImpl.Mutable of(int x, int y, int z) {
            return new Vector3IntImpl.Mutable(x, y, z);
        }

        private Mutable(int x, int y, int z) {
            super(x, y, z);
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> set(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> setX(int x) {
            this.x = x;
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> setY(int y) {
            this.y = y;
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> setZ(int z) {
            this.z = z;
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> add(int x, int y, int z) {
            this.x += x;
            this.y += y;
            this.z += z;
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> add(@NotNull Vector3Int<?, ?> vector) {
            this.x += vector.getX();
            this.y += vector.getY();
            this.z += vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> addScalar(int scalar) {
            this.x += scalar;
            this.y += scalar;
            this.z += scalar;
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> sub(int x, int y, int z) {
            this.x -= x;
            this.y -= y;
            this.z -= z;
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> sub(@NotNull Vector3Int<?, ?> vector) {
            this.x -= vector.getX();
            this.y -= vector.getY();
            this.z -= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> subScalar(int scalar) {
            this.x -= scalar;
            this.y -= scalar;
            this.z -= scalar;
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> mul(int x, int y, int z) {
            this.x *= x;
            this.y *= y;
            this.z *= z;
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> mul(@NotNull Vector3Int<?, ?> vector) {
            this.x *= vector.getX();
            this.y *= vector.getY();
            this.z *= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> mulScalar(int scalar) {
            this.x *= scalar;
            this.y *= scalar;
            this.z *= scalar;
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> div(int x, int y, int z) {
            this.x /= x;
            this.y /= y;
            this.z /= z;
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> div(@NotNull Vector3Int<?, ?> vector) {
            this.x /= vector.getX();
            this.y /= vector.getY();
            this.z /= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector3Int.Mutable<Vector3IntImpl, Vector3Impl> divScalar(int scalar) {
            this.x /= scalar;
            this.y /= scalar;
            this.z /= scalar;
            return this;
        }

        @Override
        public @NotNull Vector.Mutable<Vector3IntImpl> normalize() {
            double length = length();
            if (length != 0) {
                this.x /= length;
                this.y /= length;
                this.z /= length;
            }
            return this;
        }


        //TODO PROPER ROT
        @Override
        public @NotNull Vector.Mutable<Vector3IntImpl> rotate(@NotNull Rotation rotation) {
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

        @Override
        public @NotNull Vector3IntImpl immutable() {
            return this;
        }
    }
}
