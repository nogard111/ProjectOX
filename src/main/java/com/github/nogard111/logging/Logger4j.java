package com.github.nogard111.logging;

import com.github.nogard111.logging.ILogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logger4j implements ILogger {
    protected static final Logger logger = LogManager.getLogger();

    @Override
    public void LogInfo(String message) {
        logger.info(message);
    }

    @Override
    public void LogError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}


