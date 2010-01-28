 package com.hisun.trigger;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import java.util.ArrayList;
 import java.util.Date;
 import org.apache.commons.lang.time.DateUtils;
 
 public class HiTimeTrigger
 {
   public static final Logger log = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   private ArrayList childs = new ArrayList();
 
   public void addChilds(Object item)
   {
     this.childs.add(item);
   }
 
   public ArrayList getSchItems()
   {
     return this.childs;
   }
 
   public void init() throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("HiTimeTrigger init()");
     }
     Date currentDate = new Date(System.currentTimeMillis());
     currentDate = DateUtils.truncate(currentDate, 12);
 
     for (int i = 0; i < this.childs.size(); ++i)
     {
       HiSchItem schItem = (HiSchItem)this.childs.get(i);
       schItem.init();
       schItem.setLastDate(currentDate);
     }
   }
 }