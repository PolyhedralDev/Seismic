package com.polyhedraldevelopment.seismic.type;

import com.polyhedraldevelopment.seismic.math.numericanalysis.interpolation.InterpolationFunctions;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;


public class CubicSpline {

    private final double[] fromValues;
    private final double[] toValues;
    private final double[] gradients;

    /**
     * Constructs a CubicSpline from a list of points.
     *
     * @param points the list of points to construct the spline from
     */
    public CubicSpline(List<Point> points) {
        Collections.sort(points);

        this.fromValues = new double[points.size()];
        this.toValues = new double[points.size()];
        this.gradients = new double[points.size()];

        for(int i = 0; i < points.size(); i++) {
            fromValues[i] = points.get(i).from;
            toValues[i] = points.get(i).to;
            gradients[i] = points.get(i).gradient;
        }
    }

    /**
     * Calculates the interpolated value for the given input using the provided arrays.
     *
     * @param in         the input value
     * @param fromValues the array of from values
     * @param toValues   the array of to values
     * @param gradients  the array of gradients
     *
     * @return the interpolated value
     */
    public static double calculate(double in, double[] fromValues, double[] toValues, double[] gradients) {
        int pointIdx = CubicSpline.floorBinarySearch(in, fromValues) - 1;

        int pointIdxLast = fromValues.length - 1;

        if(pointIdx < 0) { // If to left of first point return linear function intersecting said point using point's gradient
            return gradients[0] * (in - fromValues[0]) + toValues[0];
        } else if(pointIdx == pointIdxLast) { // Do same if to right of last point
            return gradients[pointIdxLast] * (in - fromValues[pointIdxLast]) + toValues[pointIdxLast];
        } else {
            double fromLeft = fromValues[pointIdx];
            double fromRight = fromValues[pointIdx + 1];

            double toLeft = toValues[pointIdx];
            double toRight = toValues[pointIdx + 1];

            double gradientLeft = gradients[pointIdx];
            double gradientRight = gradients[pointIdx + 1];

            double fromDelta = fromRight - fromLeft;
            double toDelta = toRight - toLeft;

            double t = (in - fromLeft) / fromDelta;

            return InterpolationFunctions.lerp(t, toLeft, toRight) + t * (1.0F - t) * InterpolationFunctions.lerp(t,
                gradientLeft * fromDelta - toDelta,
                -gradientRight * fromDelta + toDelta);
        }
    }

    private static int floorBinarySearch(double targetValue, double[] values) {
        int left = 0;
        int right = values.length;
        int idx = right - left;
        while(idx > 0) {
            int halfDelta = idx / 2;
            int mid = left + halfDelta;
            if(targetValue < values[mid]) {
                idx = halfDelta;
            } else {
                left = mid + 1;
                idx -= halfDelta + 1;
            }
        }
        return left;
    }

    /**
     * Applies the cubic spline interpolation to the given input.
     *
     * @param in the input value
     *
     * @return the interpolated value
     */
    public double apply(double in) {
        return CubicSpline.calculate(in, fromValues, toValues, gradients);
    }


    public record Point(double from, double to, double gradient) implements Comparable<Point> {

        /**
         * Compares this Point to another Point based on the 'from' value.
         *
         * @param o the other Point to compare to
         *
         * @return a negative integer, zero, or a positive integer as this Point's 'from' value
         *     is less than, equal to, or greater than the specified Point's 'from' value
         */
        @Override
        public int compareTo(@NotNull CubicSpline.Point o) {
            return Double.compare(from, o.from);
        }
    }
}
