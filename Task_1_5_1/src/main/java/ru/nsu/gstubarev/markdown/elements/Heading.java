package ru.nsu.gstubarev.markdown.elements;

import ru.nsu.gstubarev.markdown.Element;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;
import ru.nsu.gstubarev.markdown.exceptions.SelectionsSequenceException;

/**
 * Represents a heading in Markdown.
 */
public class Heading extends Element {
    private final int level;
    private final String text;

    /**
     * Creates a new heading.
     * @param level heading level (1-6)
     * @param text heading text
     */
    public Heading(int level, String text) {
        if (level < 1 || level > 6) {
            throw new SelectionsSequenceException("Heading level must be between 1 and 6");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new EmptyElementException("Heading text cannot be empty");
        }
        this.level = level;
        this.text = text.trim();
    }

    @Override
    public String serialize() {
        String hashes = "#".repeat(level);
        return hashes + " " + text;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Heading)) {
            return false;
        }
        Heading other = (Heading) obj;
        return level == other.level && text.equals(other.text);
    }
}