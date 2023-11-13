package org.main.config_loader;

import org.main.LoggingLevel;
import org.main.config.FileLoggerConfig;
import org.main.config.LoggerConfig;

import java.io.*;

public class FileLoggerConfigLoader implements LoggerConfigLoader {
    private static final String KEY_FILE = "FILE";
    private static final String KEY_LEVEL = "LEVEL";
    private static final String KEY_MAX_SIZE = "MAX-SIZE";
    private static final String KEY_FORMAT = "FORMAT";


    @Override
    public LoggerConfig load(String configFilePath) {
        File configFile = new File(configFilePath);

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                return loadFromReader(reader);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return createDefaultConfiguration();
    }

    @Override
    public LoggerConfig load(File file) {
        try {
            return load(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoggerConfig load(InputStream stream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            return loadFromReader(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createDefaultConfiguration();
    }

    private LoggerConfig loadFromReader(BufferedReader reader) throws IOException {
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
                    case KEY_FILE:
                        logFilePath = value;
                        break;
                    case KEY_LEVEL:
                        logLevel = LoggingLevel.valueOf(value);
                        break;
                    case KEY_MAX_SIZE:
                        maxLogFileSize = Integer.parseInt(value);
                        break;
                    case KEY_FORMAT:
                        logFormat = value;
                        break;
                }
            }
        }

        if (logFilePath != null && logLevel != null && maxLogFileSize > 0 && logFormat != null) {
            return new FileLoggerConfig(logFilePath, maxLogFileSize, logLevel, logFormat);
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
