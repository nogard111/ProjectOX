package com.github.nogard111.OXGame;

public class Player {
  public final String name;
  public int score = 0;
  public FieldType symbol;
  private Player nextPlayer;


  public Player(String playerName, FieldType symbol) {
    name = playerName;
    this.symbol = symbol;
  }

  public String getScore() {
    return name + " : " + score + "p ";
  }

  public void updateResultTie() {
    score++;
  }

  public void updateScoreWin() {
    score += 3;
  }

  public void setNext(Player player) {
    nextPlayer = player;
  }

  public Player getNextPlayer() {
    return nextPlayer;
  }
}
