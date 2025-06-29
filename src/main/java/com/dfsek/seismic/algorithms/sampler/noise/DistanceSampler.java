package com.dfsek.seismic.algorithms.sampler.noise;


import com.dfsek.seismic.type.DistanceFunction;


public class DistanceSampler extends NoiseFunction {

    private final DistanceFunction distanceFunction;
    private final double ox, oy, oz;
    private final boolean normalize;
    private final double radius;

    private final double distanceAtRadius;

    public DistanceSampler(long salt, DistanceFunction distanceFunction, double ox, double oy, double oz, boolean normalize,
                           double radius) {
        super(1, salt);
        this.distanceFunction = distanceFunction;
        this.ox = ox;
        this.oy = oy;
        this.oz = oz;
        this.normalize = normalize;
        this.radius = radius;
        this.distanceAtRadius = DistanceSampler.distance2d(distanceFunction, radius,
            0); // distance2d and distance3d should return the same value
    }

    private static double distance2d(DistanceFunction distanceFunction, double x, double z) {
        return distanceFunction.getDistance(x, z);
    }

    private static double distance3d(DistanceFunction distanceFunction, double x, double y, double z) {
        return distanceFunction.getDistance(x, y, z);
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y) {
        double dx = x - ox;
        double dy = y - oz;
        if(normalize && (Math.abs(dx) > radius || Math.abs(dy) > radius)) return 1;
        double dist = DistanceSampler.distance2d(distanceFunction, dx, dy);
        if(normalize) return Math.min(((2 * dist) / distanceAtRadius) - 1, 1);
        return dist;
    }

    @Override
    public double getNoiseRaw(long seed, double x, double y, double z) {
        double dx = x - ox;
        double dy = y - oy;
        double dz = z - oz;
        if(normalize && (Math.abs(dx) > radius || Math.abs(dy) > radius || Math.abs(dz) > radius)) return 1;
        double dist = DistanceSampler.distance3d(distanceFunction, dx, dy, dz);
        if(normalize) return Math.min(((2 * dist) / distanceAtRadius) - 1, 1);
        return dist;
    }
}
