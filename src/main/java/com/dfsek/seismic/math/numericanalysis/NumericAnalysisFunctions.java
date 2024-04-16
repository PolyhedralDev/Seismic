package com.dfsek.seismic.math.numericanalysis;

import  com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;

public class NumericAnalysisFunctions {
    /**
     * Returns the result of a 1D linear interpolation between two points.
     *
     * @param v0 the value at the left corner of the first line in the 1x2 grid.
     * @param v1 the value at the right corner of the first line in the 1x2 grid.
     * @param t  the interpolation parameter.
     * @return the interpolated value.
     */
    public static double lerp(double v0, double v1, double t) {
        return ArithmeticFunctions.fma((v1 - v0), t, v0);
    }

    /**
     * Returns the result of a 2D bilinear interpolation between four points.
     *
     * @param v00 the value at the front-top-left corner of the first 2x2 grid in the 2x2x2 grid.
     * @param v10 the value at the front-top-right corner of the first 2x2 grid in the 2x2x2 grid.
     * @param v01 the value at the front-bottom-left corner of the first 2x2 grid in the 2x2x2 grid.
     * @param v11 the value at the front-bottom-right corner of the first 2x2 grid in the 2x2x2 grid.
     * @param tx  the horizontal interpolation parameter.
     * @param ty  the vertical interpolation parameter.
     * @return the interpolated value.
     */
    public static double biLerp(double v00, double v10, double v01, double v11, double tx, double ty) {
        double lerpX1 = lerp(v00, v10, tx);
        double lerpX2 = lerp(v01, v11, tx);
        return lerp(lerpX1, lerpX2, ty);
    }

    /**
     * Returns the result of a 3D trilinear interpolation between eight points.
     *
     * @param v000 the value at the front-top-left corner of the first 2x2 grid in the 2x2x2 grid.
     * @param v100 the value at the front-top-right corner of the first 2x2 grid in the 2x2x2 grid.
     * @param v010 the value at the front-bottom-left corner of the first 2x2 grid in the 2x2x2 grid.
     * @param v110 the value at the front-bottom-right corner of the first 2x2 grid in the 2x2x2 grid.
     * @param v001 the value at the back-top-left corner of the first 2x2 grid in the 2x2x2 grid.
     * @param v101 the value at the back-top-right corner of the first 2x2 grid in the 2x2x2 grid.
     * @param v011 the value at the back-bottom-left corner of the first 2x2 grid in the 2x2x2 grid.
     * @param v111 the value at the back-bottom-right corner of the first 2x2 grid in the 2x2x2 grid.
     * @param tx   the horizontal interpolation parameter.
     * @param ty   the vertical interpolation parameter.
     * @param tz   the depth interpolation parameter.
     * @return the interpolated value.
     */
    public static double triLerp(double v000, double v100, double v010, double v110, double v001, double v101, double v011, double v111, double tx, double ty, double tz) {
        double x00 = biLerp(v000, v100, v010, v110, tx, ty);
        double x11 = biLerp(v001, v101, v011, v111, tx, ty);
        return lerp(x00, x11, tz);
    }

    /**
     * Returns the result of a 1D cubic interpolation between four points.
     *
     * @param v00 the value at the front-left corner of the first cubic line in the 1x4 grid.
     * @param v10 the value at the front-right corner of the first cubic line in the 1x4 grid.
     * @param v01 the value at the back-left corner of the first cubic line in the 1x4 grid.
     * @param v11 the value at the back-right corner of the first cubic line in the 1x4 grid.
     * @param t   the interpolation parameter.
     * @return the interpolated value.
     */
    public static double cubicLerp(double v00, double v10, double v01, double v11, double t) {
        double j = (v00 - v10);
        double p = (v11 - v01) - j;
        double t2 = t * t;
        double h = t2 * (j - p);
        return ArithmeticFunctions.fma((t2 * t), p, h) + ArithmeticFunctions.fma(t, (v01 - v00), v10);
    }

    /**
     * Returns the result of a 2D bicubic interpolation between sixteen points.
     *
     * @param v00 the value at the front-top-left corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v10 the value at the front-top-right corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v20 the value at the front-middle-left corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v30 the value at the front-middle-right corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v01 the value at the front-bottom-left corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v11 the value at the front-bottom-right corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v21 the value at the back-top-left corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v31 the value at the back-top-right corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v02 the value at the back-middle-left corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v12 the value at the back-middle-right corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v22 the value at the front-top-left corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v32 the value at the front-top-right corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v03 the value at the front-middle-left corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v13 the value at the front-middle-right corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v23 the value at the front-bottom-left corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v33 the value at the front-bottom-right corner of the second 4x4 grid in the 4x4x4 grid.
     * @param tx  the horizontal interpolation parameter.
     * @param ty  the vertical interpolation parameter.
     * @return the interpolated value.
     */
    public static double biCubicLerp(double v00, double v10, double v20, double v30,
                                     double v01, double v11, double v21, double v31,
                                     double v02, double v12, double v22, double v32,
                                     double v03, double v13, double v23, double v33,
                                     double tx, double ty) {
        double arr0 = cubicLerp(v00, v10, v20, v30, tx);
        double arr1 = cubicLerp(v01, v11, v21, v31, tx);
        double arr2 = cubicLerp(v02, v12, v22, v32, tx);
        double arr3 = cubicLerp(v03, v13, v23, v33, tx);
        return cubicLerp(arr0, arr1, arr2, arr3, ty);
    }

