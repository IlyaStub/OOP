package ru.nsu.gstubarev.markdown;

import ru.nsu.gstubarev.markdown.elements.CodeBlock;
import ru.nsu.gstubarev.markdown.elements.Heading;
import ru.nsu.gstubarev.markdown.elements.Image;
import ru.nsu.gstubarev.markdown.elements.Link;
import ru.nsu.gstubarev.markdown.elements.ListMd;
import ru.nsu.gstubarev.markdown.elements.QuoteBlock;
import ru.nsu.gstubarev.markdown.elements.Table;
import ru.nsu.gstubarev.markdown.elements.Task;
import ru.nsu.gstubarev.markdown.elements.TextMd;

/**
 * Main class.
 */
public class Main {
    /**
     * Demonstration method main.
     *
     * @param args don't use
     */
    public static void main(String[] args) {
        TextMd normal = TextMd.builder("обычный").build();
        TextMd bold = TextMd.builder("жирный").bold().build();
        TextMd italic = TextMd.builder("курсив").italic().build();
        TextMd code = TextMd.builder("код").code().build();
        TextMd strike = TextMd.builder("зачеркнутый").strikethrough().build();

        System.out.println(normal.serialize());
        System.out.println(bold.serialize());
        System.out.println(italic.serialize());
        System.out.println(code.serialize());
        System.out.println(strike.serialize());

        Heading h1 = new Heading(1, "Первый");
        Heading h2 = new Heading(2, "Второй");

        System.out.println(h1.serialize());
        System.out.println(h2.serialize());

        Link link = new Link("Яндекс", "https://ya.ru");
        System.out.println(link.serialize());

        Image img = new Image("Кот", "cat.jpg", "Милый кот");
        System.out.println(img.serialize());

        System.out.println("\n=== Цитата ===");
        QuoteBlock quote = QuoteBlock.builder()
                .addLine("Первая строка")
                .addLine("Вторая строка")
                .build();
        System.out.println(quote.serialize());

        System.out.println("\n=== Блок кода ===");
        CodeBlock codeBlock = CodeBlock.builder()
                .addLine("public class Main {")
                .addLine("}")
                .language("java")
                .build();
        System.out.println(codeBlock.serialize());

        Task todo = Task.builder("Сделать").build();
        Task done = Task.builder("Сделано").completed().build();
        System.out.println(todo.serialize());
        System.out.println(done.serialize());

        ListMd list = ListMd.builder()
                .addTextItem("Первый пункт")
                .addItem(TextMd.builder("Жирный пункт").bold().build())
                .addItem(Task.builder("Задача").build())
                .addItem(new Link("Ссылка", "https://example.com"))
                .build();
        System.out.println(list.serialize());

        Table table = Table.builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT, Table.ALIGN_CENTER)
                .withRowLimit(3)
                .addRow("№", "Название", "Статус")
                .addRow(1, "Первое", "Готово")
                .addRow(2, "Второе", TextMd.builder("В процессе").italic().build())
                .addRow(3, "Третье", "Не начато")
                .addRow(4, "Четвертое", "Отменено")
                .build();
        System.out.println(table.serialize());
    }
}