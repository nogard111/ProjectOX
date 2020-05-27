package com.github.nogard111.configuration;

import com.github.nogard111.FieldType;
import com.github.nogard111.GameConfig;
import com.github.nogard111.YouGotToBeKiddingException;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConfigLoader implements IConfigLoader {
  private final InputStream stream;

  public ConfigLoader(InputStream in) {
    stream = in;
  }


  @Override
  public GameConfig getConfigFromUser() {
    GameConfig config = null;
    System.out.println("Let's Start with some little config :)");
    boolean ready = false;
    while (!ready) {
      try {
        var scanner = new Scanner(stream);
        System.out.println("You need do everything correctly in one go");

        System.out.println("Board size columns:");
        var columnSize = scanner.nextByte();
        System.out.println("Board size rows:");
        var rowSize = scanner.nextByte();
        System.out.println("Symbols in line to win:");
        var symbolsInLineToWin = scanner.nextByte();

        if (columnSize > 20 || rowSize > 20 || (symbolsInLineToWin > rowSize && symbolsInLineToWin > columnSize)) {
          throw new YouGotToBeKiddingException();
        }

        System.out.println("Player O Name:");
        var playerOName = scanner.next();
        System.out.println("Player X Name:");
        var playerXName = scanner.next();

        System.out.println("Who start (o/x)? :");
        var playerToStartStr = scanner.next();
        FieldType playerToStart = convertToFieldType(playerToStartStr);

        config = new GameConfig(columnSize, rowSize, symbolsInLineToWin, playerOName, playerXName, playerToStart);
        ready = true;
      } catch (NoSuchElementException exception) {
        System.out.println("Sorry you typed something wrong ...");
      } catch (YouGotToBeKiddingException e) {
        System.out.println("Sorry your input parameters are not very wise...");
      }
    }
    return config;
  }

  FieldType convertToFieldType(String playerToStartStr) throws NoSuchElementException {
    FieldType playerToStart;
    switch (playerToStartStr) {
      case "o":
      case "O":
        playerToStart = FieldType.O;
        break;
      case "x":
      case "X":
        playerToStart = FieldType.X;
        break;
      default:
        throw new NoSuchElementException();
    }
    return playerToStart;
  }
}

