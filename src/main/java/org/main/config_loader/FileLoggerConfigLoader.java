package org.main.config_loader;

import org.main.LoggingLevel;
import org.main.config.FileLoggerConfig;
import org.main.config.LoggerConfig;

import java.io.*;

public class FileLoggerConfigLoader implements LoggerConfigLoader {
    private final String configFilePath;

    public FileLoggerConfigLoader(String configFilePath) {
        this.configFilePath = configFilePath;
    }


    @Override
    public LoggerConfig load() {
        File configFile = new File(configFilePath);

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {

                String logFilePath = null;
                int maxLogFileSize = 0;
                LoggingLevel logLevel = null;
                String logFormat = null;

                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(": ");

                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();

                        switch (key) {
                            case "FILE":
                                logFilePath = value;
                                break;
                            case "LEVEL":
                                logLevel = LoggingLevel.valueOf(value);
                                break;
                            case "MAX-SIZE":
                                maxLogFileSize = Integer.parseInt(value);
                                break;
                            case "FORMAT":
                                logFormat = value;
                                break;
                        }

                    }
                }

                if (logFilePath != null && logLevel != null && maxLogFileSize > 0 && logFormat != null) {
                    return new FileLoggerConfig(logFilePath, maxLogFileSize, logLevel, logFormat);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return createDefaultConfiguration();
    }

    private LoggerConfig createDefaultConfiguration() {
        String logFilePath = "logs/Log.txt";
        int maxLogFileSize = 1024;
        LoggingLevel logLevel = LoggingLevel.INFO;
        String logFormat = "[%s][%s] Message: [%s]";

        return new FileLoggerConfig(logFilePath, maxLogFileSize, logLevel, logFormat);
    }
}
