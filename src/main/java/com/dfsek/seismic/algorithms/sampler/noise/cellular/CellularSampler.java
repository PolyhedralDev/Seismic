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
import com.dfsek.seismic.math.trigonometry.TrigonometryFunctions;
import com.dfsek.seismic.type.DistanceFunction;
import com.dfsek.seismic.type.sampler.Sampler;


/**
 * NoiseSampler implementation for Cellular (Voronoi/Worley) Noise.
 */
public class CellularSampler extends CellularStyleSampler {
    public CellularSampler(double frequency, long salt, Sampler noiseLookup, DistanceFunction distanceFunction, CellularReturnType returnType,
                           double jitterModifier, boolean saltLookup) {
        super(frequency, salt, noiseLookup, distanceFunction, returnType, jitterModifier, saltLookup);
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

        double cellularJitter = 0.43701595 * jitterModifier;

        int xPrimed = (xr - 1) * NoiseFunction.PRIME_X;
        int yPrimedBase = (yr - 1) * NoiseFunction.PRIME_Y;

        double centerX = x;
        double centerY = y;

        for(int xi = xr - 1; xi <= xr + 1; xi++) {
            int yPrimed = yPrimedBase;
            double xiMinusX = xi - x;

            for(int yi = yr - 1; yi <= yr + 1; yi++) {
                int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed);
                int idx = hash & (255 << 1);

                double vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx], cellularJitter, xiMinusX);
                double vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_2D[idx | 1], cellularJitter, yi - y);

                double newDistance = switch(distanceFunction) {
                    case Euclidean, EuclideanSq -> ArithmeticFunctions.fma(vecX, vecX, vecY * vecY);
                    case Manhattan -> Math.abs(vecX) + Math.abs(vecY);
                    case Hybrid -> (Math.abs(vecX) + Math.abs(vecY)) + ArithmeticFunctions.fma(vecX, vecX, vecY * vecY);
                };

                if (newDistance < distance0) {
                    distance2 = distance1;
                    distance1 = distance0;
                    distance0 = newDistance;

                    closestHash = hash;
                    centerX = (vecX + x) * invFrequency;
                    centerY = (vecY + y) * invFrequency;
                } else if (newDistance < distance1) {
                    distance2 = distance1;
                    distance1 = newDistance;
                } else if (newDistance < distance2) {
                    distance2 = newDistance;
                }
                yPrimed += NoiseFunction.PRIME_Y;
            }
            xPrimed += NoiseFunction.PRIME_X;
        }

        if(distanceFunction == DistanceFunction.Euclidean && returnType != CellularReturnType.CellValue) {
            distance0 = Math.sqrt(distance0);

            if(returnType != CellularReturnType.Distance) {
                distance1 = Math.sqrt(distance1);
            }
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

        double cellularJitter = 0.39614353 * jitterModifier;

        int xPrimed = (xr - 1) * NoiseFunction.PRIME_X;
        int yPrimedBase = (yr - 1) * NoiseFunction.PRIME_Y;
        int zPrimedBase = (zr - 1) * NoiseFunction.PRIME_Z;

        double centerX = x;
        double centerY = y;
        double centerZ = z;

        for(int xi = xr - 1; xi <= xr + 1; xi++) {
            int yPrimed = yPrimedBase;
            double xiMinusX = xi - x;

            for(int yi = yr - 1; yi <= yr + 1; yi++) {
                int zPrimed = zPrimedBase;
                double yiMinusY = yi - y;

                for(int zi = zr - 1; zi <= zr + 1; zi++) {
                    int hash = HashingFunctions.hashPrimeCoords(seed, xPrimed, yPrimed, zPrimed);
                    int idx = hash & (255 << 2);

                    double vecX = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_3D[idx], cellularJitter, xiMinusX);
                    double vecY = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_3D[idx | 1], cellularJitter, yiMinusY);
                    double vecZ = ArithmeticFunctions.fma(CellularSampler.RAND_VECS_3D[idx | 2], cellularJitter, zi - z);

                    double newDistance = switch(distanceFunction) {
                        case Euclidean, EuclideanSq -> ArithmeticFunctions.fma(vecX, vecX,
                            ArithmeticFunctions.fma(vecY, vecY, vecZ * vecZ));
                        case Manhattan -> Math.abs(vecX) + Math.abs(vecY) + Math.abs(vecZ);
                        case Hybrid -> (Math.abs(vecX) + Math.abs(vecY) + Math.abs(vecZ)) + ArithmeticFunctions.fma(vecX, vecX,
                            ArithmeticFunctions.fma(vecY, vecY, vecZ * vecZ));
                    };

                    if (newDistance < distance0) {
                        distance2 = distance1;
                        distance1 = distance0;
                        distance0 = newDistance;

                        closestHash = hash;
                        centerX = (vecX + x) * invFrequency;
                        centerY = (vecY + y) * invFrequency;
                        centerZ = (vecZ + z) * invFrequency;
                    } else if (newDistance < distance1) {
                        distance2 = distance1;
                        distance1 = newDistance;
                    } else if (newDistance < distance2) {
                        distance2 = newDistance;
                    }
                    zPrimed += NoiseFunction.PRIME_Z;
                }
                yPrimed += NoiseFunction.PRIME_Y;
            }
            xPrimed += NoiseFunction.PRIME_X;
        }

        if(distanceFunction == DistanceFunction.Euclidean && returnType != CellularReturnType.CellValue) {
            distance0 = Math.sqrt(distance0);

            if(returnType != CellularReturnType.Distance) {
                distance1 = Math.sqrt(distance1);
            }
        }

        return returnType.getReturn(this, sl, distance0, distance1, distance2, x, y, z, centerX, centerY, centerZ, closestHash);
    }


}
