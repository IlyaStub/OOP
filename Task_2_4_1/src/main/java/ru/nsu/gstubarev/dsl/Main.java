package ru.nsu.gstubarev.dsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.gstubarev.dsl.dataClasses.Config;
import ru.nsu.gstubarev.dsl.outputs.HtmlGenerator;
import ru.nsu.gstubarev.dsl.outputs.ReportGenerator;
import ru.nsu.gstubarev.dsl.services.BuildService;
import ru.nsu.gstubarev.dsl.services.CheckerService;
import ru.nsu.gstubarev.dsl.services.GitService;
import ru.nsu.gstubarev.dsl.services.StyleChecker;
import ru.nsu.gstubarev.dsl.services.ToolManager;
import ru.nsu.gstubarev.dsl.utils.CommandExecutor;
import ru.nsu.gstubarev.dsl.utils.TestParser;
import java.io.File;

/**
 * Main class.
 */
public class Main {
    /**
     * Method main.
     */
    public static void main(String[] args) {
        CompilerConfiguration compilerConfig = new CompilerConfiguration();
        compilerConfig.setScriptBaseClass(CourseScript.class.getName());

        GroovyShell shell =
                new GroovyShell(Main.class.getClassLoader(), new Binding(), compilerConfig);

        try {
            CourseScript script = (CourseScript) shell.parse(new File("conf.groovy"));
            script.run();

            Config configRes = script.getConfig();

            CommandExecutor executor = new CommandExecutor();
            ToolManager toolManager = new ToolManager();
            TestParser testParser = new TestParser();
            GitService gitService = new GitService();

            StyleChecker styleChecker = new StyleChecker(toolManager, executor);
            BuildService buildService = new BuildService(executor, styleChecker, testParser);

            CheckerService engine = new CheckerService(gitService, buildService);

            engine.runChecks(configRes);

            System.out.println("конфигурация загружена");
            ReportGenerator reportGenerator = new HtmlGenerator();
            reportGenerator.gen(configRes, "report.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}