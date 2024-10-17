/*
 * Copyright (c) 2020-2024 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.polyhedraldevelopment.seismic.algorithms.sampler.normalizer;


import com.polyhedraldevelopment.seismic.math.statistic.StatisticFunctions;
import com.polyhedraldevelopment.seismic.type.sampler.Sampler;


/**
 * Normalizer to redistribute normally distributed data to a continuous distribution via an automatically generated lookup table.
 */
public class NormalNormalizer extends Normalizer {

    private final double[] lookup;

    public NormalNormalizer(Sampler sampler, int buckets, double mean, double standardDeviation) {
        super(sampler);
        this.lookup = new double[buckets];

        for(int i = 0; i < buckets; i++) {
            lookup[i] = StatisticFunctions.normalInverse((double) i / buckets, mean, standardDeviation);
        }
    }

    @Override
    public double normalize(double in) {
        int start = 0;
        int end = lookup.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(lookup[mid] <= in) {
                start = mid;
            } else {
                end = mid;
            }
        }
        double left = Math.abs(lookup[start] - in);
        double right = Math.abs(lookup[end] - in);

        double fin;
        if(left <= right) {
            fin = (double) start / (lookup.length);
        } else fin = (double) end / (lookup.length);

        return (fin - 0.5) * 2;
    }
}
