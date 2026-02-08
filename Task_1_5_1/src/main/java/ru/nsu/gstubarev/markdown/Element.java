package ru.nsu.gstubarev.markdown;

/**
 * Base class for all Markdown elements.
 */
public interface Element {
    /**
     * Compares this element with another object for equality.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal
     */
    public abstract boolean equals(Object obj);

    /**
     * Converts the element to Markdown string.
     *
     * @return Markdown representation
     */
    public abstract String serialize();
}