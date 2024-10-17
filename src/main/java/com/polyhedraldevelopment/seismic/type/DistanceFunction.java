package com.polyhedraldevelopment.seismic.type;

import com.polyhedraldevelopment.seismic.math.algebra.AlgebraFunctions;


public enum DistanceFunction {

    Euclidean {
        @Override
        public double getDistance(double x, double y) {
            return Math.sqrt(x * x + y * y);
        }

        @Override
        public double getDistance(double x, double y, double z) {
            return Math.sqrt(x * x + y * y + z * z);
        }

        @Override
        public double getInverseDistance(double x, double y) {
            return AlgebraFunctions.invSqrt(x * x + y * y);
        }

        @Override
        public double getInverseDistance(double x, double y, double z) {
            return AlgebraFunctions.invSqrt(x * x + y * y + z * z);
        }
    },
    EuclideanSq {
        @Override
        public double getDistance(double x, double y) {
            return x * x + y * y;
        }

        @Override
        public double getDistance(double x, double y, double z) {
            return x * x + y * y + z * z;
        }

        @Override
        public double getInverseDistance(double x, double y) {
            return 1.0 / getDistance(x, y);
        }

        @Override
        public double getInverseDistance(double x, double y, double z) {
            return 1.0 / getDistance(x, y, z);
        }
    },
    Manhattan {
        @Override
        public double getDistance(double x, double y) {
            return Math.abs(x) + Math.abs(y);
        }

        @Override
        public double getDistance(double x, double y, double z) {
            return Math.abs(x) + Math.abs(y) + Math.abs(z);
        }

        @Override
        public double getInverseDistance(double x, double y) {
            return 1.0 / getDistance(x, y);
        }

        @Override
        public double getInverseDistance(double x, double y, double z) {
            return 1.0 / getDistance(x, y, z);
        }
    },
    Hybrid {
        @Override
        public double getDistance(double x, double y) {
            return (Math.abs(x) + Math.abs(y)) + (x * x + y * y);
        }

        @Override
        public double getDistance(double x, double y, double z) {
            return (Math.abs(x) + Math.abs(y) + Math.abs(z)) + (x * x + y * y + z * z);
        }

        @Override
        public double getInverseDistance(double x, double y) {
            return 1.0 / getDistance(x, y);
        }

        @Override
        public double getInverseDistance(double x, double y, double z) {
            return 1.0 / getDistance(x, y, z);
        }
    };

    public abstract double getDistance(double x, double y);

    public abstract double getDistance(double x, double y, double z);

    public abstract double getInverseDistance(double x, double y);

    public abstract double getInverseDistance(double x, double y, double z);
}
