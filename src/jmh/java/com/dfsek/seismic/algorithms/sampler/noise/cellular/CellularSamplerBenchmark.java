package com.dfsek.seismic.algorithms.sampler.noise.cellular;

import com.dfsek.seismic.algorithms.sampler.noise.NoiseFunction;
import com.dfsek.seismic.algorithms.sampler.noise.simplex.OpenSimplex2Sampler;
import com.dfsek.seismic.type.DistanceFunction;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class CellularSamplerBenchmark {

    private NoiseFunction cellular;

    private long seed;
    private int startX;
    private int startY;
    private int startZ;

    @Setup
    public void setup() {
        cellular = new CellularSampler(0.02d, 123123, new OpenSimplex2Sampler(0.2d, 12372834),
            DistanceFunction.EuclideanSq, CellularStyleSampler.CellularReturnType.Distance, 1.0d, true);

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
    public double benchmarkCellular3D() {
        double sum = 0.0;

        int sx = startX;
        int sy = startY;
        int sz = startZ;
        long s = seed;
        NoiseFunction ns = cellular;

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
    public double benchmarkCellular2D() {
        double sum = 0.0;

        int sx = startX;
        int sy = startY;
        long s = seed;
        NoiseFunction ns = cellular;

        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                sum += ns.getNoiseRaw(s, sx + x, sy + y);
            }
        }
        return sum;
    }
}