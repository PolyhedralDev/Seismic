package com.dfsek.seismic.algorithms.sampler.noise;

import com.dfsek.seismic.algorithms.sampler.DomainWarpedSampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.AdditionSampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.FrequencySampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.SaltSampler;
import com.dfsek.seismic.algorithms.sampler.noise.simplex.OpenSimplex2Sampler;
import com.dfsek.seismic.type.sampler.Sampler;

import java.io.IOException;


public class Test {
    static void main() throws IOException {

        Sampler s = FrequencySampler.frequency(0.5, SaltSampler.salt(10L, new AdditionSampler(new OpenSimplex2Sampler(), new ConstantSampler(2)))).compile();
        Sampler s2 = new DomainWarpedSampler(new OpenSimplex2Sampler(), new OpenSimplex2Sampler(), 0.8).compile();
        System.out.printf("%s", s2.getSample(0, 100.4, 2, 3));
    }
}
