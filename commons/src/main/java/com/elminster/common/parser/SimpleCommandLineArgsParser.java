package com.elminster.common.parser;

import com.elminster.common.constants.Constants.StringConstants;
import com.elminster.common.command.CommandLineArgs;
import com.elminster.common.util.StringUtil;

/**
 * A simple parser to parse the command line arguments into CommandLineArgs.
 * 
 * @author jgu
 * @version 1.0
 */
public class SimpleCommandLineArgsParser implements IParser<String[], CommandLineArgs> {

  /** the option marks. */
  private static final String[] OPTION_MARK = { StringConstants.HYPHEN + StringConstants.HYPHEN,
    StringConstants.HYPHEN };

  /**
   * {@inheritDoc}
   */
  @Override
  public CommandLineArgs parse(String[] args) throws ParseException {
    CommandLineArgs clArgs = new CommandLineArgs();

    if (null != args) {
      boolean isOpt = false;
      String optName = null;
      String lastOptName = null;
      for (String arg : args) {
        String trimmed = arg.trim();
        lastOptName = optName;
        optName = getOpt(trimmed);
        if (isOpt) {
          if (null == optName) {
            if (StringUtil.isEmpty(trimmed)) {
              trimmed = null;
            }
            clArgs.addOptionArg(lastOptName, trimmed);
            optName = null;
            isOpt = false;
          } else {
            clArgs.addOptionArg(lastOptName, null);
          }
          lastOptName = null;
        } else {
          if (StringUtil.isNotEmpty(optName)) {
            isOpt = true;
          } else {
            if (StringUtil.isNotEmpty(trimmed)) {
              clArgs.addNonOptionArg(trimmed);
            }
          }
        }
      }
    }

    return clArgs;
  }

  /**
   * Get option name.
   * @param trimmed the trimmed String
   * @return the option name if the String start with <code>-</code> or <code>--</code>
   */
  private String getOpt(String trimmed) {
    for (String optionMark : OPTION_MARK) {
      if (trimmed.startsWith(optionMark)) {
        return trimmed.substring(optionMark.length());
      }
    }
    return null;
  }
}
