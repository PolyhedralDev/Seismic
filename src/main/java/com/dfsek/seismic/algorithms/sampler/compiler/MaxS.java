package com.dfsek.seismic.algorithms.sampler.compiler;

public record MaxS(int max, int maxS2, int maxS3) {
    public MaxS store2() {
        return new MaxS(max + 2, maxS2, maxS3);
    }

    public MaxS store() {
        return new MaxS(max + 1, maxS2, maxS3);
    }
}
