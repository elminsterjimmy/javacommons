package com.elminster.common.escape;

import java.util.ArrayList;
import java.util.List;

public class EscaperBuilder {

  private List<Pair> cache = new ArrayList<>();

  public static EscaperBuilder newBuilder() {
    return new EscaperBuilder();
  }

  public Escaper build() {
    return new StringReplaceEscaper();
  }

  public EscaperBuilder addEscape(String escape, String escaped) {
    cache.add(new Pair(escape, escaped));
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

  class StringReplaceEscaper implements Escaper {

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
        for (Pair pair : cache) {
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
