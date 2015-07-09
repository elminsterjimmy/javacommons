package com.elminster.common.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.elminster.common.constants.Constants.CharacterConstants;
import com.elminster.common.constants.Constants.EncodingConstants;
import com.elminster.common.constants.Constants.StringConstants;

/**
 * The CSV helper.
 * 
 * @author jgu
 * @version 1.0
 */
final public class CSVHelper {

  /** encoding. */
  private static final String ENCODING = EncodingConstants.UTF8;
  /** Split: "," */
  private static final String SPLIT = StringConstants.COMMA;

  /** line items */
  private List<Element> items = new Vector<Element>();

  /**
   * Constructor.
   */
  public CSVHelper() {
  }

  /**
   * Add item to CVS line.
   * 
   * @param item
   *          the item
   */
  public void addItem(Object item) {
    addItem(item.toString(), false);
  }

  /**
   * Add item to line with option enquote.
   * 
   * @param item
   *          the item
   * @param enquote
   *          has enquote
   */
  public void addItem(Object item, boolean enquote) {
    if (null == item) {
      item = "";
    }
    items.add(new Element(item.toString(), enquote));
  }

  /**
   * Get a CVS line.
   * 
   * @return a CVS line
   */
  public String getLine() {
    StringBuffer list = new StringBuffer();
    boolean first = true;
    for (Element element : items) {
      if (first) {
        first = false;
      } else {
        list.append(SPLIT);
      }
      String item = element.getItem();
      list.append(item);
    }
    list.append(StringUtil.newline());
    items.clear();
    return list.toString();
  }

  /**
   * Get element size of line.
   * 
   * @return element size of line
   */
  public int size() {
    return items.size();
  }

  /**
   * Get the specified pure item(without enquote) of the element.
   * 
   * @param n
   *          index of the element
   * @return the item
   */
  public String getItem(int n) {
    Element element = items.get(n);
    return element.getRawItem();
  }

  /**
   * Remove the specified item from element.
   * 
   * @param n
   *          index of the element
   */
  public void removeItem(int n) {
    items.remove(n);
  }

  /**
   * Get enumeration of the element.
   * 
   * @return the enumeration of the element
   */
  public Enumeration<String> elements() {
    return new CSVLineEnumerator(items);
  }

  /**
   * Write line to specified file.
   * 
   * @param file
   *          the file to write
   * @throws Exception
   *           if write error
   */
  public void write(String file) throws Exception {
    FileUtil.writeLine2file(this.getLine(), file, true, ENCODING);
  }

  class Element {
    /** actual item */
    private String item;
    /** enquote or not */
    private boolean enquote;

    /**
     * Constructor.
     * 
     * @param item
     *          the item
     */
    Element(String item) {
      this(item, false);
    }

    /**
     * Constructor
     * 
     * @param item
     *          the item
     * @param enquote
     *          is enquote
     */
    Element(String item, boolean enquote) {
      this.item = item;
      this.enquote = enquote;
    }

    /**
     * Get enquoted item
     * 
     * @return enquoted item
     */
    public String getItem() {
      return enquote(item, enquote);
    }

    /**
     * Get pure item
     * 
     * @return pure item
     */
    public String getRawItem() {
      return item;
    }
  }

  class CSVLineEnumerator implements Enumeration<String> {
    /** line items */
    private List<Element> items;
    /** index */
    private int n;

