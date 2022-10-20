package com.github.steanky.vector;

import org.jetbrains.annotations.NotNull;

/**
 * A three-dimensional bounding box with integer components. Supports immutable and mutable variants.
 */
public sealed interface Bounds3I permits Bounds3I.Base {
    /**
     * The origin x-component. This is the x-coordinate of the corner of the bounding box that is closest to
     * {@link Integer#MIN_VALUE}.
     * @return the x-origin
     */
    int originX();

    /**
     * The origin y-component. This is the y-coordinate of the corner of the bounding box that is closest to
     * {@link Integer#MIN_VALUE}.
     * @return the y-origin
     */
    int originY();

    /**
     * The origin z-component. This is the z-coordinate of the corner of the bounding box that is closest to
     * {@link Integer#MIN_VALUE}.
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
     * @return the x-max
     */
    default int maxX() {
        return originX() + lengthX();
    }

    /**
     * The maximum y-component. This is the y-coordinate of the corner of the bounding box that is farthest from
     * {@link Integer#MIN_VALUE}.
     * @return the y-max
     */
    default int maxY() {
        return originY() + lengthY();
    }

    /**
     * The maximum z-component. This is the z-coordinate of the corner of the bounding box that is farthest from
     * {@link Integer#MIN_VALUE}.
     * @return the z-max
     */
    default int maxZ() {
        return originZ() + lengthZ();
    }

    /**
     * The volume of the bounds.
     *
     * @return the product of the lengths of the bounds
     */
    int volume();

    /**
     * Changes the origin vector of the bounds.
     *
     * @param x the new x-origin
     * @param y the new y-origin
     * @param z the new z-origin
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    @NotNull Bounds3I setOrigin(int x, int y, int z);

    /**
     * Convenience override for {@link Bounds3I#setOrigin(int, int, int)}.
     *
     * @param vector the origin vector
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
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    @NotNull Bounds3I setLengths(int x, int y, int z);

    /**
     * Convenience override for {@link Bounds3I#setLengths(int, int, int)}. Length components must be {@code >= 0}.
     *
     * @param vector the lengths vector
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    default @NotNull Bounds3I setLengths(@NotNull Vec3I vector) {
        return setLengths(vector.x(), vector.y(), vector.z());
    }

    /**
     * Sets all components of the bounds.
     *
     * @param x the new x-origin
     * @param y the new y-origin
     * @param z the new z-origin
     * @param lX the new x-length
     * @param lY the new y-length
     * @param lZ the new z-length
     * @return this bounds, for chaining
     * @throws UnsupportedOperationException if this bounds is immutable
     */
    default @NotNull Bounds3I set(int x, int y, int z, int lX, int lY, int lZ) {
        throw new UnsupportedOperationException();
    }

    /**
     * Convenience override for {@link Bounds3I#set(int, int, int, int, int, int)}.
     *
     * @param origin origin vector
     * @param lengths lengths vector
     * @return this bounds, for chaining
     */
    default @NotNull Bounds3I set(@NotNull Vec3I origin, @NotNull Vec3I lengths) {
        return set(origin.x(), origin.y(), origin.z(), lengths.x(), lengths.y(), lengths.z());
    }

    /**
     * Convenience override for {@link Bounds3I#set(int, int, int, int, int, int)}.
     *
     * @param origin origin vector
     * @param lX length x
     * @param lY length y
     * @param lZ length z
     * @return this bounds, for chaining
     */
    default @NotNull Bounds3I set(@NotNull Vec3I origin, int lX, int lY, int lZ) {
        return set(origin.x(), origin.y(), origin.z(), lX, lY, lZ);
    }

