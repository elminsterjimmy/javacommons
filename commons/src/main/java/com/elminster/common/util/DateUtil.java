package com.elminster.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.elminster.common.constants.Constants.TimeUnit;
import com.elminster.common.util.Messages.Message;

/**
 * The Date Utilities.
 * 
 * @author jgu
 * @version 1.0
 */
abstract public class DateUtil {

  /** the default datetime format <code>yyyy-MM-dd HH:mm:ss</code>. */
  public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"; //$NON-NLS-1$
  /** the default date format <code>yyyy-MM-dd</code>. */
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd"; //$NON-NLS-1$
  /** the simple date format <code>yyyyMMdd</code>. */
  public static final String SIMPLE_DATE_FORMAT = "yyyyMMdd"; //$NON-NLS-1$
  /** the default time format <code>HH:mm:ss</code>. */
  public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss"; //$NON-NLS-1$
  /** the ISO 8601 date format <code>yyyy-MM-dd'T'hh:mm:ssX</code>. */
  public static final String ISO8601_FORMAT = "yyyy-MM-dd'T'hh:mm:ssX"; //$NON-NLS-1$
  /** million second. */
  public static final long MILLION_SECOND = TimeUnit.MILLION_SECOND;
  /** second. */
  public static final long SECOND = TimeUnit.SECOND_IN_MS;
  /** minute. */
  public static final long MINUTE = TimeUnit.MINUTE_IN_MS;
  /** hour. */
  public static final long HOUR = TimeUnit.HOUR_IN_MS;
  /** day. */
  public static final long DAY = TimeUnit.DAY_IN_MS;
  /** week. */
  public static final long WEEK = TimeUnit.WEEK_IN_MS;

  /**
   * Get date via specified year, month, date, hours, minutes and second as
   * daily use format
   * 
   * @param year
   * @param month
   * @param date
   * @param hrs
   * @param min
   * @param sec
   * @return the Date
   */
  public static Date getDate(int year, int month, int date, int hrs, int min, int sec) {
    Calendar c = Calendar.getInstance();
    c.set(year, month - 1, date, hrs, min, sec);
    return c.getTime();
  }

  /**
   * Get date via specified year, month, date as daily use format
   * 
   * @param year
   * @param month
   * @param date
   * @return the Date
   */
  public static Date getDate(int year, int month, int date) {
    Calendar c = Calendar.getInstance();
    c.set(year, month - 1, date);
    return c.getTime();
  }

  /**
   * Get current Date.
   * 
   * @return the current Date
   */
  public static Date getCurrentDate() {
    Calendar c = Calendar.getInstance();
    Date date = c.getTime();
    return date;
  }

  /**
   * Get the String format of the current date.
   * 
   * @param format
   *          the format
   * @param locale
   *          the locale
   * @param tz
   *          the timezone
   * @return the String format of the current date.
   */
  public static String getCurrentDateStr(String format, Locale locale, TimeZone tz) {
    return getDateString(getCurrentDate(), format, locale, tz);
  }

  /**
   * Get the String format of the current date.
   * 
   * @param format
   *          the format
   * @param locale
   *          the locale
   * @return the String format of the current date.
   */
  public static String getCurrentDateStr(String format, Locale locale) {
    return getDateString(getCurrentDate(), format, locale);
  }

  /**
   * Get the String format of the current date.
   * 
   * @param format
   *          the format
   * @return the String format of the current date.
   */
  public static String getCurrentDateStr(String format) {
    return getCurrentDateStr(format, Locale.getDefault());
  }

  /**
   * Get the String format of the current date.
   * 
   * @return the String format of the current date.
   */
  public static String getCurrentDateStr() {
    return getCurrentDateStr(DEFAULT_DATETIME_FORMAT);
  }

