/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.seismic.algorithms.sampler.noise;


import com.dfsek.seismic.algorithms.hashing.HashingFunctions;
import com.dfsek.seismic.algorithms.sampler.noise.random.WhiteNoiseSampler;
import com.dfsek.seismic.math.floatingpoint.FloatingPointFunctions;
import com.dfsek.seismic.math.trigonometry.TrigonometryFunctions;


public class GaborSampler extends NoiseFunction {
    private final WhiteNoiseSampler rand;
    private final double deviation;
    private final double a;
    private final double f0;

    private final double kernelRadius;
    private final double g;
    private final double rotation;
    private final boolean isotropic;


    public GaborSampler(double frequency, long salt, double frequency0, double deviation, double rotation, double impulsesPerKernel,
                        double a, boolean isotropic) {
        super(frequency, salt);
        this.deviation = deviation;
        this.rotation = TrigonometryConstants.PI * rotation;
        this.f0 = frequency0;
        this.a = a;
        this.isotropic = isotropic;

        kernelRadius = (Math.sqrt(-Math.log(0.05) / TrigonometryConstants.PI) / this.a);
        double impulseDensity = (impulsesPerKernel / (TrigonometryConstants.PI * kernelRadius * kernelRadius));
        double impulsesPerCell = impulseDensity * kernelRadius * kernelRadius;
        g = Math.exp(-impulsesPerCell);

        rand = new WhiteNoiseSampler(frequency, salt);
    }

    private double gaborNoise(long seed, double x, double y) {
        x /= kernelRadius;
        y /= kernelRadius;
        int xi = FloatingPointFunctions.floor(x);
        int yi = FloatingPointFunctions.floor(y);
        double xf = x - xi;
        double yf = y - yi;
        double noise = 0;
        for(int dx = -1; dx <= 1; dx++) {
            for(int dz = -1; dz <= 1; dz++) {
                noise += calculateCell(seed, xi + dx, yi + dz, xf - dx, yf - dz);
            }
        }
        return noise;
    }

    private double calculateCell(long seed, int xi, int yi, double x, double y) {
        long mashedSeed = HashingFunctions.murmur64(31L * xi + yi) + seed;

        double gaussianSource = (rand.getNoiseRaw(mashedSeed++) + 1) / 2;
        int impulses = 0;
        while(gaussianSource > g) {
            impulses++;
            gaussianSource *= (rand.getNoiseRaw(mashedSeed++) + 1) / 2;
        }

        double noise = 0;
        for(int i = 0; i < impulses; i++) {
            noise += rand.getNoiseRaw(mashedSeed++) * gabor(isotropic ? (rand.getNoiseRaw(mashedSeed++) + 1) * TrigonometryConstants.PI : rotation,
                x * kernelRadius, y * kernelRadius);
        }
        return noise;
    }

    private double gabor(double omega_0, double x, double y) {
        return deviation * (Math.exp(-TrigonometryConstants.PI * (a * a) * (x * x + y * y)) * TrigonometryFunctions.cos(
            2 * TrigonometryConstants.PI * f0 * (x * TrigonometryFunctions.cos(omega_0) +
                                y * TrigonometryFunctions.sin(
                                    omega_0))));
    }

    @Override
    public double getNoiseRaw(long seed, double x, double z) {
        return gaborNoise(seed, x, z);
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y, double z) {
        return gaborNoise(seed, x, z);
    }
}
