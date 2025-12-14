package ru.nsu.gstubarev.markdown.elements;

import ru.nsu.gstubarev.markdown.Element;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;
import ru.nsu.gstubarev.markdown.exceptions.SelectionsSequenceException;

/**
 * Represents formatted text in Markdown.
 */
public class TextMd implements Element {
    private final String content;
    private final boolean isBold;
    private final boolean isItalic;
    private final boolean isStrikethrough;
    private final boolean isCode;

    private TextMd(TextBuilder builder) {
        this.content = builder.content;
        this.isBold = builder.isBold;
        this.isItalic = builder.isItalic;
        this.isStrikethrough = builder.isStrikethrough;
        this.isCode = builder.isCode;
    }

    @Override
    public String serialize() {
        String result = content;

        if (isCode) {
            result = "`" + result + "`";
        }
        if (isStrikethrough) {
            result = "~~" + result + "~~";
        }
        if (isItalic) {
            result = "*" + result + "*";
        }
        if (isBold) {
            result = "**" + result + "**";
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextMd)) {
            return false;
        }
        TextMd other = (TextMd) obj;
        return content.equals(other.content)
                && isBold == other.isBold
                && isItalic == other.isItalic
                && isStrikethrough == other.isStrikethrough
                && isCode == other.isCode;
    }

    /**
     * Creates a builder for TextMd.
     *
     * @param content the text content
     * @return new builder instance
     */
    public static TextBuilder builder(String content) {
        return new TextBuilder(content);
    }

    /**
     * Builder for TextMd.
     */
    public static class TextBuilder {
        private final String content;
        private boolean isBold = false;
        private boolean isItalic = false;
        private boolean isStrikethrough = false;
        private boolean isCode = false;

        /**
         * Creates a new TextBuilder.
         *
         * @param content the text content
         */
        public TextBuilder(String content) {
            this.content = content;
        }

        /**
         * Makes the text bold.
         *
         * @return this builder
         */
        public TextBuilder bold() {
            this.isBold = true;
            return this;
        }

        /**
         * Makes the text italic.
         *
         * @return this builder
         */
        public TextBuilder italic() {
            this.isItalic = true;
            return this;
        }

        /**
         * Makes the text strikethrough.
         *
         * @return this builder
         */
        public TextBuilder strikethrough() {
            this.isStrikethrough = true;
            return this;
        }

        /**
         * Formats text as inline code.
         *
         * @return this builder
         */
        public TextBuilder code() {
            this.isCode = true;
            return this;
        }

        private void validate() {
            if (content == null || content.trim().isEmpty()) {
                throw new EmptyElementException("Text content cannot be empty");
            }

            if (isCode && isStrikethrough) {
                throw new SelectionsSequenceException("Code cannot be strikethrough in Markdown");
            }
        }

        /**
         * Builds the TextMd element.
         *
         * @return new TextMd instance
         */
        public TextMd build() {
            validate();
            return new TextMd(this);
        }
    }
}