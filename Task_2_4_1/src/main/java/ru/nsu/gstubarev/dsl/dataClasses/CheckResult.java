package ru.nsu.gstubarev.dsl.dataClasses;

public class CheckResult {
    public boolean compiled = false;
    public boolean docsGen = false;
    public boolean withoutReviewDogs = false;

    public int testsPassed = 0;
    public int testsFailed = 0;
    public int testsSkipped = 0;
    public int finalScore = 0;

    public String getTestsString() {
        if (!compiled) return "-";
        return testsPassed + "/" + testsFailed + "/" + testsSkipped;
    }
}
