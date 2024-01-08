package org.lib.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lib.logger.LoggingLevel;

@Getter
@AllArgsConstructor
public class FileLoggerConfig implements LoggerConfig {
    private String logFilePath;
    private final int maxLogFileSize;
    private final LoggingLevel logLevel;
    private final String logFormat;

    public void changLogFile(String newLogFilePath) {
        logFilePath = newLogFilePath;
    };
}
