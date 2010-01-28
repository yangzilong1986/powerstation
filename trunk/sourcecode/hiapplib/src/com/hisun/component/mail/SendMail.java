 package com.hisun.component.mail;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.common.HiETF2HashMapList;
 import com.hisun.data.cache.HiDataCache;
 import com.hisun.data.cache.HiDataCacheConfig;
 import com.hisun.data.object.HiEmailInfo;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.template.TemplateEngine;
 import com.hisun.template.TemplateFactory;
 import java.util.ArrayList;
 import org.apache.commons.mail.HtmlEmail;
 
 public class SendMail
 {
   private TemplateEngine freemarkerEngine;
   private HiDataCacheConfig dataCacheConfig;
   private int idx;
 
   public SendMail()
   {
     this.freemarkerEngine = ((TemplateEngine)TemplateFactory.getInstance().getBean("freemarker"));
     this.dataCacheConfig = HiDataCacheConfig.getInstance();
     this.idx = 0;
   }
 
   public int execute(HiATLParam args, HiMessageContext ctx) throws HiException, Exception {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     HtmlEmail email = new HtmlEmail();
     HiEmailInfo emailInfo = getEmailInfo();
     log.info(emailInfo);
     email.setHostName(emailInfo.getHost());
     email.setSmtpPort(emailInfo.getPort());
     email.setAuthentication(emailInfo.getUser(), emailInfo.getPasswd());
     email.setFrom(emailInfo.getFrom(), emailInfo.getName());
 
     String reciever = HiArgUtils.getStringNotNull(args, "receiver");
 
     String subject = HiArgUtils.getStringNotNull(args, "subject");
 
     String template = HiArgUtils.getStringNotNull(args, "template");
 
     email.setCharset("GBK");
     email.addTo(reciever);
     email.setSubject(subject);
 
     HiETF2HashMapList etf2HashMap = new HiETF2HashMapList(msg.getETFBody());
     StringBuffer result = this.freemarkerEngine.run(etf2HashMap.map(), template);
     email.setHtmlMsg(result.toString());
     email.setTextMsg("Your email client does not support HTML messages");
     email.send();
     return 0;
   }
 
   private HiEmailInfo getEmailInfo() {
     ArrayList emailInfos = this.dataCacheConfig.getDataCache("EMAIL_INFOS").getDataList();
     if (this.idx >= emailInfos.size())
       this.idx = 0;
     HiEmailInfo emailInfo = (HiEmailInfo)emailInfos.get(this.idx);
     this.idx += 1;
     return emailInfo;
   }
 }