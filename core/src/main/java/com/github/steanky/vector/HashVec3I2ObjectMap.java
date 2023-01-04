package com.github.steanky.vector;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Implementation of {@link Vec3I2ObjectMap} based on an internal {@link Long2ObjectOpenHashMap}. Null keys and values
 * are not supported.
 *
 * @param <T> the type of object held in the map
 */
public class HashVec3I2ObjectMap<T> extends BitPackingVec3I2ObjectMap<T> {
    /**
     * Creates a new {@link HashVec3I2ObjectMap} with the given origin and bounds, backed by an underlying
     * {@link Long2ObjectOpenHashMap} See {@link BitPackingVec3I2ObjectMap} for more details.
     *
     * @param x               the x-origin
     * @param y               the y-origin
     * @param z               the z-origin
     * @param width           the x-width
     * @param height          the y-width
     * @param depth           the z-width
     * @param initialCapacity the initial capacity of the underlying map
     * @param loadFactor      the load factor of the underlying map
     */
    public HashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth, int initialCapacity,
            float loadFactor) {
        super(x, y, z, width, height, depth, new Long2ObjectOpenHashMap<>(initialCapacity, loadFactor));
    }

    /**
     * Convenience overload that uses the default initial size {@link Hash#DEFAULT_INITIAL_SIZE} and default load factor
     * {@link Hash#DEFAULT_LOAD_FACTOR} for the internal {@link Long2ObjectOpenHashMap}.
     *
     * @param x      the x-origin
     * @param y      the y-origin
     * @param z      the z-origin
     * @param width  the x-width
     * @param height the y-width
     * @param depth  the z-width
     */
    public HashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth) {
        this(x, y, z, width, height, depth, Hash.DEFAULT_INITIAL_SIZE, Hash.DEFAULT_LOAD_FACTOR);
    }

    /**
     * Convenience overload that uses the default load factor {@link Hash#DEFAULT_LOAD_FACTOR} for the internal
     * {@link Long2ObjectOpenHashMap}.
     *
     * @param x               the x-origin
     * @param y               the y-origin
     * @param z               the z-origin
     * @param width           the x-width
     * @param height          the y-width
     * @param depth           the z-width
     * @param initialCapacity the initial capacity of the underlying map
     */
    public HashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth, int initialCapacity) {
        this(x, y, z, width, height, depth, initialCapacity, Hash.DEFAULT_LOAD_FACTOR);
    }

    /**
     * Convenience overload that uses the default initial size {@link Hash#DEFAULT_INITIAL_SIZE} for the internal
     * {@link Long2ObjectOpenHashMap}.
     *
     * @param x          the x-origin
     * @param y          the y-origin
     * @param z          the z-origin
     * @param width      the x-width
     * @param height     the y-width
     * @param depth      the z-width
     * @param loadFactor the load factor of the underlying map
     */
    public HashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth, float loadFactor) {
        this(x, y, z, width, height, depth, Hash.DEFAULT_INITIAL_SIZE, loadFactor);
    }

    /**
     * Convenience overload for {@link HashVec3I2ObjectMap#HashVec3I2ObjectMap(int, int, int, int, int, int, int, float)}
     * that uses the origin and lengths from the provided bounds, the default initial size
     * {@link Hash#DEFAULT_INITIAL_SIZE}, and the default load factor {@link Hash#DEFAULT_LOAD_FACTOR}.
     *
     * @param bounds the bounds which provides the origin and lengths
     */
    public HashVec3I2ObjectMap(@NotNull Bounds3I bounds) {
        this(bounds.originX(), bounds.originY(), bounds.originZ(), bounds.lengthX(), bounds.lengthY(), bounds.lengthZ(),
                Hash.DEFAULT_INITIAL_SIZE, Hash.DEFAULT_LOAD_FACTOR);
    }

    /**
     * Convenience overload for {@link HashVec3I2ObjectMap#HashVec3I2ObjectMap(int, int, int, int, int, int, int, float)}
     * that uses the origin and lengths from the provided bounds, the given initial size, and the default load factor
     * {@link Hash#DEFAULT_LOAD_FACTOR}.
     *
     * @param bounds the bounds which provides the origin and lengths
     * @param initialCapacity the initial capacity for the underlying map
     */
    public HashVec3I2ObjectMap(@NotNull Bounds3I bounds, int initialCapacity) {
        this(bounds.originX(), bounds.originY(), bounds.originZ(), bounds.lengthX(), bounds.lengthY(), bounds.lengthZ(),
                initialCapacity, Hash.DEFAULT_LOAD_FACTOR);
    }

    /**
     * Convenience overload for {@link HashVec3I2ObjectMap#HashVec3I2ObjectMap(int, int, int, int, int, int, int, float)}
     * that uses the origin and lengths from the provided bounds, the default initial size
     * {@link Hash#DEFAULT_INITIAL_SIZE}, and the given load factor.
     *
     * @param bounds the bounds which provides the origin and lengths
     * @param loadFactor the load factor for the underlying map
     */
    public HashVec3I2ObjectMap(@NotNull Bounds3I bounds, float loadFactor) {
        this(bounds.originX(), bounds.originY(), bounds.originZ(), bounds.lengthX(), bounds.lengthY(), bounds.lengthZ(),
                Hash.DEFAULT_INITIAL_SIZE, loadFactor);
    }

    /**
     * Convenience overload for {@link HashVec3I2ObjectMap#HashVec3I2ObjectMap(int, int, int, int, int, int, int, float)}
     * that uses the origin and lengths from the provided bounds, the given initial size, and the given load factor.
     *
     * @param bounds the bounds which provides the origin and lengths
     * @param initialCapacity the initial capacity for the underlying map
     * @param loadFactor the load factor for the underlying map
     */
    public HashVec3I2ObjectMap(@NotNull Bounds3I bounds, int initialCapacity, float loadFactor) {
        this(bounds.originX(), bounds.originY(), bounds.originZ(), bounds.lengthX(), bounds.lengthY(), bounds.lengthZ(),
                initialCapacity, loadFactor);
    }

    @Override
    public T computeIfAbsent(int x, int y, int z, @NotNull Vec3IFunction<? extends T> mappingFunction) {
        Objects.requireNonNull(mappingFunction);

        long key = pack(x, y, z);
        T v = underlyingMap.get(key);
        if (v != null) {
            return v;
        }

        T functionResult = mappingFunction.apply(x, y, z);
        if (functionResult == null) {
            return null;
        }

        underlyingMap.put(key, functionResult);
        return functionResult;
    }

    @Override
    public T computeIfPresent(int x, int y, int z, @NotNull Vec3IObjectBiFunction<? super T, ? extends T> remappingFunction) {
        Objects.requireNonNull(remappingFunction);

        long key = pack(x, y, z);
        T oldValue = underlyingMap.get(key);
        if (oldValue == null) {
            return null;
        }

        T newValue = remappingFunction.apply(x, y, z, oldValue);
        if (newValue == null) {
            underlyingMap.remove(key);
            return null;
        }

        underlyingMap.put(key, newValue);
        return newValue;
    }

    @Override
    public T compute(int x, int y, int z, @NotNull Vec3IObjectBiFunction<? super T, ? extends T> remappingFunction) {
        Objects.requireNonNull(remappingFunction);

        long key = pack(x, y, z);
        T oldValue = underlyingMap.get(key);
        boolean contained = oldValue != null;
        T newValue = remappingFunction.apply(x, y, z, contained ? oldValue : null);
        if (newValue == null) {
            if (contained) {
                underlyingMap.remove(key);
            }

            return null;
        }

        underlyingMap.put(key, newValue);
        return newValue;
    }

    /**
     * Calls {@link Long2ObjectOpenHashMap#trim()} on the underlying map.
     * @return true if there was enough memory to trim the map
     */
    public boolean trim() {
        return ((Long2ObjectOpenHashMap<?>)underlyingMap).trim();
    }

    /**
     * Calls {@link Long2ObjectOpenHashMap#trim(int)} on the underlying map.
     * @param n the threshold for trimming
     * @return true if there was enough memory to trim the map
     */
    public boolean trim(int n) {
        return ((Long2ObjectOpenHashMap<?>)underlyingMap).trim(n);
    }
}