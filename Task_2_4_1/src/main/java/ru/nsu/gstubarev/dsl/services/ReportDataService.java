package ru.nsu.gstubarev.dsl.services;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.dataClasses.CheckResult;
import ru.nsu.gstubarev.dsl.dataClasses.Group;
import ru.nsu.gstubarev.dsl.dataClasses.ReportData;
import ru.nsu.gstubarev.dsl.dataClasses.ReportData.GroupData;
import ru.nsu.gstubarev.dsl.dataClasses.ReportData.SummaryRow;
import ru.nsu.gstubarev.dsl.dataClasses.ReportData.SummaryTable;
import ru.nsu.gstubarev.dsl.dataClasses.ReportData.StudentRow;
import ru.nsu.gstubarev.dsl.dataClasses.ReportData.TaskTable;
import ru.nsu.gstubarev.dsl.dataClasses.Student;
import ru.nsu.gstubarev.dsl.dataClasses.Task;

/**
 * Service for collecting and building report data.
 */
public class ReportDataService {

    /**
     * Collects report data from configuration.
     */
    public ReportData collect(Config config) {
        List<GroupData> groups = new ArrayList<>();

        for (Group group : config.getGroups()) {
            List<Long> checkedTaskIds = config.getChecks().get(group.getName());
            if (checkedTaskIds == null) {
                continue;
            }

            List<TaskTable> taskTables = new ArrayList<>();
            for (Long taskId : checkedTaskIds) {
                Task task = config.getTaskById(taskId);
                if (task == null) {
                    continue;
                }

                List<StudentRow> rows = new ArrayList<>();
                for (Student student : group.getStudents()) {
                    CheckResult res = student.getResult(taskId);

                    String fio = student.getFio();
                    String build = (res != null && res.compiled) ? "+" : "-";
                    String docs = (res != null && res.docsGen) ? "+" : "-";
                    String style = (res != null && res.withoutReviewDogs) ? "+" : "-";
                    String tests = (res != null) ? res.getTestsString() : "0/0/0";
                    int bonus = config.getBonus(student.getNameGit(), taskId);
                    int total = (res != null) ? res.finalScore : 0;

                    rows.add(new StudentRow(fio, build, docs, style, tests, bonus, total));
                }
                taskTables.add(new TaskTable(task.getName(), rows));
            }

            SummaryTable summaryTable = prepareSummary(group, checkedTaskIds, config);
            groups.add(new GroupData(group.getName(), taskTables, summaryTable));
        }

        return new ReportData(groups);
    }

    private SummaryTable prepareSummary(Group group, List<Long> taskIds, Config config) {
        List<String> taskNames = new ArrayList<>();
        for (Long id : taskIds) {
            taskNames.add(config.getTaskById(id).getName());
        }

        List<SummaryRow> rows = new ArrayList<>();
        for (Student student : group.getStudents()) {
            String fio = student.getFio();
            List<Integer> scores = new ArrayList<>();
            int sum = 0;
            for (Long id : taskIds) {
                CheckResult r = student.getResult(id);
                int score = (r != null) ? r.finalScore : 0;
                scores.add(score);
                sum += score;
            }
            rows.add(new SummaryRow(fio, scores, sum, "?%", "-"));
        }

        return new SummaryTable(taskNames, rows);
    }
}