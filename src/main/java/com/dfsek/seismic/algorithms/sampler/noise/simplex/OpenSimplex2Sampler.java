/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.simplex;

import com.dfsek.seismic.algorithms.sampler.noise.NoiseFunction;
import com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.util.UnsafeUtils;


/**
 * NoiseSampler implementation to provide OpenSimplex2 noise.
 */
public class OpenSimplex2Sampler extends OpenSimplex2StyleSampler {
    public OpenSimplex2Sampler(double frequency, long salt) {
        super(frequency, salt);
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y) {
        int seed = (int) sl;
        // 2D OpenSimplex2 case uses the same algorithm as ordinary Simplex.
        double s = (x + y) * OpenSimplex2StyleSampler.SKEW_2D;
        x += s;
        y += s;


        int i = FloatingPointFunctions.floor(x);
        int j = FloatingPointFunctions.floor(y);
        double xi = x - i;
        double yi = y - j;

        double t = (xi + yi) * OpenSimplex2StyleSampler.UNSKEW_2D;
        double x0 = xi - t;
        double y0 = yi - t;

        i *= NoiseFunction.PRIME_X;
        j *= NoiseFunction.PRIME_Y;

        double value = 0;

        double[] grads = SimplexStyleSampler.GRADIENTS_2D;

        double a = OpenSimplex2StyleSampler.RSQUARED_2D - x0 * x0 - y0 * y0;
        if(a > 0) {
            double aa = a * a;
            value = aa * aa * SimplexStyleSampler.gradCoord(grads, seed, i, j, x0, y0);
        }


        double c = ArithmeticFunctions.fma(OpenSimplex2StyleSampler.GRADIENT_SCALE_PRIMARY, t, (
            OpenSimplex2StyleSampler.GRADIENT_SCALE_SECONDARY + a));
        if(c > 0) {
            double x2 = x0 + OpenSimplex2StyleSampler.DOUBLE_UNSKEW_2D_MINUS_1;
            double y2 = y0 + OpenSimplex2StyleSampler.DOUBLE_UNSKEW_2D_MINUS_1;
            double cc = c * c;
            value = ArithmeticFunctions.fma(cc * cc,
                SimplexStyleSampler.gradCoord(grads, seed, i + NoiseFunction.PRIME_X, j + NoiseFunction.PRIME_Y, x2, y2), value);
        }

        if(y0 > x0) {
            double x1 = x0 + OpenSimplex2StyleSampler.UNSKEW_2D;
            double y1 = y0 + OpenSimplex2StyleSampler.UNSKEW_2D_MINUS_1;
            double b = OpenSimplex2StyleSampler.RSQUARED_2D - x1 * x1 - y1 * y1;
            if(b > 0) {
                double bb = b * b;
                value = ArithmeticFunctions.fma(bb * bb,
                    SimplexStyleSampler.gradCoord(grads, seed, i, j + NoiseFunction.PRIME_Y, x1, y1), value);
            }
        } else {
            double x1 = x0 + OpenSimplex2StyleSampler.UNSKEW_2D_MINUS_1;
            double y1 = y0 + OpenSimplex2StyleSampler.UNSKEW_2D;
            double b = OpenSimplex2StyleSampler.RSQUARED_2D - x1 * x1 - y1 * y1;
            if(b > 0) {
                double bb = b * b;
                value = ArithmeticFunctions.fma(bb * bb,
                    SimplexStyleSampler.gradCoord(grads, seed, i + NoiseFunction.PRIME_X, j, x1, y1), value);
            }
        }

        return value * 99.83685446303647;
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y, double z) {
        int seed = (int) sl;
        // 3D OpenSimplex2Sampler case uses two offset rotated cube grids.
        double r = (x + y + z) * OpenSimplex2StyleSampler.ROTATE_3D; // Rotation, not skew
        x = r - x;
        y = r - y;
        z = r - z;


        int i = FloatingPointFunctions.round(x);
        int j = FloatingPointFunctions.round(y);
        int k = FloatingPointFunctions.round(z);
        double x0 = x - i;
        double y0 = y - j;
        double z0 = z - k;

        int xNSign = (int) (-1.0 - x0) | 1;
        int yNSign = (int) (-1.0 - y0) | 1;
        int zNSign = (int) (-1.0 - z0) | 1;

        double ax0 = xNSign * -x0;
        double ay0 = yNSign * -y0;
        double az0 = zNSign * -z0;

        i *= NoiseFunction.PRIME_X;
        j *= NoiseFunction.PRIME_Y;
        k *= NoiseFunction.PRIME_Z;

        double value = 0;
        double a = (OpenSimplex2StyleSampler.RSQUARED_3D - x0 * x0) - ArithmeticFunctions.fma(y0, y0, z0 * z0);

        double[] grads = SimplexStyleSampler.GRADIENTS_3D;

        for(int l = 0; ; l++) {
            if(a > 0) {
                double aa = a * a;
                value = ArithmeticFunctions.fma(aa * aa, SimplexStyleSampler.gradCoord(grads, seed, i, j, k, x0, y0, z0), value);
            }

            if(ax0 >= ay0 && ax0 >= az0) {
                double b = a + ax0 + ax0;
                if(b > 1) {
                    b -= 1;
                    double bb = b * b;
                    value = ArithmeticFunctions.fma(bb * bb,
                        SimplexStyleSampler.gradCoord(grads, seed, i - xNSign * NoiseFunction.PRIME_X, j, k, x0 + xNSign,
                            y0, z0), value);
                }
            } else if(ay0 > ax0 && ay0 >= az0) {
                double b = a + ay0 + ay0;
                if(b > 1) {
                    b -= 1;
                    double bb = b * b;
                    value = ArithmeticFunctions.fma(bb * bb,
                        SimplexStyleSampler.gradCoord(grads, seed, i, j - yNSign * NoiseFunction.PRIME_Y, k, x0,
                            y0 + yNSign, z0), value);
                }
            } else {
                double b = a + az0 + az0;
                if(b > 1) {
                    b -= 1;
                    double bb = b * b;
                    value = ArithmeticFunctions.fma(bb * bb,
                        SimplexStyleSampler.gradCoord(grads, seed, i, j, k - zNSign * NoiseFunction.PRIME_Z, x0, y0,
                            z0 + zNSign), value);
                }
            }

            if(l == 1) break;

            ax0 = 0.5 - ax0;
            ay0 = 0.5 - ay0;
            az0 = 0.5 - az0;

            x0 = xNSign * ax0;
            y0 = yNSign * ay0;
            z0 = zNSign * az0;

            a += (0.75 - ax0) - (ay0 + az0);

            i += (xNSign >> 1) & NoiseFunction.PRIME_X;
            j += (yNSign >> 1) & NoiseFunction.PRIME_Y;
            k += (zNSign >> 1) & NoiseFunction.PRIME_Z;

            xNSign = -xNSign;
            yNSign = -yNSign;
            zNSign = -zNSign;

            seed = ~seed;
        }

        return value * 32.69428253173828125;
    }

