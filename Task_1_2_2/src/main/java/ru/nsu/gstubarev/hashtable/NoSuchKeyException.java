package ru.nsu.gstubarev.hashtable;

/**
 * Exception thrown when key don`t exist.
 */
public class NoSuchKeyException extends RuntimeException {
    /**
     * Constructs a new NoSuchKeyException with the specified detail message.
     *
     * @param key
     */
    public NoSuchKeyException(String key) {
        super("Нет такого ключа" + key);
    }
}
