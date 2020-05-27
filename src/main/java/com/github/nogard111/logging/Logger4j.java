package com.github.nogard111.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logger4j implements ILogger {
  protected static final Logger logger = LogManager.getLogger();

  @Override
  public void logInfo(String message) {
    logger.info(message);
  }

  @Override
  public void logError(String message, Throwable throwable) {
    logger.error(message, throwable);
  }
}


