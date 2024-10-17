/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the common/api directory.
 */

package com.polyhedraldevelopment.seismic.type;

public enum Rotation {

    CW_90(90),
    CW_180(180),
    CCW_90(270),
    NONE(0);
    private final int degrees;

    Rotation(int degrees) {
        this.degrees = degrees;
    }

    /**
     * Converts degrees to a corresponding Rotation enum value.
     *
     * @param deg the degrees to convert
     *
     * @return the corresponding Rotation enum value
     *
     * @throws IllegalArgumentException if the degrees do not match any Rotation
     */
    public static Rotation fromDegrees(int deg) {
        return switch(Math.floorMod(deg, 360)) {
            case 0 -> Rotation.NONE;
            case 90 -> Rotation.CW_90;
            case 180 -> Rotation.CW_180;
            case 270 -> Rotation.CCW_90;
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Returns the inverse of the current Rotation.
     *
     * @return the inverse Rotation
     */
    public Rotation inverse() {
        return switch(this) {
            case NONE -> NONE;
            case CCW_90 -> CW_90;
            case CW_90 -> CCW_90;
            case CW_180 -> CW_180;
        };
    }

    /**
     * Rotates the current Rotation by the specified Rotation.
     *
     * @param rotation the Rotation to apply
     *
     * @return the resulting Rotation
     */
    public Rotation rotate(Rotation rotation) {
        return fromDegrees(this.getDegrees() + rotation.getDegrees());
    }

    /**
     * Returns the degrees of the current Rotation.
     *
     * @return the degrees of the current Rotation
     */
    public int getDegrees() {
        return degrees;
    }

    /**
     * Enum representing the axes of rotation.
     */
    public enum Axis {
        X,
        Y,
        Z
    }
}
