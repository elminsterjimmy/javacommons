package com.elminster.common.cache;

import java.util.HashMap;
import java.util.Iterator;

/**
 * The Simple Cache.
 * @param <K> the key type
 * @param <V> the value type
 * @author jgu
 * @version 1.0
 */
public class SimpleCache<K, V> extends AbstractCache<K, V> {

    /**
     * Constructor.
     */
    public SimpleCache() {
        this(MAX_CAPACITY);
    }

    /**
     * Constructor.
     */
    public SimpleCache(int capacity) {
        super(capacity);
        this.cacheMap = new HashMap<>();
    }

    /**
     * Evict the expired ones, if still full, throw the {@link CacheOverflowException}.
     * @return evicted element count
     */
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
