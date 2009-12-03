/*     */ package com.hisun.parse8583;
/*     */ 
/*     */ import com.hisun.engine.invoke.impl.HiItemHelper;
/*     */ import com.hisun.engine.invoke.impl.HiMethodItem;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiConvHelper;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import com.hisun.util.HiXmlHelper;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiParser8583Handler
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*     */   final String C8583_TLV = "Tlv";
/*     */   private final String HEADER = "Header";
/*     */   private String version;
/*     */   private String cfgFile;
/*     */   private Element cfgRoot;
/*     */   private int[] tlvFldList;
/*     */   private String pcfgFile;
/*     */   int _offset;
/*     */   final Logger log;
/*     */   final HiStringManager sm;
/*     */   protected HashMap cfgRootMap;
/*     */ 
/*     */   public HiParser8583Handler()
/*     */   {
/*  37 */     this.C8583_TLV = "Tlv";
/*  38 */     this.HEADER = "Header";
/*     */ 
/*  40 */     this.version = "1";
/*  41 */     this.cfgFile = null;
/*  42 */     this.cfgRoot = null;
/*  43 */     this.tlvFldList = null;
/*     */ 
/*  46 */     this.pcfgFile = null;
/*  47 */     this._offset = 0;
/*  48 */     this.log = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */ 
/*  51 */     this.sm = HiStringManager.getManager();
/*     */ 
/*  53 */     this.cfgRootMap = new HashMap(); }
/*     */ 
/*     */   public void setCFG(String cfgFile) {
/*  56 */     this.cfgFile = cfgFile;
/*     */   }
/*     */ 
/*     */   public void setPCFG(String pcfgFile) {
/*  60 */     this.pcfgFile = pcfgFile;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException
/*     */   {
/*  65 */     HiMessage msg = ctx.getCurrentMsg();
/*  66 */     HiByteBuffer plainBytes = (HiByteBuffer)msg.getBody();
/*     */ 
/*  68 */     if (this.log.isDebugEnabled()) {
/*  69 */       this.log.debug(this.sm.getString("HiParser8583Handler.parser0"));
/*  70 */       this.log.debug(this.sm.getString("HiParser8583Handler.parser1", plainBytes.toString()));
/*     */ 
/*  72 */       this.log.debug(this.sm.getString("HiParser8583Handler.parser2", HiConvHelper.bcd2AscStr(plainBytes.getBytes())));
/*     */     }
/*     */ 
/*  76 */     HiETF etfBody = HiETFFactory.createXmlETF();
/*     */ 
/*  78 */     HiByteArrayInputStream in = new HiByteArrayInputStream(plainBytes.getBytes());
/*     */ 
/*  82 */     Element headRoot = this.cfgRoot.element("Header");
/*     */ 
/*  84 */     if (StringUtils.equals(this.version, "2"))
/*  85 */       if ((headRoot != null) && (plainBytes.charAt(0) != 48))
/*     */       {
/*  87 */         convertHeaderToETF(in, etfBody, headRoot);
/*     */       }
/*  89 */     else if (headRoot != null)
/*     */     {
/*  91 */       convertHeaderToETF(in, etfBody, headRoot);
/*     */     }
/*     */ 
/*  95 */     convertToETF(in, etfBody, this.cfgRoot);
/*     */ 
/*  97 */     msg.setBody(etfBody);
/*     */ 
/*  99 */     if (this.log.isDebugEnabled())
/* 100 */       this.log.debug(this.sm.getString("HiParser8583Handler.parsered"));
/*     */   }
/*     */ 
/*     */   public HiETF convertHeaderToETF(HiByteArrayInputStream in, HiETF etfBody, Element headRoot)
/*     */     throws HiException
/*     */   {
/* 106 */     if (this.log.isDebugEnabled()) {
/* 107 */       this.log.debug("Parser 8583 Header－Start");
/*     */     }
/*     */ 
/* 110 */     String value = makeEtfItem(in, etfBody, 300, headRoot);
/* 111 */     int size = Integer.parseInt(value, 16);
/*     */ 
/* 114 */     if (size != 0) {
/* 115 */       size = headRoot.elements().size();
/* 116 */       for (int i = 1; i < size; ++i) {
/* 117 */         makeEtfItem(in, etfBody, 300 + i, headRoot);
/*     */       }
/*     */     }
/*     */ 
/* 121 */     if (this.log.isDebugEnabled()) {
/* 122 */       this.log.debug("Parser 8583 Header－End");
/*     */     }
/* 124 */     return etfBody;
/*     */   }
/*     */ 
/*     */   public HiETF convertToETF(HiByteArrayInputStream in, HiETF etfBody, Element cfgRoot)
/*     */     throws HiException
/*     */   {
/* 130 */     makeEtfItem(in, etfBody, 0, cfgRoot);
/*     */ 
/* 133 */     String bitMap = getBitMap(in, etfBody, 1);
/*     */ 
/* 136 */     for (int i = 1; i < bitMap.length(); ++i) {
/* 137 */       if (bitMap.charAt(i) == '1') {
/* 138 */         makeEtfItem(in, etfBody, i + 1, cfgRoot);
/*     */       }
/*     */     }
/* 141 */     return etfBody;
/*     */   }
/*     */ 
/*     */   private String getBitMap(HiByteArrayInputStream in, HiETF etfBody, int idx) throws HiException
/*     */   {
/* 146 */     if (this.log.isDebugEnabled()) {
/* 147 */       this.log.debug(this.sm.getString("HiParser8583Handler.getBitMap0"));
/*     */     }
/*     */ 
/* 154 */     Element item = (Element)this.cfgRootMap.get(String.valueOf(idx));
/* 155 */     if (item == null) {
/* 156 */       throw new HiException("231409", String.valueOf(idx));
/*     */     }
/*     */ 
/* 160 */     byte[] retBytes = new byte[8];
/* 161 */     if (in.read(retBytes, 0, 8) < 8) {
/* 162 */       throw new HiException("231410", "");
/*     */     }
/*     */ 
/* 166 */     String hexStr = HiConvHelper.bcd2AscStr(retBytes);
/*     */ 
/* 169 */     String bitMap = HiConvHelper.hex2Binary(hexStr);
/*     */ 
/* 172 */     if (bitMap.charAt(0) == '1')
/*     */     {
/* 174 */       retBytes = new byte[8];
/* 175 */       if (in.read(retBytes, 0, 8) < 8) {
/* 176 */         throw new HiException("231410", "报文长度不足");
/*     */       }
/*     */ 
/* 179 */       String extHexStr = HiConvHelper.bcd2AscStr(retBytes);
/*     */ 
/* 181 */       bitMap = bitMap + HiConvHelper.hex2Binary(extHexStr);
/* 182 */       hexStr = hexStr + extHexStr;
/*     */     }
/*     */ 
/* 185 */     if (this.log.isInfoEnabled()) {
/* 186 */       this.log.info("<BITMAP> [" + hexStr + "]");
/*     */     }
/* 188 */     return bitMap;
/*     */   }
/*     */ 
/*     */   private String makeEtfItem(HiByteArrayInputStream in, HiETF etfBody, int idx, Element cfgRoot) throws HiException
/*     */   {
/* 193 */     if (this.log.isDebugEnabled()) {
/* 194 */       this.log.debug(this.sm.getString("HiParser8583Handler.parserItem0", String.valueOf(idx)));
/*     */     }
/*     */ 
/* 201 */     Element item = (Element)this.cfgRootMap.get(String.valueOf(idx));
/*     */ 
/* 203 */     if (item == null) {
/* 204 */       throw new HiException("231409", String.valueOf(idx));
/*     */     }
/*     */ 
/* 208 */     int length = 0;
/*     */ 
/* 211 */     String data_value = "";
/*     */ 
/* 213 */     String data_type = item.attributeValue("data_type");
/* 214 */     String length_type = item.attributeValue("length_type");
/* 215 */     if (length_type.equals("CONST")) {
/* 216 */       length = Integer.parseInt(item.attributeValue("length"));
/*     */     }
/* 218 */     else if (length_type.equals("VAR2"))
/* 219 */       length = getHeadLen(in, 2, item);
/* 220 */     else if (length_type.equals("VAR3"))
/* 221 */       length = getHeadLen(in, 3, item);
/*     */     else {
/* 223 */       throw new HiException("231404", item.attributeValue("field_id"));
/*     */     }
/*     */ 
/* 228 */     if ((data_type.equals("CharASCII")) || (data_type.equals("NumASCII")))
/*     */     {
/* 230 */       byte[] retBytes = null;
/* 231 */       retBytes = new byte[length];
/* 232 */       if (in.read(retBytes, 0, length) < length) {
/* 233 */         throw new HiException("231410", "报文长度不足");
/*     */       }
/*     */ 
/* 236 */       retBytes = specProc(item, retBytes);
/*     */ 
/* 239 */       if (StringUtils.equals(item.attributeValue("convert"), "hex"))
/*     */       {
/* 241 */         data_value = HiConvHelper.bcd2AscStr(retBytes);
/*     */       }
/*     */       else data_value = new String(retBytes);
/*     */     }
/* 245 */     else if ((data_type.equals("ASCBCD")) || (data_type.equals("NumBCD")) || (data_type.equals("BIT")))
/*     */     {
/* 248 */       data_value = readBcd(in, length, 2, item);
/*     */     } else {
/* 250 */       new HiException("231406", item.attributeValue("field_id"));
/*     */     }
/*     */ 
/* 254 */     if (ArrayUtils.indexOf(this.tlvFldList, idx) >= 0)
/*     */     {
/* 256 */       parserTlv(etfBody, item, data_value.getBytes(), String.valueOf(idx));
/*     */     }
/*     */     else {
/* 259 */       String itemName = item.attributeValue("etf_name");
/*     */ 
/* 261 */       etfBody.setGrandChildNode(itemName, data_value);
/*     */ 
/* 263 */       if (this.log.isInfoEnabled()) {
/* 264 */         this.log.info("[" + (in.getPos() * 2) + "][" + String.valueOf(idx) + "][" + itemName + "][" + String.valueOf(length) + "][" + data_value + "]");
/*     */       }
/*     */     }
/*     */ 
/* 268 */     return data_value;
/*     */   }
/*     */ 
/*     */   private String readBcd(HiByteArrayInputStream in, int length, int radix, Element itemNode)
/*     */     throws HiException
/*     */   {
/* 284 */     int bcdLen = length / radix;
/* 285 */     int rsvLen = length % radix;
/* 286 */     byte[] retBytes = null;
/*     */ 
/* 288 */     if (rsvLen != 0) {
/* 289 */       bcdLen += 1;
/*     */ 
/* 291 */       retBytes = new byte[bcdLen];
/* 292 */       if (in.read(retBytes, 0, bcdLen) < bcdLen) {
/* 293 */         throw new HiException("231410", "报文长度不足");
/*     */       }
/*     */ 
/* 296 */       retBytes = specProc(itemNode, retBytes);
/*     */ 
/* 298 */       if ((itemNode != null) && (StringUtils.equals(itemNode.attributeValue("align_mode"), "left")))
/*     */       {
/* 302 */         return StringUtils.substring(HiConvHelper.bcd2AscStr(retBytes), 0, length);
/*     */       }
/*     */ 
/* 305 */       return StringUtils.substring(HiConvHelper.bcd2AscStr(retBytes), radix - rsvLen);
/*     */     }
/*     */ 
/* 309 */     retBytes = new byte[bcdLen];
/* 310 */     if (in.read(retBytes, 0, bcdLen) < bcdLen) {
/* 311 */       throw new HiException("231410", "报文长度不足");
/*     */     }
/*     */ 
/* 314 */     specProc(itemNode, retBytes);
/*     */ 
/* 316 */     return HiConvHelper.bcd2AscStr(retBytes);
/*     */   }
/*     */ 
/*     */   private int getHeadLen(HiByteArrayInputStream in, int varLen, Element itemNode)
/*     */     throws HiException
/*     */   {
/* 334 */     if (StringUtils.equals(itemNode.attributeValue("var_type"), "bin"))
/*     */     {
/* 337 */       return Integer.parseInt(readBcd(in, varLen, 2, null));
/*     */     }
/* 339 */     byte[] len = new byte[varLen];
/* 340 */     if (in.read(len, 0, varLen) < varLen) {
/* 341 */       throw new HiException("231410", "解析前置长度,不足");
/*     */     }
/*     */ 
/* 344 */     return Integer.parseInt(new String(len));
/*     */   }
/*     */ 
/*     */   private void checkCfg(Element cfgRoot, boolean isPutTlvList)
/*     */     throws HiException
/*     */   {
/* 351 */     if ((cfgRoot == null) || (cfgRoot.elements().size() < 129)) {
/* 352 */       throw new HiException("231400", this.cfgFile);
/*     */     }
/*     */ 
/* 356 */     Element item = cfgRoot.element("Header");
/* 357 */     if (item != null) {
/* 358 */       checkItemCfg(item, false);
/*     */     }
/*     */ 
/* 362 */     checkItemCfg(cfgRoot, isPutTlvList);
/*     */ 
/* 365 */     item = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "0");
/*     */ 
/* 368 */     if (item == null) {
/* 369 */       throw new HiException("231407", "0");
/*     */     }
/*     */ 
/* 372 */     item = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "1");
/*     */ 
/* 375 */     if (item == null)
/* 376 */       throw new HiException("231407", "1");
/*     */   }
/*     */ 
/*     */   private void checkItemCfg(Element cfgRoot, boolean isPutTlvList)
/*     */     throws HiException
/*     */   {
/* 383 */     Iterator it = cfgRoot.elementIterator("Item");
/*     */ 
/* 386 */     int i = 0;
/*     */ 
/* 388 */     while (it.hasNext())
/*     */     {
/*     */       int id;
/* 389 */       Element item = (Element)it.next();
/* 390 */       String itemAttr = item.attributeValue("field_id");
/*     */       try
/*     */       {
/* 393 */         id = Integer.parseInt(itemAttr);
/*     */       } catch (NumberFormatException ne) {
/* 395 */         throw new HiException("231424", String.valueOf(i));
/*     */       }
/*     */ 
/* 399 */       if ((isPutTlvList) && (item.elements("Tlv").size() > 0))
/*     */       {
/* 401 */         this.tlvFldList = ArrayUtils.add(this.tlvFldList, id);
/*     */       }
/*     */ 
/* 404 */       if (StringUtils.isBlank(item.attributeValue("etf_name")))
/*     */       {
/* 406 */         throw new HiException("231401", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 410 */       itemAttr = item.attributeValue("length_type");
/*     */ 
/* 412 */       if (StringUtils.isBlank(itemAttr)) {
/* 413 */         throw new HiException("231402", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 417 */       if (itemAttr.equals("CONST")) {
/* 418 */         String lenAttr = item.attributeValue("length");
/*     */ 
/* 420 */         if ((StringUtils.isBlank(lenAttr)) || (!(StringUtils.isNumeric(lenAttr))))
/*     */         {
/* 422 */           throw new HiException("231403", item.attributeValue("field_id"));
/*     */         }
/*     */       }
/* 425 */       else if ((itemAttr.equals("VAR2")) || (itemAttr.equals("VAR3")))
/*     */       {
/* 427 */         String len_type = item.attributeValue("var_type");
/*     */ 
/* 429 */         if ((!(StringUtils.isEmpty(len_type))) && (!(StringUtils.equals(len_type, "bin")))) if (!(StringUtils.equals(len_type, "char")))
/*     */           {
/* 436 */             throw new HiException("231429", item.attributeValue("field_id"));
/*     */           }
/*     */       }
/*     */       else
/*     */       {
/* 441 */         throw new HiException("231404", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 446 */       itemAttr = item.attributeValue("data_type");
/* 447 */       if (StringUtils.isBlank(itemAttr)) {
/* 448 */         throw new HiException("231405", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 452 */       if ((!(itemAttr.equals("CharASCII"))) && (!(itemAttr.equals("NumASCII"))) && (!(itemAttr.equals("ASCBCD"))) && (!(itemAttr.equals("NumBCD")))) if (!(itemAttr.equals("BIT")))
/*     */         {
/* 459 */           throw new HiException("231406", item.attributeValue("field_id"));
/*     */         }
/*     */ 
/*     */ 
/* 464 */       itemAttr = item.attributeValue("align_mode");
/* 465 */       if ((!(StringUtils.isEmpty(itemAttr))) && (!(StringUtils.equals(itemAttr, "left")))) if (!(StringUtils.equals(itemAttr, "right")))
/*     */         {
/* 472 */           throw new HiException("231428", item.attributeValue("field_id"));
/*     */         }
/*     */ 
/*     */ 
/* 477 */       ++i;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parserTlv(HiETF etfBody, Element itemNode, byte[] dataBytes, String field_id)
/*     */     throws HiException
/*     */   {
/* 496 */     if (dataBytes == null) {
/* 497 */       return;
/*     */     }
/* 499 */     if (this.log.isInfoEnabled()) {
/* 500 */       this.log.info(this.sm.getString("HiParser8583Handler.parserTlv0", field_id, new String(dataBytes)));
/*     */     }
/*     */ 
/* 506 */     HiByteArrayInputStream bIn = new HiByteArrayInputStream(dataBytes);
/* 507 */     while (bIn.available() > 0) {
/* 508 */       parserTlvItem(etfBody, itemNode, bIn);
/*     */     }
/* 510 */     bIn = null;
/*     */ 
/* 512 */     if (this.log.isInfoEnabled())
/* 513 */       this.log.info(this.sm.getString("HiParser8583Handler.parserTlvOk", field_id));
/*     */   }
/*     */ 
/*     */   private void parserTlvItem(HiETF etfBody, Element itemNode, HiByteArrayInputStream bIn)
/*     */     throws HiException
/*     */   {
/* 519 */     if (this.log.isDebugEnabled()) {
/* 520 */       this.log.debug("parserTlvItem() start");
/*     */     }
/*     */ 
/* 524 */     byte[] bytes = new byte[1];
/* 525 */     if (bIn.read(bytes, 0, 1) < 1) {
/* 526 */       return;
/*     */     }
/*     */ 
/* 529 */     String tag = HiConvHelper.bcd2AscStr(bytes);
/* 530 */     if ((tag.endsWith("F")) || (tag.endsWith("f"))) {
/* 531 */       if (bIn.read(bytes, 0, 1) < 1) {
/* 532 */         throw new HiException("231410", "TLV: 取tag标签有误");
/*     */       }
/*     */ 
/* 536 */       tag = tag + HiConvHelper.bcd2AscStr(bytes);
/*     */     }
/*     */ 
/* 539 */     if (this.log.isInfoEnabled()) {
/* 540 */       this.log.info("Tlv: tag=[" + tag + "]");
/*     */     }
/*     */ 
/* 544 */     int length = 0;
/* 545 */     if (bIn.read(bytes, 0, 1) < 1) {
/* 546 */       throw new HiException("231410", "TLV: 取子域长度有误");
/*     */     }
/*     */ 
/* 550 */     if (bytes[0] >= 0)
/*     */     {
/* 552 */       length = bytes[0];
/*     */     } else {
/* 554 */       int preLen = 0;
/* 555 */       if (bytes[0] == -127)
/*     */       {
/* 557 */         preLen = 1;
/* 558 */       } else if (bytes[0] == -126)
/*     */       {
/* 560 */         preLen = 2;
/*     */       }
/*     */       else throw new HiException("231426", String.valueOf(bytes[0]));
/*     */ 
/* 567 */       for (int i = 0; i < preLen; ++i) {
/* 568 */         int len = bIn.read();
/* 569 */         if (len == -1) {
/* 570 */           throw new HiException("231410", "TLV: 取子域长度有误");
/*     */         }
/*     */ 
/* 574 */         length = length * 256 + len;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 579 */     if (length == 0) {
/* 580 */       return;
/*     */     }
/*     */ 
/* 583 */     if (this.log.isDebugEnabled()) {
/* 584 */       this.log.debug("Tlv: length=[" + length + "]");
/*     */     }
/*     */ 
/* 587 */     bytes = new byte[length];
/* 588 */     if (bIn.read(bytes, 0, length) < length) {
/* 589 */       throw new HiException("231410", "TLV: 取子域值有误, 需长度[" + length + "]");
/*     */     }
/*     */ 
/* 594 */     Element tlvNode = HiXmlHelper.selectSingleNode(itemNode, "Tlv", "tag", tag);
/*     */ 
/* 596 */     if (tlvNode == null)
/*     */     {
/* 598 */       throw new HiException("231427", itemNode.attributeValue("field_id"), tag);
/*     */     }
/*     */ 
/* 601 */     String data_type = tlvNode.attributeValue("data_type");
/*     */ 
/* 603 */     String data_value = "";
/*     */ 
/* 605 */     if ((data_type.equals("CharASCII")) || (data_type.equals("NumASCII")))
/*     */     {
/* 607 */       data_value = new String(bytes);
/* 608 */     } else if ((data_type.equals("BIT")) || (data_type.equals("ASCBCD")) || (data_type.equals("NumBCD")))
/*     */     {
/* 611 */       data_value = HiConvHelper.bcd2AscStr(bytes);
/*     */ 
/* 613 */       length *= 2;
/*     */     } else {
/* 615 */       new HiException("231425", tag);
/*     */     }
/*     */ 
/* 619 */     String itemName = tlvNode.attributeValue("etf_name");
/*     */ 
/* 621 */     etfBody.setGrandChildNode(itemName, data_value);
/*     */ 
/* 623 */     if (this.log.isInfoEnabled())
/* 624 */       this.log.info(this.sm.getString("HiParser8583Handler.parserTlv1", tag, itemName, String.valueOf(length), data_value));
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/* 632 */     if (this.log.isDebugEnabled()) {
/* 633 */       this.log.debug(this.sm.getString("HiParser8583Handler.init1", this.cfgFile));
/*     */     }
/*     */ 
/* 636 */     Element pcfgRoot = null;
/*     */ 
/* 638 */     URL fileUrl = HiResource.getResource(this.cfgFile);
/* 639 */     SAXReader saxReader = new SAXReader();
/*     */     try
/*     */     {
/* 642 */       this.cfgRoot = saxReader.read(fileUrl).getRootElement();
/* 643 */       cacheItem(this.cfgRoot);
/*     */ 
/* 645 */       if (!(StringUtils.isEmpty(this.pcfgFile))) {
/* 646 */         fileUrl = HiResource.getResource(this.pcfgFile);
/*     */ 
/* 648 */         pcfgRoot = saxReader.read(fileUrl).getRootElement();
/*     */       }
/*     */     }
/*     */     catch (DocumentException e) {
/* 652 */       throw new HiException("213319", fileUrl.getFile(), e);
/*     */     }
/*     */ 
/* 657 */     checkCfg(this.cfgRoot, true);
/*     */ 
/* 659 */     if (pcfgRoot != null)
/*     */     {
/* 661 */       checkCfg(pcfgRoot, false);
/*     */ 
/* 664 */       HiContext.getCurrentContext().setProperty("8583_CFG_NODE", pcfgRoot);
/*     */     }
/*     */     else
/*     */     {
/* 668 */       HiContext.getCurrentContext().setProperty("8583_CFG_NODE", this.cfgRoot);
/*     */     }
/*     */ 
/* 671 */     if (this.log.isDebugEnabled())
/* 672 */       this.log.debug(this.sm.getString("HiParser8583Handler.init2", this.cfgFile));
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 677 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String version) {
/* 681 */     this.version = version;
/*     */   }
/*     */ 
/*     */   public byte[] specProc(Element element, byte[] values) throws HiException {
/* 685 */     if (element == null)
/* 686 */       return values;
/* 687 */     String pro_dll1 = element.attributeValue("pro_dll");
/* 688 */     String pro_func1 = element.attributeValue("pro_func");
/*     */ 
/* 690 */     if ((StringUtils.isNotBlank(pro_dll1)) && (StringUtils.isNotBlank(pro_func1)))
/*     */     {
/* 692 */       HiMethodItem pro_method1 = HiItemHelper.getMethod(pro_dll1, pro_func1);
/*     */ 
/* 694 */       values = HiItemHelper.execMethod(pro_method1, values, this.log);
/*     */     }
/* 696 */     return values;
/*     */   }
/*     */ 
/*     */   public void cacheItem(Element root) {
/* 700 */     Iterator iter = root.elementIterator();
/* 701 */     while (iter.hasNext()) {
/* 702 */       Element item = (Element)iter.next();
/* 703 */       this.cfgRootMap.put(item.attributeValue("field_id"), item);
/*     */     }
/*     */ 
/* 706 */     Element header = root.element("Header");
/* 707 */     if (header == null) {
/* 708 */       return;
/*     */     }
/*     */ 
/* 711 */     iter = header.elementIterator();
/* 712 */     while (iter.hasNext()) {
/* 713 */       Element item = (Element)iter.next();
/* 714 */       this.cfgRootMap.put(item.attributeValue("field_id"), item);
/*     */     }
/*     */   }
/*     */ }