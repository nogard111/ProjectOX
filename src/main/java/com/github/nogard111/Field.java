package com.github.nogard111;

public class Field {
  public final int xPos;
  public final int yPos;
  public FieldType type = FieldType.NONE;

  public Field(int x, int y) {
    xPos = x;
    yPos = y;
  }
}
