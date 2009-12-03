/*    */ package com.hisun.mon.atc;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class SetETFOutList
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 28 */     HiMessage msg = ctx.getCurrentMsg();
/* 29 */     Logger log = HiLog.getLogger(msg);
/*    */ 
/* 31 */     if (log.isDebugEnabled()) {
/* 32 */       log.debug("SetETFOutList start");
/*    */     }
/*    */ 
/* 35 */     String list = HiArgUtils.getStringNotNull(args, "list");
/* 36 */     String[] nodes = StringUtils.split(list, "|");
/*    */ 
/* 38 */     HiETF etf = msg.getETFBody();
/* 39 */     HiETF newEtf = HiETFFactory.createETF();
/* 40 */     for (int i = 0; i < nodes.length; ++i) {
/* 41 */       newEtf.setGrandChildNode(nodes[i], etf.getGrandChildValue(nodes[i]));
/*    */     }
/*    */ 
/* 44 */     msg.setHeadItem("ETFOUTLIST", newEtf);
/* 45 */     if (log.isDebugEnabled()) {
/* 46 */       log.debug("SetETFOutList end");
/*    */     }
/* 48 */     return 0;
/*    */   }
/*    */ }