package com.elminster.common.cli.test;

import com.elminster.common.cli.Command;
import com.elminster.common.cli.CommandLineExec;
import com.elminster.common.cli.CommandLineResult;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CliExecTest {

  @Test
  public void testCliExec() {
    CommandLineExec exec = new CommandLineExec();
    Future<CommandLineResult> future = exec.execute(new Command("tracert www.163.com"));
    try {
      CommandLineResult result = future.get();
      System.out.println(result);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

  }
}
