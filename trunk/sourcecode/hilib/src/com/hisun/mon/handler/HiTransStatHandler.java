/*    */ package com.hisun.mon.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.mon.HiRunStatInfoPool;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import java.util.HashMap;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiTransStatHandler
/*    */   implements IHandler
/*    */ {
/*    */   private Logger log;
/*    */   private String[] fields;
/*    */ 
/*    */   public HiTransStatHandler()
/*    */   {
/* 27 */     this.log = null;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext arg0) throws HiException {
/* 31 */     HashMap map = new HashMap();
/* 32 */     HiMessage msg = arg0.getCurrentMsg();
/* 33 */     HiETF root = msg.getETFBody();
/* 34 */     for (int i = 0; (this.fields != null) && (i < this.fields.length); ++i) {
/* 35 */       String tmp = root.getGrandChildValue(this.fields[i]);
/* 36 */       if (StringUtils.isBlank(tmp)) {
/*    */         continue;
/*    */       }
/* 39 */       map.put(this.fields[i], tmp);
/*    */     }
/* 41 */     long stm = ((Long)msg.getObjectHeadItem("STM")).longValue();
/* 42 */     int elapseTime = (int)(System.currentTimeMillis() - stm);
/* 43 */     String msgTyp = root.getChildValue("MSG_TYP");
/* 44 */     String rspCd = root.getChildValue("RSP_CD");
/* 45 */     String rspMsg = root.getChildValue("RSP_MSG");
/* 46 */     String code = msg.getHeadItem("STC");
/*    */ 
/* 48 */     HiRunStatInfoPool.getInstance().once(msg.getRequestId(), code, elapseTime, System.currentTimeMillis(), msgTyp, rspCd, rspMsg, map);
/*    */   }
/*    */ 
/*    */   public void setFields(String fields)
/*    */   {
/* 53 */     this.fields = fields.split("\\|");
/*    */   }
/*    */ 
/*    */   public String getFields() {
/* 57 */     return "";
/*    */   }
/*    */ }