package com.github.nogard111.OXGame;

import java.util.Map;
import java.util.function.Function;

enum GameState {
  INITIALIZATION,
  USER_ACTION,
  UPDATE_BOARD,
  WIN_STAGE,
  WIN_GAME
}

class GameplayState {
  GameState state;
  Function<Object, Boolean> stateInitialAction;
  Map<GameState, Function<Object, Boolean>> possibleNextState;

  public GameplayState(GameState state, Function<Object, Boolean> stateInitialAction, Map<GameState, Function<Object, Boolean>> possibleNextStateTransitions) {
    this.state = state;
    this.possibleNextState = possibleNextStateTransitions;
    this.stateInitialAction = stateInitialAction;
  }
}

interface IStateMachine
{
  void updateState(GameState requestedState);
}


class WinState
{
  IStateMachine machine;

  public WinState() {
  }

  void Action()
  {
    machine.updateState(GameState state );
  }
}

