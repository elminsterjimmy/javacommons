package com.elminster.common.cache;

public class CacheBuilder {

    public static <K, V> ICache<K, V> newSimpleCache() {
        return new SimpleCache<>();
    }

    public static <K, V> ICache<K, V> newSimpleCache(int capacity) {
        return new SimpleCache<>(capacity);
    }

    public static <K, V> ICache<K, V> newFIFOCache() {
        return new FIFOCache<>();
    }

    public static <K, V> ICache<K, V> newFIFOCache(int capacity) {
        return new FIFOCache<>(capacity);
    }

    public static <K, V> ICache<K, V> newLFUCache() {
        return new LFUCache<>();
    }

    public static <K, V> ICache<K, V> newLFUCache(int capacity) {
        return new LFUCache<>(capacity);
    }

    public static <K, V> ICache<K, V> newLRUCache() {
        return new LFUCache<>();
    }

    public static <K, V> ICache<K, V> newLRUCache(int capacity) {
        return new LFUCache<>(capacity);
    }
}
