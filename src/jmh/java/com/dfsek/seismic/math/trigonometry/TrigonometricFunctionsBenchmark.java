package com.dfsek.seismic.math.trigonometry;

import org.openjdk.jmh.annotations.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class TrigonometricFunctionsBenchmark {
    private static final int SIZE = 65536;
    private static final int MASK = SIZE - 1;

    private double[] input1; // For sin/cos/tan
    private double[] input2; // For atan2 (y)

    private int index;

    @Setup
    public void setup() {
        input1 = new double[SIZE];
        input2 = new double[SIZE];
        Random random = new Random();

        for (int i = 0; i < SIZE; i++) {
            input1[i] = random.nextDouble() * 2 * Math.PI;
            input2[i] = random.nextDouble() * 2 * Math.PI;
        }
        index = 0;
    }

    private int nextIndex() {
        return index++ & MASK;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public double benchmarkSinJava() {
        return Math.sin(input1[nextIndex()]);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public double benchmarkSinFast() {
        return TrigonometryFunctions.sin(input1[nextIndex()]);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public double benchmarkCosJava() {
        return Math.cos(input1[nextIndex()]);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public double benchmarkCosFast() {
        return TrigonometryFunctions.cos(input1[nextIndex()]);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public double benchmarkTanJava() {
        return Math.tan(input1[nextIndex()]);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public double benchmarkTanFast() {
        return TrigonometryFunctions.tan(input1[nextIndex()]);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public double benchmarkAtan2Java() {
        int i = nextIndex();
        return Math.atan2(input1[i], input2[i]);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, time = 5)
    public double benchmarkAtan2Fast() {
        int i = nextIndex();
        return TrigonometryFunctions.atan2(input1[i], input2[i]);
    }
}