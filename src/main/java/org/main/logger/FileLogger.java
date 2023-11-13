package org.main.logger;

import org.main.LoggingLevel;
import org.main.config.FileLoggerConfig;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;


public class FileLogger implements Logger {
    private final FileLoggerConfig config;

    public FileLogger(FileLoggerConfig config) {
        this.config = config;
    }

    public FileLoggerConfig getConfig() {
        return config;
    }

    @Override
    public void info(String message) {
        if (config.getLogLevel().isVisible(LoggingLevel.INFO)) {
            writeLog("INFO: " + message);
        }
    }

    @Override
    public void debug(String message) {
        if (config.getLogLevel().isVisible(LoggingLevel.DEBUG)) {
            writeLog("DEBUG: " + message);
        }
    }

    private void writeLog(String logMessage) {
        String logFilePath = config.getLogFilePath();
        String logFormat = config.getLogFormat();

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFilePath, true))) {
            if (config.getMaxLogFileSize() > 0) {
                long fileSize = new File(logFilePath).length();
                if (fileSize > config.getMaxLogFileSize()) {
                    rotateLogFile();
                }
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());
            String formattedMessage = String.format(logFormat, timestamp, config.getLogLevel(), logMessage);

            writer.println(formattedMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rotateLogFile() {
        String newLogFilePath = generateNewLogFilePath();
        config.changLogFile(newLogFilePath);
    }

    private String generateNewLogFilePath() {
        String logFilePath = config.getLogFilePath();

        int lastIndexOfSlash = logFilePath.lastIndexOf("/");
        if (lastIndexOfSlash == -1) {
            return logFilePath;
        }


        String newLogFileName = "Log_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + "_" + generateRandomNumber() + ".txt";

        return logFilePath.substring(0, lastIndexOfSlash) + "/" + newLogFileName;
    }

    private int generateRandomNumber() {
        return new Random().nextInt(10000);
    }

}
