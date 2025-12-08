/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise.cellular;

import com.dfsek.seismic.algorithms.hashing.HashingFunctions;
import com.dfsek.seismic.algorithms.sampler.noise.NoiseFunction;
import com.dfsek.seismic.math.arithmetic.ArithmeticFunctions;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.type.DistanceFunction;
import com.dfsek.seismic.type.sampler.Sampler;


/**
 * NoiseSampler implementation for Cellular (Voronoi/Worley) Noise.
 */
public class CellularSampler extends CellularStyleSampler {
    private final boolean needsDistance2;
    private final boolean needsDistance3;
    private final boolean needsClosestHash;
    private final boolean needsCoords;
    private final boolean needsDistance0Sq;
    private final boolean needsDistance1Sq;
    private final boolean needsDistance2Sq;

    public CellularSampler(double frequency, long salt, Sampler noiseLookup, DistanceFunction distanceFunction,
                           CellularReturnType returnType,
                           double jitterModifier, boolean saltLookup) {
        super(frequency, salt, noiseLookup, distanceFunction, returnType, jitterModifier, saltLookup);
        boolean usesDistance2Operators =
            returnType == CellularReturnType.Distance2Add || returnType == CellularReturnType.Distance2Sub ||
            returnType == CellularReturnType.Distance2Mul || returnType == CellularReturnType.Distance2Div;
        boolean usesDistance3Operators =
            returnType == CellularReturnType.Distance3Add || returnType == CellularReturnType.Distance3Sub ||
            returnType == CellularReturnType.Distance3Mul || returnType == CellularReturnType.Distance3Div;
        if(distanceFunction == DistanceFunction.Euclidean) {
            this.needsDistance0Sq =
                returnType == CellularReturnType.Distance || usesDistance2Operators || usesDistance3Operators;
            this.needsDistance1Sq = returnType == CellularReturnType.Distance2 || usesDistance2Operators;
            this.needsDistance2Sq = returnType == CellularReturnType.Distance3 || usesDistance3Operators;
        } else {
            this.needsDistance0Sq = false;
            this.needsDistance1Sq = false;
            this.needsDistance2Sq = false;
        }
        this.needsDistance3 = usesDistance3Operators || returnType == CellularReturnType.Distance3;
        this.needsDistance2 = needsDistance3 || usesDistance2Operators || returnType == CellularReturnType.Distance2;
        this.needsClosestHash = returnType == CellularReturnType.CellValue;
        this.needsCoords = returnType == CellularReturnType.LocalNoiseLookup;
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y) {
        int seed = (int) sl;
        int xr = FloatingPointFunctions.round(x);
        int yr = FloatingPointFunctions.round(y);

        double distance0 = Double.MAX_VALUE;
        double distance1 = Double.MAX_VALUE;
        double distance2 = Double.MAX_VALUE;
        int closestHash = 0;

        double centerX = x;
        double centerY = y;

        int xrPlus1 = xr + 1;
        int yrPlus1 = yr + 1;
        int xrMinus1 = xr - 1;
        int yrMinus1 = yr - 1;

        double xrMinusX = xr - x;
        double yrMinusY = yr - y;
        double xrPlus1MinusX = xrPlus1 - x;
        double yrPlus1MinusY = yrPlus1 - y;
        double xrMinus1MinusX = xrMinus1 - x;
        double yrMinus1MinusY = yrMinus1 - y;

        int xrPrimed = xr * NoiseFunction.PRIME_X;
        int yrPrimed = yr * NoiseFunction.PRIME_Y;
        int xrPlus1Primed = xrPlus1 * NoiseFunction.PRIME_X;
        int xrMinus1Primed = xrMinus1 * NoiseFunction.PRIME_X;
        int yrPlus1Primed = yrPlus1 * NoiseFunction.PRIME_Y;
        int yrMinus1Primed = yrMinus1 * NoiseFunction.PRIME_Y;

        double[] minusCord = {
            xrMinusX, yrMinusY,
            xrMinusX, yrPlus1MinusY,
            xrPlus1MinusX, yrMinusY,
            xrMinusX, yrMinus1MinusY,
            xrMinus1MinusX, yrMinusY,
            xrPlus1MinusX, yrPlus1MinusY,
            xrPlus1MinusX, yrMinus1MinusY,
            xrMinus1MinusX, yrMinus1MinusY,
            xrMinus1MinusX, yrPlus1MinusY
        };

        int[] primed = {
            xrPrimed, yrPrimed,
            xrPrimed, yrPlus1Primed,
            xrPlus1Primed, yrPrimed,
            xrPrimed, yrMinus1Primed,
            xrMinus1Primed, yrPrimed,
            xrPlus1Primed, yrPlus1Primed,
            xrPlus1Primed, yrMinus1Primed,
            xrMinus1Primed, yrMinus1Primed,
            xrMinus1Primed, yrPlus1Primed
        };

        for(int i = 0; i < 18; i += 2) {
            int xPrimed = primed[i];
            int yPrimed = primed[i + 1];
            double xiMinusX = minusCord[i];
            double yiMinusY = minusCord[i + 1];

            int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed);
            int idx = hash & (255 << 1);

            double vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx], twoDCellularJitter, xiMinusX);
            double vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx | 1], twoDCellularJitter, yiMinusY);

            double newDistance = switch(distanceFunction) {
                case Euclidean, EuclideanSq -> ArithmeticFunctions.fma(vecX, vecX, vecY * vecY);
                case Manhattan -> Math.abs(vecX) + Math.abs(vecY);
                case Hybrid -> (Math.abs(vecX) + Math.abs(vecY)) + ArithmeticFunctions.fma(vecX, vecX, vecY * vecY);
            };

            if(newDistance < distance0) {
                if(needsDistance2) {
                    if(needsDistance3) {
                        distance2 = distance1;
                    }
                    distance1 = distance0;
                }
                distance0 = newDistance;

                if (needsClosestHash) {
                    closestHash = hash;
                }
                if (needsCoords) {
                    centerX = (vecX + x) * invFrequency;
                    centerY = (vecY + y) * invFrequency;
                }
            } else if(needsDistance2 && newDistance < distance1) {
                distance2 = distance1;
                distance1 = newDistance;
            } else if(needsDistance3 && newDistance < distance2) {
                distance2 = newDistance;
            }
        }

        if(needsDistance0Sq) {
            distance0 = Math.sqrt(distance0);
        }
        if(needsDistance1Sq) {
            distance1 = Math.sqrt(distance1);
        }
        if(needsDistance2Sq) {
            distance2 = Math.sqrt(distance2);
        }

        return returnType.getReturn(this, sl, distance0, distance1, distance2, x, y, centerX, centerY, closestHash);
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y, double z) {
        int seed = (int) sl;
        int xr = FloatingPointFunctions.round(x);
        int yr = FloatingPointFunctions.round(y);
        int zr = FloatingPointFunctions.round(z);

        double distance0 = Double.MAX_VALUE;
        double distance1 = Double.MAX_VALUE;
        double distance2 = Double.MAX_VALUE;
        int closestHash = 0;

        double centerX = x;
        double centerY = y;
        double centerZ = z;

        int xrMinus1 = xr - 1, xrPlus1 = xr + 1;
        int yrMinus1 = yr - 1, yrPlus1 = yr + 1;
        int zrMinus1 = zr - 1, zrPlus1 = zr + 1;

        double xrMinusX = xr - x;
        double xrPlus1MinusX = xrPlus1 - x;
        double xrMinus1MinusX = xrMinus1 - x;

        double yrMinusY = yr - y;
        double yrPlus1MinusY = yrPlus1 - y;
        double yrMinus1MinusY = yrMinus1 - y;

        double zrMinusZ = zr - z;
        double zrPlus1MinusZ = zrPlus1 - z;
        double zrMinus1MinusZ = zrMinus1 - z;

        int xrPrimed = xr * NoiseFunction.PRIME_X;
        int xrPlus1Primed = xrPlus1 * NoiseFunction.PRIME_X;
        int xrMinus1Primed = xrMinus1 * NoiseFunction.PRIME_X;

        int yrPrimed = yr * NoiseFunction.PRIME_Y;
        int yrPlus1Primed = yrPlus1 * NoiseFunction.PRIME_Y;
        int yrMinus1Primed = yrMinus1 * NoiseFunction.PRIME_Y;

        int zrPrimed = zr * NoiseFunction.PRIME_Z;
        int zrPlus1Primed = zrPlus1 * NoiseFunction.PRIME_Z;
        int zrMinus1Primed = zrMinus1 * NoiseFunction.PRIME_Z;

        double[] offsets = {
            // Center
            xrMinusX, yrMinusY, zrMinusZ,

            // Faces
            xrPlus1MinusX, yrMinusY, zrMinusZ,
            xrMinus1MinusX, yrMinusY, zrMinusZ,
            xrMinusX, yrPlus1MinusY, zrMinusZ,
            xrMinusX, yrMinus1MinusY, zrMinusZ,
            xrMinusX, yrMinusY, zrPlus1MinusZ,
            xrMinusX, yrMinusY, zrMinus1MinusZ,

            // Edges
            xrPlus1MinusX, yrPlus1MinusY, zrMinusZ,
            xrPlus1MinusX, yrMinus1MinusY, zrMinusZ,
            xrMinus1MinusX, yrPlus1MinusY, zrMinusZ,
            xrMinus1MinusX, yrMinus1MinusY, zrMinusZ,

            xrPlus1MinusX, yrMinusY, zrPlus1MinusZ,
            xrPlus1MinusX, yrMinusY, zrMinus1MinusZ,
            xrMinus1MinusX, yrMinusY, zrPlus1MinusZ,
            xrMinus1MinusX, yrMinusY, zrMinus1MinusZ,

            xrMinusX, yrPlus1MinusY, zrPlus1MinusZ,
            xrMinusX, yrPlus1MinusY, zrMinus1MinusZ,
            xrMinusX, yrMinus1MinusY, zrPlus1MinusZ,
            xrMinusX, yrMinus1MinusY, zrMinus1MinusZ,

            // Corners
            xrPlus1MinusX, yrPlus1MinusY, zrPlus1MinusZ,
            xrPlus1MinusX, yrPlus1MinusY, zrMinus1MinusZ,
            xrPlus1MinusX, yrMinus1MinusY, zrPlus1MinusZ,
            xrPlus1MinusX, yrMinus1MinusY, zrMinus1MinusZ,

            xrMinus1MinusX, yrPlus1MinusY, zrPlus1MinusZ,
            xrMinus1MinusX, yrPlus1MinusY, zrMinus1MinusZ,
            xrMinus1MinusX, yrMinus1MinusY, zrPlus1MinusZ,
            xrMinus1MinusX, yrMinus1MinusY, zrMinus1MinusZ
        };

        int[] primed = {
            // Center
            xrPrimed, yrPrimed, zrPrimed,

            // Faces
            xrPlus1Primed, yrPrimed, zrPrimed,
            xrMinus1Primed, yrPrimed, zrPrimed,
            xrPrimed, yrPlus1Primed, zrPrimed,
            xrPrimed, yrMinus1Primed, zrPrimed,
            xrPrimed, yrPrimed, zrPlus1Primed,
            xrPrimed, yrPrimed, zrMinus1Primed,

            // Edges
            xrPlus1Primed, yrPlus1Primed, zrPrimed,
            xrPlus1Primed, yrMinus1Primed, zrPrimed,
            xrMinus1Primed, yrPlus1Primed, zrPrimed,
            xrMinus1Primed, yrMinus1Primed, zrPrimed,

            xrPlus1Primed, yrPrimed, zrPlus1Primed,
            xrPlus1Primed, yrPrimed, zrMinus1Primed,
            xrMinus1Primed, yrPrimed, zrPlus1Primed,
            xrMinus1Primed, yrPrimed, zrMinus1Primed,

            xrPrimed, yrPlus1Primed, zrPlus1Primed,
            xrPrimed, yrPlus1Primed, zrMinus1Primed,
            xrPrimed, yrMinus1Primed, zrPlus1Primed,
            xrPrimed, yrMinus1Primed, zrMinus1Primed,

            // Corners
            xrPlus1Primed, yrPlus1Primed, zrPlus1Primed,
            xrPlus1Primed, yrPlus1Primed, zrMinus1Primed,
            xrPlus1Primed, yrMinus1Primed, zrPlus1Primed,
            xrPlus1Primed, yrMinus1Primed, zrMinus1Primed,

            xrMinus1Primed, yrPlus1Primed, zrPlus1Primed,
            xrMinus1Primed, yrPlus1Primed, zrMinus1Primed,
            xrMinus1Primed, yrMinus1Primed, zrPlus1Primed,
            xrMinus1Primed, yrMinus1Primed, zrMinus1Primed
        };

        for(int i = 0; i < 81; i += 3) {
            int xPrimed = primed[i];
            int yPrimed = primed[i + 1];
            int zPrimed = primed[i + 2];

            double xiMinusX = offsets[i];
            double yiMinusY = offsets[i + 1];
            double ziMinusZ = offsets[i + 2];
            int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed, zPrimed);
            int idx = hash & (255 << 2);

            double vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_3D[idx], threeDCellularJitter, xiMinusX);
            double vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_3D[idx | 1], threeDCellularJitter, yiMinusY);
            double vecZ = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_3D[idx | 2], threeDCellularJitter, ziMinusZ);

            double newDistance = switch(distanceFunction) {
                case Euclidean, EuclideanSq -> ArithmeticFunctions.fma(vecX, vecX,
                    ArithmeticFunctions.fma(vecY, vecY, vecZ * vecZ));
                case Manhattan -> Math.abs(vecX) + Math.abs(vecY) + Math.abs(vecZ);
                case Hybrid -> (Math.abs(vecX) + Math.abs(vecY) + Math.abs(vecZ)) + ArithmeticFunctions.fma(vecX, vecX,
                    ArithmeticFunctions.fma(vecY, vecY, vecZ * vecZ));
            };

            if(newDistance < distance0) {
                if(needsDistance2) {
                    if(needsDistance3) {
                        distance2 = distance1;
                    }
                    distance1 = distance0;
                }
                distance0 = newDistance;

                if (needsClosestHash) {
                    closestHash = hash;
                }
                if (needsCoords) {
                    centerX = (vecX + x) * invFrequency;
                    centerY = (vecY + y) * invFrequency;
                    centerZ = (vecZ + z) * invFrequency;
                }
            } else if(needsDistance2 && newDistance < distance1) {
                distance2 = distance1;
                distance1 = newDistance;
            } else if(needsDistance3 && newDistance < distance2) {
                distance2 = newDistance;
            }
        }

        if(needsDistance0Sq) {
            distance0 = Math.sqrt(distance0);
        }
        if(needsDistance1Sq) {
            distance1 = Math.sqrt(distance1);
        }
        if(needsDistance2Sq) {
            distance2 = Math.sqrt(distance2);
        }

        return returnType.getReturn(this, sl, distance0, distance1, distance2, x, y, z, centerX, centerY, centerZ, closestHash);
    }
}
