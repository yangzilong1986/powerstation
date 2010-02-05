 package com.hisun.mon.handler;
 
 import com.hisun.protocol.tcp.HiMessageInOut;
 import com.hisun.util.HiByteBuffer;
 import java.io.IOException;
 import java.io.InputStream;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiMonECAPMessageInOut extends HiMessageInOut
 {
   public int read(InputStream in, HiByteBuffer byteBuffer)
     throws IOException
   {
     int preLen = getPreLen();
 
     if (preLen == 0) {
       byteBuffer.append(super.read(in));
       return byteBuffer.length();
     }
     byte[] tmp = new byte[preLen];
 
     int len = in.read(tmp);
     if (len != tmp.length) {
       return 0;
     }
 
     len = NumberUtils.toInt(new String(tmp, 0, preLen).trim());
 
     byte[] buf = new byte[1024];
     int count = 0;
     while (count < len) {
       int readlen = in.read(buf, 0, Math.min(len - count, 1024));
       if (readlen == -1)
         break;
       byteBuffer.append(buf, 0, readlen);
       count += readlen;
     }
 
     if (count != len) {
       throw new IOException("data is not receive completed:(" + len + "," + 
         count + ")");
     }
     return byteBuffer.length();
   }
 }