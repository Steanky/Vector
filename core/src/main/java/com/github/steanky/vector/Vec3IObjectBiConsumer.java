package com.github.steanky.vector;

/**
 * A consumer that takes an integer triplet and some object.
 *
 * @param <T> the type of object accepted
 */
@FunctionalInterface
public interface Vec3IObjectBiConsumer<T> {
    /**
     * Accepts some values.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @param t the object
     */
    void accept(int x, int y, int z, T t);
}
