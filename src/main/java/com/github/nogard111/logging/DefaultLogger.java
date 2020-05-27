package com.github.nogard111.logging;

public class DefaultLogger {
  private static ILogger logger = new ConsoleLogger();

  public static ILogger getLogger() {
    return logger;
  }

  public static void setLogger(ILogger logger) {
    DefaultLogger.logger = logger;
  }
}
