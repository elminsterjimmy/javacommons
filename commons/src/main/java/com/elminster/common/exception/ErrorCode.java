package com.elminster.common.exception;

/**
 * The error code.
 * 
 * @author jgu
 * @version 1.0
 */
final public class ErrorCode {

  /** the code. */
  private final String code;
  /** the code name. */
  private final String codeName;
  
  public ErrorCode(String code, String codeName) {
    this.code = code;
    this.codeName = codeName;
  }
  /**
   * @return the codeName
   */
  public String getCodeName() {
    return codeName;
  }
  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }
}
