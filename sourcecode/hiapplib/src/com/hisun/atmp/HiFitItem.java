/*     */ package com.hisun.atmp;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ class HiFitItem
/*     */   implements Serializable
/*     */ {
/*     */   int fitTrk;
/*     */   int fitOfs;
/*     */   String fitCtt;
/*     */   int fitLen;
/*     */   int crdTrk;
/*     */   int crdOfs;
/*     */   int crdLen;
/*     */   String crdFlg;
/*     */   int cdCdOf;
/*     */   String bnkTyp;
/*     */   String crdNam;
/*     */   String intMod;
/*     */   String fitNo;
/*     */   int vaDtOf;
/*     */ 
/*     */   public HashMap toMap()
/*     */   {
/* 162 */     HashMap map = new HashMap();
/* 163 */     map.put("fitTrk", new Integer(this.fitTrk));
/* 164 */     map.put("fitOfs", new Integer(this.fitOfs));
/* 165 */     map.put("fitCtt", this.fitCtt);
/* 166 */     map.put("fitLen", new Integer(this.fitLen));
/* 167 */     map.put("crdTrk", new Integer(this.crdTrk));
/* 168 */     map.put("crdOfs", new Integer(this.crdOfs));
/* 169 */     map.put("crdLen", new Integer(this.crdLen));
/* 170 */     map.put("crdFlg", this.crdFlg);
/* 171 */     map.put("cdCdOf", new Integer(this.cdCdOf));
/* 172 */     map.put("bnkTyp", this.bnkTyp);
/* 173 */     map.put("crdNam", this.crdNam);
/* 174 */     map.put("intMod", this.intMod);
/* 175 */     map.put("fitNo", this.fitNo);
/* 176 */     map.put("vaDtOf", new Integer(this.vaDtOf));
/* 177 */     return map;
/*     */   }
/*     */ 
/*     */   public void toFitItem(HashMap map) {
/* 181 */     this.fitTrk = ((Integer)map.get("fitTrk")).intValue();
/* 182 */     this.fitOfs = ((Integer)map.get("fitOfs")).intValue();
/* 183 */     this.fitCtt = ((String)map.get("fitCtt"));
/* 184 */     this.fitLen = ((Integer)map.get("fitLen")).intValue();
/* 185 */     this.crdTrk = ((Integer)map.get("crdTrk")).intValue();
/* 186 */     this.crdOfs = ((Integer)map.get("crdOfs")).intValue();
/* 187 */     this.crdLen = ((Integer)map.get("crdLen")).intValue();
/* 188 */     this.crdFlg = ((String)map.get("crdFlg"));
/* 189 */     this.cdCdOf = ((Integer)map.get("cdCdOf")).intValue();
/* 190 */     this.bnkTyp = ((String)map.get("bnkTyp"));
/* 191 */     this.crdNam = ((String)map.get("crdNam"));
/* 192 */     this.intMod = ((String)map.get("intMod"));
/* 193 */     this.fitNo = ((String)map.get("fitNo"));
/* 194 */     this.vaDtOf = ((Integer)map.get("vaDtOf")).intValue();
/*     */   }
/*     */ }