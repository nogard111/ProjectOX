package com.github.nogard111.OXGame;


import com.github.nogard111.configuration.ConfigLoader;
import com.github.nogard111.configuration.StreamProvider;
import com.google.inject.AbstractModule;

public class ModuleDI extends AbstractModule {
  @Override
  protected void configure() {
    bind(IGameEngine.class).to(GameEngine.class);
    //bind(ConfigLoader.class).toProvider(() -> new ConfigLoader(new StreamProvider(System.in)));
    bind(GameConfig.class).toProvider(
        () -> new ConfigLoader(new StreamProvider(System.in)).getConfigFromUser() )
        .asEagerSingleton();
  }
}