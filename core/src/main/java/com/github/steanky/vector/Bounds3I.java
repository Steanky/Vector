package com.github.steanky.vector;

import org.jetbrains.annotations.NotNull;

/**
 * A three-dimensional bounding box with integer components. Supports immutable and mutable variants.
 */
@SuppressWarnings("DuplicatedCode")
public sealed interface Bounds3I permits Bounds3I.Base {
    /**
     * Override of {@link Bounds3I#enclosingImmutable(Bounds3I...)} to avoid an array allocation.
     *
     * @param bounds the first and only bounds
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3I enclosingImmutable(@NotNull Bounds3I bounds) {
        return bounds.immutable();
    }

    /**
     * Override of {@link Bounds3I#enclosingImmutable(Bounds3I...)} to avoid an array allocation
     *
     * @param first  the first bounds
     * @param second the second bounds
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3I enclosingImmutable(@NotNull Bounds3I first, @NotNull Bounds3I second) {
        return Base.enclosingFromPair(false, first, second);
    }

    /**
     * Calculates the smallest possible bounds that encloses each of given child bounds. Produces an immutable bounds.
     *
     * @param bounds the child bounds array
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3I enclosingImmutable(@NotNull Bounds3I @NotNull ... bounds) {
        return Base.enclosingFromArray(false, bounds);
    }

    /**
     * Override of {@link Bounds3I#enclosingMutable(Bounds3I...)} to avoid an array allocation.
     *
     * @param bounds the first and only bounds
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3I enclosingMutable(@NotNull Bounds3I bounds) {
        return bounds.mutableCopy();
    }

    /**
     * Override of {@link Bounds3I#enclosingMutable(Bounds3I...)} to avoid an array allocation
     *
     * @param first  the first bounds
     * @param second the second bounds
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3I enclosingMutable(@NotNull Bounds3I first, @NotNull Bounds3I second) {
        return Base.enclosingFromPair(true, first, second);
    }

    /**
     * Calculates the smallest possible bounds that encloses each of given child bounds. Produces a mutable bounds.
     *
     * @param bounds the child bounds array
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3I enclosingMutable(@NotNull Bounds3I @NotNull ... bounds) {
        return Base.enclosingFromArray(true, bounds);
    }

    /**
     * Creates a new, immutable bounds from the provided origin and length vectors. None of the lengths may be
     * negative.
     *
     * @param x  the x-coordinate of the origin
     * @param y  the y-coordinate of the origin
     * @param z  the z-coordinate of the origin
     * @param lX the x-length
     * @param lY the y-length
     * @param lZ the z-length
     *
     * @return a new immutable bounds
     * @throws IllegalArgumentException if any of the lengths are negative
     */
    static @NotNull Bounds3I immutable(int x, int y, int z, int lX, int lY, int lZ) {
        Base.validateLengths(lX, lY, lZ);
        return new Immutable(x, y, z, lX, lY, lZ);
    }

    /**
     * Convenience override for {@link Bounds3I#immutable(int, int, int, int, int, int)}.
     *
     * @param origin  the origin vector
     * @param lengths the lengths vector
     *
     * @return a new immutable bounds
     */
    static @NotNull Bounds3I immutable(@NotNull Vec3I origin, @NotNull Vec3I lengths) {
        return immutable(origin.x(), origin.y(), origin.z(), lengths.x(), lengths.y(), lengths.z());
    }

    /**
     * Convenience override for {@link Bounds3I#immutable(int, int, int, int, int, int)}.
     *
     * @param origin the origin vector
     * @param lX     the x-length
     * @param lY     the y-length
     * @param lZ     the z-length
     *
     * @return a new immutable bounds
     */
    static @NotNull Bounds3I immutable(@NotNull Vec3I origin, int lX, int lY, int lZ) {
        return immutable(origin.x(), origin.y(), origin.z(), lX, lY, lZ);
    }

