/*    */ package com.hisun.component.web;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class ValImgCaptcha
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 25 */     HiMessage msg = ctx.getCurrentMsg();
/* 26 */     HttpSession session = (HttpSession)msg.getObjectHeadItem("_WEB_SESSION");
/* 27 */     String chkCod = HiArgUtils.getStringNotNull(args, "ChkCod");
/* 28 */     String value = (String)session.getAttribute("__IMAGE_CAPTCHA");
/* 29 */     session.removeAttribute("__IMAGE_CAPTCHA");
/*    */ 
/* 31 */     Logger log = HiLog.getLogger(msg);
/* 32 */     log.info("[" + value + "]:[" + chkCod + "]");
/* 33 */     if (!(StringUtils.equalsIgnoreCase(value, chkCod))) {
/* 34 */       return 1;
/*    */     }
/* 36 */     return 0;
/*    */   }
/*    */ }