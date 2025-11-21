package ru.nsu.gstubarev.hashtable;

/**
 * The Main class.
 */
public class Main {
    /**
     * Main method to demonstrate HashTable functionality.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        HashTable<String, Number> table = new HashTable<>();
        table.put("Fer", 1);
        table.put("Fer", 2);
        table.put("Tr", 3);
        table.put("fda", 3.31);

        System.out.println(table.get("Tr"));
        System.out.println(table.get("Fer"));
        System.out.println(table.get("fda"));
        System.out.println(table);
    }
}