    /**
     * Convenience override for {@link Bounds3I#immutable(int, int, int, int, int, int)}.
     *
     * @param x       the x-coordinate of the origin
     * @param y       the y-coordinate of the origin
     * @param z       the z-coordinate of the origin
     * @param lengths the lengths vector
     *
     * @return a new immutable bounds
     */
    static @NotNull Bounds3I immutable(int x, int y, int z, @NotNull Vec3I lengths) {
        return immutable(x, y, z, lengths.x(), lengths.y(), lengths.z());
    }

    /**
     * Creates a new, mutable bounds from the provided origin and length vectors. None of the lengths may be negative.
     *
     * @param x  the x-coordinate of the origin
     * @param y  the y-coordinate of the origin
     * @param z  the z-coordinate of the origin
     * @param lX the x-length
     * @param lY the y-length
     * @param lZ the z-length
     *
     * @return a new mutable bounds
     * @throws IllegalArgumentException if any of the lengths are negative
     */
    static @NotNull Bounds3I mutable(int x, int y, int z, int lX, int lY, int lZ) {
        Base.validateLengths(lX, lY, lZ);
        return new Mutable(x, y, z, lX, lY, lZ);
    }

    /**
     * Convenience override for {@link Bounds3I#mutable(int, int, int, int, int, int)}.
     *
     * @param origin  the origin vector
     * @param lengths the lengths vector
     *
     * @return a new mutable bounds
     */
    static @NotNull Bounds3I mutable(@NotNull Vec3I origin, @NotNull Vec3I lengths) {
        return mutable(origin.x(), origin.y(), origin.z(), lengths.x(), lengths.y(), lengths.z());
    }

    /**
     * Convenience override for {@link Bounds3I#mutable(int, int, int, int, int, int)}.
     *
     * @param origin the origin vector
     * @param lX     the x-length
     * @param lY     the y-length
     * @param lZ     the z-length
     *
     * @return a new mutable bounds
     */
    static @NotNull Bounds3I mutable(@NotNull Vec3I origin, int lX, int lY, int lZ) {
        return mutable(origin.x(), origin.y(), origin.z(), lX, lY, lZ);
    }

    /**
     * Convenience override for {@link Bounds3I#mutable(int, int, int, int, int, int)}.
     *
     * @param x       the x-coordinate of the origin
     * @param y       the y-coordinate of the origin
     * @param z       the z-coordinate of the origin
     * @param lengths the lengths vector
     *
     * @return a new mutable bounds
     */
    static @NotNull Bounds3I mutable(int x, int y, int z, @NotNull Vec3I lengths) {
        return mutable(x, y, z, lengths.x(), lengths.y(), lengths.z());
    }

    /**
     * The origin x-component. This is the x-coordinate of the corner of the bounding box that is closest to
     * {@link Integer#MIN_VALUE}.
     *
     * @return the x-origin
     */
    int originX();

    /**
     * The origin y-component. This is the y-coordinate of the corner of the bounding box that is closest to
     * {@link Integer#MIN_VALUE}.
     *
     * @return the y-origin
     */
    int originY();

    /**
     * The origin z-component. This is the z-coordinate of the corner of the bounding box that is closest to
     * {@link Integer#MIN_VALUE}.
     *
     * @return the z-origin
     */
    int originZ();

    /**
     * The x-length of the bounds. Will always be larger than or equal to 0.
     *
     * @return the x-length of this bounds
     */
    int lengthX();

    /**
     * The y-length of the bounds. Will always be larger than or equal to 0.
     *
     * @return the y-length of this bounds
     */
    int lengthY();

    /**
     * The z-length of the bounds. Will always be larger than or equal to 0.
     *
     * @return the z-length of this bounds
     */
    int lengthZ();

    /**
     * The maximum x-component. This is the x-coordinate of the corner of the bounding box that is farthest from
     * {@link Integer#MIN_VALUE}.
     *
     * @return the x-max
     */
    default int maxX() {
        return originX() + lengthX();
    }

