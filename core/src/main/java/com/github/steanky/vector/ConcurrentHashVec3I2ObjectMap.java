package com.github.steanky.vector;

import it.unimi.dsi.fastutil.Hash;
import org.jetbrains.annotations.NotNull;
import space.vectrix.flare.fastutil.Long2ObjectSyncMap;

import java.util.function.BiFunction;

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

    @Override
    public T get(int x, int y, int z) {
        return underlyingMap.get(pack(x, y, z));
    }

    @Override
    public T put(int x, int y, int z, T value) {
        return underlyingMap.put(pack(x, y, z), value);
    }

    @Override
    public T remove(int x, int y, int z) {
        return underlyingMap.remove(pack(x, y, z));
    }

    @Override
    public boolean remove(int x, int y, int z, Object value) {
        return underlyingMap.remove(pack(x, y, z), value);
    }

    @Override
    public boolean containsKey(int x, int y, int z) {
        return underlyingMap.containsKey(pack(x, y, z));
    }

    @Override
    public T computeIfAbsent(int x, int y, int z, @NotNull Vec3IFunction<? extends T> mappingFunction) {
        return underlyingMap.computeIfAbsent(pack(x, y, z), ignored -> mappingFunction.apply(x, y, z));
    }

    @Override
    public T computeIfPresent(int x, int y, int z, @NotNull Vec3IObjectBiFunction<? super T, ? extends T> remappingFunction) {
        return underlyingMap.computeIfPresent(pack(x, y, z), (ignored, t) -> remappingFunction.apply(x, y, z, t));
    }

    @Override
    public T compute(int x, int y, int z, @NotNull Vec3IObjectBiFunction<? super T, ? extends T> remappingFunction) {
        return underlyingMap.compute(pack(x, y, z), (ignored, t) -> remappingFunction.apply(x, y, z, t));
    }

    @Override
    public T putIfAbsent(int x, int y, int z, T value) {
        return underlyingMap.putIfAbsent(pack(x, y, z), value);
    }

    @Override
    public T replace(int x, int y, int z, T value) {
        return underlyingMap.replace(pack(x, y, z), value);
    }

    @Override
    public boolean replace(int x, int y, int z, T oldValue, T newValue) {
        return underlyingMap.replace(pack(x, y, z), oldValue, newValue);
    }

    @Override
    public void replaceAll(@NotNull Vec3IObjectBiFunction<? super T, ? extends T> function) {
        underlyingMap.replaceAll((l, t) -> function.apply(x(l), y(l), z(l), t));
    }

    @Override
    public T getOrDefault(int x, int y, int z, T def) {
        return underlyingMap.getOrDefault(pack(x, y, z), def);
    }

    @Override
    public T merge(int x, int y, int z, T value, @NotNull BiFunction<? super T, ? super T, ? extends T> mergeFunction) {
        return underlyingMap.merge(pack(x, y, z), value, mergeFunction);
    }

    @Override
    public void forEach(@NotNull Vec3IObjectBiConsumer<? super T> consumer) {
        underlyingMap.forEach((l, t) -> consumer.accept(x(l), y(l), z(l), t));
    }
}
