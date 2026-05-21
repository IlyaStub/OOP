package ru.nsu.gstubarev.topology.worker;

import java.io.PrintWriter;
import ru.nsu.gstubarev.topology.Command;
import ru.nsu.gstubarev.topology.MessageParser;
import ru.nsu.gstubarev.topology.PrimeChecker;

/**
 * Parses and handles commands received by a worker node.
 */
public class CommandHandler {

    /**
     * Processes a single command line and writes response to output.
     */
    public void handle(String line, PrintWriter out) {
        if (line.equals(Command.PING.getText())) {
            out.println(Command.PONG.getText());
            return;
        }
        if (line.startsWith(Command.CHECK.getText())) {
            boolean result = handleCheck(line);
            out.println(MessageParser.buildResult(result));
            return;
        }
        System.err.println("Unknown command: " + line);
    }

    private boolean handleCheck(String line) {
        long[] numbers = MessageParser.parseCheck(line);
        for (long num : numbers) {
            if (!PrimeChecker.isPrime(num)) {
                return true;
            }
        }
        return false;
    }
}