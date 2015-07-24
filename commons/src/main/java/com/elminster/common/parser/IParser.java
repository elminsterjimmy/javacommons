package com.elminster.common.parser;


/**
 * The parser interface.
 * 
 * @author jgu
 * @version 1.0
 * @param <K> to parse type
 * @param <T> the parsed type
 */
public interface IParser<T, K> {

  /**
   * Parse the K to T.
   * @param toParse the Object to parse
   * @return parsed Object
   * @throws ParseException on error
   */
  public T parse(K toParse) throws ParseException;
}
