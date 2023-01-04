package com.github.steanky.vector;

import it.unimi.dsi.fastutil.longs.AbstractLong2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

/**
 * A further abstract implementation of {@link AbstractVec3I2ObjectMap} which is based on a bounded rectangular prism of
 * possible unique values, and an internal {@link Long2ObjectMap} which holds them. Null values are disallowed.
 *
 * @param <T> the type of object stored in the underlying map
 * @see ConcurrentHashVec3I2ObjectMap
 * @see HashVec3I2ObjectMap
 */
public abstract class BitPackingVec3I2ObjectMap<T> extends AbstractVec3I2ObjectMap<T> {
    /**
     * The underlying map.
     */
    protected final Long2ObjectMap<T> underlyingMap;

    private final int x;
    private final int y;
    private final int z;

    private final int maskX;
    private final int maskY;
    private final int maskZ;

    private final int bitHeight;
    private final int bitDepth;

    /**
     * Creates a new {@link BitPackingVec3I2ObjectMap} with the given origin and bounds. All operations are performed
     * relative to the origin. Combining with width, height, and depth gives a rectangular prism bounding the set of all
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
     * @param x               the x-origin
     * @param y               the y-origin
     * @param z               the z-origin
     * @param width           the x-width
     * @param height          the y-width
     * @param depth           the z-width
     * @param underlyingMap   the underlying {@link Long2ObjectMap} in which to store data
     */
    protected BitPackingVec3I2ObjectMap(int x, int y, int z, int width, int height, int depth,
            @NotNull Long2ObjectMap<T> underlyingMap) {
        if (width <= 0 || height <= 0 || depth <= 0) {
            throw new IllegalArgumentException("Side lengths cannot be negative or 0");
        }

        this.x = x;
        this.y = y;
        this.z = z;

        int widthBit = Integer.highestOneBit(Math.max(width - 1, 1));
        int heightBit = Integer.highestOneBit(Math.max(height - 1, 1));
        int depthBit = Integer.highestOneBit(Math.max(depth - 1, 1));

        int bitWidth = bitSize(widthBit);
        int bitHeight = bitSize(heightBit);
        int bitDepth = bitSize(depthBit);

        if ((bitWidth + bitHeight + bitDepth) > Long.SIZE) {
            throw new IllegalArgumentException(
                    "Cannot create a BasicVec3I2ObjectMap with more than 2^64 possible values");
        }

        this.maskX = (widthBit << 1) - 1;
        this.maskY = (heightBit << 1) - 1;
        this.maskZ = (depthBit << 1) - 1;

        this.bitHeight = bitHeight;
        this.bitDepth = bitDepth;

        this.underlyingMap = Objects.requireNonNull(underlyingMap);
    }

