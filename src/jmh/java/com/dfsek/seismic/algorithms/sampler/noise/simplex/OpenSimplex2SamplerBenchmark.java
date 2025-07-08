package com.dfsek.seismic.algorithms.sampler.noise.simplex;

import com.dfsek.seismic.algorithms.sampler.noise.NoiseFunction;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;


@State(Scope.Benchmark)
public class OpenSimplex2SamplerBenchmark {
    private final NoiseFunction sampler = new OpenSimplex2Sampler(0.02d, 123123 << 1);


    @Benchmark
    @Group("opensimplex2")
    @Fork(1)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 5)
    public void benchmarkOS23D() {
        int startX = (int) (Math.random() * 10000);
        int startY = (int) (Math.random() * 10000);
        int startZ = (int) (Math.random() * 10000);
        long seed = (Double.doubleToLongBits(Math.random()) << Double.doubleToLongBits(Math.random())) ^ Double.doubleToLongBits(
            Math.random());
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 384; y++) {
                for(int z = 0; z < 16; z++) {
                    sampler.getNoiseRaw(seed, startX + x, startY + y, startZ + z);
                }
            }
        }
    }

    @Benchmark
    @Group("opensimplex2")
    @Fork(1)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 5)
    public void benchmarkOS22D() {
        int startX = (int) (Math.random() * 10000);
        int startY = (int) (Math.random() * 10000);
        long seed = (Double.doubleToLongBits(Math.random()) << Double.doubleToLongBits(Math.random())) ^ Double.doubleToLongBits(
            Math.random());
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                sampler.getNoiseRaw(seed, startX + x, startY + y);
            }
        }
    }
}

