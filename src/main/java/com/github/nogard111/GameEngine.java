package com.github.nogard111;

import java.util.EnumMap;
import java.util.Map;

public class GameEngine implements IGameEngine {
  private int gamesToPlay = 3;
  private final int sizeY;
  private final int sizeX;
  private final int lenToWin;
  private final Field[][] fields;
  private FieldType currentPlayer;
  private FieldType startPlayer;
  private final Map<FieldType, Player> players = new EnumMap<>(FieldType.class);
  private final GameNotifications notificationsPresenter;

  /**
   * @param notificationPresenter
   * @param config
   */
  public GameEngine(final GameNotifications notificationPresenter, GameConfig config) {
    notificationsPresenter = notificationPresenter;

    String playerOName = config.playerOName;
    String playerXName = config.playerXName;
    sizeX = config.columnSize;
    sizeY = config.rowSize;
    lenToWin = config.lenToWin;
    startPlayer = config.startingPlayer;
    currentPlayer = config.startingPlayer;

    players.put(FieldType.O, new Player(playerOName));
    players.put(FieldType.X, new Player(playerXName));

    fields = BoardHelper.GenerateFields(sizeY, sizeX);

  }

  private static FieldType getOppositePlayer(FieldType currentPlayer) {
    return currentPlayer == FieldType.O ? FieldType.X : FieldType.O;
  }

  /**
   * @param x Ratio of the board from 0 to 1
   * @param y Ratio of the board from 0 to 1
   * @return Does click cause change
   */
  @Override
  public boolean clicked(float x, float y) {
    Field selected = getField(x, y);
    if (selected.type == FieldType.NONE) {
      selected.type = currentPlayer;
      var winners = getWinners();
      if (winners == null) {
        switchPlayer();
        displayWhoShouldMove();
      } else {
        finishStage(winners);
      }
      return true;
    }
    return false;
  }

  void displayWhoShouldMove() {
    notificationsPresenter.displayMessage("Make your move " + players.get(currentPlayer).name);
  }

  private void finishStage(FieldType[] winners) {
    String winnerMessage;
    if (winners.length == 2) {
      for (var winner : winners) {
        ++players.get(winner).score;
      }
      winnerMessage = "It's Tie";
    } else {
      var winner = players.get(winners[0]);
      winner.score += 3;
      winnerMessage = winner.name + " wins!";
    }

    showScore();

    gamesToPlay--;
    if (gamesToPlay == 0) {
      notificationsPresenter.showFinalWinnerAndClose("FINAL WINNER IS : " + getWinner());
    } else {
      clearCurrentStage();
      notificationsPresenter.showWinnerMessage(winnerMessage);
    }

    startPlayer = getOppositePlayer(startPlayer);
    currentPlayer = startPlayer;
    displayWhoShouldMove();
  }

  private void showScore() {
    var score = players.get(FieldType.O).getScore() + " " + players.get(FieldType.X).getScore();
    notificationsPresenter.displayScore(score);
  }

  private void clearCurrentStage() {
    for (int y = 0; y < fields.length; y++) {
      var row = fields[y];
      for (int x = 0; x < row.length; x++) {
        row[x].type = FieldType.NONE;
      }
    }
  }

  private String getWinner() {
    var player1 = players.get(FieldType.O);
    var player2 = players.get(FieldType.X);

    if (player1.score == player2.score) {
      return "NONE !";
    }
    return player1.score > player2.score ? player1.name : player2.name;
  }

  private FieldType[] getWinners() {
    var collection = BoardHelper.getStandardRules(lenToWin).values();
    WinRule[] winRules = collection.toArray(new WinRule[collection.size()]);

    //check winner
    if (BoardHelper.IsPlayerAWinner(winRules, currentPlayer, fields)) {
      return new FieldType[]{currentPlayer};
    }

    //all filled -> tie
    boolean emptyFieldExists = false;
    for (int y = 0; y < fields.length; y++) {
      var row = fields[y];
      for (int x = 0; x < row.length; x++) {
        if (row[x].type == FieldType.NONE) {
          emptyFieldExists = true;
          break;
        }
      }
      if (emptyFieldExists) break;
    }
    return emptyFieldExists ? null : new FieldType[]{FieldType.X, FieldType.O};
  }

  private Field getField(float x, float y) {
    return fields[(int) (y * sizeY)][(int) (x * sizeX)];
  }

  private void switchPlayer() {
    currentPlayer = getOppositePlayer(currentPlayer);
  }

  @Override
  public Field[][] getFields() {
    return fields;
  }

  @Override
  public void onStart() {
    showScore();
    displayWhoShouldMove();
  }
}
