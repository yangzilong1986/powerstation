 package com.hisun.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiICSProperty;
 import java.io.File;
 import java.util.Date;
 import java.util.Properties;
 import java.util.StringTokenizer;
 import javax.activation.DataHandler;
 import javax.activation.FileDataSource;
 import javax.mail.Message.RecipientType;
 import javax.mail.Multipart;
 import javax.mail.Session;
 import javax.mail.Transport;
 import javax.mail.internet.InternetAddress;
 import javax.mail.internet.MimeBodyPart;
 import javax.mail.internet.MimeMessage;
 import javax.mail.internet.MimeMultipart;
 
 public class HiEMailHandler
   implements IHandler
 {
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     String address = etf.getChildValue("Addr");
     String strTitle = etf.getChildValue("subject");
     String strContent = etf.getChildValue("body");
     String strAttach = etf.getChildValue("file");
     Properties props = new Properties();
     props.setProperty("mail.transport.protocol", "smtp");
     props.setProperty("mail.smtp.host", "smtp.126.com");
     props.setProperty("mail.user", "yu_yun@hisuntech.com");
     props.setProperty("mail.password", "yu_yun");
     props.setProperty("mail.smtp.auth", "true");
     Session session = Session.getInstance(props, null);
     MimeMessage msg = new MimeMessage(session);
     try
     {
       msg.setFrom(new InternetAddress(address));
 
       msg.setSubject(strTitle);
       msg.setSentDate(new Date());
 
       Multipart mp = new MimeMultipart();
 
       MimeBodyPart mbpText = new MimeBodyPart();
       mbpText.setText(strContent);
 
       mp.addBodyPart(mbpText);
 
       if ((strAttach != null) && (!(strAttach.equals(""))))
       {
         StringTokenizer token = new StringTokenizer(strAttach, ",");
         while (token.hasMoreTokens())
         {
           try
           {
             String strFile = token.nextToken();
             MimeBodyPart mbpAttach = new MimeBodyPart();
             FileDataSource fds = new FileDataSource(HiICSProperty.getWorkDir() + File.separator + strFile);
 
             mbpAttach.setDataHandler(new DataHandler(fds));
             mbpAttach.setFileName(fds.getName());
 
             mp.addBodyPart(mbpAttach);
           }
           catch (Exception e)
           {
             e.printStackTrace();
           }
         }
       }
 
       msg.setContent(mp);
 
       Transport transport = session.getTransport(props.getProperty("mail.transport.protocol"));
 
       transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.user"), props.getProperty("mail.password"));
 
       transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
     }
     catch (Exception e)
     {
       throw new HiException("", "email is fail", e);
     }
   }
 }