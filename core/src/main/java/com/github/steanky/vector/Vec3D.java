package com.github.steanky.vector;

import org.jetbrains.annotations.NotNull;

/**
 * A representation of a three-dimensional mathematical vector with double precision components. Supports both mutable
 * and immutable variants. Immutable vectors disallow all {@code set} operations.
 * <p>
 * Other mutating operations, such as {@link Vec3D#add(double, double, double)}, are mutating if the vector is mutable,
 * but will otherwise create and return a new immutable vector.
 * <p>
 * Implementations of this interface can be obtained by calling {@link Vec3D#immutable(double, double, double)} or
 * {@link Vec3D#mutable(double, double, double)}.
 */
public sealed interface Vec3D extends Comparable<Vec3D> permits Vec3D.Base {
    /**
     * The immutable origin vector (0, 0, 0).
     */
    Vec3D ORIGIN = Vec3D.immutable(0, 0, 0);

    /**
     * The x-component of this vector.
     * @return the x-component of this vector
     */
    double x();

    /**
     * The y-component of this vector.
     * @return the y-component of this vector
     */
    double y();

    /**
     * The z-component of this vector.
     * @return the z-component of this vector
     */
    double z();

    /**
     * Adds a vector to this one.
     *
     * @param x the x-component to add
     * @param y the y-component to add
     * @param z the z-component to add
     * @return the sum of this vector and the provided coordinates
     */
    @NotNull Vec3D add(double x, double y, double z);

    /**
     * Adds a vector to this one.
     *
     * @param other the vector to add
     * @return the sum of this vector and another
     */
    default @NotNull Vec3D add(@NotNull Vec3D other) {
        return add(other.x(), other.y(), other.z());
    }

