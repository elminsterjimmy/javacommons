package com.elminster.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Reference to Class org.apache.commons.lang.exception.ExceptionUtils at Apache Commons Project.
 * 
 * @author Gu
 * @version 1.0
 *
 */
public abstract class ExceptionUtil {

  /**
   * <p>
   * The names of methods commonly used to access a wrapped exception.
   * </p>
   */
  private static String[] CAUSE_METHOD_NAMES = { 
      "getCause", //$NON-NLS-1$
      "getNextException", //$NON-NLS-1$
      "getTargetException", //$NON-NLS-1$
      "getException", //$NON-NLS-1$
      "getSourceException", //$NON-NLS-1$
      "getRootCause", //$NON-NLS-1$
      "getCausedByException", //$NON-NLS-1$
      "getNested", //$NON-NLS-1$
      "getLinkedException", //$NON-NLS-1$
      "getNestedException", //$NON-NLS-1$
      "getLinkedCause", //$NON-NLS-1$
      "getThrowable", //$NON-NLS-1$
  };

  public static int getThrowableCount(Throwable throwable) {
    return getThrowableList(throwable).size();
  }

  public static Throwable[] getThrowables(Throwable throwable) {
    List<Throwable> list = getThrowableList(throwable);
    return ((Throwable[]) list.toArray(new Throwable[list.size()]));
  }

  public static List<Throwable> getThrowableList(Throwable throwable) {
    List<Throwable> list = new ArrayList<>();
    while ((throwable != null) && (!(list.contains(throwable)))) {
      list.add(throwable);
      throwable = getCause(throwable);
    }
    return list;
  }

  @Deprecated
  public static Throwable getCause(Throwable throwable) {
    return getCause(throwable, null);
  }

  @Deprecated
  public static Throwable getCause(Throwable throwable, String[] methodNames) {
    if (throwable == null)
      return null;
    Throwable cause;
    if (methodNames == null) {
      cause = throwable.getCause();
      if (cause != null) {
        return cause;
      }

      methodNames = CAUSE_METHOD_NAMES;
    }

    for (String methodName : methodNames) {
      if (methodName != null) {
        Throwable legacyCause = getCauseUsingMethodName(throwable, methodName);
        if (legacyCause != null) {
          return legacyCause;
        }
      }
    }

    return null;
  }

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
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    throwable.printStackTrace(pw);
    return sw.getBuffer().toString();
  }
  
  /**
   * Use {@link ExceptionUtil#getStackTrace(Throwable)} instead.
   * @see https://issues.apache.org/jira/browse/LANG-491
   */
  @Deprecated
  public static String getFullStackTrace(Throwable throwable) {
    return getStackTrace(throwable);
  }
}
