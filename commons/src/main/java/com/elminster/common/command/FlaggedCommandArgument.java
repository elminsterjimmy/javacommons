package com.elminster.common.command;

import com.elminster.common.constants.Constants;
import com.elminster.common.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;

/**
 * A flagged command argument means the argument start with a flag which starts
 * with {@literal -} or {@literal --}, like {@literal -h 3}.
 *
 * @author jgu
 * @version 1.0
 */
public class FlaggedCommandArgument extends SimpleCommandArgument {

  private final String flag;

  public FlaggedCommandArgument(String flag, String argument) {
    super(argument);
    this.flag = flag;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getQuotedArgument() {
    return ArrayUtils.insert(0, super.getQuotedArgument(), flag);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getPlainArgument() {
    return ArrayUtils.insert(0, super.getPlainArgument(), flag);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return flag + Constants.StringConstants.SPACE + super.toString();
  }
}
