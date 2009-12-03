/*    */ package com.hisun.tools.parser;
/*    */ 
/*    */ import com.hisun.tools.HiClnParam;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ 
/*    */ public class HiClnTran
/*    */ {
/*    */   private LinkedHashMap _clnGroupMap;
/*    */ 
/*    */   public HiClnTran()
/*    */   {
/* 10 */     this._clnGroupMap = new LinkedHashMap(); }
/*    */ 
/*    */   public void process(HiClnParam param) throws Exception { Iterator iter = this._clnGroupMap.values().iterator();
/* 13 */     while (iter.hasNext())
/* 14 */       ((HiClnGroup)iter.next()).process(param);
/*    */   }
/*    */ 
/*    */   public void addClnGroup(HiClnGroup clnGroup)
/*    */   {
/* 20 */     this._clnGroupMap.put(clnGroup.getId(), clnGroup);
/*    */   }
/*    */ 
/*    */   public void process(String id, HiClnParam param) throws Exception {
/* 24 */     if (!(this._clnGroupMap.containsKey(id))) {
/* 25 */       throw new Exception("not found:[" + id + "] group");
/*    */     }
/* 27 */     ((HiClnGroup)this._clnGroupMap.get(id)).process(param);
/*    */   }
/*    */ }