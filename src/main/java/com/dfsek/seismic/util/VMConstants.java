// Sourced from Apache Lucene.
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dfsek.seismic.util;

import java.util.logging.Logger;

/**
 * Some useful constants.
 */
public final class VMConstants {
    /**
     * True if the Java runtime is a client runtime and C2 compiler is not enabled.
     */
    public static final boolean IS_CLIENT_VM =
            getSysProp("java.vm.info", "").contains("emulated-client");
    /**
     * True if the Java VM is based on Hotspot and has the Hotspot MX bean readable by Seismic.
     */
    public static final boolean IS_HOTSPOT_VM = HotspotVMOptionsUtils.IS_HOTSPOT_VM;
    /**
     * True if jvmci is enabled (e.g. graalvm)
     */
    public static final boolean IS_JVMCI_VM =
            HotspotVMOptionsUtils.get("UseJVMCICompiler").map(Boolean::valueOf).orElse(false);
    private static final String UNKNOWN = "Unknown";
    /**
     * JVM vendor info.
     */
    public static final String JVM_VENDOR = getSysProp("java.vm.vendor", UNKNOWN);
    /**
     * JVM vendor name.
     */
    public static final String JVM_NAME = getSysProp("java.vm.name", UNKNOWN);
    /**
     * The value of <code>System.getProperty("os.name")</code>. *
     */
    public static final String OS_NAME = getSysProp("os.name", UNKNOWN);
    /**
     * True if running on Linux.
     */
    public static final boolean LINUX = OS_NAME.startsWith("Linux");
    /**
     * True if running on Windows.
     */
    public static final boolean WINDOWS = OS_NAME.startsWith("Windows");
    /**
     * True if running on SunOS.
     */
    public static final boolean SUN_OS = OS_NAME.startsWith("SunOS");
    /**
     * True if running on Mac OS X
     */
    public static final boolean MAC_OS_X = OS_NAME.startsWith("Mac OS X");
    /**
     * True if running on FreeBSD
     */
    public static final boolean FREE_BSD = OS_NAME.startsWith("FreeBSD");
    /**
     * The value of <code>System.getProperty("os.arch")</code>.
     */
    public static final String OS_ARCH = getSysProp("os.arch", UNKNOWN);
    /**
     * True if running on a 64bit JVM
     */
    public static final boolean JRE_IS_64BIT = is64Bit();
    /**
     * The value of <code>System.getProperty("os.version")</code>.
     */
    public static final String OS_VERSION = getSysProp("os.version", UNKNOWN);
    /**
     * The value of <code>System.getProperty("java.vendor")</code>.
     */
    public static final String JAVA_VENDOR = getSysProp("java.vendor", UNKNOWN);
    /**
     * true if FMA likely means a cpu instruction and not BigDecimal logic.
     */
    private static final boolean HAS_FMA =
            (!IS_CLIENT_VM) && HotspotVMOptionsUtils.get("UseFMA").map(Boolean::valueOf).orElse(false);
    /**
     * maximum supported vectorsize.
     */
    private static final int MAX_VECTOR_SIZE =
            HotspotVMOptionsUtils.get("MaxVectorSize").map(Integer::valueOf).orElse(0);
    /**
     * true for an AMD cpu with SSE4a instructions.
     */
    private static final boolean HAS_SSE4A =
            HotspotVMOptionsUtils.get("UseXmmI2F").map(Boolean::valueOf).orElse(false);
    /**
     * true if we know VFMA has faster throughput than separate vmul/vadd.
     */
    public static final boolean HAS_FAST_VECTOR_FMA = hasFastVectorFMA();
    /**
     * true if we know FMA has faster throughput than separate mul/add.
     */
    public static final boolean HAS_FAST_SCALAR_FMA = hasFastScalarFMA();
    /**
     * true if we know ROUND intrinsics are available.
     */
    public static final boolean HAS_FAST_SCALAR_ROUND = hasFastScalarRound();
    /**
     * true if we know FLOOR intrinsics are available.
     */
    public static final boolean HAS_FAST_SCALAR_FLOOR = hasFastScalarRound();
    /**
     * true if we know CEIL intrinsics are available.
     */
    public static final boolean HAS_FAST_SCALAR_CEIL = hasFastScalarRound();

    private VMConstants() {
    } // can't construct

    private static boolean is64Bit() {
        final String datamodel = getSysProp("sun.arch.data.model");
        if (datamodel != null) {
            return datamodel.contains("64");
        } else {
            return (OS_ARCH != null && OS_ARCH.contains("64"));
        }
    }

    private static boolean hasFastVectorFMA() {
        if (HAS_FMA) {
            String value = getSysProp("paralithic.useVectorFMA", "auto");
            if ("auto".equals(value)) {
                // newer Neoverse cores have their act together
                // the problem is just apple silicon (this is a practical heuristic)
                if (OS_ARCH.equals("aarch64") && !MAC_OS_X) {
                    return true;
                }
                // zen cores or newer, its a wash, turn it on as it doesn't hurt
                // starts to yield gains for vectors only at zen4+
                if (HAS_SSE4A && MAX_VECTOR_SIZE >= 32) {
                    return true;
                }
                // intel has their act together
                return OS_ARCH.equals("amd64") && !HAS_SSE4A;
            } else {
                return Boolean.parseBoolean(value);
            }
        }
        // everyone else is slow, until proven otherwise by benchmarks
        return false;
    }

    private static boolean hasFastScalarFMA() {
        if (HAS_FMA) {
            String value = getSysProp("paralithic.useScalarFMA", "auto");
            if ("auto".equals(value)) {
                // newer Neoverse cores have their act together
                // the problem is just apple silicon (this is a practical heuristic)
                if (OS_ARCH.equals("aarch64") && !MAC_OS_X) {
                    return true;
                }
                // latency becomes 4 for the Zen3 (0x19h), but still a wash
                // until the Zen4 anyway, and big drop on previous zens:
                if (HAS_SSE4A && MAX_VECTOR_SIZE >= 64) {
                    return true;
                }
                // intel has their act together
                return OS_ARCH.equals("amd64") && !HAS_SSE4A;
            } else {
                return Boolean.parseBoolean(value);
            }
        }
        // everyone else is slow, until proven otherwise by benchmarks
        return false;
    }

    private static boolean hasFastScalarRound() {
        //TODO extend to other architectures
        return HAS_SSE4A;
    }

    private static String getSysProp(String property) {
        try {
            return AccessControllerUtils.doPrivileged(() -> System.getProperty(property));
        } catch (
                @SuppressWarnings("unused")
                SecurityException se) {
            logSecurityWarning(property);
            return null;
        }
    }

    private static String getSysProp(String property, String def) {
        try {
            return AccessControllerUtils.doPrivileged(() -> System.getProperty(property, def));
        } catch (
                @SuppressWarnings("unused")
                SecurityException se) {
            logSecurityWarning(property);
            return def;
        }
    }

    private static void logSecurityWarning(String property) {
        var log = Logger.getLogger(VMConstants.class.getName());
        log.warning("SecurityManager prevented access to system property: " + property);
    }
}