package com.dfsek.seismic.algorithms.sampler.noise.cellular;

import com.dfsek.seismic.algorithms.sampler.noise.NoiseFunction;
import com.dfsek.seismic.algorithms.sampler.noise.simplex.OpenSimplex2Sampler;
import com.dfsek.seismic.type.DistanceFunction;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;


@State(Scope.Benchmark)
public class CellularSamplerBenchmark {
    private final NoiseFunction cellular = new CellularSampler(0.02d, 123123, new OpenSimplex2Sampler(0.2d, 12372834),
        DistanceFunction.EuclideanSq, CellularStyleSampler.CellularReturnType.Distance, 1.0d, true);


    @Benchmark
    @Group("cellular")
    @Fork(1)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 5)
    public void benchmarkCellular3D() {
        int startX = (int) (Math.random() * 10000);
        int startY = (int) (Math.random() * 10000);
        int startZ = (int) (Math.random() * 10000);
        long seed = (Double.doubleToLongBits(Math.random()) << Double.doubleToLongBits(Math.random())) ^ Double.doubleToLongBits(
            Math.random());
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 384; y++) {
                for(int z = 0; z < 16; z++) {
                    cellular.getNoiseRaw(seed, startX + x, startY + y, startZ + z);
                }
            }
        }
    }

    @Benchmark
    @Group("cellular")
    @Fork(1)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 5)
    public void benchmarkCellular2D() {
        int startX = (int) (Math.random() * 10000);
        int startY = (int) (Math.random() * 10000);
        long seed = (Double.doubleToLongBits(Math.random()) << Double.doubleToLongBits(Math.random())) ^ Double.doubleToLongBits(
            Math.random());
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                cellular.getNoiseRaw(seed, startX + x, startY + y);
            }
        }
    }
}
