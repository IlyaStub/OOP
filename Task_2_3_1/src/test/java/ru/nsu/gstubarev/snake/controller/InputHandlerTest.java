package ru.nsu.gstubarev.snake.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.snake.model.GameEngine;
import ru.nsu.gstubarev.snake.model.Snake;
import ru.nsu.gstubarev.snake.model.enums.Direction;

public class InputHandlerTest {
    private InputHandler inputHandler;
    private GameEngine engineMock;
    private Snake snakeMock;

    @BeforeEach
    public void setUp() {
        inputHandler = new InputHandler();
        engineMock = mock(GameEngine.class);
        snakeMock = mock(Snake.class);

        when(engineMock.getPlayerSnake()).thenReturn(snakeMock);
        when(engineMock.isGameOver()).thenReturn(false);
    }

    @Test
    public void testValidDirectionChange() {
        when(snakeMock.getCurrentDirection()).thenReturn(Direction.LEFT);
        KeyEvent event = createKeyEventMock(KeyCode.UP);

        inputHandler.handleKeyPress(event, engineMock, false);

        verify(snakeMock).setCurrentDirection(Direction.UP);
    }

    @Test
    public void testInvalidOppositeDirectionChange() {
        when(snakeMock.getCurrentDirection()).thenReturn(Direction.UP);
        KeyEvent event = createKeyEventMock(KeyCode.DOWN);

        inputHandler.handleKeyPress(event, engineMock, false);

        verify(snakeMock, never()).setCurrentDirection(Direction.DOWN);
    }

    @Test
    public void testInputIgnoredWhenGameIsPaused() {
        when(snakeMock.getCurrentDirection()).thenReturn(Direction.LEFT);
        KeyEvent event = createKeyEventMock(KeyCode.UP);

        inputHandler.handleKeyPress(event, engineMock, true);

        verify(snakeMock, never()).setCurrentDirection(any());
    }

    @Test
    public void testInputIgnoredWhenGameOver() {
        when(engineMock.isGameOver()).thenReturn(true);
        when(snakeMock.getCurrentDirection()).thenReturn(Direction.LEFT);
        KeyEvent event = createKeyEventMock(KeyCode.UP);

        inputHandler.handleKeyPress(event, engineMock, false);

        verify(snakeMock, never()).setCurrentDirection(any());
    }

    @Test
    public void testMultipleInputsInSingleTickIgnored() {
        when(snakeMock.getCurrentDirection()).thenReturn(Direction.LEFT);
        KeyEvent eventUp = createKeyEventMock(KeyCode.UP);
        KeyEvent eventRight = createKeyEventMock(KeyCode.RIGHT);

        inputHandler.handleKeyPress(eventUp, engineMock, false);
        inputHandler.handleKeyPress(eventRight, engineMock, false);

        verify(snakeMock, times(1)).setCurrentDirection(Direction.UP);
        verify(snakeMock, never()).setCurrentDirection(Direction.RIGHT);
    }

    @Test
    public void testInputAllowedAfterTickReset() {
        when(snakeMock.getCurrentDirection()).thenReturn(Direction.LEFT);

        inputHandler.handleKeyPress(createKeyEventMock(KeyCode.UP), engineMock, false);
        verify(snakeMock).setCurrentDirection(Direction.UP);

        inputHandler.resetTick();
        when(snakeMock.getCurrentDirection()).thenReturn(Direction.UP);

        inputHandler.handleKeyPress(createKeyEventMock(KeyCode.RIGHT), engineMock, false);
        verify(snakeMock).setCurrentDirection(Direction.RIGHT);
    }

    private KeyEvent createKeyEventMock(KeyCode code) {
        KeyEvent eventMock = mock(KeyEvent.class);
        when(eventMock.getCode()).thenReturn(code);
        return eventMock;
    }
}