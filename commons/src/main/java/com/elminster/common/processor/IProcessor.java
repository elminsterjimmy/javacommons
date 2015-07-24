package com.elminster.common.processor;

/**
 * The interface for processor.
 * 
 * @author jgu
 * @version 1.0
 */
public interface IProcessor {

  /**
   * Do the process.
   * @throws ProcessException on error
   */
  public void process() throws ProcessException;
}
