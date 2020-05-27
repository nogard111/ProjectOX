package com.github.nogard111.logging;

public interface ILogger {
  void LogInfo(String message);

  void LogError(String message, Throwable throwable);
}
