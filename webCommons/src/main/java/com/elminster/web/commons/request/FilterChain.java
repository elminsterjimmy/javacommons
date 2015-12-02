package com.elminster.web.commons.request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.elminster.common.util.Assert;

public class FilterChain implements Iterable<Filter> {
  
  private List<Filter> filters = new ArrayList<Filter>();
  private Operator operator;
  private boolean negate;
  
  public FilterChain() {
    this.operator = Operator.AND;
    this.negate = false;
  }
  
  public void addFilter(Filter filter) {
    Assert.notNull(filter);
    this.filters.add(filter);
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
   * @return the negate
   */
  public boolean isNegate() {
    return negate;
  }
  
  /**
   * @param negate the negate to set
   */
  public void setNegate(boolean negate) {
    this.negate = negate;
  }
  
  public boolean isEmpty() {
    return filters.isEmpty();
  }

  public Iterator<Filter> iterator() {
    return filters.iterator();
  }

  public static enum Operator {
    AND,
    OR
  }
}
