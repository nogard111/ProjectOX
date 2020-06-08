package com.github.nogard111.OXGame;

import com.github.nogard111.OXGameUI.GameDialog;
import com.github.nogard111.logging.DefaultLogger;
import com.github.nogard111.logging.Logger4j;
import com.google.inject.Guice;
import com.google.inject.Injector;

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
    if (args.length > 0 && args[0].equals("L4J")) {
      DefaultLogger.setLogger(new Logger4j());
      DefaultLogger.getLogger().logInfo("Logger4j selected as a logger");
    } else {
      DefaultLogger.getLogger().logInfo("Logger4j can be selected by providing \"L4J\" as a argument");
    }

    Injector injector = Guice.createInjector(new ModuleDI());
    var game = injector.getInstance(IGameEngine.class);

    GameDialog dialog = new GameDialog(game);
    dialog.pack();
    dialog.setVisible(true);
  }
}


