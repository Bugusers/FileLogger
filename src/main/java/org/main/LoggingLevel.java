package org.main;

public enum LoggingLevel {
    INFO,
    DEBUG;

    public boolean isVisible(LoggingLevel other) {
        return this.ordinal() >= other.ordinal();
    }
}
