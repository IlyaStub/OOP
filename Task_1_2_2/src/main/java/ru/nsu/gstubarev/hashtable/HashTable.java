package ru.nsu.gstubarev.hashtable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Class based implementation of the MyMap interface.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class HashTable<K, V> implements MyMap<K, V> {

    private static final int STANDARD_CAPACITY = 100;

    /**
     * Class for key-value Node.
     *
     * @param <K> the type of keys maintained by this node
     * @param <V> the type of mapped values
     */
    static class Node<K, V> {
        final K key;
        private V value;

        /**
         * Just constructor for Node.
         *
         * @param key the key
         * @param value the value
         */
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Getter for key.
         *
         * @return key type of K
         */
        public final K getKey() {
            return key;
        }

        /**
         * Getter for value.
         *
         * @return value type of V
         */
        public final V getValue() {
            return value;
        }

        /**
         * Override method toString.
         *
         * @return string "key=value"
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }

        /**
         * Override method hashCode for Node.
         *
         * @return hash for Node
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        /**
         * Setter for value in Node.
         *
         * @param newValue (new value)
         * @return previous value of this Node
         */
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        /**
         * Override method equals for Node.
         *
         * @param o type of Object
         * @return true if equals else false
         */
        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            return o instanceof Node<?, ?> e && Objects.equals(key, e.getKey())
                    && Objects.equals(value, e.getValue());
        }
    }

    private ArrayList<LinkedList<Node<K, V>>> table;

    private final int capacity;

    private int size;

    private int modificationCount = 0;

    /**
     * Getter for int size.
     *
     * @return current size of ArrayList with HashTable
     */
    public int getSize() {
        return size;
    }

    /**
     * Getter for int capacity.
     *
     * @return current capacity of ArrayList with HashTable
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Getter for int modificationCount.
     *
     * @return current count of modification in ArrayList
     */
    public int getModificationCount() {
        return modificationCount;
    }

    /**
     * Constructor for HashTable.
     */
    public HashTable() {
        this.capacity = STANDARD_CAPACITY;
        this.table = new ArrayList<>(this.capacity);
        this.size = 0;
        for (int i = 0; i < capacity; i++) {
            table.add(null);
        }
    }

    private int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private int getIndexByHash(int h) {
        return (this.capacity - 1) & h;
    }

    @Override
    public V put(K key, V value) {
        int index = getIndexByHash(hash(key));

        LinkedList<Node<K, V>> bucket = table.get(index);

        if (bucket == null) {
            bucket = new LinkedList<>();
            table.set(index, bucket);
        }
        modificationCount++;

        for (Node<K, V> node : bucket) {
            if (Objects.equals(node.key, key)) {
                return node.setValue(value);
            }
        }

        bucket.add(new Node<>(key, value));
        this.size++;
        return null;
    }

    @Override
    public V remove(K key) {
        int index = getIndexByHash(hash(key));

        LinkedList<Node<K, V>> bucket = table.get(index);

        if (bucket == null) {
            return null;
        }

        Iterator<Node<K, V>> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Node<K, V> node = iterator.next();
            if (Objects.equals(key, node.key)) {
                modificationCount++;
                V value = node.value;
                iterator.remove();
                this.size--;
                return value;
            }
        }

        return null;
    }

    @Override
    public V get(K key) {
        int index = getIndexByHash(hash(key));

        LinkedList<Node<K, V>> bucket = table.get(index);

        if (bucket == null) {
            return null;
        }

        for (Node<K, V> node : bucket) {
            if (Objects.equals(key, node.key)) {
                return node.getValue();
            }
        }

        return null;
    }

    @Override
    public void update(K key, V value) {
        int index = getIndexByHash(hash(key));
        LinkedList<Node<K, V>> bucket = table.get(index);

        if (bucket == null) {
            throw new NoSuchKeyException(key.toString());
        }

        for (Node<K, V> node : bucket) {
            if (Objects.equals(key, node.key)) {
                node.value = value;
                return;
            }
        }

        throw new NoSuchKeyException(key.toString());
    }

    /**
     * Method for return bucket by index.
     *
     * @param index int value for index bucket in ArrayList
     * @return bucket by index
     */
    LinkedList<Node<K, V>> getBucket(int index) {
        return table.get(index);
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        return new HashTableIterator<>(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HashTable<?, ?> other = (HashTable<?, ?>) obj;

        if (other.capacity != this.capacity) {
            return false;
        }

        for (int i = 0; i < table.size(); i++) {
            LinkedList<Node<K, V>> bucket1 = table.get(i);
            LinkedList<?> bucket2 = other.table.get(i);

            if (bucket1 == null && bucket2 == null) {
                continue;
            }
            if (bucket1 == null || bucket2 == null) {
                return false;
            }
            if (bucket1.size() != bucket2.size()) {
                return false;
            }

            for (Node<K, V> node1 : bucket1) {
                boolean found = false;
                for (Object node2 : bucket2) {
                    if (Objects.equals(node1, node2)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (LinkedList<Node<K, V>> bucket : table) {
            if (bucket != null) {
                hash += bucket.hashCode();
            }
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HashTable{");
        sb.append("size=").append(capacity).append(", ");
        sb.append("elements=[");

        boolean first = true;
        for (LinkedList<Node<K, V>> bucket : table) {
            if (bucket != null && !bucket.isEmpty()) {
                for (Node<K, V> node : bucket) {
                    if (!first) {
                        sb.append(", ");
                    }
                    sb.append(node.toString());
                    first = false;
                }
            }
        }

        sb.append("]}");
        return sb.toString();
    }
}