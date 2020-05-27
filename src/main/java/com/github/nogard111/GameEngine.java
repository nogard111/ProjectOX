package com.github.nogard111;

public class GameEngine implements IGameEngine {
  private int gamesToPlay = 3;
  private final int sizeY;
  private final int sizeX;
  private final int lenToWin;
  private final Board board;
  private Players players;
  private final GameNotifications notificationsPresenter;

  /**
   * @param notificationPresenter
   * @param config
   */
  public GameEngine(final GameNotifications notificationPresenter, GameConfig config) {
    notificationsPresenter = notificationPresenter;

    sizeX = config.columnSize;
    sizeY = config.rowSize;
    lenToWin = config.lenToWin;

    players = new Players(config.playerOName,config.playerXName,config.startingPlayerType);

    board = new Board(sizeY, sizeX);

  }

  /**
   * @param x Ratio of the board from 0 to 1
   * @param y Ratio of the board from 0 to 1
   * @return Does click cause change
   */
  @Override
  public boolean clicked(float x, float y) {
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
      winnerMessage = "It's Tie";
      for (Player winner : winners) {
        winner.UpdateResultTie();
      }
    } else {
      players.getCurrentPlayer().updateScoreWin();
      winnerMessage = players.getCurrentPlayer().name + " wins!";
    }

    showScore();

    gamesToPlay--;
    if (gamesToPlay == 0) {
      notificationsPresenter.showFinalWinnerAndClose("FINAL WINNER IS : " + players.getPlayersStringWithHighestScore());
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
    var collection = BoardHelper.getStandardRules(lenToWin).values();
    WinRule[] winRules = collection.toArray(new WinRule[collection.size()]);

    //check winner
    if (board.IsPlayerAWinner(winRules, players.getCurrentPlayer().symbol)) {
      return new Player[]{players.getCurrentPlayer()};
    }

    //all filled -> tie
    boolean emptyFieldExists = board.areAllFieldsFilled();
    return emptyFieldExists ? null : players.getPlayers();
  }

  @Override
  public Field[][] getFields() {
    return board.getFields();
  }

  @Override
  public void onStart() {
    showScore();
    displayWhoShouldMove();
  }
}
