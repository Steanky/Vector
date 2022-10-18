package com.github.steanky.vector;

import org.jetbrains.annotations.NotNull;

/**
 * A representation of a three-dimensional mathematical vector with integer components. Supports both mutable and
 * immutable variants. Immutable vectors disallow all {@code set} operations.
 * <p>
 * Other mutating operations, such as {@link Vec3I#add(int, int, int)}, are mutating if the vector is mutable, but will
 * otherwise create and return a new immutable vector.
 */
public sealed interface Vec3I permits Vec3I.Base {
    /**
     * The immutable origin vector (0, 0, 0).
     */
    Vec3I ORIGIN = Vec3ICache.cached(0, 0, 0);

    /**
     * The x-component of this vector.
     * @return the x-component of this vector
     */
    int getX();

    /**
     * The y-component of this vector.
     * @return the y-component of this vector
     */
    int getY();

    /**
     * The z-component of this vector.
     * @return the z-component of this vector
     */
    int getZ();

    /**
     * Adds a vector to this one.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the sum of this vector and the provided coordinates
     */
    @NotNull Vec3I add(int x, int y, int z);

    /**
     * Adds a vector to this one.
     *
     * @param other the vector to add
     * @return the sum of this vector and another
     */
    default @NotNull Vec3I add(@NotNull Vec3I other) {
        return add(other.getX(), other.getY(), other.getZ());
    }

    @NotNull Vec3I sub(int x, int y, int z);

    default @NotNull Vec3I sub(@NotNull Vec3I other) {
        return sub(other.getX(), other.getY(), other.getZ());
    }

    @NotNull Vec3I mul(int x, int y, int z);

    default @NotNull Vec3I mul(@NotNull Vec3I other) {
        return mul(other.getX(), other.getY(),other.getZ());
    }

    @NotNull Vec3I div(int x, int y, int z);

    default @NotNull Vec3I div(@NotNull Vec3I other) {
        return div(other.getX(), other.getY(), other.getZ());
    }

    default void set(int x, int y, int z) {
        throw new UnsupportedOperationException();
    }

    default void setX(int x) {
        throw new UnsupportedOperationException();
    }

    default void setY(int y) {
        throw new UnsupportedOperationException();
    }

    default void setZ(int z) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the shared thread local mutable vector for use by the calling thread. Note that there is only one
     * instance created per thread, and so the value of the vector when calling this method will be whatever it was last
     * set to by the calling thread. If the calling thread has not obtained an instance previously, the vector will be
     * set to the origin (0, 0, 0).
     *
     * @return the shared thread local mutable vector
     */
    static @NotNull Vec3I threadLocal() {
        return Vec3ICache.THREAD_LOCAL.get();
    }

    /**
     * Creates a new mutable vector.
     *
     * @param x the initial x-component
     * @param y the initial y-component
     * @param z the initial z-component
     * @return a new mutable vector
     */
    static @NotNull Vec3I mutable(int x, int y, int z) {
        return new Mutable(x, y, z);
    }

    /**
     * Obtains an immutable vector for the provided coordinates. Common values, such as (0, 0, 0), may be cached.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return an immutable vector
     */
    static @NotNull Vec3I immutable(int x, int y, int z) {
        return Vec3ICache.cached(x, y, z);
    }

    sealed abstract class Base implements Vec3I permits Immutable, Mutable {
        @Override
        public final int hashCode() {
            int result = 31 + getX();
            result = 31 * result + getY();
            result = 31 * result + getZ();
            return result;
        }

        @Override
        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj == this) {
                return true;
            }

            if (obj instanceof Vec3I other) {
                return getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ();
            }

            return false;
        }

        @Override
        public @NotNull Vec3I add(int x, int y, int z) {
            return op(getX() + x, getY() + y, getZ() + z);
        }

        @Override
        public @NotNull Vec3I sub(int x, int y, int z) {
            return op(getX() - x, getY() - y, getZ() - z);
        }

        @Override
        public @NotNull Vec3I mul(int x, int y, int z) {
            return op(getX() * x, getY() * y, getZ() * z);
        }

        @Override
        public @NotNull Vec3I div(int x, int y, int z) {
            return op(getX() / x, getY() / y, getZ() / z);
        }

        protected abstract @NotNull Vec3I op(int x, int y, int z);
    }

    final class Immutable extends Base {
        private final int x;
        private final int y;
        private final int z;

        Immutable(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public int getZ() {
            return z;
        }


        @Override
        protected @NotNull Vec3I op(int x, int y, int z) {
            return immutable(x, y, z);
        }
    }

    final class Mutable extends Base {
        private int x;
        private int y;
        private int z;

        Mutable(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public int getZ() {
            return z;
        }

        @Override
        public void set(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public void setX(int x) {
            this.x = x;
        }

        @Override
        public void setY(int y) {
            this.y = y;
        }

        @Override
        public void setZ(int z) {
            this.z = z;
        }

        @Override
        protected @NotNull Vec3I op(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }
    }
}