package com.github.nogard111.OXGame;

import java.util.ArrayList;
import java.util.List;

class MultiSubscriber implements GameNotifications,IGameNotificationPublisher
{
  List<GameNotifications> subscribers = new ArrayList<>();

  @Override
  public void displayMessage(String message) {
    subscribers.forEach(i -> i.displayMessage(message));
  }

  @Override
  public void displayScore(String message) {
    subscribers.forEach(i -> i.displayScore(message));
  }

  @Override
  public void showWinnerMessage(String winnerMessage) {
    subscribers.forEach(i -> i.showWinnerMessage(winnerMessage));
  }

  @Override
  public void showFinalWinnerAndClose(String winnerMessage) {
    subscribers.forEach(i -> i.showFinalWinnerAndClose(winnerMessage));
  }

  @Override
  public void addSubscriber(GameNotifications subscriber) {
    subscribers.add(subscriber);
  }
}
