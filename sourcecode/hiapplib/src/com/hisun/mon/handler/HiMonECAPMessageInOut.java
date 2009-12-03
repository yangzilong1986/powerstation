/*    */ package com.hisun.mon.handler;
/*    */ 
/*    */ import com.hisun.protocol.tcp.HiMessageInOut;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class HiMonECAPMessageInOut extends HiMessageInOut
/*    */ {
/*    */   public int read(InputStream in, HiByteBuffer byteBuffer)
/*    */     throws IOException
/*    */   {
/* 15 */     int preLen = getPreLen();
/*    */ 
/* 17 */     if (preLen == 0) {
/* 18 */       byteBuffer.append(super.read(in));
/* 19 */       return byteBuffer.length();
/*    */     }
/* 21 */     byte[] tmp = new byte[preLen];
/*    */ 
/* 23 */     int len = in.read(tmp);
/* 24 */     if (len != tmp.length) {
/* 25 */       return 0;
/*    */     }
/*    */ 
/* 28 */     len = NumberUtils.toInt(new String(tmp, 0, preLen).trim());
/*    */ 
/* 30 */     byte[] buf = new byte[1024];
/* 31 */     int count = 0;
/* 32 */     while (count < len) {
/* 33 */       int readlen = in.read(buf, 0, Math.min(len - count, 1024));
/* 34 */       if (readlen == -1)
/*    */         break;
/* 36 */       byteBuffer.append(buf, 0, readlen);
/* 37 */       count += readlen;
/*    */     }
/*    */ 
/* 40 */     if (count != len) {
/* 41 */       throw new IOException("data is not receive completed:(" + len + "," + 
/* 42 */         count + ")");
/*    */     }
/* 44 */     return byteBuffer.length();
/*    */   }
/*    */ }