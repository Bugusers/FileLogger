Logger - file logger

The config loader loads the config via three paths: input, file path, or transferred file.

The configuration is the path of the log file, the current logging level, the maximum file size (in bytes), and the format for writing to the file.

Enumeration (enum) LoggingLevel. Consists of the values: INFO, DEBUG.
Visibility table:

| LEVEL | INFO | DEBUG |

| INFO | X |  - |

| DEBUG | X | X |

The FileLogger class has debug and info methods that take a message string as a parameter. The method writes to the file specified in the configuration in the format specified for writing from the configuration.
When the maximum file size is reached or exceeded, a new file is created for storage. The name of each new file contains the date of creation.