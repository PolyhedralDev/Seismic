package com.dfsek.seismic.algorithms.sampler.noise.random;

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

import java.lang.classfile.ClassFile;
import java.lang.constant.ConstantDescs;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import  static java.lang.constant.ConstantDescs.*;


@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class WhiteNoiseBenchmark {

    private WhiteNoiseSampler f;

    public static void main(String... args) {
        byte[] bytes = ClassFile.of().build(CD_Hello,
            clb -> clb.withFlags(ClassFile.ACC_PUBLIC)
                .withMethod(ConstantDescs.INIT_NAME, ConstantDescs.MTD_void,
                    ClassFile.ACC_PUBLIC,
                    mb -> mb.withCode(
                        cob -> cob.aload(0)
                            .invokespecial(ConstantDescs.CD_Object,
                                ConstantDescs.INIT_NAME, ConstantDescs.MTD_void)
                            .return_()))
                .withMethod("main", MTD_void_StringArray, ClassFile.ACC_PUBLIC + ClassFile.ACC_STATIC,
                    mb -> mb.withCode(
                        cob -> cob.getstatic(CD_System, "out", CD_PrintStream)
                            .ldc("Hello World")
                            .invokevirtual(CD_PrintStream, "println", MTD_void_String)
                            .return_())));

    }

    private long seed;
    private int startX;
    private int startY;
    private int startZ;

    @Setup
    public void setup() {
        f = new WhiteNoiseSampler(123123);

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
    public double mask() {
        double sum = 0.0;

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                sum += Double.longBitsToDouble(WhiteNoiseSampler.randomBits(seed, x, z) & 0x000fffffffffffffL);
            }
        }
        return sum;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 5)
    public double shift() {
        double sum = 0.0;

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                sum += Double.longBitsToDouble(WhiteNoiseSampler.randomBits(seed, x, z) >>> 12);
            }
        }
        return sum;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 5)
    public double benchmarkWhiteNoise3D() {
        double sum = 0.0;

        int sx = startX;
        int sy = startY;
        int sz = startZ;
        long s = seed;
        WhiteNoiseSampler ns = f;

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                for(int y = 0; y < 384; y++) {
                    sum += ns.getSample(s, sx + x, sy + y, sz + z);
                }
            }
        }
        return sum;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 5)
    public double benchmarkWhiteNoise2D() {
        double sum = 0.0;

        int sx = startX;
        int sy = startY;
        long s = seed;
        WhiteNoiseSampler ns = f;

        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                sum += ns.getSample(s, sx + x, sy + y);
            }
        }
        return sum;
    }
}
