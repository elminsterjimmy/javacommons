package com.elminster.web.commons.request;

public class Option {

  private FilterChain filterChain;
  
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
}
