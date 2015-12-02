package com.elminster.web.commons.request;

import javax.servlet.ServletRequest;

import com.elminster.common.constants.Constants.StringConstants;

final public class OptionFactory {

  public static final OptionFactory INSTANCE = new OptionFactory();
  
  private static final String PAGE_NUMBER = "pageNumber";
  private static final String PAGE_SIZE = "pageSize";
  private static final String ORDER = "order";
  
  private OptionFactory() {
    
  }
  
  /**
   * Get the option from HTTP request parameter.
   * pageNumber=A&pageSize=B&order=A|desc&order=B
   * @param request the HTTP request
   * @return generated option
   */
  public Option getOption(ServletRequest request) {
    Option option = new Option();
    String strPageNumber = request.getParameter(PAGE_NUMBER);
    String strPageSize = request.getParameter(PAGE_SIZE);
    if (null != strPageSize || null != strPageNumber) {
      // paging available
      int pageNumber;
      int pageSize;
      try {
        pageNumber = Integer.parseInt(strPageNumber);
      } catch (NumberFormatException nfe) {
        pageNumber = 1;
      }
      try {
        pageSize = Integer.parseInt(strPageSize);
      } catch (NumberFormatException nfe) {
        pageSize = 20;
      }
      Paging paging = new Paging(pageNumber, pageSize);
      option.setPaging(paging);
    }
    String[] orders = request.getParameterValues(ORDER);
    if (null != orders) {
      // order available
      OrderChain orderChain = new OrderChain();
      for (String order : orders) {
        String[] split = order.trim().split(StringConstants.VERTICAL_BAR);
        Order.Direction direction = Order.Direction.ASC;
        if (split.length > 1) {
          direction = Order.Direction.parseString(split[1]);
        }
        orderChain.addOrder(new Order(split[0].trim(), direction));
      }
      option.setOrderChain(orderChain);
    }
    return option;
  }
}
