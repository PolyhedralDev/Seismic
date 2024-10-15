package com.polyhedraldevelopment.seismic.math.geometry;

import com.polyhedraldevelopment.seismic.api.vector.Vector3Int;
import com.polyhedraldevelopment.seismic.type.vector.Vector3IntImpl;

import java.util.function.Consumer;


public class GeometryFunctions {
    /**
     * Generates a sphere of points around the given origin and performs the given action on each point.
     *
     * @param origin the center of the sphere.
     * @param radius the radius of the sphere.
     * @param action the action to perform on each point in the sphere.
     */
    public static void sphere(Vector3Int<?, ?> origin, int radius, Consumer<Vector3Int<?, ?>> action) {
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++) {
                for(int z = -radius; z <= radius; z++) {
                    if(x * x + y * y + z * z <= radius * radius) {
                        action.accept(Vector3IntImpl.Mutable.of(x, y, z).add(origin));
                    }
                }
            }
        }
    }

    /**
     * Generates a cube of points around the given origin and performs the given action on each point.
     *
     * @param origin the center of the cube.
     * @param radius the radius of the cube.
     * @param action the action to perform on each point in the cube.
     */
    public static void cube(Vector3Int<?, ?> origin, int radius, Consumer<Vector3Int<?, ?>> action) {
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++) {
                for(int z = -radius; z <= radius; z++) {
                    action.accept(Vector3IntImpl.Mutable.of(x, y, z).add(origin));
                }
            }
        }
    }
}
