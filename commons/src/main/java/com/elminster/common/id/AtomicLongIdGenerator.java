package com.elminster.common.id;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongIdGenerator implements IdGenerator {

    private static final AtomicLong ATOMIC_LONG = new AtomicLong(0);

    @Override
    public Serializable nextId() {
        return ATOMIC_LONG.getAndIncrement();
    }
}
