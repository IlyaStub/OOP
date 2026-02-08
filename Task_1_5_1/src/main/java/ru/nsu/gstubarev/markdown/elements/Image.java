package ru.nsu.gstubarev.markdown.elements;

import java.util.Objects;
import ru.nsu.gstubarev.markdown.Element;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;

/**
 * Represents an image in Markdown.
 */
public class Image implements Element {
    private final String altText;
    private final String url;
    private final String title;

    /**
     * Creates an image with alt text and URL.
     *
     * @param altText alternative text
     * @param url     image URL
     */
    public Image(String altText, String url) {
        this(altText, url, null);
    }

    /**
     * Creates an image with alt text, URL, and title.
     *
     * @param altText alternative text
     * @param url     image URL
     * @param title   image title
     */
    public Image(String altText, String url, String title) {
        if (altText == null) {
            altText = "One second";
        }
        if (url == null || url.trim().isEmpty()) {
            throw new EmptyElementException("Image URL cannot be empty");
        }
        this.altText = altText;
        this.url = url;
        this.title = title;
    }

    @Override
    public String serialize() {
        String result = "![" + altText + "](" + url;
        if (title != null && !title.trim().isEmpty()) {
            result += " \"" + title + "\"";
        }
        result += ")";
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Image)) {
            return false;
        }
        Image other = (Image) obj;
        return altText.equals(other.altText)
                && url.equals(other.url)
                && (Objects.equals(title, other.title));
    }
}