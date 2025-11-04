package ru.nsu.gstubarev.hashtable;

public interface MyMap<K, V> {
    V put(K key, V value);

    V remove(K key);

    V get(K key);

    void update(K key, V value);

    void iteration();
}
