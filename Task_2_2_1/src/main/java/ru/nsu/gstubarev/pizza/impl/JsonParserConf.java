package ru.nsu.gstubarev.pizza.impl;

import com.google.gson.Gson;
import ru.nsu.gstubarev.pizza.intefaces.IParserConf;
import ru.nsu.gstubarev.pizza.records.Configuration;
import java.io.FileReader;
import java.io.IOException;

public class JsonParserConf implements IParserConf {
    @Override
    public Configuration parse(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            return new Configuration(3, 2, 10, 2000, 1000, 3000);
        }
    }
}