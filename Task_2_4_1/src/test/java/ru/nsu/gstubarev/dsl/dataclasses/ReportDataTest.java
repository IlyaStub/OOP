package ru.nsu.gstubarev.dsl.dataclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import org.junit.jupiter.api.Test;

/**
 * TEST.
 */
public class ReportDataTest {

    @Test
    public void testRecordsCreationAndAccessors() {
        ReportData.StudentRow studentRow = new ReportData.StudentRow(
                "Stubarev I.D.", "+", "+", "+", "10/0/0", 5, 100
        );
        assertEquals("Stubarev I.D.", studentRow.fio());
        assertEquals(100, studentRow.total());

        ReportData.TaskTable taskTable = new ReportData.TaskTable(
                "Task 1", Collections.singletonList(studentRow)
        );
        assertEquals("Task 1", taskTable.taskName());

        ReportData.SummaryRow summaryRow = new ReportData.SummaryRow(
                "Stubarev I.D.", Collections.singletonList(10), 10, "High", "A"
        );
        assertEquals("A", summaryRow.grade());

        ReportData.SummaryTable summaryTable = new ReportData.SummaryTable(
                "CP1", Collections.singletonList("Task 1"), Collections.singletonList(summaryRow)
        );
        assertEquals("CP1", summaryTable.checkpointName());

        ReportData.GroupData groupData = new ReportData.GroupData(
                "Group-1", Collections.singletonList(taskTable),
                Collections.singletonList(summaryTable)
        );
        assertEquals("Group-1", groupData.groupName());

        ReportData reportData = new ReportData(Collections.singletonList(groupData));
        assertNotNull(reportData.groups());
        assertEquals(1, reportData.groups().size());
    }
}