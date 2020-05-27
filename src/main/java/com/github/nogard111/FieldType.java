package com.github.nogard111;

import java.util.NoSuchElementException;

public enum FieldType {
  NONE,
  O,
  X;

  public static FieldType convertToFieldType(String fieldTypeName) throws NoSuchElementException {
    FieldType fieldType;
    switch (fieldTypeName) {
      case "o":
      case "O":
        fieldType = FieldType.O;
        break;
      case "x":
      case "X":
        fieldType = FieldType.X;
        break;
      default:
        throw new NoSuchElementException();
    }
    return fieldType;
  }
}
