package ru.nsu.gstubarev.poisk;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.gstubarev.poisk.exceptions.SearchInFileException;

/**
 * Class for search substring in file.
 */
public class SearchSubstring {
    private static int[] computePrefixFunction(String pattern) {
        int patternLength = pattern.length();
        int[] prefixFunction = new int[patternLength];
        int prefixLength = 0;

        for (int i = 1; i < patternLength; i++) {
            while (prefixLength > 0 && pattern.charAt(prefixLength) != pattern.charAt(i)) {
                prefixLength = prefixFunction[prefixLength - 1];
            }

            if (pattern.charAt(prefixLength) == pattern.charAt(i)) {
                prefixLength++;
            }

            prefixFunction[i] = prefixLength;
        }

        return prefixFunction;
    }

    /**
     * Finds all occurrences of substring in file.
     *
     * @param filename path to the file
     * @param pattern substring to search for
     * @return list of start indices of occurrences
     * @throws SearchInFileException if file processing error occurs
     * @throws IllegalArgumentException if invalid parameters provided
     */
    public static List<Long> find(String filename, String pattern) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        if (pattern == null) {
            throw new IllegalArgumentException("Search pattern cannot be null");
        }

        if (pattern.isEmpty()) {
            return new ArrayList<>();
        }

        int[] prefixFunction = computePrefixFunction(pattern);
        List<Long> occur = new ArrayList<>();
        int patternLength = pattern.length();
        int currentState = 0;
        long position = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {

            char[] buffer = new char[8192];
            int charsRead;

            while ((charsRead = reader.read(buffer)) != -1) {
                for (int i = 0; i < charsRead; i++) {
                    char c = buffer[i];

                    while (currentState > 0 && pattern.charAt(currentState) != c) {
                        currentState = prefixFunction[currentState - 1];
                    }

                    if (pattern.charAt(currentState) == c) {
                        currentState++;
                    }

                    if (currentState == patternLength) {
                        occur.add(position - patternLength + 1);
                        currentState = prefixFunction[currentState - 1];
                    }
                    position++;
                }
            }
        } catch (IOException e) {
            throw new SearchInFileException("Error processing file: " + filename, e);
        }

        return occur;
    }

    /**
     * Main method.
     *
     * @param args command line arguments: file_name substring
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please write <file_name> <substring>");
            return;
        }

        String filename = args[0];
        String pattern = args[1];

        try {
            List<Long> result = find(filename, pattern);
            System.out.println("Result: " + result);
        } catch (SearchInFileException e) {
            System.err.println("Search error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Parameter error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}