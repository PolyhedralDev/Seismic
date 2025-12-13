package com.dfsek.seismic.algorithms.sampler.noise.simplex;

import com.dfsek.seismic.algorithms.sampler.noise.NoiseFunction;
import org.openjdk.jmh.annotations.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class OpenSimplex2SamplerBenchmark {
    private NoiseFunction sampler;

    private long seed;
    private int startX;
    private int startY;
    private int startZ;

    @Setup
    public void setup() {
        sampler = new OpenSimplex2Sampler(0.02d, 123123L << 1);

        Random r = new Random();
        startX = r.nextInt(10000);
        startY = r.nextInt(10000);
        startZ = r.nextInt(10000);

        seed = r.nextLong();
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 5)
    public double benchmarkOS23D() {
        double sum = 0.0;

        int sx = startX;
        int sy = startY;
        int sz = startZ;
        long s = seed;
        NoiseFunction ns = sampler;

        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 384; y++) {
                for(int z = 0; z < 16; z++) {
                    sum += ns.getNoiseRaw(s, sx + x, sy + y, sz + z);
                }
            }
        }
        return sum;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 5)
    public double benchmarkOS22D() {
        double sum = 0.0;

        int sx = startX;
        int sy = startY;
        long s = seed;
        NoiseFunction ns = sampler;

        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                sum += ns.getNoiseRaw(s, sx + x, sy + y);
            }
        }
        return sum;
    }
}