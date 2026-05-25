package ru.nsu.gstubarev.dsl.delegates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import groovy.lang.Closure;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.Config;
import ru.nsu.gstubarev.dsl.dataclasses.Group;

/**
 * TEST.
 */
public class GroupListConfigTest {

    @Test
    public void testAddGroup() {
        Config config = new Config();
        GroupListConfig delegate = new GroupListConfig(config);

        Closure<?> closure = mock(Closure.class);
        delegate.addGroup("20201", closure);
        verify(closure).call();

        assertEquals(1, config.getGroups().size());
        Group group = config.getGroups().get(0);
        assertEquals("20201", group.getName());
    }
}