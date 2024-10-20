package com.dfsek.seismic.math.numericanalysis.interpolation.sigmoid;

public class SigmoidFunctions {

    public static double logisticCurve(double a, double b) {
        return 1 / (Math.exp(-1 * a * b));
    }
}
