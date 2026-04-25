package ru.nsu.gstubarev.dsl.outputs;

import ru.nsu.gstubarev.dsl.dataClasses.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * To be honest, I didn't write this file myself, GPT helped me.
 */
public class HtmlGenerator implements ReportGenerator{
    public void gen(Config config, String outputPath) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Отчет по ООП</title>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; margin: 20px; }");
        html.append("h2 { color: #333; border-bottom: 2px solid #ccc; padding-bottom: 5px; }");
        html.append("table { border-collapse: collapse; width: 100%; margin-bottom: 30px; }");
        html.append("th, td { border: 1px solid #aaa; padding: 8px; text-align: center; }");
        html.append("th { background-color: #f2f2f2; font-weight: bold; }");
        html.append("td:first-child { text-align: left; font-weight: bold; }");
        html.append("</style></head><body>");

        html.append("<h1>Результаты проверки (oop-checker)</h1>");

        for (Group group : config.getGroups()) {
            html.append("<h2>Группа ").append(group.getName()).append("</h2>");
            List<Long> checkedTaskIds = config.getChecks().get(group.getName());

            if (checkedTaskIds != null) {
                for (Long taskId : checkedTaskIds) {
                    Task task = config.getTaskById(taskId);
                    if (task == null) continue;

                    html.append("<h3>Лабораторная ").append(task.getName()).append("</h3>");
                    html.append("<table>");
                    html.append("<tr><th>Студент</th><th>Сборка</th><th>Документация</th>")
                            .append("<th>Style guide</th><th>Тесты</th><th>Доп. балл</th><th>Общий балл</th></tr>");

                    for (Student student : group.getStudents()) {
                        html.append("<tr>").append("<td>").append(student.getFio()).append("</td>");

                        CheckResult result = student.getResult(taskId);
                        int bonus = config.getBonus(student.getNameGit(), taskId);

                        if (result == null) {
                            html.append("<td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>");
                        } else {
                            html.append("<td>").append(result.compiled ? "+" : "-").append("</td>")
                                    .append("<td>").append(result.docsGen ? "+" : "-").append("</td>")
                                    .append("<td>").append(result.withoutReviewDogs ? "+" : "-").append("</td>")
                                    .append("<td>").append(result.getTestsString()).append("</td>")
                                    .append("<td>").append(bonus).append("</td>")
                                    .append("<td>").append(result.finalScore).append("</td>");
                        }
                        html.append("</tr>");
                    }
                    html.append("</table>");
                }

                html.append("<h3>Общая статистика группы ").append(group.getName()).append("</h3>");
                html.append("<table><tr><th>Студент</th>");

                for (Long taskId : checkedTaskIds) {
                    html.append("<th>").append(config.getTaskById(taskId).getName()).append("</th>");
                }
                html.append("<th>Сумма</th><th>Активность</th><th>Оценка</th></tr>");

                for (Student student : group.getStudents()) {
                    html.append("<tr>").append("<td>").append(student.getFio()).append("</td>");

                    int totalScore = 0;
                    for (Long taskId : checkedTaskIds) {
                        CheckResult result = student.getResult(taskId);
                        if (result != null) {
                            html.append("<td>").append(result.finalScore).append("</td>");
                            totalScore += result.finalScore;
                        } else {
                            html.append("<td>-</td>");
                        }
                    }

                    html.append("<td>").append(totalScore).append("</td>")
                            .append("<td>?%</td>")
                            .append("<td>-</td>")
                            .append("</tr>");
                }
                html.append("</table>");
            }
        }
        html.append("</body></html>");

        try {
            Files.writeString(Path.of(outputPath), html.toString());
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении отчета: " + e.getMessage());
        }
    }
}