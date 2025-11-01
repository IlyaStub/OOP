package ru.nsu.gstubarev.hashtable;

public interface MyMap<K, V> {
    void put(K key, V value);

    void remove(K key);

    V get(K key);

    void update(K key);

    void iteration();
}
