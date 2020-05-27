package com.github.nogard111;

public class GameConfig {

  public final byte columnSize;
  public final byte rowSize;
  public final String playerOName;
  public final String playerXName;
  public final byte lenToWin;
  public final FieldType startingPlayerType;

  public GameConfig(byte columnSize, byte rowSize, byte lenToWin, String playerOName, String playerXName, FieldType startingPlayerType) {
    this.columnSize = columnSize;
    this.rowSize = rowSize;
    this.playerOName = playerOName;
    this.playerXName = playerXName;
    this.lenToWin = lenToWin;
    this.startingPlayerType = startingPlayerType;
  }
}
