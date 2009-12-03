/*    */ package com.hisun.protocol.tcp;
/*    */ 
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class HiMessageInOut
/*    */ {
/*    */   protected int socketBuffer;
/*    */   private int preLen;
/*    */   private String preLenType;
/*    */ 
/*    */   public HiMessageInOut()
/*    */   {
/* 14 */     this.preLenType = "asc"; }
/*    */ 
/*    */   public int getSocketBuffer() {
/* 17 */     return this.socketBuffer;
/*    */   }
/*    */ 
/*    */   public void setSocketBuffer(int socketBuffer) {
/* 21 */     this.socketBuffer = socketBuffer;
/*    */   }
/*    */ 
/*    */   public String getPreLenType() {
/* 25 */     return this.preLenType;
/*    */   }
/*    */ 
/*    */   public void setPreLenType(String type) {
/* 29 */     this.preLenType = type;
/*    */   }
/*    */ 
/*    */   public int getPreLen() {
/* 33 */     return this.preLen;
/*    */   }
/*    */ 
/*    */   public void setPreLen(int preLen) {
/* 37 */     this.preLen = preLen;
/*    */   }
/*    */ 
/*    */   public int read(InputStream in, HiMessage msg) throws IOException {
/* 41 */     byte[] data = read(in);
/* 42 */     msg.setBody(new HiByteBuffer(data));
/*    */ 
/* 45 */     msg.setHeadItem("ECT", "text/plain");
/*    */ 
/* 47 */     return data.length;
/*    */   }
/*    */ 
/*    */   public void write(OutputStream out, HiMessage msg) throws IOException {
/* 51 */     write(out, (HiByteBuffer)msg.getBody());
/*    */   }
/*    */ 
/*    */   public void write(OutputStream out, HiByteBuffer buf) throws IOException {
/* 55 */     byte[] data = buf.getBytes();
/* 56 */     if (this.preLen == 0)
/* 57 */       HiSocketUtil.write(out, data);
/*    */     else
/* 59 */       HiSocketUtil.write(out, data, this.preLen, this.preLenType);
/*    */   }
/*    */ 
/*    */   public int read(InputStream in, HiByteBuffer buf) throws IOException {
/* 63 */     buf.append(read(in));
/* 64 */     return buf.length();
/*    */   }
/*    */ 
/*    */   public byte[] read(InputStream in) throws IOException {
/* 68 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(getSocketBuffer());
/* 69 */     if (this.preLen == 0)
/* 70 */       HiSocketUtil.read(in, baos);
/*    */     else
/* 72 */       HiSocketUtil.read(in, baos, this.preLen, this.preLenType);
/* 73 */     return baos.toByteArray();
/*    */   }
/*    */ }