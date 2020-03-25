package com.elminster.common.function;

/**
 * Function interface for no arg, no return.
 *
 * @author jgu
 * @version 1.0
 */
@FunctionalInterface
public interface Action {

  /**
   * Execute the action.
   */
  void execute();
}