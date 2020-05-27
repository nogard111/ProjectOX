package com.github.nogard111.logging;

public interface ILogger {
  void logInfo(String message);

  void logError(String message, Throwable throwable);
}
