package ru.nsu.gstubarev.hashtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HashTable<K, V> implements MyMap<K, V> {

    static class Node<K, V> {
        final int hash;
        final K key;
        private V value;

        Node(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            return o instanceof Node<?, ?> e
                    && Objects.equals(key, e.getKey())
                    && Objects.equals(value, e.getValue());
        }
    }

    private ArrayList<Node<K, V>> table = new ArrayList<>();

    private int size;

    public int getSize() {
        return size;
    }

    public HashTable() {
        this(100);
    }

    public HashTable(int initialCapacity) {

    }

    private int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public void remove(K key) {

    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void update(K key) {

    }

    @Override
    public void iteration() {

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "";
    }
}
