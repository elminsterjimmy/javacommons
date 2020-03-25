package com.elminster.common.cache;

abstract public class ReadOnlyCache<K, V> implements ICache<K, V> {

  @Override
  public void put(K key, V object) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(K key, V object, long ttl) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int evict() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void remove(K key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }
}
