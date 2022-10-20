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
     *
     * @return null if the underlying value is null, or no value exists at the specified location; else the value that
     * exists at the coordinate
     */
    T get(int x, int y, int z);

    /**
     * Puts a value at a specific coordinate and returns the old value.
     *
     * @param x     the x-coordinate
     * @param y     the y-coordinate
     * @param z     the z-coordinate
     * @param value the value to put in the map
     *
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
     *
     * @return null if the underlying value was null, or no value existed at the specified location; else the value
     * previously located at the coordinate before it was removed
     */
    T remove(int x, int y, int z);

    /**
     * Removes a matching value from the map.
     *
     * @param x     the x-coordinate
     * @param y     the y-coordinate
     * @param z     the z-coordinate
     * @param value the value to match
     *
     * @return true if the value was removed, false otherwise
     */
    boolean remove(int x, int y, int z, Object value);

    /**
     * Tests if the map contains a value at a coordinate.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     *
     * @return true if the map contains a value here; false otherwise
     */
    boolean containsKey(int x, int y, int z);

    /**
     * Returns the value currently at the coordinate if it exists; else computes a new value, enters it into the map,
     * and returns it.
     *
     * @param x               the x-coordinate
     * @param y               the y-coordinate
     * @param z               the z-coordinate
     * @param mappingFunction the mapping function used to create a new value if necessary
     *
     * @return the value currently at the coordinate; else the value created by the mapping function after it is added
     * to the map
     */
    T computeIfAbsent(int x, int y, int z, @NotNull Vec3IFunction<? extends T> mappingFunction);

    /**
     * Remaps and returns the value at the coordinate if it exists.
     *
     * @param x                 the x-coordinate
     * @param y                 the y-coordinate
     * @param z                 the z-coordinate
     * @param remappingFunction the remapping function
     *
     * @return the computed value if it previously existed; otherwise null
     */
    T computeIfPresent(int x, int y, int z, @NotNull Vec3IBiFunction<? super T, ? extends T> remappingFunction);

    /**
     * Attempts to compute a value. If the remapping function returns null, the value is removed; otherwise, it is
     * entered into the map.
     *
     * @param x                 the x-coordinate
     * @param y                 the y-coordinate
     * @param z                 the z-coordinate
     * @param remappingFunction the remapping function
     *
     * @return the result of the remapping function
     */
    T compute(int x, int y, int z, @NotNull Vec3IBiFunction<? super T, ? extends T> remappingFunction);

    /**
     * Puts a new value into the map if it is absent.
     *
     * @param x     the x-coordinate
     * @param y     the y-coordinate
     * @param z     the z-coordinate
     * @param value the value to put
     *
     * @return the old value if it exists, or the new value if it was just entered
     */
    T putIfAbsent(int x, int y, int z, T value);

    /**
     * Adds all values from the given map into this one.
     *
     * @param map the map from which to add values
     */
    void putAll(@NotNull Vec3I2ObjectMap<? extends T> map);

    /**
     * Replaces the value at the coordinate, if it exists. Otherwise the map is unchanged.
     *
     * @param x     the x-coordinate
     * @param y     the y-coordinate
     * @param z     the z-coordinate
     * @param value the value to replace with
     *
     * @return the replaced value, or null if there was no value
     */
    T replace(int x, int y, int z, T value);

    /**
     * Replaces the value at the coordinate, if it exists and is equal to {@code oldValue}. Otherwise the map is
     * unchanged.
     *
     * @param x        the x-coordinate
     * @param y        the y-coordinate
     * @param z        the z-coordinate
     * @param oldValue the old value to replace
     * @param newValue the value to replace {@code oldValue} with
     *
     * @return the replaced value, or null if there was no value (or oldValue is not equal to the value previously in
     * the map)
     */
    boolean replace(int x, int y, int z, T oldValue, T newValue);

    /**
     * Replaces all mappings according to the function.
     *
     * @param function the function used to construct new mappings
     */
    void replaceAll(@NotNull Vec3IBiFunction<? super T, ? extends T> function);

    /**
     * Gets the value at the coordinate if it is present; otherwise returns the given default value.
     *
     * @param x   the x-coordinate
     * @param y   the y-coordinate
     * @param z   the z-coordinate
     * @param def the default value
     *
     * @return the value present at the coordinate if it exists; else the default value
     */
    T getOrDefault(int x, int y, int z, T def);

    /**
     * If the given coordinate does not already have a value, associates it with the value. Otherwise, the merging
     * function is called with the old value and the new value.
     *
     * @param x             the x-coordinate
     * @param y             the y-coordinate
     * @param z             the z-coordinate
     * @param value         the new value
     * @param mergeFunction the function used to merge the old value with the new value
     *
     * @return the value just entered into the map if none previously existed; else the value computed by the merge
     * function
     */
    T merge(int x, int y, int z, T value, @NotNull BiFunction<? super T, ? super T, ? extends T> mergeFunction);

    /**
     * Calls the given consumer with each coordinate and value object in this map.
     *
     * @param consumer the consumer to call
     */
    void forEach(@NotNull Vec3IBiConsumer<? super T> consumer);
}
