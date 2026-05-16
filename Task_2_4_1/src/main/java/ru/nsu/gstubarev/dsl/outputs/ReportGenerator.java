package ru.nsu.gstubarev.dsl.outputs;

import ru.nsu.gstubarev.dsl.dataclasses.Config;

/**
 * The interface that the classes implement for generating the report.
 */
public interface ReportGenerator {
    /**
     * Method for generation of report.
     *
     * @param config a report is generated from this
     * @param outPath is generated in this file
     */
    void gen(Config config, String outPath);
}
