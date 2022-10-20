package com.github.steanky.vector;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Abstract implementation of {@link Vec3I2ObjectMap} that provides some basic overrides delegating methods from
 * {@link Map} to {@link Vec3I2ObjectMap}.
 *
 * @param <T> the type of object stored in the map
 */
public abstract class AbstractVec3I2ObjectMap<T> extends AbstractMap<Vec3I, T> implements Vec3I2ObjectMap<T> {
    @Override
    public final boolean containsKey(Object key) {
        if (!(key instanceof Vec3I vec)) {
            return false;
        }

        return containsKey(vec.x(), vec.y(), vec.z());
    }

    @Override
    public final T get(Object key) {
        if (!(key instanceof Vec3I vec)) {
            return null;
        }

        return get(vec.x(), vec.y(), vec.z());
    }

    @Override
    public final T put(@NotNull Vec3I key, T value) {
        return put(key.x(), key.y(), key.z(), value);
    }

    @Override
    public final T remove(Object key) {
        if (!(key instanceof Vec3I vec)) {
            return null;
        }

        return remove(vec.x(), vec.y(), vec.z());
    }

    @Override
    public final T getOrDefault(Object key, T defaultValue) {
        if (!(key instanceof Vec3I vec)) {
            return defaultValue;
        }

        return getOrDefault(vec.x(), vec.y(), vec.z(), defaultValue);
    }

    @Override
    public final void forEach(@NotNull BiConsumer<? super Vec3I, ? super T> action) {
        Objects.requireNonNull(action);
        forEach((x, y, z, t) -> action.accept(Vec3I.immutable(x, y, z), t));
    }

    @Override
    public final void replaceAll(@NotNull BiFunction<? super Vec3I, ? super T, ? extends T> function) {
        Objects.requireNonNull(function);
        replaceAll((x, y, z, t) -> function.apply(Vec3I.immutable(x, y, z), t));
    }

    @Override
    public final T putIfAbsent(@NotNull Vec3I key, T value) {
        return putIfAbsent(key.x(), key.y(), key.z(), value);
    }

    @Override
    public final boolean remove(Object key, Object value) {
        if (!(key instanceof Vec3I vec)) {
            return false;
        }

        return remove(vec.x(), vec.y(), vec.z(), value);
    }

    @Override
    public final boolean replace(@NotNull Vec3I key, T oldValue, T newValue) {
        return replace(key.x(), key.y(), key.z(), oldValue, newValue);
    }

    @Override
    public final T replace(@NotNull Vec3I key, T value) {
        return replace(key.x(), key.y(), key.z(), value);
    }

    @Override
    public final T computeIfAbsent(@NotNull Vec3I key, @NotNull Function<? super Vec3I, ? extends T> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        return computeIfAbsent(key.x(), key.y(), key.z(), (x, y, z) -> mappingFunction.apply(Vec3I.immutable(x, y, z)));
    }

    @Override
    public final T computeIfPresent(@NotNull Vec3I key, @NotNull BiFunction<? super Vec3I, ? super T, ? extends T> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        return computeIfPresent(key.x(), key.y(), key.z(),
                (x, y, z, t) -> remappingFunction.apply(Vec3I.immutable(x, y, z), t));
    }

    @Override
    public final T compute(@NotNull Vec3I key, @NotNull BiFunction<? super Vec3I, ? super T, ? extends T> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        return compute(key.x(), key.y(), key.z(), (x, y, z, t) -> remappingFunction.apply(Vec3I.immutable(x, y, z), t));
    }

    @Override
    public final T merge(@NotNull Vec3I key, @NotNull T value, @NotNull BiFunction<? super T, ? super T, ? extends T> remappingFunction) {
        return merge(key.x(), key.y(), key.z(), value, remappingFunction);
    }
}