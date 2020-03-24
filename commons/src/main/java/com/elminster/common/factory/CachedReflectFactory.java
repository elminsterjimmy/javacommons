package com.elminster.common.factory;

import com.elminster.common.cache.CacheBuilder;
import com.elminster.common.cache.CacheTemplate;
import com.elminster.common.cache.ICache;
import com.elminster.common.exception.ObjectInstantiationExcption;

abstract public class CachedReflectFactory<K, T> extends ReflectFactory<T> {

    final private ICache<K, T> cache;

    public CachedReflectFactory() {
        this(CacheBuilder.newSimpleCache());
    }

    public CachedReflectFactory(ICache<K, T> cache) {
        this.cache = cache;
    }

    public T getInstance(K key) throws Exception {
        final T cached = new CacheTemplate<K, T>() {
            @Override
            protected T retrieveValue(K key) throws Exception {
                return createInstance(key);
            }
        }.getCached(key);
        return cached;
    }

    protected abstract T createInstance(K key) throws ObjectInstantiationExcption;
}