    /**
     * Adds the same value to each of this vector's components.
     *
     * @param c the value to add
     * @return a vector made from adding the given value to each component of this vector
     */
    default @NotNull Vec3D add(double c) {
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
    @NotNull Vec3D sub(double x, double y, double z);

    /**
     * Subtracts another vector from this one.
     *
     * @param other the vector to subtract from this one
     * @return the difference between this vector and another
     */
    default @NotNull Vec3D sub(@NotNull Vec3D other) {
        return sub(other.x(), other.y(), other.z());
    }

    /**
     * Subtracts the same value from each of this vector's components.
     *
     * @param c the value to subtract
     * @return a vector made from subtracting the given value from each component of this vector
     */
    default @NotNull Vec3D sub(double c) {
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
    @NotNull Vec3D mul(double x, double y, double z);

    /**
     * Multiplies this vector by another. This is performed by multiplying each component of this vector by each
     * equivalent component of the given vector.
     *
     * @param other the vector to multiply with this one
     * @return the product of this vector and another
     */
    default @NotNull Vec3D mul(@NotNull Vec3D other) {
        return mul(other.x(), other.y(),other.z());
    }

    /**
     * Multiplies this vector by a scalar.
     *
     * @param c the scalar to multiply by
     * @return the product of this vector and a scalar
     */
    default @NotNull Vec3D mul(double c) {
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
    @NotNull Vec3D div(double x, double y, double z);

    /**
     * Divides this vector by another. This is performed by dividing each component of this vector by the equivalent
     * component of the given vector.
     *
     * @param other the vector to divide this one by
     * @return the quotient of this vector and another
     */
    default @NotNull Vec3D div(@NotNull Vec3D other) {
        return div(other.x(), other.y(), other.z());
    }


    /**
     * Divides this vector by a scalar.
     *
     * @param c the scalar to divide by
     * @return the quotient of this vector and the scalar
     */
    default @NotNull Vec3D div(double c) {
        return div(c, c, c);
    }

    /**
     * The length of this vector (distance from the origin). Expected to be slower than {@link Vec3D#lengthSquared()}
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
    double distanceTo(double x, double y, double z);

    /**
     * Computes the distance between this vector and another.
     *
     * @param other the other vector
     * @return the distance between this vector and the other
     */
    default double distanceTo(@NotNull Vec3D other) {
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
    double distanceSquaredTo(double x, double y, double z);

    /**
     * Computes the squared distance between this vector and another.
     *
     * @param other the other vector
     * @return the squared distance between this vector and the other
     */
    default double distanceSquaredTo(@NotNull Vec3D other) {
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
    double dot(double x, double y, double z);

    /**
     * Returns the dot product of this vector and another.
     *
     * @param other the other vector
     * @return the dot product of this vector and another
     */
    default double dot(@NotNull Vec3D other) {
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
    @NotNull Vec3D cross(double x, double y, double z);

    /**
     * Returns the cross product of this vector and another.
     *
     * @param other the other vector
     * @return the cross product of this vector and another
     */
    default @NotNull Vec3D cross(@NotNull Vec3D other) {
        return cross(other.x(), other.y(), other.z());
    }

    /**
     * Creates a new, mutable copy of this vector.
     *
     * @return a new, mutable copy of this vector
     */
    @NotNull Vec3D mutableCopy();

    /**
     * If this vector is mutable, returns this vector. Otherwise, creates a new mutable vector with the same components
     * as this one, and returns it.
     *
     * @return a mutable vector with the same components as this one
     */
    default @NotNull Vec3D ensureMutable() {
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
    @NotNull Vec3D immutable();

    /**
     * Creates an immutable view of this vector. If this vector is mutable, and is modified later, the changes will be
     * visible in the returned vector.
     *
     * @return an immutable view of this vector
     */
    @NotNull Vec3D immutableView();

    /**
     * Sets the value of this vector.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     * @param z the new z-coordinate
     * @return this instance, for chaining
     */
    default @NotNull Vec3D set(double x, double y, double z) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the value of this vector.
     *
     * @param other the vector whose components will be used to set this vector's
     * @return this instance, for chaining
     */
    default @NotNull Vec3D set(@NotNull Vec3D other) {
        return set(other.x(), other.y(), other.z());
    }

    /**
     * Sets the x-coordinate of this vector.
     *
     * @param x the new x-coordinate
     * @return this instance, for chaining
     */
    default @NotNull Vec3D setX(double x) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the y-coordinate of this vector.
     *
     * @param y the new y-coordinate
     * @return this instance, for chaining
     */
    default @NotNull Vec3D setY(double y) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the z-coordinate of this vector.
     *
     * @param z the new z-coordinate
     * @return this instance, for chaining
     */
    default @NotNull Vec3D setZ(double z) {
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
    static @NotNull Vec3D threadLocal() {
        return VecCache.THREAD_LOCAL_3D.get();
    }

    /**
     * Creates a new mutable vector.
     *
     * @param x the initial x-component
     * @param y the initial y-component
     * @param z the initial z-component
     * @return a new mutable vector
     */
    static @NotNull Vec3D mutable(double x, double y, double z) {
        return new Mutable(x, y, z);
    }

    /**
     * Obtains an immutable vector for the provided coordinates.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return an immutable vector
     */
    static @NotNull Vec3D immutable(double x, double y, double z) {
        return new Immutable(x, y, z);
    }

    /**
     * Base class of both mutable and immutable Vec3D implementations. Provides common vector operations as well as
     * efficient {@link Object#equals(Object)} and {@link Object#hashCode()} implementations.
     */
    abstract sealed class Base implements Vec3D permits Immutable, Mutable, View {
        private static final int HASH_PRIME = 31;

        @Override
        public final int hashCode() {
            int result = HASH_PRIME + Double.hashCode(x());
            result = HASH_PRIME * result + Double.hashCode(y());
            result = HASH_PRIME * result + Double.hashCode(z());
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

            if (obj instanceof Vec3D other) {
                return x() == other.x() && y() == other.y() && z() == other.z();
            }

            return false;
        }

        @Override
        public final int compareTo(@NotNull Vec3D o) {
            int xComp = Double.compare(x(), o.x());
            if (xComp == 0) {
                int yComp = Double.compare(y(), o.y());
                if (yComp == 0) {
                    return Double.compare(z(), o.z());
                }

                return yComp;
            }

            return xComp;
        }

        @Override
        public @NotNull Vec3D add(double x, double y, double z) {
            return op(x() + x, y() + y, z() + z);
        }

        @Override
        public @NotNull Vec3D sub(double x, double y, double z) {
            return op(x() - x, y() - y, z() - z);
        }

        @Override
        public @NotNull Vec3D mul(double x, double y, double z) {
            return op(x() * x, y() * y, z() * z);
        }

        @Override
        public @NotNull Vec3D div(double x, double y, double z) {
            return op(x() / x, y() / y, z() / z);
        }

        @Override
        public double length() {
            return Math.sqrt(lengthSquared());
        }

        @Override
        public double lengthSquared() {
            return x() * y() * z();
        }

        @Override
        public double distanceTo(double x, double y, double z) {
            return Math.sqrt(distanceSquaredTo(x, y, z));
        }

        @Override
        public double distanceSquaredTo(double x, double y, double z) {
            double dX = x - x();
            double dY = x - y();
            double dZ = x - z();

            return dX * dX + dY * dY + dZ * dZ;
        }

        @Override
        public double dot(double x, double y, double z) {
            return x * x() + y * y() + z * z();
        }

        @Override
        public @NotNull Vec3D cross(double x, double y, double z) {
            double oX = x();
            double oY = y();
            double oZ = z();

            return op(oY * z - oZ * y, oZ * x - oX * z, oX * y - oY * x);
        }

        @Override
        public @NotNull Vec3D mutableCopy() {
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
        protected abstract @NotNull Vec3D op(double x, double y, double z);
    }

    /**
     * An immutable view of a mutable vector.
     */
    final class View extends Base {
        private final Mutable mutable;

        private View(Mutable mutable) {
            this.mutable = mutable;
        }

        @Override
        public double x() {
            return mutable.x;
        }

        @Override
        public double y() {
            return mutable.y;
        }

        @Override
        public double z() {
            return mutable.z;
        }

        @Override
        public @NotNull Vec3D immutable() {
            return new Immutable(mutable.x, mutable.y, mutable.z);
        }

        @Override
        public @NotNull Vec3D immutableView() {
            return this;
        }

        @Override
        protected @NotNull Vec3D op(double x, double y, double z) {
            return new Immutable(x, y, z);
        }
    }

    /**
     * The immutable vector type.
     */
    final class Immutable extends Base {
        private final double x;
        private final double y;
        private final double z;

        /**
         * Constructs a new immutable vector.
         *
         * @param x the x-component
         * @param y the y-component
         * @param z the z-component
         */
        Immutable(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public double x() {
            return x;
        }

        @Override
        public double y() {
            return y;
        }

        @Override
        public double z() {
            return z;
        }

        @Override
        public @NotNull Vec3D immutable() {
            return this;
        }

        @Override
        public @NotNull Vec3D immutableView() {
            return this;
        }

        @Override
        protected @NotNull Vec3D op(double x, double y, double z) {
            return new Immutable(x, y, z);
        }
    }

    /**
     * The mutable vector type.
     */
    final class Mutable extends Base {
        private double x;
        private double y;
        private double z;

        /**
         * Constructs a new mutable vector.
         *
         * @param x the initial x-component
         * @param y the initial y-component
         * @param z the initial z-component
         */
        Mutable(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public double x() {
            return x;
        }

        @Override
        public double y() {
            return y;
        }

        @Override
        public double z() {
            return z;
        }

        @Override
        public @NotNull Vec3D immutable() {
            return new Immutable(x, y, z);
        }

        @Override
        public @NotNull Vec3D immutableView() {
            return new View(this);
        }

        @Override
        public @NotNull Vec3D set(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        @Override
        public @NotNull Vec3D setX(double x) {
            this.x = x;
            return this;
        }

        @Override
        public @NotNull Vec3D setY(double y) {
            this.y = y;
            return this;
        }

        @Override
        public @NotNull Vec3D setZ(double z) {
            this.z = z;
            return this;
        }

        @Override
        protected @NotNull Vec3D op(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }
    }
}