    /**
     * The maximum y-component. This is the y-coordinate of the corner of the bounding box that is farthest from
     * {@link Integer#MIN_VALUE}.
     *
     * @return the y-max
     */
    default int maxY() {
        return originY() + lengthY();
    }

    /**
     * The maximum z-component. This is the z-coordinate of the corner of the bounding box that is farthest from
     * {@link Integer#MIN_VALUE}.
     *
     * @return the z-max
     */
    default int maxZ() {
        return originZ() + lengthZ();
    }

    /**
     * Creates a new mutable {@link Vec3I} representing the origin of this bounds.
     * @return a new Vec3I representing the origin of this bounds
     */
    default @NotNull Vec3I mutableOrigin() {
        return new Vec3I.Mutable(originX(), originY(), originZ());
    }

    /**
     * Creates an immutable {@link Vec3I} representing the origin of this bounds.
     * @return a Vec3I representing the origin of this bounds
     */
    default @NotNull Vec3I immutableOrigin() {
        return VecCache.cached(originX(), originY(), originZ());
    }

    /**
     * Creates a new mutable {@link Vec3I} representing the lengths of this bounds.
     * @return a new Vec3I representing the origin of this bounds
     */
    default @NotNull Vec3I mutableLengths() {
        return new Vec3I.Mutable(lengthX(), lengthY(), lengthZ());
    }

    /**
     * Creates an immutable {@link Vec3I} representing the lengths of this bounds.
     * @return a Vec3I representing the origin of this bounds
     */
    default @NotNull Vec3I immutableLengths() {
        return VecCache.cached(lengthX(), lengthY(), lengthZ());
    }

    /**
     * The volume of the bounds.
     *
     * @return the product of the lengths of the bounds
     */
    int volume();

    /**
     * The center of this bounds, as a mutable vector.
     * @return the center of this bounds
     */
    @NotNull Vec3D mutableCenter();

    /**
     * The center of this bounds, as an immutable vector.
     * @return the center of this bounds
     */
    @NotNull Vec3D immutableCenter();

    /**
     * Changes the origin vector of the bounds.
     *
     * @param x the new x-origin
     * @param y the new y-origin
     * @param z the new z-origin
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    @NotNull Bounds3I setOrigin(int x, int y, int z);

    /**
     * Convenience override for {@link Bounds3I#setOrigin(int, int, int)}.
     *
     * @param vector the origin vector
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    default @NotNull Bounds3I setOrigin(@NotNull Vec3I vector) {
        return setOrigin(vector.x(), vector.y(), vector.z());
    }

    /**
     * Changes the lengths of the bounds. Lengths must be {@code >= 0}.
     *
     * @param x the new x-length
     * @param y the new y-length
     * @param z the new z-length
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    @NotNull Bounds3I setLengths(int x, int y, int z);

    /**
     * Convenience override for {@link Bounds3I#setLengths(int, int, int)}. Length components must be {@code >= 0}.
     *
     * @param vector the lengths vector
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    default @NotNull Bounds3I setLengths(@NotNull Vec3I vector) {
        return setLengths(vector.x(), vector.y(), vector.z());
    }

    /**
     * Sets all components of the bounds.
     *
     * @param x  the new x-origin
     * @param y  the new y-origin
     * @param z  the new z-origin
     * @param lX the new x-length
     * @param lY the new y-length
     * @param lZ the new z-length
     *
     * @return this bounds, for chaining
     * @throws UnsupportedOperationException if this bounds is immutable
     */
    default @NotNull Bounds3I set(int x, int y, int z, int lX, int lY, int lZ) {
        throw new UnsupportedOperationException();
    }

    /**
     * Convenience override for {@link Bounds3I#set(int, int, int, int, int, int)}.
     *
     * @param origin  origin vector
     * @param lengths lengths vector
     *
     * @return this bounds, for chaining
     */
    default @NotNull Bounds3I set(@NotNull Vec3I origin, @NotNull Vec3I lengths) {
        return set(origin.x(), origin.y(), origin.z(), lengths.x(), lengths.y(), lengths.z());
    }

