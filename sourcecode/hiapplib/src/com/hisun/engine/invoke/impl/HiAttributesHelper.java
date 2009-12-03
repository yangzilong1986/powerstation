/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.message.HiContext;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiAttributesHelper
/*     */ {
/*     */   private static final String KEY = "__ATTRIBUTE";
/*  19 */   private final String ATTR_INTEGRITY = "INTEGRITY";
/*     */ 
/*  21 */   private final String ATTR_NO_INTEGRITY = "0";
/*     */ 
/*  23 */   private final String ATTR_SYS_CRCT = "1";
/*     */ 
/*  25 */   private final String ATTR_CND_CRCT = "2";
/*     */ 
/*  27 */   private final String ATTR_SYS_RSND = "6";
/*     */ 
/*  29 */   private final String ATTR_CND_RSND = "7";
/*     */ 
/*  31 */   private final String ATTR_NOCHK = "NOCHK";
/*     */ 
/*  33 */   private final String ATTR_NEED_CHK = "0";
/*     */ 
/*  35 */   private final String ATTR_NO_CHK = "1";
/*     */ 
/*  37 */   private final String ATTR_NORESPONSE = "NORESPONSE";
/*     */ 
/*  39 */   private final String ATTR_NEED_RESP = "0";
/*     */ 
/*  41 */   private final String ATTR_NO_RESP = "1";
/*     */ 
/*  43 */   private final String ATTR_NODB = "NODATABASE";
/*     */ 
/*  45 */   private final String ATTR_SHORT_DB_CONN = "0";
/*     */ 
/*  47 */   private final String ATTR_NO_DB_CONN = "1";
/*     */ 
/*  49 */   private final String ATTR_LONG_DB_CONN = "2";
/*     */ 
/*  51 */   private final String ATTR_INTGTYPE = "INTEGTYPE";
/*     */ 
/*  53 */   private final String ATTR_INTGTYPE_NO = "0";
/*     */ 
/*  55 */   private final String ATTR_INTGTYPE_HAND = "1";
/*     */ 
/*  57 */   private final String ATTR_INTGTYPE_SYS = "2";
/*     */ 
/*  59 */   private final String ATTR_INTGTYPE_RDR = "6";
/*     */ 
/*  61 */   private final String ATTR_INTGTYPE_CCL = "9";
/*     */ 
/*  63 */   private final String ATTR_MONITOR = "MONITOR";
/*     */ 
/*  65 */   private final String ATTR_MONITOR_NO = "0";
/*     */ 
/*  67 */   private final String ATTR_MONITOR_YES = "1";
/*     */ 
/*  69 */   private final String TXNTYP_NORMAL = "N";
/*     */ 
/*  71 */   private final String TXNTYP_CRCT = "C";
/*     */ 
/*  73 */   private final String TXNTYP_RSND = "R";
/*     */ 
/*  75 */   private final String TXNTYP_CCLD = "D";
/*     */ 
/*  77 */   private HashMap map = new HashMap(5);
/*     */ 
/*     */   public HiAttributesHelper() {
/*  80 */     this.map.put("INTEGRITY", "0");
/*  81 */     this.map.put("MONITOR", "0");
/*  82 */     this.map.put("INTEGTYPE", "0");
/*  83 */     this.map.put("NORESPONSE", "0");
/*  84 */     this.map.put("NOCHK", "0");
/*     */   }
/*     */ 
/*     */   public void put(String name, String value) {
/*  88 */     this.map.put(name.toUpperCase(), value);
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  92 */     String returnString = (String)this.map.get("NAME");
/*  93 */     return returnString;
/*     */   }
/*     */ 
/*     */   public HashMap getAttributesInfo() {
/*  97 */     return this.map;
/*     */   }
/*     */ 
/*     */   public String integrity()
/*     */   {
/* 111 */     return ((String)this.map.get("INTEGRITY"));
/*     */   }
/*     */ 
/*     */   public boolean isNoIntegrity()
/*     */   {
/* 120 */     return StringUtils.equals(integrity(), "0");
/*     */   }
/*     */ 
/*     */   public boolean isSysCrct()
/*     */   {
/* 129 */     return StringUtils.equals(integrity(), "1");
/*     */   }
/*     */ 
/*     */   public boolean isCndCrct()
/*     */   {
/* 138 */     return StringUtils.equals(integrity(), "2");
/*     */   }
/*     */ 
/*     */   public boolean isSysRsnd()
/*     */   {
/* 147 */     return StringUtils.equals(integrity(), "6");
/*     */   }
/*     */ 
/*     */   public boolean isCndRsnd()
/*     */   {
/* 156 */     return StringUtils.equals(integrity(), "7");
/*     */   }
/*     */ 
/*     */   public String integtype()
/*     */   {
/* 170 */     return ((String)this.map.get("INTEGTYPE"));
/*     */   }
/*     */ 
/*     */   public boolean isIntegtypeNo()
/*     */   {
/* 179 */     return StringUtils.equals(integtype(), "0");
/*     */   }
/*     */ 
/*     */   public boolean isIntegtypeHand()
/*     */   {
/* 188 */     return StringUtils.equals(integtype(), "1");
/*     */   }
/*     */ 
/*     */   public boolean isIntegtypeSys()
/*     */   {
/* 197 */     return StringUtils.equals(integtype(), "2");
/*     */   }
/*     */ 
/*     */   public boolean isIntegtypeRDR()
/*     */   {
/* 206 */     return StringUtils.equals(integtype(), "6");
/*     */   }
/*     */ 
/*     */   public boolean isIntegtypeCCL()
/*     */   {
/* 215 */     return StringUtils.equals(integtype(), "9");
/*     */   }
/*     */ 
/*     */   public String nochk()
/*     */   {
/* 226 */     return ((String)this.map.get("NOCHK"));
/*     */   }
/*     */ 
/*     */   public boolean isNoChk() {
/* 230 */     String value = nochk();
/*     */ 
/* 232 */     return (!(StringUtils.equals(value, "1")));
/*     */   }
/*     */ 
/*     */   public String noresponse()
/*     */   {
/* 245 */     return ((String)this.map.get("NORESPONSE"));
/*     */   }
/*     */ 
/*     */   public boolean isNoResponse() {
/* 249 */     String value = noresponse();
/*     */ 
/* 251 */     return (!(StringUtils.equals(value, "1")));
/*     */   }
/*     */ 
/*     */   public String nodatabase()
/*     */   {
/* 265 */     return ((String)this.map.get("NODATABASE"));
/*     */   }
/*     */ 
/*     */   public boolean isShortDbConn()
/*     */   {
/* 270 */     return StringUtils.equals(nodatabase(), "0");
/*     */   }
/*     */ 
/*     */   public boolean isNoDbConn()
/*     */   {
/* 275 */     return StringUtils.equals(nodatabase(), "1");
/*     */   }
/*     */ 
/*     */   public boolean isLongDbConn()
/*     */   {
/* 280 */     return StringUtils.equals(nodatabase(), "2");
/*     */   }
/*     */ 
/*     */   public String code()
/*     */   {
/* 289 */     return ((String)this.map.get("CODE"));
/*     */   }
/*     */ 
/*     */   public int interval()
/*     */   {
/* 298 */     String value = (String)this.map.get("INTERVAL");
/* 299 */     return NumberUtils.toInt(value, -1);
/*     */   }
/*     */ 
/*     */   public int timeout()
/*     */   {
/* 306 */     String value = (String)this.map.get("TIMEOUT");
/* 307 */     return NumberUtils.toInt(value, -1);
/*     */   }
/*     */ 
/*     */   public int maxtimes()
/*     */   {
/* 316 */     String value = (String)this.map.get("MAXTIMES");
/* 317 */     return NumberUtils.toInt(value, -1);
/*     */   }
/*     */ 
/*     */   public String objsvr()
/*     */   {
/* 326 */     return ((String)this.map.get("OBJSVR"));
/*     */   }
/*     */ 
/*     */   public String monitor()
/*     */   {
/* 333 */     return ((String)this.map.get("MONITOR"));
/*     */   }
/*     */ 
/*     */   public boolean isMonitor() {
/* 337 */     String value = monitor();
/* 338 */     return StringUtils.equals(value, "1");
/*     */   }
/*     */ 
/*     */   public static void setAttribute(HiContext ctx, HiAttributesHelper attr) {
/* 342 */     ctx.setProperty("__ATTRIBUTE", attr);
/*     */   }
/*     */ 
/*     */   public static HiAttributesHelper getAttribute(HiContext ctx)
/*     */   {
/* 350 */     HiAttributesHelper attr = (HiAttributesHelper)ctx.getProperty("__ATTRIBUTE");
/* 351 */     if (attr == null) {
/* 352 */       attr = new HiAttributesHelper();
/* 353 */       setAttribute(ctx, attr);
/*     */     }
/* 355 */     return attr;
/*     */   }
/*     */ 
/*     */   public String getTxnTyp()
/*     */   {
/* 363 */     if (isIntegtypeNo()) {
/* 364 */       super.getClass(); return "N"; }
/* 365 */     if (isIntegtypeRDR()) {
/* 366 */       super.getClass(); return "R";
/*     */     }
/* 368 */     super.getClass(); return "C";
/*     */   }
/*     */ 
/*     */   public boolean isNormalTxn() {
/* 372 */     super.getClass(); return StringUtils.equals(getTxnTyp(), "N");
/*     */   }
/*     */ 
/*     */   public boolean isRSNDTxn() {
/* 376 */     super.getClass(); return StringUtils.equals(getTxnTyp(), "R");
/*     */   }
/*     */ 
/*     */   public boolean isCRCTTxn() {
/* 380 */     super.getClass(); return StringUtils.equals(getTxnTyp(), "C");
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 384 */     return this.map.toString();
/*     */   }
/*     */ }