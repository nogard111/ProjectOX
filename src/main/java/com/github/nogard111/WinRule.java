package com.github.nogard111;

import java.awt.*;
import java.util.function.Function;

public class WinRule {
    public int limitX;
    public int limitY;
    public int noOfChecks;
    public boolean reverseXRule;
    public Function<Point, Point> getNextPointToCheck;
    public String name;

    public WinRule(int limitX, int limitY, int noOfChecks, Function<Point, Point> getNextPointToCheck, boolean reverseXRule) {
        this.limitX = limitX;
        this.limitY = limitY;
        this.noOfChecks = noOfChecks;
        this.getNextPointToCheck = getNextPointToCheck;
        this.reverseXRule = reverseXRule;
    }
}