    @Override
    public boolean isDifferentiable() {
        return true;
    }

    @Override
    public double[] getNoiseDerivativeRaw(long sl, double x, double y) {
        int seed = (int) sl;
        // 2D OpenSimplex2 case uses the same algorithm as ordinary Simplex.
        double s = (x + y) * OpenSimplex2StyleSampler.SKEW_2D;
        x += s;
        y += s;


        int i = FloatingPointFunctions.floor(x);
        int j = FloatingPointFunctions.floor(y);
        double xi = x - i;
        double yi = y - j;

        double t = (xi + yi) * OpenSimplex2StyleSampler.UNSKEW_2D;
        double x0 = xi - t;
        double y0 = yi - t;

        i *= NoiseFunction.PRIME_X;
        j *= NoiseFunction.PRIME_Y;

        double[] out = { 0.0, 0.0, 0.0 };
        double[] grads = SimplexStyleSampler.GRADIENTS_2D;

        double a = OpenSimplex2StyleSampler.RSQUARED_2D - x0 * x0 - y0 * y0;
        if(a > 0) {
            double aa = a * a, aaa = aa * a, aaaa = aa * aa;
            long gi = SimplexStyleSampler.gradCoordIndex(seed, i, j);

            double gx = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + (((long) gi) << DOUBLE_ARRAY_SHIFT));
            double gy = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + (((long) (gi | 1)) << DOUBLE_ARRAY_SHIFT));

