package com.elminster.common.util;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility class for sending, receiving mail.
 * 
 * @author Gu
 * @version 1.0
 * 
 */
abstract public class MailUtil {
  
  /** Default send mail protocol. */
  private static final String DEFAULT_SEND_PROTOCOL = "smtp";
  
  /** The logger. */
  private static Log logger = LogFactory.getLog(MailUtil.class);

  /**
   * Send mail.
   * 
   * @param smtpHost SMTP host
   * @param smtpPort SMTP port
   * @param user login user name
   * @param password login password
   * @param isAutheticate autheticate flag
   * @param from mail from
   * @param to mail to
   * @param cc mail cc
   * @param bcc mail bcc
   * @param subject subject of mail
   * @param content content of mail
   * @param filePathMap attachment file path map
   * @return send mail success or not
   * @throws AddressException
   * @throws MessagingException
   */
  public static boolean sendMail(String smtpHost,
                          int smtpPort,
                          String user,
                          String password,
                          boolean isAutheticate,
                          String from,
                          String to,
                          String cc,
                          String bcc,
                          String subject,
                          String content,
                          Map<String, String> filePathMap) throws AddressException, MessagingException {
    
    boolean rst = false;
    Properties p = new Properties();
    p.put("mail.smtp.auth", isAutheticate ? "true" : "false");
    p.put("mail.transport.protocol", DEFAULT_SEND_PROTOCOL);
    p.put("mail.smtp.host", smtpHost);
    p.put("mail.smtp.port", smtpPort);
    Session session = Session.getInstance(p);
    Message msg = new MimeMessage(session);
    // From
    msg.setFrom(new InternetAddress(from));
    // TO
    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
    msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
    msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
    // sent date
    msg.setSentDate(DateUtil.getCurrentDate());
    // subject
    msg.setSubject(subject);
    
    // content
    MimeBodyPart mbp = new MimeBodyPart();
    mbp.setDataHandler(new DataHandler(content, "text/html;charset=UTF-8"));
    
    Multipart mulp = new MimeMultipart();
    mulp.addBodyPart(mbp);
    // attach
    String fileName;
    String fileFullPath;
    DataSource source;
    if (CollectionUtil.isNotEmpty(filePathMap)) {
      Iterator<Map.Entry<String, String>> it = filePathMap.entrySet().iterator();
      while (it.hasNext()) {
        mbp = new MimeBodyPart();
        Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
        fileName = entry.getKey();
        fileFullPath = entry.getValue();
        if (StringUtil.isEmpty(fileFullPath)) {
          continue;
        }
        File f = new File(fileFullPath);
        if (!f.exists()) {
          logger.warn("Attach " + fileFullPath + " doesn't exist.");
          continue;
        }
        source = new FileDataSource(fileFullPath);
        mbp.setDataHandler(new DataHandler(source));
        mbp.setFileName(fileName);
        mulp.addBodyPart(mbp);
      }
    }
    msg.setContent(mulp);
    // log in to server
    Transport tran = session.getTransport(DEFAULT_SEND_PROTOCOL);
    tran.connect(smtpHost, user, password);

    tran.sendMessage(msg, msg.getAllRecipients());
    rst = true;
    return rst;
  }
}
