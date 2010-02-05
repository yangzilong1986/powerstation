 package com.hisun.tools.parser;
 
 import com.hisun.tools.HiClnParam;
 import java.util.ArrayList;
 
 public class HiClnGroup
 {
   private String _id;
   private boolean _ismonitor;
   private ArrayList _clnRecs;
 
   public HiClnGroup()
   {
     this._clnRecs = new ArrayList(); }
 
   public String getId() { return this._id; }
 
   public void setId(String id) {
     this._id = id; }
 
   public boolean isIsmonitor() {
     return this._ismonitor; }
 
   public void setIsmonitor(boolean ismonitor) {
     this._ismonitor = ismonitor; }
 
   public void addClnRec(Object o) {
     this._clnRecs.add(o); }
 
   public void process(HiClnParam param) throws Exception {
     for (int i = 0; i < this._clnRecs.size(); ++i)
       ((HiClnRec)this._clnRecs.get(i)).process(param);
   }
 }