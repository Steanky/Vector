package com.github.steanky.vector;

import org.jetbrains.annotations.NotNull;

/**
 * A representation of a three-dimensional mathematical vector with integer components. Supports both mutable and
 * immutable variants. Immutable vectors disallow all {@code set} operations.
 * <p>
 * Other mutating operations, such as {@link Vec3I#add(int, int, int)}, are mutating if the vector is mutable, but will
 * otherwise create and return a new immutable vector.
 * <p>
 * Implementations of this interface can be obtained by calling {@link Vec3I#immutable(int, int, int)} or
 * {@link Vec3I#mutable(int, int, int)}.
 */
public sealed interface Vec3I extends Comparable<Vec3I> permits Vec3I.Base {
    /**
     * The immutable origin vector (0, 0, 0).
     */
    Vec3I ORIGIN = VecCache.cached(0, 0, 0);

    /**
     * The x-component of this vector.
     * @return the x-component of this vector
     */
    int x();

    /**
     * The y-component of this vector.
     * @return the y-component of this vector
     */
    int y();

    /**
     * The z-component of this vector.
     * @return the z-component of this vector
     */
    int z();

    /**
     * Adds a vector to this one.
     *
     * @param x the x-component to add
     * @param y the y-component to add
     * @param z the z-component to add
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
        return add(other.x(), other.y(), other.z());
    }

    /**
     * Adds the same value to each of this vector's components.
     *
     * @param c the value to add
     * @return a vector made from adding the given value to each component of this vector
     */
    default @NotNull Vec3I add(int c) {
        return add(c, c, c);
    }

    /**
     * Subtracts another vector from this one.
     *
     * @param x the x-component to subtract
     * @param y the y-component to subtract
     * @param z the z-component to subtract
     * @return the difference of this vector and another
     */
    @NotNull Vec3I sub(int x, int y, int z);

    /**
     * Subtracts another vector from this one.
     *
     * @param other the vector to subtract from this one
     * @return the difference between this vector and another
     */
    default @NotNull Vec3I sub(@NotNull Vec3I other) {
        return sub(other.x(), other.y(), other.z());
    }

    /**
     * Subtracts the same value from each of this vector's components.
     *
     * @param c the value to subtract
     * @return a vector made from subtracting the given value from each component of this vector
     */
    default @NotNull Vec3I sub(int c) {
        return sub(c, c, c);
    }

    /**
     * Multiplies this vector by another. This is performed by multiplying each component of this vector by each
     * equivalent component of the given vector.
     *
     * @param x the x-component to multiply by
     * @param y the y-component to multiply by
     * @param z the z-component to multiply by
     * @return the product of this vector and another
     */
    @NotNull Vec3I mul(int x, int y, int z);

    /**
     * Multiplies this vector by another. This is performed by multiplying each component of this vector by each
     * equivalent component of the given vector.
     *
     * @param other the vector to multiply with this one
     * @return the product of this vector and another
     */
    default @NotNull Vec3I mul(@NotNull Vec3I other) {
        return mul(other.x(), other.y(),other.z());
    }

    /**
     * Multiplies this vector by a scalar.
     *
     * @param c the scalar to multiply by
     * @return the product of this vector and a scalar
     */
    default @NotNull Vec3I mul(int c) {
        return mul(c, c, c);
    }

    /**
     * Divides this vector by another. This is performed by dividing each component of this vector by the equivalent
     * component of the given vector.
     *
     * @param x the x-component to divide by
     * @param y the y-component to divide by
     * @param z the z-component to divide by
     * @return the quotient of this vector and another
     */
    @NotNull Vec3I div(int x, int y, int z);

    /**
     * Divides this vector by another. This is performed by dividing each component of this vector by the equivalent
     * component of the given vector.
     *
     * @param other the vector to divide this one by
     * @return the quotient of this vector and another
     */
    default @NotNull Vec3I div(@NotNull Vec3I other) {
        return div(other.x(), other.y(), other.z());
    }


    /**
     * Divides this vector by a scalar.
     *
     * @param c the scalar to divide by
     * @return the quotient of this vector and the scalar
     */
    default @NotNull Vec3I div(int c) {
        return div(c, c, c);
    }

    /**
     * The length of this vector (distance from the origin). Expected to be slower than {@link Vec3I#lengthSquared()}
     * due to requiring a {@link Math#sqrt(double)} call.
     *
     * @return the length of this vector
     */
    double length();

    /**
     * The squared length of this vector (squared distance from the origin).
     *
     * @return the squared length of this vector
     */
    double lengthSquared();

    /**
     * Computes the distance between this vector and another.
     *
     * @param x the x-component of the other vector
     * @param y the y-component of the other vector
     * @param z the z-component of the other vector
     * @return the distance between this vector and the other
     */
    double distanceTo(int x, int y, int z);

    /**
     * Computes the distance between this vector and another.
     *
     * @param other the other vector
     * @return the distance between this vector and the other
     */
    default double distanceTo(@NotNull Vec3I other) {
        return distanceTo(other.x(), other.y(), other.z());
    }

