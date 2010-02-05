 package com.hisun.sqn;
 
 import java.util.ArrayList;
 
 class HiDumTrlNode
 {
   String _brNo;
   String _txnCnl;
   String _cnlSub;
   private int _nextPos;
   ArrayList _trlList;
 
   HiDumTrlNode()
   {
     this._trlList = new ArrayList(); }
 
   public String getDumTrl() {
     synchronized (this._trlList) {
       String dumTlr = (String)this._trlList.get(this._nextPos);
       this._nextPos += 1;
       if (this._nextPos == this._trlList.size()) {
         this._nextPos = 0;
       }
       return dumTlr;
     }
   }
 }