package org.lib.config_loader;

import org.lib.config.LoggerConfig;

import java.io.File;
import java.io.InputStream;

public interface LoggerConfigLoader {
    LoggerConfig load(String configFilePath);
    LoggerConfig load(File file);
    LoggerConfig load(InputStream stream);
}

