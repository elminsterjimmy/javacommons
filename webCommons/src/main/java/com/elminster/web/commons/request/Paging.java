package com.elminster.web.commons.request;

public class Paging {

  private int pageNumber;
  private int pageSize;
  
  public Paging(int pageNumber, int pageSize) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
  }
  
  /**
   * @return the pageNumber
   */
  public int getPageNumber() {
    return pageNumber;
  }
  /**
   * @return the pageSize
   */
  public int getPageSize() {
    return pageSize;
  }
  /**
   * @return the offset
   */
  public int getOffset() {
    return pageNumber * pageSize;
  }
  
  public Paging getPreviousPage() {
    return 1 == this.pageNumber ? this : new Paging(pageNumber - 1, pageSize);
  }
  
  public Paging getNextPage() {
    return new Paging(pageNumber + 1, pageSize);
  }
}
