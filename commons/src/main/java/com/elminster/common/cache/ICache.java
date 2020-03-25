package com.elminster.common.cache;

/**
 * The cache interface.
 * 
 * @author jgu
 * @version 1.0
 */
 public interface ICache<K, V> {

  /**
   * Get the capacity of the cache.
   * @return the capacity of the cache
   */
  int capacity();

  /**
   * Put a key-value pair into the cache.
   * @param key the key
   * @param object the value
   */
   void put(K key, V object);

  /**
   * Put a key-value pair with a TTL(time to live) into the cache.
   * @param key the key
   * @param object the value
   * @param ttl the TTL, unit <I>ms</I>
   */
   void put(K key, V object, long ttl);

  /**
   * Get the cached value from the key
   * @param key the key
   * @return the value associated with the key {@literal null} for not hit in the cache
   */
   V get(K key);

  /**
   * Evict the expired values.
   * @return evicted element count
   */
   int evict();

  /**
   * Check if the cache is full.
   * @return if the cache is full
   */
   boolean isFull();

  /**
   * Remove the cached value with the key
   * @param key the key
   */
   void remove(K key);

  /**
   * Clear the cache
   */
  void clear();

  /**
   * Get the size of the cache.
   * @return the size of the cache
   */
   int size();

  /**
   * Check if the cache is empty.
   * @return if the cache is empty
   */
   boolean isEmpty();

   /**
    * Dump the cache.
    */
   void dump();
}
