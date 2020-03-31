package com.elminster.common.command.test;

import com.elminster.common.command.CommandArgument;
import com.elminster.common.command.CommandArgumentBuilder;
import org.junit.Assert;
import org.junit.Test;

public class CommandArgumentTest {

  @Test
  public void testSensitive() {
    CommandArgument commandArgument = CommandArgumentBuilder.create("abc", true);
    Assert.assertEquals("******", commandArgument.toString());
    CommandArgument flaggedCommandArgument = CommandArgumentBuilder.createFlagged("-h", "abc", true);
    Assert.assertEquals("-h ******", flaggedCommandArgument.toString());
  }
}