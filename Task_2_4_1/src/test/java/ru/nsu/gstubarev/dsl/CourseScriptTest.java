package ru.nsu.gstubarev.dsl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import groovy.lang.Closure;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.dsl.dataclasses.Config;

/**
 * TEST.
 */
public class CourseScriptTest {

    private static class DummyScript extends CourseScript {
        @Override
        public Object run() {
            return null;
        }
    }

    @Test
    public void testGetConfig() {
        DummyScript script = new DummyScript();
        Config config = script.getConfig();
        assertNotNull(config);
    }

    @Test
    public void testDeclareMethods() {
        DummyScript script = new DummyScript();
        Closure<?> closure = mock(Closure.class);

        script.declareTasks(closure);
        verify(closure).call();

        Closure<?> closureGroup = mock(Closure.class);
        script.declareGroups(closureGroup);
        verify(closureGroup).call();

        Closure<?> closureCheck = mock(Closure.class);
        script.declareCheckpoints(closureCheck);
        verify(closureCheck).call();

        Closure<?> closureCmd = mock(Closure.class);
        script.command("run", closureCmd);
        verify(closureCmd).call();
    }
}