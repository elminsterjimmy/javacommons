package com.elminster.common.util.test;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.elminster.common.util.MailUtil;

public class MailUtilTest {

  @Ignore
  @Test
  public void testSendMail() throws AddressException, MessagingException {
    String host = "smtp.163.com";
    int port = 25;
    String user = "slayerdragon";
    String password = "1921419214bb";
    boolean isAutheticate = true;
    String from = "slayerdragon@163.com";
    String to = "slayerdragon@163.com,elminster.jimmy@gmail.com";
    String cc = "slayerdragon@163.com";
    String bcc = "slayerdragon@163.com";
    String subject = "test测试";
    String content = "test测试<a href=\"http://www.sina.com\">www.sina.com</a>";
    Map<String, String> filePathMap = new HashMap<String, String>();
    filePathMap.put("test1txt.txt", "C:\\temp\\test1.txt");
    filePathMap.put("test2txt.txt", "C:\\temp\\test2.txt");
    filePathMap.put("test3txt.txt", "C:\\temp\\test3.txt");
    Assert.assertEquals(true, MailUtil.sendMail(host, port, user, password, isAutheticate, from, to, cc, bcc, subject, content, filePathMap));
  }
}
