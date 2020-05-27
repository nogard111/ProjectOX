package com.github.nogard111.configuration;

import com.github.nogard111.FieldType;
import com.github.nogard111.GameConfig;
import com.github.nogard111.YouGotToBeKiddingException;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConfigLoader implements IConfigLoader {
    private InputStream stream;

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
                System.out.println("Symbols in row to win:");
                var rowToWin = scanner.nextByte();

                if (columnSize > 20 || rowSize > 20 || (rowToWin > rowSize && rowToWin > columnSize)) {
                    throw new YouGotToBeKiddingException();
                }

                System.out.println("Player O Name:");
                var playerO = scanner.next();
                System.out.println("Player X Name:");
                var playerX = scanner.next();

                System.out.println("Who start (o/x)? :");
                var playerToStartStr = scanner.next();
                FieldType playerToStart;
                switch (playerToStartStr)
                {
                    case "o":
                    case "O":
                        playerToStart = FieldType.O;
                        break;
                    case "X":
                    case "x":
                        playerToStart = FieldType.X;
                        break;
                    default:
                        throw new YouGotToBeKiddingException();
                }

                config = new GameConfig(columnSize, rowSize, rowToWin, playerO, playerX, playerToStart);
                ready = true;
            } catch (NoSuchElementException exception) {
                System.out.println("Sorry you typed something wrong ...");
            } catch (YouGotToBeKiddingException e) {
                System.out.println("Sorry your input parameters are not very wise...");
            }
        }
        return config;
    }
}

