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
    private final boolean needs3dCoords;

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
        this.needs3dCoords = returnType == CellularReturnType.LocalNoiseLookup;
        this.needsCoords = returnType == CellularReturnType.Angle || needs3dCoords;
    }

    //Manually unrolled
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

        int hash = HashingFunctions.hashPrimeCoords(seed, xrPrimed, yrPrimed);
        int idx = hash & (255 << 1);

        double vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx], twoDCellularJitter, xrMinusX);
        double vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx | 1], twoDCellularJitter, yrMinusY);

        double newDistance = switch(distanceFunction) {
            case Euclidean, EuclideanSq -> ArithmeticFunctions.fma(vecX, vecX, vecY * vecY);
            case Manhattan -> Math.abs(vecX) + Math.abs(vecY);
            case Hybrid -> (Math.abs(vecX) + Math.abs(vecY)) + ArithmeticFunctions.fma(vecX, vecX, vecY * vecY);
        };

        if(newDistance < distance0) {
            distance0 = newDistance;

            if(needsClosestHash) {
                closestHash = hash;
            }
            if(needsCoords) {
                centerX = (vecX + x) * invFrequency;
                centerY = (vecY + y) * invFrequency;
            }
        }
        hash = HashingFunctions.hashPrimeCoords(seed, xrPrimed, yrPlus1Primed);
        idx = hash & (255 << 1);

        vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx], twoDCellularJitter, xrMinusX);
        vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx | 1], twoDCellularJitter, yrPlus1MinusY);

        newDistance = switch(distanceFunction) {
            case Euclidean, EuclideanSq -> ArithmeticFunctions.fma(vecX, vecX, vecY * vecY);
            case Manhattan -> Math.abs(vecX) + Math.abs(vecY);
            case Hybrid -> (Math.abs(vecX) + Math.abs(vecY)) + ArithmeticFunctions.fma(vecX, vecX, vecY * vecY);
        };

        if(newDistance < distance0) {
            if(needsDistance2) {
                distance1 = distance0;
            }
            distance0 = newDistance;

            if(needsClosestHash) {
                closestHash = hash;
            }
            if(needsCoords) {
                centerX = (vecX + x) * invFrequency;
                centerY = (vecY + y) * invFrequency;
            }
        } else if(needsDistance2 && newDistance < distance1) {
            distance1 = newDistance;
        }
        hash = HashingFunctions.hashPrimeCoords(seed, xrPrimed, yrMinus1Primed);
        idx = hash & (255 << 1);

        vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx], twoDCellularJitter, xrMinusX);
        vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx | 1], twoDCellularJitter, yrMinus1MinusY);

        newDistance = switch(distanceFunction) {
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

            if(needsClosestHash) {
                closestHash = hash;
            }
            if(needsCoords) {
                centerX = (vecX + x) * invFrequency;
                centerY = (vecY + y) * invFrequency;
            }
        } else if(needsDistance2 && newDistance < distance1) {
            if(needsDistance3) {
                distance2 = distance1;
            }
            distance1 = newDistance;
        } else if(needsDistance3 && newDistance < distance2) {
            distance2 = newDistance;
        }
        hash = HashingFunctions.hashPrimeCoords(seed, xrMinus1Primed, yrPrimed);
        idx = hash & (255 << 1);

        vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx], twoDCellularJitter, xrMinus1MinusX);
        vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx | 1], twoDCellularJitter, yrMinusY);

        newDistance = switch(distanceFunction) {
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

            if(needsClosestHash) {
                closestHash = hash;
            }
            if(needsCoords) {
                centerX = (vecX + x) * invFrequency;
                centerY = (vecY + y) * invFrequency;
            }
        } else if(needsDistance2 && newDistance < distance1) {
            if(needsDistance3) {
                distance2 = distance1;
            }
            distance1 = newDistance;
        } else if(needsDistance3 && newDistance < distance2) {
            distance2 = newDistance;
        }
        hash = HashingFunctions.hashPrimeCoords(seed, xrPlus1Primed, yrPrimed);
        idx = hash & (255 << 1);

        vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx], twoDCellularJitter, xrPlus1MinusX);
        vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx | 1], twoDCellularJitter, yrMinusY);

        newDistance = switch(distanceFunction) {
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

            if(needsClosestHash) {
                closestHash = hash;
            }
            if(needsCoords) {
                centerX = (vecX + x) * invFrequency;
                centerY = (vecY + y) * invFrequency;
            }
        } else if(needsDistance2 && newDistance < distance1) {
            if(needsDistance3) {
                distance2 = distance1;
            }
            distance1 = newDistance;
        } else if(needsDistance3 && newDistance < distance2) {
            distance2 = newDistance;
        }
        hash = HashingFunctions.hashPrimeCoords(seed, xrPlus1Primed, yrPlus1Primed);
        idx = hash & (255 << 1);

        vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx], twoDCellularJitter, xrPlus1MinusX);
        vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx | 1], twoDCellularJitter, yrPlus1MinusY);

        newDistance = switch(distanceFunction) {
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

            if(needsClosestHash) {
                closestHash = hash;
            }
            if(needsCoords) {
                centerX = (vecX + x) * invFrequency;
                centerY = (vecY + y) * invFrequency;
            }
        } else if(needsDistance2 && newDistance < distance1) {
            if(needsDistance3) {
                distance2 = distance1;
            }
            distance1 = newDistance;
        } else if(needsDistance3 && newDistance < distance2) {
            distance2 = newDistance;
        }
        hash = HashingFunctions.hashPrimeCoords(seed, xrPlus1Primed, yrMinus1Primed);
        idx = hash & (255 << 1);

        vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx], twoDCellularJitter, xrPlus1MinusX);
        vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx | 1], twoDCellularJitter, yrMinus1MinusY);

        newDistance = switch(distanceFunction) {
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

            if(needsClosestHash) {
                closestHash = hash;
            }
            if(needsCoords) {
                centerX = (vecX + x) * invFrequency;
                centerY = (vecY + y) * invFrequency;
            }
        } else if(needsDistance2 && newDistance < distance1) {
            if(needsDistance3) {
                distance2 = distance1;
            }
            distance1 = newDistance;
        } else if(needsDistance3 && newDistance < distance2) {
            distance2 = newDistance;
        }
        hash = HashingFunctions.hashPrimeCoords(seed, xrMinus1Primed, yrMinus1Primed);
        idx = hash & (255 << 1);

        vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx], twoDCellularJitter, xrMinus1MinusX);
        vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx | 1], twoDCellularJitter, yrMinus1MinusY);

        newDistance = switch(distanceFunction) {
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

            if(needsClosestHash) {
                closestHash = hash;
            }
            if(needsCoords) {
                centerX = (vecX + x) * invFrequency;
                centerY = (vecY + y) * invFrequency;
            }
        } else if(needsDistance2 && newDistance < distance1) {
            if(needsDistance3) {
                distance2 = distance1;
            }
            distance1 = newDistance;
        } else if(needsDistance3 && newDistance < distance2) {
            distance2 = newDistance;
        }
        hash = HashingFunctions.hashPrimeCoords(seed, xrMinus1Primed, yrPlus1Primed);
        idx = hash & (255 << 1);

        vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx], twoDCellularJitter, xrMinus1MinusX);
        vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx | 1], twoDCellularJitter, yrPlus1MinusY);

        newDistance = switch(distanceFunction) {
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

            if(needsClosestHash) {
                closestHash = hash;
            }
            if(needsCoords) {
                centerX = (vecX + x) * invFrequency;
                centerY = (vecY + y) * invFrequency;
            }
        } else if(needsDistance2 && newDistance < distance1) {
            if(needsDistance3) {
                distance2 = distance1;
            }
            distance1 = newDistance;
        } else if(needsDistance3 && newDistance < distance2) {
            distance2 = newDistance;
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

        return returnType.getReturn(this, sl, distance0, distance1, distance2, x, y, centerX, centerY,
            closestHash);
    }

    @Override
    public double getNoiseRaw(long sl, double x, double y, double z) {
        int seed = (int) sl;
        int xr = FloatingPointFunctions.round(x);
        int yr = FloatingPointFunctions.round(y);
        int zr = FloatingPointFunctions.round(z);

        NoiseState3D state = new NoiseState3D(
            Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE,
            0,
            x, y, z
        );

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

        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPrimed, yrPrimed, zrPrimed, xrMinusX, yrMinusY, zrMinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPlus1Primed, yrPrimed, zrPrimed, xrPlus1MinusX, yrMinusY, zrMinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrMinus1Primed, yrPrimed, zrPrimed, xrMinus1MinusX, yrMinusY, zrMinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPrimed, yrPlus1Primed, zrPrimed, xrMinusX, yrPlus1MinusY, zrMinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPrimed, yrMinus1Primed, zrPrimed, xrMinusX, yrMinus1MinusY, zrMinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPrimed, yrPrimed, zrPlus1Primed, xrMinusX, yrMinusY, zrPlus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPrimed, yrPrimed, zrMinus1Primed, xrMinusX, yrMinusY, zrMinus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPlus1Primed, yrPlus1Primed, zrPrimed, xrPlus1MinusX, yrPlus1MinusY, zrMinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPlus1Primed, yrMinus1Primed, zrPrimed, xrPlus1MinusX, yrMinus1MinusY, zrMinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrMinus1Primed, yrPlus1Primed, zrPrimed, xrMinus1MinusX, yrPlus1MinusY, zrMinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrMinus1Primed, yrMinus1Primed, zrPrimed, xrMinus1MinusX, yrMinus1MinusY, zrMinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPlus1Primed, yrPrimed, zrPlus1Primed, xrPlus1MinusX, yrMinusY, zrPlus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPlus1Primed, yrPrimed, zrMinus1Primed, xrPlus1MinusX, yrMinusY, zrMinus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrMinus1Primed, yrPrimed, zrPlus1Primed, xrMinus1MinusX, yrMinusY, zrPlus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrMinus1Primed, yrPrimed, zrMinus1Primed, xrMinus1MinusX, yrMinusY, zrMinus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPrimed, yrPlus1Primed, zrPlus1Primed, xrMinusX, yrPlus1MinusY, zrPlus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPrimed, yrPlus1Primed, zrMinus1Primed, xrMinusX, yrPlus1MinusY, zrMinus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPrimed, yrMinus1Primed, zrPlus1Primed, xrMinusX, yrMinus1MinusY, zrPlus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPrimed, yrMinus1Primed, zrMinus1Primed, xrMinusX, yrMinus1MinusY, zrMinus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPlus1Primed, yrPlus1Primed, zrPlus1Primed, xrPlus1MinusX, yrPlus1MinusY, zrPlus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPlus1Primed, yrPlus1Primed, zrMinus1Primed, xrPlus1MinusX, yrPlus1MinusY, zrMinus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPlus1Primed, yrMinus1Primed, zrPlus1Primed, xrPlus1MinusX, yrMinus1MinusY, zrPlus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrPlus1Primed, yrMinus1Primed, zrMinus1Primed, xrPlus1MinusX, yrMinus1MinusY, zrMinus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrMinus1Primed, yrPlus1Primed, zrPlus1Primed, xrMinus1MinusX, yrPlus1MinusY, zrPlus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrMinus1Primed, yrPlus1Primed, zrMinus1Primed, xrMinus1MinusX, yrPlus1MinusY, zrMinus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrMinus1Primed, yrMinus1Primed, zrPlus1Primed, xrMinus1MinusX, yrMinus1MinusY, zrPlus1MinusZ);
        noiseLoopCalc3D(threeDCellularJitter, seed, x, y, z, state, xrMinus1Primed, yrMinus1Primed, zrMinus1Primed, xrMinus1MinusX, yrMinus1MinusY, zrMinus1MinusZ);

        if(needsDistance0Sq) {
            state.distance0 = Math.sqrt(state.distance0);
        }
        if(needsDistance1Sq) {
            state.distance1 = Math.sqrt(state.distance1);
        }
        if(needsDistance2Sq) {
            state.distance2 = Math.sqrt(state.distance2);
        }

        return returnType.getReturn(this, sl, state.distance0, state.distance1, state.distance2, x, y, z, state.centerX, state.centerY,
            state.centerZ, state.closestHash);
    }

    void noiseLoopCalc3D(double threeDCellularJitter, int seed, double x, double y, double z,
                         NoiseState3D state, int xPrimed, int yPrimed, int zPrimed, double xiMinusX, double yiMinusY, double ziMinusZ) {
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

        if(newDistance < state.distance0) {
            if(needsDistance2) {
                if(needsDistance3) {
                    state.distance2 = state.distance1;
                }
                state.distance1 = state.distance0;
            }
            state.distance0 = newDistance;

            if(needsClosestHash) {
                state.closestHash = hash;
            }
            if(needsCoords) {
                state.centerX = (vecX + x) * invFrequency;
                state.centerY = (vecY + y) * invFrequency;
                if(needs3dCoords) {
                    state.centerZ = (vecZ + z) * invFrequency;
                }
            }
        } else if(needsDistance2 && newDistance < state.distance1) {
            if(needsDistance3) {
                state.distance2 = state.distance1;
            }
            state.distance1 = newDistance;
        } else if(needsDistance3 && newDistance < state.distance2) {
            state.distance2 = newDistance;
        }
    }

    private static class NoiseState3D {
        double centerX, centerY, centerZ;
        double distance0, distance1, distance2;
        int closestHash;

        NoiseState3D(double distance0, double distance1, double distance2, int closestHash, double centerX, double centerY,
                     double centerZ) {
            this.distance0 = distance0;
            this.distance1 = distance1;
            this.distance2 = distance2;
            this.closestHash = closestHash;
            this.centerX = centerX;
            this.centerY = centerY;
            this.centerZ = centerZ;
        }
    }
}
