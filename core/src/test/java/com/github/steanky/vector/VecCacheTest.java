package com.github.steanky.vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VecCacheTest {
    @Test
    void cachesAll() {
        for (int i = -4; i < 4; i++) {
            for (int j = -4; j < 4; j++) {
                for (int k = -4; k < 4; k++) {
                    Vec3I cached = VecCache.cached(i, j, k);

                    assertNotNull(cached);
                    assertEquals(i, cached.x());
                    assertEquals(j, cached.y());
                    assertEquals(k, cached.z());

                    assertSame(cached, VecCache.cached(i, j, k));
                }
            }
        }
    }

    @Test
    void cacheOutOfBounds() {
        Vec3I outOfBounds = VecCache.cached(0, 0, 5);
        Vec3I outOfBounds2 = VecCache.cached(0, 0, 5);
        assertNotSame(outOfBounds, outOfBounds2);
    }
}