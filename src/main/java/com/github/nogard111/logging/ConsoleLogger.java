package com.github.nogard111.logging;

public class ConsoleLogger implements ILogger {
  @Override
  public void logInfo(String message) {
    System.out.println(message);
  }

  @Override
  public void logError(String message, Throwable throwable) {
    System.out.println(message);
    throwable.printStackTrace(System.out);
  }
}
