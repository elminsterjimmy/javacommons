package com.elminster.common.util.test;

import java.util.Date;
import java.util.Locale;

import junit.framework.Assert;

import org.junit.Test;

import com.elminster.common.util.DateUtil;

public class DateUtilTest {

  @Test
  public void testDayBetween() {
    Date date1 = DateUtil.getDate(2000, 1, 1);
    Date date2 = DateUtil.getDate(2000, 2, 1);
    Assert.assertEquals(31, DateUtil.getDayBetween(date1, date2));
  }
  
  @Test
  public void testAddDay() {
    Date date1 = DateUtil.getDate(2000, 1, 1);
    Date date2 = DateUtil.getDate(2000, 1, 2);
    Date date3 = DateUtil.getDate(1999, 12, 31);
    Assert.assertEquals(date2, DateUtil.addDay(date1, 1));
    Assert.assertEquals(date3, DateUtil.addDay(date1, -1));
    Assert.assertEquals(date2, DateUtil.addDay(date3, 2));
  }

  @Test
  public void testGetWeekDay() {
    Date date = DateUtil.getDate(2011, 7, 8);
    Assert.assertEquals("Fri", DateUtil.getWeekday(date, Locale.ENGLISH));
  }
}