    /**
     * Convenience override for {@link Bounds3I#set(int, int, int, int, int, int)}.
     *
     * @param origin origin vector
     * @param lX     length x
     * @param lY     length y
     * @param lZ     length z
     *
     * @return this bounds, for chaining
     */
    default @NotNull Bounds3I set(@NotNull Vec3I origin, int lX, int lY, int lZ) {
        return set(origin.x(), origin.y(), origin.z(), lX, lY, lZ);
    }

    /**
     * Convenience override for {@link Bounds3I#set(int, int, int, int, int, int)}.
     *
     * @param x       origin x
     * @param y       origin y
     * @param z       origin z
     * @param lengths lengths vector
     *
     * @return this bounds, for chaining
     */
    default @NotNull Bounds3I set(int x, int y, int z, @NotNull Vec3I lengths) {
        return set(x, y, z, lengths.x(), lengths.y(), lengths.z());
    }

    /**
     * Shifts the position of this bounding box.
     *
     * @param x the x-shift
     * @param y the y-shift
     * @param z the z-shift
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    @NotNull Bounds3I shift(int x, int y, int z);

    /**
     * Convenience override for {@link Bounds3I#shift(int, int, int)}.
     *
     * @param amount the shift vector
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    default @NotNull Bounds3I shift(@NotNull Vec3I amount) {
        return shift(amount.x(), amount.y(), amount.z());
    }

    /**
     * Expands this bounding box in all directions. Giving a negative amount will shrink the bounding box instead.
     * Shrinking the bounding box such that any length would become negative will result in an exception.
     *
     * @param amount the amount by which to expand the bounding box
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     * @throws IllegalArgumentException if this expansion would cause any of the bounding box lengths to shrink below 0
     */
    @NotNull Bounds3I expand(int amount);

    /**
     * Expands this bounding box in a specific direction. For example, expanding by (0, -1, 0) will cause this bounding
     * box to scale by one unit "downwards" if the y-axis is assumed to be the "vertical" axis.
     *
     * @param x the x expansion coordinate
     * @param y the y expansion coordinate
     * @param z the z expansion coordinate
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    @NotNull Bounds3I expandDirectional(int x, int y, int z);

    /**
     * Convenience override for {@link Bounds3I#expandDirectional(int, int, int)}.
     *
     * @param amount the expansion vector
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    default @NotNull Bounds3I expandDirectional(@NotNull Vec3I amount) {
        return expandDirectional(amount.x(), amount.y(), amount.z());
    }

    /**
     * Shrinks this bounding box in a specific direction. The actual length of any one side of the resulting bounds will
     * shrink no smaller than 0.
     *
     * @param x the x shrink coordinate
     * @param y the y shrink coordinate
     * @param z the z shrink coordinate
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    @NotNull Bounds3I shrinkDirectional(int x, int y, int z);

    /**
     * Convenience override for {@link Bounds3I#shrinkDirectional(int, int, int)}.
     *
     * @param amount the shrink vector
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    default @NotNull Bounds3I shrinkDirectional(@NotNull Vec3I amount) {
        return shrinkDirectional(amount.x(), amount.y(), amount.z());
    }

    /**
     * Tests if this bounding box overlaps another. More formally, tests if the set of all vectors enumerated by
     * {@link Bounds3I#forEach(Vec3IConsumer)} has at least one element in common with the equivalent set from the other
     * bounds.
     *
     * @param other the other bounds to test for overlap with
     *
     * @return true if there is an overlap, false otherwise
     */
    boolean overlaps(@NotNull Bounds3I other);

    /**
     * Tests if this bounding box overlaps another. The behavior of this function is undefined when any of the lengths
     * are negative.
     *
     * @param ox the other origin x
     * @param oy the other origin y
     * @param oz the other origin z
     * @param lx the other length x
     * @param ly the other length y
     * @param lz the other length z
     * @return true if there is an overlap, false otherwise
     */
    boolean overlaps(int ox, int oy, int oz, int lx, int ly, int lz);

