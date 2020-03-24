package com.elminster.common.cache;

abstract public class CacheTemplate<K, V> {

    private final ICache<K, V> cache;

    public CacheTemplate() {
        this.cache = CacheBuilder.newSimpleCache();
    }

    public CacheTemplate(ICache<K, V> cache) {
        this.cache = cache;
    }

    public V getCached(K key) throws Exception {
        V value = cache.get(key);
        if (null == value) {
            value = retrieveValue(key);
            cache.put(key, value);
        }
        return value;
    }

    abstract protected V retrieveValue(K key) throws Exception;
}
