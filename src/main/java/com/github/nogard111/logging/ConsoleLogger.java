package com.github.nogard111.logging;

public class ConsoleLogger implements ILogger {
    @Override
    public void LogInfo(String message) {
        System.out.println(message);
    }

    @Override
    public void LogError(String message, Throwable throwable) {
        System.out.println(message);
        throwable.printStackTrace(System.out);
    }
}
