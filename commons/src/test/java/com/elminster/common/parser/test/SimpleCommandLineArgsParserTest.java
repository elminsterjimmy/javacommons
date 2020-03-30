package com.elminster.common.parser.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.constants.Constants.StringConstants;
import com.elminster.common.cli.CommandLineArgs;
import com.elminster.common.parser.ParseException;
import com.elminster.common.parser.SimpleCommandLineArgsParser;

public class SimpleCommandLineArgsParserTest {

  @Test
  public void testParseArguments() throws ParseException {
    String command = "-i  -v  --cookie \"CSRF-TOKEN=token\" -H \"Content-Type:application/json\" -H \"X-CSRF-TOKEN:token\" --data-binary @d:/test.json localhost:8080/user/login";
    
    String[] clArguments = command.split(StringConstants.SPACE);
    SimpleCommandLineArgsParser parser = new SimpleCommandLineArgsParser();
    CommandLineArgs commandLineArgs = parser.parse(clArguments);
    List<String> optI = commandLineArgs.getOptionValues("i");
    Assert.assertTrue(null != optI);
    Assert.assertTrue(0 == optI.size());
    List<String> optV = commandLineArgs.getOptionValues("v");
    Assert.assertTrue(null != optV);
    Assert.assertTrue(0 == optV.size());
    List<String> optCookie = commandLineArgs.getOptionValues("cookie");
    Assert.assertTrue(null != optCookie);
    Assert.assertTrue(1 == optCookie.size());
    Assert.assertTrue("\"CSRF-TOKEN=token\"".equals(optCookie.get(0)));
    List<String> optH = commandLineArgs.getOptionValues("H");
    Assert.assertTrue(null != optH);
    Assert.assertTrue(2 == optH.size());
    Assert.assertTrue("\"Content-Type:application/json\"".equals(optH.get(0)));
    Assert.assertTrue("\"X-CSRF-TOKEN:token\"".equals(optH.get(1)));
    Assert.assertTrue(null != commandLineArgs.getOptionValues("data-binary"));
    Assert.assertTrue(null != commandLineArgs.getNonOptionArgs());
    Assert.assertTrue("localhost:8080/user/login".equals(commandLineArgs.getNonOptionArgs().get(0)));
  }
}
