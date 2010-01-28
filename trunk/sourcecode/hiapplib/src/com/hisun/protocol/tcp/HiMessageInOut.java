 package com.hisun.protocol.tcp;
 
 import com.hisun.message.HiMessage;
 import com.hisun.util.HiByteBuffer;
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 
 public class HiMessageInOut
 {
   protected int socketBuffer;
   private int preLen;
   private String preLenType;
 
   public HiMessageInOut()
   {
     this.preLenType = "asc"; }
 
   public int getSocketBuffer() {
     return this.socketBuffer;
   }
 
   public void setSocketBuffer(int socketBuffer) {
     this.socketBuffer = socketBuffer;
   }
 
   public String getPreLenType() {
     return this.preLenType;
   }
 
   public void setPreLenType(String type) {
     this.preLenType = type;
   }
 
   public int getPreLen() {
     return this.preLen;
   }
 
   public void setPreLen(int preLen) {
     this.preLen = preLen;
   }
 
   public int read(InputStream in, HiMessage msg) throws IOException {
     byte[] data = read(in);
     msg.setBody(new HiByteBuffer(data));
 
     msg.setHeadItem("ECT", "text/plain");
 
     return data.length;
   }
 
   public void write(OutputStream out, HiMessage msg) throws IOException {
     write(out, (HiByteBuffer)msg.getBody());
   }
 
   public void write(OutputStream out, HiByteBuffer buf) throws IOException {
     byte[] data = buf.getBytes();
     if (this.preLen == 0)
       HiSocketUtil.write(out, data);
     else
       HiSocketUtil.write(out, data, this.preLen, this.preLenType);
   }
 
   public int read(InputStream in, HiByteBuffer buf) throws IOException {
     buf.append(read(in));
     return buf.length();
   }
 
   public byte[] read(InputStream in) throws IOException {
     ByteArrayOutputStream baos = new ByteArrayOutputStream(getSocketBuffer());
     if (this.preLen == 0)
       HiSocketUtil.read(in, baos);
     else
       HiSocketUtil.read(in, baos, this.preLen, this.preLenType);
     return baos.toByteArray();
   }
 }