package ru.nsu.gstubarev.dsl.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.gstubarev.dsl.dataclasses.CheckResult;
import ru.nsu.gstubarev.dsl.dataclasses.Checkpoint;
import ru.nsu.gstubarev.dsl.dataclasses.Config;
import ru.nsu.gstubarev.dsl.dataclasses.Group;
import ru.nsu.gstubarev.dsl.dataclasses.ReportData;
import ru.nsu.gstubarev.dsl.dataclasses.ReportData.GroupData;
import ru.nsu.gstubarev.dsl.dataclasses.ReportData.StudentRow;
import ru.nsu.gstubarev.dsl.dataclasses.ReportData.SummaryRow;
import ru.nsu.gstubarev.dsl.dataclasses.ReportData.SummaryTable;
import ru.nsu.gstubarev.dsl.dataclasses.ReportData.TaskTable;
import ru.nsu.gstubarev.dsl.dataclasses.Student;
import ru.nsu.gstubarev.dsl.dataclasses.Task;

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
                    rows.add(new StudentRow(student.getFio(),
                            (res != null && res.compiled) ? "+" : "-",
                            (res != null && res.docsGen) ? "+" : "-",
                            (res != null && res.withoutReviewDogs) ? "+" : "-",
                            (res != null) ? res.getTestsString() : "0/0/0",
                            config.getBonus(student.getNameGit(), taskId),
                            (res != null) ? res.finalScore : 0));
                }
                taskTables.add(new TaskTable(task.getName(), rows));
            }

            List<SummaryTable> summaries = new ArrayList<>();

            if (config.getCheckpoints() != null && !config.getCheckpoints().isEmpty()) {
                for (Checkpoint cp : config.getCheckpoints()) {
                    summaries.add(prepareSummaryForCheckpoint(group, checkedTaskIds, config, cp));
                }
            } else {
                summaries.add(prepareSummaryForCheckpoint(group, checkedTaskIds, config, null));
            }

            groups.add(new GroupData(group.getName(), taskTables, summaries));
        }

        return new ReportData(groups);
    }

    private SummaryTable prepareSummaryForCheckpoint(Group group,
                                                     List<Long> taskIds,
                                                     Config config,
                                                     Checkpoint checkpoint) {
        List<String> taskNames = new ArrayList<>();
        int maxPossibleSumForCheckpoint = 0;

        for (Long id : taskIds) {
            Task t = config.getTaskById(id);
            if (t != null) {
                taskNames.add(t.getName());

                if (checkpoint != null) {
                    LocalDate hardDeadline = LocalDate.parse(t.getHardDeadline().toString());
                    if (!hardDeadline.isAfter(checkpoint.getDate())) {
                        maxPossibleSumForCheckpoint += t.getMaxScores();
                    }
                } else {
                    maxPossibleSumForCheckpoint += t.getMaxScores();
                }
            }
        }

        List<SummaryRow> rows = new ArrayList<>();
        for (Student student : group.getStudents()) {
            List<Integer> scores = new ArrayList<>();
            int totalSum = 0;
            int sumForCheckpoint = 0;
            int rawActiveWeeks = 0;

            for (Long id : taskIds) {
                CheckResult r = student.getResult(id);
                int score = (r != null) ? r.finalScore : 0;
                scores.add(score);
                totalSum += score;

                if (r != null) {
                    rawActiveWeeks = r.activeWeeks;
                }
                Task t = config.getTaskById(id);
                if (t != null && checkpoint != null) {
                    LocalDate hardDeadline = LocalDate.parse(t.getHardDeadline().toString());
                    if (!hardDeadline.isAfter(checkpoint.getDate())) {
                        sumForCheckpoint += score;
                    }
                } else {
                    sumForCheckpoint += score;
                }
            }

            int expectedWeeks = 6;
            int activityPercentage = (int) ((((double) rawActiveWeeks) / expectedWeeks) * 100);
            if (activityPercentage > 100) {
                activityPercentage = 100;
            }
            String activityStr = activityPercentage + "%";

            String grade;
            if (maxPossibleSumForCheckpoint == 0) {
                grade = "нет лаб для аттестации";
            } else {
                double successRate = (double) sumForCheckpoint / maxPossibleSumForCheckpoint;

                if (successRate >= 0.85) {
                    grade = "5";
                } else if (successRate >= 0.70) {
                    grade = "4";
                } else if (successRate >= 0.50) {
                    grade = "3";
                } else {
                    grade = "2";
                }
            }
            String fio = student.getFio();
            rows.add(new SummaryRow(fio, scores, totalSum, activityStr, grade));
        }

        String title = (checkpoint != null) ? checkpoint.getName() : "Общая статистика";
        return new SummaryTable(title, taskNames, rows);
    }
}
