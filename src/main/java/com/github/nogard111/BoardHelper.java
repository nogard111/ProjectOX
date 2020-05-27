package com.github.nogard111;

import java.awt.*;
import java.util.Map;

public class BoardHelper {

  static Field[][] GenerateFields(int sizeY, int sizeX) {
    Field[][] fields = new Field[sizeY][sizeX];
    for (int y = 0; y < fields.length; y++) {
      var row = fields[y];
      for (int x = 0; x < row.length; x++) {
        row[x] = new Field(x, y);
      }
    }
    return fields;
  }

  static boolean IsPlayerAWinner(WinRule[] winRules, FieldType playerType, Field[][] fields) {
    for (var rule : winRules) {
      for (int y = 0; y < fields.length - rule.limitY; y++) {
        var row = fields[y];
        for (int x = 0; x < row.length - rule.limitX; x++) {
          boolean win = true;
          Point place = new Point(rule.reverseXRule ? x + rule.noOfChecks - 1 : x, y);
          for (int i = 0; i < rule.noOfChecks; i++, place = rule.getNextPointToCheck.apply(place)) {
            if (fields[place.y][place.x].type != playerType) {
              win = false;
              break;
            }
          }
          if (win) {
            System.out.println("win by " + rule.name);
            return true;
          }
        }
      }
    }
    return false;
  }

  static Map<WinRuleType, WinRule> getStandardRules(int lenToWin) {
    var map = Map.of(
            WinRuleType.HORIZONTAL,
            new WinRule(lenToWin - 1, 0, lenToWin, (Point k) -> new Point(k.x + 1, k.y), false),
            WinRuleType.VERTICAL,
            new WinRule(0, lenToWin - 1, lenToWin, (Point k) -> new Point(k.x, k.y + 1), false),
            WinRuleType.BACKSLASH,
            new WinRule(lenToWin - 1, lenToWin - 1, lenToWin, (Point k) -> new Point(k.x + 1, k.y + 1), false),
            WinRuleType.SLASH,
            new WinRule(lenToWin - 1, lenToWin - 1, lenToWin, (Point k) -> new Point(k.x - 1, k.y + 1), true)
    );
    map.forEach((a, b) -> b.name = a.toString());
    return map;
        /*
        return new WinRule[]{
                new WinRule(lenToWin-1,0,lenToWin,(Point k) -> new Point(k.x+1,k.y),false),
                new WinRule(0,lenToWin-1,lenToWin,(Point k) -> new Point(k.x,k.y+1),false),
                new WinRule(lenToWin-1,lenToWin-1,lenToWin,(Point k) -> new Point(k.x+1,k.y+1),false),
                new WinRule(lenToWin-1,lenToWin-1,lenToWin,(Point k) -> new Point(k.x-1,k.y+1),true)
        };*/
  }

  enum WinRuleType {
    HORIZONTAL,
    VERTICAL,
    BACKSLASH,
    SLASH
  }
}
