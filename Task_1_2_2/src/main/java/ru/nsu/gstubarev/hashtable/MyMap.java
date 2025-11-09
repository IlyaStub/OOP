package ru.nsu.gstubarev.hashtable;

import java.util.Iterator;

/**
 * The Interface looks like minimal version of Map.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public interface MyMap<K, V> extends Iterable<HashTable.Node<K, V>>{
    /**
     * Put value to HashTable by key.
     *
     * @param key the key
     * @param value the value
     * @return Old value of by key if it was else null
     */
    V put(K key, V value);

    /**
     * Remove value from HashTable by key.
     *
     * @param key the key
     * @return removed value
     */
    V remove(K key);

    /**
     * Get value by key.
     *
     * @param key the key
     * @return value by key
     */
    V get(K key);

    /**
     * Change value in HashTable by key.
     *
     * @param key the key
     * @param value the value
     */
    void update(K key, V value);
}