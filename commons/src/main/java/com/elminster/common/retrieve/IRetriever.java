package com.elminster.common.retrieve;


/**
 * The retrieve interface.
 * 
 * @author jgu
 * @version 1.0
 * @param <T> retrieve type
 */
public interface IRetriever<T> {

  /**
   * Retrieve something.
   * @return the retrieved Object
   * @throws RetrieveException on error
   */
  public T retrieve() throws RetrieveException;
}
