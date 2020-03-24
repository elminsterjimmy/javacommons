package com.elminster.common.cache;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The cache object.
 * 
 * @author jgu
 * @version 1.0
 * @param <K> value type
 */
class CacheObject<K, V> {

  private static final long NO_TTL = -1;
  /** the key. */
  private final K key;
  /** the value. */
  private final V value;
  /** the time to live. */
  private final long ttl;
  /** the created time. */
  private final long createdTime;
  /** the hit count. */
  private final AtomicLong hit;
  /** the last hit time. */
  private long lastHitTime;
  
  /**
   * Constructor.
   * @param key the key
   * @param value the value
   */
  public CacheObject(K key, V value) {
    this(key, value, NO_TTL);
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
    this.hit = new AtomicLong();
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
    return hit.get();
  }
  
  /**
   * Add hit.
   */
  public void addHit() {
    hit.incrementAndGet();
    lastHitTime = System.currentTimeMillis();
  }
  
  /**
   * Sub hit count.
   * @param sub the sub
   * @return the hit count
   */
  public long subHit(long sub) {
    return hit.addAndGet(-sub);
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