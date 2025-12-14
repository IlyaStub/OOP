package ru.nsu.gstubarev.markdown.elements;

import java.util.Objects;
import ru.nsu.gstubarev.markdown.Element;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;

/**
 * Represents a code block in Markdown.
 */
public class CodeBlock implements Element {
    private final String code;
    private final String language;

    /**
     * Creates a code block without language specification.
     *
     * @param code the code content
     */
    public CodeBlock(String code) {
        this(code, null);
    }

    /**
     * Creates a code block with language.
     *
     * @param code     the code content
     * @param language programming language
     */
    public CodeBlock(String code, String language) {
        if (code == null) {
            throw new EmptyElementException("Code cannot be null");
        }
        this.code = code;
        this.language = language;
    }

    @Override
    public String serialize() {
        String lang = (language != null && !language.trim().isEmpty()) ? language.trim() : "";
        return "```" + lang + "\n" + code + "\n```";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CodeBlock)) {
            return false;
        }
        CodeBlock other = (CodeBlock) obj;
        return code.equals(other.code)
                && (Objects.equals(language, other.language));
    }

    /**
     * Creates a builder for CodeBlock.
     *
     * @return new builder instance
     */
    public static CodeBuilder builder() {
        return new CodeBuilder();
    }

    /**
     * Builder for CodeBlock.
     */
    public static class CodeBuilder {
        private final StringBuilder codeBuilder = new StringBuilder();
        private String language;

        /**
         * Sets the programming language.
         *
         * @param language programming language
         * @return this builder
         */
        public CodeBuilder language(String language) {
            this.language = language;
            return this;
        }

        /**
         * Adds a line of code.
         *
         * @param line code line
         * @return this builder
         */
        public CodeBuilder addLine(String line) {
            codeBuilder.append(line).append("\n");
            return this;
        }

        /**
         * Adds multiple lines of code.
         *
         * @param lines code lines
         * @return this builder
         */
        public CodeBuilder addLines(String... lines) {
            for (String line : lines) {
                codeBuilder.append(line).append("\n");
            }
            return this;
        }

        /**
         * Builds the CodeBlock.
         *
         * @return new CodeBlock instance
         */
        public CodeBlock build() {
            String code = codeBuilder.toString();
            if (code.endsWith("\n")) {
                code = code.substring(0, code.length() - 1);
            }
            return new CodeBlock(code, language);
        }
    }
}