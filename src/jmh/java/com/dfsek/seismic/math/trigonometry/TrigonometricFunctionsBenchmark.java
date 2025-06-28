package com.dfsek.seismic.math.trigonometry;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;


@State(Scope.Benchmark)
public class TrigonometricFunctionsBenchmark {
    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkAtan2Java() {
        double _x = Math.atan2(1.0, 1.0);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkAtan2Fast() {
        double _x = TrigonometryFunctions.fastAtan2(1, 1);
    }
}
