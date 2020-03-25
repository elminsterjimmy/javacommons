package com.elminster.common.cache;

/**
 * The cache statistics.
 *
 * @author jgu
 * @version 1.0
 */
public interface CacheStatistics {

    /**
     * Get the cache miss count.
     * @return the cache miss count
     */
    long getMissedCount();

    /**
     * Get the cache hit count.
     * @return the cache hit count
     */
    long getHitCount();

    /**
     * Get the cache hit percentage
     * @return the cache hit percentage
     */
    double getHitPercent();
}
