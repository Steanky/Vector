package com.github.steanky.vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vec3ICacheTest {
    @Test
    void cachesAll() {
        for (int i = -4; i < 4; i++) {
            for (int j = -4; j < 4; j++) {
                for (int k = -4; k < 4; k++) {
                    Vec3I cached = Vec3ICache.cached(i, j, k);

                    assertNotNull(cached);
                    assertEquals(i, cached.getX());
                    assertEquals(j, cached.getY());
                    assertEquals(k, cached.getZ());

                    assertSame(cached, Vec3ICache.cached(i, j, k));
                }
            }
        }
    }

    @Test
    void cacheOutOfBounds() {
        Vec3I outOfBounds = Vec3ICache.cached(0, 0, 5);
        Vec3I outOfBounds2 = Vec3ICache.cached(0, 0, 5);
        assertNotSame(outOfBounds, outOfBounds2);
    }
}