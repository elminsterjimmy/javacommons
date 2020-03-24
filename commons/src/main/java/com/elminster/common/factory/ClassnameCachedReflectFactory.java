package com.elminster.common.factory;

import com.elminster.common.cache.CacheBuilder;
import com.elminster.common.cache.ICache;
import com.elminster.common.exception.ObjectInstantiationExcption;

public class ClassnameCachedReflectFactory<T> extends CachedReflectFactory<String, T> {

    public ClassnameCachedReflectFactory() {
        this(CacheBuilder.newSimpleCache());
    }

    public ClassnameCachedReflectFactory(ICache<String, T> cache) {
        super(cache);
    }

    @Override
    protected T createInstance(String className) throws ObjectInstantiationExcption {
        return super.instantiateInstance(className);
    }
}
