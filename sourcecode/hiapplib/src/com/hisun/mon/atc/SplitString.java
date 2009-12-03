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
/*    */ public class SplitString
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 17 */     String list = HiArgUtils.getStringNotNull(args, "list");
/* 18 */     String sepr = args.get("sepr");
/* 19 */     if (StringUtils.isEmpty(sepr)) {
/* 20 */       sepr = "|";
/*    */     }
/* 22 */     String grpNm = args.get("group");
/* 23 */     if (StringUtils.isEmpty(grpNm)) {
/* 24 */       grpNm = "GROUP";
/*    */     }
/* 26 */     String[] strArr = StringUtils.split(list, sepr);
/* 27 */     HiETF body = ctx.getCurrentMsg().getETFBody();
/* 28 */     int i = 0;
/* 29 */     while (i < strArr.length)
/*    */     {
/* 31 */       body.setGrandChildNode(grpNm + "_" + (i + 1) + ".data", strArr[i]);
/* 32 */       ++i;
/*    */     }
/* 34 */     body.setGrandChildNode(grpNm + "_NUM", String.valueOf(strArr.length));
/* 35 */     return 0;
/*    */   }
/*    */ }