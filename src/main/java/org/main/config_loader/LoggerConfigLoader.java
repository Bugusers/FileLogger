package org.main.config_loader;

import org.main.config.LoggerConfig;

import java.io.File;
import java.io.InputStream;

public interface LoggerConfigLoader {
    LoggerConfig load(String configFilePath);
    LoggerConfig load(File file);
    LoggerConfig load(InputStream stream);
}

