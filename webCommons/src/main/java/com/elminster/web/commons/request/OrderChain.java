package com.elminster.web.commons.request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.elminster.common.util.Assert;

public class OrderChain implements Iterable<Order> {

  private List<Order> orders = new ArrayList<Order>();
  
  public OrderChain() {
    
  }
  
  public OrderChain(Order order) {
    Assert.notNull(order);
    this.addOrder(order);
  }
  
  public void addOrder(Order order) {
    orders.add(order);
  }
  
  public boolean isEmpty() {
    return orders.isEmpty();
  }
  
  public Iterator<Order> iterator() {
    return orders.iterator();
  }
}