    /**
     * Constructor.
     * 
     * @param items
     *          line items
     */
    CSVLineEnumerator(List<Element> items) {
      this.items = items;
      n = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String nextElement() {
      n++;
      Element element = items.get(n - 1);
      return element.getRawItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMoreElements() {
      return n < items.size();
    }
  }

  /**
   * Get enquoted item
   * 
   * @param item
   *          the item
   * @return enquoted item
   */
  public static String enquote(String item) {
    return enquote(item, false);
  }

  /**
   * Get enquoted item
   * 
   * @param item
   *          the item
   * @param enquote
   *          is enquote
   * @return enquoted item
   */
  public static String enquote(String item, boolean enquote) {
    if (StringUtil.isEmpty(item)) {
      if (enquote) {
        // ""
        return StringConstants.DOUBLE_QUOTE + StringConstants.DOUBLE_QUOTE;
      } else {
        return StringConstants.EMPTY_STRING;
      }
    }

    if (item.indexOf(StringConstants.LF) < 0 && item.indexOf(StringConstants.CR) < 0
        && item.indexOf(StringConstants.DOUBLE_QUOTE) < 0 && item.indexOf(StringConstants.COMMA) < 0 && !enquote) {
      return item;
    }

    StringBuffer sb = new StringBuffer(item.length() * 2 + 2);
    sb.append(StringConstants.DOUBLE_QUOTE);
    for (int ind = 0; ind < item.length(); ind++) {
      char ch = item.charAt(ind);
      if (CharacterConstants.DOUBLE_QUOTE == ch || CharacterConstants.COMMA == ch) {
        sb.append(CharacterConstants.DOUBLE_QUOTE);
      }
      sb.append(ch);
    }
    sb.append(CharacterConstants.DOUBLE_QUOTE);
    return new String(sb);
  }

  public static String enquoteForLIB(String item) {
    if (item.length() == 0) {
      return item;
    }
    if (item.indexOf(StringConstants.DOUBLE_QUOTE) < 0 && item.indexOf(StringConstants.COMMA) < 0) {
      return item;
    }

    StringBuffer sb = new StringBuffer();
    for (int ind = 0; ind < item.length(); ind++) {
      char ch = item.charAt(ind);
      if ((CharacterConstants.DOUBLE_QUOTE != ch) && (CharacterConstants.COMMA != ch)) {
        sb.append(ch);
      }
    }

    return sb.toString();
  }

  /**
   * Write CVS lines to file.
   * 
   * @param file
   *          the file to write
   * @param csvLines
   *          CVS lines
   * @throws Exception
   *           if write error
   */
  public static void write(String file, String[] csvLines) throws Exception {
    FileUtil.writeLines2file(CollectionUtil.array2List(csvLines), file, true, ENCODING);
  }

  /**
   * 
   * @param target
   * @param delimiter
   * @return
   */
  public static List<String> split(String target, String delimiter) {
    List<String> result = new ArrayList<String>();
    int pos = 0;
    String temp = target;
    while ((pos = temp.indexOf(delimiter)) != -1) {
      result.add(temp.substring(0, pos));
      temp = temp.substring(pos + 1);
    }
    //
    if (target.endsWith(delimiter)) {
      result.add(StringConstants.EMPTY_STRING);
    } else {
      result.add(temp);
    }
    return result;
  }

  /**
   * Split a CVS line into a item list.
   * 
   * @param str
   *          the CVS line
   * @return a item list
   */
  public static List<String> split(String str) {
    List<String> list = new ArrayList<String>();

    // .*".+".*
    String pattern = ".*\".+\".*";
    Pattern p = Pattern.compile(pattern);
    Matcher matcher = p.matcher(str);

    boolean special = matcher.find();

    if (special) {
      // sign stack
      Stack<String> signStack = new Stack<String>();
      // String queue
      Queue<String> stringQueue = new LinkedList<String>();

      boolean closed = true;

      // remove "/r", "/n"
      str = StringUtil.chomp(str);

      int length = str.length();

      StringBuilder string = new StringBuilder();
      for (int i = 0; i < length; i++) {
        char c = str.charAt(i);

        if (closed && CharacterConstants.COMMA == c) {
          // ","
          // enqueue to the string
          stringQueue.offer(string.toString());
          // reset the string
          string = new StringBuilder();
        } else if (CharacterConstants.DOUBLE_QUOTE == c) {
          // """
          if (closed) {
            // set the close flag
            closed = false;
            // push to the stack
            signStack.push(StringConstants.SHARP);
            // do nothing
          } else {
            // unclosed
            if (length == i + 1 || CharacterConstants.COMMA == str.charAt(i + 1)) {
              // last character or following a ","
              // reset the close flag
              closed = true;
              // pop from the stack
              signStack.pop();
              // do nothing
            } else {
              // other condition
              // ignore the """
              // add the next character to the string, and skip it
              string.append(str.charAt(++i));
              // do nothing with the flag and the stack
            }
          }
        } else {
          // add to the string
          string.append(c);
        }

      } // end for

      // last string is left, add into queue
      stringQueue.add(string.toString());

      // analyze fail
      if (!closed || !signStack.isEmpty()) {
        return null;
      } else {
        // insert into the return list
        list.addAll(stringQueue);
      }
    } else {
      list = CSVHelper.split(str, StringConstants.COMMA);
    }

    return list;
  }
}