    /**
     * Tests if this bounding box contains the given vector.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     *
     * @return true if this bounds contains the vector, false otherwise
     */
    boolean contains(int x, int y, int z);

    /**
     * Convenience override for {@link Bounds3I#contains(int, int, int)}.
     *
     * @param vector the vector
     *
     * @return true if this bounds contains the vector, false otherwise
     */
    default boolean contains(@NotNull Vec3I vector) {
        return contains(vector.x(), vector.y(), vector.z());
    }

    /**
     * Enumerates every position encompassed by the bounds.
     *
     * @param consumer the {@link Vec3IConsumer} that accepts each vector
     */
    void forEach(@NotNull Vec3IConsumer consumer);

    /**
     * Enumerates every position encompassed by the bounds, until the given {@link Vec3IPredicate} returns true.
     *
     * @param predicate the predicate that accepts each vector and controls termination
     */
    void forEachUntil(@NotNull Vec3IPredicate predicate);

    /**
     * Creates a new mutable copy of this bounds.
     *
     * @return a new mutable copy of this bounds
     */
    @NotNull Bounds3I mutableCopy();

    /**
     * If this bounds is mutable, returns it. Otherwise, creates a mutable copy and returns it.
     *
     * @return this bounds if already mutable; otherwise a mutable copy
     */
    default @NotNull Bounds3I ensureMutable() {
        if (this instanceof Mutable) {
            return this;
        }

        return mutableCopy();
    }

    /**
     * Creates an immutable copy of this bounds if it is mutable, otherwise, returns this bounds.
     *
     * @return an immutable bounds
     */
    @NotNull Bounds3I immutable();

    /**
     * Creates a read-through immutable bounds. The returned bounds cannot be modified, but changes to the underlying
     * bounds, if mutable, will be visible. If this bounds is already immutable, this bounds is returned.
     *
     * @return an immutable view bounds
     */
    @NotNull Bounds3I immutableView();

    /**
     * Base class of bounds implementations.
     */
    abstract sealed class Base implements Bounds3I permits Mutable, Immutable, View {
        private static final int HASH_PRIME = 31;

        private static void validateLengths(int x, int y, int z) {
            if (x < 0 || y < 0 || z < 0) {
                throw new IllegalArgumentException("Negative lengths are not allowed");
            }
        }

        private static int validateLength(int a, int b) {
            int sum = a + b;
            if (sum < 0) {
                throw new IllegalArgumentException("Negative lengths are not allowed");
            }

            return sum;
        }

        private static Bounds3I enclosingFromPair(boolean mutable, Bounds3I first, Bounds3I second) {
            int minX = Math.min(first.originX(), second.originX());
            int minY = Math.min(first.originY(), second.originY());
            int minZ = Math.min(first.originZ(), second.originZ());

            int maxX = Math.max(first.maxX(), second.maxX());
            int maxY = Math.max(first.maxY(), second.maxY());
            int maxZ = Math.max(first.maxZ(), second.maxZ());

            return mutable ? new Mutable(minX, minY, minZ, maxX - minX, maxY - minY, maxZ - minZ) :
                    new Immutable(minX, minY, minZ, maxX - minX, maxY - minY, maxZ - minZ);
        }

