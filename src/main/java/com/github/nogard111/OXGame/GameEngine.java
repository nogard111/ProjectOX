package com.github.nogard111.OXGame;

import com.github.nogard111.logging.DefaultLogger;

public class GameEngine implements IGameEngine, IGameNotificationPublisher {
  private final int sizeY;
  private final int sizeX;
  private final int lenToWin;
  private final Board board;
  private int gamesToPlay = 3;
  private final Players players;
  private MultiSubscriber notificationsPresenter = new MultiSubscriber();

  /**
   * @param config : configuration of the game
   */
  public GameEngine(GameConfig config) {
    sizeX = config.columnSize;
    sizeY = config.rowSize;
    lenToWin = config.lenToWin;

    players = new Players(config.playerOName, config.playerXName, config.startingPlayerType);
    board = new Board(sizeX, sizeY);
  }

  /**
   * @param x Ratio of the board from 0 to 1
   * @param y Ratio of the board from 0 to 1
   * @return Does click cause change
   */
  @Override
  public boolean clicked(float x, float y) {
    var current = players.getCurrentPlayer();
    DefaultLogger.getLogger().logInfo("Player " + current.name + " trying to set field at: " + x + " " + y + " ");
    if (board.trySetFieldSymbol(players.getCurrentPlayer().symbol,
        (int) (x * sizeX),
        (int) (y * sizeY))) {
      var winners = getWinners();
      if (winners == null) {
        players.switchPlayer();
        displayWhoShouldMove();
      } else {
        finishStage(winners);
      }
      return true;
    }
    return false;
  }

  void displayWhoShouldMove() {
    notificationsPresenter.displayMessage("Make your move " + players.getCurrentPlayer().name);
  }

  private void finishStage(Player[] winners) {
    String winnerMessage;
    if (winners.length == 2) {
      winnerMessage = "It's a Tie";
      for (Player winner : winners) {
        winner.updateResultTie();
      }
    } else {
      players.getCurrentPlayer().updateScoreWin();
      winnerMessage = players.getCurrentPlayer().name + " wins!";
    }

    showScore();

    gamesToPlay--;
    if (gamesToPlay == 0) {
      String finalWinnerMessage = "FINAL WINNER IS : " + players.getPlayersStringWithHighestScore();
      DefaultLogger.getLogger().logInfo(finalWinnerMessage);
      notificationsPresenter.showFinalWinnerAndClose(finalWinnerMessage);
    } else {
      board.clearBoard();
      notificationsPresenter.showWinnerMessage(winnerMessage);
    }

    players.finishStage();
    displayWhoShouldMove();
  }

  private void showScore() {
    var score = players.getAllPlayersScore();
    notificationsPresenter.displayScore(score);
  }

  private Player[] getWinners() {
    var winRules = GameRules.getStandardRules(lenToWin).values();

    //check winner
    if (board.isPlayerAWinner(winRules, players.getCurrentPlayer().symbol)) {
      return new Player[]{players.getCurrentPlayer()};
    }

    //all filled -> tie
    boolean emptyFieldExists = !board.areAllFieldsFilled();
    return emptyFieldExists ? null : players.getPlayers();
  }

  /*
  @docParten
   */
  @Override
  public Field[][] getFields() {
    return board.getFields();
  }

  @Override
  public void onStart(final GameNotifications notificationPresenter) {
    addSubscriber(notificationPresenter);
    DefaultLogger.getLogger().logInfo("Game started");
    showScore();
    displayWhoShouldMove();
  }

  @Override
  public void addSubscriber(GameNotifications subscriber) {
    notificationsPresenter.addSubscriber(subscriber);
  }
}
