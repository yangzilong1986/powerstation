/*     */ package com.hisun.trigger;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerDestroyListener;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.time.DateUtils;
/*     */ 
/*     */ public class HiTrigger
/*     */   implements IHandler, IServerInitListener, IServerDestroyListener
/*     */ {
/*  25 */   private String strFileName = null;
/*     */ 
/*  31 */   private HiTimeTrigger triggerObj = null;
/*     */ 
/*     */   public void setFilename(String strFileName)
/*     */   {
/*  28 */     this.strFileName = strFileName;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  34 */     ArrayList schItems = this.triggerObj.getSchItems();
/*  35 */     Date currentDate = new Date(System.currentTimeMillis());
/*  36 */     for (int i = 0; i < schItems.size(); ++i) {
/*  37 */       HiSchItem item = (HiSchItem)schItems.get(i);
/*  38 */       Logger log = HiTimeTrigger.log;
/*  39 */       currentDate = DateUtils.truncate(currentDate, 12);
/*     */       try {
/*  41 */         if (isTrigger(item, currentDate)) {
/*  42 */           currentDate = DateUtils.truncate(currentDate, 12);
/*     */ 
/*  44 */           HiMessage msg = ctx.getCurrentMsg().cloneNoBodyAndRqID();
/*  45 */           ctx.setCurrentMsg(msg);
/*  46 */           item.process(ctx, currentDate);
/*     */         }
/*     */       } catch (Exception e) {
/*  49 */         log.error(e, e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isTrigger(HiSchItem item, Date currentDate)
/*     */     throws HiException
/*     */   {
/*  57 */     Logger log = HiTimeTrigger.log;
/*     */ 
/*  59 */     Date lastDate = item.getLastDate();
/*     */ 
/*  63 */     if (DateUtils.truncate(lastDate, 12).equals(currentDate))
/*     */     {
/*  67 */       return (!(item.isRepeatSend()));
/*     */     }
/*     */ 
/*  74 */     Date afterDate = item.getCronExpression().getTimeAfter(currentDate);
/*     */ 
/*  76 */     long triggerTime = DateUtils.truncate(afterDate, 12).getTime();
/*     */ 
/*  80 */     return (triggerTime > currentDate.getTime());
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/* 104 */     if (this.strFileName.equalsIgnoreCase(""))
/* 105 */       this.triggerObj = HiSchUtilites.loadTimeTriggerFormDB();
/*     */     else
/* 107 */       this.triggerObj = HiSchUtilites.loadTimeTrigger(this.strFileName);
/* 108 */     this.triggerObj.init();
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/*     */   }
/*     */ }