package com.elminster.common.cache;

/**
 * The cache interface.
 * 
 * @author jgu
 * @version 1.0
 */
public interface ICache<K, V> {
  
  public int capacity();

  public void put(K key, V object);

  public void put(K key, V object, long ttl);

  public V get(K key);

  public int evict();

  public boolean isFull();

  public void remove(K key);

  public void clear();

  public int size();

  public boolean isEmpty();
  
  public void dump();

  public long getMissedCount();
  
  public long getHitCount();
  
  public double getHitPercent();
}
