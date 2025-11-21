package ru.nsu.gstubarev.hashtable;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Iterator for HashTable.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class HashTableIterator<K, V> implements Iterator<HashTable.Node<K, V>> {

    private int cur = 0;
    private Iterator<HashTable.Node<K, V>> curBucket = null;
    private HashTable<K, V> hashTable;
    private final int startModCount;

    /**
     * Constructs a new HashTableIterator for the given hash table.
     *
     * @param hashTable the hash table to iterate over
     */
    public HashTableIterator(HashTable<K, V> hashTable) {
        this.hashTable = hashTable;
        this.startModCount = hashTable.getModificationCount();
        findNextBucket();
    }

    private void findNextBucket() {
        while (cur < hashTable.getCapacity()) {
            LinkedList<HashTable.Node<K, V>> bucket = hashTable.getBucket(cur);
            if (bucket != null && !bucket.isEmpty()) {
                curBucket = bucket.iterator();
                return;
            }
            cur++;
        }
        curBucket = null;
    }

    /**
     * Checks if there are more elements to iterate over.
     *
     * @return true if there are more elements, false otherwise
     * @throws ConcurrentModificationException if the hash table was modified during iteration
     */
    @Override
    public boolean hasNext() {
        if (hashTable.getModificationCount() != startModCount) {
            throw new ConcurrentModificationException();
        }
        if (curBucket != null && curBucket.hasNext()) {
            return true;
        }
        if (curBucket == null) {
            return false;
        }

        cur++;
        findNextBucket();
        return curBucket != null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next Node
     * @throws ConcurrentModificationException if the hash table was modified during iteration
     */
    @Override
    public HashTable.Node<K, V> next() {
        if (hashTable.getModificationCount() != startModCount) {
            throw new ConcurrentModificationException();
        }
        return curBucket.next();
    }
}