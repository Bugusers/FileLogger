package org.main;

import org.main.config.FileLoggerConfig;
import org.main.config_loader.FileLoggerConfigLoader;
import org.main.logger.FileLogger;
import org.main.logger.Logger;

public class Main {
    public static void main(String[] args) {
        FileLoggerConfigLoader loader = new FileLoggerConfigLoader("config.txt");
        Logger logger = new FileLogger((FileLoggerConfig) loader.load());

        logger.debug("Debug");
        logger.info("info");

    }
}