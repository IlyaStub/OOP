package ru.nsu.gstubarev.hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashTableTest {

    private HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    void put() {
        assertNull(hashTable.put("k1", 100));
        assertEquals(100, hashTable.put("k1", 200));
        assertEquals(200, hashTable.get("k1"));
    }

    @Test
    void get() {
        assertNull(hashTable.get("nonexistent"));

        hashTable.put("k1", 100);
        assertEquals(100, hashTable.get("k1"));

        hashTable.put("k2", 200);
        assertEquals(200, hashTable.get("k2"));
    }

    @Test
    void remove() {
        hashTable.put("k1", 100);
        assertEquals(100, hashTable.remove("k1"));
        assertNull(hashTable.get("k1"));
        assertNull(hashTable.remove("xd"));
    }

    @Test
    void update() {
        hashTable.put("k1", 100);
        hashTable.update("k1", 300);
        assertEquals(300, hashTable.get("k1"));

        assertThrows(NoSuchKeyException.class, () -> hashTable.update("xd", 400));
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<String, Integer> table2 = new HashTable<>();

        table1.put("k1", 100);
        table1.put("k2", 200);

        table2.put("k1", 100);
        table2.put("k2", 200);

        assertTrue(table1.equals(table2));

        table2.put("k3", 300);
        assertFalse(table1.equals(table2));
    }

    @Test
    void testHashCode() {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<String, Integer> table2 = new HashTable<>();

        table1.put("k1", 100);
        table1.put("k2", 200);

        table2.put("k1", 100);
        table2.put("k2", 200);

        assertEquals(table1.hashCode(), table2.hashCode());

        table2.put("k3", 300);
        assertFalse(table1.hashCode() == table2.hashCode());
    }

    @Test
    void testToString() {
        hashTable.put("k1", 100);
        hashTable.put("k2", 200);

        String result = hashTable.toString();
        assertTrue(result.contains("k1"));
        assertTrue(result.contains("k2"));
        assertTrue(result.contains("100"));
        assertTrue(result.contains("200"));
    }

    @Test
    void getSize() {
        HashTable<String, Integer> table = new HashTable<>();
        assertEquals(table.getSize(), 0);
    }

    @Test
    void testCollisions() {
        hashTable.put("a", 1);
        hashTable.put("b", 2);
        hashTable.put("c", 3);

        assertEquals(1, hashTable.get("a"));
        assertEquals(2, hashTable.get("b"));
        assertEquals(3, hashTable.get("c"));

        assertEquals(1, hashTable.remove("a"));
        assertNull(hashTable.get("a"));
    }

    @Test
    void testNullKey() {
        hashTable.put(null, 100);
        assertEquals(100, hashTable.get(null));

        assertEquals(100, hashTable.remove(null));
        assertNull(hashTable.get(null));
    }
}