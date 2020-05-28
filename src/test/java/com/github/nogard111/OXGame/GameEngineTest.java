package com.github.nogard111.OXGame;

import com.github.nogard111.OXGame.FieldType;
import com.github.nogard111.OXGame.GameConfig;
import com.github.nogard111.OXGame.GameEngine;
import com.github.nogard111.OXGame.GameNotifications;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.internal.verification.VerificationModeFactory.times;

// Mockito info
//Mockito.when(mockList.size()).thenReturn(100);

public class GameEngineTest {
    static GameConfig config3by3 = new GameConfig((byte) 3, (byte) 3, (byte) 3, "Adam", "Ewa", FieldType.O);
    static GameConfig config1by1 = new GameConfig((byte) 1, (byte) 1, (byte) 1, "Adam", "Ewa", FieldType.O);

    /**
     * ClickTest
     */
    @Test
    public void ClickTest() {
        //arrange
        GameNotifications mockedPresenter = mock(GameNotifications.class);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

        var engine = new GameEngine(config3by3);
        engine.onStart(mockedPresenter);

        engine.clicked(0, 0);
        var fields = engine.getFields();

        assertNotSame(fields[0][0].type, FieldType.NONE);
        Mockito.verify(mockedPresenter, times(2)).displayMessage(arg.capture());
    }

    /**
     * Win Test
     */
    @Test
    public void WinTest() {
        //arrange
        GameNotifications mockedPresenter = mock(GameNotifications.class);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
        var engine = new GameEngine( config3by3);
        engine.onStart(mockedPresenter);
        Point[] points = new Point[]{
                new Point(0, 0),//player 1
                new Point(0, 1),
                new Point(1, 0),//player 1
                new Point(1, 1),
                new Point(2, 0),//player 1
        };

        //act
        for (var pt : points) {
            engine.clicked(GetXRatio(config3by3, pt.x), GetYRatio(config3by3, pt.y));
        }

        //assert
        Mockito.verify(mockedPresenter).showWinnerMessage(arg.capture());
    }

    /**
     * Win Test
     */
    @Test
    public void WinTestWithNotAllowedMove() {
        //arrange
        GameNotifications mockedPresenter = mock(GameNotifications.class);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
        var engine = new GameEngine(config3by3);
        engine.onStart(mockedPresenter);
        Point[] points = new Point[]{
                new Point(0, 0),//player 1
                new Point(0, 1),
                new Point(0, 1),//player 1 // not allowed
                new Point(1, 0),//player 1
                new Point(1, 1),
                new Point(2, 0),//player 1
        };

        //act
        for (var pt : points) {
            engine.clicked(GetXRatio(config3by3, pt.x), GetYRatio(config3by3, pt.y));
        }

        //assert
        Mockito.verify(mockedPresenter).showWinnerMessage(arg.capture());
    }

    private float GetXRatio(GameConfig config, int i) {
        return (float) (i) / (float) config.columnSize + 0.00001f;
    }

    private float GetYRatio(GameConfig config, int i) {
        return (float) (i) / (float) config.rowSize + 0.00001f;
    }

    /**
     * Tie Test triple
     */
    @Test
    public void TripleTieTest() {
        //arrange
        GameNotifications mockedPresenter = mock(GameNotifications.class);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
        var engine = new GameEngine(config3by3);
        engine.onStart(mockedPresenter);
        Point[] points = new Point[]{
                new Point(0, 0),//player 1
                new Point(0, 1),
                new Point(1, 0),//player 1
                new Point(1, 1),

                new Point(2, 1),//player 1
                new Point(2, 0),
                new Point(0, 2),//player 1
                new Point(1, 2),

                new Point(2, 2),//player 1
        };

        //act
        for (var pt : points) {
            engine.clicked(GetXRatio(config3by3, pt.x), GetYRatio(config3by3, pt.y));
        }
        for (var pt : points) {
            engine.clicked(GetXRatio(config3by3, pt.x), GetYRatio(config3by3, pt.y));
        }
        for (var pt : points) {
            engine.clicked(GetXRatio(config3by3, pt.x), GetYRatio(config3by3, pt.y));
        }

        //assert
        Mockito.verify(mockedPresenter,times(2)).showWinnerMessage(arg.capture());
        System.out.println(arg.getValue());
    }

    /**
     * Final Win Test
     */
    @Test
    public void FinalWinTest() {
        //arrange
        GameNotifications mockedPresenter = mock(GameNotifications.class);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
        var engine = new GameEngine(config1by1);
        engine.onStart(mockedPresenter);

        //board 1x1 only 1 click for win
        Point[] points = new Point[]{
                new Point(0, 0),//player 1
                new Point(0, 1),
                new Point(1, 0),//player 1
        };

        //act
        for (var pt : points) {
            engine.clicked(GetXRatio(config3by3, pt.x), GetYRatio(config3by3, pt.y));
        }

        //assert
        Mockito.verify(mockedPresenter).showFinalWinnerAndClose(arg.capture());
    }
}
