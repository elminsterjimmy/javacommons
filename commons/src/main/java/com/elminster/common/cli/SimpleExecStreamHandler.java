package com.elminster.common.cli;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The Sample Exec Stream Handler.
 * This handler only accepts the stdoutIs and stderrIs, no interact with the command line.
 *
 * @author jgu
 * @version 1.0
 */
public class SimpleExecStreamHandler extends AbstractExecStreamHandler implements ExecuteStreamHandler {

  private ByteArrayOutputStream stdoutOutputStream;
  private ByteArrayOutputStream stderrOutputStream;

  public void start() throws IOException {
    super.start();
    stdoutOutputStream = new ByteArrayOutputStream();
    stderrOutputStream = new ByteArrayOutputStream();
    PipeThread stdoutThread = new PipeThread(stdoutIs, stdoutOutputStream);
    PipeThread stderrThread = new PipeThread(stderrIs, stderrOutputStream);
    stdoutThread.start();
    stderrThread.start();
  }

  public void stop() throws IOException {
    super.stop();
    if (null != stderrOutputStream) {
      stderrOutputStream.close();
    }
    if (null != stderrOutputStream) {
      stdoutOutputStream.close();
    }
  }

  @Override
  public String getStdout() {
    return new String(stdoutOutputStream.toByteArray());
  }

  @Override
  public String getStderr() {
    return new String(stderrOutputStream.toByteArray());
  }

  /**
   * Helper thread that copies one stream to another, storing an exception if one occurs.
   */
  static class PipeThread extends Thread {
    final private InputStream inputStream;
    final private OutputStream outputStream;
    volatile Exception exception;

    public PipeThread(final InputStream inputStream, final OutputStream outputStream) {
      this.inputStream = inputStream;
      this.outputStream = outputStream;
      this.exception = null;
    }

    @Override
    public void run() {
      try {
        final byte buff[] = new byte[1024];
        int nread = this.inputStream.read(buff, 0, 1024);
        while (nread > 0) {
          this.outputStream.write(buff, 0, nread);
          nread = this.inputStream.read(buff, 0, 1024);
        }
      } catch (Exception e) {
        this.exception = e;
      }
    }

    public Exception getException() {
      return this.exception;
    }
  }
}
