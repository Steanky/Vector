package com.github.steanky.vector;

@FunctionalInterface
public interface Vec3IBiFunction<T, R> {
    R apply(int x, int y, int z, T t);
}
