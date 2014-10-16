package com.elminster.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reference to Class org.apache.commons.lang.exception.ExceptionUtils
 * at Apache Commons Project.
 * 
 * @author Gu
 * @version 1.0
 *
 */
public abstract class ExceptionUtil {
	
	/**
     * <p>The names of methods commonly used to access a wrapped exception.</p>
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
	
	// Lock object for CAUSE_METHOD_NAMES
    private static final Object CAUSE_METHOD_NAMES_LOCK = new Object();
    
    /**
     * <p>The Method object for Java 1.4 getCause.</p>
     */
    private static final Method THROWABLE_CAUSE_METHOD;
    
    /**
     * <p>The Method object for Java 1.4 initCause.</p>
     */
    @SuppressWarnings("unused")
	private static final Method THROWABLE_INITCAUSE_METHOD;
    
    static {
        Method causeMethod;
        try {
            causeMethod = Throwable.class.getMethod("getCause", null); //$NON-NLS-1$
        } catch (Exception e) {
            causeMethod = null;
        }
        THROWABLE_CAUSE_METHOD = causeMethod;
        try {
            causeMethod = Throwable.class.getMethod("initCause", new Class[]{Throwable.class}); //$NON-NLS-1$
        } catch (Exception e) {
            causeMethod = null;
        }
        THROWABLE_INITCAUSE_METHOD = causeMethod;
    }
    
    /**
     * <p>Checks if the Throwable class has a <code>getCause</code> method.</p>
     *
     * <p>This is true for JDK 1.4 and above.</p>
     *
     * @return true if Throwable is nestable
     * @since 2.0
     */
    public static boolean isThrowableNested() {
        return THROWABLE_CAUSE_METHOD != null;
    }
	
	/**
     * <p>Returns the list of <code>Throwable</code> objects in the
     * exception chain.</p>
     *
     * <p>A throwable without cause will return a list containing
     * one element - the input throwable.
     * A throwable with one cause will return a list containing
     * two elements. - the input throwable and the cause throwable.
     * A <code>null</code> throwable will return a list of size zero.</p>
     *
     * <p>This method handles recursive cause structures that might
     * otherwise cause infinite loops. The cause chain is processed until
     * the end is reached, or until the next item in the chain is already
     * in the result set.</p>
     *
     * @param throwable  the throwable to inspect, may be null
     * @return the list of throwables, never null
     * @since Commons Lang 2.2
     */
    public static List<Throwable> getThrowableList(Throwable throwable) {
        List<Throwable> list = new ArrayList<Throwable>();
        while (throwable != null && list.contains(throwable) == false) {
            list.add(throwable);
            throwable = getCause(throwable);
        }
        return list;
    }
	
	/**
     * <p>Returns the list of <code>Throwable</code> objects in the
     * exception chain.</p>
     *
     * <p>A throwable without cause will return an array containing
     * one element - the input throwable.
     * A throwable with one cause will return an array containing
     * two elements. - the input throwable and the cause throwable.
     * A <code>null</code> throwable will return an array of size zero.</p>
     *
     * <p>From version 2.2, this method handles recursive cause structures
     * that might otherwise cause infinite loops. The cause chain is
     * processed until the end is reached, or until the next item in the
     * chain is already in the result set.</p>
     *
     * @see #getThrowableList(Throwable)
     * @param throwable  the throwable to inspect, may be null
     * @return the array of throwables, never null
     */
    public static Throwable[] getThrowables(Throwable throwable) {
        List<Throwable> list = getThrowableList(throwable);
        return list.toArray(new Throwable[list.size()]);
    }
    
    /**
     * <p>Checks whether this <code>Throwable</code> class can store a cause.</p>
     *
     * <p>This method does <b>not</b> check whether it actually does store a cause.<p>
     *
     * @param throwable  the <code>Throwable</code> to examine, may be null
     * @return boolean <code>true</code> if nested otherwise <code>false</code>
     * @since 2.0
     */
    public static boolean isNestedThrowable(Throwable throwable) {
        if (throwable == null) {
            return false;
        }

        if (throwable instanceof Nestable) {
            return true;
        } else if (throwable instanceof SQLException) {
            return true;
        } else if (throwable instanceof InvocationTargetException) {
            return true;
        } else if (isThrowableNested()) {
            return true;
        }

        Class<? extends Throwable> cls = throwable.getClass();
        synchronized(CAUSE_METHOD_NAMES_LOCK) {
            for (int i = 0, isize = CAUSE_METHOD_NAMES.length; i < isize; i++) {
                try {
                    Method method = cls.getMethod(CAUSE_METHOD_NAMES[i], null);
                    if (method != null && Throwable.class.isAssignableFrom(method.getReturnType())) {
                        return true;
                    }
                } catch (NoSuchMethodException ignored) {
                    // exception ignored
                } catch (SecurityException ignored) {
                    // exception ignored
                }
            }
        }

        try {
            Field field = cls.getField("detail"); //$NON-NLS-1$
            if (field != null) {
                return true;
            }
        } catch (NoSuchFieldException ignored) {
            // exception ignored
        } catch (SecurityException ignored) {
            // exception ignored
        }

        return false;
    }

