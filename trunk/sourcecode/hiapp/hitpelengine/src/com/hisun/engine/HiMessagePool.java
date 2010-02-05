 package com.hisun.engine;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
 import java.util.Iterator;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiMessagePool
 {
   private static final String THREAD_HEADER = "ORI_THREAD";
   private final ConcurrentHashMap headers = new ConcurrentHashMap();
   private int memPoolTmOut = 31;
   private int memPoolSize = 300;
 
   public HiMessagePool()
   {
     String value = HiContext.getCurrentContext().getStrProp("@PARA", "_MEMPOOLTMOUT");
 
     this.memPoolTmOut = NumberUtils.toInt(value, 30);
     value = HiContext.getCurrentContext().getStrProp("@PARA", "_MEMPOOLSIZE");
 
     this.memPoolSize = NumberUtils.toInt(value, 60);
   }
 
   public ConcurrentHashMap getPool() {
     return this.headers;
   }
 
   public static synchronized void setMessagePool() {
     setMessagePool(HiContext.getCurrentContext());
   }
 
   public static synchronized void setMessagePool(HiContext ctx) {
     if (ctx.containsProperty("ORI_THREAD"))
       return;
     HiMessagePool msgPool = new HiMessagePool();
     ctx.setProperty("ORI_THREAD", msgPool);
   }
 
   public static HiMessagePool getMessagePool() {
     return getMessagePool(HiContext.getCurrentContext());
   }
 
   public static HiMessagePool getMessagePool(HiContext ctx) {
     return ((HiMessagePool)ctx.getProperty("ORI_THREAD"));
   }
 
   public synchronized void saveHeader(Object key, HiMessage msg)
     throws HiException
   {
     if (this.headers.containsKey(key)) {
       HiMessage msg1 = (HiMessage)this.headers.get(key);
       Long stm = (Long)msg.getObjectHeadItem("STM");
       boolean timeout = System.currentTimeMillis() - stm.longValue() > this.memPoolTmOut * 1000;
       if (!(timeout)) {
         throw new HiException("213342", "mempool duplicate key:[" + key.toString() + "]");
       }
     }
     else if (this.headers.size() >= this.memPoolSize) {
       removeTimeOut();
       if (this.headers.size() >= this.memPoolSize) {
         throw new HiException("213343", "mempool overflow size:[" + key.toString() + "]");
       }
     }
 
     this.headers.put(key, msg);
   }
 
   public void removeTimeOut() {
     Iterator it = this.headers.entrySet().iterator();
     while (it.hasNext()) {
       Map.Entry entry = (Map.Entry)it.next();
       HiMessage msg = (HiMessage)entry.getValue();
       Long stm = (Long)msg.getObjectHeadItem("STM");
       boolean timeout = System.currentTimeMillis() - stm.longValue() > this.memPoolTmOut * 1000;
       if (timeout)
         it.remove();
     }
   }
 
   public Object restoreHeader(Object key, HiMessage msg)
   {
     HiMessage orimsg = (HiMessage)this.headers.get(key);
 
     Logger log = HiLog.getLogger(msg);
 
     if (orimsg == null) {
       log.warn("invalid message:" + msg);
       return msg;
     }
     this.headers.remove(key);
     orimsg.setBody(msg.getBody());
     msg.setHead(orimsg.getHead());
 
     return msg;
   }
 
   public Object getHeader(Object key) {
     return this.headers.get(key);
   }
 
   public void removeHeader(Object key)
   {
     this.headers.remove(key);
   }
 
   public boolean containsHeader(Object key)
   {
     return this.headers.containsKey(key);
   }
 }