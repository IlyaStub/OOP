package ru.nsu.gstubarev.dsl.outputs;

import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.dataClasses.Group;
import ru.nsu.gstubarev.dsl.dataClasses.Student;
import ru.nsu.gstubarev.dsl.dataClasses.Task;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * To be honest, I didn't write this file myself, GPT helped me.
 */
public class HtmlGenerator implements ReportGenerator {
    @Override
    public void gen(Config config, String outputPath) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Отчет по ООП</title>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; margin: 20px; }");
        html.append("h2 { color: #333; border-bottom: 2px solid #ccc; padding-bottom: 5px; }");
        html.append("table { border-collapse: collapse; width: 100%; margin-bottom: 30px; }");
        html.append("th, td { border: 1px solid #aaa; padding: 8px; text-align: center; }");
        html.append("th { background-color: #f2f2f2; font-weight: bold; }");
        html.append("td:first-child { text-align: left; font-weight: bold; }"); // Имя студента слева
        html.append("</style></head><body>");

        html.append("<h1>Результаты проверки (oop-checker test)</h1>");

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
                        int bonus = config.getBonus(student.getNameGit(), taskId);

                        html.append("<tr>")
                                .append("<td>").append(student.getFio()).append("</td>")
                                .append("<td>?</td>")
                                .append("<td>?</td>")
                                .append("<td>?</td>")
                                .append("<td>?/?/?</td>")
                                .append("<td>").append(bonus).append("</td>")
                                .append("<td>?</td>")
                                .append("</tr>");
                    }
                    html.append("</table>");
                }

                html.append("<h3>Общая статистика группы ").append(group.getName()).append("</h3>");
                html.append("<table>");
                html.append("<tr><th>Студент</th>");
                for (Long taskId : checkedTaskIds) {
                    Task task = config.getTaskById(taskId);
                    html.append("<th>").append(task.getName()).append("</th>");
                }
                html.append("<th>Сумма</th><th>Активность</th><th>Оценка</th></tr>");

                for (Student student : group.getStudents()) {
                    html.append("<tr>")
                            .append("<td>").append(student.getFio()).append("</td>");

                    for (Long taskId : checkedTaskIds) {
                        html.append("<td>?</td>");
                    }

                    html.append("<td>?</td>")
                            .append("<td>?%</td>")
                            .append("<td>-</td>")
                            .append("</tr>");
                }
                html.append("</table>");
            } else {
                html.append("<p>Для этой группы нет назначенных проверок.</p>");
            }
        }

        html.append("</body></html>");

        try {
            Files.writeString(Path.of(outputPath), html.toString());
            System.out.println("HTML отчет успешно сгенерирован: " + outputPath);
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении отчета: " + e.getMessage());
        }
    }
}