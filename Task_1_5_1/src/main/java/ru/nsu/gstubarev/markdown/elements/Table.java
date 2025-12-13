package ru.nsu.gstubarev.markdown.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.nsu.gstubarev.markdown.Element;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;
import ru.nsu.gstubarev.markdown.exceptions.SelectionsSequenceException;

/**
 * Represents a table in Markdown.
 */
public class Table extends Element {
    /**
     * Left alignment constant.
     */
    public static final String ALIGN_LEFT = "left";
    /**
     * Center alignment constant.
     */
    public static final String ALIGN_CENTER = "center";
    /**
     * Right alignment constant.
     */
    public static final String ALIGN_RIGHT = "right";

    private final List<List<Element>> rows;
    private final List<String> alignments;
    private final int rowLimit;

    private Table(TableBuilder builder) {
        this.rows = new ArrayList<>(builder.rows);
        this.alignments = new ArrayList<>(builder.alignments);
        this.rowLimit = builder.rowLimit;
    }

    @Override
    public String serialize() {
        if (rows.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("|");
        for (Element cell : rows.get(0)) {
            sb.append(" ").append(cell.serialize()).append(" |");
        }
        sb.append("\n");

        sb.append("|");
        int columns = rows.get(0).size();
        for (int i = 0; i < columns; i++) {
            String align = (i < alignments.size()) ? alignments.get(i) : ALIGN_LEFT;
            switch (align) {
                case ALIGN_LEFT -> sb.append(" :--- |");
                case ALIGN_CENTER -> sb.append(" :---: |");
                case ALIGN_RIGHT -> sb.append(" ---: |");
                default -> sb.append(" --- |");
            }
        }
        sb.append("\n");

        for (int i = 1; i < rows.size() && i <= rowLimit + 1; i++) {
            sb.append("|");
            for (Element cell : rows.get(i)) {
                sb.append(" ").append(cell.serialize()).append(" |");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Table)) {
            return false;
        }
        Table other = (Table) obj;
        return rows.equals(other.rows)
                && alignments.equals(other.alignments)
                && rowLimit == other.rowLimit;
    }

    /**
     * Creates a builder for Table.
     *
     * @return new builder instance
     */
    public static TableBuilder builder() {
        return new TableBuilder();
    }

    /**
     * Builder for Table.
     */
    public static class TableBuilder {
        private final List<List<Element>> rows = new ArrayList<>();
        private final List<String> alignments = new ArrayList<>();
        private int rowLimit = Integer.MAX_VALUE - 1;

        /**
         * Sets column alignments.
         *
         * @param alignments alignment for each column
         * @return this builder
         */
        public TableBuilder withAlignments(String... alignments) {
            this.alignments.clear();
            this.alignments.addAll(Arrays.asList(alignments));
            return this;
        }

        /**
         * Sets maximum number of data rows.
         *
         * @param limit row limit
         * @return this builder
         */
        public TableBuilder withRowLimit(int limit) {
            if (limit < 0) {
                throw new SelectionsSequenceException("Row limit cannot be negative");
            }
            this.rowLimit = limit;
            return this;
        }

        /**
         * Adds a row to the table.
         *
         * @param cells row cells
         * @return this builder
         */
        public TableBuilder addRow(Object... cells) {
            if (rows.size() >= rowLimit + 1) {
                return this;
            }

            List<Element> row = new ArrayList<>();
            for (Object cell : cells) {
                if (cell instanceof Element) {
                    row.add((Element) cell);
                } else if (cell instanceof String) {
                    row.add(TextMd.builder((String) cell).build());
                } else if (cell instanceof Number) {
                    row.add(TextMd.builder(cell.toString()).build());
                } else if (cell != null) {
                    row.add(TextMd.builder(cell.toString()).build());
                } else {
                    row.add(TextMd.builder("").build());
                }
            }
            rows.add(row);
            return this;
        }

        /**
         * Builds the Table.
         *
         * @return new Table instance
         */
        public Table build() {
            if (rows.isEmpty()) {
                throw new EmptyElementException("Table must have at least one row");
            }
            return new Table(this);
        }
    }
}