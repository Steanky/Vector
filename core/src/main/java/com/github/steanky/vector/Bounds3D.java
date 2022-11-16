package com.github.steanky.vector;

import org.jetbrains.annotations.NotNull;

/**
 * A three-dimensional bounding box with integer components. Supports immutable and mutable variants.
 */
//there's no way to deduplicate much of this code without hurting performance significantly; e.g. by using Number for
//utility methods
@SuppressWarnings("DuplicatedCode")
public sealed interface Bounds3D permits Bounds3D.Base {
    /**
     * Override of {@link Bounds3D#enclosingImmutable(Bounds3D...)} to avoid an array allocation.
     *
     * @param bounds the first and only bounds
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3D enclosingImmutable(@NotNull Bounds3D bounds) {
        return bounds.immutable();
    }

    /**
     * Override of {@link Bounds3D#enclosingImmutable(Bounds3D...)} to avoid an array allocation
     *
     * @param first  the first bounds
     * @param second the second bounds
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3D enclosingImmutable(@NotNull Bounds3D first, @NotNull Bounds3D second) {
        return Base.enclosingFromPair(false, first, second);
    }

    /**
     * Calculates the smallest possible bounds that encloses each of given child bounds. Produces an immutable bounds.
     *
     * @param bounds the child bounds array
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3D enclosingImmutable(@NotNull Bounds3D @NotNull ... bounds) {
        return Base.enclosingFromArray(false, bounds);
    }

    /**
     * Override of {@link Bounds3D#enclosingMutable(Bounds3D...)} to avoid an array allocation.
     *
     * @param bounds the first and only bounds
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3D enclosingMutable(@NotNull Bounds3D bounds) {
        return bounds.mutableCopy();
    }

    /**
     * Override of {@link Bounds3D#enclosingMutable(Bounds3D...)} to avoid an array allocation
     *
     * @param first  the first bounds
     * @param second the second bounds
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3D enclosingMutable(@NotNull Bounds3D first, @NotNull Bounds3D second) {
        return Base.enclosingFromPair(true, first, second);
    }

    /**
     * Calculates the smallest possible bounds that encloses each of given child bounds. Produces a mutable bounds.
     *
     * @param bounds the child bounds array
     *
     * @return the smallest possible bounds that encloses the given child bounds
     */
    static @NotNull Bounds3D enclosingMutable(@NotNull Bounds3D @NotNull ... bounds) {
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
    static @NotNull Bounds3D immutable(double x, double y, double z, double lX, double lY, double lZ) {
        Base.validateLengths(lX, lY, lZ);
        return new Immutable(x, y, z, lX, lY, lZ);
    }

    /**
     * Convenience override for {@link Bounds3D#immutable(double, double, double, double, double, double)}.
     *
     * @param origin  the origin vector
     * @param lengths the lengths vector
     *
     * @return a new immutable bounds
     */
    static @NotNull Bounds3D immutable(@NotNull Vec3D origin, @NotNull Vec3D lengths) {
        return immutable(origin.x(), origin.y(), origin.z(), lengths.x(), lengths.y(), lengths.z());
    }

    /**
     * Convenience override for {@link Bounds3D#immutable(double, double, double, double, double, double)}.
     *
     * @param origin the origin vector
     * @param lX     the x-length
     * @param lY     the y-length
     * @param lZ     the z-length
     *
     * @return a new immutable bounds
     */
    static @NotNull Bounds3D immutable(@NotNull Vec3D origin, double lX, double lY, double lZ) {
        return immutable(origin.x(), origin.y(), origin.z(), lX, lY, lZ);
    }

    /**
     * Convenience override for {@link Bounds3D#immutable(double, double, double, double, double, double)}.
     *
     * @param x       the x-coordinate of the origin
     * @param y       the y-coordinate of the origin
     * @param z       the z-coordinate of the origin
     * @param lengths the lengths vector
     *
     * @return a new immutable bounds
     */
    static @NotNull Bounds3D immutable(double x, double y, double z, @NotNull Vec3D lengths) {
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
    static @NotNull Bounds3D mutable(double x, double y, double z, double lX, double lY, double lZ) {
        Base.validateLengths(lX, lY, lZ);
        return new Mutable(x, y, z, lX, lY, lZ);
    }

    /**
     * Convenience override for {@link Bounds3D#mutable(double, double, double, double, double, double)}.
     *
     * @param origin  the origin vector
     * @param lengths the lengths vector
     *
     * @return a new mutable bounds
     */
    static @NotNull Bounds3D mutable(@NotNull Vec3D origin, @NotNull Vec3D lengths) {
        return mutable(origin.x(), origin.y(), origin.z(), lengths.x(), lengths.y(), lengths.z());
    }

    /**
     * Convenience override for {@link Bounds3D#mutable(double, double, double, double, double, double)}.
     *
     * @param origin the origin vector
     * @param lX     the x-length
     * @param lY     the y-length
     * @param lZ     the z-length
     *
     * @return a new mutable bounds
     */
    static @NotNull Bounds3D mutable(@NotNull Vec3D origin, double lX, double lY, double lZ) {
        return mutable(origin.x(), origin.y(), origin.z(), lX, lY, lZ);
    }

    /**
     * Convenience override for {@link Bounds3D#mutable(double, double, double, double, double, double)}.
     *
     * @param x       the x-coordinate of the origin
     * @param y       the y-coordinate of the origin
     * @param z       the z-coordinate of the origin
     * @param lengths the lengths vector
     *
     * @return a new mutable bounds
     */
    static @NotNull Bounds3D mutable(double x, double y, double z, @NotNull Vec3D lengths) {
        return mutable(x, y, z, lengths.x(), lengths.y(), lengths.z());
    }

    /**
     * The origin x-component. This is the x-coordinate of the corner of the bounding box that is closest to
     * {@link Integer#MIN_VALUE}.
     *
     * @return the x-origin
     */
    double originX();

    /**
     * The origin y-component. This is the y-coordinate of the corner of the bounding box that is closest to
     * {@link Integer#MIN_VALUE}.
     *
     * @return the y-origin
     */
    double originY();

    /**
     * The origin z-component. This is the z-coordinate of the corner of the bounding box that is closest to
     * {@link Integer#MIN_VALUE}.
     *
     * @return the z-origin
     */
    double originZ();

    /**
     * The x-length of the bounds. Will always be larger than or equal to 0.
     *
     * @return the x-length of this bounds
     */
    double lengthX();

    /**
     * The y-length of the bounds. Will always be larger than or equal to 0.
     *
     * @return the y-length of this bounds
     */
    double lengthY();

    /**
     * The z-length of the bounds. Will always be larger than or equal to 0.
     *
     * @return the z-length of this bounds
     */
    double lengthZ();

    /**
     * The maximum x-component. This is the x-coordinate of the corner of the bounding box that is farthest from
     * {@link Integer#MIN_VALUE}.
     *
     * @return the x-max
     */
    default double maxX() {
        return originX() + lengthX();
    }

    /**
     * The maximum y-component. This is the y-coordinate of the corner of the bounding box that is farthest from
     * {@link Integer#MIN_VALUE}.
     *
     * @return the y-max
     */
    default double maxY() {
        return originY() + lengthY();
    }

    /**
     * The maximum z-component. This is the z-coordinate of the corner of the bounding box that is farthest from
     * {@link Integer#MIN_VALUE}.
     *
     * @return the z-max
     */
    default double maxZ() {
        return originZ() + lengthZ();
    }

    /**
     * The volume of the bounds.
     *
     * @return the product of the lengths of the bounds
     */
    double volume();

    /**
     * Changes the origin vector of the bounds.
     *
     * @param x the new x-origin
     * @param y the new y-origin
     * @param z the new z-origin
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    @NotNull Bounds3D setOrigin(double x, double y, double z);

    /**
     * Convenience override for {@link Bounds3D#setOrigin(double, double, double)}.
     *
     * @param vector the origin vector
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    default @NotNull Bounds3D setOrigin(@NotNull Vec3D vector) {
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
    @NotNull Bounds3D setLengths(double x, double y, double z);

    /**
     * Convenience override for {@link Bounds3D#setLengths(double, double, double)}. Length components must be
     * {@code >= 0}.
     *
     * @param vector the lengths vector
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    default @NotNull Bounds3D setLengths(@NotNull Vec3D vector) {
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
    default @NotNull Bounds3D set(double x, double y, double z, double lX, double lY, double lZ) {
        throw new UnsupportedOperationException();
    }

    /**
     * Convenience override for {@link Bounds3D#set(double, double, double, double, double, double)}.
     *
     * @param origin  origin vector
     * @param lengths lengths vector
     *
     * @return this bounds, for chaining
     */
    default @NotNull Bounds3D set(@NotNull Vec3D origin, @NotNull Vec3D lengths) {
        return set(origin.x(), origin.y(), origin.z(), lengths.x(), lengths.y(), lengths.z());
    }

    /**
     * Convenience override for {@link Bounds3D#set(double, double, double, double, double, double)}.
     *
     * @param origin origin vector
     * @param lX     length x
     * @param lY     length y
     * @param lZ     length z
     *
     * @return this bounds, for chaining
     */
    default @NotNull Bounds3D set(@NotNull Vec3D origin, double lX, double lY, double lZ) {
        return set(origin.x(), origin.y(), origin.z(), lX, lY, lZ);
    }

    /**
     * Convenience override for {@link Bounds3D#set(double, double, double, double, double, double)}.
     *
     * @param x       origin x
     * @param y       origin y
     * @param z       origin z
     * @param lengths lengths vector
     *
     * @return this bounds, for chaining
     */
    default @NotNull Bounds3D set(double x, double y, double z, @NotNull Vec3D lengths) {
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
    @NotNull Bounds3D shift(double x, double y, double z);

    /**
     * Convenience override for {@link Bounds3D#shift(double, double, double)}.
     *
     * @param amount the shift vector
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    default @NotNull Bounds3D shift(@NotNull Vec3D amount) {
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
    @NotNull Bounds3D expand(double amount);

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
    @NotNull Bounds3D expandDirectional(double x, double y, double z);

    /**
     * Convenience override for {@link Bounds3D#expandDirectional(double, double, double)}.
     *
     * @param amount the expansion vector
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    default @NotNull Bounds3D expandDirectional(@NotNull Vec3D amount) {
        return expandDirectional(amount.x(), amount.y(), amount.z());
    }

    /**
     * Shrinks this bounding box in a specific direction. If this operation would cause any lengths to become negative,
     * an exception will be thrown.
     *
     * @param x the x shrink coordinate
     * @param y the y shrink coordinate
     * @param z the z shrink coordinate
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     * @throws IllegalArgumentException if this shrink would cause any of the bounding box lengths to go below 0
     */
    @NotNull Bounds3D shrinkDirectional(double x, double y, double z);

    /**
     * Convenience override for {@link Bounds3D#shrinkDirectional(double, double, double)}.
     *
     * @param amount the shrink vector
     *
     * @return a new bounds if this bounds is immutable, otherwise this bounds
     */
    default @NotNull Bounds3D shrinkDirectional(@NotNull Vec3D amount) {
        return shrinkDirectional(amount.x(), amount.y(), amount.z());
    }

    /**
     * Tests if this bounding box overlaps another.
     *
     * @param other the other bounds to test for overlap with
     *
     * @return true if there is an overlap, false otherwise
     */
    boolean overlaps(@NotNull Bounds3D other);

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
    boolean overlaps(double ox, double oy, double oz, double lx, double ly, double lz);

    /**
     * Tests if this bounding box contains the given vector.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     *
     * @return true if this bounds contains the vector, false otherwise
     */
    boolean contains(double x, double y, double z);

    /**
     * Convenience override for {@link Bounds3D#contains(double, double, double)}.
     *
     * @param vector the vector
     *
     * @return true if this bounds contains the vector, false otherwise
     */
    default boolean contains(@NotNull Vec3D vector) {
        return contains(vector.x(), vector.y(), vector.z());
    }

    /**
     * Creates a new mutable copy of this bounds.
     *
     * @return a new mutable copy of this bounds
     */
    @NotNull Bounds3D mutableCopy();

    /**
     * If this bounds is mutable, returns it. Otherwise, creates a mutable copy and returns it.
     *
     * @return this bounds if already mutable; otherwise a mutable copy
     */
    default @NotNull Bounds3D ensureMutable() {
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
    @NotNull Bounds3D immutable();

    /**
     * Creates a read-through immutable bounds. The returned bounds cannot be modified, but changes to the underlying
     * bounds, if mutable, will be visible. If this bounds is already immutable, this bounds is returned.
     *
     * @return an immutable view bounds
     */
    @NotNull Bounds3D immutableView();

    /**
     * Base class of bounds implementations.
     */
    abstract sealed class Base implements Bounds3D permits Mutable, Immutable, View {
        private static final int HASH_PRIME = 31;

        private static void validateLengths(double x, double y, double z) {
            if (x < 0 || y < 0 || z < 0) {
                throw new IllegalArgumentException("Negative lengths are not allowed");
            }
        }

        private static double validateLength(double a, double b) {
            double sum = a + b;
            if (sum < 0) {
                throw new IllegalArgumentException("Negative lengths are not allowed");
            }

            return sum;
        }

        private static Bounds3D enclosingFromPair(boolean mutable, Bounds3D first, Bounds3D second) {
            double minX = Math.min(first.originX(), second.originX());
            double minY = Math.min(first.originY(), second.originY());
            double minZ = Math.min(first.originZ(), second.originZ());

            double maxX = Math.max(first.maxX(), second.maxX());
            double maxY = Math.max(first.maxY(), second.maxY());
            double maxZ = Math.max(first.maxZ(), second.maxZ());

            return mutable ? new Mutable(minX, minY, minZ, maxX - minX, maxY - minY, maxZ - minZ) :
                    new Immutable(minX, minY, minZ, maxX - minX, maxY - minY, maxZ - minZ);
        }

        private static Bounds3D enclosingFromArray(boolean mutable, Bounds3D... bounds) {
            if (bounds.length == 0) {
                throw new IllegalArgumentException("Must provide at least one region");
            }

            Bounds3D first = bounds[0];
            if (bounds.length == 1) {
                return mutable ? first.mutableCopy() : first.immutable();
            }

            double minX = first.originX();
            double minY = first.originY();
            double minZ = first.originZ();

            double maxX = first.maxX();
            double maxY = first.maxY();
            double maxZ = first.maxZ();

            for (int i = 1; i < bounds.length; i++) {
                Bounds3D sample = bounds[i];

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
            int result = HASH_PRIME + Double.hashCode(originX());
            result = HASH_PRIME * result + Double.hashCode(originY());
            result = HASH_PRIME * result + Double.hashCode(originZ());
            result = HASH_PRIME * result + Double.hashCode(lengthX());
            result = HASH_PRIME * result + Double.hashCode(lengthY());
            return HASH_PRIME * result + Double.hashCode(lengthZ());
        }

        @Override
        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj == this) {
                return true;
            }

            if (obj instanceof Bounds3D other) {
                return originX() == other.originX() && originY() == other.originY() && originZ() == other.originZ() &&
                        lengthX() == other.lengthX() && lengthY() == other.lengthY() && lengthZ() == other.lengthZ();
            }

            return false;
        }

        @Override
        public double volume() {
            return lengthX() * lengthY() * lengthZ();
        }

        @Override
        public @NotNull Bounds3D setOrigin(double x, double y, double z) {
            return originOp(x, y, z);
        }

        @Override
        public @NotNull Bounds3D setLengths(double x, double y, double z) {
            validateLengths(x, y, z);
            return lengthOp(x, y, z);
        }

        @Override
        public @NotNull Bounds3D shift(double x, double y, double z) {
            return originOp(originX() + x, originY() + y, originZ() + z);
        }

        @Override
        public @NotNull Bounds3D expand(double amount) {
            double doubleAmount = amount * 2;

            double lX = validateLength(lengthX(), doubleAmount);
            double lY = validateLength(lengthY(), doubleAmount);
            double lZ = validateLength(lengthZ(), doubleAmount);
            return op(originX() - amount, originY() - amount, originZ() - amount, lX, lY, lZ);
        }

        @Override
        public @NotNull Bounds3D expandDirectional(double x, double y, double z) {
            return expandOrShrink(x, y, z, true);
        }

        @Override
        public @NotNull Bounds3D shrinkDirectional(double x, double y, double z) {
            return expandOrShrink(x, y, z, false);
        }

        @Override
        public boolean overlaps(@NotNull Bounds3D other) {
            return overlaps(other.originX(), other.originY(), other.originZ(), other.lengthX(), other.lengthY(),
                    other.lengthZ());
        }

        @Override
        public boolean overlaps(double ox, double oy, double oz, double lx, double ly, double lz) {
            double thisMaxX = maxX();
            double thisMaxY = maxY();
            double thisMaxZ = maxZ();

            double otherMaxX = ox + lx;
            double otherMaxY = oy + ly;
            double otherMaxZ = oz + lz;

            return originX() < otherMaxX && originY() < otherMaxY && originZ() < otherMaxZ && thisMaxX > ox &&
                    thisMaxY > oy && thisMaxZ > oz;
        }

        @Override
        public boolean contains(double x, double y, double z) {
            if (x >= originX() && y >= originY() && z >= originZ()) {
                double nx = maxX();
                double ny = maxY();
                double nz = maxZ();

                return x < nx && y < ny && z < nz;
            }

            return false;
        }

        @Override
        public @NotNull Bounds3D mutableCopy() {
            return new Mutable(originX(), originY(), originZ(), lengthX(), lengthY(), lengthZ());
        }

        private @NotNull Bounds3D expandOrShrink(double x, double y, double z, boolean expand) {
            double originOffsetX = 0;
            double originOffsetY = 0;
            double originOffsetZ = 0;

            double lengthOffsetX = 0;
            double lengthOffsetY = 0;
            double lengthOffsetZ = 0;

            if (x < 0) {
                if (expand) {
                    originOffsetX = x;
                } else {
                    lengthOffsetX = x;
                }
            } else if (x > 0) {
                if (expand) {
                    lengthOffsetX = x;
                } else {
                    originOffsetX = x;
                }
            }

            if (y < 0) {
                if (expand) {
                    originOffsetY = y;
                } else {
                    lengthOffsetY = y;
                }
            } else if (y > 0) {
                if (expand) {
                    lengthOffsetY = y;
                } else {
                    originOffsetY = y;
                }
            }

            if (z < 0) {
                if (expand) {
                    originOffsetZ = z;
                } else {
                    lengthOffsetZ = z;
                }
            } else if (z > 0) {
                if (expand) {
                    lengthOffsetZ = z;
                } else {
                    originOffsetZ = z;
                }
            }

            double lX = validateLength(lengthX(), lengthOffsetX);
            double lY = validateLength(lengthY(), lengthOffsetY);
            double lZ = validateLength(lengthZ(), lengthOffsetZ);

            return op(originX() + originOffsetX, originY() + originOffsetY, originZ() + originOffsetZ, lX, lY, lZ);
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
        protected abstract @NotNull Bounds3D originOp(double x, double y, double z);

        /**
         * Sets the lengths to a new value, or creates a new bounds with the new length values.
         *
         * @param x the x-length
         * @param y the y-length
         * @param z the z-length
         *
         * @return this bounds if mutable; otherwise a new bounds
         */
        protected abstract @NotNull Bounds3D lengthOp(double x, double y, double z);

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
        protected abstract @NotNull Bounds3D op(double oX, double oY, double oZ, double lX, double lY, double lZ);
    }

    /**
     * The mutable bounds implementation.
     */
    final class Mutable extends Base {
        private double oX;
        private double oY;
        private double oZ;

        private double lX;
        private double lY;
        private double lZ;

        Mutable(double oX, double oY, double oZ, double lX, double lY, double lZ) {
            this.oX = oX;
            this.oY = oY;
            this.oZ = oZ;

            this.lX = lX;
            this.lY = lY;
            this.lZ = lZ;
        }

        @Override
        public double originX() {
            return oX;
        }

        @Override
        public double originY() {
            return oY;
        }

        @Override
        public double originZ() {
            return oZ;
        }

        @Override
        public double lengthX() {
            return lX;
        }

        @Override
        public double lengthY() {
            return lY;
        }

        @Override
        public double lengthZ() {
            return lZ;
        }

        @Override
        public @NotNull Bounds3D set(double x, double y, double z, double lX, double lY, double lZ) {
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
        public @NotNull Bounds3D immutable() {
            return new Immutable(oX, oY, oZ, lX, lY, lZ);
        }

        @Override
        public @NotNull Bounds3D immutableView() {
            return new View(this);
        }

        @Override
        protected @NotNull Bounds3D originOp(double x, double y, double z) {
            this.oX = x;
            this.oY = y;
            this.oZ = z;
            return this;
        }

        @Override
        protected @NotNull Bounds3D lengthOp(double x, double y, double z) {
            this.lX = x;
            this.lY = y;
            this.lZ = z;
            return this;
        }

        @Override
        protected @NotNull Bounds3D op(double oX, double oY, double oZ, double lX, double lY, double lZ) {
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
        private final double oX;
        private final double oY;
        private final double oZ;

        private final double lX;
        private final double lY;
        private final double lZ;

        Immutable(double oX, double oY, double oZ, double lX, double lY, double lZ) {
            this.oX = oX;
            this.oY = oY;
            this.oZ = oZ;

            this.lX = lX;
            this.lY = lY;
            this.lZ = lZ;
        }

        @Override
        public double originX() {
            return oX;
        }

        @Override
        public double originY() {
            return oY;
        }

        @Override
        public double originZ() {
            return oZ;
        }

        @Override
        public double lengthX() {
            return lX;
        }

        @Override
        public double lengthY() {
            return lY;
        }

        @Override
        public double lengthZ() {
            return lZ;
        }

        @Override
        public @NotNull Bounds3D immutable() {
            return this;
        }

        @Override
        public @NotNull Bounds3D immutableView() {
            return this;
        }

        @Override
        protected @NotNull Bounds3D originOp(double x, double y, double z) {
            return new Immutable(x, y, z, lX, lY, lZ);
        }

        @Override
        protected @NotNull Bounds3D lengthOp(double x, double y, double z) {
            return new Immutable(oX, oY, oZ, x, y, z);
        }

        @Override
        protected @NotNull Bounds3D op(double oX, double oY, double oZ, double lX, double lY, double lZ) {
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
        public double originX() {
            return mutable.oX;
        }

        @Override
        public double originY() {
            return mutable.oY;
        }

        @Override
        public double originZ() {
            return mutable.oZ;
        }

        @Override
        public double lengthX() {
            return mutable.lX;
        }

        @Override
        public double lengthY() {
            return mutable.lY;
        }

        @Override
        public double lengthZ() {
            return mutable.lZ;
        }

        @Override
        public @NotNull Bounds3D immutable() {
            return new Immutable(mutable.oX, mutable.oY, mutable.oZ, mutable.lX, mutable.lY, mutable.lZ);
        }

        @Override
        public @NotNull Bounds3D immutableView() {
            return this;
        }

        @Override
        protected @NotNull Bounds3D originOp(double x, double y, double z) {
            return new Immutable(x, y, z, mutable.lX, mutable.lY, mutable.lZ);
        }

        @Override
        protected @NotNull Bounds3D lengthOp(double x, double y, double z) {
            return new Immutable(mutable.oX, mutable.oY, mutable.oZ, x, y, z);
        }

        @Override
        protected @NotNull Bounds3D op(double oX, double oY, double oZ, double lX, double lY, double lZ) {
            return new Immutable(oX, oY, oZ, lX, lY, lZ);
        }
    }
}
