package com.elminster.common.escape;

/**
 * The escaper.
 * 
 * @author jinggu
 * @version 1.0
 */
public interface Escaper {

  /**
   * Escape the origin string.
   * 
   * @param origin
   *          the origin string
   * @return escaped string
   */
  public String escape(String origin);
}
