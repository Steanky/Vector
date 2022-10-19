package com.github.steanky.vector;

/**
 * A consumer of integer triplets.
 */
@FunctionalInterface
public interface Vec3IConsumer {
    /**
     * Accepts the given coordinate.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    void accept(int x, int y, int z);
}
