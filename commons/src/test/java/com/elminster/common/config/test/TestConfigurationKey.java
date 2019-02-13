package com.elminster.common.config.test;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.config.CommonConfiguration;
import com.elminster.common.config.IConfigProvider.BooleanKey;
import com.elminster.common.config.IConfigProvider.DoubleKey;
import com.elminster.common.config.IConfigProvider.FloatKey;
import com.elminster.common.config.IConfigProvider.IntegerKey;
import com.elminster.common.config.IConfigProvider.LongKey;
import com.elminster.common.config.IConfigProvider.StringKey;


public class TestConfigurationKey {

  class TestConfiguration extends CommonConfiguration {}
  
  @Test
  public void testConfigurationKey() {
    TestConfiguration config = new TestConfiguration();
    StringKey sk1 = new StringKey("sk1", "sk1");
    StringKey sk2 = new StringKey("sk2", "sk2");
    StringKey sk3 = new StringKey("sk3", null);
    Assert.assertEquals("sk1", config.getStringProperty(sk1));
    Assert.assertEquals("sk2", config.getStringProperty(sk2));
    Assert.assertNull(config.getStringProperty(sk3));
    
    IntegerKey ik1 = new IntegerKey("ik1", 1);
    IntegerKey ik2 = new IntegerKey("ik1", 2);
    IntegerKey ik3 = new IntegerKey("ik3", null);
    Assert.assertEquals(Integer.valueOf(1), config.getIntegerProperty(ik1));
    Assert.assertEquals(Integer.valueOf(2), config.getIntegerProperty(ik2));
    Assert.assertNull(config.getIntegerProperty(ik3));
    
    LongKey lk1 = new LongKey("lk1", 1L);
    LongKey lk2 = new LongKey("lk2", 2L);
    LongKey lk3 = new LongKey("lk3", null);
    Assert.assertEquals(Long.valueOf(1), config.getLongProperty(lk1));
    Assert.assertEquals(Long.valueOf(2), config.getLongProperty(lk2));
    Assert.assertNull(config.getLongProperty(lk3));
    
    FloatKey fk1 = new FloatKey("fk1", 1.0f);
    FloatKey fk2 = new FloatKey("fk2", 2.0f);
    FloatKey fk3 = new FloatKey("fk3", null);
    Assert.assertEquals(Float.valueOf(1.0f), config.getFloatProperty(fk1));
    Assert.assertEquals(Float.valueOf(2.0f), config.getFloatProperty(fk2));
    Assert.assertNull(config.getFloatProperty(fk3));
    
    DoubleKey dk1 = new DoubleKey("dk1", 1.0);
    DoubleKey dk2 = new DoubleKey("dk2", 2.0);
    DoubleKey dk3 = new DoubleKey("dk3", null);
    Assert.assertEquals(Double.valueOf(1.0), config.getDoubleProperty(dk1));
    Assert.assertEquals(Double.valueOf(2.0), config.getDoubleProperty(dk2));
    Assert.assertNull(config.getDoubleProperty(dk3));
    
    BooleanKey bk1 = new BooleanKey("bk1", true);
    BooleanKey bk2 = new BooleanKey("bk2", false);
    BooleanKey bk3 = new BooleanKey("bk3", null);
    Assert.assertTrue(config.getBooleanProperty(bk1));
    Assert.assertFalse(config.getBooleanProperty(bk2));
    Assert.assertNull(config.getBooleanProperty(bk3));
  }
}
