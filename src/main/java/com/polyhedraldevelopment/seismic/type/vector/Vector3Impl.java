package com.polyhedraldevelopment.seismic.type.vector;

import com.polyhedraldevelopment.seismic.api.type.Rotation;
import com.polyhedraldevelopment.seismic.api.vector.Vector;
import com.polyhedraldevelopment.seismic.api.vector.Vector.Mutable;
import com.polyhedraldevelopment.seismic.api.vector.Vector2;
import com.polyhedraldevelopment.seismic.api.vector.Vector3;
import com.polyhedraldevelopment.seismic.api.vector.Vector3Int;
import com.polyhedraldevelopment.seismic.math.algebra.AlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.algebra.LinearAlgebraFunctions;
import com.polyhedraldevelopment.seismic.math.floatingpoint.FloatingPointFunctions;
import org.jetbrains.annotations.NotNull;


public class Vector3Impl implements Vector3<Vector3Impl, Vector3IntImpl> {
    private static final Vector3Impl ZERO = new Vector3Impl(0, 0, 0);
    private static final Vector3Impl UNIT = new Vector3Impl(0, 1, 0);
    protected double x, y, z;

    Vector3Impl(double x, double y, double z) {
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
    public static Vector3Impl of(double x, double y, double z) {
        return new Vector3Impl(x, y, z);
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public int getFloorX() {
        return (int) Math.floor(this.x);
    }

    @Override
    public int getFloorY() {
        return (int) Math.floor(this.y);
    }

    @Override
    public int getFloorZ() {
        return (int) Math.floor(this.z);
    }

    @Override
    public @NotNull Vector3IntImpl toInt() {
        return new Vector3IntImpl(getFloorX(), getFloorY(), getFloorZ());
    }

    @Override
    public @NotNull Vector3Impl zero() {
        return ZERO;
    }

    @Override
    public @NotNull Vector3Impl unit() {
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
        return AlgebraFunctions.invSqrt(lengthSquared());
    }

    @Override
    public double inverseLengthSquared() {
        return 1.0 / lengthSquared();
    }

    @Override
    public double distance(@NotNull Vector3Impl vector) {
        return Math.sqrt(distanceSquared(vector));
    }

    @Override
    public double distanceSquared(@NotNull Vector3Impl vector) {
        double dx = this.x - vector.x;
        double dy = this.y - vector.y;
        double dz = this.z - vector.z;
        return dx * dx + dy * dy + dz * dz;
    }

    @Override
    public double dot(@NotNull Vector3Impl vector) {
        return LinearAlgebraFunctions.dotProduct(this.x, this.y, this.z, vector.x, vector.y, vector.z);
    }

    @Override
    public @NotNull Vector3Impl copy() {
        return new Vector3Impl(this.x, this.y, this.z);
    }

    @Override
    public @NotNull Vector.Mutable<Vector3Impl> mutable() {
        return new Vector3Impl.Mutable(this.x, this.y, this.z);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Long.hashCode(Double.doubleToLongBits(this.x));
        hash = 79 * hash + Long.hashCode(Double.doubleToLongBits(this.y));
        hash = 79 * hash + Long.hashCode(Double.doubleToLongBits(this.z));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Vector3 other)) return false;
        return FloatingPointFunctions.equals(x, other.getX()) && FloatingPointFunctions.equals(y, other.getY()) &&
               FloatingPointFunctions.equals(z, other.getZ());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }


    public static class Mutable extends Vector3Impl implements Vector3.Mutable<Vector3Impl, Vector3IntImpl> {

        /**
         * Creates a new instance of `Mutable` with the specified x, y, and z coordinates.
         *
         * @param x the x-coordinate of the vector
         * @param y the y-coordinate of the vector
         * @param z the z-coordinate of the vector
         * @return a new `Mutable` instance with the specified coordinates
         */
        public static Vector3Impl.Mutable of(double x, double y, double z) {
            return new Vector3Impl.Mutable(x, y, z);
        }

        private Mutable(double x, double y, double z) {
            super(x, y, z);
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> set(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> setX(double x) {
            this.x = x;
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> setY(double y) {
            this.y = y;
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> setZ(double z) {
            this.z = z;
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> add(double x, double y, double z) {
            this.x += x;
            this.y += y;
            this.z += z;
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> add(@NotNull Vector3<?, ?> vector) {
            this.x += vector.getX();
            this.y += vector.getY();
            this.z += vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> addScalar(double scalar) {
            this.x += scalar;
            this.y += scalar;
            this.z += scalar;
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> sub(double x, double y, double z) {
            this.x -= x;
            this.y -= y;
            this.z -= z;
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> sub(@NotNull Vector3<?, ?> vector) {
            this.x -= vector.getX();
            this.y -= vector.getY();
            this.z -= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> subScalar(double scalar) {
            this.x -= scalar;
            this.y -= scalar;
            this.z -= scalar;
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> mul(double x, double y, double z) {
            this.x *= x;
            this.y *= y;
            this.z *= z;
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> mul(@NotNull Vector3<?, ?> vector) {
            this.x *= vector.getX();
            this.y *= vector.getY();
            this.z *= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> mulScalar(double scalar) {
            this.x *= scalar;
            this.y *= scalar;
            this.z *= scalar;
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> div(double x, double y, double z) {
            this.x /= x;
            this.y /= y;
            this.z /= z;
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> div(@NotNull Vector3<?, ?> vector) {
            this.x /= vector.getX();
            this.y /= vector.getY();
            this.z /= vector.getZ();
            return this;
        }

        @Override
        public @NotNull Vector3.Mutable<Vector3Impl, Vector3IntImpl> divScalar(double scalar) {
            this.x /= scalar;
            this.y /= scalar;
            this.z /= scalar;
            return this;
        }

        @Override
        public @NotNull Vector.Mutable<Vector3Impl> normalize() {
            double length = length();
            if (length != 0) {
                this.x /= length;
                this.y /= length;
                this.z /= length;
            }
            return this;
        }


        //TODO PROPER ROTATION
        @Override
        public @NotNull Vector.Mutable<Vector3Impl> rotate(@NotNull Rotation rotation) {
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
        public @NotNull Vector3Impl immutable() {
            return this;
        }
    }
}
