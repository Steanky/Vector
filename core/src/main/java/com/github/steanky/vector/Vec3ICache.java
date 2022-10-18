package com.github.steanky.vector;

import org.jetbrains.annotations.NotNull;

/**
 * Internal class for caching vectors. Not part of the public API.
 */
class Vec3ICache {
    /**
     * The common {@link ThreadLocal} instance used for thread-local mutable vectors.
     */
    static final ThreadLocal<Vec3I> THREAD_LOCAL = ThreadLocal.withInitial(() -> Vec3I.mutable(0, 0, 0));

    //8x8x8 cube of vectors centered at the origin
    private static final Vec3I[] CACHE = new Vec3I[512];
    private static final int SHIFT = 3;
    private static final int HALF_WIDTH = 4;

    /**
     * Caches or creates a new immutable vector. The vector will be cached if all the vectors components satisfy the
     * constraint {@code -HALF_WIDTH <= x < HALF_WIDTH}.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return a new immutable vector, or a cached instance if possible
     */
    static @NotNull Vec3I cached(int x, int y, int z) {
        if (inRange(x) && inRange(y) && inRange(z)) {
            int index = ((x + HALF_WIDTH) << (SHIFT << 1)) | ((y + HALF_WIDTH) << SHIFT) | (z + HALF_WIDTH);
            Vec3I vector = CACHE[index];
            if (vector == null) {
                return CACHE[index] = new Vec3I.Immutable(x, y, z);
            }

            return vector;
        }

        return new Vec3I.Immutable(x, y, z);
    }

    private static boolean inRange(int value) {
        return value >= -HALF_WIDTH && value < HALF_WIDTH;
    }
}
