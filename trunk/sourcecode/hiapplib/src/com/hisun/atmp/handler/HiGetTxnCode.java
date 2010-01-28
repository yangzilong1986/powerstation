 package com.hisun.atmp.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import org.apache.commons.lang.StringUtils;
 
 public class HiGetTxnCode
   implements IHandler, IServerInitListener
 {
   public static final int NCR_MESSAGE = 0;
   public static final int DIBAO_MESSAGE = 1;
   public static final int OTHER_MESSAGE = 2;
   private char _deli;
 
   public HiGetTxnCode()
   {
     this._deli = '\28'; }
 
   public char getDeli() {
     return this._deli;
   }
 
   public void setDeli(char deli) {
     this._deli = deli;
   }
 
   public void process(HiMessageContext arg0) throws HiException {
     getTxnCode(arg0);
   }
 
   public void getTxnCode(HiMessageContext arg0) throws HiException {
     HiMessage msg = arg0.getCurrentMsg();
     Logger log = HiLog.getLogger(arg0.getCurrentMsg());
 
     HiByteBuffer buffer = (HiByteBuffer)msg.getBody();
 
     String txnCode = null;
     switch (getType(arg0, this._deli))
     {
     case 1:
       String[] strs = StringUtils.splitPreserveAllTokens(buffer.toString(), '\28');
 
       if (strs.length < 3) {
         throw new HiException("310007", "dibao");
       }
 
       log.info("迪宝第二域:" + strs[1]);
       if ("RESP".equalsIgnoreCase(StringUtils.substring(strs[1], 0, 4))) {
         txnCode = strs[5];
       }
       else {
         txnCode = strs[2] + "D";
       }
       break;
     case 0:
       if (buffer.length() < 13) {
         throw new HiException("310007", "NCR");
       }
 
       txnCode = buffer.substr(10, 3) + "N";
       break;
     default:
       throw new HiException("310007", "ATM");
     }
     msg.setHeadItem("STC", txnCode);
   }
 
   public void serverInit(ServerEvent arg0) throws HiException
   {
   }
 
   public static int getType(HiMessageContext ctx, char deli) {
     HiMessage msg = ctx.getCurrentMsg();
     HiByteBuffer buffer = (HiByteBuffer)msg.getBody();
     if (buffer.charAt(0) == deli) {
       return 1;
     }
     if (buffer.length() < 5) {
       return 2;
     }
     String s1 = buffer.substr(0, 5);
     if (StringUtils.equalsIgnoreCase(s1, "ABCDE")) {
       return 0;
     }
     return 2;
   }
 
   public static int getType(HiMessageContext ctx)
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiByteBuffer buffer = (HiByteBuffer)msg.getBody();
     if (buffer.charAt(0) == 28) {
       return 1;
     }
     if (buffer.length() < 5) {
       return 2;
     }
     String s1 = buffer.substr(0, 5);
     if (StringUtils.equalsIgnoreCase(s1, "ABCDE")) {
       return 0;
     }
     return 2;
   }
 }