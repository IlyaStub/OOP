package ru.nsu.gstubarev.markdown.elements;

import ru.nsu.gstubarev.markdown.Element;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;

/**
 * Represents a hyperlink in Markdown.
 */
public class Link extends Element {
    private final String text;
    private final String url;

    /**
     * Creates a new link.
     *
     * @param text link text
     * @param url link URL
     */
    public Link(String text, String url) {
        if (text == null || text.trim().isEmpty()) {
            throw new EmptyElementException("Link text cannot be empty");
        }
        if (url == null || url.trim().isEmpty()) {
            throw new EmptyElementException("URL cannot be empty");
        }
        this.text = text;
        this.url = url;
    }

    @Override
    public String serialize() {
        return "[" + text + "](" + url + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Link)) {
            return false;
        }
        Link other = (Link) obj;
        return text.equals(other.text) && url.equals(other.url);
    }
}