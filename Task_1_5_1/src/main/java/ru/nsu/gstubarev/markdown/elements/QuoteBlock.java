package ru.nsu.gstubarev.markdown.elements;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.gstubarev.markdown.Element;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;

/**
 * Represents a block quote in Markdown.
 */
public class QuoteBlock implements Element {
    private final List<String> lines;

    /**
     * Creates a quote block from text.
     *
     * @param text the quote text
     */
    public QuoteBlock(String text) {
        this.lines = new ArrayList<>();
        addLines(text);
    }

    /**
     * Creates a quote block from list of lines.
     *
     * @param lines list of quote lines
     */
    public QuoteBlock(List<String> lines) {
        this.lines = new ArrayList<>(lines);
    }

    private void addLines(String text) {
        if (text != null) {
            String[] splitLines = text.split("\n");
            for (String line : splitLines) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        }
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append("> ").append(line).append("\n");
        }
        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QuoteBlock)) {
            return false;
        }
        QuoteBlock other = (QuoteBlock) obj;
        return lines.equals(other.lines);
    }

    /**
     * Creates a builder for QuoteBlock.
     *
     * @return new builder instance
     */
    public static QuoteBuilder builder() {
        return new QuoteBuilder();
    }

    /**
     * Builder for QuoteBlock.
     */
    public static class QuoteBuilder {
        private final List<String> lines = new ArrayList<>();

        /**
         * Adds a line to the quote.
         *
         * @param line the line to add
         * @return this builder
         */
        public QuoteBuilder addLine(String line) {
            if (line != null && !line.trim().isEmpty()) {
                lines.add(line.trim());
            }
            return this;
        }

        /**
         * Adds multiple lines to the quote.
         *
         * @param lines lines to add
         * @return this builder
         */
        public QuoteBuilder addLines(String... lines) {
            for (String line : lines) {
                addLine(line);
            }
            return this;
        }

        /**
         * Builds the QuoteBlock.
         *
         * @return new QuoteBlock instance
         */
        public QuoteBlock build() {
            if (lines.isEmpty()) {
                throw new EmptyElementException("BlockQuote must have at least one line");
            }
            return new QuoteBlock(lines);
        }
    }
}