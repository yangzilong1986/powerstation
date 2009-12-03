/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSysException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiConvHelper;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import com.hisun.util.HiXmlHelper;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiPackTlv extends HiEngineModel
/*     */ {
/*     */   private String field_id;
/*     */   private String must_fields;
/*     */   private String opt_fields;
/*     */   private boolean isInit;
/*     */   private Map tlvNodeMap;
/*     */   private HashSet mustSet;
/*     */   private HashSet optSet;
/*     */   private final Logger logger;
/*     */ 
/*     */   public HiPackTlv()
/*     */   {
/*  42 */     this.isInit = false;
/*     */ 
/*  44 */     this.tlvNodeMap = new HashMap();
/*  45 */     this.mustSet = new HashSet();
/*     */ 
/*  47 */     this.optSet = new HashSet();
/*     */ 
/*  52 */     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  57 */     return "PackTlv";
/*     */   }
/*     */ 
/*     */   public void setMust_fields(String must_fields)
/*     */   {
/*  62 */     this.must_fields = must_fields;
/*     */   }
/*     */ 
/*     */   public void setOpt_fields(String opt_fields)
/*     */   {
/*  67 */     this.opt_fields = opt_fields;
/*     */   }
/*     */ 
/*     */   public void setField_id(String field_id)
/*     */   {
/*  72 */     this.field_id = field_id;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  77 */     return super.toString() + ":PackTlv[" + this.field_id + "]";
/*     */   }
/*     */ 
/*     */   public void loadAfter()
/*     */     throws HiException
/*     */   {
/*  87 */     if (this.logger.isDebugEnabled()) {
/*  88 */       this.logger.debug("loadAfter() - start");
/*     */     }
/*  90 */     if (StringUtils.isEmpty(this.field_id))
/*     */     {
/*  92 */       throw new HiException("213231", "所属域 field_id,不允许为空.");
/*     */     }
/*     */ 
/*  95 */     if (StringUtils.isBlank(this.must_fields))
/*     */     {
/*  97 */       throw new HiException("213232", "必需组包的子域号列表 must_fields,不允许为空.");
/*     */     }
/*  99 */     checkValidField(this.must_fields, true);
/*     */ 
/* 101 */     if (StringUtils.isNotBlank(this.opt_fields))
/*     */     {
/* 103 */       checkValidField(this.opt_fields, false);
/*     */     }
/*     */ 
/* 106 */     if (this.logger.isDebugEnabled())
/* 107 */       this.logger.debug("loadAfter() - end");
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 114 */       HiMessage msg = ctx.getCurrentMsg();
/* 115 */       Logger log = HiLog.getLogger(msg);
/* 116 */       if (log.isInfoEnabled()) {
/* 117 */         log.info(sm.getString("HiTlv.process00", HiEngineUtilities.getCurFlowStep(), this.field_id));
/*     */       }
/*     */ 
/* 120 */       doProcess(ctx);
/*     */     } catch (HiException e) {
/* 122 */       throw e;
/*     */     } catch (Throwable te) {
/* 124 */       throw new HiSysException("213234", this.field_id, te);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doProcess(HiMessageContext ctx) throws HiException {
/* 129 */     HiMessage msg = ctx.getCurrentMsg();
/* 130 */     Logger log = HiLog.getLogger(msg);
/* 131 */     if (log.isDebugEnabled()) {
/* 132 */       log.debug("doProcess(HiMessageContext) - start.\nTlv process start.");
/*     */     }
/*     */ 
/* 136 */     initCfgNode(ctx);
/*     */ 
/* 139 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 140 */     convertToTlv(msg, out);
/*     */ 
/* 143 */     saveTLV(ctx, this.field_id, out.toByteArray());
/*     */ 
/* 145 */     if (log.isInfoEnabled()) {
/* 146 */       log.info(sm.getString("HiTlv.processOk", this.field_id, out.toString()));
/*     */     }
/* 148 */     if (log.isDebugEnabled()) {
/* 149 */       log.debug("Tlv: OK =======================");
/*     */ 
/* 151 */       log.debug("doProcess(HiMessageContext) - end.");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void convertToTlv(HiMessage msg, ByteArrayOutputStream out)
/*     */     throws HiException
/*     */   {
/* 158 */     HiETF etfBody = msg.getETFBody();
/* 159 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 161 */     Iterator it = this.tlvNodeMap.entrySet().iterator();
/*     */ 
/* 170 */     while (it.hasNext())
/*     */     {
/* 172 */       Map.Entry itemEntry = (Map.Entry)it.next();
/* 173 */       String tlvTag = (String)itemEntry.getKey();
/* 174 */       Element tlvNode = (Element)itemEntry.getValue();
/* 175 */       String etfName = tlvNode.attributeValue("etf_name");
/* 176 */       String etfVal = etfBody.getGrandChildValue(etfName);
/*     */ 
/* 178 */       if (etfVal == null)
/*     */       {
/* 180 */         if (!(this.mustSet.contains(tlvTag)))
/*     */           continue;
/* 182 */         throw new HiException("213235", this.field_id, tlvTag, etfName);
/*     */       }
/*     */ 
/* 191 */       fitTlv(etfVal, tlvTag, tlvNode, out);
/*     */ 
/* 193 */       if (!(log.isInfoEnabled()))
/*     */         continue;
/* 195 */       log.info(sm.getString("HiTlv.packTlvOk", etfName, String.valueOf(etfVal.length()), etfVal, tlvTag));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void fitTlv(String value, String tag, Element itemNode, ByteArrayOutputStream out)
/*     */     throws HiException
/*     */   {
/*     */     byte[] valBytes;
/* 220 */     String data_type = itemNode.attributeValue("data_type");
/*     */ 
/* 223 */     if (!(data_type.endsWith("ASCII")))
/*     */     {
/* 225 */       valBytes = HiConvHelper.ascStr2Bcd(value);
/*     */     }
/*     */     else
/*     */     {
/* 229 */       valBytes = value.getBytes();
/*     */     }
/*     */ 
/* 233 */     byte[] tagBytes = HiConvHelper.ascStr2Bcd(tag);
/* 234 */     out.write(tagBytes, 0, tagBytes.length);
/*     */ 
/* 237 */     int valLen = valBytes.length;
/* 238 */     if (valLen < 128)
/*     */     {
/* 241 */       out.write(valLen);
/*     */     }
/* 243 */     else if (valLen < 256)
/*     */     {
/* 246 */       out.write(129);
/* 247 */       out.write(valLen);
/*     */     }
/* 249 */     else if (valLen < 65536)
/*     */     {
/* 252 */       out.write(130);
/*     */ 
/* 254 */       byte[] bp = new byte[2];
/* 255 */       bp[0] = (byte)(valLen >> 8 & 0xFF);
/* 256 */       bp[1] = (byte)(valLen & 0xFF);
/*     */ 
/* 258 */       out.write(bp, 0, 2);
/*     */     }
/*     */     else
/*     */     {
/* 263 */       throw new HiException("213236", tag, String.valueOf(valLen));
/*     */     }
/*     */ 
/* 267 */     out.write(valBytes, 0, valLen);
/*     */   }
/*     */ 
/*     */   private void saveTLV(HiMessageContext ctx, String field_id, byte[] tlvBytes)
/*     */   {
/* 279 */     Map tlvMap = (Map)ctx.getProperty("8583_PACKTLV_VAL");
/* 280 */     if (tlvMap == null)
/*     */     {
/* 282 */       tlvMap = new HashMap();
/* 283 */       ctx.setProperty("8583_PACKTLV_VAL", tlvMap);
/*     */     }
/*     */ 
/* 286 */     tlvMap.put(field_id, tlvBytes);
/*     */   }
/*     */ 
/*     */   private synchronized void initCfgNode(HiMessageContext ctx) throws HiException
/*     */   {
/* 291 */     if (this.isInit)
/*     */     {
/* 293 */       return;
/*     */     }
/*     */ 
/* 296 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 297 */     if (log.isDebugEnabled()) {
/* 298 */       log.debug("first initCfgNode - start.");
/*     */     }
/*     */ 
/* 301 */     Element cfgRoot = (Element)ctx.getProperty("8583_CFG_NODE");
/* 302 */     if (cfgRoot == null)
/*     */     {
/* 304 */       throw new HiException("213222", "");
/*     */     }
/*     */ 
/* 307 */     this.tlvNodeMap.clear();
/*     */ 
/* 309 */     putMapNode(cfgRoot, this.mustSet);
/* 310 */     putMapNode(cfgRoot, this.optSet);
/*     */ 
/* 312 */     this.isInit = true;
/*     */ 
/* 314 */     if (log.isDebugEnabled()) {
/* 315 */       log.debug("must_fields [" + this.must_fields + "]");
/* 316 */       log.debug("opt_fields [" + this.opt_fields + "]");
/* 317 */       log.debug("first initCfgNode - end.");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void putMapNode(Element cfgRoot, Set tlvSet)
/*     */     throws HiException
/*     */   {
/* 327 */     Element itemNode = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", this.field_id);
/* 328 */     if (itemNode == null)
/*     */     {
/* 330 */       throw new HiException("213223", this.field_id);
/*     */     }
/*     */ 
/* 333 */     Iterator it = tlvSet.iterator();
/* 334 */     while (it.hasNext())
/*     */     {
/* 336 */       String tlvTag = (String)it.next();
/*     */ 
/* 338 */       Element tlvNode = HiXmlHelper.selectSingleNode(itemNode, "Tlv", "tag", tlvTag);
/*     */ 
/* 340 */       if (tlvNode == null)
/*     */       {
/* 342 */         throw new HiException("213233", this.field_id, tlvTag);
/*     */       }
/*     */ 
/* 345 */       this.tlvNodeMap.put(tlvTag, tlvNode);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkValidField(String fields, boolean isMust) throws HiException
/*     */   {
/* 351 */     StringTokenizer tokenizer = new StringTokenizer(fields, "|");
/* 352 */     while (tokenizer.hasMoreElements())
/*     */     {
/* 354 */       if (isMust)
/*     */       {
/* 356 */         this.mustSet.add(tokenizer.nextToken());
/*     */       }
/*     */ 
/* 360 */       this.optSet.add(tokenizer.nextToken());
/*     */     }
/*     */   }
/*     */ }