package com.elminster.common.cache;

/**
 * The Cache Builder.
 *
 * @author jgu
 * @version 1.0
 */
public class CacheBuilder {

    /**
     * Create a new {@link SimpleCache}.
     * @param <K> the key type
     * @param <V> the value type
     * @return a new Simple Cache
     */
    public static <K, V> ICache<K, V> newSimpleCache() {
        return new SimpleCache<>();
    }

    /**
     * Create a new {@link SimpleCache} with the capacity.
     * @param <K> the key type
     * @param <V> the value type
     * @param capacity the capacity
     * @return a new Simple Cache
     */
    public static <K, V> ICache<K, V> newSimpleCache(int capacity) {
        return new SimpleCache<>(capacity);
    }

    /**
     * Create a new {@link FIFOCache}.
     * @param <K> the key type
     * @param <V> the value type
     * @return a new FIFO Cache
     */
    public static <K, V> ICache<K, V> newFIFOCache() {
        return new FIFOCache<>();
    }

    /**
     * Create a new {@link FIFOCache} with the capacity.
     * @param <K> the key type
     * @param <V> the value type
     * @param capacity the capacity
     * @return a new FIFO Cache
     */
    public static <K, V> ICache<K, V> newFIFOCache(int capacity) {
        return new FIFOCache<>(capacity);
    }

    /**
     * Create a new {@link LFUCache}.
     * @param <K> the key type
     * @param <V> the value type
     * @return a new LFU Cache
     */
    public static <K, V> ICache<K, V> newLFUCache() {
        return new LFUCache<>();
    }

    /**
     * Create a new {@link LFUCache} with the capacity.
     * @param <K> the key type
     * @param <V> the value type
     * @param capacity the capacity
     * @return a new LFU Cache
     */
    public static <K, V> ICache<K, V> newLFUCache(int capacity) {
        return new LFUCache<>(capacity);
    }

    /**
     * Create a new {@link LRUCache}.
     * @param <K> the key type
     * @param <V> the value type
     * @return a new LRU Cache
     */
    public static <K, V> ICache<K, V> newLRUCache() {
        return new LFUCache<>();
    }

    /**
     * Create a new {@link LRUCache} with the capacity.
     * @param <K> the key type
     * @param <V> the value type
     * @param capacity the capacity
     * @return a new LRU Cache
     */
    public static <K, V> ICache<K, V> newLRUCache(int capacity) {
        return new LFUCache<>(capacity);
    }
}
