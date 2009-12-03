/*    */ package com.hisun.tools.parser;
/*    */ 
/*    */ import com.hisun.tools.HiClnParam;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class HiClnGroup
/*    */ {
/*    */   private String _id;
/*    */   private boolean _ismonitor;
/*    */   private ArrayList _clnRecs;
/*    */ 
/*    */   public HiClnGroup()
/*    */   {
/* 11 */     this._clnRecs = new ArrayList(); }
/*    */ 
/*    */   public String getId() { return this._id; }
/*    */ 
/*    */   public void setId(String id) {
/* 16 */     this._id = id; }
/*    */ 
/*    */   public boolean isIsmonitor() {
/* 19 */     return this._ismonitor; }
/*    */ 
/*    */   public void setIsmonitor(boolean ismonitor) {
/* 22 */     this._ismonitor = ismonitor; }
/*    */ 
/*    */   public void addClnRec(Object o) {
/* 25 */     this._clnRecs.add(o); }
/*    */ 
/*    */   public void process(HiClnParam param) throws Exception {
/* 28 */     for (int i = 0; i < this._clnRecs.size(); ++i)
/* 29 */       ((HiClnRec)this._clnRecs.get(i)).process(param);
/*    */   }
/*    */ }