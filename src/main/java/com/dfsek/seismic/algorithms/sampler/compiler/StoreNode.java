package com.dfsek.seismic.algorithms.sampler.compiler;

import com.dfsek.seismic.type.sampler.Sampler;


/**
 * Replacement node to store a result in a LV entry
 */
public record StoreNode(Sampler in, int index2, int index3) implements Node {
}