    /**
     * <p>A way to get the entire nested stack-trace of an throwable.</p>
     *
     * <p>The result of this method is highly dependent on the JDK version
     * and whether the exceptions override printStackTrace or not.</p>
     *
     * @param throwable  the <code>Throwable</code> to be examined
     * @return the nested stack trace, with the root cause first
     * @since 2.0
     */
    public static String getFullStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        Throwable[] ts = getThrowables(throwable);
        for (int i = 0; i < ts.length; i++) {
            ts[i].printStackTrace(pw);
            if (isNestedThrowable(ts[i])) {
                break;
            }
        }
        return sw.getBuffer().toString();
    }
    
    /**
     * <p>Introspects the <code>Throwable</code> to obtain the cause.</p>
     *
     * <p>The method searches for methods with specific names that return a 
     * <code>Throwable</code> object. This will pick up most wrapping exceptions,
     * including those from JDK 1.4, and
     * {@link org.apache.commons.lang.exception.NestableException NestableException}.
     * The method names can be added to using {@link #addCauseMethodName(String)}.</p>
     *
     * <p>The default list searched for are:</p>
     * <ul>
     *  <li><code>getCause()</code></li>
     *  <li><code>getNextException()</code></li>
     *  <li><code>getTargetException()</code></li>
     *  <li><code>getException()</code></li>
     *  <li><code>getSourceException()</code></li>
     *  <li><code>getRootCause()</code></li>
     *  <li><code>getCausedByException()</code></li>
     *  <li><code>getNested()</code></li>
     * </ul>
     * 
     * <p>In the absence of any such method, the object is inspected for a
     * <code>detail</code> field assignable to a <code>Throwable</code>.</p>
     *
     * <p>If none of the above is found, returns <code>null</code>.</p>
     *
     * @param throwable  the throwable to introspect for a cause, may be null
     * @return the cause of the <code>Throwable</code>,
     *  <code>null</code> if none found or null throwable input
     * @since 1.0
     */
    public static Throwable getCause(Throwable throwable) {
        synchronized(CAUSE_METHOD_NAMES_LOCK) {
            return getCause(throwable, CAUSE_METHOD_NAMES);
        }
    }
    
    /**
     * <p>Introspects the <code>Throwable</code> to obtain the cause.</p>
     *
     * <ol>
     * <li>Try known exception types.</li>
     * <li>Try the supplied array of method names.</li>
     * <li>Try the field 'detail'.</li>
     * </ol>
     *
     * <p>A <code>null</code> set of method names means use the default set.
     * A <code>null</code> in the set of method names will be ignored.</p>
     *
     * @param throwable  the throwable to introspect for a cause, may be null
     * @param methodNames  the method names, null treated as default set
     * @return the cause of the <code>Throwable</code>,
     *  <code>null</code> if none found or null throwable input
     * @since 1.0
     */
    public static Throwable getCause(Throwable throwable, String[] methodNames) {
        if (throwable == null) {
            return null;
        }
        Throwable cause = getCauseUsingWellKnownTypes(throwable);
        if (cause == null) {
            if (methodNames == null) {
                synchronized(CAUSE_METHOD_NAMES_LOCK) {
                    methodNames = CAUSE_METHOD_NAMES;
                }
            }
            for (int i = 0; i < methodNames.length; i++) {
                String methodName = methodNames[i];
                if (methodName != null) {
                    cause = getCauseUsingMethodName(throwable, methodName);
                    if (cause != null) {
                        break;
                    }
                }
            }

            if (cause == null) {
                cause = getCauseUsingFieldName(throwable, "detail"); //$NON-NLS-1$
            }
        }
        return cause;
    }
    
    /**
     * <p>Finds a <code>Throwable</code> by field name.</p>
     *
     * @param throwable  the exception to examine
     * @param fieldName  the name of the attribute to examine
     * @return the wrapped exception, or <code>null</code> if not found
     */
    private static Throwable getCauseUsingFieldName(Throwable throwable, String fieldName) {
        Field field = null;
        try {
            field = throwable.getClass().getField(fieldName);
        } catch (NoSuchFieldException ignored) {
            // exception ignored
        } catch (SecurityException ignored) {
            // exception ignored
        }

        if (field != null && Throwable.class.isAssignableFrom(field.getType())) {
            try {
                return (Throwable) field.get(throwable);
            } catch (IllegalAccessException ignored) {
                // exception ignored
            } catch (IllegalArgumentException ignored) {
                // exception ignored
            }
        }
        return null;
    }
    
    /**
     * <p>Finds a <code>Throwable</code> for known types.</p>
     * 
     * <p>Uses <code>instanceof</code> checks to examine the exception,
     * looking for well known types which could contain chained or
     * wrapped exceptions.</p>
     *
     * @param throwable  the exception to examine
     * @return the wrapped exception, or <code>null</code> if not found
     */
    private static Throwable getCauseUsingWellKnownTypes(Throwable throwable) {
        if (throwable instanceof Nestable) {
            return ((Nestable) throwable).getCause();
        } else if (throwable instanceof SQLException) {
            return ((SQLException) throwable).getNextException();
        } else if (throwable instanceof InvocationTargetException) {
            return ((InvocationTargetException) throwable).getTargetException();
        } else {
            return null;
        }
    }
    
    /**
     * <p>Finds a <code>Throwable</code> by method name.</p>
     *
     * @param throwable  the exception to examine
     * @param methodName  the name of the method to find and invoke
     * @return the wrapped exception, or <code>null</code> if not found
     */
    private static Throwable getCauseUsingMethodName(Throwable throwable, String methodName) {
        Method method = null;
        try {
            method = throwable.getClass().getMethod(methodName, null);
        } catch (NoSuchMethodException ignored) {
            // exception ignored
        } catch (SecurityException ignored) {
            // exception ignored
        }

        if (method != null && Throwable.class.isAssignableFrom(method.getReturnType())) {
            try {
                return (Throwable) method.invoke(throwable, new Object[0]);
            } catch (IllegalAccessException ignored) {
                // exception ignored
            } catch (IllegalArgumentException ignored) {
                // exception ignored
            } catch (InvocationTargetException ignored) {
                // exception ignored
            }
        }
        return null;
    }
}
