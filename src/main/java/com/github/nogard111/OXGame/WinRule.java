package com.github.nogard111.OXGame;

import java.awt.*;
import java.util.function.Function;

class WinRule {
  int limitX;
  int limitY;
  String name;
  private int noOfChecks;
  private boolean reverseXRule;
  private Function<Point, Point> getNextPointToCheck;

  public WinRule(int limitX, int limitY, int noOfChecks, Function<Point, Point> getNextPointToCheck, boolean reverseXRule) {
    this.limitX = limitX;
    this.limitY = limitY;
    this.noOfChecks = noOfChecks;
    this.getNextPointToCheck = getNextPointToCheck;
    this.reverseXRule = reverseXRule;
  }

  Point[] generatePlacesToCheck(int startX,int startY)
  {
    var placesToCheck = new Point[noOfChecks];
    Point place = new Point(reverseXRule ? startX + noOfChecks - 1 : startX, startY);
    for (int i = 0; i < noOfChecks; i++, place = getNextPointToCheck.apply(place)) {
      placesToCheck[i] = place;
    }
    return placesToCheck;
  }
}
