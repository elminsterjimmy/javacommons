package com.elminster.common.factory;

import com.elminster.common.cache.CacheBuilder;
import com.elminster.common.cache.ICache;
import com.elminster.common.exception.ObjectInstantiationExcption;

public class ClassCachedReflectFactory<T> extends CachedReflectFactory<Class<? extends T>, T> {

    public ClassCachedReflectFactory() {
        this(CacheBuilder.newSimpleCache());
    }

    public ClassCachedReflectFactory(ICache<Class<? extends T>, T> cache) {
        super(cache);
    }

    @Override
    protected T createInstance(Class<? extends T> clazz) throws ObjectInstantiationExcption {
        return super.instantiateInstance(clazz);
    }
}
