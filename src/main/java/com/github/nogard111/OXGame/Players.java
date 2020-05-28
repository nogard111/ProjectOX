package com.github.nogard111.OXGame;


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

  String getPlayersStringWithHighestScore() {
    if (playerO.score == playerX.score) {
      return "NONE !";
    }
    return playerO.score > playerX.score ? playerO.name : playerX.name;
  }

  String getAllPlayersScore() {
    return playerO.getScore() + " " + playerX.getScore();
  }

  Player getCurrentPlayer() {
    return currentPlayer;
  }

  void switchPlayer() {
    currentPlayer = currentPlayer.getNextPlayer();
  }

  Player[] getPlayers() {
    return new Player[]{playerO, playerX};
  }

  void finishStage() {
    startPlayer = startPlayer.getNextPlayer();
    currentPlayer = startPlayer;
  }
}
