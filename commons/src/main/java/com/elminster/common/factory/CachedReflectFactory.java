package com.elminster.common.factory;

import com.elminster.common.cache.CacheBuilder;
import com.elminster.common.cache.DaemonLoadCache;
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

    public T getInstance(K key) {
        return new DaemonLoadCache<K, T>() {
            @Override
            protected T retrieveValueWhenCacheMissed(K key) throws Exception {
                return createInstance(key);
            }
        }.get(key);
    }

    protected abstract T createInstance(K key) throws ObjectInstantiationExcption;
}
