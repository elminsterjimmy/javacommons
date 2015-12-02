package com.elminster.web.commons.request;

public class Filter {
  
  private String name;
  private Object value;
  private Operator operator = Operator.EQUAL;
  
  public Filter(String name, Object value) {
    this.name = name;
    this.value = value;
  }
  
  public Filter(FilterChain subFilter) {
    this(null, subFilter);
  }
  
  /**
   * @return the operator
   */
  public Operator getOperator() {
    return operator;
  }

  /**
   * @param operator the operator to set
   */
  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the value
   */
  public Object getValue() {
    return value;
  }

  public static enum Operator {
    EQUAL,
    NOT_EQUAL,
    GREATER_THAN,
    GREATER_EQUAL,
    LESSER_THAN,
    LESSER_EQUAL,
    LIKE,
    NOT_LIKE,
    IN,
    NOT_IN
  }
}
