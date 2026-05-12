package ru.nsu.gstubarev.dsl.outputs;

import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.dataClasses.ReportData;
import ru.nsu.gstubarev.dsl.services.ReportDataService;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class for html generation.
 */
public class HtmlGenerator implements ReportGenerator {
    private final ReportDataService dataService = new ReportDataService();

    @Override
    public void gen(Config config, String outputPath) {
        ReportData data = dataService.collect(config);

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>")
                .append("body { font-family: Arial, sans-serif; margin: 20px; }")
                .append("table { border-collapse: collapse; width: 100%; margin-bottom: 30px; }")
                .append("th, td { border: 1px solid #aaa; padding: 8px; text-align: center; }")
                .append("th { background-color: #f2f2f2; }")
                .append("</style></head><body><h1>Результаты (oop-checker)</h1>");

        for (ReportData.GroupData group : data.groups()) {
            html.append("<h2>Группа ").append(group.groupName()).append("</h2>");
            for (ReportData.TaskTable taskTable : group.taskTables()) {
                html.append("<h3>Лабораторная ").append(taskTable.taskName()).append("</h3>");
                html.append("<table><tr><th>Студент</th><th>Сборка</th><th>Документация</th>")
                        .append("<th>Style</th><th>Тесты</th><th>Доп. " +
                                "балл</th><th>Общий балл</th></tr>");

                for (ReportData.StudentRow row : taskTable.rows()) {
                    html.append("<tr><td>").append(row.fio()).append("</td>")
                            .append("<td>").append(row.build()).append("</td>")
                            .append("<td>").append(row.docs()).append("</td>")
                            .append("<td>").append(row.style()).append("</td>")
                            .append("<td>").append(row.tests()).append("</td>")
                            .append("<td>").append(row.bonus()).append("</td>")
                            .append("<td>").append(row.total()).append("</td></tr>");
                }
                html.append("</table>");
            }

            html.append("<h3>Общая статистика группы ").append(group.groupName())
                    .append("</h3><table><tr><th>Студент</th>");
            for (String name : group.summaryTable().taskNames()) {
                html.append("<th>").append(name).append("</th>");
            }
            html.append("<th>Сумма</th><th>Активность</th><th>Оценка</th></tr>");

            for (ReportData.SummaryRow sRow : group.summaryTable().rows()) {
                html.append("<tr><td>").append(sRow.fio()).append("</td>");
                for (Integer score : sRow.scores()) {
                    html.append("<td>").append(score).append("</td>");
                }
                html.append("<td>").append(sRow.totalSum()).append("</td>")
                        .append("<td>").append(sRow.activity()).append("</td>")
                        .append("<td>").append(sRow.grade()).append("</td></tr>");
            }
            html.append("</table>");
        }

        html.append("</body></html>");

        try {
            Files.writeString(Path.of(outputPath), html.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}