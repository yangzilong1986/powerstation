 package com.hisun.mon.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiConvHelper;
 
 public class HiMonXmlPreProcess
   implements IHandler, IServerInitListener
 {
   private String txnCode = "_TCOD";
   private int txnCodeLen = 6;
   private int root_start_pos;
   private int root_end_pos;
   final Logger log = (Logger)HiContext.getCurrentContext()
     .getProperty("SVR.log");
 
   public void process(HiMessageContext ctx) throws HiException { HiMessage msg = ctx.getCurrentMsg();
     HiByteBuffer plainBytes = (HiByteBuffer)msg.getBody();
     if (this.log.isDebugEnabled())
     {
       this.log.debug("Source Data:[" + HiConvHelper.bcd2AscStr(plainBytes.subbyte(0, plainBytes.length())) + "]");
     }
     if (plainBytes.length() > 0)
     {
       this.root_start_pos = plainBytes.indexOf("<root>".getBytes(), 0);
       this.root_end_pos = plainBytes.indexOf("</root>".getBytes(), 0);
 
       msg.setHeadItem(this.txnCode, new String(plainBytes.subbyte(0, this.root_start_pos)));
 
       msg.setHeadItem("STC", "MON090101");
 
       msg.setBody(new HiByteBuffer(plainBytes.subbyte(this.root_start_pos, this.root_end_pos - this.root_start_pos + "</root>".length())));
     }
     else
     {
       throw new HiException("MON0001", " Message length too short");
     }
   }
 
   public void serverInit(ServerEvent arg0)
     throws HiException
   {
   }
 }