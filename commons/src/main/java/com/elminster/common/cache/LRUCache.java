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
    this(MAX_CAPACITY);
  }

  /**
   * Constructor.
   *
   * @param capacity
   *          the capacity
   */
  public LRUCache(int capacity) {
    super(capacity);

    cacheMap = new LinkedHashMap<K, CacheObject<K, V>>(capacity + 1, 1.0f, true) {
      /** the serial version uid. */
      private static final long serialVersionUID = -144947055454271699L;

      /**
       * {@inheritDoc}
       */
      @Override
      protected boolean removeEldestEntry(Map.Entry<K, CacheObject<K, V>> eldest) {
        return size() > LRUCache.this.capacity;
      }
    };
  }

  /**
   * Evict the expired ones first, if still full evict the oldest unread element.
   * @return evicted element count
   */
  @Override
  protected int doEviction() {
    int count = 0;
    CacheObject<K, V> lastRead = null;
    Iterator<CacheObject<K, V>> values = cacheMap.values().iterator();
    CacheObject<K, V> co;
    while (values.hasNext()) {
      co = values.next();
      if (co.isExpired()) {
        values.remove();
        count++;
        continue;
      }

      if (null == lastRead || co.getLastHitTime() < lastRead.getLastHitTime()) {
        lastRead = co;
      }
    }

    if (isFull() && null != lastRead) {
      cacheMap.remove(lastRead);
      count++;
    }
    return count;
  }
}