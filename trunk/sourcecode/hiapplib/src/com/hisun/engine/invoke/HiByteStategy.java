 package com.hisun.engine.invoke;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.util.HiByteBuffer;
 
 public class HiByteStategy extends HiAbstractStrategy
 {
   public Object createBeforeMess(HiMessage mess, boolean isInnerMess)
     throws HiException
   {
     if (isInnerMess)
     {
       HiByteBuffer bb = new HiByteBuffer(1024);
       return bb;
     }
 
     Object body = mess.getBody();
 
     return body;
   }
 
   public Object createAfterMess(HiMessage mess)
     throws HiException
   {
     Object obj = mess.getObjectHeadItem("PlainText");
     Logger log = HiLog.getLogger(mess);
 
     if (obj instanceof HiByteBuffer)
     {
       return obj;
     }
     if (obj instanceof byte[])
     {
       return new HiByteBuffer((byte[])(byte[])obj);
     }
 
     return obj;
   }
 }