package ru.nsu.gstubarev.dsl.dataClasses;

import java.util.List;

/**
 * Root container for report results.
 */
public record ReportData(List<GroupData> groups) {

    /**
     * Group data with tasks and summary.
     */
    public record GroupData(
            String groupName,
            List<TaskTable> taskTables,
            SummaryTable summaryTable
    ) {}

    /**
     * Task evaluation table.
     */
    public record TaskTable(
            String taskName,
            List<StudentRow> rows
    ) {}

    /**
     * Student assessment details.
     */
    public record StudentRow(
            String fio,
            String build,
            String docs,
            String style,
            String tests,
            int bonus,
            int total
    ) {}

    /**
     * Aggregated results summary.
     */
    public record SummaryTable(
            List<String> taskNames,
            List<SummaryRow> rows
    ) {}

    /**
     * Student summary row.
     */
    public record SummaryRow(
            String fio,
            List<Integer> scores,
            int totalSum,
            String activity,
            String grade
    ) {}
}