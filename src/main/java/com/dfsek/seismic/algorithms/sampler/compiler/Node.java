package com.dfsek.seismic.algorithms.sampler.compiler;

import com.dfsek.seismic.type.sampler.Sampler;
import com.dfsek.seismic.util.DynamicClassLoader;

import java.lang.classfile.ClassBuilder;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.classfile.constantpool.ConstantPoolBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.classfile.ClassFile.ACC_PUBLIC;
import static java.lang.constant.ConstantDescs.CD_double;
import static java.lang.constant.ConstantDescs.CD_long;
import static java.lang.constant.ConstantDescs.CD_void;


public interface Node {
    default CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int zSlot, MaxS max) {
        return b;
    }

    default CodeBuilder build(CodeBuilder b, ClassDesc clazz, int seedSlot, int xSlot, int ySlot, int zSlot, MaxS max) {
        return b;
    }

    default ClassBuilder members(ClassBuilder b, ClassDesc clazz) {
        return b;
    }

    default Node optimise() {
        return this;
    }

    default int lvSize2() {
        return 0;
    }

    default int lvSize3() {
        return 0;
    }

    default Sampler compile() {
        ClassDesc clazz = DynamicClassLoader.generate("com.dfsek.seismic.generated.TestSampler");
        byte[] clazzBytes = ClassFile
            .of()
            .build(clazz,
                classBuilder -> members(classBuilder
                    .withInterfaces(ConstantPoolBuilder.of().classEntry(ClassDesc.of(Sampler.class.getName())))
                    .withMethod("getSample",
                        MethodTypeDesc.of(CD_double, CD_long, CD_double, CD_double, CD_double),
                        ACC_PUBLIC,
                        b -> b.withCode(c -> build(c, clazz, 1, 3, 5, 7, new MaxS(9, lvSize2(), lvSize3())).dreturn())
                    )
                    .withMethod("<init>",
                        MethodTypeDesc.of(CD_void),
                        ACC_PUBLIC,
                        b -> b.withCode(c -> c
                            .aload(0)
                            .invokespecial(ClassDesc.of("java.lang.Object"), "<init>", MethodTypeDesc.of(CD_void))
                            .return_())
                    ), clazz));
        DynamicClassLoader loader = new DynamicClassLoader();

        Class<?> clazzD = loader.defineClass(clazz.packageName() + "." + clazz.displayName(), clazzBytes);
        try {
            Object instance = clazzD.getDeclaredConstructor().newInstance();
            return (Sampler) instance;
        } catch(ReflectiveOperationException e) {
            throw new Error(e); // Should literally never happen
        }
    }
}
