package com.polyhedraldevelopment.seismic.type.vector;

import com.polyhedraldevelopment.seismic.api.type.Rotation;
import com.polyhedraldevelopment.seismic.api.vector.Vector;
import com.polyhedraldevelopment.seismic.api.vector.Vector.Mutable;
import com.polyhedraldevelopment.seismic.api.vector.Vector2;
import com.polyhedraldevelopment.seismic.api.vector.Vector2Int;
import com.polyhedraldevelopment.seismic.math.algebra.AlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.algebra.LinearAlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.floatingpoint.FloatingPointFunctions;
import org.jetbrains.annotations.NotNull;


public class Vector2IntImpl implements Vector2Int<Vector2IntImpl, Vector3IntImpl, Vector2Impl> {
    private static final Vector2IntImpl ZERO = new Vector2IntImpl(0, 0);
    private static final Vector2IntImpl UNIT = new Vector2IntImpl(0, 1);
    protected int x, z;

    Vector2IntImpl(int x, int z) {
        this.x = x;
        this.z = z;
    }

    /**
     * Creates a new instance of `Vector2IntImpl` with the specified x and z coordinates.
     *
     * @param x the x-coordinate of the vector
     * @param z the z-coordinate of the vector
     * @return a new `Vector2IntImpl` instance with the specified coordinates
     */
    public static Vector2IntImpl of(int x, int z) {
        return new Vector2IntImpl(x, z);
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    @Override
    public @NotNull Vector3IntImpl extrude(int y) {
        return new Vector3IntImpl(this.x, y, this.z);
    }

    @Override
    public @NotNull Vector2Impl toFloat() {
        return new Vector2Impl(this.x, this.z);
    }

    @Override
    public @NotNull Vector2IntImpl zero() {
        return ZERO;
    }

    @Override
    public @NotNull Vector2IntImpl unit() {
        return UNIT;
    }

    @Override
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    @Override
    public double lengthSquared() {
        return this.x * this.x + this.z * this.z;
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
    public double distance(@NotNull Vector2IntImpl vector) {
        return Math.sqrt(distanceSquared(vector));
    }

    @Override
    public double distanceSquared(@NotNull Vector2IntImpl vector) {
        int dx = this.x - vector.x;
        int dz = this.z - vector.z;
        return dx * dx + dz * dz;
    }

    @Override
    public double dot(@NotNull Vector2IntImpl vector) {
        return LinearAlgebraFunctions.dotProduct(this.x, this.z, vector.x, vector.z);
    }

    @Override
    public @NotNull Vector2IntImpl copy() {
        return new Vector2IntImpl(this.x, this.z);
    }

    @Override
    public @NotNull Vector.Mutable<Vector2IntImpl> mutable() {
        return new Vector2IntImpl.Mutable(this.x, this.z);
    }

    @Override
    public int hashCode() {
        return (31 * x) + z;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Vector2Int<?, ?, ?> other)) return false;
        return FloatingPointFunctions.equals(this.x, other.getX()) && FloatingPointFunctions.equals(this.z, other.getZ());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + z + ")";
    }

    public static class Mutable extends Vector2IntImpl implements Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> {

        /**
         * Creates a new instance of `Mutable` with the specified x and z coordinates.
         *
         * @param x the x-coordinate of the vector
         * @param z the z-coordinate of the vector
         * @return a new `Mutable` instance with the specified coordinates
         */
        public static Vector2IntImpl.Mutable of(int x, int z) {
            return new Vector2IntImpl.Mutable(x, z);
        }

        private Mutable(int x, int z) {
            super(x, z);
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> set(int x, int z) {
            this.x = x;
            this.z = z;
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> setX(int x) {
            this.x = x;
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> setZ(int z) {
            this.z = z;
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> add(int x, int z) {
            this.x += x;
            this.z += z;
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> add(@NotNull Vector2Int<?, ?, ?> vector) {
            this.x += vector.getX();
            this.z += vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> addScalar(int scalar) {
            this.x += scalar;
            this.z += scalar;
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> sub(int x, int z) {
            this.x -= x;
            this.z -= z;
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> sub(@NotNull Vector2Int<?, ?, ?> vector) {
            this.x -= vector.getX();
            this.z -= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> subScalar(int scalar) {
            this.x -= scalar;
            this.z -= scalar;
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> mul(int x, int z) {
            this.x *= x;
            this.z *= z;
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> mul(@NotNull Vector2Int<?, ?, ?> vector) {
            this.x *= vector.getX();
            this.z *= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> mulScalar(int scalar) {
            this.x *= scalar;
            this.z *= scalar;
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> div(int x, int z) {
            this.x /= x;
            this.z /= z;
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> div(@NotNull Vector2Int<?, ?, ?> vector) {
            this.x /= vector.getX();
            this.z /= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector2Int.Mutable<Vector2IntImpl, Vector3IntImpl, Vector2Impl> divScalar(int scalar) {
            this.x /= scalar;
            this.z /= scalar;
            return this;
        }

        @Override
        public @NotNull Vector.Mutable<Vector2IntImpl> normalize() {
            double length = length();
            if (length != 0) {
                this.x /= length;
                this.z /= length;
            }
            return this;
        }

        @Override
        public @NotNull Vector.Mutable<Vector2IntImpl> rotate(@NotNull Rotation rotation) {
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
        public @NotNull Vector2IntImpl immutable() {
            return this;
        }
    }
}
