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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Some useful constants.
 */
public final class VMConstants {
    /**
     * True if the Java runtime is a client runtime and C2 compiler is not enabled.
     */
    public static final boolean IS_CLIENT_VM =
        VMConstants.getSysProp("java.vm.info", "").contains("emulated-client");
    /**
     * True if the Java VM is based on Hotspot and has the Hotspot MX bean readable by Seismic.
     */
    public static final boolean IS_HOTSPOT_VM = HotspotVMOptionsUtils.IS_HOTSPOT_VM;
    /**
     * True if jvmci is enabled (e.g. graalvm)
     */
    public static final boolean IS_JVMCI_VM =
        HotspotVMOptionsUtils.get("UseJVMCICompiler").map(Boolean::valueOf).orElse(Boolean.FALSE);
    private static final String UNKNOWN = "Unknown";
    /**
     * JVM vendor info.
     */
    public static final String JVM_VENDOR = VMConstants.getSysProp("java.vm.vendor", VMConstants.UNKNOWN);
    /**
     * JVM vendor name.
     */
    public static final String JVM_NAME = VMConstants.getSysProp("java.vm.name", VMConstants.UNKNOWN);
    /**
     * The value of {@code System.getProperty("os.name")}. *
     */
    public static final String OS_NAME = VMConstants.getSysProp("os.name", VMConstants.UNKNOWN);
    /**
     * True if running on Linux.
     */
    public static final boolean LINUX = VMConstants.OS_NAME.startsWith("Linux");
    /**
     * True if running on Windows.
     */
    public static final boolean WINDOWS = VMConstants.OS_NAME.startsWith("Windows");
    /**
     * True if running on SunOS.
     */
    public static final boolean SUN_OS = VMConstants.OS_NAME.startsWith("SunOS");
    /**
     * True if running on Mac OS X
     */
    public static final boolean MAC_OS_X = VMConstants.OS_NAME.startsWith("Mac OS X");
    /**
     * True if running on FreeBSD
     */
    public static final boolean FREE_BSD = VMConstants.OS_NAME.startsWith("FreeBSD");
    /**
     * The value of {@code System.getProperty("os.arch")}.
     */
    public static final String OS_ARCH = VMConstants.getSysProp("os.arch", VMConstants.UNKNOWN);
    /**
     * True if running on a 64bit JVM
     */
    public static final boolean JRE_IS_64BIT = VMConstants.is64Bit();
    /**
     * The value of {@code System.getProperty("os.version")}.
     */
    public static final String OS_VERSION = VMConstants.getSysProp("os.version", VMConstants.UNKNOWN);
    /**
     * The value of {@code System.getProperty("java.vendor")}.
     */
    public static final String JAVA_VENDOR = VMConstants.getSysProp("java.vendor", VMConstants.UNKNOWN);
    /**
     * true if FMA likely means a cpu instruction and not BigDecimal logic.
     */
    private static final boolean HAS_FMA =
        (!VMConstants.IS_CLIENT_VM) && HotspotVMOptionsUtils.get("UseFMA").map(Boolean::valueOf).orElse(Boolean.FALSE);
    /**
     * maximum supported vectorsize.
     */
    private static final int MAX_VECTOR_SIZE =
        HotspotVMOptionsUtils.get("MaxVectorSize").map(Integer::valueOf).orElse(0);
    /**
     * true for an AMD cpu with SSE4a instructions.
     */
    private static final boolean HAS_SSE4A =
        HotspotVMOptionsUtils.get("UseXmmI2F").map(Boolean::valueOf).orElse(Boolean.FALSE);
    /**
     * true if we know VFMA has faster throughput than separate vmul/vadd.
     */
    public static final boolean HAS_FAST_VECTOR_FMA = VMConstants.hasFastVectorFMA();
    /**
     * true if we know FMA has faster throughput than separate mul/add.
     */
    public static final boolean HAS_FAST_SCALAR_FMA = VMConstants.hasFastScalarFMA();

    private VMConstants() {
    } // can't construct

    private static boolean is64Bit() {
        final String datamodel = VMConstants.getSysProp();
        if(datamodel != null) {
            return datamodel.contains("64");
        } else {
            return (VMConstants.OS_ARCH != null && VMConstants.OS_ARCH.contains("64"));
        }
    }

    private static boolean hasFastVectorFMA() {
        if(VMConstants.HAS_FMA) {
            String value = VMConstants.getSysProp("paralithic.useVectorFMA", "auto");
            if("auto".equals(value)) {
                // newer Neoverse cores have their act together
                // the problem is just apple silicon (this is a practical heuristic)
                if(VMConstants.OS_ARCH.equals("aarch64") && !VMConstants.MAC_OS_X) {
                    return true;
                }
                // zen cores or newer, its a wash, turn it on as it doesn't hurt
                // starts to yield gains for vectors only at zen4+
                if(VMConstants.HAS_SSE4A && VMConstants.MAX_VECTOR_SIZE >= 32) {
                    return true;
                }
                // intel has their act together
                return VMConstants.OS_ARCH.equals("amd64") && !VMConstants.HAS_SSE4A;
            } else {
                return Boolean.parseBoolean(value);
            }
        }
        // everyone else is slow, until proven otherwise by benchmarks
        return false;
    }

    private static boolean hasFastScalarFMA() {
        if(VMConstants.HAS_FMA) {
            String value = VMConstants.getSysProp("seismic.useScalarFMA", "auto");
            if("auto".equals(value)) {
                // newer Neoverse cores have their act together
                // the problem is just apple silicon (this is a practical heuristic)
                if(VMConstants.OS_ARCH.equals("aarch64") && !VMConstants.MAC_OS_X) {
                    return true;
                }
                // latency becomes 4 for the Zen3 (0x19h), but still a wash
                // until the Zen4 anyway, and big drop on previous zens:
                if(VMConstants.HAS_SSE4A && VMConstants.MAX_VECTOR_SIZE >= 64) {
                    return true;
                }
                // intel has their act together
                return VMConstants.OS_ARCH.equals("amd64") && !VMConstants.HAS_SSE4A;
            } else {
                return Boolean.parseBoolean(value);
            }
        }
        // everyone else is slow, until proven otherwise by benchmarks
        return false;
    }

    private static String getSysProp() {
        try {
            return AccessControllerUtils.doPrivileged(() -> System.getProperty("sun.arch.data.model"));
        } catch(
            @SuppressWarnings("unused")
            SecurityException se) {
            VMConstants.logSecurityWarning("sun.arch.data.model");
            return null;
        }
    }

    private static String getSysProp(String property, String def) {
        try {
            return AccessControllerUtils.doPrivileged(() -> System.getProperty(property, def));
        } catch(
            @SuppressWarnings("unused")
            SecurityException se) {
            VMConstants.logSecurityWarning(property);
            return def;
        }
    }

    private static void logSecurityWarning(String property) {
        Logger log = LoggerFactory.getLogger(VMConstants.class);
        log.warn("SecurityManager prevented access to system property: {}", property);
    }
}