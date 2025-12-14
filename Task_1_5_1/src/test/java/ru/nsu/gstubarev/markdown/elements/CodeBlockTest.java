package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;

class CodeBlockTest {
    @Test
    void serializeWithoutLanguage() {
        CodeBlock code = new CodeBlock("print('Hello')");
        assertEquals("```\nprint('Hello')\n```", code.serialize());
    }

    @Test
    void serializeWithLanguage() {
        CodeBlock code = new CodeBlock("public class Test {}", "java");
        assertEquals("```java\npublic class Test {}\n```", code.serialize());
    }

    @Test
    void builderWithoutLines() {
        CodeBlock code = CodeBlock.builder().build();
        assertEquals("```\n\n```", code.serialize());
    }

    @Test
    void testEquals() {
        CodeBlock codeBlock1 = new CodeBlock("test", "java");
        CodeBlock codeBlock2 = new CodeBlock("test", "java");
        CodeBlock codeBlock3 = new CodeBlock("test", "python");
        CodeBlock codeBlock4 = new CodeBlock("different", "java");

        assertEquals(codeBlock1, codeBlock2);
        assertNotEquals(codeBlock1, codeBlock3);
        assertNotEquals(codeBlock1, codeBlock4);
        assertNotEquals(codeBlock1, null);
    }

    @Test
    void builderWithLanguage() {
        CodeBlock.CodeBuilder builder = CodeBlock.builder();
        assertNotNull(builder);

        CodeBlock codeBlock = builder
                .addLine("line1")
                .addLine("line2")
                .language("java")
                .build();

        assertNotNull(codeBlock);
        assertEquals("```java\nline1\nline2\n```", codeBlock.serialize());
    }

    @Test
    void builderWithoutLanguage() {
        CodeBlock.CodeBuilder builder = CodeBlock.builder();
        assertNotNull(builder);

        CodeBlock codeBlock = builder
                .addLine("line1")
                .addLine("line2")
                .build();

        assertNotNull(codeBlock);
        assertEquals("```\nline1\nline2\n```", codeBlock.serialize());
    }

    @Test
    void builderEmptyCode() {
        CodeBlock codeBlock = CodeBlock.builder().build();
        assertEquals("```\n\n```", codeBlock.serialize());
    }

    @Test
    void builderWithNullLanguage() {
        CodeBlock codeBlock = CodeBlock.builder()
                .addLine("test")
                .language(null)
                .build();
        assertEquals("```\ntest\n```", codeBlock.serialize());
    }

    @Test
    void constructorWithNullCodeShouldThrowException() {
        assertThrows(EmptyElementException.class,
                () -> new CodeBlock(null));
    }
}