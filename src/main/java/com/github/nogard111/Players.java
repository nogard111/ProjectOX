package com.github.nogard111;


public class Players {
  private final Player playerO;
  private final Player playerX;
  private Player currentPlayer;
  private Player startPlayer;

  public Players(String playerOName, String playerXName, FieldType startPlayerSymbol) {
    playerO = new Player(playerOName, FieldType.O);
    playerX = new Player(playerXName, FieldType.X);
    playerX.setNext(playerO);
    playerO.setNext(playerX);
    this.startPlayer = startPlayerSymbol == playerO.symbol ? playerO : playerX;
    this.currentPlayer = startPlayer;
  }

  public String getPlayersStringWithHighestScore() {
    if (playerO.score == playerX.score) {
      return "NONE !";
    }
    return playerO.score > playerX.score ? playerO.name : playerX.name;
  }

  public String getAllPlayersScore() {
    return playerO.getScore() + " " + playerX.getScore();
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public void switchPlayer() {
    currentPlayer = currentPlayer.getNextPlayer();
  }

  public Player[] getPlayers() {
    return new Player[]{playerO, playerX};
  }

  public void finishStage() {
    startPlayer = startPlayer.getNextPlayer();
    currentPlayer = startPlayer;
  }
}
