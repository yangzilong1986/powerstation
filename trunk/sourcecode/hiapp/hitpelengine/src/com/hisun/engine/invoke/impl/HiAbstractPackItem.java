 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiITFEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.util.HiByteBuffer;
 import org.apache.commons.lang.StringUtils;
 
 public abstract class HiAbstractPackItem extends HiITFEngineModel
 {
   private final Logger logger;
   protected String name;
 
   public HiAbstractPackItem()
   {
     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
   }
 
   public String getName()
   {
     return this.name;
   }
 
   public void setName(String name)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setName(String) - start");
     }
 
     this.name = name;
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setName(String) - end");
   }
 
   public void loadAfter()
     throws HiException
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("init() - start");
     }
 
     if (StringUtils.isBlank(this.name))
     {
       throw new HiException("213144");
     }
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("init() - end");
   }
 
   protected void initMsgState(HiMessage msg, String ect, HiByteBuffer packValue)
   {
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled())
     {
       log.debug("initMsgState(HiMessage, String, String) - start");
     }
 
     msg.setHeadItem("PlainText", packValue);
     msg.setHeadItem("PlainOffset", "0");
     msg.setHeadItem("ECT", ect);
 
     if (!(log.isDebugEnabled()))
       return;
     log.debug("initMsgState(HiMessage, String, String) - end");
   }
 
   protected void restoreMsgState(HiMessage msg, HiByteBuffer plainBuf, int plainOffset, String msgType)
   {
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled())
     {
       log.debug("restoreMsgState(HiMessage, StringBuffer, int, String) - start");
     }
 
     msg.setHeadItem("PlainText", plainBuf);
     msg.setHeadItem("PlainOffset", String.valueOf(plainOffset));
     msg.setHeadItem("ECT", msgType);
 
     if (!(log.isDebugEnabled()))
       return;
     log.debug("restoreMsgState(HiMessage, StringBuffer, int, String) - end");
   }
 
   public String toString()
   {
     return super.toString() + ":name[" + this.name + "]";
   }
 }