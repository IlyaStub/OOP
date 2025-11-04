package ru.nsu.gstubarev.hashtable;

public class NoSuchKeyException extends RuntimeException {
    public NoSuchKeyException(String key) {
        super("Нет такого ключа" + key);
    }
}
