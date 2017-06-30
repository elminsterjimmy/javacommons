package com.elminster.common.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.elminster.common.exception.ObjectInstantiationExcption;
import com.elminster.common.util.ReflectUtil;

/**
 * The Reflect Factory.
 * 
 * @author jinggu
 * @version 1.0
 * @param <T>
 *          the generic type
 */
abstract public class ReflectFactory<T> {

  /**
   * Instantiate an instance with class name and constructor args.
   * 
   * @param className
   *          the class name
   * @param argsClasses
   *          the args classes
   * @param args
   *          the args
   * @return instantiated instance
   * @throws ObjectInstantiationExcption
   *           on error
   */
  @SuppressWarnings("unchecked")
  protected T instantiateInstance(String className, Class<?>[] argsClasses, Object[] args) throws ObjectInstantiationExcption {
    try {
      Class<? extends T> clazz = (Class<? extends T>) ReflectUtil.forName(className);
      return instantiateInstance(clazz, argsClasses, args);
    } catch (ClassNotFoundException | SecurityException | IllegalArgumentException e) {
      throw new ObjectInstantiationExcption(e);
    }
  }

  /**
   * Instantiate an instance with class and constructor args.
   * 
   * @param clazz
   *          the class
   * @param argsClasses
   *          the args classes
   * @param args
   *          the args
   * @return instantiated instance
   * @throws ObjectInstantiationExcption
   *           on error
   */
  protected T instantiateInstance(Class<? extends T> clazz, Class<?>[] argsClasses, Object[] args) throws ObjectInstantiationExcption {
    try {
      Constructor<? extends T> constructor = ReflectUtil.getConstructor(clazz, argsClasses);
      T instance = constructor.newInstance(args);
      return instance;
    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new ObjectInstantiationExcption(e);
    }
  }

  /**
   * Instantiate an instance with class default constructor.
   * 
   * @param className
   *          the class name
   * @return instantiated instance
   * @throws ObjectInstantiationExcption
   *           on error
   */
  @SuppressWarnings("unchecked")
  protected T instantiateInstance(String className) throws ObjectInstantiationExcption {
    try {
      T instance = (T) ReflectUtil.newInstanceViaReflect(className);
      return instance;
    } catch (ClassNotFoundException | SecurityException | IllegalArgumentException | NoSuchMethodException | InstantiationException | IllegalAccessException
        | InvocationTargetException e) {
      throw new ObjectInstantiationExcption(e);
    }
  }

  /**
   * Instantiate an instance with class default constructor.
   * 
   * @param clazz
   *          the class
   * @return instantiated instance
   * @throws ObjectInstantiationExcption
   *           on error
   */
  protected T instantiateInstance(Class<? extends T> clazz) throws ObjectInstantiationExcption {
    try {
      T instance = ReflectUtil.newInstanceViaReflect(clazz);
      return instance;
    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new ObjectInstantiationExcption(e);
    }
  }
}
