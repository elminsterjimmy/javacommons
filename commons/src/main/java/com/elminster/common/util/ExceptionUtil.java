package com.elminster.common.util;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reference to Class org.apache.commons.lang.exception.ExceptionUtils at Apache Commons Project.
 * 
 * @author Gu
 * @version 1.0
 *
 */
public abstract class ExceptionUtil {

  private static Throwable getCauseUsingMethodName(Throwable throwable, String methodName) {
    Method method = null;
    try {
      method = throwable.getClass().getMethod(methodName, new Class[0]);
    } catch (NoSuchMethodException localNoSuchMethodException) {
    } catch (SecurityException localSecurityException) {
    }
    if ((method != null) && (Throwable.class.isAssignableFrom(method.getReturnType())))
      try {
        return ((Throwable) method.invoke(throwable, new Object[0]));
      } catch (IllegalAccessException localIllegalAccessException) {
      } catch (IllegalArgumentException localIllegalArgumentException) {
      } catch (InvocationTargetException localInvocationTargetException) {
      }
    return null;
  }

  public static String getStackTrace(Throwable throwable) {
    return ExceptionUtils.getStackTrace(throwable);
  }
}
