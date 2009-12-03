/*    */ package com.hisun.tools.parser;
/*    */ 
/*    */ import com.hisun.tools.HiFilBrcParam;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class HiFileBpRecover
/*    */ {
/*    */   private HashMap _fileBpRecoverItemMap;
/*    */ 
/*    */   public HiFileBpRecover()
/*    */   {
/*  9 */     this._fileBpRecoverItemMap = new HashMap(); }
/*    */ 
/*    */   public void addFileBpRecoverItem(HiFileBpRecoverItem item) {
/* 12 */     this._fileBpRecoverItemMap.put(item.getFiletype(), item);
/*    */   }
/*    */ 
/*    */   public void process(String fileType, HiFilBrcParam param) throws Exception {
/* 16 */     if (!(this._fileBpRecoverItemMap.containsKey(fileType))) {
/* 17 */       throw new Exception("not found:[" + fileType + "] group");
/*    */     }
/* 19 */     ((HiFileBpRecoverItem)this._fileBpRecoverItemMap.get(fileType)).process(param);
/*    */   }
/*    */ 
/*    */   public void process(HiFilBrcParam param) throws Exception {
/* 23 */     Iterator iter = this._fileBpRecoverItemMap.values().iterator();
/* 24 */     while (iter.hasNext())
/* 25 */       ((HiFileBpRecoverItem)iter.next()).process(param);
/*    */   }
/*    */ }