package com.github.nogard111;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

// Mockito info
//Mockito.when(mockList.size()).thenReturn(100);

public class GameEngineTest {
    static GameConfig config = new GameConfig((byte)3,(byte)4,(byte)3,"Adam","Ewa",FieldType.O);

    /**
     * ClickTest
     */
    @Test
    public void ClickTest()
    {
        //arrange
        GameNotifications mockedPresenter = mock(GameNotifications.class);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

        var engine = new GameEngine(mockedPresenter,config);

        engine.clicked(0,0);
        var fields = engine.getFields();

        assertNotSame(fields[0][0].type, FieldType.NONE);
        Mockito.verify(mockedPresenter).displayMessage(arg.capture());
    }
    /**
     * Win Test
     */
    @Test
    public void WinTest()
    {
        //arrange
        GameNotifications mockedPresenter = mock(GameNotifications.class);

        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);


        var engine = new GameEngine(mockedPresenter,config);

        engine.clicked(0,0);//player 1
        engine.clicked(0,0.45f);
        engine.clicked(0.45f,0);//player 1
        engine.clicked(0.45f,0.45f);
        engine.clicked(0.99f,0);//player 1

        Mockito.verify(mockedPresenter).showWinnerMessage(arg.capture());
    }
}
