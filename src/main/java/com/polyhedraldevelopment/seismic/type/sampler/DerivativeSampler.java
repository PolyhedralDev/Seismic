package com.polyhedraldevelopment.seismic.type.sampler;

/**
 * A NoiseSampler which additionally may provide a 1st directional derivative
 */
public interface DerivativeSampler extends Sampler {

    static boolean isDifferentiable(Sampler sampler) {
        return sampler instanceof DerivativeSampler dSampler && dSampler.isDifferentiable();
    }

    /**
     * Samplers may or may not be able to provide a derivative depending on what
     * inputs they take, this method signals whether this is the case.
     *
     * @return If the noise sampler provides a derivative or not
     */
    boolean isDifferentiable();

    /**
     * Derivative return version of standard 2D noise evaluation
     *
     * @return 3 element array, in index order: noise value, partial x derivative, partial y derivative
     */
    double[] getSampleDerivative(long seed, double x, double y);

    /**
     * Derivative return version of standard 3D noise evaluation
     *
     * @return 4 element array, in index order: noise value, partial x derivative, partial y derivative, partial z derivative
     */
    double[] getSampleDerivative(long seed, double x, double y, double z);
}
