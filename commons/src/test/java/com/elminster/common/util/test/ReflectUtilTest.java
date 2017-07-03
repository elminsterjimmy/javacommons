package com.elminster.common.util.test;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.ObjectUtil;
import com.elminster.common.util.ReflectUtil;

public class ReflectUtilTest {

  @Test
  public void testGetAllInterface() {
    Assert.assertTrue(ObjectUtil.isEqual(
        new Class<?>[] { TestInterfaceA.class }, ReflectUtil
            .getAllInterface(TestInterfaceB.class)));
  }

  @Test
  public void testGetAllField() {
    System.out.println(ObjectUtil.toString(ReflectUtil
        .getAllField(TestInterfaceB.class)));
    System.out.println(ObjectUtil.toString(ReflectUtil
        .getAllField(TestClassC.class)));
    System.out.println(ObjectUtil.toString(ReflectUtil
        .getAllMethod(TestClassC.class)));
  }

  @Test
  public void testGetAllMethod() {
    System.out.println(ObjectUtil.toString(ReflectUtil
        .getAllMethod(TestClassC.class)));
  }

  @Test
  public void testInvoke() {
  }

  @Test
  public void testGetFieldValue() throws IllegalArgumentException,
      IllegalAccessException {
    TestGetFieldClass clazz = new TestGetFieldClass();
    Assert.assertEquals(false, ReflectUtil.getFieldValue(clazz, "b1"));
    Assert.assertEquals(true, ReflectUtil.getFieldValue(clazz, "b2"));
    Assert.assertEquals(false, ReflectUtil.getFieldValue(clazz, "b3"));
    Assert.assertEquals("test1", ReflectUtil.getFieldValue(clazz, "s1"));
    Assert.assertEquals("test2", ReflectUtil.getFieldValue(clazz, "s2"));
  }

  @Test
  public void testGetCallMethodName() {
    Assert.assertEquals(
        "com.elminster.common.util.test.ReflectUtilTest#testGetCallMethodName",
        ReflectUtil.getCallMethodName(1));
  }
  
  @Test
  public void testNewInstance() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Assert.assertTrue(String.class == ReflectUtil.newInstanceViaReflect(String.class).getClass());
  }
  
  @Test
  public void testIsAssignableFrom() {
    // function (TestA a)
    // function (c)
    Assert.assertTrue(TestClassA.class.isAssignableFrom(TestClassC.class));
  }
  
  class TestGetFieldClass {
    private Boolean b1 = false;
    private boolean b2 = true;
    @SuppressWarnings("unused")
    private boolean b3 = false;
    private String s1 = "test1";
    @SuppressWarnings("unused")
    private String s2 = "test2";
    
    public boolean isB1() {
      return b1;
    }
    
    public boolean getB2() {
      return b2;
    }
    
    public String getS1() {
      return s1;
    }
  }
  
  interface TestInterfaceA {
    public static final String INTERFACE_A_STR = "INTERFACE_A_STR";
    public static final int INTERFACE_A_INT = 1;
  }
  
  interface TestInterfaceB extends TestInterfaceA {
    public static final String INTERFACE_B_STR = "INTERFACE_B_STR";
    public static final int INTERFACE_B_INT = 2;
  }
  
  class TestClassA {
    
    public static final String CLASS_A_STR = "CLASS_A_STR";
    public static final int CLASS_A_INT = 1;
    
    @SuppressWarnings("unused")
    private void funAA() {
    };
    
    protected int funAB() {
      return 1;
    }
    
    public String funAC() {
      return "AC";
    }
  }
  
  class TestClassB extends TestClassA {
    
    public static final String CLASS_B_STR = "CLASS_B_STR";
    public static final int CLASS_B_INT = 2;
    
    @SuppressWarnings("unused")
    private void funBA() {
    };
    
    protected int funBB() {
      return 2;
    }
    
    public String funBC() {
      return "BC";
    }
  }
  
  class TestClassC extends TestClassB {
    
    public static final String CLASS_C_STR = "CLASS_C_STR";
    public static final int CLASS_C_INT = 3;
    
    @SuppressWarnings("unused")
    private void funCA() {
    };
    
    protected int funCB() {
      return 3;
    }
    
    public String funCC() {
      return "CC";
    }
  }
}

