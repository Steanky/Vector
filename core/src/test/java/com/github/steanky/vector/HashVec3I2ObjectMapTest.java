package com.github.steanky.vector;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HashVec3I2ObjectMapTest {
    @Test
    void iterateKeys() {
        Vec3I2ObjectMap<String> map = new HashVec3I2ObjectMap<>(0, 0, 0, 5, 5, 5);
        map.put(0, 0, 0, "test");
        map.put(4, 4, 4, "test2");

        Set<Vec3I> actual = new HashSet<>(2);
        Set<Vec3I> expected = Set.of(Vec3I.immutable(0, 0, 0), Vec3I.immutable(4, 4, 4));
        map.forEach((x, y, z, s) -> {
            actual.add(Vec3I.immutable(x, y, z));
        });

        assertEquals(expected, actual);

    }

    @Test
    void addressableSpace() {
        HashVec3I2ObjectMap<Integer> map = new HashVec3I2ObjectMap<>(0, 0, 0, 2, 2, 2);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    map.put(i, j, k, i * j * k);
                }
            }
        }

        assertEquals(8, map.size());
        assertEquals(8, map.addressableSize());
    }

    @Test
    void accessWrapping() {
        HashVec3I2ObjectMap<Integer> map = new HashVec3I2ObjectMap<>(0, 0, 0, 2, 2, 2);
        map.put(0, 0, 0, 10);

        for (int i = 0; i < 1000; i++) {
            int c = i * 2;
            assertEquals( 10, map.get(c, c, c));
        }

    }

    @Test
    void smallSpace() {
        Vec3I2ObjectMap<String> smallSpace = new HashVec3I2ObjectMap<>(0, 0, 0, 4, 4, 4);
        smallSpace.put(0, 0, 0, "test");
        String result = smallSpace.get(0, 0, 0);
        assertEquals("test", result);
    }

    @Test
    void smallWidths() {
        HashVec3I2ObjectMap<String> map = new HashVec3I2ObjectMap<>(0, 0, 0, 1, 1, 1);
        assertEquals(2, map.width());
        assertEquals(2, map.height());
        assertEquals(2, map.depth());
    }

    @Test
    void mediumWidths() {
        HashVec3I2ObjectMap<String> map = new HashVec3I2ObjectMap<>(0, 0, 0, 16, 16, 16);
        assertEquals(16, map.width());
        assertEquals(16, map.height());
        assertEquals(16, map.depth());
    }

    @Test
    void largeWidths() {
        HashVec3I2ObjectMap<String> map = new HashVec3I2ObjectMap<>(0, 0, 0, Integer.MAX_VALUE, 16,
                16);
        assertEquals(Integer.MAX_VALUE + 1L, map.width());
        assertEquals(16, map.height());
        assertEquals(16, map.depth());
    }

    @Test
    void maximumSpace() {
        assertDoesNotThrow(() -> new HashVec3I2ObjectMap<>(0, 0, 0, Integer.MAX_VALUE, //31 bits
                Integer.MAX_VALUE, //31 bits (62 total)
                4)); //2 bits (64 total, maximum capacity)

        assertThrows(IllegalArgumentException.class,
                () -> new HashVec3I2ObjectMap<>(0, 0, 0, Integer.MAX_VALUE, //31 bits
                        Integer.MAX_VALUE, //31 bits (62 total)
                        5)); //3 bits (65 total, capacity exceeded)
    }

    @Test
    void packedSpace() {
        Vec3I2ObjectMap<Vec3I> map = new HashVec3I2ObjectMap<>(-4, -4, -4, 8, 8, 8);

        for (int i = -4; i < 4; i++) {
            for (int j = -4; j < 4; j++) {
                for (int k = -4; k < 4; k++) {
                    Vec3I vec = Vec3I.immutable(i, j, k);
                    map.put(i, j, k, vec);
                    assertEquals(vec, map.get(i, j, k));
                }
            }
        }
    }

    @Test
    void computeIfAbsent() {
        Vec3I2ObjectMap<Vec3I> map = new HashVec3I2ObjectMap<>(-4, -4, -4, 8, 8, 8);

        Vec3I result = map.computeIfAbsent(0, 0, 0, (x, y, z) -> Vec3I.immutable(10, 10, 10));
        assertEquals(Vec3I.immutable(10, 10, 10), result);
        assertTrue(map.containsKey(0, 0, 0));

        Vec3I result2 = map.computeIfAbsent(0, 0, 0, (x, y, z) -> Vec3I.immutable(20, 20, 20));
        assertSame(result, result2);
    }

    @Test
    void computeIfPresent() {
        Vec3I2ObjectMap<Vec3I> map = new HashVec3I2ObjectMap<>(-4, -4, -4, 8, 8, 8);

        Vec3I result = map.computeIfPresent(0, 0, 0, (x, y, z, old) -> Vec3I.ORIGIN);
        assertNull(result);

        map.put(0, 0, 0, Vec3I.ORIGIN);
        map.computeIfPresent(0, 0, 0, (x, y, z, old) -> {
            assertSame(Vec3I.ORIGIN, old);
            return null;
        });
        assertFalse(map.containsKey(0, 0, 0));
        assertEquals(0, map.size());

        map.put(0, 0, 0, Vec3I.ORIGIN);
        Vec3I value = map.computeIfPresent(0, 0, 0, (x, y, z, old) -> Vec3I.immutable(1, 1, 1));
        assertEquals(Vec3I.immutable(1, 1, 1), value);
        assertTrue(map.containsKey(0, 0, 0));
        assertEquals(1, map.size());
    }

    @Test
    void compute() {
        Vec3I2ObjectMap<Vec3I> map = new HashVec3I2ObjectMap<>(-4, -4, -4, 8, 8, 8);

        Vec3I value = map.compute(0, 0, 0, (x, y, z, old) -> {
            assertNull(old);
            return Vec3I.ORIGIN;
        });
        assertSame(Vec3I.ORIGIN, value);
        assertEquals(1, map.size());
        assertTrue(map.containsKey(0, 0, 0));

        Vec3I removedValue = map.compute(0, 0, 0, (x, y, z, old) -> {
            assertNotNull(old);
            return null;
        });
        assertNull(removedValue);
        assertFalse(map.containsKey(0, 0, 0));
        assertEquals(0, map.size());
    }
}