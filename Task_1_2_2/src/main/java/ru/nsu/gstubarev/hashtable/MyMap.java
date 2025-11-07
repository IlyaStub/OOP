package ru.nsu.gstubarev.hashtable;

/**
 * The Interface looks like minimal version of Map.
 *
 * @param <K>
 * @param <V>
 */
public interface MyMap<K, V> {
    /**
     * Put value to HashTable by key.
     *
     * @param key
     * @param value
     * @return Old value of by key if it was else null
     */
    V put(K key, V value);

    /**
     * Remove value from HashTable by key.
     *
     * @param key
     * @return removed value
     */
    V remove(K key);

    /**
     * Get value by key.
     *
     * @param key
     * @return value by key
     */
    V get(K key);

    /**
     * Change value in HashTable by key.
     *
     * @param key
     * @param value
     */
    void update(K key, V value);

    /**
     * Method for iteration by HashTable.
     */
    void iteration();
}
