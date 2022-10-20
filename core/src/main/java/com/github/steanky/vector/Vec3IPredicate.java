package com.github.steanky.vector;

/**
 * A predicate which accepts an integer triplet.
 */
public interface Vec3IPredicate {
    /**
     * Tests this predicate.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return true if the predicat succeeds; false otherwise
     */
    boolean test(int x, int y, int z);
}
