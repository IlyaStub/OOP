package ru.nsu.gstubarev.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.markdown.exceptions.EmptyElementException;

class ListMdTest {

    @Test
    void serializeWithoutMarker() {
        ListMd list1 = ListMd.builder()
                .addTextItem("Item 1")
                .addTextItem("Item 2")
                .build();
        assertEquals("- Item 1\n- Item 2", list1.serialize());
    }

    @Test
    void serializeDifListWithMarker() {
        ListMd list2 = ListMd.builder()
                .marker("*")
                .addItem(TextMd.builder("Bold item").bold().build())
                .addItem(new Link("Link", "http://example.com"))
                .addItem(Task.builder("Task").completed().build())
                .build();
        assertTrue(list2.serialize().contains("* **Bold item**"));
        assertTrue(list2.serialize().contains("* [Link](http://example.com)"));
        assertTrue(list2.serialize().contains("* [x] Task"));
    }

    @Test
    void serializeEmptyList() {
        EmptyElementException exception = assertThrows(EmptyElementException.class, () -> {
            ListMd.builder().build();
        });
        assertEquals("List must contain at least one item", exception.getMessage());
    }

    @Test
    void serializeWithDifferentMarkers() {
        ListMd dashList = ListMd.builder()
                .marker("-")
                .addTextItem("Item 1")
                .addTextItem("Item 2")
                .build();
        assertEquals("- Item 1\n- Item 2", dashList.serialize());

        ListMd starList = ListMd.builder()
                .marker("*")
                .addTextItem("Item 1")
                .addTextItem("Item 2")
                .build();
        assertEquals("* Item 1\n* Item 2", starList.serialize());

        ListMd plusList = ListMd.builder()
                .marker("+")
                .addTextItem("Item 1")
                .addTextItem("Item 2")
                .build();
        assertEquals("+ Item 1\n+ Item 2", plusList.serialize());
    }

    @Test
    void testEquals() {
        ListMd list1 = ListMd.builder()
                .addTextItem("Item 1")
                .addTextItem("Item 2")
                .build();

        ListMd list2 = ListMd.builder()
                .addTextItem("Item 1")
                .addTextItem("Item 2")
                .build();

        ListMd list3 = ListMd.builder()
                .marker("*")
                .addTextItem("Item 1")
                .addTextItem("Item 2")
                .build();

        ListMd list4 = ListMd.builder()
                .addTextItem("Item 1")
                .build();

        assertEquals(list1, list2);
        assertNotEquals(list1, list3);
        assertNotEquals(list1, list4);
        assertNotEquals(list1, null);
    }

    @Test
    void testEqualsWithComplexItems() {
        ListMd list1 = ListMd.builder()
                .addItem(TextMd.builder("Text").italic().build())
                .addItem(Task.builder("Task").completed().build())
                .build();

        ListMd list2 = ListMd.builder()
                .addItem(TextMd.builder("Text").italic().build())
                .addItem(Task.builder("Task").completed().build())
                .build();

        ListMd list3 = ListMd.builder()
                .addItem(TextMd.builder("Text").italic().build())
                .addItem(Task.builder("Task").build())
                .build();

        assertEquals(list1, list2);
        assertNotEquals(list1, list3);
    }

    @Test
    void builder() {
        ListMd.ListBuilder builder = ListMd.builder();
        assertNotNull(builder);

        ListMd list = builder
                .marker("+")
                .addTextItem("First")
                .addTextItem("Second")
                .build();

        assertNotNull(list);
        assertTrue(list.serialize().startsWith("+"));
    }

    @Test
    void addItemsMethod() {
        ListMd list = ListMd.builder()
                .addItems(
                        TextMd.builder("Item 1").build(),
                        TextMd.builder("Item 2").bold().build(),
                        Task.builder("Task 3").build()
                )
                .build();

        String result = list.serialize();
        assertTrue(result.contains("Item 1"));
        assertTrue(result.contains("**Item 2**"));
        assertTrue(result.contains("[ ] Task 3"));
    }
}