    /**
     * Computes the squared distance between this vector and another.
     *
     * @param x the x-component of the other vector
     * @param y the y-component of the other vector
     * @param z the z-component of the other vector
     * @return the squared distance between this vector and the other
     */
    double distanceSquaredTo(int x, int y, int z);

    /**
     * Computes the squared distance between this vector and another.
     *
     * @param other the other vector
     * @return the squared distance between this vector and the other
     */
    default double distanceSquaredTo(@NotNull Vec3I other) {
        return distanceSquaredTo(other.x(), other.y(), other.z());
    }

    /**
     * Returns the dot product of this vector and another.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the dot product of this vector and another
     */
    int dot(int x, int y, int z);

    /**
     * Returns the dot product of this vector and another.
     *
     * @param other the other vector
     * @return the dot product of this vector and another
     */
    default int dot(@NotNull Vec3I other) {
        return dot(other.x(), other.y(), other.z());
    }

    /**
     * Returns the cross product of this vector and another.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the cross product of this vector and another
     */
    @NotNull Vec3I cross(int x, int y, int z);

    /**
     * Returns the cross product of this vector and another.
     *
     * @param other the other vector
     * @return the cross product of this vector and another
     */
    default @NotNull Vec3I cross(@NotNull Vec3I other) {
        return cross(other.x(), other.y(), other.z());
    }

    /**
     * Creates a new, mutable copy of this vector.
     *
     * @return a new, mutable copy of this vector
     */
    @NotNull Vec3I mutableCopy();

    /**
     * If this vector is mutable, returns this vector. Otherwise, creates a new mutable vector with the same components
     * as this one, and returns it.
     *
     * @return a mutable vector with the same components as this one
     */
    default @NotNull Vec3I ensureMutable() {
        if (this instanceof Mutable) {
            return this;
        }

        return mutableCopy();
    }

    /**
     * Creates an immutable copy of this vector if it is mutable; if it is immutable, returns this vector.
     *
     * @return an immutable copy of this vector, or this same vector if it is immutable
     */
    @NotNull Vec3I immutable();

    /**
     * Creates an immutable view of this vector. If this vector is mutable, and is modified later, the changes will be
     * visible in the returned vector. If this vector is already immutable (or a view), it is returned as-is.
     *
     * @return an immutable view of this vector
     */
    @NotNull Vec3I immutableView();

    /**
     * Sets the value of this vector.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     * @param z the new z-coordinate
     * @return this instance, for chaining
     */
    default @NotNull Vec3I set(int x, int y, int z) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the value of this vector.
     *
     * @param other the vector whose components will be used to set this vector's
     * @return this instance, for chaining
     */
    default @NotNull Vec3I set(@NotNull Vec3I other) {
        return set(other.x(), other.y(), other.z());
    }

    /**
     * Sets the x-coordinate of this vector.
     *
     * @param x the new x-coordinate
     * @return this instance, for chaining
     */
    default @NotNull Vec3I setX(int x) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the y-coordinate of this vector.
     *
     * @param y the new y-coordinate
     * @return this instance, for chaining
     */
    default @NotNull Vec3I setY(int y) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the z-coordinate of this vector.
     *
     * @param z the new z-coordinate
     * @return this instance, for chaining
     */
    default @NotNull Vec3I setZ(int z) {
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
        return VecCache.THREAD_LOCAL_3I.get();
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
        return VecCache.cached(x, y, z);
    }

