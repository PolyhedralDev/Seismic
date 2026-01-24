package com.dfsek.seismic.algorithms.sampler.compiler;

import java.lang.classfile.ClassBuilder;
import java.lang.classfile.CodeBuilder;


public interface Node {
    default CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int zSlot, int max) {
        return b;
    }

    default CodeBuilder build(CodeBuilder b, int seedSlot, int xSlot, int ySlot, int zSlot, int max) {
        return b;
    }

    default ClassBuilder members(ClassBuilder b) {
        return b;
    }
}
