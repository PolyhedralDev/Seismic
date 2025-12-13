package com.dfsek.seismic.algorithms.sampler.noise;

import com.dfsek.seismic.algorithms.sampler.noise.cellular.CellularSampler;
import com.dfsek.seismic.algorithms.sampler.noise.cellular.CellularStyleSampler;
import com.dfsek.seismic.algorithms.sampler.noise.simplex.OpenSimplex2Sampler;
import com.dfsek.seismic.math.floatingpoint.FloatingPointConstants;
import com.dfsek.seismic.type.DistanceFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class OpenSimplex2SamplerTest {

    @Test
    void getNoiseRaw() {
        NoiseFunction sampler = new OpenSimplex2Sampler(0.02d, 12372834);
        assertEquals(-0.6864350184376126, sampler.getNoiseRaw(12, 12, 456), FloatingPointConstants.EPSILON);
    }

    @Test
    void getNoiseRaw3D() {
        NoiseFunction sampler = new OpenSimplex2Sampler(0.2d, 12372835);
        assertEquals(-0.11021875000090221, sampler.getNoiseRaw(123, 5674, 43, 423), FloatingPointConstants.EPSILON);
    }
}