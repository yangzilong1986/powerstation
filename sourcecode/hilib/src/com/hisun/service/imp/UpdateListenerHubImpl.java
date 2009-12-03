/*    */ package com.hisun.service.imp;
/*    */ 
/*    */ import com.hisun.event.UpdateListener;
/*    */ import com.hisun.service.UpdateListenerHub;
/*    */ import edu.emory.mathcs.backport.java.util.concurrent.CopyOnWriteArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class UpdateListenerHubImpl
/*    */   implements UpdateListenerHub
/*    */ {
/*    */   private final List _listeners;
/*    */ 
/*    */   public UpdateListenerHubImpl()
/*    */   {
/* 12 */     this._listeners = new CopyOnWriteArrayList(); }
/*    */ 
/*    */   public void addUpdateListener(UpdateListener listener) {
/* 15 */     this._listeners.add(listener);
/*    */   }
/*    */ 
/*    */   public void fireUpdateEvent()
/*    */   {
/* 24 */     for (int i = 0; i < this._listeners.size(); ++i)
/* 25 */       ((UpdateListener)this._listeners.get(i)).checkForUpdates();
/*    */   }
/*    */ }