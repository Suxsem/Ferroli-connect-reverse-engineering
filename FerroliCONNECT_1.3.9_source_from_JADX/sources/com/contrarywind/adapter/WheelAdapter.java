package com.contrarywind.adapter;

public interface WheelAdapter<T> {
    T getItem(int i);

    int getItemsCount();

    int indexOf(T t);
}
