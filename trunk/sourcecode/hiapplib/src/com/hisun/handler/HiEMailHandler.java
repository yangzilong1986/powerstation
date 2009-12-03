/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import java.io.File;
/*    */ import java.util.Date;
/*    */ import java.util.Properties;
/*    */ import java.util.StringTokenizer;
/*    */ import javax.activation.DataHandler;
/*    */ import javax.activation.FileDataSource;
/*    */ import javax.mail.Message.RecipientType;
/*    */ import javax.mail.Multipart;
/*    */ import javax.mail.Session;
/*    */ import javax.mail.Transport;
/*    */ import javax.mail.internet.InternetAddress;
/*    */ import javax.mail.internet.MimeBodyPart;
/*    */ import javax.mail.internet.MimeMessage;
/*    */ import javax.mail.internet.MimeMultipart;
/*    */ 
/*    */ public class HiEMailHandler
/*    */   implements IHandler
/*    */ {
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 33 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 34 */     String address = etf.getChildValue("Addr");
/* 35 */     String strTitle = etf.getChildValue("subject");
/* 36 */     String strContent = etf.getChildValue("body");
/* 37 */     String strAttach = etf.getChildValue("file");
/* 38 */     Properties props = new Properties();
/* 39 */     props.setProperty("mail.transport.protocol", "smtp");
/* 40 */     props.setProperty("mail.smtp.host", "smtp.126.com");
/* 41 */     props.setProperty("mail.user", "yu_yun@hisuntech.com");
/* 42 */     props.setProperty("mail.password", "yu_yun");
/* 43 */     props.setProperty("mail.smtp.auth", "true");
/* 44 */     Session session = Session.getInstance(props, null);
/* 45 */     MimeMessage msg = new MimeMessage(session);
/*    */     try
/*    */     {
/* 49 */       msg.setFrom(new InternetAddress(address));
/*    */ 
/* 54 */       msg.setSubject(strTitle);
/* 55 */       msg.setSentDate(new Date());
/*    */ 
/* 57 */       Multipart mp = new MimeMultipart();
/*    */ 
/* 60 */       MimeBodyPart mbpText = new MimeBodyPart();
/* 61 */       mbpText.setText(strContent);
/*    */ 
/* 63 */       mp.addBodyPart(mbpText);
/*    */ 
/* 65 */       if ((strAttach != null) && (!(strAttach.equals(""))))
/*    */       {
/* 68 */         StringTokenizer token = new StringTokenizer(strAttach, ",");
/* 69 */         while (token.hasMoreTokens())
/*    */         {
/*    */           try
/*    */           {
/* 73 */             String strFile = token.nextToken();
/* 74 */             MimeBodyPart mbpAttach = new MimeBodyPart();
/* 75 */             FileDataSource fds = new FileDataSource(HiICSProperty.getWorkDir() + File.separator + strFile);
/*    */ 
/* 79 */             mbpAttach.setDataHandler(new DataHandler(fds));
/* 80 */             mbpAttach.setFileName(fds.getName());
/*    */ 
/* 82 */             mp.addBodyPart(mbpAttach);
/*    */           }
/*    */           catch (Exception e)
/*    */           {
/* 86 */             e.printStackTrace();
/*    */           }
/*    */         }
/*    */       }
/*    */ 
/* 91 */       msg.setContent(mp);
/*    */ 
/* 93 */       Transport transport = session.getTransport(props.getProperty("mail.transport.protocol"));
/*    */ 
/* 95 */       transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.user"), props.getProperty("mail.password"));
/*    */ 
/* 98 */       transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 103 */       throw new HiException("", "email is fail", e);
/*    */     }
/*    */   }
/*    */ }