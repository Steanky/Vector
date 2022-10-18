package com.github.steanky.vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashVec3I2ObjectMapTest {
    @Test
    void smallSpace() {
        Vec3I2ObjectMap<String> smallSpace = new HashVec3I2ObjectMap<>(0, 0, 0, 4, 4, 4);
        smallSpace.put(0, 0, 0, "test");
        String result = smallSpace.get(0, 0, 0);
        assertEquals("test", result);
    }

    @Test
    void maximumSpace() {
        assertDoesNotThrow(() -> new HashVec3I2ObjectMap<>(0, 0, 0,
                Integer.MAX_VALUE, //31 bits
                Integer.MAX_VALUE, //31 bits (62 total)
                3)); //2 bits (64 total, maximum capacity)

        assertThrows(IllegalArgumentException.class, () -> new HashVec3I2ObjectMap<>(0, 0, 0,
                Integer.MAX_VALUE, //31 bits
                Integer.MAX_VALUE, //31 bits (62 total)
                4)); //3 bits (65 total, capacity exceeded)
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
}