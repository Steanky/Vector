package com.github.steanky.vector;

/**
 * A function that accepts a vector as an integer triplet.
 *
 * @param <T> the type of value returned by this function
 */
@FunctionalInterface
public interface Vec3IFunction<T> {
    /**
     * Calls this function with the provided value.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     *
     * @return the function's value
     */
    T apply(int x, int y, int z);
}
