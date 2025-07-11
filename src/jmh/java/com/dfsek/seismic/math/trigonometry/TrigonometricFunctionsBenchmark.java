package com.dfsek.seismic.math.trigonometry;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Random;


@State(Scope.Benchmark)
public class TrigonometricFunctionsBenchmark {
    private final Random random = new Random();

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkSinJava() {
        double input = random.nextDouble() * 2 * Math.PI;
        double _x = Math.sin(input);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkSinFast() {
        double input = random.nextDouble() * 2 * Math.PI;
        double _x = TrigonometryFunctions.sin(input);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkCosJava() {
        double input = random.nextDouble() * 2 * Math.PI;
        double _x = Math.cos(input);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkCosFast() {
        double input = random.nextDouble() * 2 * Math.PI;
        double _x = TrigonometryFunctions.cos(input);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkTanJava() {
        double input = random.nextDouble() * 2 * Math.PI;
        double _x = Math.tan(input);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkTanFast() {
        double input = random.nextDouble() * 2 * Math.PI;
        double _x = TrigonometryFunctions.tan(input);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkAtan2Java() {
        double y = random.nextDouble() * 2 - 1;
        double x = random.nextDouble() * 2 - 1;
        double _x = Math.atan2(y, x);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public void benchmarkAtan2Fast() {
        double y = random.nextDouble() * 2 - 1;
        double x = random.nextDouble() * 2 - 1;
        double _x = TrigonometryFunctions.atan2(y, x);
    }
}
