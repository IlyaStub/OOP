package ru.nsu.gstubarev.dsl.dataclasses;

import java.time.LocalDate;

/**
 * Holds single task check result.
 */
public class CheckResult {
    public boolean compiled = false;
    public boolean docsGen = false;
    public boolean withoutReviewDogs = false;

    public int testsPassed = 0;
    public int testsFailed = 0;
    public int testsSkipped = 0;
    public int finalScore = 0;

    public LocalDate commitDate;
    public int activeWeeks = 0;

    /**
     * Returns formatted test counters.
     */
    public String getTestsString() {
        if (!compiled) {
            return "-";
        }
        return testsPassed + "/" + testsFailed + "/" + testsSkipped;
    }
}