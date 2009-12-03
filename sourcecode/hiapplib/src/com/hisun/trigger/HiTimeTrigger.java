/*    */ package com.hisun.trigger;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import org.apache.commons.lang.time.DateUtils;
/*    */ 
/*    */ public class HiTimeTrigger
/*    */ {
/* 23 */   public static final Logger log = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*    */ 
/* 26 */   private ArrayList childs = new ArrayList();
/*    */ 
/*    */   public void addChilds(Object item)
/*    */   {
/* 30 */     this.childs.add(item);
/*    */   }
/*    */ 
/*    */   public ArrayList getSchItems()
/*    */   {
/* 35 */     return this.childs;
/*    */   }
/*    */ 
/*    */   public void init() throws HiException
/*    */   {
/* 40 */     if (log.isDebugEnabled()) {
/* 41 */       log.debug("HiTimeTrigger init()");
/*    */     }
/* 43 */     Date currentDate = new Date(System.currentTimeMillis());
/* 44 */     currentDate = DateUtils.truncate(currentDate, 12);
/*    */ 
/* 46 */     for (int i = 0; i < this.childs.size(); ++i)
/*    */     {
/* 48 */       HiSchItem schItem = (HiSchItem)this.childs.get(i);
/* 49 */       schItem.init();
/* 50 */       schItem.setLastDate(currentDate);
/*    */     }
/*    */   }
/*    */ }