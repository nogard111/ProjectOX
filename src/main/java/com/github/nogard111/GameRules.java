package com.github.nogard111;

import java.awt.*;
import java.util.Map;

public class GameRules {


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
