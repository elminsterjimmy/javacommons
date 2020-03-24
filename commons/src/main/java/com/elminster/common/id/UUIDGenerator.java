package com.elminster.common.id;

import java.io.Serializable;
import java.util.UUID;

final public class UUIDGenerator implements IdGenerator {

  @Override
  public Serializable nextId() {
    return UUID.randomUUID().toString();
  }
}
