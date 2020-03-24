package com.elminster.common.cache;

import java.util.Iterator;

public class SimpleCache<K, V> extends AbstractCache<K, V> {

    public SimpleCache() {
        this(INFINITE_CAPACITY);
    }

    public SimpleCache(int capacity) {
        super(capacity);
    }

    @Override
    protected int doEviction() {
        int count = 0;
        Iterator<CacheObject<K, V>> values = cacheMap.values().iterator();
        // first evict the expired objects
        while (values.hasNext()) {
            CacheObject<K, V> co = values.next();
            if (co.isExpired()) {
                values.remove();
                count++;
            }
        }

        // still full, throw CacheOverflowException
        if (isFull()) {
            throw new CacheOverflowException(capacity, size());
        }
        return count;
    }
}
