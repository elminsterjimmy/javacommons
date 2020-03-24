package com.elminster.common.cache;

public class CacheOverflowException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Cache overflow! size=[%d] vs capacity=[%d]";

    public CacheOverflowException(int capacity, int size) {
        super(String.format(MESSAGE_TEMPLATE, size, capacity));
    }
}
