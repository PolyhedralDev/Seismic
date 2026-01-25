package com.dfsek.seismic.algorithms.sampler.compiler;

import java.lang.classfile.ClassBuilder;
import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;


public interface Node {
    default CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int zSlot, int max) {
        return b;
    }

    default CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int ySlot, int zSlot, int max) {
        return b;
    }

    default ClassBuilder members(ClassBuilder b, ClassDesc clazz) {
        return b;
    }
}
