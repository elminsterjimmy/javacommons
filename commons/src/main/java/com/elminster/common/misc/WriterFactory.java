package com.elminster.common.misc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WriterFactory {

  public static final WriterFactory INSTANCE = new WriterFactory();
  
  private final Map<String, IWriter> writerCache = new HashMap<String, IWriter>();
  
  public IWriter getWriter(String fileName) {
    IWriter writer = writerCache.get(fileName);
    if (null == writer) {
      try {
        writer = new AsynchorizedFileWriter(fileName);
        new Thread((Runnable)writer).start();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      writerCache.put(fileName, writer);
    }
    return writer;
  }
}
