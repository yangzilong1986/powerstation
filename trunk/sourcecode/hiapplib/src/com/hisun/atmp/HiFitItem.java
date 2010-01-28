 package com.hisun.atmp;
 
 import java.io.Serializable;
 import java.util.HashMap;
 
 class HiFitItem
   implements Serializable
 {
   int fitTrk;
   int fitOfs;
   String fitCtt;
   int fitLen;
   int crdTrk;
   int crdOfs;
   int crdLen;
   String crdFlg;
   int cdCdOf;
   String bnkTyp;
   String crdNam;
   String intMod;
   String fitNo;
   int vaDtOf;
 
   public HashMap toMap()
   {
     HashMap map = new HashMap();
     map.put("fitTrk", new Integer(this.fitTrk));
     map.put("fitOfs", new Integer(this.fitOfs));
     map.put("fitCtt", this.fitCtt);
     map.put("fitLen", new Integer(this.fitLen));
     map.put("crdTrk", new Integer(this.crdTrk));
     map.put("crdOfs", new Integer(this.crdOfs));
     map.put("crdLen", new Integer(this.crdLen));
     map.put("crdFlg", this.crdFlg);
     map.put("cdCdOf", new Integer(this.cdCdOf));
     map.put("bnkTyp", this.bnkTyp);
     map.put("crdNam", this.crdNam);
     map.put("intMod", this.intMod);
     map.put("fitNo", this.fitNo);
     map.put("vaDtOf", new Integer(this.vaDtOf));
     return map;
   }
 
   public void toFitItem(HashMap map) {
     this.fitTrk = ((Integer)map.get("fitTrk")).intValue();
     this.fitOfs = ((Integer)map.get("fitOfs")).intValue();
     this.fitCtt = ((String)map.get("fitCtt"));
     this.fitLen = ((Integer)map.get("fitLen")).intValue();
     this.crdTrk = ((Integer)map.get("crdTrk")).intValue();
     this.crdOfs = ((Integer)map.get("crdOfs")).intValue();
     this.crdLen = ((Integer)map.get("crdLen")).intValue();
     this.crdFlg = ((String)map.get("crdFlg"));
     this.cdCdOf = ((Integer)map.get("cdCdOf")).intValue();
     this.bnkTyp = ((String)map.get("bnkTyp"));
     this.crdNam = ((String)map.get("crdNam"));
     this.intMod = ((String)map.get("intMod"));
     this.fitNo = ((String)map.get("fitNo"));
     this.vaDtOf = ((Integer)map.get("vaDtOf")).intValue();
   }
 }