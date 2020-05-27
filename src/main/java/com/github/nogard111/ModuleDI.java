package com.github.nogard111;

import com.google.inject.AbstractModule;

public class ModuleDI extends AbstractModule {
  @Override
  protected void configure() {
    bind(IGameEngine.class).to(GameEngine.class);
  }
}
