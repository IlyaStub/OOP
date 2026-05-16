package ru.nsu.gstubarev.dsl.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Parses XML test results.
 */
public class TestParser {
    /**
     * Holds aggregated test counts.
     */
    public static class TestStats {
        public int passed = 0;
        public int failed = 0;
        public int skipped = 0;
    }

    /**
     * Parses test XML files.
     */
    public TestStats parse(File resultsDir) {
        TestStats stats = new TestStats();
        if (!resultsDir.exists() || !resultsDir.isDirectory()) {
            return stats;
        }

        File[] xmlFiles = resultsDir.listFiles((dir, name) -> name.endsWith(".xml"));
        if (xmlFiles == null) {
            return stats;
        }

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            for (File xml : xmlFiles) {
                Document doc = dBuilder.parse(xml);
                doc.getDocumentElement().normalize();
                Element suite = (Element) doc.getElementsByTagName("testsuite").item(0);

                if (suite != null) {
                    int tests = Integer.parseInt(suite.getAttribute("tests"));
                    int failures = Integer.parseInt(suite.getAttribute("failures"));
                    int errors = Integer.parseInt(suite.getAttribute("errors"));
                    int skipped = Integer.parseInt(suite.getAttribute("skipped"));

                    stats.failed += (failures + errors);
                    stats.skipped += skipped;
                    stats.passed += (tests - failures - errors - skipped);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка парсинга XML: " + e.getMessage());
        }
        return stats;
    }
}