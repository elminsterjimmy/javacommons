package com.elminster.common.cache;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The LRU cache.
 * 
 * @author jgu
 * @version 1.0
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public class LRUCache<K, V> extends AbstractCache<K, V> {

  /**
   * Constructor.
   */
  public LRUCache() {
    this(0);
  }

  /**
   * Constructor.
   * 
   * @param capacity
   *          the capacity
   */
  public LRUCache(int capacity) {
    this.capacity = capacity;

    cacheMap = new LinkedHashMap<K, CacheObject<K, V>>(capacity + 1, 1.0f, true) {
      /** the serial version uid. */
      private static final long serialVersionUID = -144947055454271699L;

      /**
       * {@inheritDoc}
       */
      @Override
      protected boolean removeEldestEntry(Map.Entry<K, CacheObject<K, V>> eldest) {
        if (LRUCache.this.capacity == 0) {
          return false;
        }
        return size() > LRUCache.this.capacity;
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int doEviction() {
    int count = 0;
    Iterator<CacheObject<K, V>> values = cacheMap.values().iterator();
    CacheObject<K, V> co;
    while (values.hasNext()) {
      co = values.next();
      if (co.isExpired()) {
        values.remove();
        count++;
      }
    }
    return count;
  }
}