    /**
     * Convenience override for {@link Bounds3I#set(int, int, int, int, int, int)}.
     *
     * @param x origin x
     * @param y origin y
     * @param z origin z
     * @param lengths lengths vector
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
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    @NotNull Bounds3I shift(int x, int y, int z);

    /**
     * Convenience override for {@link Bounds3I#shift(int, int, int)}.
     *
     * @param amount the shift vector
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
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    @NotNull Bounds3I expandDirectional(int x, int y, int z);

    default @NotNull Bounds3I expandDirectional(@NotNull Vec3I amount) {
        return expandDirectional(amount.x(), amount.y(), amount.z());
    }

    @NotNull Bounds3I shrinkDirectional(int x, int y, int z);

    default @NotNull Bounds3I shrinkDirectional(@NotNull Vec3I amount) {
        return shrinkDirectional(amount.x(), amount.y(), amount.z());
    }

    boolean overlaps(@NotNull Bounds3I other);

    boolean contains(int x, int y, int z);

    default boolean contains(@NotNull Vec3I vector) {
        return contains(vector.x(), vector.y(), vector.z());
    }

    void forEach(@NotNull Vec3IConsumer consumer);

    void forEachUntil(@NotNull Vec3IPredicate predicate);

    @NotNull Bounds3I mutableCopy();

    default @NotNull Bounds3I ensureMutable() {
        if (this instanceof Mutable) {
            return this;
        }

        return mutableCopy();
    }

    @NotNull Bounds3I immutable();

    @NotNull Bounds3I immutableView();

    static @NotNull Bounds3I enclosingImmutable(@NotNull Bounds3I bounds) {
        return bounds.immutable();
    }

    static @NotNull Bounds3I enclosingImmutable(@NotNull Bounds3I first, @NotNull Bounds3I second) {
        return Base.enclosingFromPair(false, first, second);
    }

    static @NotNull Bounds3I enclosingImmutable(@NotNull Bounds3I @NotNull ... bounds) {
        return Base.enclosingFromArray(false, bounds);
    }

    static @NotNull Bounds3I enclosingMutable(@NotNull Bounds3I bounds) {
        return bounds.mutableCopy();
    }

    static @NotNull Bounds3I enclosingMutable(@NotNull Bounds3I first, @NotNull Bounds3I second) {
        return Base.enclosingFromPair(true, first, second);
    }

    static @NotNull Bounds3I enclosingMutable(@NotNull Bounds3I @NotNull ... bounds) {
        return Base.enclosingFromArray(true, bounds);
    }

    static @NotNull Bounds3I immutable(int x, int y, int z, int lX, int lY, int lZ) {
        Base.validateLengths(lX, lY, lZ);
        return new Immutable(x, y, z, lX, lY, lZ);
    }

    static @NotNull Bounds3I immutable(@NotNull Vec3I origin, @NotNull Vec3I lengths) {
        int lX = lengths.x();
        int lY = lengths.y();
        int lZ = lengths.z();

        Base.validateLengths(lX, lY, lZ);
        return new Immutable(origin.x(), origin.y(), origin.z(), lX, lY, lZ);
    }

    static @NotNull Bounds3I immutable(@NotNull Vec3I origin, int lX, int lY, int lZ) {
        Base.validateLengths(lX, lY, lZ);
        return new Immutable(origin.x(), origin.y(), origin.z(), lX, lY, lZ);
    }

    static @NotNull Bounds3I immutable(int x, int y, int z, @NotNull Vec3I lengths) {
        int lX = lengths.x();
        int lY = lengths.y();
        int lZ = lengths.z();

        Base.validateLengths(lX, lY, lZ);
        return new Immutable(x, y, z, lX, lY, lZ);
    }

    static @NotNull Bounds3I mutable(int x, int y, int z, int lX, int lY, int lZ) {
        Base.validateLengths(lX, lY, lZ);
        return new Mutable(x, y, z, lX, lY, lZ);
    }

    static @NotNull Bounds3I mutable(@NotNull Vec3I origin, @NotNull Vec3I lengths) {
        int lX = lengths.x();
        int lY = lengths.y();
        int lZ = lengths.z();

        Base.validateLengths(lX, lY, lZ);
        return new Mutable(origin.x(), origin.y(), origin.z(), lX, lY, lZ);
    }

    static @NotNull Bounds3I mutable(@NotNull Vec3I origin, int lX, int lY, int lZ) {
        Base.validateLengths(lX, lY, lZ);
        return new Mutable(origin.x(), origin.y(), origin.z(), lX, lY, lZ);
    }

    static @NotNull Bounds3I mutable(int x, int y, int z, @NotNull Vec3I lengths) {
        int lX = lengths.x();
        int lY = lengths.y();
        int lZ = lengths.z();

        Base.validateLengths(lX, lY, lZ);
        return new Mutable(x, y, z, lX, lY, lZ);
    }

    abstract sealed class Base implements Bounds3I permits Mutable, Immutable, View {
        private static final int HASH_PRIME = 31;

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
                return originX() == other.originX() && originY() == other.originY() && originZ() == other.originZ()
                        && lengthX() == other.lengthX() && lengthY() == other.lengthY() && lengthZ() == other.lengthZ();
            }

            return false;
        }

        private static void validateLengths(int x, int y, int z) {
            if (x < 0 || y < 0 || z < 0) {
                throw new IllegalArgumentException("Negative lengths are not allowed");
            }
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

        private static Bounds3I enclosingFromArray(boolean mutable, Bounds3I ... bounds) {
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
        public int volume() {
            return lengthX() * lengthY() * lengthZ();
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
            return op(originX() - amount, originY() - amount, originZ() - amount, Math.max(lengthX() +
                    doubleAmount, 0), Math.max(lengthY() + doubleAmount, 0), Math.max(lengthZ() + doubleAmount, 0));
        }

        private @NotNull Bounds3I expandOrShrink(int x, int y, int z, boolean expand) {
            int originOffsetX = 0;
            int originOffsetY = 0;
            int originOffsetZ = 0;

            int lengthOffsetX = 0;
            int lengthOffsetY = 0;
            int lengthOffsetZ = 0;

            if (x < 0) {
                if (expand) {
                    originOffsetX = x;
                }
                else {
                    lengthOffsetX = x;
                }
            }
            else if (x > 0) {
                if (expand) {
                    lengthOffsetX = x;
                }
                else {
                    originOffsetX = x;
                }
            }

            if (y < 0) {
                if (expand) {
                    originOffsetY = y;
                }
                else {
                    lengthOffsetY = y;
                }
            }
            else if (y > 0) {
                if (expand) {
                    lengthOffsetY = y;
                }
                else {
                    originOffsetY = y;
                }
            }

            if (z < 0) {
                if (expand) {
                    originOffsetZ = z;
                }
                else {
                    lengthOffsetZ = z;
                }
            }
            else if (z > 0) {
                if (expand) {
                    lengthOffsetZ = z;
                }
                else {
                    originOffsetZ = z;
                }
            }

            return op(originX() + originOffsetX, originY() + originOffsetY, originZ() + originOffsetZ,
                    Math.max(lengthX() + lengthOffsetX, 0), Math.max(lengthY() + lengthOffsetY, 0), Math.max(lengthZ() +
                            lengthOffsetZ, 0));
        }

        @Override
        public @NotNull Bounds3I expandDirectional(int x, int y, int z) {
            return expandOrShrink(x, y, z, true);
        }

        @Override
        public @NotNull Bounds3I shrinkDirectional(int x, int y, int z) {
            return expandOrShrink(x, y, z, false);
        }

        @Override
        public boolean overlaps(@NotNull Bounds3I other) {
            int nx1 = maxX();
            int ny1 = maxY();
            int nz1 = maxZ();

            int nx2 = other.maxX();
            int ny2 = other.maxY();
            int nz2 = other.maxZ();

            return Math.min(originX(), nx1) < Math.max(other.originX(), nx2) &&
                    Math.max(originX(), nx1) > Math.min(other.originX(), nx2) &&
                    Math.min(originY(), ny1) < Math.max(other.originY(), ny2) &&
                    Math.max(originY(), ny1) > Math.min(other.originY(), ny2) &&
                    Math.min(originZ(), nz1) < Math.max(other.originZ(), nz2) &&
                    Math.max(originZ(), nz1) > Math.min(other.originZ(), nz2);
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

        protected abstract @NotNull Bounds3I originOp(int x, int y, int z);

        protected abstract @NotNull Bounds3I lengthOp(int x, int y, int z);

        protected abstract @NotNull Bounds3I op(int oX, int oY, int oZ, int lX, int lY, int lZ);
    }

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
