package com.elminster.common.escape;

import java.util.HashMap;
import java.util.Map;

public class EscaperBuilder {

  private Map<String, String> cache = new HashMap<String, String>();
  
  public static EscaperBuilder newBuilder() {
    return new EscaperBuilder();
  }
  
  public Escaper build() {
    return new CachedEscaper();
  }
  
  public EscaperBuilder addEscape(String escape, String escaped) {
    cache.put(escape, escaped);
    return this;
  }
  
  class CachedEscaper implements Escaper {

    @Override
    public String escape(String origin) {
      return cache.get(origin);
    }
    
  }
}
