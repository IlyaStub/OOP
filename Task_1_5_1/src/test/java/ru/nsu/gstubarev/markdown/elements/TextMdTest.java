package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TextMdTest {

    @Test
    void serialize() {
        TextMd normal = TextMd.builder("normal").build();
        assertEquals("normal", normal.serialize());

        TextMd bold = TextMd.builder("bold").bold().build();
        assertEquals("**bold**", bold.serialize());

        TextMd italic = TextMd.builder("italic").italic().build();
        assertEquals("*italic*", italic.serialize());

        TextMd code = TextMd.builder("code").code().build();
        assertEquals("`code`", code.serialize());

        TextMd strike = TextMd.builder("strike").strikethrough().build();
        assertEquals("~~strike~~", strike.serialize());

        TextMd boldItalic = TextMd.builder("bold italic").bold().italic().build();
        assertEquals("***bold italic***", boldItalic.serialize());

        TextMd boldItalicStrike = TextMd.builder("text").bold().italic().strikethrough().build();
        assertEquals("***~~text~~***", boldItalicStrike.serialize());
    }

    @Test
    void testEquals() {
        TextMd text1 = TextMd.builder("test").bold().italic().build();
        TextMd text2 = TextMd.builder("test").bold().italic().build();
        TextMd text3 = TextMd.builder("test").bold().build();
        TextMd text4 = TextMd.builder("different").bold().italic().build();

        assertEquals(text1, text2);
        assertNotEquals(text1, text3);
        assertNotEquals(text1, text4);
        assertNotEquals(text1, null);
    }

    @Test
    void builder() {
        TextMd.TextBuilder builder = TextMd.builder("test");
        assertNotNull(builder);

        TextMd text = builder
                .bold()
                .italic()
                .strikethrough()
                .build();

        assertNotNull(text);
        assertTrue(text.serialize().contains("***~~test~~***"));
    }

    @Test
    void builderCodeCanBeCombinedWithBoldAndItalic() {
        TextMd text = TextMd.builder("test").code().bold().italic().build();
        assertEquals("***`test`***", text.serialize());
    }
}