    /**
     * Returns the result of a 3D tricubic interpolation between sixty-four points.
     *
     * @param v000 the value at the front-top-left corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v100 the value at the front-top-right corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v200 the value at the front-middle-left corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v300 the value at the front-middle-right corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v010 the value at the front-bottom-left corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v110 the value at the front-bottom-right corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v210 the value at the front-middle-left corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v310 the value at the front-middle-right corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v020 the value at the back-top-left corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v120 the value at the back-top-right corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v220 the value at the back-middle-left corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v320 the value at the back-middle-right corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v030 the value at the back-bottom-left corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v130 the value at the back-bottom-right corner of the first 4x4 grid in the 4x4x4 grid.
     * @param v230 the value at the back-middle-left corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v330 the value at the back-middle-right corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v001 the value at the front-top-left corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v101 the value at the front-top-right corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v201 the value at the front-middle-left corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v301 the value at the front-middle-right corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v011 the value at the front-bottom-left corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v111 the value at the front-bottom-right corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v211 the value at the front-middle-left corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v311 the value at the front-middle-right corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v021 the value at the back-top-left corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v121 the value at the back-top-right corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v221 the value at the back-middle-left corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v321 the value at the back-middle-right corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v031 the value at the back-bottom-left corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v131 the value at the back-bottom-right corner of the second 4x4 grid in the 4x4x4 grid.
     * @param v231 the value at the back-middle-left corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v331 the value at the back-middle-right corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v002 the value at the front-top-left corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v102 the value at the front-top-right corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v202 the value at the front-middle-left corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v302 the value at the front-middle-right corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v012 the value at the front-bottom-left corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v112 the value at the front-bottom-right corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v212 the value at the front-middle-left corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v312 the value at the front-middle-right corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v022 the value at the back-top-left corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v122 the value at the back-top-right corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v222 the value at the back-middle-left corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v322 the value at the back-middle-right corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v032 the value at the back-bottom-left corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v132 the value at the back-bottom-right corner of the third 4x4 grid in the 4x4x4 grid.
     * @param v232 the value at the back-middle-left corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v332 the value at the back-middle-right corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v003 the value at the front-top-left corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v103 the value at the front-top-right corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v203 the value at the front-middle-left corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v303 the value at the front-middle-right corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v013 the value at the front-bottom-left corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v113 the value at the front-bottom-right corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v213 the value at the front-middle-left corner of the fifth 4x4 grid in the 4x4x4 grid.
     * @param v313 the value at the front-middle-right corner of the fifth 4x4 grid in the 4x4x4 grid.
     * @param v023 the value at the back-top-left corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v123 the value at the back-top-right corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v223 the value at the back-middle-left corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v323 the value at the back-middle-right corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v033 the value at the back-bottom-left corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v133 the value at the back-bottom-right corner of the fourth 4x4 grid in the 4x4x4 grid.
     * @param v233 the value at the back-middle-left corner of the fifth 4x4 grid in the 4x4x4 grid.
     * @param v333 the value at the back-middle-right corner of the fifth 4x4 grid in the 4x4x4 grid.
     * @param tx   the horizontal interpolation parameter.
     * @param ty   the vertical interpolation parameter.
     * @param tz   the depth interpolation parameter.
     * @return the interpolated value.
     */
    public static double triCubicLerp(double v000, double v100, double v200, double v300,
                                      double v010, double v110, double v210, double v310,
                                      double v020, double v120, double v220, double v320,
                                      double v030, double v130, double v230, double v330,
                                      double v001, double v101, double v201, double v301,
                                      double v011, double v111, double v211, double v311,
                                      double v021, double v121, double v221, double v321,
                                      double v031, double v131, double v231, double v331,
                                      double v002, double v102, double v202, double v302,
                                      double v012, double v112, double v212, double v312,
                                      double v022, double v122, double v222, double v322,
                                      double v032, double v132, double v232, double v332,
                                      double v003, double v103, double v203, double v303,
                                      double v013, double v113, double v213, double v313,
                                      double v023, double v123, double v223, double v323,
                                      double v033, double v133, double v233, double v333,
                                      double tx, double ty, double tz) {
        double arr0 = biCubicLerp(v000, v100, v200, v300, v010, v110, v210, v310, v020, v120, v220, v320, v030, v130, v230, v330, tx, ty);
        double arr1 = biCubicLerp(v001, v101, v201, v301, v011, v111, v211, v311, v021, v121, v221, v321, v031, v131, v231, v331, tx, ty);
        double arr2 = biCubicLerp(v002, v102, v202, v302, v012, v112, v212, v312, v022, v122, v222, v322, v032, v132, v232, v332, tx, ty);
        double arr3 = biCubicLerp(v003, v103, v203, v303, v013, v113, v213, v313, v023, v123, v223, v323, v033, v133, v233, v333, tx, ty);
        return cubicLerp(arr0, arr1, arr2, arr3, tz);
    }

    /**
     * Returns the result of a quintic easing function.
     *
     * @param t the interpolation parameter.
     * @return the interpolated value.
     */
    public static double easingQuinticInterpolation(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    /**
     * Returns the result of a Hermite easing function.
     *
     * @param t the interpolation parameter.
     * @return the interpolated value.
     */
    public static double easingHermiteInterpolation(double t) {
        return t * t * (3 - 2 * t);
    }
}