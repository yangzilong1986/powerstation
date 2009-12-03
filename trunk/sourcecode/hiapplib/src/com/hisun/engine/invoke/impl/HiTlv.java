/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.cfg.HiTlvItem;
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.engine.HiITFEngineModel;
/*     */ import com.hisun.engine.invoke.HiStrategyFactory;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSysException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiConvHelper;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiTlv extends HiEngineModel
/*     */ {
/*     */   private String tag;
/*  37 */   private boolean necessary = true;
/*     */ 
/*  39 */   private String value = null;
/*     */ 
/*  41 */   private HiExpression exp = null;
/*     */ 
/*  43 */   private boolean isInit = false;
/*     */   private Map cfgMap;
/*     */   HiTlvItem tlvItem;
/*     */   private String pro_dll;
/*     */   private String pro_func;
/*  50 */   private HiMethodItem pro_method = null;
/*     */ 
/*  52 */   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */ 
/*  54 */   private final HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public HiTlv() throws HiException
/*     */   {
/*  58 */     initAtt();
/*     */   }
/*     */ 
/*     */   public void initAtt() throws HiException {
/*  62 */     if (this.logger.isDebugEnabled()) {
/*  63 */       this.logger.debug("initAtt() - start");
/*     */     }
/*  65 */     HiContext context = HiContext.getCurrentContext();
/*  66 */     HiITFEngineModel itemAttribute = HiITFEngineModel.getItemAttribute(context);
/*     */ 
/*  68 */     if (itemAttribute != null) {
/*  69 */       this.necessary = itemAttribute.is_necessary();
/*     */ 
/*  71 */       if (this.logger.isDebugEnabled()) {
/*  72 */         this.logger.debug("TLV继承属性:necessary[" + this.necessary + "]");
/*     */       }
/*     */     }
/*     */ 
/*  76 */     if (this.logger.isDebugEnabled())
/*  77 */       this.logger.debug("initAtt() - end");
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  82 */     return "Tlv";
/*     */   }
/*     */ 
/*     */   public String getTag() {
/*  86 */     return this.tag;
/*     */   }
/*     */ 
/*     */   public void setTag(String tag) {
/*  90 */     this.tag = tag;
/*  91 */     if (this.logger.isDebugEnabled())
/*  92 */       this.logger.debug("setTag TAG[" + tag + "]");
/*     */   }
/*     */ 
/*     */   public void setNecessary(String necessary)
/*     */   {
/*  97 */     if (StringUtils.equalsIgnoreCase(necessary, "no"))
/*     */     {
/*  99 */       this.necessary = false;
/*     */     }
/* 101 */     if (this.logger.isDebugEnabled())
/* 102 */       this.logger.debug("setNecessary TAG[" + this.tag + "]");
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 107 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(String value) {
/* 111 */     this.value = value;
/* 112 */     if (this.logger.isDebugEnabled())
/* 113 */       this.logger.debug("setValue TAG[" + this.tag + "] ");
/*     */   }
/*     */ 
/*     */   public void setExpression(String expression)
/*     */   {
/* 118 */     this.exp = HiExpFactory.createExp(expression);
/* 119 */     if (this.logger.isDebugEnabled())
/* 120 */       this.logger.debug("setExpression TAG[" + this.tag + "]");
/*     */   }
/*     */ 
/*     */   public void setPro_dll(String pro_dll)
/*     */   {
/* 125 */     if (this.logger.isDebugEnabled()) {
/* 126 */       this.logger.debug("setPro_dll(String) - start");
/*     */     }
/*     */ 
/* 129 */     this.pro_dll = pro_dll;
/*     */ 
/* 131 */     if (this.logger.isDebugEnabled())
/* 132 */       this.logger.debug("setPro_dll(String) - end");
/*     */   }
/*     */ 
/*     */   public void setPro_func(String pro_func)
/*     */   {
/* 137 */     if (this.logger.isDebugEnabled()) {
/* 138 */       this.logger.debug("setPro_func(String) - start");
/*     */     }
/*     */ 
/* 141 */     this.pro_func = pro_func;
/*     */ 
/* 143 */     if (this.logger.isDebugEnabled())
/* 144 */       this.logger.debug("setPro_func(String) - end");
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 149 */     return super.toString() + ":tag[" + this.tag + "]";
/*     */   }
/*     */ 
/*     */   public void loadAfter() throws HiException {
/* 153 */     if (this.logger.isDebugEnabled()) {
/* 154 */       this.logger.debug("loadAfter() - start");
/*     */     }
/*     */ 
/* 157 */     if (StringUtils.isEmpty(this.tag))
/*     */     {
/* 159 */       throw new HiException("231508", this.tag);
/*     */     }
/*     */ 
/* 163 */     if ((StringUtils.isNotBlank(this.pro_dll)) && (StringUtils.isNotBlank(this.pro_func))) {
/* 164 */       this.pro_method = HiItemHelper.getMethod(this.pro_dll, this.pro_func);
/*     */     }
/*     */ 
/* 167 */     HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiByteStategy");
/*     */ 
/* 170 */     if (this.logger.isDebugEnabled())
/* 171 */       this.logger.debug("loadAfter() - end ");
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException
/*     */   {
/*     */     try {
/* 177 */       HiMessage msg = ctx.getCurrentMsg();
/* 178 */       Logger log = HiLog.getLogger(msg);
/* 179 */       if (log.isInfoEnabled()) {
/* 180 */         log.info(this.sm.getString("HiTlvProcess.process00", HiEngineUtilities.getCurFlowStep(), this.tag));
/*     */       }
/*     */ 
/* 184 */       doProcess(ctx);
/*     */     } catch (HiException e) {
/* 186 */       throw e;
/*     */     } catch (Throwable te) {
/* 188 */       throw new HiSysException("231512", this.tag, te);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doProcess(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     HiByteBuffer tlvBody;
/* 194 */     HiMessage msg = ctx.getCurrentMsg();
/* 195 */     Logger log = HiLog.getLogger(msg);
/* 196 */     if (log.isDebugEnabled()) {
/* 197 */       log.debug("doProcess(HiMessageContext) - start.\nTLV process start.");
/*     */     }
/*     */ 
/* 202 */     initCfgNode(ctx);
/*     */ 
/* 205 */     HiETF etfBody = msg.getETFBody();
/* 206 */     if (this.tlvItem.is_group)
/*     */     {
/* 208 */       int repeatNum = getRepeat_num(msg, this.tlvItem.repeat_name);
/* 209 */       if (this.tlvItem.node_type == 0)
/*     */       {
/* 211 */         for (int i = 1; i <= repeatNum; ++i)
/*     */         {
/* 213 */           if (log.isInfoEnabled()) {
/* 214 */             log.info(this.sm.getString("HiTlvProcess.doProcess1", this.tlvItem.tag, this.tlvItem.etf_name + "_" + i));
/*     */           }
/*     */ 
/* 217 */           formatEtf(ctx, msg, etfBody, this.tlvItem.etf_name + "_" + i);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 226 */         for (int i = 1; i <= repeatNum; ++i)
/*     */         {
/* 228 */           if (log.isInfoEnabled()) {
/* 229 */             log.info(this.sm.getString("HiTlvProcess.doProcess1", this.tlvItem.tag, this.tlvItem.etf_name + "_" + i));
/*     */           }
/*     */ 
/* 233 */           HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
/* 234 */           String curEtfLvl = HiItemHelper.getCurEtfLevel(msg);
/*     */ 
/* 236 */           resetMsgState(msg, new HiByteBuffer(128));
/* 237 */           HiItemHelper.setCurEtfLevel(msg, curEtfLvl + this.tlvItem.etf_name + "_" + i + ".");
/*     */ 
/* 239 */           super.process(ctx);
/*     */ 
/* 241 */           tlvBody = HiItemHelper.getPlainText(msg);
/*     */ 
/* 244 */           addPlainNode(msg, plainBody, tlvBody);
/*     */ 
/* 246 */           resetMsgState(msg, plainBody);
/* 247 */           HiItemHelper.setCurEtfLevel(msg, curEtfLvl);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 253 */     else if (this.tlvItem.node_type == 0)
/*     */     {
/* 256 */       formatEtf(ctx, msg, etfBody, this.tlvItem.etf_name);
/*     */     }
/*     */     else
/*     */     {
/*     */       HiByteBuffer plainBody;
/* 258 */       if (this.tlvItem.node_type == 1)
/*     */       {
/* 260 */         if (log.isInfoEnabled()) {
/* 261 */           log.info(this.sm.getString("HiTlvProcess.doProcess0", this.tlvItem.tag));
/*     */         }
/*     */ 
/* 265 */         plainBody = HiItemHelper.getPlainText(msg);
/* 266 */         String curEtfLvl = HiItemHelper.getCurEtfLevel(msg);
/*     */ 
/* 268 */         resetMsgState(msg, new HiByteBuffer(128));
/* 269 */         HiItemHelper.setCurEtfLevel(msg, curEtfLvl + this.tlvItem.etf_name + ".");
/*     */ 
/* 271 */         super.process(ctx);
/*     */ 
/* 273 */         tlvBody = HiItemHelper.getPlainText(msg);
/*     */ 
/* 276 */         addPlainNode(msg, plainBody, tlvBody);
/*     */ 
/* 278 */         resetMsgState(msg, plainBody);
/* 279 */         HiItemHelper.setCurEtfLevel(msg, curEtfLvl);
/*     */       }
/* 281 */       else if (this.tlvItem.node_type == 2)
/*     */       {
/* 283 */         if (log.isInfoEnabled()) {
/* 284 */           log.info(this.sm.getString("HiTlvProcess.doProcess0", this.tlvItem.tag));
/*     */         }
/*     */ 
/* 287 */         plainBody = HiItemHelper.getPlainText(msg);
/*     */ 
/* 290 */         resetMsgState(msg, new HiByteBuffer(128));
/*     */ 
/* 292 */         super.process(ctx);
/*     */ 
/* 294 */         HiByteBuffer tlvBody = HiItemHelper.getPlainText(msg);
/*     */ 
/* 297 */         addPlainNode(msg, plainBody, tlvBody);
/*     */ 
/* 299 */         resetMsgState(msg, plainBody);
/*     */       }
/*     */     }
/*     */ 
/* 303 */     if (log.isDebugEnabled())
/* 304 */       log.debug("doProcess(HiMessage, HiMessageContext) - end");
/*     */   }
/*     */ 
/*     */   private void formatEtf(HiMessageContext ctx, HiMessage msg, HiETF etfBody, String etf_name)
/*     */     throws HiException
/*     */   {
/* 310 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 312 */     String itemVal = HiItemHelper.getEtfItem(msg, etf_name);
/* 313 */     if (itemVal == null)
/*     */     {
/* 316 */       if (this.value != null)
/*     */       {
/* 319 */         itemVal = this.value;
/* 320 */         if (log.isInfoEnabled())
/* 321 */           log.info(this.sm.getString("HiItem.formatEtf3", etf_name, itemVal));
/*     */       }
/* 323 */       else if (!(this.necessary)) {
/* 324 */         if (log.isInfoEnabled()) {
/* 325 */           log.warn(this.sm.getString("HiTlvProcess.formatEtf1", etf_name));
/*     */         }
/* 327 */         itemVal = "";
/*     */       }
/*     */       else
/*     */       {
/* 331 */         throw new HiException("213122", etf_name, HiItemHelper.getCurEtfLevel(msg) + etf_name);
/*     */       }
/*     */ 
/* 336 */       if (this.exp != null)
/*     */       {
/* 338 */         HiItemHelper.addEtfItem(msg, etf_name, itemVal);
/*     */       }
/*     */     }
/* 341 */     else if (log.isInfoEnabled()) {
/* 342 */       log.info(this.sm.getString("HiItem.formatEtf2", etf_name, itemVal));
/*     */     }
/*     */ 
/* 347 */     if (this.exp != null) {
/*     */       try {
/* 349 */         itemVal = HiItemHelper.execExpression(msg, this.exp);
/*     */       } catch (HiException e) {
/* 351 */         if (!(this.necessary)) {
/* 352 */           log.warn("exp:" + this.exp + "计算失败!" + e.getMessage());
/* 353 */           itemVal = "";
/*     */         } else {
/* 355 */           throw e;
/*     */         }
/*     */       }
/*     */     }
/* 359 */     byte[] valBytes = itemVal.getBytes();
/*     */ 
/* 361 */     if (this.pro_method != null) {
/* 362 */       valBytes = HiItemHelper.execMethod(this.pro_method, valBytes, log);
/*     */     }
/*     */ 
/* 365 */     addPlainItem(msg, valBytes);
/*     */   }
/*     */ 
/*     */   private void addPlainNode(HiMessage msg, HiByteBuffer plainBody, HiByteBuffer nodeVal) throws HiException {
/* 369 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 371 */     plainBody.append(getFitTag());
/*     */ 
/* 374 */     plainBody.append(getFitHeadLen(nodeVal.length()));
/*     */ 
/* 377 */     byte[] bytes = nodeVal.getBytes();
/* 378 */     plainBody.append(bytes);
/*     */ 
/* 380 */     if (log.isInfoEnabled())
/* 381 */       log.info(this.sm.getString("HiTlvProcess.addPlainNode0", this.tlvItem.tag, String.valueOf(bytes.length), new String(bytes)));
/*     */   }
/*     */ 
/*     */   private void addPlainItem(HiMessage msg, byte[] valBytes)
/*     */     throws HiException
/*     */   {
/*     */     byte[] bytes;
/* 385 */     Logger log = HiLog.getLogger(msg);
/* 386 */     HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
/*     */ 
/* 390 */     plainBody.append(getFitTag());
/*     */ 
/* 394 */     switch (this.tlvItem.data_type)
/*     */     {
/*     */     case 0:
/* 398 */       String hex = Long.toString(Long.parseLong(new String(valBytes)), 16);
/* 399 */       if (hex.length() % 2 != 0)
/*     */       {
/* 401 */         hex = '0' + hex;
/*     */       }
/* 403 */       bytes = HiConvHelper.ascStr2Bcd(hex);
/* 404 */       break;
/*     */     case 1:
/* 407 */       bytes = HiConvHelper.ascByte2Bcd(valBytes);
/* 408 */       break;
/*     */     case 2:
/* 412 */       bytes = valBytes;
/* 413 */       break;
/*     */     default:
/* 417 */       bytes = valBytes;
/*     */     }
/*     */ 
/* 422 */     plainBody.append(getFitHeadLen(bytes.length));
/*     */ 
/* 425 */     plainBody.append(bytes);
/*     */ 
/* 427 */     if (log.isInfoEnabled())
/* 428 */       log.info(this.sm.getString("HiTlvProcess.addPlainItem0", this.tlvItem.tag, String.valueOf(bytes.length), new String(bytes)));
/*     */   }
/*     */ 
/*     */   private byte[] getFitHeadLen(int itemLen)
/*     */     throws HiException
/*     */   {
/* 435 */     if (this.tlvItem.len_type == 0)
/*     */     {
/* 438 */       strItemLen = Long.toString(itemLen, 16);
/*     */ 
/* 440 */       int itemHeadLen = strItemLen.length();
/* 441 */       if (itemHeadLen % 2 != 0) {
/* 442 */         strItemLen = "0" + strItemLen;
/*     */       }
/*     */ 
/* 445 */       if (itemHeadLen > this.tlvItem.head_len * 2)
/*     */       {
/* 447 */         throw new HiException("213133", this.tag, String.valueOf(itemHeadLen), String.valueOf(this.tlvItem.head_len));
/*     */       }
/* 449 */       if (itemHeadLen < this.tlvItem.head_len * 2) {
/* 450 */         strItemLen = StringUtils.leftPad(strItemLen, this.tlvItem.head_len * 2, "0");
/*     */       }
/*     */ 
/* 453 */       return HiConvHelper.ascStr2Bcd(strItemLen);
/*     */     }
/*     */ 
/* 458 */     String strItemLen = String.valueOf(itemLen);
/*     */ 
/* 460 */     if (strItemLen.length() > this.tlvItem.head_len)
/*     */     {
/* 462 */       throw new HiException("213134", this.tag, String.valueOf(strItemLen.length()), String.valueOf(this.tlvItem.head_len));
/*     */     }
/*     */ 
/* 465 */     if (strItemLen.length() < this.tlvItem.head_len) {
/* 466 */       strItemLen = StringUtils.leftPad(strItemLen, this.tlvItem.head_len, "0");
/*     */     }
/*     */ 
/* 469 */     return strItemLen.getBytes();
/*     */   }
/*     */ 
/*     */   private byte[] getFitTag()
/*     */   {
/* 475 */     switch (this.tlvItem.tag_type)
/*     */     {
/*     */     case 1:
/* 479 */       return HiConvHelper.ascStr2Bcd(this.tag);
/*     */     case 2:
/* 483 */       return this.tag.getBytes();
/*     */     case 0:
/* 487 */       String hex = Integer.toString(Integer.parseInt(this.tag), 16);
/* 488 */       if (hex.length() % 2 != 0)
/*     */       {
/* 490 */         hex = '0' + hex;
/*     */       }
/* 492 */       return HiConvHelper.ascStr2Bcd(hex);
/*     */     }
/*     */ 
/* 495 */     return HiConvHelper.ascStr2Bcd(this.tag);
/*     */   }
/*     */ 
/*     */   private void initCfgNode(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 502 */     if (this.isInit) {
/* 503 */       return;
/*     */     }
/*     */ 
/* 506 */     this.cfgMap = ((Map)ctx.getProperty("TLV_CFG_NODE"));
/* 507 */     if (this.cfgMap == null) {
/* 508 */       throw new HiException("231510", "");
/*     */     }
/*     */ 
/* 512 */     this.tlvItem = ((HiTlvItem)this.cfgMap.get(this.tag));
/* 513 */     if (this.tlvItem != null)
/*     */       return;
/* 515 */     throw new HiException("231511", this.tag);
/*     */   }
/*     */ 
/*     */   protected void resetMsgState(HiMessage msg, HiByteBuffer packValue)
/*     */   {
/* 520 */     Logger log = HiLog.getLogger(msg);
/* 521 */     if (log.isDebugEnabled())
/*     */     {
/* 523 */       log.debug("resetMsgState(HiMessage, String, int) - start");
/*     */     }
/*     */ 
/* 526 */     msg.setHeadItem("PlainText", packValue);
/*     */ 
/* 529 */     if (!(log.isDebugEnabled()))
/*     */       return;
/* 531 */     log.debug("resetMsgState(HiMessage, String, int) - end");
/*     */   }
/*     */ 
/*     */   private int getRepeat_num(HiMessage msg, String repeat_name)
/*     */   {
/* 537 */     if (repeat_name == null)
/*     */     {
/* 539 */       return 0;
/*     */     }
/* 541 */     return Integer.parseInt(HiItemHelper.getEtfItem(msg, repeat_name));
/*     */   }
/*     */ }