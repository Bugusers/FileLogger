package org.main.config;

import org.main.LoggingLevel;

public interface LoggerConfig{
    String getLogFilePath();
    long getMaxLogFileSize() ;
    LoggingLevel getLogLevel();
    String getLogFormat();
}
