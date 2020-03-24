package com.elminster.common.escape;

import com.elminster.common.cache.CacheBuilder;
import com.elminster.common.cache.ICache;

public class EscaperBuilder {

  private ICache<String, String> cache = CacheBuilder.newSimpleCache();
  
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
