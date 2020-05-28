package com.github.nogard111.OXGame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;


@RunWith(Parameterized.class)
public class WinChecks {
  private Point[] inputPoints;
  private byte boardSizeX;
  private byte boardSizeY;
  private boolean expectedResult;
  private byte lenToWin;

  public WinChecks(Point[] inputPoints, int boardSizeX, int boardSizeY, int lenToWin, boolean expectedResult) {
    this.inputPoints = inputPoints;
    this.expectedResult = expectedResult;
    this.boardSizeX = (byte) boardSizeX;
    this.boardSizeY = (byte) boardSizeY;
    this.lenToWin = (byte) lenToWin;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> automaticGameParameters() {
    return Arrays.asList(new Object[][]{
        {
            //1x1 board
            new Point[]{
                new Point(0, 0)}
            , 1, 1, 1, true}, {
        new Point[]{
            new Point(0, 0)}
        , 1, 1, 2, false}

        //2x2 board
        , {
        new Point[]{
            new Point(0, 0),
            new Point(0, 0)}
        , 2, 2, 2, false}
        , {
        new Point[]{
            new Point(0, 0),
            new Point(0, 1)}
        , 2, 2, 2, true}
        , {
        new Point[]{
            new Point(0, 0),
            new Point(1, 0)}
        , 2, 2, 2, true}
        , {
        new Point[]{
            new Point(0, 0),
            new Point(1, 1)}
        , 2, 2, 2, true}
        , {
        new Point[]{
            new Point(0, 1),
            new Point(1, 0)}
        , 2, 2, 2, true}
        /// 3x3
        , {
        new Point[]{
            new Point(0, 1),
            new Point(1, 0)}
        , 3, 3, 2, true}
        , {
        new Point[]{
            new Point(0, 1),
            new Point(1, 0)}
        , 3, 3, 3, false}
        , {
        new Point[]{
            new Point(0, 0),
            new Point(0, 1),
            new Point(0, 2)}
        , 3, 3, 3, true}
        , {
        new Point[]{
            new Point(1, 0),
            new Point(1, 1),
            new Point(1, 2)}
        , 3, 3, 3, true}
        , {
        new Point[]{
            new Point(0, 0),
            new Point(1, 1),
            new Point(2, 2)}
        , 3, 3, 3, true}
        , {
        new Point[]{
            new Point(0, 1),
            new Point(1, 1),
            new Point(2, 1)}
        , 3, 3, 3, true}
        , {
        new Point[]{
            new Point(0, 2),
            new Point(1, 2),
            new Point(2, 2)}
        , 3, 3, 3, true}
        , {
        new Point[]{
            new Point(0, 2),
            new Point(1, 1),
            new Point(2, 0)}
        , 3, 3, 3, true}
        /// 2x4
        , {
        new Point[]{
            new Point(0, 2),
            new Point(0, 1),
            new Point(0, 0)}
        , 2, 4, 3, true}
        , {
        new Point[]{
            new Point(0, 0),
            new Point(1, 0)}
        , 2, 4, 2, true}
    });
  }

  @Test
  public void testPrimeNumberChecker() {
    var rulesCollection = GameRules.getStandardRules(lenToWin).values();
    var board = new Board(boardSizeX, boardSizeY);

    //act
    for (var place : inputPoints) {
      board.trySetFieldSymbol(FieldType.O, place.x, place.y);
    }

    assertEquals(expectedResult,
        board.isPlayerAWinner(rulesCollection, FieldType.O));
  }
}


