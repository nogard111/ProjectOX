package com.github.nogard111.OXGame;

public interface GameNotifications {
  void displayMessage(String message);

  void displayScore(String message);

  void showWinnerMessage(String winnerMessage);

  void showFinalWinnerAndClose(String winnerMessage);
}
