package com.github.nogard111;

public class GameConfig {

    public final byte columnSize;
    public final byte rowSize;
    public final String playerO;
    public final String playerX;
    public final byte lenToWin;
    public final FieldType startingPlayer;

    public GameConfig(byte columnSize, byte rowSize, byte lenToWin, String playerO, String playerX, FieldType startingPlayer) {
        this.columnSize = columnSize;
        this.rowSize = rowSize;
        this.playerO = playerO;
        this.playerX = playerX;
        this.lenToWin = lenToWin;
        this.startingPlayer = startingPlayer;
    }
}
