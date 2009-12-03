/*    */ package com.hisun.ccb.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.sw.big5.HiBIG5SwCode;
/*    */ import com.hisun.sw.gbk.HiGBSwCode;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiSwCodeHandler
/*    */   implements IHandler
/*    */ {
/*    */   private String _code;
/*    */ 
/*    */   public void host2client(HiMessageContext ctx)
/*    */   {
/* 22 */     HiMessage msg = ctx.getCurrentMsg();
/* 23 */     HiByteBuffer buf = (HiByteBuffer)msg.getBody();
/* 24 */     buf = host2client(buf);
/* 25 */     msg.setBody(buf);
/*    */   }
/*    */ 
/*    */   public void client2host(HiMessageContext ctx) {
/* 29 */     HiMessage msg = ctx.getCurrentMsg();
/* 30 */     HiByteBuffer buf = (HiByteBuffer)msg.getBody();
/* 31 */     buf = client2host(buf);
/* 32 */     msg.setBody(buf);
/*    */   }
/*    */ 
/*    */   private HiByteBuffer host2client(HiByteBuffer buf)
/*    */   {
/*    */     int len;
/* 36 */     byte[] bs1 = buf.getBytes();
/* 37 */     byte[] bs2 = new byte[bs1.length];
/*    */ 
/* 39 */     if (StringUtils.equalsIgnoreCase(this._code, "GBK"))
/* 40 */       len = HiGBSwCode.HostToClient(bs1, bs2);
/* 41 */     else if (StringUtils.equalsIgnoreCase(this._code, "BIG5"))
/* 42 */       len = HiBIG5SwCode.HostToClient(bs1, bs2);
/*    */     else {
/* 44 */       len = HiBIG5SwCode.HostToClient(bs1, bs2);
/*    */     }
/* 46 */     buf.clear();
/* 47 */     buf.append(bs2, 0, len);
/* 48 */     return buf;
/*    */   }
/*    */ 
/*    */   private HiByteBuffer client2host(HiByteBuffer buf)
/*    */   {
/*    */     int len;
/* 52 */     byte[] bs1 = buf.getBytes();
/* 53 */     byte[] bs2 = new byte[bs1.length];
/* 54 */     for (int i = 0; i < bs2.length; ++i) {
/* 55 */       bs2[i] = 32;
/*    */     }
/*    */ 
/* 58 */     if (StringUtils.equalsIgnoreCase(this._code, "GBK"))
/* 59 */       len = HiGBSwCode.ClientToHost(bs1, bs2);
/* 60 */     else if (StringUtils.equalsIgnoreCase(this._code, "BIG5"))
/* 61 */       len = HiBIG5SwCode.ClientToHost(bs1, bs2);
/*    */     else {
/* 63 */       len = HiBIG5SwCode.ClientToHost(bs1, bs2);
/*    */     }
/* 65 */     buf.clear();
/* 66 */     buf.append(bs2, 0, len);
/* 67 */     return buf;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 71 */     HiMessage msg = ctx.getCurrentMsg();
/* 72 */     String sch = msg.getHeadItem("SCH");
/* 73 */     HiByteBuffer buf = (HiByteBuffer)msg.getBody();
/* 74 */     if (StringUtils.equals(sch, "rq"))
/* 75 */       buf = host2client(buf);
/*    */     else {
/* 77 */       buf = client2host(buf);
/*    */     }
/* 79 */     msg.setBody(buf);
/*    */   }
/*    */ 
/*    */   public String getCode() {
/* 83 */     return this._code;
/*    */   }
/*    */ 
/*    */   public void setCode(String code) {
/* 87 */     this._code = code;
/*    */   }
/*    */ }