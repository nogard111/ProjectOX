package com.github.nogard111.OXGame;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FieldTypeTest {

  /**
   * convert to O
   */
  @Test
  public void convertO()
  {
    assertEquals(FieldType.O,FieldType.convertToFieldType("o"));
  }

  /**
   * convert to X
   */
  @Test
  public void convertX()
  {
    assertEquals(FieldType.X,FieldType.convertToFieldType("x"));
  }
}
