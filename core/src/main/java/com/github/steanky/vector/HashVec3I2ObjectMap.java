package com.github.steanky.vector;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

/**
 * Implementation of {@link Vec3I2ObjectMap} based on an internal {@link Long2ObjectOpenHashMap}.
 * @param <T>
 */
public class HashVec3I2ObjectMap<T> implements Vec3I2ObjectMap<T> {
    private final Long2ObjectMap<T> underlyingMap;

    private final int x;
    private final int y;
    private final int z;

    private final int maskX;
    private final int maskY;
    private final int maskZ;

    private final int bitHeight;
    private final int bitDepth;

    public HashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth, int initialCapacity,
            float loadFactor) {
        this.underlyingMap = new Long2ObjectOpenHashMap<>(initialCapacity, loadFactor);
        if (width <= 0 || height <= 0 || depth <= 0) {
            throw new IllegalArgumentException("Side lengths cannot be negative or 0");
        }

        this.x = x;
        this.y = y;
        this.z = z;

        int widthBit = Integer.highestOneBit(width);
        int heightBit = Integer.highestOneBit(height);
        int depthBit = Integer.highestOneBit(depth);

        int bitWidth = bitSize(widthBit);
        int bitHeight = bitSize(heightBit);
        int bitDepth = bitSize(depthBit);

        if ((bitWidth + bitHeight + bitDepth) > Long.SIZE) {
            throw new IllegalArgumentException("Cannot create a BasicVec3I2ObjectMap with more than 2^64 possible " +
                    "values");
        }

        this.maskX = (widthBit << 1) - 1;
        this.maskY = (heightBit << 1) - 1;
        this.maskZ = (depthBit << 1) - 1;

        this.bitHeight = bitHeight;
        this.bitDepth = bitDepth;
    }

    public HashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth) {
        this(x, y, z, width, height, depth, Hash.DEFAULT_INITIAL_SIZE, Hash.DEFAULT_LOAD_FACTOR);
    }

    public HashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth, int initialCapacity) {
        this(x, y, z, width, height, depth, initialCapacity, Hash.DEFAULT_LOAD_FACTOR);
    }

    public HashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth, float loadFactor) {
        this(x, y, z, width, height, depth, Hash.DEFAULT_INITIAL_SIZE, loadFactor);
    }


    private static int bitSize(int highestBit) {
        return Integer.numberOfTrailingZeros(highestBit) + 1;
    }

    private long index(long x, long y, int z) {
        return (((x - this.x) & maskX) << (bitHeight + bitDepth)) |
                (((y - this.y) & maskY) << bitDepth) |
                ((z - this.z) & maskZ);
    }

    @Override
    public T get(int x, int y, int z) {
        return underlyingMap.get(index(x, y, z));
    }

    @Override
    public T put(int x, int y, int z, T value) {
        return underlyingMap.put(index(x, y, z), value);
    }

    @Override
    public T remove(int x, int y, int z) {
        return underlyingMap.remove(index(x, y, z));
    }

    @Override
    public boolean containsKey(int x, int y, int z) {
        return underlyingMap.containsKey(index(x, y, z));
    }

    @Override
    public T computeIfAbsent(int x, int y, int z, @NotNull Vec3IFunction<? extends T> mappingFunction) {
        Objects.requireNonNull(mappingFunction, "mappingFunction");
        return underlyingMap.computeIfAbsent(index(x, y, z), ignored -> mappingFunction.apply(x, y, z));
    }

    @Override
    public void clear() {
        underlyingMap.clear();
    }

    @Override
    public @NotNull Collection<T> values() {
        return underlyingMap.values();
    }
}