            double rampValue = ArithmeticFunctions.fma(gx, x0, gy * y0);
            out[0] = ArithmeticFunctions.fma(aaaa, rampValue, out[0]);
            out[1] = ArithmeticFunctions.fma(-8 * rampValue * aaa, x0, ArithmeticFunctions.fma(gx, aaaa, out[1]));
            out[2] = ArithmeticFunctions.fma(-8 * rampValue * aaa, y0, ArithmeticFunctions.fma(gy, aaaa, out[2]));
        }

        double c = ArithmeticFunctions.fma(OpenSimplex2StyleSampler.GRADIENT_SCALE_PRIMARY, t, (
            OpenSimplex2StyleSampler.GRADIENT_SCALE_SECONDARY + a));
        if(c > 0) {
            double x2 = x0 + OpenSimplex2StyleSampler.DOUBLE_UNSKEW_2D_MINUS_1;
            double y2 = y0 + OpenSimplex2StyleSampler.DOUBLE_UNSKEW_2D_MINUS_1;
            double cc = c * c, ccc = cc * c, cccc = cc * cc;
            long gi = SimplexStyleSampler.gradCoordIndex(seed, i + NoiseFunction.PRIME_X, j + NoiseFunction.PRIME_Y);

            double gx = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + (((long) gi) << DOUBLE_ARRAY_SHIFT));
            double gy = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + (((long) (gi | 1)) << DOUBLE_ARRAY_SHIFT));

            double rampValue = ArithmeticFunctions.fma(gx, x2, gy * y2);
            out[0] = ArithmeticFunctions.fma(cccc, rampValue, out[0]);
            out[1] = ArithmeticFunctions.fma(-8 * rampValue * ccc, x2, ArithmeticFunctions.fma(gx, cccc, out[1]));
            out[2] = ArithmeticFunctions.fma(-8 * rampValue * ccc, y2, ArithmeticFunctions.fma(gy, cccc, out[2]));
        }

        if(y0 > x0) {
            double x1 = x0 + OpenSimplex2StyleSampler.UNSKEW_2D;
            double y1 = y0 + OpenSimplex2StyleSampler.UNSKEW_2D_MINUS_1;
            double b = OpenSimplex2StyleSampler.RSQUARED_2D - x1 * x1 - y1 * y1;
            if(b > 0) {
                double bb = b * b, bbb = bb * b, bbbb = bb * bb;
                long gi = SimplexStyleSampler.gradCoordIndex(seed, i, j + NoiseFunction.PRIME_Y);

                double gx = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + (gi << DOUBLE_ARRAY_SHIFT));
                double gy = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + ((gi | 1) << DOUBLE_ARRAY_SHIFT));

                double rampValue = ArithmeticFunctions.fma(gx, x1, gy * y1);
                out[0] = ArithmeticFunctions.fma(bbbb, rampValue, out[0]);
                out[1] = ArithmeticFunctions.fma(-8 * rampValue * bbb, x1, ArithmeticFunctions.fma(gx, bbbb, out[1]));
                out[2] = ArithmeticFunctions.fma(-8 * rampValue * bbb, y1, ArithmeticFunctions.fma(gy, bbbb, out[2]));
            }
        } else {
            double x1 = x0 + OpenSimplex2StyleSampler.UNSKEW_2D_MINUS_1;
            double y1 = y0 + OpenSimplex2StyleSampler.UNSKEW_2D;
            double b = OpenSimplex2StyleSampler.RSQUARED_2D - x1 * x1 - y1 * y1;
            if(b > 0) {
                double bb = b * b, bbb = bb * b, bbbb = bb * bb;
                long gi = SimplexStyleSampler.gradCoordIndex(seed, i + NoiseFunction.PRIME_X, j);

                double gx = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + (gi << DOUBLE_ARRAY_SHIFT));
                double gy = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + ((gi | 1) << DOUBLE_ARRAY_SHIFT));

                double rampValue = ArithmeticFunctions.fma(gx, x1, gy * y1);
                out[0] = ArithmeticFunctions.fma(bbbb, rampValue, out[0]);
                out[1] = ArithmeticFunctions.fma(-8 * rampValue * bbb, x1, ArithmeticFunctions.fma(gx, bbbb, out[1]));
                out[2] = ArithmeticFunctions.fma(-8 * rampValue * bbb, y1, ArithmeticFunctions.fma(gy, bbbb, out[2]));
            }
        }

        out[0] *= 99.83685446303647;
        out[1] *= 99.83685446303647;
        out[2] *= 99.83685446303647;
        return out;
    }

    @Override
    public double[] getNoiseDerivativeRaw(long sl, double x, double y, double z) {
        int seed = (int) sl;
        // 3D OpenSimplex2Sampler case uses two offset rotated cube grids.
        double r = (x + y + z) * OpenSimplex2StyleSampler.ROTATE_3D; // Rotation, not skew
        x = r - x;
        y = r - y;
        z = r - z;


        int i = FloatingPointFunctions.round(x);
        int j = FloatingPointFunctions.round(y);
        int k = FloatingPointFunctions.round(z);
        double x0 = x - i;
        double y0 = y - j;
        double z0 = z - k;

        int xNSign = (int) (-1.0 - x0) | 1;
        int yNSign = (int) (-1.0 - y0) | 1;
        int zNSign = (int) (-1.0 - z0) | 1;

        double ax0 = xNSign * -x0;
        double ay0 = yNSign * -y0;
        double az0 = zNSign * -z0;

        i *= NoiseFunction.PRIME_X;
        j *= NoiseFunction.PRIME_Y;
        k *= NoiseFunction.PRIME_Z;

        double[] out = { 0.0, 0.0, 0.0, 0.0 };
        double[] grads = SimplexStyleSampler.GRADIENTS_3D;
        double a = (OpenSimplex2StyleSampler.RSQUARED_3D - x0 * x0) - (ArithmeticFunctions.fma(y0, y0, z0 * z0));

        for(int l = 0; ; l++) {
            if(a > 0) {
                double aa = a * a, aaa = aa * a, aaaa = aa * aa;
                long gi = SimplexStyleSampler.gradCoordIndex(seed, i, j, k);

                double gx = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + (gi << DOUBLE_ARRAY_SHIFT));
                double gy = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + ((gi | 1) << DOUBLE_ARRAY_SHIFT));
                double gz = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + ((gi | 2) << DOUBLE_ARRAY_SHIFT));

                double rampValue = ArithmeticFunctions.fma(gx, x0, ArithmeticFunctions.fma(gy, y0, gz * z0));
                out[0] = ArithmeticFunctions.fma(aaaa, rampValue, out[0]);
                out[1] = ArithmeticFunctions.fma(-8 * rampValue * aaa, x0, ArithmeticFunctions.fma(gx, aaaa, out[1]));
                out[2] = ArithmeticFunctions.fma(-8 * rampValue * aaa, y0, ArithmeticFunctions.fma(gy, aaaa, out[2]));
                out[3] = ArithmeticFunctions.fma(-8 * rampValue * aaa, z0, ArithmeticFunctions.fma(gz, aaaa, out[3]));
            }

            if(ax0 >= ay0 && ax0 >= az0) {
                double b = a + ax0 + ax0;
                if(b > 1) {
                    b -= 1;
                    double bb = b * b, bbb = bb * b, bbbb = bb * bb;
                    long gi = SimplexStyleSampler.gradCoordIndex(seed, i - xNSign * NoiseFunction.PRIME_X, j, k);

                    double gx = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + (gi << DOUBLE_ARRAY_SHIFT));
                    double gy = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + ((gi | 1) << DOUBLE_ARRAY_SHIFT));
                    double gz = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + ((gi | 2) << DOUBLE_ARRAY_SHIFT));

                    double rampValue = ArithmeticFunctions.fma(gx, (x0 + xNSign), ArithmeticFunctions.fma(gy, y0, gz * z0));
                    out[0] = ArithmeticFunctions.fma(bbbb, rampValue, out[0]);
                    out[1] = ArithmeticFunctions.fma(-8 * rampValue * bbb, (x0 + xNSign), ArithmeticFunctions.fma(gx, bbbb, out[1]));
                    out[2] = ArithmeticFunctions.fma(-8 * rampValue * bbb, y0, ArithmeticFunctions.fma(gy, bbbb, out[2]));
                    out[3] = ArithmeticFunctions.fma(-8 * rampValue * bbb, z0, ArithmeticFunctions.fma(gz, bbbb, out[3]));

                }
            } else if(ay0 > ax0 && ay0 >= az0) {
                double b = a + ay0 + ay0;
                if(b > 1) {
                    b -= 1;
                    double bb = b * b, bbb = bb * b, bbbb = bb * bb;
                    long gi = SimplexStyleSampler.gradCoordIndex(seed, i, j - yNSign * NoiseFunction.PRIME_Y, k);

                    double gx = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + (gi << DOUBLE_ARRAY_SHIFT));
                    double gy = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + ((gi | 1) << DOUBLE_ARRAY_SHIFT));
                    double gz = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + ((gi | 2) << DOUBLE_ARRAY_SHIFT));

                    double rampValue = ArithmeticFunctions.fma(gx, x0, ArithmeticFunctions.fma(gy, (y0 + yNSign), gz * z0));
                    out[0] = ArithmeticFunctions.fma(bbbb, rampValue, out[0]);
                    out[1] = ArithmeticFunctions.fma(-8 * rampValue * bbb, x0, ArithmeticFunctions.fma(gx, bbbb, out[1]));
                    out[2] = ArithmeticFunctions.fma(-8 * rampValue * bbb, (y0 + yNSign), ArithmeticFunctions.fma(gy, bbbb, out[2]));
                    out[3] = ArithmeticFunctions.fma(-8 * rampValue * bbb, z0, ArithmeticFunctions.fma(gz, bbbb, out[3]));
                }
            } else {
                double b = a + az0 + az0;
                if(b > 1) {
                    b -= 1;
                    double bb = b * b, bbb = bb * b, bbbb = bb * bb;
                    long gi = SimplexStyleSampler.gradCoordIndex(seed, i, j, k - zNSign * NoiseFunction.PRIME_Z);

                    double gx = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + (gi << DOUBLE_ARRAY_SHIFT));
                    double gy = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + ((gi | 1) << DOUBLE_ARRAY_SHIFT));
                    double gz = UnsafeUtils.UNSAFE.getDouble(grads, DOUBLE_ARRAY_BASE + ((gi | 2) << DOUBLE_ARRAY_SHIFT));

                    double rampValue = ArithmeticFunctions.fma(gx, x0, ArithmeticFunctions.fma(gy, y0, gz * (z0 + zNSign)));
                    out[0] = ArithmeticFunctions.fma(bbbb, rampValue, out[0]);
                    out[1] = ArithmeticFunctions.fma(-8 * rampValue * bbb, x0, ArithmeticFunctions.fma(gx, bbbb, out[1]));
                    out[2] = ArithmeticFunctions.fma(-8 * rampValue * bbb, y0, ArithmeticFunctions.fma(gy, bbbb, out[2]));
                    out[3] = ArithmeticFunctions.fma(-8 * rampValue * bbb, (z0 + zNSign), ArithmeticFunctions.fma(gz, bbbb, out[3]));

                }
            }


            if(l == 1) break;

            ax0 = 0.5 - ax0;
            ay0 = 0.5 - ay0;
            az0 = 0.5 - az0;

            x0 = xNSign * ax0;
            y0 = yNSign * ay0;
            z0 = zNSign * az0;

            a += (0.75 - ax0) - (ay0 + az0);

            i += (xNSign >> 1) & NoiseFunction.PRIME_X;
            j += (yNSign >> 1) & NoiseFunction.PRIME_Y;
            k += (zNSign >> 1) & NoiseFunction.PRIME_Z;

            xNSign = -xNSign;
            yNSign = -yNSign;
            zNSign = -zNSign;

            seed = ~seed;
        }
        out[0] *= 32.69428253173828125;
        out[1] *= 32.69428253173828125;
        out[2] *= 32.69428253173828125;
        out[3] *= 32.69428253173828125;
        return out;
    }
}
