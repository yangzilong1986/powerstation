/*    */ package com.hisun.component.mail;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.common.HiETF2HashMapList;
/*    */ import com.hisun.data.cache.HiDataCache;
/*    */ import com.hisun.data.cache.HiDataCacheConfig;
/*    */ import com.hisun.data.object.HiEmailInfo;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.template.TemplateEngine;
/*    */ import com.hisun.template.TemplateFactory;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.commons.mail.HtmlEmail;
/*    */ 
/*    */ public class SendMail
/*    */ {
/*    */   private TemplateEngine freemarkerEngine;
/*    */   private HiDataCacheConfig dataCacheConfig;
/*    */   private int idx;
/*    */ 
/*    */   public SendMail()
/*    */   {
/* 30 */     this.freemarkerEngine = ((TemplateEngine)TemplateFactory.getInstance().getBean("freemarker"));
/* 31 */     this.dataCacheConfig = HiDataCacheConfig.getInstance();
/* 32 */     this.idx = 0;
/*    */   }
/*    */ 
/*    */   public int execute(HiATLParam args, HiMessageContext ctx) throws HiException, Exception {
/* 36 */     HiMessage msg = ctx.getCurrentMsg();
/* 37 */     Logger log = HiLog.getLogger(msg);
/* 38 */     HtmlEmail email = new HtmlEmail();
/* 39 */     HiEmailInfo emailInfo = getEmailInfo();
/* 40 */     log.info(emailInfo);
/* 41 */     email.setHostName(emailInfo.getHost());
/* 42 */     email.setSmtpPort(emailInfo.getPort());
/* 43 */     email.setAuthentication(emailInfo.getUser(), emailInfo.getPasswd());
/* 44 */     email.setFrom(emailInfo.getFrom(), emailInfo.getName());
/*    */ 
/* 46 */     String reciever = HiArgUtils.getStringNotNull(args, "receiver");
/*    */ 
/* 48 */     String subject = HiArgUtils.getStringNotNull(args, "subject");
/*    */ 
/* 50 */     String template = HiArgUtils.getStringNotNull(args, "template");
/*    */ 
/* 52 */     email.setCharset("GBK");
/* 53 */     email.addTo(reciever);
/* 54 */     email.setSubject(subject);
/*    */ 
/* 56 */     HiETF2HashMapList etf2HashMap = new HiETF2HashMapList(msg.getETFBody());
/* 57 */     StringBuffer result = this.freemarkerEngine.run(etf2HashMap.map(), template);
/* 58 */     email.setHtmlMsg(result.toString());
/* 59 */     email.setTextMsg("Your email client does not support HTML messages");
/* 60 */     email.send();
/* 61 */     return 0;
/*    */   }
/*    */ 
/*    */   private HiEmailInfo getEmailInfo() {
/* 65 */     ArrayList emailInfos = this.dataCacheConfig.getDataCache("EMAIL_INFOS").getDataList();
/* 66 */     if (this.idx >= emailInfos.size())
/* 67 */       this.idx = 0;
/* 68 */     HiEmailInfo emailInfo = (HiEmailInfo)emailInfos.get(this.idx);
/* 69 */     this.idx += 1;
/* 70 */     return emailInfo;
/*    */   }
/*    */ }