package com.github.steanky.vector;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Bounds3ITest {
    @Test
    void singleIteration() {
        Bounds3I bounds = Bounds3I.immutable(0, 0, 0, 1, 1, 1);
        List<Vec3I> vectors = new ArrayList<>(1);
        bounds.forEach((x, y, z) -> vectors.add(Vec3I.immutable(x, y, z)));

        assertEquals(1, vectors.size());
        assertEquals(Vec3I.immutable(0, 0, 0), vectors.get(0));
    }

    @Test
    void expand() {
        Bounds3I bounds = Bounds3I.mutable(0, 0, 0, 1, 1, 1);
        bounds.expand(1);

        assertEquals(-1, bounds.originX());
        assertEquals(-1, bounds.originY());
        assertEquals(-1, bounds.originZ());

        assertEquals(2, bounds.maxX());
        assertEquals(2, bounds.maxY());
        assertEquals(2, bounds.maxZ());
    }
}