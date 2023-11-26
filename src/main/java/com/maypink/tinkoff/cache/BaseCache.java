package com.maypink.tinkoff.cache;

import java.util.Optional;

public interface BaseCache<K, V> {
    boolean put(K key, V value);

    Optional<V> get(K key);

    int size();

    public void setSize(int size);

    boolean isEmpty();

    void clear();
}