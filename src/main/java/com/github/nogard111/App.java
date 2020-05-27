package com.github.nogard111;

/**
 * OX GAME.
 */
public class App {
  /**
   * Start app.
   *
   * @param args params from console
   */
  public static void main(String[] args) {

    GameDialog dialog = new GameDialog();
    dialog.pack();
    dialog.setVisible(true);
    System.exit(0);
  }
}


