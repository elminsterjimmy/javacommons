package com.elminster.common.exception;

import javax.lang.model.UnknownEntityException;

/**
 * Unknown Enum Exception.
 * 
 * @author jinggu
 * @version 1.0
 */
public class UnknownEnumException extends UnknownEntityException {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public UnknownEnumException(String message) {
    super(message);
  }
}
