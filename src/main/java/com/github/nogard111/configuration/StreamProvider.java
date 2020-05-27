package com.github.nogard111.configuration;

import java.io.InputStream;

public class StreamProvider {
  private final InputStream stream;

  public StreamProvider(InputStream in) {
    stream = in;
  }

  public InputStream getStream() {
    return stream;
  }
}