        private static Bounds3I enclosingFromArray(boolean mutable, Bounds3I... bounds) {
            if (bounds.length == 0) {
                throw new IllegalArgumentException("Must provide at least one region");
            }

            Bounds3I first = bounds[0];
            if (bounds.length == 1) {
                return mutable ? first.mutableCopy() : first.immutable();
            }

            int minX = first.originX();
            int minY = first.originY();
            int minZ = first.originZ();

            int maxX = first.maxX();
            int maxY = first.maxY();
            int maxZ = first.maxZ();

            for (int i = 1; i < bounds.length; i++) {
                Bounds3I sample = bounds[i];

                minX = Math.min(minX, sample.originX());
                minY = Math.min(minY, sample.originY());
                minZ = Math.min(minZ, sample.originZ());

                maxX = Math.max(maxX, sample.maxX());
                maxY = Math.max(maxY, sample.maxY());
                maxZ = Math.max(maxZ, sample.maxZ());
            }

            return mutable ? new Mutable(minX, minY, minZ, maxX - minX, maxY - minY, maxZ - minZ) :
                    new Immutable(minX, minY, minZ, maxX - minX, maxY - minY, maxZ - minZ);
        }

        @Override
        public final int hashCode() {
            int result = HASH_PRIME + originX();
            result = HASH_PRIME * result + originY();
            result = HASH_PRIME * result + originZ();
            result = HASH_PRIME * result + lengthX();
            result = HASH_PRIME * result + lengthY();
            return HASH_PRIME * result + lengthZ();
        }

        @Override
        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj == this) {
                return true;
            }

            if (obj instanceof Bounds3I other) {
                return originX() == other.originX() && originY() == other.originY() && originZ() == other.originZ() &&
                        lengthX() == other.lengthX() && lengthY() == other.lengthY() && lengthZ() == other.lengthZ();
            }

