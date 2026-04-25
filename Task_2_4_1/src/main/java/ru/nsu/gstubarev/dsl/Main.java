package ru.nsu.gstubarev.dsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.gstubarev.dsl.dataClasses.Config;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        CompilerConfiguration compilerConfig = new CompilerConfiguration();
        compilerConfig.setScriptBaseClass(CourseScript.class.getName());

        GroovyShell shell = new GroovyShell(Main.class.getClassLoader(), new Binding(), compilerConfig);

        try {
            CourseScript script = (CourseScript) shell.parse(new File("conf.groovy"));
            script.run();

            Config resultConfig = script.getConfig();

            System.out.println("конфигурация загружена");
            System.out.println(resultConfig);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}