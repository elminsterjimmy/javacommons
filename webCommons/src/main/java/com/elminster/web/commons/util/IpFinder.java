package com.elminster.web.commons.util;

import javax.servlet.http.HttpServletRequest;

import com.elminster.common.util.StringUtil;

/**
 * The IpFinder is an utility to find the ip comes from request.
 * It trying to find the ip by proxies and also directly.
 * 
 * @author jgu
 * @version 1.0
 */
final public class IpFinder {

  /**
   * The header to try for different proxies.
   */
  // @formatter:off
  private static final String[] HEADERS_TO_TRY = { 
    "X-Forwarded-For", 
    "Proxy-Client-IP", 
    "WL-Proxy-Client-IP",
    "HTTP_X_FORWARDED_FOR",
    "HTTP_X_FORWARDED",
    "HTTP_X_CLUSTER_CLIENT_IP",
    "HTTP_CLIENT_IP",
    "HTTP_FORWARDED_FOR",
    "HTTP_FORWARDED",
    "HTTP_VIA",
    "REMOTE_ADDR"
  };
  //@formatter:on

  /** Unknown ip. */
  private static final String UNKNOWN = "unknown";

  /**
   * Get the ip address from the request.
   * @param request the request
   * @return the ip address
   */
  public static String getRequestIpAddress(HttpServletRequest request) {
    for (String header : HEADERS_TO_TRY) {
      String ip = request.getHeader(header);
      if (StringUtil.isNotEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
        return ip;
      }
    }
    return request.getRemoteAddr();
  }
}
