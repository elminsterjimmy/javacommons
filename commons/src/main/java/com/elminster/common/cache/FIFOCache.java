package com.elminster.common.cache;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * The FIFO cache.
 * 
 * @author jgu
 * @version 1.0
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public class FIFOCache<K, V> extends AbstractCache<K, V> {
  
  /**
   * Constructor.
   */
  public FIFOCache() {
    this(0);
  }

  /**
   * Constructor.
   * @param capacity the capacity
   */
  public FIFOCache(int capacity) {
    super(capacity);
    cacheMap = new LinkedHashMap<>(capacity + 1, 1.0f, false);
  }

  /**
   * Evict the expired ones first, if still full evict the first element.
   */
  @Override
  protected int doEviction() {
    int count = 0;
    CacheObject<K, V> first = null;
    
    Iterator<CacheObject<K, V>> values = cacheMap.values().iterator();
    // first evict the expired objects
    while (values.hasNext()) {
      CacheObject<K, V> co = values.next();
      if (co.isExpired()) {
        values.remove();
        count++;
      }
      if (null == first) {
        first = co;
      }
    }
    // if still full, evict the first one
    if (isFull() && null != first) {
      cacheMap.remove(first.getKey());
      count++;
    }
    return count;
  }
}
