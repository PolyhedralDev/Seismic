package com.dfsek.seismic.algorithms.sampler.noise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CellularSamplerTest {

    @Test
    void getNoiseRaw() {
        NoiseFunction sampler = new CellularSampler();
        assertEquals(-0.8090170594460182, sampler.getNoiseRaw(12, 12, 456), 0.0001);
    }

    @Test
    void getNoiseRaw3D() {
        NoiseFunction sampler = new CellularSampler();
        assertEquals(-0.8430703036518714, sampler.getNoiseRaw(0, 5674, 43, 423), 0.0001);
    }
}