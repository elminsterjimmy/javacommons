package com.elminster.common.misc;

import java.io.IOException;

public interface IWriter {

  public IWriter append(String msg) throws IOException;
  
  public IWriter appendLn(String msg) throws IOException;
  
  public void close() throws IOException;
}
