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
   * Convert the specified array to a list which contains all elements in the array
   * 
   * @param array
   *          the specified array
   * @return a list which contains all elements in the array
   */
  public static <T> List<T> array2List(T[] array) {
    return Arrays.asList(array);
  }

  /**
   * Convert the specified collection to an array which contains all elements in the collection.
   * 
   * @param collection
   *          the specified collection
   * @return an array which contains all elements in the collection
   */
  public static Object[] collection2Array(Collection<?> collection) {
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
   * Merge an array to specified collection.
   * 
   * @param collection
   *          specified collection
   * @param array
   *          an array
   */
  public static <T> void mergeArray(Collection<T> collection, T[] array) {
    if (null == collection) {
      throw new IllegalArgumentException(Messages.getString(Message.COLLECTION_IS_NULL));
    }
    for (T elem : array) {
      collection.add(elem);
    }
  }

  /**
   * Deep clone a map, dependent on Cloneable interface.
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
          Object clone = value;
          if (value instanceof Cloneable) {
            // try to call clone()
            try {
              clone = ReflectUtil.invoke(value, "clone", new Object[] {}); // $NONNLS1$
            } catch (IllegalArgumentException e) {
              // ignore
            } catch (NoSuchMethodException e) {
              // ignore
            } catch (InvocationTargetException e) {
              // ignore
            } catch (Exception e) {
              // ignore
            }
          } else if (value instanceof Collection) {
            clone = cloneCollection((Collection) value);
          } else if (value instanceof Map) {
            clone = cloneMap((Map) value);
          }
          cloned.put(key, clone);
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
   * Deep clone a collection, dependent on Cloneable interface.
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
          } else if (entry instanceof Collection) {
            cloned.add(CollectionUtil.cloneCollection((Collection) entry));
          } else if (entry instanceof Map) {
            cloned.add(CollectionUtil.cloneMap((Map) entry));
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

  /**
   * Get the map value with default null replace.
   * 
   * @param map
   *          the map to get value
   * @param key
   *          the key
   * @param defaultValue
   *          the null place value
   * @return the map value with the specified key or the default value if null
   */
  public static <T, K> K mapGetWithNullPlace(Map<T, K> map, T key, K defaultValue) {
    K value = map.get(key);
    if (null == value) {
      value = defaultValue;
    }
    return value;
  }

  /**
   * Join the collection with the conjunction.
   * 
   * @param collection
   *          the collection
   * @param conjunction
   *          the conjunction
   * @return the joined String
   */
  public static <T> String join(Iterable<T> collection, String conjunction) {
    StringBuilder sb = new StringBuilder();
    boolean isFirst = true;
    for (T item : collection) {
      if (isFirst) {
        isFirst = false;
      } else {
        sb.append(conjunction);
      }
      sb.append(item);
    }
    return sb.toString();
  }

  /**
   * Join the array with the conjunction.
   * 
   * @param array
   *          the array
   * @param conjunction
   *          the conjunction
   * @return the joined String
   */
  public static <T> String join(T[] array, String conjunction) {
    StringBuilder sb = new StringBuilder();
    boolean isFirst = true;
    for (T item : array) {
      if (isFirst) {
        isFirst = false;
      } else {
        sb.append(conjunction);
      }
      sb.append(item);
    }
    return sb.toString();
  }

  /**
   * Get the sub collection.
   * @param collection the origin collection
   * @param start the start index (included)
   * @param end the end index (excluded)
   * @return the sub collection
   */
  @SuppressWarnings("unchecked")
  public static <T> Collection<T> subCollection(Collection<T> collection, int start, int end) {
    if (end >= start) {
      try {
        Collection<T> cloned = (Collection<T>) collection.getClass().newInstance();
        int i = 0;
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
          T next = it.next();
          if (i < start) {
            continue;
          }
          if (i >= end) {
            break;
          }
          cloned.add(next);
          i++;
        }
        return cloned;
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    } else {
      throw new IllegalArgumentException(Messages.getString(Message.START_SHOULD_NOT_GREAT_THAN_END));
    }
  }
}
