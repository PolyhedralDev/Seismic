package com.dfsek.seismic.algorithms.sampler.noise.simplex;

public abstract class OpenSimplex2StyleSampler extends SimplexStyleSampler {
    protected static final double SQRT3 = 1.7320508075688772935274463415059;
    protected static final double SKEW_2D = 0.5 * (OpenSimplex2StyleSampler.SQRT3 - 1.0);
    protected static final double UNSKEW_2D = (3 - OpenSimplex2StyleSampler.SQRT3) / 6;
    protected static final double UNSKEW_2D_MINUS_1 = OpenSimplex2StyleSampler.UNSKEW_2D - 1;
    protected static final double DOUBLE_UNSKEW_2D = 2 * OpenSimplex2StyleSampler.UNSKEW_2D;
    protected static final double ONE_MINUS_DOUBLE_UNSKEW_2D = 1 - OpenSimplex2StyleSampler.DOUBLE_UNSKEW_2D;
    protected static final double GRADIENT_SCALE_PRIMARY = (2 * OpenSimplex2StyleSampler.ONE_MINUS_DOUBLE_UNSKEW_2D * (1 / OpenSimplex2StyleSampler.UNSKEW_2D - 2));
    protected static final double GRADIENT_SCALE_SECONDARY = (-2 * OpenSimplex2StyleSampler.ONE_MINUS_DOUBLE_UNSKEW_2D *
                                                              OpenSimplex2StyleSampler.ONE_MINUS_DOUBLE_UNSKEW_2D);
    protected static final double DOUBLE_UNSKEW_2D_MINUS_1 = OpenSimplex2StyleSampler.DOUBLE_UNSKEW_2D - 1;
    protected static final double ROTATE_3D = (2.0 / 3.0);
    protected static final double RSQUARED_2D = 0.5;
    protected static final double RSQUARED_3D = 0.6;

    public OpenSimplex2StyleSampler(double frequency, long salt) {
        super(frequency, salt);
    }
}
