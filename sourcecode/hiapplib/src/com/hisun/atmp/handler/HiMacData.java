/*    */ package com.hisun.atmp.handler;
/*    */ 
/*    */ import com.hisun.atmp.crypt.Shuffle;
/*    */ import com.hisun.dispatcher.HiRouterOut;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import org.apache.commons.codec.binary.Hex;
/*    */ 
/*    */ public class HiMacData
/*    */   implements IHandler
/*    */ {
/*    */   private String key;
/*    */ 
/*    */   public HiMacData()
/*    */   {
/* 21 */     this.key = "POS_EXTRA"; }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 24 */     HiMessage msg = ctx.getCurrentMsg();
/* 25 */     byte[] data = ((HiByteBuffer)msg.getBody()).getBytes();
/*    */ 
/* 27 */     HiETF root = (HiETF)ctx.getProperty(this.key);
/*    */ 
/* 29 */     byte[] d = getMACDate(data);
/*    */ 
/* 31 */     root.setChildValue("DatVal", new String(Hex.encodeHex(d)));
/*    */ 
/* 34 */     ctx.setProperty(this.key, msg.getBody());
/* 35 */     msg.setBody(root);
/* 36 */     msg.setHeadItem("SDT", "STHDSSB1");
/* 37 */     msg.setHeadItem("STC", "310002");
/* 38 */     msg.setHeadItem("ECT", "text/etf");
/* 39 */     Logger log = HiLog.getLogger(msg);
/*    */ 
/* 41 */     msg.setHeadItem("SCH", "rq");
/*    */ 
/* 43 */     if (log.isInfoEnabled()) {
/* 44 */       log.info("before genMac:" + msg.toString());
/*    */     }
/*    */ 
/* 47 */     HiRouterOut.process(ctx);
/* 48 */     if (log.isInfoEnabled()) {
/* 49 */       log.info("after genMac:" + ctx.getCurrentMsg().toString());
/*    */     }
/* 51 */     process2(ctx);
/*    */   }
/*    */ 
/*    */   public void process2(HiMessageContext arg0) throws HiException {
/* 55 */     HiMessage msg = arg0.getCurrentMsg();
/* 56 */     HiETF root = msg.getETFBody();
/*    */ 
/* 58 */     HiByteBuffer buf = (HiByteBuffer)arg0.getProperty(this.key);
/* 59 */     byte[] outbuf = buf.getBytes();
/*    */ 
/* 61 */     String mac = root.getChildValue("MacVal");
/*    */ 
/* 64 */     if (mac != null) {
/* 65 */       char[] pmac = new char[8];
/* 66 */       Shuffle.strToBCD(pmac, mac.toCharArray(), 8);
/* 67 */       byte[] bytes = chars2bytes(pmac);
/*    */ 
/* 69 */       System.arraycopy(bytes, 0, outbuf, outbuf.length - 8, 8);
/*    */     }
/* 71 */     msg.setBody(new HiByteBuffer(outbuf));
/*    */   }
/*    */ 
/*    */   private byte[] getMACDate(byte[] d8583)
/*    */   {
/* 78 */     int len = d8583.length - 8;
/* 79 */     len = (len > 512) ? 512 : len;
/* 80 */     byte[] ret = new byte[len];
/* 81 */     System.arraycopy(d8583, 0, ret, 0, len);
/* 82 */     return ret;
/*    */   }
/*    */ 
/*    */   private static byte[] chars2bytes(char[] key) {
/* 86 */     byte[] b_key = new byte[key.length];
/* 87 */     for (int i = 0; i < key.length; ++i)
/* 88 */       b_key[i] = (byte)key[i];
/* 89 */     return b_key;
/*    */   }
/*    */ 
/*    */   public String getKey() {
/* 93 */     return this.key;
/*    */   }
/*    */ 
/*    */   public void setKey(String key) {
/* 97 */     this.key = key;
/*    */   }
/*    */ }