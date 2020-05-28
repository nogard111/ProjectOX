package com.github.nogard111.OXGame;

class Board {
  Field[][] fields;

  /**
   * @param sizeX : board size on X axis
   * @param sizeY : board size on Y axis
   */
  public Board(int sizeX, int sizeY) {
    fields = new Field[sizeY][sizeX];
    for (int y = 0; y < fields.length; y++) {
      var row = fields[y];
      for (int x = 0; x < row.length; x++) {
        row[x] = new Field(x, y);
      }
    }
  }

  boolean areAllFieldsFilled() {
    boolean emptyFieldExists = false;
    for (int y = 0; y < fields.length; y++) {
      var row = fields[y];
      for (int x = 0; x < row.length; x++) {
        if (row[x].type == FieldType.NONE) {
          emptyFieldExists = true;
          break;
        }
      }
      if (emptyFieldExists) {
        break;
      }
    }
    return emptyFieldExists;
  }

  void clearBoard() {
    for (int y = 0; y < fields.length; y++) {
      var row = fields[y];
      for (int x = 0; x < row.length; x++) {
        row[x].type = FieldType.NONE;
      }
    }
  }

  boolean isPlayerAWinner(WinRule[] winRules, FieldType playerType) {
    for (var rule : winRules) {
      if (isRuleFulfilled(playerType, rule)) return true;
    }
    return false;
  }

  private boolean isRuleFulfilled(FieldType playerType, WinRule rule) {
    for (int y = 0; y < fields.length - rule.limitY; y++) {
      var row = fields[y];
      for (int x = 0; x < row.length - rule.limitX; x++) {
        boolean win = true;
        for (var place : rule.generatePlacesToCheck(x, y)) {
          if (fields[place.y][place.x].type != playerType) {
            win = false;
            break;
          }
        }
        if (win) {
          System.out.println(playerType.toString() + " win by " + rule.name);
          return true;
        }
      }
    }
    return false;
  }

  Field[][] getFields() {
    return fields;
  }

  boolean trySetFieldSymbol(FieldType symbol, int x, int y) {
    Field selected = fields[y][x];
    if (selected.type == FieldType.NONE) {
      selected.type = symbol;
      return true;
    }
    return false;
  }
}
