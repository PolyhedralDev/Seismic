package com.dfsek.seismic.util;

import com.dfsek.seismic.algorithms.sampler.compiler.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class DynamicClassLoader extends ClassLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicClassLoader.class);
    public DynamicClassLoader() {
        super(Node.class.getClassLoader());
    }

    public Class<?> defineClass(String name, byte[] data) {
        File dump = new File("./.seismic/out/classes/" + name.substring(name.lastIndexOf('.') + 1) + ".class");
        dump.getParentFile().mkdirs();
        LOGGER.info("Dumping class {} to {}", name, dump.getAbsolutePath());
        try(FileOutputStream out = new FileOutputStream(dump)) {
            out.write(data);
        } catch(IOException e) {
            LOGGER.error("Failed to dump class.", e);
        }
        return defineClass(name, data, 0, data.length);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return Class.forName(name);
    }
}
