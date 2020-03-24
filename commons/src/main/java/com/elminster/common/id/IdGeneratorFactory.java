package com.elminster.common.id;

import com.elminster.common.factory.ClassCachedReflectFactory;

public class IdGeneratorFactory extends ClassCachedReflectFactory<IdGenerator> {

    private static final IdGeneratorFactory INSTANCE = new IdGeneratorFactory();

    private IdGeneratorFactory() {
    }

    public static IdGeneratorFactory getIdGeneratorFacotry() {
        return INSTANCE;
    }
}
