 package com.hisun.tools.parser;
 
 import com.hisun.tools.HiFilBrcParam;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.Iterator;
 
 public class HiFileBpRecover
 {
   private HashMap _fileBpRecoverItemMap;
 
   public HiFileBpRecover()
   {
     this._fileBpRecoverItemMap = new HashMap(); }
 
   public void addFileBpRecoverItem(HiFileBpRecoverItem item) {
     this._fileBpRecoverItemMap.put(item.getFiletype(), item);
   }
 
   public void process(String fileType, HiFilBrcParam param) throws Exception {
     if (!(this._fileBpRecoverItemMap.containsKey(fileType))) {
       throw new Exception("not found:[" + fileType + "] group");
     }
     ((HiFileBpRecoverItem)this._fileBpRecoverItemMap.get(fileType)).process(param);
   }
 
   public void process(HiFilBrcParam param) throws Exception {
     Iterator iter = this._fileBpRecoverItemMap.values().iterator();
     while (iter.hasNext())
       ((HiFileBpRecoverItem)iter.next()).process(param);
   }
 }