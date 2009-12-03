/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineUtilities;
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
/*     */ import com.hisun.util.HiXmlHelper;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiPack8583 extends HiEngineModel
/*     */ {
/*     */   private String type_code;
/*     */   private HiExpression type_code_expr;
/*     */   private String must_fields;
/*     */   private String opt_fields;
/*     */   private int bitmap_len;
/*     */   private boolean pack_header;
/*     */   private String hLen_pos;
/*     */   private int header_len;
/*     */   private String hLen_name;
/*     */   private String mLen_pos;
/*     */   private String mLen_name;
/*     */   private String pro_dll;
/*     */   private String pro_func;
/*     */   private HiMethodItem pro_method;
/*     */   private String pro_fields;
/*     */   private boolean isInit;
/*     */   private Element msgTypCodNode;
/*     */   private Element headerRoot;
/*     */   private Map itemNodeMap;
/*     */   private HashSet mustSet;
/*     */   private HashSet optSet;
/*     */   private HashSet proSet;
/*     */   private final int bitMapLen = 128;
/*     */   private final Logger logger;
/*     */ 
/*     */   public HiPack8583()
/*     */   {
/*  53 */     this.bitmap_len = 0;
/*     */ 
/*  55 */     this.pack_header = false;
/*     */ 
/*  57 */     this.hLen_pos = null;
/*  58 */     this.header_len = -1;
/*  59 */     this.hLen_name = null;
/*     */ 
/*  62 */     this.mLen_pos = null;
/*  63 */     this.mLen_name = null;
/*     */ 
/*  69 */     this.pro_method = null;
/*     */ 
/*  73 */     this.isInit = false;
/*     */ 
/*  76 */     this.headerRoot = null;
/*     */ 
/*  78 */     this.itemNodeMap = new TreeMap();
/*  79 */     this.mustSet = new HashSet();
/*     */ 
/*  81 */     this.optSet = new HashSet();
/*  82 */     this.proSet = new HashSet();
/*  83 */     this.bitMapLen = 128;
/*     */ 
/*  88 */     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */   }
/*     */ 
/*     */   public String getNodeName() {
/*  92 */     return "Pack8583";
/*     */   }
/*     */ 
/*     */   public void setMust_fields(String must_fields) {
/*  96 */     this.must_fields = must_fields;
/*     */   }
/*     */ 
/*     */   public void setOpt_fields(String opt_fields) {
/* 100 */     this.opt_fields = opt_fields;
/*     */   }
/*     */ 
/*     */   public void setPack_header(String pack_header) {
/* 104 */     if (StringUtils.equals(pack_header, "1"))
/* 105 */       this.pack_header = true;
/*     */   }
/*     */ 
/*     */   public void setHlen_pos(String hLen_pos)
/*     */   {
/* 110 */     this.hLen_pos = hLen_pos;
/*     */   }
/*     */ 
/*     */   public void setHeader_len(String header_len) {
/* 114 */     this.header_len = Integer.parseInt(header_len);
/*     */   }
/*     */ 
/*     */   public void setMlen_pos(String mLen_pos) {
/* 118 */     this.mLen_pos = mLen_pos;
/*     */   }
/*     */ 
/*     */   public void setType_code(String type_code) {
/* 122 */     this.type_code = type_code;
/* 123 */     this.type_code_expr = HiExpFactory.createExp(type_code);
/*     */   }
/*     */ 
/*     */   public void setBitmap_len(String bit_len) throws HiException {
/*     */     try {
/* 128 */       this.bitmap_len = Integer.parseInt(bit_len.trim());
/*     */     } catch (Exception e) {
/* 130 */       this.logger.error("Pack8583 setBitmap_len(String)", e);
/* 131 */       throw new HiException("213237", bit_len, e);
/*     */     }
/* 133 */     if ((this.bitmap_len != 128) && (this.bitmap_len != 64)) {
/* 134 */       this.logger.error("Pack8583 setBitmap_len(String) 只允许64或128");
/*     */ 
/* 136 */       throw new HiException("213237", bit_len);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setPro_dll(String pro_dll) {
/* 141 */     if (this.logger.isDebugEnabled()) {
/* 142 */       this.logger.debug("setPro_dll(String) - start");
/*     */     }
/*     */ 
/* 145 */     this.pro_dll = pro_dll;
/*     */ 
/* 147 */     if (this.logger.isDebugEnabled())
/* 148 */       this.logger.debug("setPro_dll(String) - end");
/*     */   }
/*     */ 
/*     */   public void setPro_func(String pro_func)
/*     */   {
/* 153 */     if (this.logger.isDebugEnabled()) {
/* 154 */       this.logger.debug("setPro_func(String) - start");
/*     */     }
/*     */ 
/* 157 */     this.pro_func = pro_func;
/*     */ 
/* 159 */     if (this.logger.isDebugEnabled())
/* 160 */       this.logger.debug("setPro_func(String) - end");
/*     */   }
/*     */ 
/*     */   public void setPro_fields(String pro_fields)
/*     */   {
/* 165 */     if (this.logger.isDebugEnabled()) {
/* 166 */       this.logger.debug("setPro_fields(String) - start");
/*     */     }
/*     */ 
/* 169 */     this.pro_fields = pro_fields;
/*     */ 
/* 171 */     if (this.logger.isDebugEnabled())
/* 172 */       this.logger.debug("setPro_fields(String) - end");
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 177 */     return super.toString() + ":type_code[" + this.type_code + "]";
/*     */   }
/*     */ 
/*     */   public void loadAfter()
/*     */     throws HiException
/*     */   {
/* 186 */     if (this.logger.isDebugEnabled()) {
/* 187 */       this.logger.debug("loadAfter() - start");
/*     */     }
/* 189 */     if (StringUtils.isEmpty(this.type_code)) {
/* 190 */       throw new HiException("213220", "消息类型域 type_code,不允许为空.");
/*     */     }
/*     */ 
/* 194 */     if (StringUtils.isBlank(this.must_fields)) {
/* 195 */       throw new HiException("213221", "必需组包的域号列表 must_fields,不允许为空.");
/*     */     }
/*     */ 
/* 198 */     checkValidField(this.must_fields, this.mustSet);
/*     */ 
/* 200 */     if (StringUtils.isNotBlank(this.opt_fields)) {
/* 201 */       checkValidField(this.opt_fields, this.optSet);
/*     */     }
/*     */ 
/* 204 */     if (StringUtils.isNotBlank(this.pro_fields)) {
/* 205 */       checkValidField(this.pro_fields, this.proSet);
/*     */     }
/*     */ 
/* 209 */     if ((StringUtils.isNotBlank(this.pro_dll)) && (StringUtils.isNotBlank(this.pro_func))) {
/* 210 */       this.pro_method = HiItemHelper.getMethod(this.pro_dll, this.pro_func);
/*     */     }
/*     */ 
/* 213 */     HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiByteStategy");
/*     */ 
/* 216 */     if (this.logger.isDebugEnabled())
/* 217 */       this.logger.debug("loadAfter() - end");
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 224 */       super.process(ctx);
/*     */ 
/* 226 */       HiMessage msg = ctx.getCurrentMsg();
/* 227 */       Logger log = HiLog.getLogger(msg);
/* 228 */       if (log.isInfoEnabled()) {
/* 229 */         log.info(sm.getString("HiPack8583.process00", HiEngineUtilities.getCurFlowStep(), this.type_code));
/*     */       }
/*     */ 
/* 233 */       doProcess(ctx);
/*     */     } catch (HiException e) {
/* 235 */       throw e;
/*     */     } catch (Throwable te) {
/* 237 */       throw new HiSysException("213229", this.type_code, te);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doProcess(HiMessageContext ctx) throws HiException
/*     */   {
/* 243 */     HiMessage msg = ctx.getCurrentMsg();
/* 244 */     Logger log = HiLog.getLogger(msg);
/* 245 */     if (log.isDebugEnabled()) {
/* 246 */       log.debug("doProcess(HiMessageContext) - start.\nPack8583 process start.");
/*     */     }
/*     */ 
/* 251 */     initCfgNode(ctx);
/*     */ 
/* 253 */     char[] bitMapByte = StringUtils.repeat("0", 128).toCharArray();
/*     */ 
/* 255 */     ByteArrayOutputStream out = new ByteArrayOutputStream(128);
/*     */ 
/* 258 */     convertToPlain(ctx, out, bitMapByte);
/*     */ 
/* 261 */     HiByteBuffer plainOut = null;
/* 262 */     Object plainBody = HiItemHelper.getPlainObject(msg);
/* 263 */     if (plainBody instanceof HiByteBuffer)
/* 264 */       plainOut = (HiByteBuffer)plainBody;
/* 265 */     else if (plainBody instanceof byte[])
/* 266 */       plainOut = new HiByteBuffer((byte[])(byte[])plainBody);
/*     */     else {
/* 268 */       throw new HiException("213230", this.type_code);
/*     */     }
/*     */ 
/* 272 */     int bodyLen = 0;
/*     */ 
/* 277 */     byte[] msgTypByte = HiPack8583Helper.fitPlain(this.type_code_expr.getValue(ctx).getBytes(), this.msgTypCodNode, log);
/* 278 */     bodyLen = msgTypByte.length;
/*     */ 
/* 281 */     String bitMap = null;
/* 282 */     if (this.bitmap_len == 128) {
/* 283 */       bitMapByte[0] = '1';
/* 284 */       bitMap = HiConvHelper.binary2hex(new String(bitMapByte));
/* 285 */     } else if (this.bitmap_len == 64) {
/* 286 */       bitMapByte[0] = '0';
/* 287 */       bitMap = HiConvHelper.binary2hex(new String(bitMapByte, 0, 64));
/*     */     }
/* 290 */     else if (bitMapByte[0] == '0') {
/* 291 */       bitMap = HiConvHelper.binary2hex(new String(bitMapByte, 0, 64));
/*     */     } else {
/* 293 */       bitMap = HiConvHelper.binary2hex(new String(bitMapByte));
/*     */     }
/*     */ 
/* 297 */     byte[] outMapByte = HiConvHelper.ascStr2Bcd(bitMap);
/* 298 */     bodyLen += outMapByte.length;
/*     */ 
/* 301 */     byte[] outByte = out.toByteArray();
/* 302 */     bodyLen += outByte.length;
/*     */ 
/* 305 */     if (this.pack_header) {
/* 306 */       ByteArrayOutputStream hOut = new ByteArrayOutputStream(64);
/* 307 */       packHeader(ctx, hOut, bodyLen, log);
/* 308 */       byte[] hOutByte = hOut.toByteArray();
/*     */ 
/* 311 */       plainOut.append(hOutByte, 0, hOutByte.length);
/*     */     }
/*     */ 
/* 315 */     plainOut.append(msgTypByte, 0, msgTypByte.length);
/* 316 */     plainOut.append(outMapByte, 0, outMapByte.length);
/* 317 */     plainOut.append(outByte, 0, outByte.length);
/*     */ 
/* 320 */     msg.setHeadItem("PlainText", plainOut);
/*     */ 
/* 322 */     if (log.isInfoEnabled()) {
/* 323 */       log.info(sm.getString("HiPack8583.processOk", bitMap, plainOut.toString()));
/*     */     }
/*     */ 
/* 327 */     if (log.isDebugEnabled()) {
/* 328 */       log.debug("Pack8583: OK =======================");
/* 329 */       log.debug("Pack8583: bitMap [" + bitMap + "]");
/* 330 */       log.debug("Pack8583: pack [" + plainOut.toString() + "]");
/* 331 */       log.debug("doProcess(HiMessageContext) - end.");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void convertToPlain(HiMessageContext ctx, ByteArrayOutputStream out, char[] bitMapByte) throws HiException
/*     */   {
/* 337 */     HiMessage msg = ctx.getCurrentMsg();
/* 338 */     HiETF etfBody = msg.getETFBody();
/* 339 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 342 */     Map tlvMap = (Map)ctx.getProperty("8583_PACKTLV_VAL");
/*     */ 
/* 344 */     Iterator it = this.itemNodeMap.entrySet().iterator();
/*     */ 
/* 349 */     String type = "ETF";
/*     */ 
/* 352 */     int idx = 0;
/*     */ 
/* 355 */     while (it.hasNext())
/*     */     {
/*     */       String fldVal;
/* 356 */       Map.Entry itemEntry = (Map.Entry)it.next();
/* 357 */       Integer bitIdx = (Integer)itemEntry.getKey();
/* 358 */       idx = bitIdx.intValue() - 1;
/*     */ 
/* 360 */       if ((this.bitmap_len == 64) && (idx >= 64)) {
/*     */         break;
/*     */       }
/*     */ 
/* 364 */       Element itemNode = (Element)itemEntry.getValue();
/*     */ 
/* 366 */       String fldName = bitIdx.toString();
/* 367 */       byte[] valByte = getTLV(tlvMap, fldName);
/* 368 */       if (valByte != null) {
/* 369 */         fldVal = new String(valByte);
/* 370 */         type = "TLV";
/*     */       }
/*     */       else {
/* 373 */         fldName = itemNode.attributeValue("etf_name");
/*     */ 
/* 375 */         fldVal = etfBody.getGrandChildValue(fldName);
/* 376 */         type = "ETF";
/*     */ 
/* 378 */         if (fldVal == null) {
/* 379 */           if (this.mustSet.contains(bitIdx));
/* 380 */           throw new HiException("213226", bitIdx.toString(), fldName);
/*     */         }
/*     */ 
/* 388 */         if (StringUtils.equals(itemNode.attributeValue("convert"), "hex"))
/*     */         {
/* 390 */           valByte = HiConvHelper.ascStr2Bcd(fldVal);
/*     */         }
/*     */         else valByte = fldVal.getBytes();
/*     */       }
/*     */ 
/* 395 */       valByte = specProc(itemNode, valByte, log);
/* 396 */       if ((this.pro_method != null) && (this.proSet.contains(bitIdx))) {
/* 397 */         valByte = HiItemHelper.execMethod(this.pro_method, valByte, log);
/*     */       }
/* 399 */       valByte = HiPack8583Helper.fitPlain(valByte, itemNode, log);
/*     */ 
/* 405 */       out.write(valByte, 0, valByte.length);
/*     */ 
/* 407 */       bitMapByte[idx] = '1';
/*     */ 
/* 409 */       if (log.isInfoEnabled());
/* 410 */       log.info(sm.getString("HiPack8583.packItemOk", fldName, String.valueOf(idx + 1), String.valueOf(valByte.length), new String(valByte), type));
/*     */     }
/*     */ 
/* 417 */     if ((idx >= 64) && (bitMapByte[0] == '0'))
/* 418 */       bitMapByte[0] = '1';
/*     */   }
/*     */ 
/*     */   private void packHeader(HiMessageContext ctx, ByteArrayOutputStream out, int bodyLen, Logger log)
/*     */     throws HiException
/*     */   {
/* 424 */     if (log.isInfoEnabled()) {
/* 425 */       log.info("Pack 8583 Header Start==============>");
/*     */     }
/*     */ 
/* 428 */     HiMessage msg = ctx.getCurrentMsg();
/* 429 */     HiETF etfBody = msg.getETFBody();
/* 430 */     int msgLen = bodyLen;
/*     */ 
/* 432 */     if (this.header_len == 0) {
/* 433 */       out.write(0);
/* 434 */       return; }
/* 435 */     if (this.header_len > 0) {
/* 436 */       HiItemHelper.addEtfItem(msg, this.hLen_name, Integer.toHexString(this.header_len));
/*     */ 
/* 438 */       msgLen += this.header_len;
/*     */     }
/*     */ 
/* 441 */     HiItemHelper.addEtfItem(msg, this.mLen_name, String.valueOf(msgLen));
/*     */ 
/* 444 */     Iterator it = this.headerRoot.elementIterator();
/* 445 */     Element itemNode = null;
/*     */ 
/* 449 */     while (it.hasNext()) {
/* 450 */       itemNode = (Element)it.next();
/*     */ 
/* 452 */       String fldName = itemNode.attributeValue("etf_name");
/*     */ 
/* 454 */       String fldVal = etfBody.getGrandChildValue(fldName);
/*     */ 
/* 456 */       byte[] valByte = HiPack8583Helper.fitPlain(fldVal.getBytes(), itemNode, log);
/*     */ 
/* 459 */       out.write(valByte, 0, valByte.length);
/*     */ 
/* 461 */       if (log.isInfoEnabled());
/* 462 */       log.info(sm.getString("HiPack8583.packItemOk", fldName, String.valueOf(valByte.length), new String(valByte), "ETF"));
/*     */     }
/*     */ 
/* 467 */     if (log.isInfoEnabled())
/* 468 */       log.info("<==============Pack 8583 Header End");
/*     */   }
/*     */ 
/*     */   private byte[] fitConstPlain(Element itemNode, String field_id, String value, String data_type, int length, Logger log)
/*     */   {
/*     */     byte[] valBytes;
/* 519 */     int valLen = value.getBytes().length;
/*     */ 
/* 522 */     if (valLen > length) {
/* 523 */       value = value.substring(0, length);
/*     */     }
/*     */ 
/* 527 */     if ((!(data_type.endsWith("ASCII"))) && (length % 2 != 0)) {
/* 528 */       ++length;
/*     */     }
/*     */ 
/* 531 */     if (valLen < length)
/*     */     {
/* 533 */       String fill_asc = itemNode.attributeValue("fill_asc");
/*     */ 
/* 536 */       if (data_type.equals("CharASCII"))
/*     */       {
/* 538 */         value = fillCharRight(value, fill_asc, itemNode.attributeValue("align_mode"), length - valLen);
/*     */       }
/*     */       else
/*     */       {
/* 550 */         value = fillCharLeft(value, fill_asc, itemNode.attributeValue("align_mode"), length - valLen);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 560 */     if (!(data_type.endsWith("ASCII")))
/* 561 */       valBytes = HiConvHelper.ascStr2Bcd(value);
/*     */     else {
/* 563 */       valBytes = value.getBytes();
/*     */     }
/*     */ 
/* 566 */     if (log.isDebugEnabled()) {
/* 567 */       log.debug(sm.getString("HiPack8583.packItem0", field_id, new String(valBytes)));
/*     */     }
/*     */ 
/* 573 */     return valBytes;
/*     */   }
/*     */ 
/*     */   private byte[] fitVarPlain(Element itemNode, String field_id, String value, String data_type, int varLen, Logger log)
/*     */     throws HiException
/*     */   {
/*     */     byte[] valLenBytes;
/* 592 */     byte[] valBytes = value.getBytes();
/* 593 */     int valueLen = valBytes.length;
/*     */ 
/* 595 */     int valueAllocLen = valueLen;
/* 596 */     int varAllocLen = varLen;
/*     */ 
/* 598 */     if (!(data_type.endsWith("ASCII"))) {
/* 599 */       if (valueLen % 2 != 0)
/*     */       {
/* 601 */         value = fillCharLeft(value, itemNode.attributeValue("fill_asc"), itemNode.attributeValue("align_mode"), 1);
/*     */ 
/* 604 */         ++valueAllocLen;
/*     */       }
/*     */ 
/* 612 */       valBytes = HiConvHelper.ascStr2Bcd(value);
/*     */ 
/* 614 */       valueAllocLen /= 2;
/*     */     }
/*     */ 
/* 618 */     String valLenStr = String.valueOf(valueLen);
/* 619 */     if (valLenStr.length() > varLen) {
/* 620 */       throw new HiException("213228", String.valueOf(varLen), String.valueOf(field_id));
/*     */     }
/*     */ 
/* 626 */     if (StringUtils.equals(itemNode.attributeValue("var_type"), "bin"))
/*     */     {
/* 630 */       if (varLen % 2 != 0) {
/* 631 */         ++varAllocLen;
/*     */       }
/*     */ 
/* 634 */       if (valLenStr.length() < varAllocLen) {
/* 635 */         valLenStr = StringUtils.leftPad(valLenStr, varAllocLen, '0');
/*     */       }
/*     */ 
/* 638 */       valLenBytes = HiConvHelper.ascStr2Bcd(valLenStr);
/*     */ 
/* 640 */       varAllocLen /= 2;
/*     */     } else {
/* 642 */       if (valLenStr.length() < varLen) {
/* 643 */         valLenStr = StringUtils.leftPad(valLenStr, varLen, '0');
/*     */       }
/*     */ 
/* 646 */       valLenBytes = valLenStr.getBytes();
/*     */     }
/*     */ 
/* 650 */     ByteBuffer bb = ByteBuffer.allocate(varAllocLen + valueAllocLen);
/* 651 */     bb.put(valLenBytes);
/* 652 */     bb.put(valBytes);
/*     */ 
/* 654 */     if (log.isDebugEnabled()) {
/* 655 */       log.debug(sm.getString("HiPack8583.packItem1", String.valueOf(varLen), field_id, new String(bb.array())));
/*     */     }
/*     */ 
/* 660 */     return bb.array();
/*     */   }
/*     */ 
/*     */   private String fillChar(String value, String fill_char, String align_mode, int repeat)
/*     */   {
/* 672 */     if (repeat <= 0) {
/* 673 */       return value;
/*     */     }
/* 675 */     if (StringUtils.isEmpty(fill_char)) {
/* 676 */       fill_char = "0";
/*     */     }
/*     */ 
/* 679 */     if (repeat > 1) {
/* 680 */       fill_char = StringUtils.repeat(fill_char, repeat);
/*     */     }
/*     */ 
/* 684 */     if (StringUtils.equals(align_mode, "left"))
/* 685 */       value = value + fill_char;
/*     */     else {
/* 687 */       value = fill_char + value;
/*     */     }
/* 689 */     return value;
/*     */   }
/*     */ 
/*     */   private String fillCharLeft(String value, String fill_asc, String align_mode, int repeat)
/*     */   {
/* 703 */     if (repeat <= 0) {
/* 704 */       return value;
/*     */     }
/* 706 */     if (StringUtils.isEmpty(fill_asc)) {
/* 707 */       fill_asc = "48";
/*     */     }
/*     */ 
/* 710 */     String fill_char = "0";
/* 711 */     if (StringUtils.equals(fill_asc, "48"))
/*     */     {
/* 713 */       if (repeat > 1)
/* 714 */         fill_char = StringUtils.repeat(fill_char, repeat);
/*     */     }
/*     */     else {
/* 717 */       byte b = Integer.valueOf(fill_asc).byteValue();
/* 718 */       byte[] fillBytes = null;
/* 719 */       fillBytes = HiItemHelper.repeat(fillBytes, b, repeat);
/* 720 */       fill_char = new String(fillBytes);
/*     */     }
/*     */ 
/* 724 */     if (StringUtils.equals(align_mode, "left"))
/* 725 */       value = value + fill_char;
/*     */     else {
/* 727 */       value = fill_char + value;
/*     */     }
/* 729 */     return value;
/*     */   }
/*     */ 
/*     */   private String fillCharRight(String value, String fill_asc, String align_mode, int repeat)
/*     */   {
/* 743 */     if (repeat <= 0) {
/* 744 */       return value;
/*     */     }
/* 746 */     if (StringUtils.isEmpty(fill_asc)) {
/* 747 */       fill_asc = "32";
/*     */     }
/*     */ 
/* 750 */     String fill_char = " ";
/* 751 */     if (StringUtils.equals(fill_asc, "32"))
/*     */     {
/* 753 */       if (repeat > 1)
/* 754 */         fill_char = StringUtils.repeat(fill_char, repeat);
/*     */     }
/*     */     else {
/* 757 */       byte b = Integer.valueOf(fill_asc).byteValue();
/* 758 */       byte[] fillBytes = null;
/* 759 */       fillBytes = HiItemHelper.repeat(fillBytes, b, repeat);
/* 760 */       fill_char = new String(fillBytes);
/*     */     }
/*     */ 
/* 764 */     if (StringUtils.equals(align_mode, "right"))
/* 765 */       value = fill_char + value;
/*     */     else {
/* 767 */       value = value + fill_char;
/*     */     }
/* 769 */     return value;
/*     */   }
/*     */ 
/*     */   private byte[] getTLV(Map tlvMap, String field_id)
/*     */   {
/* 780 */     if (tlvMap == null) {
/* 781 */       return null;
/*     */     }
/*     */ 
/* 784 */     if (tlvMap.containsKey(field_id)) {
/* 785 */       return ((byte[])(byte[])tlvMap.get(field_id));
/*     */     }
/*     */ 
/* 788 */     return null;
/*     */   }
/*     */ 
/*     */   private synchronized void initCfgNode(HiMessageContext ctx) throws HiException
/*     */   {
/* 793 */     if (this.isInit) {
/* 794 */       return;
/*     */     }
/*     */ 
/* 797 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 798 */     if (log.isDebugEnabled()) {
/* 799 */       log.debug("first initCfgNode - start.");
/*     */     }
/*     */ 
/* 802 */     Element cfgRoot = (Element)ctx.getProperty("8583_CFG_NODE");
/* 803 */     if (cfgRoot == null) {
/* 804 */       throw new HiException("213222", "");
/*     */     }
/*     */ 
/* 807 */     this.msgTypCodNode = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "0");
/*     */ 
/* 816 */     this.itemNodeMap.clear();
/*     */ 
/* 818 */     putMapNode(cfgRoot, this.mustSet);
/* 819 */     putMapNode(cfgRoot, this.optSet);
/*     */ 
/* 821 */     checkHeaderCfg(cfgRoot);
/*     */ 
/* 823 */     this.isInit = true;
/*     */ 
/* 825 */     if (log.isDebugEnabled()) {
/* 826 */       log.debug("must_fields [" + this.must_fields + "]");
/* 827 */       log.debug("opt_fields [" + this.opt_fields + "]");
/* 828 */       log.debug("first initCfgNode - end.");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void putMapNode(Element cfgRoot, Set fieldSet)
/*     */     throws HiException
/*     */   {
/* 836 */     Iterator it = fieldSet.iterator();
/* 837 */     while (it.hasNext()) {
/* 838 */       Integer fieldIdx = (Integer)it.next();
/*     */ 
/* 840 */       Element itemNode = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", fieldIdx.toString());
/*     */ 
/* 843 */       if (itemNode == null) {
/* 844 */         throw new HiException("213223", String.valueOf(fieldIdx));
/*     */       }
/*     */ 
/* 848 */       this.itemNodeMap.put(fieldIdx, itemNode);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkValidField(String fields, HashSet fldSet)
/*     */     throws HiException
/*     */   {
/*     */     Integer idx;
/* 856 */     StringTokenizer tokenizer = new StringTokenizer(fields, "|");
/*     */     do { if (!(tokenizer.hasMoreElements())) return;
/*     */       try {
/* 859 */         idx = Integer.valueOf(tokenizer.nextToken());
/*     */ 
/* 861 */         fldSet.add(idx);
/*     */       }
/*     */       catch (NumberFormatException ne) {
/* 864 */         throw new HiException("213225", getNodeName(), fields);
/*     */       }
/*     */     }
/* 867 */     while ((idx.intValue() > 1) && (idx.intValue() <= 128));
/* 868 */     throw new HiException("213224", fields);
/*     */   }
/*     */ 
/*     */   private void checkHeaderCfg(Element cfgRoot)
/*     */     throws HiException
/*     */   {
/* 881 */     if (this.pack_header) {
/* 882 */       this.headerRoot = HiXmlHelper.selectSingleNode(cfgRoot, "Header");
/* 883 */       if (this.headerRoot == null) {
/* 884 */         throw new HiException("", "8583配置文件没有Header配置节点");
/*     */       }
/*     */ 
/* 889 */       if (this.hLen_pos != null)
/*     */       {
/* 893 */         item = HiXmlHelper.selectSingleNode(this.headerRoot, "Item", "field_id", this.hLen_pos);
/*     */ 
/* 896 */         if (item == null) {
/* 897 */           throw new HiException("", "8583配置文件没有该配置节点 field_id:" + this.hLen_pos);
/*     */         }
/*     */ 
/* 900 */         this.hLen_name = item.attributeValue("etf_name");
/*     */ 
/* 903 */         if (this.header_len == -1) {
/* 904 */           this.header_len = countHeaderLen();
/*     */         }
/*     */       }
/*     */ 
/* 908 */       if (this.mLen_pos == null) {
/*     */         return;
/*     */       }
/*     */ 
/* 912 */       Element item = HiXmlHelper.selectSingleNode(this.headerRoot, "Item", "field_id", this.mLen_pos);
/*     */ 
/* 915 */       if (item == null) {
/* 916 */         throw new HiException("", "8583配置文件没有该配置节点 field_id:" + this.mLen_pos);
/*     */       }
/*     */ 
/* 919 */       this.mLen_name = item.attributeValue("etf_name");
/*     */     }
/*     */   }
/*     */ 
/*     */   private int countHeaderLen()
/*     */     throws HiException
/*     */   {
/* 927 */     if (this.headerRoot == null) {
/* 928 */       return 0;
/*     */     }
/*     */ 
/* 931 */     return 46;
/*     */   }
/*     */ 
/*     */   public byte[] specProc(Element element, byte[] values, Logger log) throws HiException
/*     */   {
/* 936 */     if (element == null)
/* 937 */       return values;
/* 938 */     String pro_dll1 = element.attributeValue("pro_dll");
/* 939 */     String pro_func1 = element.attributeValue("pro_func");
/*     */ 
/* 941 */     if ((StringUtils.isNotBlank(pro_dll1)) && (StringUtils.isNotBlank(pro_func1)))
/*     */     {
/* 943 */       HiMethodItem pro_method1 = HiItemHelper.getMethod(pro_dll1, pro_func1);
/*     */ 
/* 945 */       values = HiItemHelper.execMethod(pro_method1, values, log);
/*     */     }
/* 947 */     return values;
/*     */   }
/*     */ }