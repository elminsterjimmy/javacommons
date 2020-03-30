package com.elminster.common.cli.test;

import com.elminster.common.cli.Command;
import com.elminster.common.cli.CommandLineExec;
import com.elminster.common.cli.CommandLineResult;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CliExecTest {

  @Test
  public void testCliExec() {
    CommandLineExec exec = new CommandLineExec();
    Future<CommandLineResult> future = exec.execute(new Command("tracert www.163.com"));
    try {
      CommandLineResult result = future.get(10, TimeUnit.SECONDS);
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
