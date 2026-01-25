package com.dfsek.seismic.algorithms.sampler.noise;

import com.dfsek.seismic.algorithms.sampler.DomainWarpedSampler;
import com.dfsek.seismic.algorithms.sampler.KernelSampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.AdditionSampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.FrequencySampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.SaltSampler;
import com.dfsek.seismic.algorithms.sampler.noise.fractal.BrownianMotionSampler;
import com.dfsek.seismic.algorithms.sampler.noise.fractal.WeightedBrownianMotionSampler;
import com.dfsek.seismic.algorithms.sampler.noise.simplex.OpenSimplex2Sampler;
import com.dfsek.seismic.type.sampler.Sampler;

import java.io.IOException;


public class Test {
    static void main() throws IOException {
        Sampler s = new KernelSampler(new double[][]{
            new double[] {0,  1, 0},
            new double[] {1, -4, 1},
            new double[] {0,  1, 0}
        }, OpenSimplex2Sampler.instance());
        s = new KernelSampler(new double[][]{
            new double[] {0,  1, 0},
            new double[] {1, -4, 1},
            new double[] {0,  1, 0}
        }, s);
        s = new KernelSampler(new double[][]{
            new double[] {0,  1, 0},
            new double[] {1, -4, 1},
            new double[] {0,  1, 0}
        }, s);
        Sampler s2 = s.compile();
        //Sampler s = BrownianMotionSampler.of(OpenSimplex2Sampler.instance(), 0.5, 2, 0, 4).compile();
        //Sampler s2 = new WeightedBrownianMotionSampler(OpenSimplex2Sampler.instance(), 0.5, 2, 0, 4).compile();
        //Sampler s2 = new DomainWarpedSampler(OpenSimplex2Sampler.instance(), OpenSimplex2Sampler.instance(), 0.8).compile();
        System.out.printf("%s", s.getSample(0, 100.4, 2, 3));
        System.out.printf("%s", s2.getSample(0, 100.4, 2, 3));
    }
}
