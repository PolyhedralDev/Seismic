package com.dfsek.seismic.algorithms.sampler.noise;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Benchmark)
public class CellularSamplerBenchmark {
    private final NoiseFunction cellular = new CellularSampler();

    @Benchmark
    @Group("cellular")
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkCellular3D() {
        cellular.getNoiseRaw(0, 0, 0, 0);
    }

    @Benchmark
    @Group("cellular")
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkCellular2D() {
        cellular.getNoiseRaw(0, 0, 0);
    }
}
