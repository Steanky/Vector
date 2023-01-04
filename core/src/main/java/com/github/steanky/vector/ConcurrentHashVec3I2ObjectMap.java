package com.github.steanky.vector;

import it.unimi.dsi.fastutil.Hash;
import org.jetbrains.annotations.NotNull;
import space.vectrix.flare.fastutil.Long2ObjectSyncMap;

/**
 * A thread-safe implementation of {@link Vec3I2ObjectMap} backed by a {@link Long2ObjectSyncMap}. Otherwise, the
 * performance and implementation characteristics are the same as {@link HashVec3I2ObjectMap}.
 * @param <T> the type of object stored in this map
 */
public class ConcurrentHashVec3I2ObjectMap<T> extends BitPackingVec3I2ObjectMap<T>  {
    /**
     * Creates a new {@link ConcurrentHashVec3I2ObjectMap} with the given origin and bounds, backed by an underlying
     * {@link Long2ObjectSyncMap} See {@link BitPackingVec3I2ObjectMap} for more details.
     *
     * @param x               the x-origin
     * @param y               the y-origin
     * @param z               the z-origin
     * @param width           the x-width
     * @param height          the y-width
     * @param depth           the z-width
     * @param initialCapacity the initial capacity of the underlying map
     */
    public ConcurrentHashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth, int initialCapacity) {
        super(x, y, z, width, height, depth, Long2ObjectSyncMap.hashmap(initialCapacity));
    }

    /**
     * Convenience overload that uses the default initial size {@link Hash#DEFAULT_INITIAL_SIZE} for the internal
     * {@link Long2ObjectSyncMap}.
     *
     * @param x      the x-origin
     * @param y      the y-origin
     * @param z      the z-origin
     * @param width  the x-width
     * @param height the y-width
     * @param depth  the z-width
     */
    public ConcurrentHashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth) {
        this(x, y, z, width, height, depth, Hash.DEFAULT_INITIAL_SIZE);
    }

    /**
     * Convenience overload for
     * {@link ConcurrentHashVec3I2ObjectMap#ConcurrentHashVec3I2ObjectMap(int, int, int, int, int, int, int)}
     * that uses the origin and lengths from the provided bounds, and the default initial size
     * {@link Hash#DEFAULT_INITIAL_SIZE}.
     *
     * @param bounds the bounds which provides the origin and lengths
     */
    public ConcurrentHashVec3I2ObjectMap(@NotNull Bounds3I bounds) {
        this(bounds.originX(), bounds.originY(), bounds.originZ(), bounds.lengthX(), bounds.lengthY(), bounds.lengthZ(),
                Hash.DEFAULT_INITIAL_SIZE);
    }

    /**
     * Convenience overload for
     * {@link ConcurrentHashVec3I2ObjectMap#ConcurrentHashVec3I2ObjectMap(int, int, int, int, int, int, int)} that uses
     * the origin and lengths from the provided bounds, and the given initial size.
     *
     * @param bounds the bounds which provides the origin and lengths
     * @param initialCapacity the initial capacity for the underlying map
     */
    public ConcurrentHashVec3I2ObjectMap(@NotNull Bounds3I bounds, int initialCapacity) {
        this(bounds.originX(), bounds.originY(), bounds.originZ(), bounds.lengthX(), bounds.lengthY(), bounds.lengthZ(),
                initialCapacity);
    }
}
