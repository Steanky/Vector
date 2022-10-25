package com.github.steanky.vector;

/**
 * A function that accepts an integer triplet and some other object.
 *
 * @param <T> the object type to be accepted
 * @param <R> the object type to be returned
 */
@FunctionalInterface
public interface Vec3IObjectBiFunction<T, R> {
    /**
     * Calls this function with the provided coordinate and object.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @param t the object
     *
     * @return the result of this function
     */
    R apply(int x, int y, int z, T t);
}
