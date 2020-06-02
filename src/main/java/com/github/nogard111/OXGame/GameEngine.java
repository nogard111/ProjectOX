package com.github.nogard111.OXGame;

import com.github.nogard111.logging.DefaultLogger;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class GameEngine implements IGameEngine, IGameNotificationPublisher, IStateMachine {
  private final int sizeY;
  private final int sizeX;
  private final int lenToWin;
  private final Board board;
  private final Function<Object, Boolean> emptyCorrectFunction = (Object obj) -> {
    return true;
  };
  private int gamesToPlay = 3;
  private final Players players;
  private final MultiSubscriber notificationsPresenter = new MultiSubscriber();
  private GameState currentState = GameState.INITIALIZATION;
  private final Map<GameState,GameplayState> gameStates;

  /**
   * @param config : configuration of the game
   */
  public GameEngine(GameConfig config) {
    sizeX = config.columnSize;
    sizeY = config.rowSize;
    lenToWin = config.lenToWin;

    players = new Players(config.playerOName, config.playerXName, config.startingPlayerType);
    board = new Board(sizeX, sizeY);

    gameStates = new EnumMap<GameState, GameplayState>(GameState.class);
    var GameStatesList = new ArrayList<GameplayState>();
    GameStatesList.add(
        new GameplayState(GameState.INITIALIZATION,emptyCorrectFunction,
            Map.of(GameState.USER_ACTION, emptyCorrectFunction))
    );
    GameStatesList.add(
        new GameplayState(GameState.USER_ACTION,emptyCorrectFunction,
            Map.of(
                GameState.UPDATE_BOARD, emptyCorrectFunction))
    );
    GameStatesList.add(
        new GameplayState(GameState.UPDATE_BOARD,emptyCorrectFunction,
            Map.of(
                GameState.USER_ACTION, emptyCorrectFunction))
    );

    for (var state : GameStatesList) {
      gameStates.put(state.state, state);
    }
  }

  /**
   * @param x Ratio of the board from 0 to 1
   * @param y Ratio of the board from 0 to 1
   * @return Does click cause change
   */
  @Override
  public boolean clicked(float x, float y) {
    return stateHandlerUserAction(x, y);
  }

  boolean stateHandlerUserAction(float x, float y) {
    var current = players.getCurrentPlayer();
    DefaultLogger.getLogger().logInfo("Player " + current.name + " trying to set field at: " + x + " " + y + " ");
    if (board.trySetFieldSymbol(players.getCurrentPlayer().symbol,
        (int) (x * sizeX),
        (int) (y * sizeY))) {
      stateHandlerUpdateBoard();
      return true;
    }
    return false;
  }

  void stateHandlerUpdateBoard() {
    var winners = getWinners();
    if (winners == null) {
      players.switchPlayer();
      displayWhoShouldMove();
    } else {
      stateHandlerFinishStage(winners);
    }
  }

  void displayWhoShouldMove() {
    notificationsPresenter.displayMessage("Make your move " + players.getCurrentPlayer().name);
  }

  private void stateHandlerFinishStage(Player[] winners) {
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
      stateHandlerFinalFinishGame();
    } else {
      stateTransitionWinToUserAction(winnerMessage);
    }
  }

  private void stateTransitionWinToUserAction(String winnerMessage) {
    board.clearBoard();
    notificationsPresenter.showWinnerMessage(winnerMessage);
    players.finishStage();
    displayWhoShouldMove();
  }

  private void stateHandlerFinalFinishGame() {
    String finalWinnerMessage = "FINAL WINNER IS : " + players.getPlayersStringWithHighestScore();
    DefaultLogger.getLogger().logInfo(finalWinnerMessage);
    notificationsPresenter.showFinalWinnerAndClose(finalWinnerMessage);
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
    updateState(GameState.USER_ACTION);
  }

  @Override
  public void addSubscriber(GameNotifications subscriber) {
    notificationsPresenter.addSubscriber(subscriber);
  }

  public void updateState(GameState requestedState)
  {
    var state = this.gameStates.get(currentState);
    if(state.possibleNextState.containsKey(requestedState)){
      var transitionAction = state.possibleNextState.get(requestedState);
      if(transitionAction.apply(null))
      {
        currentState = requestedState;
        this.gameStates.get(currentState).stateInitialAction.apply(null);

      }else{
        DefaultLogger.getLogger().logError("Transition "+currentState+" to "+requestedState+" failed ",null);
      }
    }else{
      DefaultLogger.getLogger().logError("Transition "+currentState+" to "+requestedState+" is not possible ",null);
    }
  }
}
