/*    */ package com.hisun.component.mail;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.data.cache.HiDataCache;
/*    */ import com.hisun.data.cache.HiDataCacheConfig;
/*    */ import com.hisun.data.object.HiEmailInfo;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.commons.mail.HtmlEmail;
/*    */ 
/*    */ public class SendMailByMsg
/*    */ {
/*    */   private HiDataCacheConfig dataCacheConfig;
/*    */   private int idx;
/*    */ 
/*    */   public SendMailByMsg()
/*    */   {
/* 26 */     this.dataCacheConfig = HiDataCacheConfig.getInstance();
/* 27 */     this.idx = 0;
/*    */   }
/*    */ 
/*    */   public int execute(HiATLParam args, HiMessageContext ctx) throws HiException, Exception {
/* 31 */     HiMessage msg = ctx.getCurrentMsg();
/* 32 */     Logger log = HiLog.getLogger(msg);
/* 33 */     HtmlEmail email = new HtmlEmail();
/* 34 */     HiEmailInfo emailInfo = getEmailInfo();
/* 35 */     log.info(emailInfo);
/* 36 */     email.setHostName(emailInfo.getHost());
/* 37 */     email.setSmtpPort(emailInfo.getPort());
/* 38 */     email.setAuthentication(emailInfo.getUser(), emailInfo.getPasswd());
/* 39 */     email.setFrom(emailInfo.getFrom(), emailInfo.getName());
/*    */ 
/* 41 */     String reciever = HiArgUtils.getStringNotNull(args, "receiver");
/*    */ 
/* 43 */     String subject = HiArgUtils.getStringNotNull(args, "subject");
/*    */ 
/* 45 */     String content = HiArgUtils.getStringNotNull(args, "content");
/*    */ 
/* 47 */     email.setCharset("GBK");
/* 48 */     email.addTo(reciever);
/* 49 */     email.setSubject(subject);
/*    */ 
/* 51 */     email.setHtmlMsg(content);
/* 52 */     email.setTextMsg("Your email client does not support HTML messages");
/*    */ 
/* 54 */     email.send();
/*    */ 
/* 56 */     return 0;
/*    */   }
/*    */ 
/*    */   private HiEmailInfo getEmailInfo() {
/* 60 */     ArrayList emailInfos = this.dataCacheConfig.getDataCache("EMAIL_INFOS").getDataList();
/* 61 */     if (this.idx >= emailInfos.size())
/* 62 */       this.idx = 0;
/* 63 */     HiEmailInfo emailInfo = (HiEmailInfo)emailInfos.get(this.idx);
/* 64 */     this.idx += 1;
/* 65 */     return emailInfo;
/*    */   }
/*    */ }