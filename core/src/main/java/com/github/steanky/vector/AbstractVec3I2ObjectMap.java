package com.github.steanky.vector;

import java.util.AbstractMap;

/**
 * Abstract implementation of {@link Vec3I2ObjectMap} that provides some basic overrides.
 *
 * @param <T> the type of object stored in the map
 */
public abstract class AbstractVec3I2ObjectMap<T> extends AbstractMap<Vec3I, T> implements Vec3I2ObjectMap<T> {
    @Override
    public final boolean containsKey(Object key) {
        if (!(key instanceof Vec3I vec)) {
            return false;
        }

        return containsKey(vec.getX(), vec.getY(), vec.getZ());
    }

    @Override
    public final T get(Object key) {
        if(!(key instanceof Vec3I vec)) {
            return null;
        }

        return get(vec.getX(), vec.getY(), vec.getZ());
    }

    @Override
    public final T put(Vec3I key, T value) {
        return put(key.getX(), key.getY(), key.getZ(), value);
    }

    @Override
    public final T remove(Object key) {
        if (!(key instanceof Vec3I vec)) {
            return null;
        }

        return remove(vec.getX(), vec.getY(), vec.getZ());
    }
}
