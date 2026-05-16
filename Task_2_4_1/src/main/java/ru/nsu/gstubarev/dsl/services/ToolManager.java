package ru.nsu.gstubarev.dsl.services;

import ru.nsu.gstubarev.dsl.exceptions.ToolDownloadException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Manages external tools required for task checking.
 */
public class ToolManager {
    private static final String CS_URL = "https://github.com/checkstyle/checkstyle"
            + "/releases/download/checkstyle-10.17.0/checkstyle-10.17.0-all.jar";
    private static final String GOOGLE_XML = "https://raw.githubusercontent.com/"
            + "checkstyle/checkstyle/master/src/main/resources/google_checks.xml";

    private final Path toolsDir = Paths.get("tools").toAbsolutePath();

    /**
     * Returns path to the Checkstyle JAR, downloading it if necessary.
     */
    public Path getJar() throws IOException {
        return ensureFile("checkstyle-all.jar", CS_URL);
    }

    /**
     * Returns path to the Checkstyle config, downloading it if necessary.
     */
    public Path getXml() throws IOException {
        return ensureFile("checkstyle.xml", GOOGLE_XML);
    }

    private Path ensureFile(String name, String url) throws IOException {
        Path target = toolsDir.resolve(name);
        try {
            if (!Files.exists(target)) {
                System.out.println("Загрузка " + name);
                try (InputStream in = URI.create(url).toURL().openStream()) {
                    Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            return target;
        } catch (IOException e) {
            throw new ToolDownloadException("Не удалось загрузить или сохранить инструмент " + name + " по URL: " + url, e);
        }
    }
}