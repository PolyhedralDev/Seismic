package com.dfsek.seismic.algorithms.sampler.noise;

import com.dfsek.seismic.algorithms.sampler.noise.simplex.OpenSimplex2Sampler;
import com.dfsek.seismic.math.floatingpoint.FloatingPointConstants;
import com.dfsek.seismic.type.sampler.Sampler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class OpenSimplex2SamplerTest {

    @Test
    void getNoiseRaw() {
        Sampler sampler = OpenSimplex2Sampler.instance();
        assertEquals(-0.6864350184376126, sampler.getSample(12, 12, 456), FloatingPointConstants.EPSILON);
    }

    @Test
    void getNoiseRaw3D() {
        Sampler sampler = OpenSimplex2Sampler.instance();
        assertEquals(-0.11021875000090221, sampler.getSample(123, 5674, 43, 423), FloatingPointConstants.EPSILON);
    }
}