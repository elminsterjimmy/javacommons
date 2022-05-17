package com.elminster.common.escape;

import java.util.ArrayList;
import java.util.List;

/**
 * The Escaper builder.
 *
 * @author jgu
 * @version 1.0
 */
public class EscaperBuilder {

  /** the escape pairs. */
  private List<Pair> escapePairs = new ArrayList<>();

  /**
   * Create a new EscaperBuilder.
   * @return a new EscaperBuilder
   */
  public static EscaperBuilder newBuilder() {
    return new EscaperBuilder();
  }

  /**
   * Builder a new String Escaper.
   * @return a new String Escaper
   */
  public Escaper build() {
    return new StringReplaceEscaper();
  }

  /**
   * Add a escape.
   * @param escape the String need to escape
   * @param escaped the String escaped
   * @return the EscaperBuilder
   */
  public EscaperBuilder addEscape(String escape, String escaped) {
    escapePairs.add(new Pair(escape, escaped));
    return this;
  }

  class Pair {
    String src;
    String dest;

    public Pair(String src, String dest) {
      this.src = src;
      this.dest = dest;
    }
  }

  /**
   * The String Replace Escaper.
   *
   * @author jgu
   * @version 1.0
   */
  class StringReplaceEscaper implements Escaper {

    /**
     * {@inheritDoc}
     */
    @Override
    public String escape(String origin) {
      if (null == origin) {
        return null;
      }
      String escaped = origin;
      int strLen = escaped.length();
      StringBuilder sb = new StringBuilder(strLen * 2);

      int p = 0;
      while (p < strLen) {
        boolean match = false;
        for (Pair pair : escapePairs) {
          int len = pair.src.length();
          if (p + len < strLen) {
            String sub = escaped.substring(p, p + len);
            if (sub.equals(pair.src)) {
              sb.append(pair.dest);
              p += len;
              match = true;
              break;
            }
          }
        }
        if (!match) {
          sb.append(escaped.charAt(p));
          p++;
        }
      }
      return sb.toString();
    }
  }
}
