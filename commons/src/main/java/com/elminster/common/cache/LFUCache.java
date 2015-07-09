package com.elminster.common.cache;

import java.util.HashMap;
import java.util.Iterator;

/**
 * The LFU cache.
 * 
 * @author jgu
 * @version 1.0
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public class LFUCache<K, V> extends AbstractCache<K, V> {

  /**
   * Constructor.
   */
  public LFUCache() {
    this(0);
  }
  
  /**
   * Constructor.
   * @param capacity the capacity
   */
  public LFUCache(int capacity) {
    this.capacity = capacity;
    this.cacheMap = new HashMap<K, CacheObject<K, V>>();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected int doEviction() {
    int count = 0;
    
    CacheObject<K, V> minHit = null;

    Iterator<CacheObject<K, V>> values = cacheMap.values().iterator();
    CacheObject<K, V> co;
    while (values.hasNext()) {
      co = values.next();
      if (co.isExpired()) {
        values.remove();
        count++;
        continue;
      }

      if (null == minHit || co.getHitCount() < minHit.getHitCount()) {
        minHit = co;
      }
    }

    if (isFull() && null != minHit) {
      long minHitCount = minHit.getHitCount();
      values = cacheMap.values().iterator();
      while (values.hasNext()) {
        co = values.next();
        if (co.subHit(minHitCount) <= 0) {
          values.remove();
          count++;
        }
      }
    }
    
    return count;
  }

}
