/*    */ package com.hisun.atmp.tcp;
/*    */ 
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.protocol.tcp.HiMessageInOut;
/*    */ import com.hisun.protocol.tcp.HiSocketUtil;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.apache.commons.codec.binary.Hex;
/*    */ 
/*    */ public class HiPOSMessageInOut extends HiMessageInOut
/*    */ {
/*    */   private Logger log;
/*    */ 
/*    */   public void setLog(Logger log)
/*    */   {
/* 21 */     this.log = log;
/*    */   }
/*    */ 
/*    */   public HiPOSMessageInOut(Logger log) {
/* 25 */     this.log = log;
/*    */   }
/*    */ 
/*    */   public HiPOSMessageInOut() {
/*    */   }
/*    */ 
/*    */   public int read(InputStream in, HiMessage msg) throws IOException {
/* 32 */     ByteArrayOutputStream buf = new ByteArrayOutputStream(getSocketBuffer());
/*    */ 
/* 42 */     if (getPreLen() == 0)
/* 43 */       HiSocketUtil.read(in, buf);
/*    */     else
/* 45 */       HiSocketUtil.read(in, buf, getPreLen(), getPreLenType());
/* 46 */     if (buf.size() == 0) {
/* 47 */       return 0;
/*    */     }
/* 49 */     byte[] data = buf.toByteArray();
/* 50 */     byte[] tudu = new byte[4];
/*    */ 
/* 53 */     System.arraycopy(data, 1, tudu, 0, 4);
/* 54 */     msg.setHeadItem("POS_TPDU", tudu);
/* 55 */     byte[] d8583 = new byte[data.length - 5];
/* 56 */     System.arraycopy(data, 5, d8583, 0, data.length - 5);
/* 57 */     msg.setBody(new HiByteBuffer(d8583));
/*    */ 
/* 59 */     byte[] data1 = getMACData(d8583);
/* 60 */     msg.setHeadItem("MACDATA", new String(Hex.encodeHex(data1)));
/* 61 */     msg.setHeadItem("DATALEN", String.valueOf(data1.length));
/*    */ 
/* 63 */     msg.setHeadItem("ECT", "text/plain");
/*    */ 
/* 65 */     return data.length;
/*    */   }
/*    */ 
/*    */   private byte[] getMACData(byte[] d8583)
/*    */   {
/* 72 */     int len = d8583.length - 8;
/* 73 */     len = (len > 512) ? 512 : len;
/* 74 */     byte[] ret = new byte[len];
/* 75 */     System.arraycopy(d8583, 0, ret, 0, len);
/* 76 */     return ret;
/*    */   }
/*    */ 
/*    */   public void write(OutputStream out, HiMessage msg) throws IOException {
/* 80 */     byte[] data = ((HiByteBuffer)msg.getBody()).getBytes();
/* 81 */     byte[] buf = new byte[data.length + 5];
/* 82 */     byte[] tpdu = (byte[])(byte[])msg.getObjectHeadItem("POS_TPDU");
/* 83 */     buf[0] = 96;
/* 84 */     buf[1] = tpdu[2];
/* 85 */     buf[2] = tpdu[3];
/* 86 */     buf[3] = tpdu[0];
/* 87 */     buf[4] = tpdu[1];
/* 88 */     System.arraycopy(data, 0, buf, 5, data.length);
/* 89 */     if (getPreLen() == 0)
/* 90 */       HiSocketUtil.write(out, buf);
/*    */     else
/* 92 */       HiSocketUtil.write(out, buf, getPreLen(), getPreLenType());
/*    */   }
/*    */ }