    /**
     * Creates a mutable vector from the result of calling {@link Math#floor(double)} on each component.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return a mutable floored vector
     */
    static @NotNull Vec3I mutableFloored(double x, double y, double z) {
        return mutable((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    /**
     * Creates a mutable vector from the result of calling {@link Math#floor(double)} on each component of the provided
     * double-precision vector.
     *
     * @param other the double-precision vector
     * @return a mutable floored vector
     */
    static @NotNull Vec3I mutableFloored(@NotNull Vec3D other) {
        return mutableFloored(other.x(), other.y(), other.z());
    }

    /**
     * Creates an immutable vector from the result of calling {@link Math#floor(double)} on each component.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return an immutable floored vector
     */
    static @NotNull Vec3I immutableFloored(double x, double y, double z) {
        return VecCache.cached((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    /**
     * Creates an immutable vector from the result of calling {@link Math#floor(double)} on each component of the
     * provided double-precision vector.
     *
     * @param other the double-precision vector
     * @return an immutable floored vector
     */
    static @NotNull Vec3I immutableFloored(@NotNull Vec3D other) {
        return immutableFloored(other.x(), other.y(), other.z());
    }

    /**
     * Base class of both mutable and immutable Vec3I implementations. Provides common vector operations as well as
     * efficient {@link Object#equals(Object)} and {@link Object#hashCode()} implementations.
     */
    abstract sealed class Base implements Vec3I permits Immutable, Mutable, View {
        private static final int HASH_PRIME = 31;

        @Override
        public final int hashCode() {
            int result = HASH_PRIME + x();
            result = HASH_PRIME * result + y();
            result = HASH_PRIME * result + z();
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
                return x() == other.x() && y() == other.y() && z() == other.z();
            }

            return false;
        }

        @Override
        public final int compareTo(@NotNull Vec3I o) {
            int xComp = Integer.compare(x(), o.x());
            if (xComp == 0) {
                int yComp = Integer.compare(y(), o.y());
                if (yComp == 0) {
                    return Integer.compare(z(), o.z());
                }

                return yComp;
            }

            return xComp;
        }

        @Override
        public @NotNull Vec3I add(int x, int y, int z) {
            return op(x() + x, y() + y, z() + z);
        }

        @Override
        public @NotNull Vec3I sub(int x, int y, int z) {
            return op(x() - x, y() - y, z() - z);
        }

        @Override
        public @NotNull Vec3I mul(int x, int y, int z) {
            return op(x() * x, y() * y, z() * z);
        }

        @Override
        public @NotNull Vec3I div(int x, int y, int z) {
            return op(x() / x, y() / y, z() / z);
        }

        @Override
        public double length() {
            return Math.sqrt(lengthSquared());
        }

        @Override
        public double lengthSquared() {
            int x = x();
            int y = y();
            int z = z();

            return x * x + y * y + z * z;
        }

        @Override
        public double distanceTo(int x, int y, int z) {
            return Math.sqrt(distanceSquaredTo(x, y, z));
        }

        @Override
        public double distanceSquaredTo(int x, int y, int z) {
            int dX = x - x();
            int dY = x - y();
            int dZ = x - z();

            return dX * dX + dY * dY + dZ * dZ;
        }

        @Override
        public int dot(int x, int y, int z) {
            return x * x() + y * y() + z * z();
        }

        @Override
        public @NotNull Vec3I cross(int x, int y, int z) {
            int oX = x();
            int oY = y();
            int oZ = z();

            return op(oY * z - oZ * y, oZ * x - oX * z, oX * y - oY * x);
        }

        @Override
        public @NotNull Vec3I mutableCopy() {
            return new Mutable(x(), y(), z());
        }

        /**
         * Performs an operation on this vector. This may either create and return a new vector, or modify and return
         * the current vector.
         *
         * @param x the x-coordinate
         * @param y the y-coordinate
         * @param z the z-coordinate
         * @return this vector if mutable, or a new immutable vector
         */
        protected abstract @NotNull Vec3I op(int x, int y, int z);
    }

    /**
     * An immutable view of a mutable vector.
     */
    final class View extends Base {
        private final Mutable target;

        private View(Mutable target) {
            this.target = target;
        }

        @Override
        public int x() {
            return target.x;
        }

        @Override
        public int y() {
            return target.y;
        }

        @Override
        public int z() {
            return target.z;
        }

        @Override
        public @NotNull Vec3I immutable() {
            return VecCache.cached(target.x, target.y, target.z);
        }

        @Override
        public @NotNull Vec3I immutableView() {
            return this;
        }

        @Override
        protected @NotNull Vec3I op(int x, int y, int z) {
            return VecCache.cached(x, y, z);
        }

        @Override
        public String toString() {
            return "Vec3I.View{target=" + target + "}";
        }
    }

    /**
     * The immutable vector type.
     */
    final class Immutable extends Base {
        private final int x;
        private final int y;
        private final int z;

        /**
         * Constructs a new immutable vector.
         *
         * @param x the x-component
         * @param y the y-component
         * @param z the z-component
         */
        Immutable(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int x() {
            return x;
        }

        @Override
        public int y() {
            return y;
        }

        @Override
        public int z() {
            return z;
        }

        @Override
        public @NotNull Vec3I immutable() {
            return this;
        }

        @Override
        public @NotNull Vec3I immutableView() {
            return this;
        }


        @Override
        protected @NotNull Vec3I op(int x, int y, int z) {
            return VecCache.cached(x, y, z);
        }

        @Override
        public String toString() {
            return "Vec3I.Immutable{x=" + x + ", y=" + y + ", z=" + z + "}";
        }
    }

    /**
     * The mutable vector type.
     */
    final class Mutable extends Base {
        private int x;
        private int y;
        private int z;

        /**
         * Constructs a new mutable vector.
         *
         * @param x the initial x-component
         * @param y the initial y-component
         * @param z the initial z-component
         */
        Mutable(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int x() {
            return x;
        }

        @Override
        public int y() {
            return y;
        }

        @Override
        public int z() {
            return z;
        }

        @Override
        public @NotNull Vec3I immutable() {
            return VecCache.cached(x, y, z);
        }

        @Override
        public @NotNull Vec3I immutableView() {
            return new View(this);
        }

        @Override
        public @NotNull Vec3I set(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        @Override
        public @NotNull Vec3I setX(int x) {
            this.x = x;
            return this;
        }

        @Override
        public @NotNull Vec3I setY(int y) {
            this.y = y;
            return this;
        }

        @Override
        public @NotNull Vec3I setZ(int z) {
            this.z = z;
            return this;
        }

        @Override
        protected @NotNull Vec3I op(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        @Override
        public String toString() {
            return "Vec3I.Mutable{x=" + x + ", y=" + y + ", z=" + z + "}";
        }
    }
}