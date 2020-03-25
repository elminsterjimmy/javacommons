package com.elminster.common.locale;

import com.elminster.common.cache.CacheTemplate;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The locale util.
 *
 * @author jgu
 * @version 1.0
 */
public class LocaleUtil {

  /** the cache of the ResourceBundles. */
  private static CacheTemplate<CacheKey, ResourceBundle> cache = new CacheTemplate<CacheKey, ResourceBundle>() {
    @Override
    protected ResourceBundle retrieveValueWhenCacheMissed(CacheKey cacheKey) throws Exception {
      return ResourceBundle.getBundle(cacheKey.bundleName, cacheKey.locate);
    }
  };

  /**
   * Get the localized message with default locale.
   *
   * @param bundleName
   *     the bundle name
   * @param key
   *     the key
   * @param params
   *     the parameters
   * @return the localized message
   */
  public static String getI18NMessage(String bundleName, String key, String... params) {
    return getI18NMessage(bundleName, Locale.getDefault(), key, params);
  }

  /**
   * Get the localized message.
   *
   * @param bundleName
   *     the bundle name
   * @param locale
   *     the locale
   * @param key
   *     the key
   * @param params
   *     the parameters
   * @return the localized message
   */
  public static String getI18NMessage(String bundleName, Locale locale, String key, String... params) {
    CacheKey cacheKey = new CacheKey(bundleName, locale);
    ResourceBundle resourceBundle = cache.getValue(cacheKey);
    if (null == resourceBundle) {
      throw new RuntimeException(String.format("resource bundle [%s] not found!", bundleName));
    }
    String pattern = resourceBundle.getString(key);
    return MessageFormat.format(pattern, params);
  }

  static class CacheKey {
    String bundleName;
    Locale locate;

    public CacheKey(String bundleName, Locale locale) {
      this.bundleName = bundleName;
      this.locate = locale;
    }
  }
}
