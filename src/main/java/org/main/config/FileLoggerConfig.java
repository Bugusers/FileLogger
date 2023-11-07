package org.main.config;

import org.main.LoggingLevel;

public class FileLoggerConfig implements LoggerConfig {
    private final String logFilePath;
    private final Long maxLogFileSize;
    private final LoggingLevel logLevel;
    private final String logFormat;



    public FileLoggerConfig(String logFilePath, long maxLogFileSize, LoggingLevel logLevel, String logFormat) {
        this.logFilePath = logFilePath;
        this.maxLogFileSize = maxLogFileSize;
        this.logLevel = logLevel;
        this.logFormat = logFormat;
    }

    @Override
    public String getLogFilePath() {
        return logFilePath;
    }

    @Override
    public long getMaxLogFileSize() {
        return maxLogFileSize;
    }

    @Override
    public LoggingLevel getLogLevel() {
        return logLevel;
    }

    @Override
    public String getLogFormat() {
        return logFormat;
    }
}
