package ru.nsu.gstubarev.hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashTableIteratorTest {

    private HashTable<String, Integer> hashTable;
    private Iterator<HashTable.Node<String, Integer>> iterator;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
        hashTable.put("a", 1);
        hashTable.put("b", 2);
        hashTable.put("c", 3);
        iterator = hashTable.iterator();
    }

    @Test
    void testHasNextOnNonEmptyTable() {
        assertTrue(iterator.hasNext());
    }

    @Test
    void testHasNextOnEmptyTable() {
        HashTable<String, Integer> emptyTable = new HashTable<>();
        Iterator<HashTable.Node<String, Integer>> emptyIterator = emptyTable.iterator();
        assertFalse(emptyIterator.hasNext());
    }

    @Test
    void testNextReturnsAllElements() {
        int count = 0;
        while (iterator.hasNext()) {
            HashTable.Node<String, Integer> node = iterator.next();
            assertTrue(node.getKey().matches("[abc]"));
            assertTrue(node.getValue() >= 1 && node.getValue() <= 3);
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    void testConcurrentModificationOnPut() {
        iterator.hasNext();

        hashTable.put("d", 4);

        assertThrows(ConcurrentModificationException.class, () -> iterator.hasNext());
        assertThrows(ConcurrentModificationException.class, () -> iterator.next());
    }

    @Test
    void testConcurrentModificationOnRemove() {
        iterator.hasNext();

        hashTable.remove("a");

        assertThrows(ConcurrentModificationException.class, () -> iterator.hasNext());
        assertThrows(ConcurrentModificationException.class, () -> iterator.next());
    }

    @Test
    void testConcurrentModificationOnUpdate() {
        iterator.hasNext();

        hashTable.put("a", 100);

        assertThrows(ConcurrentModificationException.class, () -> iterator.hasNext());
        assertThrows(ConcurrentModificationException.class, () -> iterator.next());
    }

    @Test
    void testMultipleIteratorsIndependent() {
        Iterator<HashTable.Node<String, Integer>> iterator1 = hashTable.iterator();
        Iterator<HashTable.Node<String, Integer>> iterator2 = hashTable.iterator();

        assertTrue(iterator1.hasNext());
        assertTrue(iterator2.hasNext());

        iterator1.next();
        assertTrue(iterator2.hasNext());
    }

    @Test
    void testIteratorWithCollisions() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("a", 1);
        table.put("b", 2);

        Iterator<HashTable.Node<String, Integer>> collIterator = table.iterator();
        int count = 0;
        while (collIterator.hasNext()) {
            collIterator.next();
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testIterationOrder() {
        // Should iterate through all buckets in order
        HashTable<String, Integer> table = new HashTable<>();
        table.put("first", 1);
        table.put("second", 2);
        table.put("third", 3);

        Iterator<HashTable.Node<String, Integer>> iter = table.iterator();
        int elementsFound = 0;
        while (iter.hasNext()) {
            iter.next();
            elementsFound++;
        }
        assertEquals(3, elementsFound);
    }
}