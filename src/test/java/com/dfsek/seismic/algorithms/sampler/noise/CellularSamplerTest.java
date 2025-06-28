package com.dfsek.seismic.algorithms.sampler.noise;

import com.dfsek.seismic.algorithms.sampler.noise.simplex.OpenSimplex2Sampler;
import com.dfsek.seismic.type.DistanceFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CellularSamplerTest {

    @Test
    void getNoiseRaw() {
        NoiseFunction sampler = new CellularSampler(0.02d, 123123, new OpenSimplex2Sampler(0.2d, 12372834), DistanceFunction.EuclideanSq, CellularSampler.ReturnType.Distance, 1.0d, true);
        assertEquals(-0.8090170594460182, sampler.getNoiseRaw(12, 12, 456), 0.0001);
    }

    @Test
    void getNoiseRaw3D() {
        NoiseFunction sampler = new CellularSampler(0.02d, 123123, new OpenSimplex2Sampler(0.2d, 12372834), DistanceFunction.EuclideanSq, CellularSampler.ReturnType.Distance, 1.0d, true);
        assertEquals(-0.8430703036518714, sampler.getNoiseRaw(0, 5674, 43, 423), 0.0001);
    }
}