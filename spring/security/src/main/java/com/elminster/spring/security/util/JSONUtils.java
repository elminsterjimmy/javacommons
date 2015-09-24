package com.elminster.spring.security.util;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

  public static final JSONUtils INSTANCE = new JSONUtils();

  private static final ObjectMapper jsonMapper = new ObjectMapper();

  private JSONUtils() {
  }

  public String toJsonString(Object obj) throws IOException {
    StringWriter writer = null;
    try {
      writer = new StringWriter();
      jsonMapper.writeValue(writer, obj);
      return writer.toString();
    } finally {
      if (null != writer) {
        writer.close();
      }
    }
  }

  public Object toJaveObject(String jsonString, Class<?> javaClass) throws IOException {
    return jsonMapper.readValue(jsonString, javaClass);
  }
  
  public Object toJaveObject(byte[] jsonByte, Class<?> javaClass) throws IOException {
    return jsonMapper.readValue(jsonByte, javaClass);
  }
}