    private static int bitSize(int highestBit) {
        return Integer.numberOfTrailingZeros(highestBit) + 1;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void putAll(Map<? extends Vec3I, ? extends T> map) {
        if (map instanceof BitPackingVec3I2ObjectMap<?> other) {
            underlyingMap.putAll((Long2ObjectMap<? extends T>) other.underlyingMap);
        }
        else {
            Map.Entry[] entries = map.entrySet().toArray(Entry[]::new);
            for (int i = 0; i < entries.length; i++) {
                Map.Entry old = entries[i];
                Vec3I key = (Vec3I) old.getKey();
                Object value = Objects.requireNonNull(old.getValue());

                entries[i] = Map.entry(pack(key.x(), key.y(), key.z()), value);
            }

            underlyingMap.putAll(Map.ofEntries(entries));
        }
    }

    @Override
    public void putAll(@NotNull Vec3I2ObjectMap<? extends T> map) {
        putAll((Map<? extends Vec3I, ? extends T>) map);
    }

    @NotNull
    @Override
    public Set<Entry<Vec3I, T>> entrySet() {
        return new AbstractSet<>() {
            private final ObjectSet<Long2ObjectMap.Entry<T>> entrySet = underlyingMap.long2ObjectEntrySet();

            @Override
            public Iterator<Entry<Vec3I, T>> iterator() {
                return new Iterator<>() {
                    private final Iterator<Long2ObjectMap.Entry<T>> iterator = entrySet.iterator();

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public Entry<Vec3I, T> next() {
                        Long2ObjectMap.Entry<T> next = iterator.next();

                        long nextKey = next.getLongKey();
                        Vec3I key = unpack(nextKey);
                        return new Entry<>() {
                            @Override
                            public Vec3I getKey() {
                                return key;
                            }

                            @Override
                            public T getValue() {
                                return next.getValue();
                            }

                            @Override
                            public T setValue(T value) {
                                return next.setValue(value);
                            }
                        };
                    }

                    @Override
                    public void remove() {
                        iterator.remove();
                    }
                };
            }

            @Override
            public int size() {
                return underlyingMap.size();
            }

            @Override
            public boolean remove(Object o) {
                if (!(o instanceof Entry<?, ?> entry)) {
                    return false;
                }

                Object keyObject = entry.getKey();
                if (!(keyObject instanceof Vec3I vec)) {
                    return false;
                }

                return entrySet.remove(
                        new AbstractLong2ObjectMap.BasicEntry<>(pack(vec.x(), vec.y(), vec.z()), entry.getValue()));
            }

            @Override
            public void clear() {
                entrySet.clear();
            }
        };
    }

    /**
     * Packs three integers into a long, with respect to the bounds of this map.
     *
     * @param x the x-coordinate of the vector to pack
     * @param y the y-coordinate of the vector to pack
     * @param z the z-coordinate of the vector to pack
     * @return a single packed long
     */
    protected long pack(int x, int y, int z) {
        return ((((long)x - this.x) & maskX) << (bitHeight + bitDepth)) | ((((long)y - this.y) & maskY) << bitDepth) |
                (((long)z - this.z) & maskZ);
    }

    /**
     * Unpacks a long into a {@link Vec3I}. This is the inverse of
     * {@link BitPackingVec3I2ObjectMap#pack(int, int, int)}.
     *
     * @param key the packed long to unpack
     * @return a Vec3I representing the unpacked vector
     */
    protected @NotNull Vec3I unpack(long key) {
        return Vec3I.immutable(x(key), y(key), z(key));
    }

    /**
     * Unpacks only the x-coordinate from the given packed long.
     *
     * @param key the packed long
     * @return the x-coordinate contained in the packed long
     */
    protected int x(long key) {
        return (int) ((key >>> (bitDepth + bitHeight)) & maskX);
    }

    /**
     * Unpacks only the y-coordinate from the given packed long.
     *
     * @param key the packed long
     * @return the y-coordinate contained in the packed long
     */
    protected int y(long key) {
        return (int) ((key >>> bitDepth) & maskY);
    }

    /**
     * Unpacks only the z-coordinate from the given packed long.
     *
     * @param key the packed long
     * @return the z-coordinate contained in the packed long
     */
    protected int z(long key) {
        return (int) (key & maskZ);
    }

    /**
     * The origin x-coordinate of this map, and therefore the origin of its uniquely addressable space.
     * @return the x-coordinate of the map origin
     */
    public int originX() {
        return x;
    }

    /**
     * The origin y-coordinate of this map, and therefore the origin of its uniquely addressable space.
     * @return the y-coordinate of the map origin
     */
    public int originY() {
        return y;
    }

    /**
     * The origin z-coordinate of this map, and therefore the origin of its uniquely addressable space.
     * @return the z-coordinate of the map origin
     */
    public int originZ() {
        return z;
    }

    /**
     * Gets the actual length of this bound's edges along the x-axis. The largest uniquely addressable coordinate along
     * this axis is thus given by {@code originX + width - 1}.
     * @return the actual width along the x-axis
     */
    public long width() {
        return maskX + 1L;
    }

    /**
     * Gets the actual length of this bound's edges along the y-axis. The largest uniquely addressable coordinate along
     * this axis is thus given by {@code originY + height - 1}.
     * @return the actual width along the y-axis
     */
    public long height() {
        return maskY + 1L;
    }

    /**
     * Gets the actual length of this bound's edges along the z-axis. The largest uniquely addressable coordinate along
     * this axis is thus given by {@code originZ + depth - 1}.
     * @return the actual width along the z-axis
     */
    public long depth() {
        return maskZ + 1L;
    }

    /**
     * Computes the maximum possible capacity of this map; i.e. the number of unique elements it may store.
     * @return the addressable size of this map
     */
    public long addressableSize() {
        return width() * height() * depth();
    }

    @Override
    public T get(int x, int y, int z) {
        return underlyingMap.get(pack(x, y, z));
    }

    @Override
    public T put(int x, int y, int z, @NotNull T value) {
        Objects.requireNonNull(value);
        return underlyingMap.put(pack(x, y, z), value);
    }

    @Override
    public T remove(int x, int y, int z) {
        return underlyingMap.remove(pack(x, y, z));
    }

    @Override
    public boolean remove(int x, int y, int z, @NotNull Object value) {
        Objects.requireNonNull(value);
        return underlyingMap.remove(pack(x, y, z), value);
    }

    @Override
    public boolean containsKey(int x, int y, int z) {
        return underlyingMap.containsKey(pack(x, y, z));
    }

    @Override
    public T computeIfAbsent(int x, int y, int z, @NotNull Vec3IFunction<? extends T> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        return underlyingMap.computeIfAbsent(pack(x, y, z), ignored -> Objects.requireNonNull(mappingFunction
                .apply(x, y, z)));
    }

    @Override
    public T computeIfPresent(int x, int y, int z, @NotNull Vec3IObjectBiFunction<? super T, ? extends T> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        return underlyingMap.computeIfPresent(pack(x, y, z), (ignored, t) -> Objects.requireNonNull(remappingFunction
                .apply(x, y, z, t)));
    }

    @Override
    public T compute(int x, int y, int z, @NotNull Vec3IObjectBiFunction<? super T, ? extends T> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        return underlyingMap.compute(pack(x, y, z), (ignored, t) -> Objects.requireNonNull(remappingFunction
                .apply(x, y, z, t)));
    }

    @Override
    public T putIfAbsent(int x, int y, int z, @NotNull T value) {
        Objects.requireNonNull(value);
        return underlyingMap.putIfAbsent(pack(x, y, z), value);
    }

    @Override
    public T replace(int x, int y, int z, @NotNull T value) {
        Objects.requireNonNull(value);
        return underlyingMap.replace(pack(x, y, z), value);
    }

    @Override
    public boolean replace(int x, int y, int z, T oldValue, @NotNull T newValue) {
        Objects.requireNonNull(newValue);
        if (oldValue == null) {
            return false;
        }

        return underlyingMap.replace(pack(x, y, z), oldValue, newValue);
    }

    @Override
    public void replaceAll(@NotNull Vec3IObjectBiFunction<? super T, ? extends T> function) {
        Objects.requireNonNull(function);
        underlyingMap.replaceAll((l, t) -> Objects.requireNonNull(function.apply(x(l), y(l), z(l), t)));
    }

    @Override
    public T getOrDefault(int x, int y, int z, T def) {
        return underlyingMap.getOrDefault(pack(x, y, z), def);
    }

    @Override
    public @NotNull T merge(int x, int y, int z, @NotNull T value,
            @NotNull BiFunction<? super T, ? super T, ? extends T> mergeFunction) {
        Objects.requireNonNull(value);
        return underlyingMap.merge(pack(x, y, z), value, (t, t2) -> Objects.requireNonNull(mergeFunction.apply(t, t2)));
    }

    @Override
    public void forEach(@NotNull Vec3IObjectBiConsumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        underlyingMap.forEach((l, t) -> consumer.accept(x(l), y(l), z(l), t));
    }

    @Override
    public int size() {
        return underlyingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return underlyingMap.isEmpty();
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null) {
            return false;
        }

        return underlyingMap.containsValue(value);
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
