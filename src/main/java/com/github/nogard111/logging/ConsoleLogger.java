package com.github.nogard111.logging;

public class ConsoleLogger implements ILogger {
  @Override
  public void logInfo(String message) {
    System.out.println(message);
  }

  @Override
  public void logError(String message, Throwable throwable) {
    System.err.println(message);
    System.out.println(message);
    if(throwable != null) {
      throwable.printStackTrace(System.err);
    }
  }
}