            return false;
        }

        @Override
        public int volume() {
            return lengthX() * lengthY() * lengthZ();
        }

        @Override
        public @NotNull Vec3D mutableCenter() {
            return new Vec3D.Mutable(originX() + (lengthX() / 2.0),
                    originY() + (lengthY() / 2.0),
                    originZ() + (lengthZ() / 2.0));
        }

        @Override
        public @NotNull Vec3D immutableCenter() {
            return new Vec3D.Immutable(originX() + (lengthX() / 2.0),
                    originY() + (lengthY() / 2.0),
                    originZ() + (lengthZ() / 2.0));
        }

        @Override
        public @NotNull Bounds3I setOrigin(int x, int y, int z) {
            return originOp(x, y, z);
        }

        @Override
        public @NotNull Bounds3I setLengths(int x, int y, int z) {
            validateLengths(x, y, z);
            return lengthOp(x, y, z);
        }

        @Override
        public @NotNull Bounds3I shift(int x, int y, int z) {
            return originOp(originX() + x, originY() + y, originZ() + z);
        }

        @Override
        public @NotNull Bounds3I expand(int amount) {
            int doubleAmount = amount * 2;

            int lX = validateLength(lengthX(), doubleAmount);
            int lY = validateLength(lengthY(), doubleAmount);
            int lZ = validateLength(lengthZ(), doubleAmount);
            return op(originX() - amount, originY() - amount, originZ() - amount, lX, lY, lZ);
        }

        @Override
        public @NotNull Bounds3I expandDirectional(int x, int y, int z) {
            int nox = originX() + Math.min(x, 0);
            int noy = originY() + Math.min(y, 0);
            int noz = originZ() + Math.min(z, 0);

            int nlx = lengthX() + Math.abs(x);
            int nly = lengthY() + Math.abs(y);
            int nlz = lengthZ() + Math.abs(z);

            return op(nox, noy, noz, nlx, nly, nlz);
        }

        @Override
        public @NotNull Bounds3I shrinkDirectional(int x, int y, int z) {
            int lx = lengthX();
            int ly = lengthY();
            int lz = lengthZ();

            int nlx = Math.max(lx - Math.abs(x), 0);
            int nly = Math.max(ly - Math.abs(y), 0);
            int nlz = Math.max(lz - Math.abs(z), 0);

            int nox = originX() + lx < 0 ? 0 : lx - nlx;
            int noy = originY() + ly < 0 ? 0 : ly - nly;
            int noz = originZ() + lz < 0 ? 0 : lz - nlz;

            return op(nox, noy, noz, nlx, nly, nlz);
        }

        @Override
        public boolean overlaps(@NotNull Bounds3I other) {
            return overlaps(other.originX(), other.originY(), other.originZ(), other.lengthX(), other.lengthY(),
                    other.lengthZ());
        }

        @Override
        public boolean overlaps(int ox, int oy, int oz, int lx, int ly, int lz) {
            return originX() < ox + lx && originY() < oy + ly && originZ() < oz + lz && maxX() > ox &&
                    maxY() > oy && maxZ() > oz;
        }

        @Override
        public boolean contains(int x, int y, int z) {
            if (x >= originX() && y >= originY() && z >= originZ()) {
                int nx = maxX();
                int ny = maxY();
                int nz = maxZ();

                return x < nx && y < ny && z < nz;
            }

            return false;
        }

        @Override
        public void forEach(@NotNull Vec3IConsumer consumer) {
            int nx = maxX();
            int ny = maxY();
            int nz = maxZ();

            for (int i = originX(); i < nx; i++) {
                for (int j = originY(); j < ny; j++) {
                    for (int k = originZ(); k < nz; k++) {
                        consumer.accept(i, j, k);
                    }
                }
            }
        }

        @Override
        public void forEachUntil(@NotNull Vec3IPredicate predicate) {
            int nx = maxX();
            int ny = maxY();
            int nz = maxZ();

            for (int i = originX(); i < nx; i++) {
                for (int j = originY(); j < ny; j++) {
                    for (int k = originZ(); k < nz; k++) {
                        if (predicate.test(i, j, k)) {
                            return;
                        }
                    }
                }
            }
        }

        @Override
        public @NotNull Bounds3I mutableCopy() {
            return new Mutable(originX(), originY(), originZ(), lengthX(), lengthY(), lengthZ());
        }

        /**
         * Sets the origin to a new value, or creates a new bounds with the new origin value.
         *
         * @param x the x-origin
         * @param y the y-origin
         * @param z the z-origin
         *
         * @return this bounds if mutable; otherwise a new bounds
         */
        protected abstract @NotNull Bounds3I originOp(int x, int y, int z);

        /**
         * Sets the lengths to a new value, or creates a new bounds with the new length values.
         *
         * @param x the x-length
         * @param y the y-length
         * @param z the z-length
         *
         * @return this bounds if mutable; otherwise a new bounds
         */
        protected abstract @NotNull Bounds3I lengthOp(int x, int y, int z);

        /**
         * Sets the origin and lengths to new values, or creates a new bounds with the new parameters.
         *
         * @param oX the x-origin
         * @param oY the y-origin
         * @param oZ the z-origin
         * @param lX the x-length
         * @param lY the y-length
         * @param lZ the z-length
         *
         * @return this bounds if mutable; otherwise a new bounds
         */
        protected abstract @NotNull Bounds3I op(int oX, int oY, int oZ, int lX, int lY, int lZ);
    }

    /**
     * The mutable bounds implementation.
     */
    final class Mutable extends Base {
        private int oX;
        private int oY;
        private int oZ;

        private int lX;
        private int lY;
        private int lZ;

        Mutable(int oX, int oY, int oZ, int lX, int lY, int lZ) {
            this.oX = oX;
            this.oY = oY;
            this.oZ = oZ;

            this.lX = lX;
            this.lY = lY;
            this.lZ = lZ;
        }

        @Override
        public int originX() {
            return oX;
        }

        @Override
        public int originY() {
            return oY;
        }

        @Override
        public int originZ() {
            return oZ;
        }

        @Override
        public int lengthX() {
            return lX;
        }

        @Override
        public int lengthY() {
            return lY;
        }

        @Override
        public int lengthZ() {
            return lZ;
        }

        @Override
        public @NotNull Bounds3I set(int x, int y, int z, int lX, int lY, int lZ) {
            Base.validateLengths(lX, lY, lZ);

            this.oX = x;
            this.oY = y;
            this.oZ = z;

            this.lX = lX;
            this.lY = lY;
            this.lZ = lZ;
            return this;
        }

        @Override
        public @NotNull Bounds3I immutable() {
            return new Immutable(oX, oY, oZ, lX, lY, lZ);
        }

        @Override
        public @NotNull Bounds3I immutableView() {
            return new View(this);
        }

        @Override
        protected @NotNull Bounds3I originOp(int x, int y, int z) {
            this.oX = x;
            this.oY = y;
            this.oZ = z;
            return this;
        }

        @Override
        protected @NotNull Bounds3I lengthOp(int x, int y, int z) {
            this.lX = x;
            this.lY = y;
            this.lZ = z;
            return this;
        }

        @Override
        protected @NotNull Bounds3I op(int oX, int oY, int oZ, int lX, int lY, int lZ) {
            this.oX = oX;
            this.oY = oY;
            this.oZ = oZ;

            this.lX = lX;
            this.lY = lY;
            this.lZ = lZ;
            return this;
        }
    }

    /**
     * The immutable bounds implementation.
     */
    final class Immutable extends Base {
        private final int oX;
        private final int oY;
        private final int oZ;

        private final int lX;
        private final int lY;
        private final int lZ;

        Immutable(int oX, int oY, int oZ, int lX, int lY, int lZ) {
            this.oX = oX;
            this.oY = oY;
            this.oZ = oZ;

            this.lX = lX;
            this.lY = lY;
            this.lZ = lZ;
        }

        @Override
        public int originX() {
            return oX;
        }

        @Override
        public int originY() {
            return oY;
        }

        @Override
        public int originZ() {
            return oZ;
        }

        @Override
        public int lengthX() {
            return lX;
        }

        @Override
        public int lengthY() {
            return lY;
        }

        @Override
        public int lengthZ() {
            return lZ;
        }

        @Override
        public @NotNull Bounds3I immutable() {
            return this;
        }

        @Override
        public @NotNull Bounds3I immutableView() {
            return this;
        }

        @Override
        protected @NotNull Bounds3I originOp(int x, int y, int z) {
            return new Immutable(x, y, z, lX, lY, lZ);
        }

        @Override
        protected @NotNull Bounds3I lengthOp(int x, int y, int z) {
            return new Immutable(oX, oY, oZ, x, y, z);
        }

        @Override
        protected @NotNull Bounds3I op(int oX, int oY, int oZ, int lX, int lY, int lZ) {
            return new Immutable(oX, oY, oZ, lX, lY, lZ);
        }
    }

    /**
     * The read-through bounds implementation.
     */
    final class View extends Base {
        private final Mutable mutable;

        private View(Mutable mutable) {
            this.mutable = mutable;
        }

        @Override
        public int originX() {
            return mutable.oX;
        }

        @Override
        public int originY() {
            return mutable.oY;
        }

        @Override
        public int originZ() {
            return mutable.oZ;
        }

        @Override
        public int lengthX() {
            return mutable.lX;
        }

        @Override
        public int lengthY() {
            return mutable.lY;
        }

        @Override
        public int lengthZ() {
            return mutable.lZ;
        }

        @Override
        public @NotNull Bounds3I immutable() {
            return new Immutable(mutable.oX, mutable.oY, mutable.oZ, mutable.lX, mutable.lY, mutable.lZ);
        }

        @Override
        public @NotNull Bounds3I immutableView() {
            return this;
        }

        @Override
        protected @NotNull Bounds3I originOp(int x, int y, int z) {
            return new Immutable(x, y, z, mutable.lX, mutable.lY, mutable.lZ);
        }

        @Override
        protected @NotNull Bounds3I lengthOp(int x, int y, int z) {
            return new Immutable(mutable.oX, mutable.oY, mutable.oZ, x, y, z);
        }

        @Override
        protected @NotNull Bounds3I op(int oX, int oY, int oZ, int lX, int lY, int lZ) {
            return new Immutable(oX, oY, oZ, lX, lY, lZ);
        }
    }
}