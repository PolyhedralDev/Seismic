package com.dfsek.seismic.algorithms.sampler.noise;

import com.dfsek.seismic.algorithms.sampler.noise.fractal.BrownianMotionSampler;
import com.dfsek.seismic.algorithms.sampler.noise.fractal.WeightedBrownianMotionSampler;
import com.dfsek.seismic.algorithms.sampler.noise.simplex.OpenSimplex2Sampler;
import com.dfsek.seismic.type.sampler.Sampler;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class CompilerTest {
    @Test
    public void validateCompiler(){
        Sampler s = BrownianMotionSampler.of(OpenSimplex2Sampler.instance(), 0.5, 2, 0, 4).compile();
        Sampler s2 = new WeightedBrownianMotionSampler(OpenSimplex2Sampler.instance(), 0.5, 2, 0, 4);

        Random r = new Random();
        for(int i = 0; i < 1000; i++) {
            long seed = r.nextLong();
            int x = r.nextInt();
            int y = r.nextInt();
            int z = r.nextInt();
            assertEquals(s.getSample(seed, x, y, z), s2.getSample(seed, x, y, z));
        }
    }
}
