package com.github.nogard111;

public interface IGameEngine {
    boolean clicked(float x, float y);

    Field[][] getFields();

    void onStart();
}
