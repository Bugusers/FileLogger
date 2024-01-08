package org.lib.config;

import org.lib.logger.LoggingLevel;

public interface LoggerConfig{
    String getLogFilePath();
    int getMaxLogFileSize() ;
    LoggingLevel getLogLevel();
    String getLogFormat();
}
