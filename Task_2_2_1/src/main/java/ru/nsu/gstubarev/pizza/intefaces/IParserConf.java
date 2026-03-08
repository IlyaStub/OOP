package ru.nsu.gstubarev.pizza.intefaces;

import ru.nsu.gstubarev.pizza.records.Configuration;

/**
 * Interface for parsing configuration files.
 */
public interface IParserConf {
    /**
     * Parses the configuration from a specified file.
     *
     * @param filePath the path to the configuration file
     * @return parsed configuration object
     */
    Configuration parse(String filePath);
}