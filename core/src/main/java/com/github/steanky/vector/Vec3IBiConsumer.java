package com.github.steanky.vector;

public interface Vec3IBiConsumer<T> {
    void accept(int x, int y, int z, T t);
}
