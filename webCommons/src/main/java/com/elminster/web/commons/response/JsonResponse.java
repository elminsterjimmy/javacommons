package com.elminster.web.commons.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The general JSON response.
 * 
 * @author jgu
 * @version 1.0
 */
public class JsonResponse implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -6882544319032795924L;
  /** the response status: OK. */
  public static final String STATUS_OK = "OK";
  /** the response status: ERROR. */
  public static final String STATUS_ERROR = "ERROR";
  
  /**
   * The json status.
   */
  private String status;
  /**
   * The json errors.
   */
  private List<ResponseError> errors;
  /**
   * The json data.
   */
  private Object data;
  /**
   * The created / updated resource URI.
   */
  private String resource;
  /**
   * The next Page URI.
   */
  private String nextPage;
  /**
   * The page Number.
   */
  private int pageNumber;
  /**
   * The page size.
   */
  private int pageSize;
  /**
   * The total pages.
   */
  private int totalPages;
  
  public JsonResponse() {
    this.status = STATUS_OK;
  }
  
  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }
  /**
   * @param status the status to set
   */
  public JsonResponse setStatus(String status) {
    this.status = status;
    return this;
  }
  /**
   * @return the errors
   */
  public List<ResponseError> getErrors() {
    return errors;
  }
  /**
   * @param errors the errors to set
   */
  public JsonResponse setErrors(List<ResponseError> errors) {
    this.errors = errors;
    return this;
  }
  
  public JsonResponse addError(ResponseError error) {
    if (null == this.errors) {
      this.errors = new ArrayList<ResponseError>();
    }
    this.errors.add(error);
    return this;
  }

  /**
   * @return the data
   */
  public Object getData() {
    return data;
  }

  /**
   * @param data the data to set
   */
  public JsonResponse setData(Object data) {
    this.data = data;
    return this;
  }

  /**
   * @return the resource
   */
  public String getResource() {
    return resource;
  }

  /**
   * @param resource the resource to set
   */
  public JsonResponse setResource(String resource) {
    this.resource = resource;
    return this;
  }

  /**
   * @return the nextPage
   */
  public String getNextPage() {
    return nextPage;
  }

  /**
   * @param nextPage the nextPage to set
   */
  public JsonResponse setNextPage(String nextPage) {
    this.nextPage = nextPage;
    return this;
  }

  /**
   * @return the pageNumber
   */
  public int getPageNumber() {
    return pageNumber;
  }

  /**
   * @param pageNumber the pageNumber to set
   */
  public JsonResponse setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
    return this;
  }

  /**
   * @return the pageSize
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * @param pageSize the pageSize to set
   */
  public JsonResponse setPageSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  /**
   * @return the totalPages
   */
  public int getTotalPages() {
    return totalPages;
  }

  /**
   * @param totalPages the totalPages to set
   */
  public JsonResponse setTotalPages(int totalPages) {
    this.totalPages = totalPages;
    return this;
  }
}