package com.github.nogard111;

import java.awt.*;

public class Board {
  Field[][] fields;

  public Board(int sizeY, int sizeX) {
    fields = new Field[sizeY][sizeX];
    for (int y = 0; y < fields.length; y++) {
      var row = fields[y];
      for (int x = 0; x < row.length; x++) {
        row[x] = new Field(x, y);
      }
    }
  }

  public boolean areAllFieldsFilled() {
    boolean emptyFieldExists = false;
    for (int y = 0; y < fields.length; y++) {
      var row = fields[y];
      for (int x = 0; x < row.length; x++) {
        if (row[x].type == FieldType.NONE) {
          emptyFieldExists = true;
          break;
        }
      }
      if (emptyFieldExists) break;
    }
    return emptyFieldExists;
  }

  public void clearBoard() {
    for (int y = 0; y < fields.length; y++) {
      var row = fields[y];
      for (int x = 0; x < row.length; x++) {
        row[x].type = FieldType.NONE;
      }
    }
  }

  public boolean IsPlayerAWinner(WinRule[] winRules, FieldType playerType) {
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

  public Field[][] getFields() {
    return fields;
  }

  public boolean trySetFieldSymbol(FieldType symbol, int x, int y) {
    Field selected = fields[y][x];
      if(selected .type == FieldType.NONE)
      {
        selected.type = symbol;
        return true;
      }
      return false;
  }
}
