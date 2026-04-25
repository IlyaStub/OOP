package ru.nsu.gstubarev.dsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.outputs.HtmlGenerator;
import ru.nsu.gstubarev.dsl.outputs.ReportGenerator;
import ru.nsu.gstubarev.dsl.services.CheckerService;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        CompilerConfiguration compilerConfig = new CompilerConfiguration();
        compilerConfig.setScriptBaseClass(CourseScript.class.getName());

        GroovyShell shell = new GroovyShell(Main.class.getClassLoader(), new Binding(), compilerConfig);

        try {
            CourseScript script = (CourseScript) shell.parse(new File("conf.groovy"));
            script.run();

            Config configRes = script.getConfig();

            CheckerService engine = new CheckerService();
            engine.runChecks(configRes);

            System.out.println("конфигурация загружена");
            ReportGenerator reportGenerator = new HtmlGenerator();
            reportGenerator.gen(configRes, "report.html");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}