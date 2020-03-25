package com.elminster.common.locale;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Localizable annotation.
 * This annotation is used to generate particular i18n message for specified field.
 *
 * @author jgu
 * @version 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Localizable {
  /** the resource bundle name. */
  String bundle() default "";
  /** the resource key. */
  String key() default "";
  /** the addition params indicate to the field of the Object. */
  String[] params();
}