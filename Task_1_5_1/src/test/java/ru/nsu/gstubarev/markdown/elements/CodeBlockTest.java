package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CodeBlockTest {

    @Test
    void serialize() {
        CodeBlock codeBlock1 = new CodeBlock("public class Test {}", "java");
        assertEquals("```java\npublic class Test {}\n```", codeBlock1.serialize());

        CodeBlock codeBlock2 = new CodeBlock("print('Hello')");
        assertEquals("```\nprint('Hello')\n```", codeBlock2.serialize());

        CodeBlock codeBlock3 = CodeBlock.builder()
                .addLine("function test() {")
                .addLine("  return 42;")
                .addLine("}")
                .language("javascript")
                .build();
        assertEquals("```javascript\nfunction test() {\n  return 42;\n}\n```",
                codeBlock3.serialize());
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
    void builder() {
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
    void constructorWithNullCodeShouldThrowException() {
        assertThrows(ru.nsu.gstubarev.markdown.exceptions.EmptyElementException.class,
                () -> new CodeBlock(null));
    }
}