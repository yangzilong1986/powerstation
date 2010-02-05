 package com.hisun.engine.invoke.impl;
 
 import com.hisun.message.HiContext;
 import java.util.HashMap;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiAttributesHelper
 {
   private static final String KEY = "__ATTRIBUTE";
   private final String ATTR_INTEGRITY = "INTEGRITY";
 
   private final String ATTR_NO_INTEGRITY = "0";
 
   private final String ATTR_SYS_CRCT = "1";
 
   private final String ATTR_CND_CRCT = "2";
 
   private final String ATTR_SYS_RSND = "6";
 
   private final String ATTR_CND_RSND = "7";
 
   private final String ATTR_NOCHK = "NOCHK";
 
   private final String ATTR_NEED_CHK = "0";
 
   private final String ATTR_NO_CHK = "1";
 
   private final String ATTR_NORESPONSE = "NORESPONSE";
 
   private final String ATTR_NEED_RESP = "0";
 
   private final String ATTR_NO_RESP = "1";
 
   private final String ATTR_NODB = "NODATABASE";
 
   private final String ATTR_SHORT_DB_CONN = "0";
 
   private final String ATTR_NO_DB_CONN = "1";
 
   private final String ATTR_LONG_DB_CONN = "2";
 
   private final String ATTR_INTGTYPE = "INTEGTYPE";
 
   private final String ATTR_INTGTYPE_NO = "0";
 
   private final String ATTR_INTGTYPE_HAND = "1";
 
   private final String ATTR_INTGTYPE_SYS = "2";
 
   private final String ATTR_INTGTYPE_RDR = "6";
 
   private final String ATTR_INTGTYPE_CCL = "9";
 
   private final String ATTR_MONITOR = "MONITOR";
 
   private final String ATTR_MONITOR_NO = "0";
 
   private final String ATTR_MONITOR_YES = "1";
 
   private final String TXNTYP_NORMAL = "N";
 
   private final String TXNTYP_CRCT = "C";
 
   private final String TXNTYP_RSND = "R";
 
   private final String TXNTYP_CCLD = "D";
 
   private HashMap map = new HashMap(5);
 
   public HiAttributesHelper() {
     this.map.put("INTEGRITY", "0");
     this.map.put("MONITOR", "0");
     this.map.put("INTEGTYPE", "0");
     this.map.put("NORESPONSE", "0");
     this.map.put("NOCHK", "0");
   }
 
   public void put(String name, String value) {
     this.map.put(name.toUpperCase(), value);
   }
 
   public String getName() {
     String returnString = (String)this.map.get("NAME");
     return returnString;
   }
 
   public HashMap getAttributesInfo() {
     return this.map;
   }
 
   public String integrity()
   {
     return ((String)this.map.get("INTEGRITY"));
   }
 
   public boolean isNoIntegrity()
   {
     return StringUtils.equals(integrity(), "0");
   }
 
   public boolean isSysCrct()
   {
     return StringUtils.equals(integrity(), "1");
   }
 
   public boolean isCndCrct()
   {
     return StringUtils.equals(integrity(), "2");
   }
 
   public boolean isSysRsnd()
   {
     return StringUtils.equals(integrity(), "6");
   }
 
   public boolean isCndRsnd()
   {
     return StringUtils.equals(integrity(), "7");
   }
 
   public String integtype()
   {
     return ((String)this.map.get("INTEGTYPE"));
   }
 
   public boolean isIntegtypeNo()
   {
     return StringUtils.equals(integtype(), "0");
   }
 
   public boolean isIntegtypeHand()
   {
     return StringUtils.equals(integtype(), "1");
   }
 
   public boolean isIntegtypeSys()
   {
     return StringUtils.equals(integtype(), "2");
   }
 
   public boolean isIntegtypeRDR()
   {
     return StringUtils.equals(integtype(), "6");
   }
 
   public boolean isIntegtypeCCL()
   {
     return StringUtils.equals(integtype(), "9");
   }
 
   public String nochk()
   {
     return ((String)this.map.get("NOCHK"));
   }
 
   public boolean isNoChk() {
     String value = nochk();
 
     return (!(StringUtils.equals(value, "1")));
   }
 
   public String noresponse()
   {
     return ((String)this.map.get("NORESPONSE"));
   }
 
   public boolean isNoResponse() {
     String value = noresponse();
 
     return (!(StringUtils.equals(value, "1")));
   }
 
   public String nodatabase()
   {
     return ((String)this.map.get("NODATABASE"));
   }
 
   public boolean isShortDbConn()
   {
     return StringUtils.equals(nodatabase(), "0");
   }
 
   public boolean isNoDbConn()
   {
     return StringUtils.equals(nodatabase(), "1");
   }
 
   public boolean isLongDbConn()
   {
     return StringUtils.equals(nodatabase(), "2");
   }
 
   public String code()
   {
     return ((String)this.map.get("CODE"));
   }
 
   public int interval()
   {
     String value = (String)this.map.get("INTERVAL");
     return NumberUtils.toInt(value, -1);
   }
 
   public int timeout()
   {
     String value = (String)this.map.get("TIMEOUT");
     return NumberUtils.toInt(value, -1);
   }
 
   public int maxtimes()
   {
     String value = (String)this.map.get("MAXTIMES");
     return NumberUtils.toInt(value, -1);
   }
 
   public String objsvr()
   {
     return ((String)this.map.get("OBJSVR"));
   }
 
   public String monitor()
   {
     return ((String)this.map.get("MONITOR"));
   }
 
   public boolean isMonitor() {
     String value = monitor();
     return StringUtils.equals(value, "1");
   }
 
   public static void setAttribute(HiContext ctx, HiAttributesHelper attr) {
     ctx.setProperty("__ATTRIBUTE", attr);
   }
 
   public static HiAttributesHelper getAttribute(HiContext ctx)
   {
     HiAttributesHelper attr = (HiAttributesHelper)ctx.getProperty("__ATTRIBUTE");
     if (attr == null) {
       attr = new HiAttributesHelper();
       setAttribute(ctx, attr);
     }
     return attr;
   }
 
   public String getTxnTyp()
   {
     if (isIntegtypeNo()) {
       super.getClass(); return "N"; }
     if (isIntegtypeRDR()) {
       super.getClass(); return "R";
     }
     super.getClass(); return "C";
   }
 
   public boolean isNormalTxn() {
     super.getClass(); return StringUtils.equals(getTxnTyp(), "N");
   }
 
   public boolean isRSNDTxn() {
     super.getClass(); return StringUtils.equals(getTxnTyp(), "R");
   }
 
   public boolean isCRCTTxn() {
     super.getClass(); return StringUtils.equals(getTxnTyp(), "C");
   }
 
   public String toString() {
     return this.map.toString();
   }
 }