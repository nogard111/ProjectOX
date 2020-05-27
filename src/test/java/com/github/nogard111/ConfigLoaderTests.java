package com.github.nogard111;

import com.github.nogard111.configuration.ConfigLoader;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class ConfigLoaderTests {
  /**
   * Load Test
   */
  @Test
  public void LoarTest()
  {
    String configString = "3\n\r" +
            "4\n\r" +
            "2\n\r" +
            "M\n\r" +
            "T\n\r" +
            "o\n\r";
    InputStream stream = new ByteArrayInputStream(configString.getBytes(StandardCharsets.UTF_8));
    //arrange
    var loader = new ConfigLoader(stream);
    var config = loader.getConfigFromUser();


    assertEquals(4,config.rowSize);
    assertEquals(3,config.columnSize);
    assertEquals(2,config.lenToWin);
    assertEquals("M",config.playerOName);
    assertEquals("T",config.playerXName);
  }
}
