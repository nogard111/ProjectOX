package com.github.nogard111;

public class Player {
    public final String name;
    public int score = 0;

    public Player(String playerName) {
        name = playerName;
    }

    public String getScore() {
        return name + " " + score + "p";
    }
}
