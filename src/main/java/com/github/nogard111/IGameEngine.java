package com.github.nogard111;

public interface IGameEngine {
  /**
   * Input click to interact with game.
   * @param x Ratio of the board from 0 to 1
   * @param y Ratio of the board from 0 to 1
   * @return Does click cause change
   */
  boolean clicked(float x, float y);

  Field[][] getFields();

  void onStart(GameNotifications gameDialog);
}
