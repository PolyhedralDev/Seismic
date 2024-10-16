package com.polyhedraldevelopment.seismic.type.vector;

import com.polyhedraldevelopment.seismic.api.type.Rotation;
import com.polyhedraldevelopment.seismic.api.vector.Vector;
import com.polyhedraldevelopment.seismic.api.vector.Vector.Mutable;
import com.polyhedraldevelopment.seismic.api.vector.Vector2;
import com.polyhedraldevelopment.seismic.api.vector.Vector2Int;
import com.polyhedraldevelopment.seismic.api.vector.Vector3;
import com.polyhedraldevelopment.seismic.math.algebra.AlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.algebra.LinearAlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.floatingpoint.FloatingPointFunctions;
import org.jetbrains.annotations.NotNull;


public class Vector2Impl implements Vector2<Vector2Impl, Vector3Impl, Vector2IntImpl> {
    private static final Vector2Impl
        ZERO = new Vector2Impl(0, 0);
    private static final Vector2Impl
        UNIT = new Vector2Impl(0, 1);
    protected double x, z;

    Vector2Impl(double x, double z) {
        this.x = x;
        this.z = z;
    }

    /**
     * Creates a new instance of `Vector2Impl` with the specified x and z coordinates.
     *
     * @param x the x-coordinate of the vector
     * @param z the z-coordinate of the vector
     * @return a new `Vector2Impl` instance with the specified coordinates
     */
    public static Vector2Impl of(double x, double z) {
        return new Vector2Impl(x, z);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public int getFloorX() {
        return (int) Math.floor(x);
    }

    @Override
    public int getFloorZ() {
        return (int) Math.floor(z);
    }

    @Override
    public @NotNull Vector3Impl extrude(double y) {
        return new Vector3Impl(x, y, z);
    }

    @Override
    public @NotNull Vector2IntImpl toInt() {
        return new Vector2IntImpl(getFloorX(), getFloorZ());
    }

    @Override
    public @NotNull Vector2Impl zero() {
        return ZERO;
    }

    @Override
    public @NotNull Vector2Impl unit() {
        return UNIT;
    }

    @Override
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    @Override
    public double lengthSquared() {
        return x * x + z * z;
    }

    @Override
    public double inverseLength() {
        return AlgebraFunctions.invSqrt(lengthSquared());
    }

    @Override
    public double inverseLengthSquared() {
        return 1 / lengthSquared();
    }

    @Override
    public double distance(@NotNull Vector2Impl vector) {
        return Math.sqrt(distanceSquared(vector));
    }

    @Override
    public double distanceSquared(@NotNull Vector2Impl vector) {
        double dx = vector.getX() - x;
        double dz = vector.getZ() - z;
        return dx * dx + dz * dz;
    }

    @Override
    public double dot(@NotNull Vector2Impl vector) {
        return LinearAlgebraFunctions.dotProduct(x, z, vector.getX(), vector.getZ());
    }

    @Override
    public @NotNull Vector2Impl copy() {
        return new Vector2Impl(x, z);
    }

    @Override
    public @NotNull Vector.Mutable<Vector2Impl> mutable() {
        return new Mutable(x, z);
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
        if(!(obj instanceof Vector2<?, ?, ?> other)) return false;
        return FloatingPointFunctions.equals(this.x, other.getX()) && FloatingPointFunctions.equals(this.z, other.getZ());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + z + ")";
    }

    public static class Mutable extends Vector2Impl implements Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> {

        /**
         * Creates a new instance of `Mutable` with the specified x and z coordinates.
         *
         * @param x the x-coordinate of the vector
         * @param z the z-coordinate of the vector
         * @return a new `Mutable` instance with the specified coordinates
         */
        public static Vector2Impl.Mutable of(double x, double z) {
            return new Vector2Impl.Mutable(x, z);
        }

        private Mutable(double x, double z) {
            super(x, z);
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> set(double x, double z) {
            this.x = x;
            this.z = z;
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> setX(double x) {
            this.x = x;
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> setZ(double z) {
            this.z = z;
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> add(double x, double z) {
            this.x += x;
            this.z += z;
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> add(@NotNull Vector2<?, ?, ?> vector) {
            this.x += vector.getX();
            this.z += vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> addScalar(double scalar) {
            this.x += scalar;
            this.z += scalar;
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> sub(double x, double z) {
            this.x -= x;
            this.z -= z;
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> sub(@NotNull Vector2<?, ?, ?> vector) {
            this.x -= vector.getX();
            this.z -= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> subScalar(double scalar) {
            this.x -= scalar;
            this.z -= scalar;
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> mul(double x, double z) {
            this.x *= x;
            this.z *= z;
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> mul(@NotNull Vector2<?, ?, ?> vector) {
            this.x *= vector.getX();
            this.z *= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> mulScalar(double scalar) {
            this.x *= scalar;
            this.z *= scalar;
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> div(double x, double z) {
            this.x /= x;
            this.z /= z;
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> div(@NotNull Vector2<?, ?, ?> vector) {
            this.x /= vector.getX();
            this.z /= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector2.Mutable<Vector2Impl, Vector3Impl, Vector2IntImpl> divScalar(double scalar) {
            this.x /= scalar;
            this.z /= scalar;
            return this;
        }

        @Override
        public @NotNull Vector.Mutable<Vector2Impl> normalize() {
            divScalar(length());
            return this;
        }

        @Override
        public @NotNull Vector.Mutable<Vector2Impl> rotate(@NotNull Rotation rotation) {
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

        @Override
        public @NotNull Vector2Impl immutable() {
            return this;
        }
    }
}
