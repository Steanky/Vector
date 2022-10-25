package com.github.steanky.vector;

/**
 * A predicate that accepts two integer triplets.
 */
@FunctionalInterface
public interface Vec3IBiPredicate {
    /**
     * Tests this predicate.
     *
     * @param x1 the first x-coordinate
     * @param y1 the first y-coordinate
     * @param z1 the first z-coordinate
     * @param x2 the second x-coordinate
     * @param y2 the second y-coordinate
     * @param z2 the second z-coordinate
     * @return true if this predicate succeeded; false otherwise
     */
    boolean test(int x1, int y1, int z1, int x2, int y2, int z2);
}
