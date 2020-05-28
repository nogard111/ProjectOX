package com.github.nogard111.OXGame;

import com.github.nogard111.configuration.ConfigLoader;
import com.github.nogard111.configuration.StreamProvider;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ConfigLoaderTests {

  String properConfigString = "3\n\r" +
          "4\n\r" +
          "2\n\r" +
          "M\n\r" +
          "T\n\r" +
          "o\n\r";
  /**
   * Load proper config
   */
  @Test
  public void LoadProperConfigTest()
  {
    InputStream stream = new ByteArrayInputStream(properConfigString.getBytes(StandardCharsets.UTF_8));
    //arrange
    var loader = new ConfigLoader(new StreamProvider(stream));
    var config = loader.getConfigFromUser();


    assertEquals(4,config.rowSize);
    assertEquals(3,config.columnSize);
    assertEquals(2,config.lenToWin);
    assertEquals("M",config.playerOName);
    assertEquals("T",config.playerXName);
  }

  /**
   * Load proper config on second try
   */
  @Test
  public void LoadProperConfigAfterWrongSymbolTest()
  {
    String wrongConfigString = "3\n\r" +
            "4\n\r" +
            "2\n\r" +
            "M\n\r" +
            "T\n\r" +
            "Q\n\r";//wrong will try again

    InputStream wrongStream = new ByteArrayInputStream(wrongConfigString.getBytes(StandardCharsets.UTF_8));
    InputStream properStream = new ByteArrayInputStream(properConfigString.getBytes(StandardCharsets.UTF_8));
    //arrange

    var streamProvider = mock(StreamProvider.class);
    Mockito.when(streamProvider.getStream()).thenAnswer(new Answer() {
      private int count = 0;

      public Object answer(InvocationOnMock invocation) {
        if (count++ == 0)
          return wrongStream;

        return properStream;
      }
    });

    var loader = new ConfigLoader(streamProvider);
    var config = loader.getConfigFromUser();


    assertEquals(4,config.rowSize);
    assertEquals(3,config.columnSize);
    assertEquals(2,config.lenToWin);
    assertEquals("M",config.playerOName);
    assertEquals("T",config.playerXName);
  }


  /**
   * Load proper config on second try
   */
  @Test
  public void LoadProperConfigAfterInconsistentDimensionTest()
  {
    String wrongConfigString = "3\n\r" +
            "4\n\r" +
            "20\n\r";//wrong will try again


    InputStream wrongStream = new ByteArrayInputStream(wrongConfigString.getBytes(StandardCharsets.UTF_8));
    InputStream properStream = new ByteArrayInputStream(properConfigString.getBytes(StandardCharsets.UTF_8));
    //arrange

    var streamProvider = mock(StreamProvider.class);
    Mockito.when(streamProvider.getStream()).thenAnswer(new Answer() {
      private int count = 0;

      public Object answer(InvocationOnMock invocation) {
        if (count++ == 0)
          return wrongStream;

        return properStream;
      }
    });

    var loader = new ConfigLoader(streamProvider);
    var config = loader.getConfigFromUser();


    assertEquals(4,config.rowSize);
    assertEquals(3,config.columnSize);
    assertEquals(2,config.lenToWin);
    assertEquals("M",config.playerOName);
    assertEquals("T",config.playerXName);
  }
}
