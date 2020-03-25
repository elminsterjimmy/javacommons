package com.elminster.common.escape;

/**
 * The XML escaper.
 *
 * @author jgu
 * @version 1.0
 */
public class XMLEscaper implements Escaper {

  private static final Escaper INTERNAL_ESCAPER =
      EscaperBuilder.newBuilder()
          .addEscape("\"", "&quot;")
          .addEscape("'", "&#39;")
          .addEscape("&", "&amp;")
          .addEscape("<", "&lt;")
          .addEscape(">", "&gt;").build();

  /**
   * {@inheritDoc}
   */
  @Override
  public String escape(String origin) {
    return INTERNAL_ESCAPER.escape(origin);
  }

}
