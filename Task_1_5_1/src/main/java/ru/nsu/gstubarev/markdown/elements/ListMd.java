package ru.nsu.gstubarev.markdown.elements;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.gstubarev.markdown.Element;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;
import ru.nsu.gstubarev.markdown.exceptions.SelectionsSequenceException;

/**
 * Represents an unordered list in Markdown.
 */
public class ListMd implements Element {
    private final List<Element> items;
    private final String marker;

    private ListMd(ListBuilder builder) {
        this.items = new ArrayList<>(builder.items);
        this.marker = builder.marker;
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        for (Element item : items) {
            sb.append(marker).append(" ").append(item.serialize()).append("\n");
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
        if (!(obj instanceof ListMd)) {
            return false;
        }
        ListMd other = (ListMd) obj;
        return items.equals(other.items) && marker.equals(other.marker);
    }

    /**
     * Creates a builder for ListMd.
     *
     * @return new builder instance
     */
    public static ListBuilder builder() {
        return new ListBuilder();
    }

    /**
     * Builder for ListMd.
     */
    public static class ListBuilder {
        private final List<Element> items = new ArrayList<>();
        private String marker = "-";

        /**
         * Sets the list marker.
         *
         * @param marker list marker (-, *, or +)
         * @return this builder
         */
        public ListBuilder marker(String marker) {
            if (!"-".equals(marker) && !"*".equals(marker) && !"+".equals(marker)) {
                throw new SelectionsSequenceException("Marker must be '-', '*', or '+'");
            }
            this.marker = marker;
            return this;
        }

        /**
         * Adds an element to the list.
         *
         * @param item list item
         * @return this builder
         */
        public ListBuilder addItem(Element item) {
            if (item != null) {
                items.add(item);
            }
            return this;
        }

        /**
         * Adds multiple elements to the list.
         *
         * @param items list items
         * @return this builder
         */
        public ListBuilder addItems(Element... items) {
            for (Element item : items) {
                addItem(item);
            }
            return this;
        }

        /**
         * Adds a text item to the list.
         *
         * @param text item text
         * @return this builder
         */
        public ListBuilder addTextItem(String text) {
            return addItem(TextMd.builder(text).build());
        }

        /**
         * Adds a task item to the list.
         *
         * @param text      task description
         * @param completed completion status
         * @return this builder
         */
        public ListBuilder addTaskItem(String text, boolean completed) {
            return addItem(Task.builder(text).completed().build());
        }

        /**
         * Builds the ListMd.
         *
         * @return new ListMd instance
         */
        public ListMd build() {
            if (items.isEmpty()) {
                throw new EmptyElementException("List must contain at least one item");
            }
            return new ListMd(this);
        }
    }
}
