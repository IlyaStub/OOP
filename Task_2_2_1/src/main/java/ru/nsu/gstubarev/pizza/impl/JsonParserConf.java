package ru.nsu.gstubarev.pizza.impl;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import ru.nsu.gstubarev.pizza.intefaces.IparserConf;
import ru.nsu.gstubarev.pizza.records.Configuration;

/**
 * JSON implementation of configuration parser using Gson library.
 */
public class JsonParserConf implements IparserConf {
    @Override
    public Configuration parse(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException("Read file error");
        }
    }
}