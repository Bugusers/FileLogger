package org.main;

import org.main.config.FileLoggerConfig;
import org.main.config_loader.FileLoggerConfigLoader;
import org.main.logger.FileLogger;
import org.main.logger.Logger;

public class Main {
    public static void main(String[] args) {
        FileLoggerConfigLoader loader = new FileLoggerConfigLoader();
        Logger logger = new FileLogger((FileLoggerConfig) loader.load("configs/config.txt"));


        for(int i = 0; i < 10; i++) {
            logger.debug("Debug");
            logger.info("info");
        }

    }
}