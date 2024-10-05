package com.dfsek.seismic.util;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class AccessControllerUtils {
    // Extracted to a method to be able to apply the SuppressForbidden annotation
    @SuppressWarnings("removal")
    @SuppressForbidden(reason = "security manager")
    public static <T> T doPrivileged(PrivilegedAction<T> action) {
        return AccessController.doPrivileged(action);
    }
}
