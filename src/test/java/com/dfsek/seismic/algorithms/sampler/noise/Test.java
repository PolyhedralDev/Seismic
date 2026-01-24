package com.dfsek.seismic.algorithms.sampler.noise;

import com.dfsek.seismic.algorithms.sampler.arithmetic.AdditionSampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.FrequencySampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.SaltSampler;
import com.dfsek.seismic.type.sampler.Sampler;

import java.io.IOException;


public class Test {
    static void main() throws IOException {

        Sampler s = FrequencySampler.frequency(0.5, SaltSampler.salt(10L, new AdditionSampler(new ConstantSampler(1), new ConstantSampler(2)))).compile();
        System.out.printf("%s", s.getSample(0, 1, 2, 3));
    }
}
