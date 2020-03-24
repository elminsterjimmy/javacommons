package com.elminster.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * The abstract cache.
 * 
 * @author jgu
 * @version 1.0
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public abstract class AbstractCache<K, V> implements ICache<K, V> {

  protected static final int INFINITE_CAPACITY = -1;

  private static final Logger logger = LoggerFactory.getLogger(AbstractCache.class);

  /** the capacity of the cache, -1 = infinite. */
  protected final int capacity;
  /** the hit count. */
  protected volatile long hit = 0;
  /** the missed count. */
  protected volatile long missed = 0;
  /** the actually map to use. */
  protected Map<K, CacheObject<K, V>> cacheMap;
  /** the lock used in the cache. */
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  /** the read lock. */
  private final ReadLock readLock = lock.readLock();
  /** the write lock. */
  private final WriteLock writeLock = lock.writeLock();

  public AbstractCache() {
    this(INFINITE_CAPACITY);
  }

  public AbstractCache(int capacity) {
    this.capacity = capacity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int capacity() {
    return capacity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void put(K key, V value) {
    _put(key, new CacheObject<>(key, value));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void put(K key, V value, long ttl) {
    _put(key, new CacheObject<>(key, value, ttl));
  }

  private void _put(K key, CacheObject<K, V> value) {
    try {
      writeLock.lock();
      if (isFull()) {
        // do the evict
        if (logger.isDebugEnabled()) {
          logger.debug("Cache is full, do the evict....");
        }
        evict();
        if (logger.isDebugEnabled()) {
          logger.debug("Cache is full, do the evict....");
        }
      }
      cacheMap.put(key, value);
    } finally {
      writeLock.unlock();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public V get(K key) {
    try {
      readLock.lock();
      CacheObject<K, V> cachedObject = cacheMap.get(key);
      V value = null;
      if (null == cachedObject) {
        missed++;
      }
      // expired
      if (cachedObject.isExpired()) {
        cacheMap.remove(key);
        missed++;
      } else {
        hit++;
        value = cachedObject.getValue();
        cachedObject.addHit();
      }
      return value;
    } finally {
      readLock.unlock();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int evict() {
    try {
      writeLock.lock();
      return doEviction();
    } finally {
      writeLock.unlock();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFull() {
    return (this.capacity > 0 && this.cacheMap.size() >= this.capacity)
            || Integer.MAX_VALUE == this.cacheMap.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void remove(K key) {
    try {
      writeLock.lock();
      if (isEmpty()) {
        throw new CacheUnderflowException();
      }
      cacheMap.remove(key);
    } finally {
      writeLock.unlock();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    try {
      writeLock.lock();
      cacheMap.clear();
    } finally {
      writeLock.unlock();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {
    return cacheMap.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty() {
    return cacheMap.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dump() {
    try {
      readLock.lock();
      System.out.println(cacheMap.toString());
    } finally {
      readLock.unlock();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getMissedCount() {
    return missed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getHitCount() {
    return hit;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getHitPercent() {
    return 0 == hit ? 0 : (hit / (hit + missed));
  }

  /**
   * Do the eviction by specified algorithm.
   * @return evict count
   */
  abstract protected int doEviction();
}
