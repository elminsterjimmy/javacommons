package com.elminster.web.commons.request;

public class Order {

  private String key;
  private Direction direction;
  
  public Order(String key) {
    this.key = key;
    this.direction = Direction.ASC;
  }
  
  public Order(String key, Direction direction) {
    this.key = key;
    this.direction = direction;
  }
  
  /**
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * @return the direct
   */
  public Direction getDirection() {
    return direction;
  }

  public static enum Direction {
    
    ASC,
    DESC;
    
    private static final String DESC_STRING = "DESC";
    
    public static Direction parseString(String s) {
      if (null != s) {
        if (s.trim().equalsIgnoreCase(DESC_STRING)) {
          return DESC;
        }
      }
      return ASC;
    }
  }
}
