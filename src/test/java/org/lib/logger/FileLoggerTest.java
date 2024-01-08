package org.lib.logger;

import org.junit.jupiter.api.*;
import org.lib.config.FileLoggerConfig;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

class FileLoggerTest {
    private static final String TEST_LOG_FILE_PATH = "src/test/java/org/lib/testLogFile.txt";

    @BeforeEach
    void setUp() throws IOException {
        File testLogFile = new File(TEST_LOG_FILE_PATH);
        if (!testLogFile.exists()) {
            testLogFile.createNewFile();
        }
    }

    @AfterEach
    void tearDown() {
        File testLogFile = new File(TEST_LOG_FILE_PATH);
        if (testLogFile.exists()) {
            testLogFile.delete();
        }
    }
    @Test
    public void testInfoLogging() {
        try {
            FileLoggerConfig mockConfig = mock(FileLoggerConfig.class);
            when(mockConfig.getLogLevel()).thenReturn(LoggingLevel.INFO);
            when(mockConfig.getLogFilePath()).thenReturn(TEST_LOG_FILE_PATH);
            when(mockConfig.getLogFormat()).thenReturn("[%s][%s] Message - [%s]");
            when(mockConfig.getMaxLogFileSize()).thenReturn(1024);

            FileLogger fileLogger = new FileLogger(mockConfig);


            fileLogger.info("Test Info Message");
            fileLogger.debug("Test Debug Message");

            String logContent;
            logContent = Files.readString(Path.of(TEST_LOG_FILE_PATH));

            assertTrue(logContent.contains("INFO: Test Info Message"));
            assertFalse(logContent.contains("DEBUG: Test Debug Message"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDebugLogging() {
        try {
            FileLoggerConfig mockConfig = mock(FileLoggerConfig.class);
            when(mockConfig.getLogLevel()).thenReturn(LoggingLevel.DEBUG);
            when(mockConfig.getLogFilePath()).thenReturn(TEST_LOG_FILE_PATH);
            when(mockConfig.getLogFormat()).thenReturn("[%s][%s] Message - [%s]");
            when(mockConfig.getMaxLogFileSize()).thenReturn(1024);

            FileLogger fileLogger = new FileLogger(mockConfig);

            fileLogger.debug("Test Debug Message");
            fileLogger.info("Test Info Message");


            String logContent;
            logContent = Files.readString(Path.of(TEST_LOG_FILE_PATH));

            assertTrue(logContent.contains("DEBUG: Test Debug Message"));
            assertTrue(logContent.contains("INFO: Test Info Message"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}