package com.elminster.common.escape;

public class XMLEscaper implements Escaper {

  private static final Escaper INTERNAL_ESCAPER =
      EscaperBuilder.newBuilder()
          .addEscape("\"", "&quot;")
          .addEscape("'", "&#39;")
          .addEscape("&", "&amp;")
          .addEscape("<", "&lt;")
          .addEscape(">", "&gt;").build();

  @Override
  public String escape(String origin) {
    return INTERNAL_ESCAPER.escape(origin);
  }

}
