package com.github.steanky.vector;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * A {@link Map} that stores values under integer-vector keys. This is more efficient than using a map of {@link Vec3I}
 * keys to some value, since there is no need to call {@link Object#equals(Object)} or {@link Object#hashCode()} on the
 * key vectors. Additionally, operations like {@link Vec3I2ObjectMap#get(int, int, int)} do not require the creation of
 * a potentially redundant object.
 * <p>
 * Null keys are not supported. Null values are supported, and can be distinguished from the absence of a value using
 * {@link Vec3I2ObjectMap#containsKey(int, int, int)}.
 * <p>
 * Implementations may, at their discretion, disallow any number of specific coordinates for use as keys, for example as
 * part of a size limitation scheme or due to restrictions inherent in their design.
 *
 * @param <T> the type of value stored in the map
 */
public interface Vec3I2ObjectMap<T> extends Map<Vec3I, T> {
    /**
     * Gets the value at a specific coordinate.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return null if the underlying value is null, or no value exists at the specified location; else the value that
     * exists at the coordinate
     */
    T get(int x, int y, int z);

    /**
     * Puts a value at a specific coordinate and returns the old value.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @param value the value to put in the map
     * @return null if the underlying value was null, or no value existed at the specified location; else the value
     * previously located at the coordinate before it was replaced
     */
    T put(int x, int y, int z, T value);

    /**
     * Removes a value from the map.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return null if the underlying value was null, or no value existed at the specified location; else the value
     * previously located at the coordinate before it was removed
     */
    T remove(int x, int y, int z);

    boolean remove(int x, int y, int z, Object value);

    /**
     * Tests if the map contains a value at a coordinate.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return true if the map contains a value here; false otherwise
     */
    boolean containsKey(int x, int y, int z);

    /**
     * Returns the value currently at the coordinate if it exists; else computes a new value, enters it into the map,
     * and returns it.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @param mappingFunction the mapping function used to create a new value if necessary
     * @return the value currently at the coordinate; else the value created by the mapping function after it is added
     * to the map
     */
    T computeIfAbsent(int x, int y, int z, @NotNull Vec3IFunction<? extends T> mappingFunction);

    T computeIfPresent(int x, int y, int z, @NotNull Vec3IBiFunction<? super T, ? extends T> remappingFunction);

    T compute(int x, int y, int z, @NotNull Vec3IBiFunction<? super T, ? extends T> remappingFunction);

    T putIfAbsent(int x, int y, int z, T value);

    void putAll(@NotNull Vec3I2ObjectMap<? extends T> map);

    T replace(int x, int y, int z, T value);

    boolean replace(int x, int y, int z, T oldValue, T newValue);

    void replaceAll(@NotNull Vec3IBiFunction<? super T, ? extends T> function);

    T getOrDefault(int x, int y, int z, T def);

    T merge(int x, int y, int z, T value, @NotNull BiFunction<? super T, ? super T, ? extends T> mergeFunction);

    void forEach(@NotNull Vec3IBiConsumer<? super T> consumer);
}
