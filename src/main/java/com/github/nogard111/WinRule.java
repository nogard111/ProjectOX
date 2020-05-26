package com.github.nogard111;

import java.awt.*;
import java.util.function.Function;

public class WinRule {
    public int limitRows;
    public int limitColumn;
    public int noOfChecks;
    public boolean reverseXRule;
    public Function<Point, Point> getNextPointToCheck;

    public WinRule(int limitRows, int limitColumn,int noOfChecks, Function<Point, Point> getNextPointToCheck,boolean reverseXRule) {
        this.limitRows = limitRows;
        this.limitColumn = limitColumn;
        this.noOfChecks = noOfChecks;
        this.getNextPointToCheck = getNextPointToCheck;
        this.reverseXRule = reverseXRule;
    }
}