  /**
   * Get the String format of the specified date.
   * 
   * @param date
   *          the date
   * @param format
   *          the format
   * @param locale
   *          the locale
   * @param tz
   *          the timezone
   * @return the String format of the specified date
   */
  public static String getDateString(Date date, String format, Locale locale, TimeZone tz) {
    if (null == date) {
      throw new NullPointerException(Messages.getString(Message.DATE_IS_NULL));
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
    sdf.setTimeZone(tz);
    return sdf.format(date);
  }

  /**
   * Get the String format of the specified date.
   * 
   * @param date
   *          the date
   * @param format
   *          the format
   * @param tz
   *          the timezone
   * @return the String format of the specified date
   */
  public static String getDateString(Date date, String format, TimeZone tz) {
    return getDateString(date, format, Locale.getDefault(), tz);
  }

  /**
   * Get the String format of the specified date.
   * 
   * @param date
   *          the date
   * @param format
   *          the format
   * @param locale
   *          the locale
   * @return the String format of the specified date
   */
  public static String getDateString(Date date, String format, Locale locale) {
    return getDateString(date, format, locale, TimeZone.getDefault());
  }

  /**
   * Get the String format of the specified date.
   * 
   * @param date
   *          the date
   * @param format
   *          the format
   * @return the String format of the specified date
   */
  public static String getDateString(Date date, String format) {
    return getDateString(date, format, Locale.getDefault());
  }

  /**
   * Get the String format of the specified date.
   * 
   * @param date
   *          the date
   * @return the String format of the specified date
   */
  public static String getDateString(Date date) {
    return getDateString(date, DEFAULT_DATETIME_FORMAT);
  }

  /**
   * Parser the String date into Date.
   * 
   * @param dateStr
   *          the String date
   * @param format
   *          the format
   * @param locale
   *          the locale
   * @return the Date
   * @throws ParseException
   *           on error
   */
  public static Date parserDateStr(String dateStr, String format, Locale locale) throws ParseException {
    if (null == dateStr) {
      throw new NullPointerException(Messages.getString(Message.DATE_IS_NULL));
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
    return sdf.parse(dateStr);
  }

  /**
   * Parser the String date into Date.
   * 
   * @param dateStr
   *          the String date
   * @param format
   *          the format
   * @return the Date
   * @throws ParseException
   *           on error
   */
  public static Date parserDateStr(String dateStr, String format) throws ParseException {
    return parserDateStr(dateStr, format, Locale.getDefault());
  }

  /**
   * Parser the String date into Date.
   * 
   * @param dateStr
   *          the String date
   * @return the Date
   * @throws ParseException
   *           on error
   */
  public static Date parserDateStr(String dateStr) throws ParseException {
    return parserDateStr(dateStr, DEFAULT_DATETIME_FORMAT);
  }

  /**
   * Add years to original date.
   * 
   * @param original
   *          the original date
   * @param yearCount
   *          the year count
   * @return the date add years
   */
  public static Date addYear(Date original, int yearCount) {
    Calendar c = Calendar.getInstance();
    c.setTime(original);
    c.add(Calendar.YEAR, yearCount);
    return c.getTime();
  }

  /**
   * Add months to original date.
   * 
   * @param original
   *          the original date
   * @param monthCount
   *          the month count
   * @return the date add months
   */
  public static Date addMonth(Date original, int monthCount) {
    Calendar c = Calendar.getInstance();
    c.setTime(original);
    c.add(Calendar.MONTH, monthCount);
    return c.getTime();
  }

  /**
   * Add days to original date.
   * 
   * @param original
   *          the original date
   * @param dayCount
   *          the day count
   * @return the date add days
   */
  public static Date addDay(Date original, int dayCount) {
    Calendar c = Calendar.getInstance();
    c.setTime(original);
    c.add(Calendar.DATE, dayCount);
    return c.getTime();
  }

  /**
   * Add hours to original date.
   * 
   * @param original
   *          the original date
   * @param hourCount
   *          the hour count
   * @return the date add hours
   */
  public static Date addHour(Date original, int hourCount) {
    Calendar c = Calendar.getInstance();
    c.setTime(original);
    c.add(Calendar.HOUR_OF_DAY, hourCount);
    return c.getTime();
  }

  /**
   * Add minutes to original date.
   * 
   * @param original
   *          the original date
   * @param minCount
   *          the minute count
   * @return the date add minutes
   */
  public static Date addMinute(Date original, int minCount) {
    Calendar c = Calendar.getInstance();
    c.setTime(original);
    c.add(Calendar.MINUTE, minCount);
    return c.getTime();
  }

  /**
   * Get the day between start date and end date.
   * 
   * @param start
   *          the start date
   * @param end
   *          the end date
   * @return the dyas between 2 dates
   */
  public static int getDayBetween(Date start, Date end) {
    Calendar c = Calendar.getInstance();
    c.setTime(start);
    long startTime = c.getTimeInMillis();
    c.setTime(end);
    long endTime = c.getTimeInMillis();
    long betweenDays = (endTime - startTime) / DAY;
    return (new Long(betweenDays)).intValue();
  }

  /**
   * Get weekday of given date.
   * 
   * @param date
   *          the date
   * @param locale
   *          the locale
   * @return the weekday
   */
  public static String getWeekday(Date date, Locale locale) {
    if (null == date) {
      throw new NullPointerException(Messages.getString(Message.DATE_IS_NULL));
    }
    SimpleDateFormat sdf = new SimpleDateFormat("E", locale); //$NON-NLS-1$
    return sdf.format(date);
  }

  /**
   * Get weekday of given date.
   * 
   * @param date
   *          the date
   * @return the weekday
   */
  public static String getWeekday(Date date) {
    return getWeekday(date, Locale.getDefault());
  }
}
