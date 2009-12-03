/*     */ package com.hisun.sqn;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ class HiDumTrlNode
/*     */ {
/*     */   String _brNo;
/*     */   String _txnCnl;
/*     */   String _cnlSub;
/*     */   private int _nextPos;
/*     */   ArrayList _trlList;
/*     */ 
/*     */   HiDumTrlNode()
/*     */   {
/* 118 */     this._trlList = new ArrayList(); }
/*     */ 
/*     */   public String getDumTrl() {
/* 121 */     synchronized (this._trlList) {
/* 122 */       String dumTlr = (String)this._trlList.get(this._nextPos);
/* 123 */       this._nextPos += 1;
/* 124 */       if (this._nextPos == this._trlList.size()) {
/* 125 */         this._nextPos = 0;
/*     */       }
/* 127 */       return dumTlr;
/*     */     }
/*     */   }
/*     */ }