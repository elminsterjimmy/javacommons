package com.elminster.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.elminster.common.util.Messages.Message;

/**
 * Collection Utilities
 * 
 * @author Gu
 * @version 1.0
 * 
 */
public abstract class CollectionUtil {

  /**
   * Check whether the specified collection is empty
   * 
   * @param collection
   *          the specified collection
   * @return whether the the specified collection is empty
   */
  public static boolean isEmpty(Collection<?> collection) {
    return null == collection || collection.isEmpty();
  }

  /**
   * Check whether the specified collection is not empty
   * 
   * @param collection
   *          the specified collection
   * @return whether the the specified collection is not empty
   */
  public static boolean isNotEmpty(Collection<?> collection) {
    return !isEmpty(collection);
  }

  /**
   * Check whether the specified map is empty
   * 
   * @param map
   *          the specified map
   * @return whether the the specified map is empty
   */
  public static boolean isEmpty(Map<?, ?> map) {
    return null == map || map.isEmpty();
  }

  /**
   * Check whether the specified map is not empty
   * 
   * @param map
   *          the specified map
   * @return whether the the specified map is not empty
   */
  public static boolean isNotEmpty(Map<?, ?> map) {
    return !isEmpty(map);
  }

  /**
   * Convert the specified array to a list which contains all elements in the
   * array
   * 
   * @param array
   *          the specified array
   * @return a list which contains all elements in the array
   */
  public static List<?> array2List(Object array) {
    return Arrays.asList(ObjectUtil.toObjectArray(array));
  }

  /**
   * Convert the specified collection to an array which contains all elements in
   * the collection
   * 
   * @param collection
   *          the specified collection
   * @return an array which contains all elements in the collection
   */
  public static Object collection2Array(Collection<?> collection) {
    if (null == collection) {
      return new Object[0];
    }
    Object[] array = null;
    Iterator<?> iterator = collection.iterator();
    while (iterator.hasNext()) {
      Object obj = iterator.next();
      Class<?> clazz = obj.getClass();
      array = (Object[]) Array.newInstance(clazz, collection.size());
      int i = 0;
      iterator = collection.iterator();
      while (iterator.hasNext()) {
        array[i++] = iterator.next();
      }
    }
    return array;
  }

  /**
   * Merge an array to specified collection
   * 
   * @param collection
   *          specified collection
   * @param array
   *          an array
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static void mergeArray(Collection collection, Object array) {
    if (null == collection) {
      throw new IllegalArgumentException(Messages.getString(Message.COLLECTION_IS_NULL));
    }
    Object[] arr = ObjectUtil.toObjectArray(array);
    for (Object elem : arr) {
      collection.add(elem);
    }
  }

  /**
   * Deep clone a map.
   * 
   * @param map
   *          map to clone
   * @return cloned map
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static Map cloneMap(Map map) {
    Map cloned = null;
    if (CollectionUtil.isNotEmpty(map)) {
      try {
        cloned = (Map) map.getClass().newInstance();
        Set<Entry> set = map.entrySet();
        Iterator<Entry> it = set.iterator();
        while (it.hasNext()) {
          Entry entry = it.next();
          Object key = entry.getKey();
          Object value = entry.getValue();
          if (value instanceof Cloneable) {
            Object clone;
            // try to call clone()
            try {
              clone = ReflectUtil.invoke(value, "clone", new Object[] {}); // $NONNLS1$
              // if successful
              value = clone;
            } catch (IllegalArgumentException e) {
              // ignore
            } catch (NoSuchMethodException e) {
              // ignore
            } catch (InvocationTargetException e) {
              // ignore
            } catch (Exception e) {
              // ignore
            }
          }
          cloned.put(key, value);
        }
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    return cloned;
  }

  /**
   * Deep clone a collection.
   * 
   * @param map
   *          collection to clone
   * @return cloned map
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static Collection cloneCollection(Collection c) {
    Collection cloned = null;
    if (CollectionUtil.isNotEmpty(c)) {
      try {
        cloned = (Collection) c.getClass().newInstance();
        Iterator it = c.iterator();
        while (it.hasNext()) {
          Object entry = it.next();
          if (entry instanceof Cloneable) {
            Object clone;
            // try to call clone()
            try {
              clone = ReflectUtil.invoke(entry, "clone", new Object[] {}); // $NONNLS1$
              // if successful
              entry = clone;
            } catch (IllegalArgumentException e) {
              // ignore
            } catch (NoSuchMethodException e) {
              // ignore
            } catch (InvocationTargetException e) {
              // ignore
            } catch (Exception e) {
              // ignore
            }
          }
          cloned.add(entry);
        }
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    return cloned;
  }
}
