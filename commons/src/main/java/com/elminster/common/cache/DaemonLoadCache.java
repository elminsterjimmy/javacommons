package com.elminster.common.cache;

/**
 * The daemon load cache.
 *
 * @param <K>
 *     the key type
 * @param <V>
 *     the value type
 * @author jgu
 * @version 1.0
 */
abstract public class DaemonLoadCache<K, V> extends ReadOnlyCache<K, V> implements ICache<K, V> {

  /**
   * The cache.
   */
  private final ICache<K, V> cache;

  /**
   * Constructor the Cache Template.
   */
  public DaemonLoadCache() {
    this.cache = CacheBuilder.newSimpleCache();
  }

  /**
   * Constructor the Cache Template with the cache.
   *
   * @param cache
   *     the cache
   */
  public DaemonLoadCache(ICache<K, V> cache) {
    this.cache = cache;
  }

  /**
   * Get the value from the key.
   * First it will get the value from the cache, if cache missed, will try to retrieve the value
   * by {@link #retrieveValueWhenCacheMissed(Object)} method.
   *
   * @param key
   *     the key associated with the value
   * @return the value
   */
  @Override
  public V get(K key) {
    V value = cache.get(key);
    if (null == value) {
      try {
        value = retrieveValueWhenCacheMissed(key);
        cache.put(key, value);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return value;
  }

  /**
   * When cache missed, retrieve the value from the key.
   *
   * @param key
   *     the key associated with the value
   * @return the value
   * @throws Exception
   *     on error
   */
  abstract protected V retrieveValueWhenCacheMissed(K key) throws Exception;

  @Override
  public int capacity() {
    return cache.capacity();
  }

  @Override
  public boolean isFull() {
    return cache.isFull();
  }

  @Override
  public int size() {
    return cache.size();
  }

  @Override
  public boolean isEmpty() {
    return cache.isEmpty();
  }

  @Override
  public void dump() {
    cache.dump();
  }
}
