package ru.nsu.gstubarev.topology;

/**
 * Builds and parses protocol messages for Master-Worker communication.
 */
public final class MessageParser {
    /**
     * Builds a CHECK command string from an array of numbers.
     */
    public static String buildCheck(long[] chunk) {
        StringBuilder sb = new StringBuilder(Command.CHECK.getText());
        sb.append(" ");
        for (int i = 0; i < chunk.length; i++) {
            sb.append(chunk[i]);
            if (i < chunk.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * Parses numbers from a CHECK command string.
     */
    public static long[] parseCheck(String line) {
        String payload = line.substring(Command.CHECK.getText().length()).trim();
        String[] parts = payload.split(",");
        long[] numbers = new long[parts.length];
        for (int i = 0; i < parts.length; i++) {
            numbers[i] = Long.parseLong(parts[i].trim());
        }
        return numbers;
    }

    /**
     * Builds a RESULT response string.
     */
    public static String buildResult(boolean value) {
        return Command.RESULT.getText() + " " + value;
    }

    /**
     * Parses a boolean value from a RESULT response string.
     */
    public static boolean parseResult(String line) {
        if (!line.startsWith(Command.RESULT.getText())) {
            throw new IllegalArgumentException("Expected RESULT, got: " + line);
        }
        String value = line.substring(Command.RESULT.getText().length()).trim();
        return Boolean.parseBoolean(value);
    }
}