package com.elminster.common.command.test;

import com.elminster.common.command.Command;
import com.elminster.common.command.CommandArgumentBuilder;
import com.elminster.common.command.CommandLineExec;
import com.elminster.common.command.CommandLineResult;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CommandExecTest {

  @Test
  public void testCliExec() {
    CommandLineExec exec = new CommandLineExec();
    Future<CommandLineResult> future = exec.execute(new Command("ping",
        CommandArgumentBuilder.createFlagged("-n", "5"),
        CommandArgumentBuilder.createFlagged("-i", "64"),
        CommandArgumentBuilder.create("www.163.com")));
    try {
      CommandLineResult result = future.get(100, TimeUnit.SECONDS);
      System.out.println(result);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
      future.cancel(true);
    }
  }
}
