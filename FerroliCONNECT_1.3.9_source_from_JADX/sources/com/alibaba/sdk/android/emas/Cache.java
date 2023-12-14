package com.alibaba.sdk.android.emas;

public interface Cache<T> {
    void add(T t);

    void clear();

    T get();

    boolean remove(T t);
}
