package com.elminster.web.commons.request;

public class Option {

  private FilterChain filterChain;
  
  private OrderChain orderChain;
  
  private Paging paging;
  
  public Option() {
    
  }

  /**
   * @return the filterChain
   */
  public FilterChain getFilterChain() {
    return filterChain;
  }

  /**
   * @param filterChain the filterChain to set
   */
  public void setFilterChain(FilterChain filterChain) {
    this.filterChain = filterChain;
  }

  /**
   * @return the orderChain
   */
  public OrderChain getOrderChain() {
    return orderChain;
  }

  /**
   * @param orderChain the orderChain to set
   */
  public void setOrderChain(OrderChain orderChain) {
    this.orderChain = orderChain;
  }

  /**
   * @return the paging
   */
  public Paging getPaging() {
    return paging;
  }

  /**
   * @param paging the paging to set
   */
  public void setPaging(Paging paging) {
    this.paging = paging;
  }
}
