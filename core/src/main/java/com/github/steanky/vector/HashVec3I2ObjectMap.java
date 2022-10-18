package com.github.steanky.vector;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

/**
 * Implementation of {@link Vec3I2ObjectMap} based on an internal {@link Long2ObjectOpenHashMap}.
 *
 * @param <T> the type of object held in the map
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

    /**
     * Creates a new {@link HashVec3I2ObjectMap} with the given origin and bounds. All operations are performed relative
     * to the origin. Combining with width, height, and depth gives a rectangular prism bounding the set of all
     * potentially unique values. Attempting to perform an operation that exceeds this space will "wrap around" and
     * access an area of the space corresponding to the modulus of the actual width of the exceeding dimension.
     * Therefore, there are no coordinates which are strictly outside the scope of this map, although the actual number
     * of unique values possible is limited to the product of the actual widths.
     * <p>
     * The "actual width" of an axis is not necessarily the value given to the constructor. Rather, it is the highest
     * power of two that is strictly greater than the given width. For example, a given width of 1 will yield an actual
     * width of 2, and a given width of 4 will yield an actual width of 8.
     * <p>
     * The sum of the base-2 logarithms of the actual widths cannot exceed {@link Long#SIZE}, or an
     * {@link IllegalArgumentException} will be thrown by this constructor.
     * <p>
     * Negative widths are not allowed and will result in an {@link IllegalArgumentException}.
     *
     * @param x the x-origin
     * @param y the y-origin
     * @param z the z-origin
     * @param width the x-width
     * @param height the y-width
     * @param depth the z-width
     * @param initialCapacity the initial capacity of the underlying map
     * @param loadFactor the load factor of the underlying map
     */
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

    /**
     * Convenience overload that uses the default initial size {@link Hash#DEFAULT_INITIAL_SIZE} and default load factor
     * {@link Hash#DEFAULT_LOAD_FACTOR} for the internal {@link Long2ObjectOpenHashMap}.
     *
     * @param x the x-origin
     * @param y the y-origin
     * @param z the z-origin
     * @param width the x-width
     * @param height the y-width
     * @param depth the z-width
     */
    public HashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth) {
        this(x, y, z, width, height, depth, Hash.DEFAULT_INITIAL_SIZE, Hash.DEFAULT_LOAD_FACTOR);
    }

    /**
     * Convenience overload that uses the default load factor {@link Hash#DEFAULT_LOAD_FACTOR} for the internal
     * {@link Long2ObjectOpenHashMap}.
     *
     * @param x the x-origin
     * @param y the y-origin
     * @param z the z-origin
     * @param width the x-width
     * @param height the y-width
     * @param depth the z-width
     * @param initialCapacity the initial capacity of the underlying map
     */
    public HashVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth, int initialCapacity) {
        this(x, y, z, width, height, depth, initialCapacity, Hash.DEFAULT_LOAD_FACTOR);
    }

    /**
     * Convenience overload that uses the default initial size {@link Hash#DEFAULT_INITIAL_SIZE} for the internal
     * {@link Long2ObjectOpenHashMap}.
     *
     * @param x the x-origin
     * @param y the y-origin
     * @param z the z-origin
     * @param width the x-width
     * @param height the y-width
     * @param depth the z-width
     * @param loadFactor the load factor of the underlying map
     */
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
