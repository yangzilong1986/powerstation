/*     */ package com.hisun.message;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.pubinterface.HiCloseable;
/*     */ import com.hisun.util.LockableHashtable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiMessageContext extends HiContext
/*     */   implements Cloneable
/*     */ {
/*     */   private HiMessage currentMsg;
/*     */   private Stack _responseMsgs;
/*     */   private Stack parentStack;
/* 447 */   private HiDataBaseUtil databaseUtil = new HiDataBaseUtil();
/*     */ 
/*     */   public static void setCurrentMessageContext(HiMessageContext mc)
/*     */   {
/*  31 */     setCurrentContext(mc);
/*     */   }
/*     */ 
/*     */   public static HiMessageContext getCurrentMessageContext()
/*     */   {
/*  41 */     return ((HiMessageContext)getCurrentContext());
/*     */   }
/*     */ 
/*     */   public HiMessageContext() {
/*  45 */     super("MsgContext");
/*     */   }
/*     */ 
/*     */   public HiMessageContext(HiMessageContext ctx)
/*     */   {
/*  52 */     super("MsgContext");
/*  53 */     this.currentMsg = new HiMessage(ctx.getCurrentMsg());
/*     */ 
/*  58 */     this.parentStack = ((Stack)ctx.parentStack.clone());
/*  59 */     this.parent = ctx.parent;
/*  60 */     this.bag.setParent(this.parent.bag);
/*  61 */     Iterator iter = ctx.bag.entrySet().iterator();
/*     */ 
/*  65 */     while (iter.hasNext()) {
/*  66 */       Map.Entry entry = (Map.Entry)iter.next();
/*  67 */       this.bag.put(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiMessage getCurrentMsg()
/*     */   {
/* 121 */     return this.currentMsg;
/*     */   }
/*     */ 
/*     */   public HiMessage getRequestMsg()
/*     */   {
/* 128 */     return getCurrentMsg();
/*     */   }
/*     */ 
/*     */   public void setRequestMsg(HiMessage msg) {
/* 132 */     setCurrentMsg(msg);
/*     */   }
/*     */ 
/*     */   public HiMessage getResponseMsg()
/*     */   {
/* 138 */     if ((this._responseMsgs == null) || (this._responseMsgs.isEmpty()))
/* 139 */       return getCurrentMsg();
/* 140 */     return ((HiMessage)this._responseMsgs.peek());
/*     */   }
/*     */ 
/*     */   public HiMessage popResponseMsg() {
/* 144 */     if ((this._responseMsgs == null) || (this._responseMsgs.isEmpty()))
/* 145 */       return null;
/* 146 */     return ((HiMessage)this._responseMsgs.pop());
/*     */   }
/*     */ 
/*     */   public void setResponseMsg(HiMessage msg) {
/* 150 */     if (this._responseMsgs == null) {
/* 151 */       this._responseMsgs = new Stack();
/*     */     }
/* 153 */     this._responseMsgs.push(msg);
/*     */   }
/*     */ 
/*     */   public void setCurrentMsg(HiMessage currentMsg)
/*     */   {
/* 160 */     this.currentMsg = currentMsg;
/*     */   }
/*     */ 
/*     */   public void pushParent(HiContext parent)
/*     */   {
/* 168 */     if (this.parentStack == null) {
/* 169 */       this.parentStack = new Stack();
/*     */     }
/*     */ 
/* 172 */     if (this.parent != null)
/* 173 */       this.parentStack.push(this.parent);
/* 174 */     this.parent = parent;
/* 175 */     this.bag.setParent(parent.bag);
/*     */   }
/*     */ 
/*     */   public HiContext popParent()
/*     */   {
/* 182 */     HiContext old = this.parent;
/*     */ 
/* 184 */     if ((this.parentStack == null) || (this.parentStack.isEmpty())) {
/* 185 */       this.parent = null;
/* 186 */       return old;
/*     */     }
/*     */ 
/* 189 */     this.parent = ((HiContext)this.parentStack.pop());
/* 190 */     this.bag.setParent(this.parent.bag);
/* 191 */     return old;
/*     */   }
/*     */ 
/*     */   public void setBaseSource(String strKey, Object strValue)
/*     */   {
/* 229 */     if (strValue == null) {
/* 230 */       strValue = "";
/*     */     }
/* 232 */     setProperty(strKey, strValue);
/*     */   }
/*     */ 
/*     */   public Object getBaseSource(String strKey)
/*     */   {
/* 242 */     return getProperty(strKey);
/*     */   }
/*     */ 
/*     */   public Object removeBaseSource(String key)
/*     */   {
/* 251 */     return this.bag.remove(key.toUpperCase());
/*     */   }
/*     */ 
/*     */   public void setPara(String strKey, Object strValue)
/*     */   {
/* 261 */     setProperty("@PARA", strKey, strValue);
/*     */   }
/*     */ 
/*     */   public Object getPara(String strKey)
/*     */   {
/* 270 */     return getProperty("@PARA", strKey);
/*     */   }
/*     */ 
/*     */   public String getETFValue(HiETF etfRoot, String strKey) {
/* 274 */     return etfRoot.getGrandChildValue(strKey);
/*     */   }
/*     */ 
/*     */   public Object getSpecExpre(HiETF etfRoot, String strKey)
/*     */   {
/*     */     String strName;
/* 278 */     if (strKey.startsWith("@ETF")) {
/* 279 */       strName = StringUtils.substringAfter(strKey, "@ETF.");
/*     */ 
/* 281 */       return getETFValue(etfRoot, strName); }
/* 282 */     if (strKey.startsWith("$")) {
/* 283 */       strName = StringUtils.substringAfter(strKey, "$");
/* 284 */       return getETFValue(etfRoot, strName); }
/* 285 */     if (strKey.startsWith("@BAS")) {
/* 286 */       strName = StringUtils.substringAfter(strKey, "@BAS.");
/*     */ 
/* 288 */       return getBaseSource(strName); }
/* 289 */     if (strKey.startsWith("~")) {
/* 290 */       strName = StringUtils.substringAfter(strKey, "~");
/* 291 */       return getBaseSource(strName); }
/* 292 */     if (strKey.startsWith("@MSG")) {
/* 293 */       strName = StringUtils.substringAfter(strKey, "@MSG.");
/*     */ 
/* 295 */       return this.currentMsg.getHeadItem(strName); }
/* 296 */     if (strKey.startsWith("%")) {
/* 297 */       strName = StringUtils.substringAfter(strKey, "%");
/* 298 */       return this.currentMsg.getHeadItem(strName); }
/* 299 */     if (strKey.startsWith("@PARA")) {
/* 300 */       strName = StringUtils.substringAfter(strKey, "@PARA.");
/* 301 */       return getPara(strName);
/*     */     }
/* 303 */     return getETFValue(etfRoot, strKey);
/*     */   }
/*     */ 
/*     */   public String getBCFG(String strKey)
/*     */     throws HiException
/*     */   {
/* 315 */     HashMap bcfgMap = (HashMap)getProperty("ROOT.BCFG");
/*     */ 
/* 317 */     if (bcfgMap == null) {
/* 318 */       return null;
/*     */     }
/* 320 */     return ((String)bcfgMap.get(strKey.toUpperCase()));
/*     */   }
/*     */ 
/*     */   public String getJnlTable()
/*     */   {
/* 394 */     return getStrProp("__TXNJNLTBL");
/*     */   }
/*     */ 
/*     */   public HashMap getTableMetaData(String strKey)
/*     */     throws HiException
/*     */   {
/* 403 */     HashMap map = null;
/*     */ 
/* 405 */     if (containsProperty("TABLEDECLARE." + strKey)) {
/* 406 */       map = (HashMap)getProperty("TABLEDECLARE", strKey);
/*     */     }
/*     */     else {
/* 409 */       HiDataBaseUtil data = getDataBaseUtil();
/* 410 */       map = data.getTableMetaData(strKey, data.getConnection());
/* 411 */       this.parent.setProperty("TABLEDECLARE", strKey, map);
/*     */     }
/*     */ 
/* 414 */     return map;
/*     */   }
/*     */ 
/*     */   public void setJnlTable(String TableName)
/*     */   {
/* 423 */     setProperty("__TXNJNLTBL", TableName);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 450 */     if (this.databaseUtil != null) {
/* 451 */       this.databaseUtil.close();
/* 452 */       this.databaseUtil = null;
/*     */     }
/*     */ 
/* 455 */     Iterator iter = this.bag.values().iterator();
/* 456 */     while (iter.hasNext()) {
/* 457 */       Object o = iter.next();
/* 458 */       if (o instanceof HiCloseable)
/*     */         try {
/* 460 */           ((HiCloseable)o).close();
/*     */         }
/*     */         catch (HiException e)
/*     */         {
/*     */         }
/*     */     }
/* 466 */     this.bag.clear();
/*     */   }
/*     */ 
/*     */   public HiDataBaseUtil getDataBaseUtil() {
/* 470 */     return this.databaseUtil;
/*     */   }
/*     */ 
/*     */   public void setDataBaseUtil(HiDataBaseUtil databaseUtil) {
/* 474 */     this.databaseUtil = databaseUtil;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 478 */     StringBuffer result = new StringBuffer();
/* 479 */     result.append("\nmsg:" + this.currentMsg.toString());
/* 480 */     result.append("\nparentStack:" + this.parentStack);
/* 481 */     result.append("\nparent: " + this.parent);
/* 482 */     result.append("\nbag: " + this.bag);
/* 483 */     result.append("\ndatabaseUtil:" + this.databaseUtil);
/* 484 */     return result.toString();
/*     */   }
/*     */ }