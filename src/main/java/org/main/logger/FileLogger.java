package org.main.logger;

import org.main.LoggingLevel;
import org.main.config.FileLoggerConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger implements Logger {
    private final FileLoggerConfig config;
    private File currentLogFile;
    private long currentLogFileSize;

    public FileLogger(FileLoggerConfig config) {
        this.config = config;
        this.currentLogFile = new File(config.getLogFilePath());
        this.currentLogFileSize = currentLogFile.length();
    }

    @Override
    public void info(String message) {
        logMessage(LoggingLevel.INFO, message);
    }

    @Override
    public void debug(String message) {
        logMessage(LoggingLevel.DEBUG, message);
    }


    private void logMessage(LoggingLevel level, String message) {
        if (currentLogFileSize + message.length() > config.getMaxLogFileSize()) {
            createNewLogFile();
        }

        String formattedMessage = formatLogMessage(level, message);
        writeLogMessage(formattedMessage);
    }

    private void createNewLogFile() {
        String newLogFilePath = generateNewLogFilePath();

        currentLogFile = new File(newLogFilePath);
        currentLogFileSize = 0;
    }

    private String formatLogMessage(LoggingLevel level, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date()) + " [" + level + "] " + message;
    }

    private void writeLogMessage(String message) {
        if (currentLogFile != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(currentLogFile, true))) {
                writer.println(message);
                updateCurrentLogFileSize(message.length());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateNewLogFilePath() {
        String logFilePath = config.getLogFilePath();
        String logFileExtension = logFilePath.substring(logFilePath.lastIndexOf('.'));
        return logFilePath.replace(logFileExtension, "_" + new SimpleDateFormat("dd.MM.yyyy-HH.mm").format(new Date()) + logFileExtension);
    }

    private void updateCurrentLogFileSize(long len) {
        currentLogFileSize += len;
    }
}
