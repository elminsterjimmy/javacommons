package com.elminster.common.serialize;

/**
 * The serializer interface.
 * 
 * @author jgu
 * @version 1.0
 */
public interface ISerializer {

  public Object serialize(Object obj) throws SerialzeException;
  
  public Object deserialize(Object obj) throws DeserialzeException;
}
