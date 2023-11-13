package org.main.logger;

import org.main.config.FileLoggerConfig;
import org.main.config.LoggerConfig;
import org.main.config_loader.FileLoggerConfigLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileLoggerTest {

    @org.junit.jupiter.api.Test
    void info_shouldWriteInfoLog_whenLogLevelAllows() {
        FileLoggerConfigLoader loader = new FileLoggerConfigLoader();
        FileLogger fileLogger = new FileLogger((FileLoggerConfig) loader.load("configs/tests/test-debug-config.txt"));


        fileLogger.info("Test message");
        fileLogger.debug("Test message");


        String logContent = readLogFile(fileLogger.getConfig().getLogFilePath());

        assertTrue(logContent.contains("INFO: Test message"));
        assertFalse(logContent.contains("DEBUG: Test message"));
    }

    @org.junit.jupiter.api.Test
    void debug_shouldWriteDebugLog_whenLogLevelAllows() {
        FileLoggerConfigLoader loader = new FileLoggerConfigLoader();
        FileLogger fileLogger = new FileLogger((FileLoggerConfig) loader.load("configs/tests/test-debug-config.txt"));

        fileLogger.info("Test message");
        fileLogger.debug("Test message");

        String logContent = readLogFile(fileLogger.getConfig().getLogFilePath());

        assertTrue(logContent.contains("INFO: Test message"));
        assertTrue(logContent.contains("DEBUG: Test message"));

    }

    private String readLogFile(String logFilePath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception based on your application's requirements
        }

        return content.toString();
    }
}