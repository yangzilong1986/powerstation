/*    */ package com.hisun.component.web;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class HtmlSubStr
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 22 */     int offset = NumberUtils.toInt(args.get("off"));
/* 23 */     int length = NumberUtils.toInt(args.get("len"));
/* 24 */     String content = args.get("Content");
/* 25 */     String dstName = HiArgUtils.getStringNotNull(args, "DstNam");
/* 26 */     HiMessage msg = ctx.getCurrentMsg();
/* 27 */     HiETF root = msg.getETFBody();
/* 28 */     Logger log = HiLog.getLogger(msg);
/* 29 */     if (StringUtils.isEmpty(content)) {
/* 30 */       return 0;
/*    */     }
/* 32 */     content = HtmlFilter.htmlChanger(content, "");
/*    */ 
/* 34 */     if (offset < 1) {
/* 35 */       offset = 1;
/*    */     }
/*    */ 
/* 38 */     if (offset >= content.length()) {
/* 39 */       return 0;
/*    */     }
/*    */ 
/* 42 */     if ((length <= 0) && (length >= content.length())) {
/* 43 */       length = content.length();
/*    */     }
/*    */ 
/* 46 */     if (offset + length >= content.length()) {
/* 47 */       length = content.length() - offset;
/*    */     }
/* 49 */     root.setChildValue(dstName, content.substring(offset - 1, offset - 1 + length));
/*    */ 
/* 51 */     return 0;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 55 */     String content = "adfasfdasdf";
/* 56 */     int offset = 1;
/* 57 */     int length = content.length();
/* 58 */     System.out.println(content.substring(3, 8));
/*    */   }
/*    */ }