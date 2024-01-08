package org.lib.config_loader;

import org.junit.jupiter.api.*;
import org.lib.config.LoggerConfig;
import org.lib.logger.LoggingLevel;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;



class FileLoggerConfigLoaderTest {
    private static final LoggerConfigLoader configLoader = new FileLoggerConfigLoader();
    private static final String TEST_CONFIG_FILE_PATH = "src/test/java/org/lib/testConfig.txt";

    @BeforeAll
    static void setUp() {
        File testConfigFile = new File(TEST_CONFIG_FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testConfigFile))) {
            if (!testConfigFile.exists()) {
                testConfigFile.createNewFile();
            }

            writer.write("FILE: testLogFile.txt\n");
            writer.write("LEVEL: DEBUG\n");
            writer.write("MAX_SIZE: 500\n");
            writer.write("FORMAT: [%s][%s] Message - [%s]\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void tearDown() {
        File testConfigFile = new File(TEST_CONFIG_FILE_PATH);
        if (testConfigFile.exists()) {
            testConfigFile.delete();
        }
    }

    @Test
    public void testLoadFromInputStream() {
        String inputStreamContent = "FILE: testLogFile.txt\n" +
                "LEVEL: DEBUG\n" +
                "MAX_SIZE: 2048\n" +
                "FORMAT: [%s][%s] Message - [%s]\n";

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStreamContent.getBytes());

        LoggerConfig config = configLoader.load(byteArrayInputStream);

        assertEquals("testLogFile.txt", config.getLogFilePath());
        assertEquals(2048, config.getMaxLogFileSize());
        assertEquals(LoggingLevel.DEBUG, config.getLogLevel());
        assertEquals("[%s][%s] Message - [%s]", config.getLogFormat());
    }


    @Test
    public void testLoadFromNonExistFile() {
        String nonExistentFilePath = "non_existent_file.txt";

        LoggerConfig config = configLoader.load(nonExistentFilePath);

        assertEquals("logs/Log.txt", config.getLogFilePath());
        assertEquals(1024, config.getMaxLogFileSize());
        assertEquals(LoggingLevel.INFO, config.getLogLevel());
        assertEquals("[%s][%s] Message - [%s]", config.getLogFormat());
    }

    @Test
    public void testLoadFromExistFile() {
        LoggerConfig config = configLoader.load(TEST_CONFIG_FILE_PATH);

        assertEquals(500, config.getMaxLogFileSize());
        assertEquals("testLogFile.txt", config.getLogFilePath());
        assertEquals(LoggingLevel.DEBUG, config.getLogLevel());
        assertEquals("[%s][%s] Message - [%s]", config.getLogFormat());
    }
}