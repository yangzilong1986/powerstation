/*    */ package com.hisun.mon.atc;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class ReplaceWith
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 30 */     String template = HiArgUtils.getStringNotNull(args, "template");
/*    */ 
/* 32 */     String repl = args.get("repl");
/* 33 */     if (StringUtils.isEmpty(repl)) {
/* 34 */       repl = "#";
/*    */     }
/*    */ 
/* 37 */     String objNam = HiArgUtils.getStringNotNull(args, "objNam");
/*    */ 
/* 40 */     String text = HiArgUtils.getStringNotNull(args, "text");
/* 41 */     String separator = args.get("sepr");
/* 42 */     if (StringUtils.isEmpty(separator)) {
/* 43 */       separator = "|";
/*    */     }
/* 45 */     String[] textArr = StringUtils.splitPreserveAllTokens(text, separator);
/*    */ 
/* 47 */     for (int i = 0; i < textArr.length; ++i) {
/* 48 */       template = StringUtils.replaceOnce(template, repl, textArr[i]);
/*    */     }
/*    */ 
/* 51 */     ctx.getCurrentMsg().getETFBody().setGrandChildNode(objNam, template);
/*    */ 
/* 53 */     return 0;
/*    */   }
/*    */ }