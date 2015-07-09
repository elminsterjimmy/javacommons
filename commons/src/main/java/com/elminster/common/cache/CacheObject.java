package com.elminster.common.cache;

/**
 * The cache object.
 * 
 * @author jgu
 * @version 1.0
 * @param <K> value type
 */
class CacheObject<K, V> {
  /** the key. */
  private final K key;
  /** the value. */
  private final V value;
  /** the time to live. */
  private final long ttl;
  /** the created time. */
  private final long createdTime;
  /** the hit count. */
  private long hit;
  /** the last hit time. */
  private long lastHitTime;
  
  /**
   * Constructor.
   * @param key the key
   * @param value the value
   */
  public CacheObject(K key, V value) {
    this(key, value, 0);
  }
  
  /**
   * Constructor.
   * @param key the key
   * @param value the value
   * @param ttl the time to live
   */
  public CacheObject(K key, V value, long ttl) {
    this.key = key;
    this.value = value;
    this.ttl = ttl;
    this.createdTime = System.currentTimeMillis();
  }
  
  /**
   * Get the key.
   * @return the key
   */
  public K getKey() {
    return key;
  }
  
  /**
   * Get the value.
   * @return the value
   */
  public V getValue() {
    return value;
  }
  
  /**
   * Get the time to live.
   * @return the time to live
   */
  public long getTtl() {
    return ttl;
  }
  
  /**
   * Get the created time.
   * @return the created time
   */
  public long getCreatedTime() {
    return createdTime;
  }
  
  /**
   * Get the hit count.
   * @return
   */
  public long getHitCount() {
    return hit;
  }
  
  /**
   * Add hit.
   */
  public void addHit() {
    hit++;
    lastHitTime = System.currentTimeMillis();
  }
  
  /**
   * Sub hit count.
   * @param sub the sub
   * @return the hit count
   */
  public long subHit(long sub) {
    hit = hit - sub;
    return hit;
  }
  
  /**
   * Get the last hit time.
   * @return the last hit time
   */
  public long getLastHitTime() {
    return lastHitTime;
  }
  
  /**
   * Is the cache object expired?
   * @return is expired or not
   */
  public boolean isExpired() {
    return ttl > 0 && System.currentTimeMillis() - createdTime >= ttl;
  }
}