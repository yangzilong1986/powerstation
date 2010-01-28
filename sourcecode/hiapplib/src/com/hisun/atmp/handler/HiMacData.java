 package com.hisun.atmp.handler;
 
 import com.hisun.atmp.crypt.Shuffle;
 import com.hisun.dispatcher.HiRouterOut;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import org.apache.commons.codec.binary.Hex;
 
 public class HiMacData
   implements IHandler
 {
   private String key;
 
   public HiMacData()
   {
     this.key = "POS_EXTRA"; }
 
   public void process(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     byte[] data = ((HiByteBuffer)msg.getBody()).getBytes();
 
     HiETF root = (HiETF)ctx.getProperty(this.key);
 
     byte[] d = getMACDate(data);
 
     root.setChildValue("DatVal", new String(Hex.encodeHex(d)));
 
     ctx.setProperty(this.key, msg.getBody());
     msg.setBody(root);
     msg.setHeadItem("SDT", "STHDSSB1");
     msg.setHeadItem("STC", "310002");
     msg.setHeadItem("ECT", "text/etf");
     Logger log = HiLog.getLogger(msg);
 
     msg.setHeadItem("SCH", "rq");
 
     if (log.isInfoEnabled()) {
       log.info("before genMac:" + msg.toString());
     }
 
     HiRouterOut.process(ctx);
     if (log.isInfoEnabled()) {
       log.info("after genMac:" + ctx.getCurrentMsg().toString());
     }
     process2(ctx);
   }
 
   public void process2(HiMessageContext arg0) throws HiException {
     HiMessage msg = arg0.getCurrentMsg();
     HiETF root = msg.getETFBody();
 
     HiByteBuffer buf = (HiByteBuffer)arg0.getProperty(this.key);
     byte[] outbuf = buf.getBytes();
 
     String mac = root.getChildValue("MacVal");
 
     if (mac != null) {
       char[] pmac = new char[8];
       Shuffle.strToBCD(pmac, mac.toCharArray(), 8);
       byte[] bytes = chars2bytes(pmac);
 
       System.arraycopy(bytes, 0, outbuf, outbuf.length - 8, 8);
     }
     msg.setBody(new HiByteBuffer(outbuf));
   }
 
   private byte[] getMACDate(byte[] d8583)
   {
     int len = d8583.length - 8;
     len = (len > 512) ? 512 : len;
     byte[] ret = new byte[len];
     System.arraycopy(d8583, 0, ret, 0, len);
     return ret;
   }
 
   private static byte[] chars2bytes(char[] key) {
     byte[] b_key = new byte[key.length];
     for (int i = 0; i < key.length; ++i)
       b_key[i] = (byte)key[i];
     return b_key;
   }
 
   public String getKey() {
     return this.key;
   }
 
   public void setKey(String key) {
     this.key = key;
   }
 }