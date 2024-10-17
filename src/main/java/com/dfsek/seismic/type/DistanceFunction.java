package com.dfsek.seismic.type;

import com.dfsek.seismic.math.algebra.AlgebraFunctions;
import com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;


public enum DistanceFunction {

    Euclidean {
        @Override
        public double getDistance(double x, double y) {
            return Math.sqrt(ArithmeticFunctions.fma(x, x, y * y));
        }

        @Override
        public double getDistance(double x, double y, double z) {
            return Math.sqrt(ArithmeticFunctions.fma(x, x, ArithmeticFunctions.fma(y, y, z * z)));
        }

        @Override
        public double getInverseDistance(double x, double y) {
            return AlgebraFunctions.invSqrt(ArithmeticFunctions.fma(x, x, y * y));
        }

        @Override
        public double getInverseDistance(double x, double y, double z) {
            return AlgebraFunctions.invSqrt(ArithmeticFunctions.fma(x, x, ArithmeticFunctions.fma(y, y, z * z)));
        }
    },
    EuclideanSq {
        @Override
        public double getDistance(double x, double y) {
            return ArithmeticFunctions.fma(x, x, y * y);
        }

        @Override
        public double getDistance(double x, double y, double z) {
            return ArithmeticFunctions.fma(x, x, ArithmeticFunctions.fma(y, y, z * z));
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
            return Math.abs(x) + Math.abs(y) + ArithmeticFunctions.fma(x, x, y * y);
        }

        @Override
        public double getDistance(double x, double y, double z) {
            return Math.abs(x) + Math.abs(y) + Math.abs(z) + ArithmeticFunctions.fma(x, x, ArithmeticFunctions.fma(y, y, z * z));
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
