package com.elminster.spring.web.upload;

import com.elminster.common.util.Assert;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * The lazy-load multipart resolver.
 * Usage:
 * <pre>
 * {@code
 *   <bean id="multipartResolver" class="com.emc.mystic.manager.web.webutil.MysticMultipartResolver">
 *         <property name="defaultEncoding" value="UTF-8" />
 *         <property name="maxUploadSize" value="10240000" />
 *         <property name="maxInMemorySize" value="1024" />
 *         <property name="lazyResolveUrlPatterns">
 *             <list>
 *                 <value>(.)* /upload</value>
 *              </list>
 *          </property>
 *  </bean>
 * }
 * </pre>
 *
 * @author jgu
 * @version 1.0
 */
public class LazyloadMultipartResolver extends CommonsMultipartResolver {

  /** the url patterns for lazyload. */
  private String[] lazyResolveUrlPatterns;

  /** Get the url patterns for lazyload */
  public String[] getLazyResolveUrlPatterns() {
    return lazyResolveUrlPatterns;
  }

  /** Set the url patterns for lazyload */
  public void setLazyResolveUrlPatterns(String[] lazyResolveUrlPatterns) {
    this.lazyResolveUrlPatterns = lazyResolveUrlPatterns;
  }

  /**
   * Wrapper the {@link HttpServletRequest} to {@link DefaultMultipartHttpServletRequest} and override
   * the {@see DefaultMultipartHttpServletRequest#initializeMultipart} to resolve the multipart request.
   *
   * @param request the HTTP request
   * @return the Multipart HTTP request
   * @throws MultipartException on error
   */
  @Override
  public MultipartHttpServletRequest resolveMultipart(final HttpServletRequest request) throws MultipartException {
    Assert.notNull(request, "Request must not be null");
    for (String pattern : lazyResolveUrlPatterns) {
      if (request.getRequestURI().matches(pattern)) {
        return new DefaultMultipartHttpServletRequest(request) {
          @Override
          protected void initializeMultipart() {
            MultipartParsingResult parsingResult = parseRequest(request);
            setMultipartFiles(parsingResult.getMultipartFiles());
            setMultipartParameters(parsingResult.getMultipartParameters());
            setMultipartParameterContentTypes(parsingResult.getMultipartParameterContentTypes());
          }
        };
      }
    }
    MultipartParsingResult parsingResult = parseRequest(request);
    return new DefaultMultipartHttpServletRequest(request, parsingResult.getMultipartFiles(),
        parsingResult.getMultipartParameters(), parsingResult.getMultipartParameterContentTypes());
  }
}
