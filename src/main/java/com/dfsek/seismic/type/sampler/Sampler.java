/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the common/api directory.
 */

package com.dfsek.seismic.type.sampler;


import com.dfsek.seismic.algorithms.sampler.arithmetic.AdditionSampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.DivisionSampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.FrequencySampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.MultiplicationSampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.SaltSampler;
import com.dfsek.seismic.algorithms.sampler.arithmetic.SubtractionSampler;
import com.dfsek.seismic.algorithms.sampler.compiler.Node;
import com.dfsek.seismic.algorithms.sampler.noise.ConstantSampler;
import com.dfsek.seismic.math.range.Range;
import com.dfsek.seismic.type.vector.Vector2;
import com.dfsek.seismic.type.vector.Vector2Int;
import com.dfsek.seismic.type.vector.Vector3;
import com.dfsek.seismic.type.vector.Vector3Int;
import com.dfsek.seismic.util.DynamicClassLoader;
import org.jetbrains.annotations.NotNull;

import java.lang.classfile.ClassFile;
import java.lang.classfile.constantpool.ConstantPoolBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.classfile.ClassFile.ACC_PUBLIC;
import static java.lang.constant.ConstantDescs.CD_double;
import static java.lang.constant.ConstantDescs.CD_long;
import static java.lang.constant.ConstantDescs.CD_void;


public interface Sampler extends Node {
    /**
     * The prime number used for the x-coordinate in noise generation.
     */
    int PRIME_X = 501125321;
    /**
     * The prime number used for the y-coordinate in noise generation.
     */
    int PRIME_Y = 1136930381;
    /**
     * The prime number used for the z-coordinate in noise generation.
     */
    int PRIME_Z = 1720413743;

    static @NotNull Sampler zero() {
        return new ConstantSampler(0);
    }

    /**
     * Get 3D noise at the given {@code Vector3} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector3 the coordinates.
     *
     * @return 3D noise value at coordinates.
     */
    default double getSample(long seed, @NotNull Vector3 vector3) {
        return getSample(seed, vector3.getX(), vector3.getY(), vector3.getZ());
    }

    /**
     * Get 3D noise at the given {@code Vector3Int} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector3 the coordinates.
     *
     * @return 3D noise value at coordinates.
     */
    default double getSample(long seed, @NotNull Vector3Int vector3) {
        return getSample(seed, vector3.getX(), vector3.getY(), vector3.getZ());
    }

    /**
     * Get 2D noise at the given {@code Vector2} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector2 the coordinates.
     *
     * @return 2D noise value at coordinates.
     */
    default double getSample(long seed, @NotNull Vector2 vector2) {
        return getSample(seed, vector2.getX(), vector2.getZ());
    }

    /**
     * Get 2D noise at the given {@code Vector2Int} coordinates, using the given seed.
     *
     * @param seed    a seed.
     * @param vector2 the coordinates.
     *
     * @return 2D noise value at coordinates.
     */
    default double getSample(long seed, @NotNull Vector2Int vector2) {
        return getSample(seed, vector2.getX(), vector2.getZ());
    }

    /**
     * Get 2D noise at the given {@code double} coordinates, using the given seed.
     *
     * @param seed a seed.
     * @param x    X coordinate.
     * @param y    Y coordinate.
     *
     * @return 3D noise value at coordinates.
     */
    double getSample(long seed, double x, double y);

    /**
     * Get 2D noise at the given {@code int} coordinates, using the given seed.
     *
     * @param seed a seed.
     * @param x    X coordinate.
     * @param y    Y coordinate.
     *
     * @return 2D noise value at coordinates.
     */
    default double getSample(long seed, int x, int y) {
        return getSample(seed, (double) x, y);
    }

    /**
     * Get 3D noise at the given {@code double} coordinates, using the given seed.
     *
     * @param seed a seed.
     * @param x    X coordinate.
     * @param y    Y coordinate.
     * @param z    Z coordinate
     *
     * @return 3D noise value at coordinates.
     */
    double getSample(long seed, double x, double y, double z);

    /**
     * Get 3D noise at the given {@code int} coordinates, using the given seed.
     *
     * @param seed a seed.
     * @param x    X coordinate.
     * @param y    Y coordinate.
     * @param z    Z coordinate
     *
     * @return 3D noise value at coordinates.
     */
    default double getSample(long seed, int x, int y, int z) {
        return getSample(seed, (double) x, y, z);
    }

    default Sampler frequency(double frequency) {
        return FrequencySampler.frequency(frequency, this);
    }

    default Sampler frequency(double frequencyX, double frequencyY, double frequencyZ) {
        return FrequencySampler.frequency(frequencyX, frequencyY, frequencyZ, this);
    }

    default Sampler salt(long salt) {
        return SaltSampler.salt(salt, this);
    }

    default Sampler plus(Sampler p) {
        return AdditionSampler.of(this, p);
    }

    default Sampler minus(Sampler p) {
        return new SubtractionSampler(this, p);
    }

    default Sampler mul(Sampler p) {
        return MultiplicationSampler.of(this, p);
    }

    default Sampler div(Sampler p) {
        return DivisionSampler.of(this, p);
    }

    default double frequencyX() {
        return 1;
    }

    default double frequencyY() {
        return 1;
    }

    default double frequencyZ() {
        return 1;
    }

    Range range();
}
