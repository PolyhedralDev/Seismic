package com.dfsek.seismic.algorithms.rotation;

import com.dfsek.seismic.types.Rotation;
import com.dfsek.seismic.types.vector.Vector2;


public class RotationFunctions {
    /**
     * Rotate and mirror a coordinate pair.
     *
     * @param orig Vector to rotate.
     * @param r    Rotation
     *
     * @return Rotated vector
     */
    public static Vector2 rotateVector(Vector2 orig, Rotation r) {
        Vector2.Mutable copy = orig.mutable();
        switch(r) {
            case CW_90 -> copy.setX(orig.getZ()).setZ(-orig.getX());
            case CCW_90 -> copy.setX(-orig.getZ()).setZ(orig.getX());
            case CW_180 -> copy.multiply(-1);
        }
        return copy.immutable();
    }
}
