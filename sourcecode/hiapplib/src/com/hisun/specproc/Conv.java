 package com.hisun.specproc;
 
 import com.hisun.common.util.HiByteUtil;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiConvHelper;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class Conv
 {
   private static Logger log = HiLog.getLogger("conv.trc");
 
   public HiByteBuffer genUnionMac(HiByteBuffer buf, HiMessageContext ctx)
   {
     HiByteBuffer buf1 = new HiByteBuffer(buf.length());
     boolean isBlank = false;
     int first = 0; int tail = buf.length();
 
     for (first = 0; first < buf.length(); ++first) {
       if (buf.charAt(first) != 32) {
         break;
       }
     }
 
     for (tail = buf.length() - 1; tail >= first; --tail) {
       if (buf.charAt(tail) != 32) {
         ++tail;
         break;
       }
 
     }
 
     for (int i = first; i < tail; ++i) {
       byte b = buf.charAt(i);
       if ((b != 44) && (b != 46) && (!(Character.isLetterOrDigit(b))) && (b != 32))
         continue;
       if (b != 32) {
         isBlank = false;
       }
       if (!(isBlank)) {
         buf1.append(Character.toUpperCase(b));
       }
       if (b == 32) {
         isBlank = true;
       }
     }
 
     return buf1;
   }
 
   public HiByteBuffer hex2str(HiByteBuffer bb, HiMessageContext mc) {
     if (bb.length() == 0)
       return bb;
     byte[] bs = HiConvHelper.ascStr2Bcd(bb.toString());
     return new HiByteBuffer(bs);
   }
 
   public HiByteBuffer str2hex(HiByteBuffer bb, HiMessageContext mc) {
     if (bb.length() == 0)
       return bb;
     HiByteBuffer buf = new HiByteBuffer(bb.length());
     for (int i = 0; i < bb.length(); ++i) {
       byte b = bb.charAt(i);
       buf.append(bin2char((b & 0xF0) >> 4));
       buf.append(bin2char(b & 0xF));
     }
     return buf;
   }
 
   private static char bin2char(int bin) {
     return (char)((bin < 10) ? bin + 48 : bin - 10 + 65);
   }
 
   public HiByteBuffer Str2Short(HiByteBuffer bb, HiMessageContext mc)
   {
     try {
       if (bb.length() == 0) {
         return bb;
       }
       int i = NumberUtils.toInt(bb.toString());
       byte[] b = HiByteUtil.shortToByteArray(i);
       if (log.isInfoEnabled()) {
         log.info("Str2Short:[" + String.valueOf(i) + "]", b);
       }
       return new HiByteBuffer(b);
     } catch (RuntimeException e) {
       Logger log = HiLog.getLogger(mc.getCurrentMsg());
       log.error(e);
       throw e;
     }
   }
 
   public HiByteBuffer Short2Str(HiByteBuffer bb, HiMessageContext mc)
   {
     try {
       if (bb.length() == 0)
         return bb;
       int i = HiByteUtil.byteArrayToShort(bb.getBytes());
       if (log.isInfoEnabled()) {
         log.info("Int2Str:[" + String.valueOf(i) + "]");
       }
       return new HiByteBuffer(String.valueOf(i).getBytes());
     } catch (RuntimeException e) {
       Logger log = HiLog.getLogger(mc.getCurrentMsg());
       log.error(e);
       throw e;
     }
   }
 
   public HiByteBuffer Int2Str(HiByteBuffer bb, HiMessageContext mc)
   {
     try
     {
       if (bb.length() == 0)
         return bb;
       int i = byteArrayToInt(bb.getBytes());
       if (log.isInfoEnabled()) {
         log.info("Int2Str:[" + String.valueOf(i) + "]");
       }
       return new HiByteBuffer(String.valueOf(i).getBytes());
     } catch (RuntimeException e) {
       Logger log = HiLog.getLogger(mc.getCurrentMsg());
       log.error(e);
       throw e;
     }
   }
 
   public HiByteBuffer Str2Int(HiByteBuffer bb, HiMessageContext mc)
   {
     try {
       if (bb.length() == 0) {
         return bb;
       }
       int i = NumberUtils.toInt(bb.toString());
       if (log.isInfoEnabled()) {
         log.info("Str2Int:[" + String.valueOf(i) + "]");
       }
       return new HiByteBuffer(intToByteArray(i));
     } catch (RuntimeException e) {
       Logger log = HiLog.getLogger(mc.getCurrentMsg());
       log.error(e);
       throw e;
     }
   }
 
   public static byte[] intToByteArray(int valor)
   {
     byte[] result = new byte[4];
     for (int i = 0; i < result.length; ++i)
     {
       result[i] = (byte)(valor & 0xFF);
       valor >>= 8;
     }
     return result;
   }
 
   public static int byteArrayToInt(byte[] bytes)
   {
     int result = 0;
 
     result |= 0xFF000000 & bytes[3] << 24;
     result |= 0xFF0000 & bytes[2] << 16;
     result |= 0xFF00 & bytes[1] << 8;
     result |= 0xFF & bytes[0];
     return result;
   }
 
   public static byte[] shortToByteArray(int valor)
   {
     byte[] result = new byte[2];
     for (int i = 0; i < result.length; ++i)
     {
       result[i] = (byte)(valor & 0xFF);
       valor >>= 8;
     }
     return result;
   }
 
   public static short byteArrayToShort(byte[] bytes)
   {
     int result = 0;
 
     result |= 0xFF00 & bytes[1] << 8;
     result |= 0xFF & bytes[0];
     return (short)result;
   }
 }