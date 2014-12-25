package com.elminster.common.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The Messages for extral String.
 * 
 * @author jgu
 * @version 1.0
 */
public class Messages {
  /** the static bundle name. */
  private static final String BUNDLE_NAME = Messages.class.getPackage().getName() + ".messages"; //$NON-NLS-1$

  /** the currently used bundle. */
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  /**
   * Utility.
   */
  private Messages() {
  }

  /**
   * @param key
   *          the resource key
   * @return the translated string
   */
  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }

  /**
   * Get the translated string given its key and required parameters. This is
   * wrapper around the java {@link MessageFormat} API.
   * 
   * @param key
   *          Message's key.
   * @param params
   *          The message's parameters.
   * @return The translated message or !key! if not found.
   * @see MessageFormat#format(String, Object[])
   */
  public static String getString(String key, Object... params) {
    String pattern = getString(key);
    return MessageFormat.format(pattern, params);
  }

  /**
   * The message.
   * 
   * @author jgu
   * @version 1.0
   */
  public interface Message {
    String FILE_NOT_FOUND = "FILE_NOT_FOUND"; //$NON-NLS-1$
    String ARRAY_TYPE_UNMATCH = "ARRAY_TYPE_UNMATCH"; //$NON-NLS-1$
    String COLLECTION_IS_NULL = "COLLECTION_IS_NULL"; //$NON-NLS-1$
    String DATE_IS_NULL = "DATE_IS_NULL"; //$NON-NLS-1$
    String OUT_OF_BOUND = "OUT_OF_BOUND"; //$NON-NLS-1$
    String OVER_INTEGER_RANGE = "OVER_INTEGER_RANGE"; //$NON-NLS-1$
    String OVER_LONG_RANGE = "OVER_LONG_RANGE"; //$NON-NLS-1$
    String SOURCE_FILE_ISNT_EXIST = "SOURCE_FILE_ISNT_EXIST"; //$NON-NLS-1$
    String SOURCE_FILE_IS_NULL = "SOURCE_FILE_IS_NULL"; //$NON-NLS-1$
    String SOURCE_FILE_IS_FOLDER = "SOURCE_FILE_IS_FOLDER"; //$NON-NLS-1$
    String DEST_FILE_IS_NULL = "DEST_FILE_IS_NULL"; //$NON-NLS-1$
    String FOLDER_NAME_IS_NULL = "FOLDER_NAME_IS_NULL"; //$NON-NLS-1$
    String SOURCE_FOLDER_IS_NULL = "SOURCE_FOLDER_IS_NULL"; //$NON-NLS-1$
    String SOURCE_FOLDER_ISNT_EXIST = "SOURCE_FOLDER_ISNT_EXIST"; //$NON-NLS-1$
    String SOURCE_FOLDER_IS_FILE = "SOURCE_FOLDER_IS_FILE"; //$NON-NLS-1$
    String PARA_IS_NULL = "PARA_IS_NULL"; //$NON-NLS-1$
    String FILE_CANNOT_FOUND = "FILE_CANNOT_FOUND"; //$NON-NLS-1$
    String TARGET_ARRAY_IS_NULL = "TARGET_ARRAY_IS_NULL"; //$NON-NLS-1$
    String XML_ENCODING_IS_NULL = "XML_ENCODING_IS_NULL"; //$NON-NLS-1$
  }
}
