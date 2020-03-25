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
    this(MAX_CAPACITY);
  }

  /**
   * Constructor.
   * @param capacity the capacity
   */
  public LFUCache(int capacity) {
    super(capacity);
    this.cacheMap = new HashMap<>();
  }

  /**
   * Evict the expired ones first, if still full evict the min hit element.
   * @return evicted element count
   */
  @Override
  protected int doEviction() {
    int count = 0;
    
    CacheObject<K, V> minHit = null;

    Iterator<CacheObject<K, V>> values = cacheMap.values().iterator();
    CacheObject<K, V> co;
    while (values.hasNext()) {
      co = values.next();
      // evict the expired ones
      if (co.isExpired()) {
        values.remove();
        count++;
        continue;
      }

      if (null == minHit || co.getHitCount() < minHit.getHitCount()) {
        minHit = co;
      }
    }

    // still full, evict the LFU one
    if (isFull() && null != minHit) {
      cacheMap.remove(minHit);
      count++;
    }
    return count;
  }
}
