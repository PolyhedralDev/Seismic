package com.dfsek.seismic.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class UnsafeUtilsTest {

    @Test
    public void testAcquireUnsafe() {
        assertNotNull(UnsafeUtils.UNSAFE);
    }
}
