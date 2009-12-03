/*     */ package com.hisun.handler;
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
/*     */ import java.io.ByteArrayInputStream;
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
/*     */   final Logger log;
/*     */   final HiStringManager sm;
/*     */   protected HashMap cfgRootMap;
/*     */ 
/*     */   public HiParser8583Handler()
/*     */   {
/*  38 */     this.C8583_TLV = "Tlv";
/*  39 */     this.HEADER = "Header";
/*     */ 
/*  41 */     this.version = "1";
/*  42 */     this.cfgFile = null;
/*  43 */     this.cfgRoot = null;
/*  44 */     this.tlvFldList = null;
/*     */ 
/*  47 */     this.pcfgFile = null;
/*     */ 
/*  49 */     this.log = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */ 
/*  52 */     this.sm = HiStringManager.getManager();
/*     */ 
/*  54 */     this.cfgRootMap = new HashMap(); }
/*     */ 
/*     */   public void setCFG(String cfgFile) { this.cfgFile = cfgFile;
/*     */   }
/*     */ 
/*     */   public void setPCFG(String pcfgFile) {
/*  60 */     this.pcfgFile = pcfgFile;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/*  64 */     HiMessage msg = ctx.getCurrentMsg();
/*  65 */     HiByteBuffer plainBytes = (HiByteBuffer)msg.getBody();
/*     */ 
/*  67 */     if (this.log.isInfoEnabled()) {
/*  68 */       this.log.info(this.sm.getString("HiParser8583Handler.parser0"));
/*  69 */       this.log.info(this.sm.getString("HiParser8583Handler.parser1", plainBytes.toString()));
/*     */ 
/*  71 */       this.log.info(this.sm.getString("HiParser8583Handler.parser2", HiConvHelper.bcd2AscStr(plainBytes.getBytes())));
/*     */     }
/*     */ 
/*  75 */     HiETF etfBody = HiETFFactory.createXmlETF();
/*     */ 
/*  77 */     ByteArrayInputStream in = new ByteArrayInputStream(plainBytes.getBytes());
/*     */ 
/*  81 */     Element headRoot = this.cfgRoot.element("Header");
/*     */ 
/*  83 */     if (StringUtils.equals(this.version, "2"))
/*  84 */       if ((headRoot != null) && (plainBytes.charAt(0) != 48))
/*     */       {
/*  86 */         convertHeaderToETF(in, etfBody, headRoot);
/*     */       }
/*  88 */     else if (headRoot != null)
/*     */     {
/*  90 */       convertHeaderToETF(in, etfBody, headRoot);
/*     */     }
/*     */ 
/*  94 */     convertToETF(in, etfBody, this.cfgRoot);
/*     */ 
/*  96 */     msg.setBody(etfBody);
/*     */ 
/*  98 */     if (this.log.isInfoEnabled())
/*  99 */       this.log.info(this.sm.getString("HiParser8583Handler.parsered"));
/*     */   }
/*     */ 
/*     */   public HiETF convertHeaderToETF(ByteArrayInputStream in, HiETF etfBody, Element headRoot)
/*     */     throws HiException
/*     */   {
/* 105 */     if (this.log.isInfoEnabled()) {
/* 106 */       this.log.info("Parser 8583 Header－Start");
/*     */     }
/*     */ 
/* 109 */     String value = makeEtfItem(in, etfBody, 300, headRoot);
/* 110 */     int size = Integer.parseInt(value, 16);
/*     */ 
/* 113 */     if (size != 0) {
/* 114 */       size = headRoot.elements().size();
/* 115 */       for (int i = 1; i < size; ++i) {
/* 116 */         makeEtfItem(in, etfBody, 300 + i, headRoot);
/*     */       }
/*     */     }
/*     */ 
/* 120 */     if (this.log.isInfoEnabled()) {
/* 121 */       this.log.info("Parser 8583 Header－End");
/*     */     }
/* 123 */     return etfBody;
/*     */   }
/*     */ 
/*     */   public HiETF convertToETF(ByteArrayInputStream in, HiETF etfBody, Element cfgRoot)
/*     */     throws HiException
/*     */   {
/* 129 */     makeEtfItem(in, etfBody, 0, cfgRoot);
/*     */ 
/* 132 */     String bitMap = getBitMap(in, etfBody, 1);
/*     */ 
/* 135 */     for (int i = 1; i < bitMap.length(); ++i) {
/* 136 */       if (bitMap.charAt(i) == '1') {
/* 137 */         makeEtfItem(in, etfBody, i + 1, cfgRoot);
/*     */       }
/*     */     }
/* 140 */     return etfBody;
/*     */   }
/*     */ 
/*     */   private String getBitMap(ByteArrayInputStream in, HiETF etfBody, int idx) throws HiException
/*     */   {
/* 145 */     if (this.log.isInfoEnabled()) {
/* 146 */       this.log.info(this.sm.getString("HiParser8583Handler.getBitMap0"));
/*     */     }
/*     */ 
/* 153 */     Element item = (Element)this.cfgRootMap.get(String.valueOf(idx));
/* 154 */     if (item == null) {
/* 155 */       throw new HiException("231409", String.valueOf(idx));
/*     */     }
/*     */ 
/* 159 */     byte[] retBytes = new byte[8];
/* 160 */     if (in.read(retBytes, 0, 8) < 8) {
/* 161 */       throw new HiException("231410", "");
/*     */     }
/*     */ 
/* 165 */     String hexStr = HiConvHelper.bcd2AscStr(retBytes);
/*     */ 
/* 168 */     String bitMap = HiConvHelper.hex2Binary(hexStr);
/*     */ 
/* 171 */     if (bitMap.charAt(0) == '1')
/*     */     {
/* 173 */       retBytes = new byte[8];
/* 174 */       if (in.read(retBytes, 0, 8) < 8) {
/* 175 */         throw new HiException("231410", "报文长度不足");
/*     */       }
/*     */ 
/* 178 */       String extHexStr = HiConvHelper.bcd2AscStr(retBytes);
/*     */ 
/* 180 */       bitMap = bitMap + HiConvHelper.hex2Binary(extHexStr);
/* 181 */       hexStr = hexStr + extHexStr;
/*     */     }
/*     */ 
/* 184 */     if (this.log.isInfoEnabled()) {
/* 185 */       this.log.info(this.sm.getString("HiParser8583Handler.getBitMap1", hexStr));
/*     */     }
/*     */ 
/* 188 */     return bitMap;
/*     */   }
/*     */ 
/*     */   private String makeEtfItem(ByteArrayInputStream in, HiETF etfBody, int idx, Element cfgRoot) throws HiException
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
/* 264 */         this.log.info(this.sm.getString("HiParser8583Handler.parserItem1", String.valueOf(idx), itemName, String.valueOf(length), data_value));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 271 */     return data_value;
/*     */   }
/*     */ 
/*     */   private String readBcd(ByteArrayInputStream in, int length, int radix, Element itemNode)
/*     */     throws HiException
/*     */   {
/* 287 */     int bcdLen = length / radix;
/* 288 */     int rsvLen = length % radix;
/* 289 */     byte[] retBytes = null;
/*     */ 
/* 291 */     if (rsvLen != 0) {
/* 292 */       bcdLen += 1;
/*     */ 
/* 294 */       retBytes = new byte[bcdLen];
/* 295 */       if (in.read(retBytes, 0, bcdLen) < bcdLen) {
/* 296 */         throw new HiException("231410", "报文长度不足");
/*     */       }
/*     */ 
/* 299 */       retBytes = specProc(itemNode, retBytes);
/*     */ 
/* 301 */       if ((itemNode != null) && (StringUtils.equals(itemNode.attributeValue("align_mode"), "left")))
/*     */       {
/* 305 */         return StringUtils.substring(HiConvHelper.bcd2AscStr(retBytes), 0, length);
/*     */       }
/*     */ 
/* 308 */       return StringUtils.substring(HiConvHelper.bcd2AscStr(retBytes), radix - rsvLen);
/*     */     }
/*     */ 
/* 312 */     retBytes = new byte[bcdLen];
/* 313 */     if (in.read(retBytes, 0, bcdLen) < bcdLen) {
/* 314 */       throw new HiException("231410", "报文长度不足");
/*     */     }
/*     */ 
/* 317 */     specProc(itemNode, retBytes);
/*     */ 
/* 319 */     return HiConvHelper.bcd2AscStr(retBytes);
/*     */   }
/*     */ 
/*     */   private int getHeadLen(ByteArrayInputStream in, int varLen, Element itemNode)
/*     */     throws HiException
/*     */   {
/* 337 */     if (StringUtils.equals(itemNode.attributeValue("var_type"), "bin"))
/*     */     {
/* 340 */       return Integer.parseInt(readBcd(in, varLen, 2, null));
/*     */     }
/* 342 */     byte[] len = new byte[varLen];
/* 343 */     if (in.read(len, 0, varLen) < varLen) {
/* 344 */       throw new HiException("231410", "解析前置长度,不足");
/*     */     }
/*     */ 
/* 347 */     return Integer.parseInt(new String(len));
/*     */   }
/*     */ 
/*     */   private void checkCfg(Element cfgRoot, boolean isPutTlvList)
/*     */     throws HiException
/*     */   {
/* 354 */     if ((cfgRoot == null) || (cfgRoot.elements().size() < 129)) {
/* 355 */       throw new HiException("231400", this.cfgFile);
/*     */     }
/*     */ 
/* 359 */     Element item = cfgRoot.element("Header");
/* 360 */     if (item != null) {
/* 361 */       checkItemCfg(item, false);
/*     */     }
/*     */ 
/* 365 */     checkItemCfg(cfgRoot, isPutTlvList);
/*     */ 
/* 368 */     item = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "0");
/*     */ 
/* 371 */     if (item == null) {
/* 372 */       throw new HiException("231407", "0");
/*     */     }
/*     */ 
/* 375 */     item = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "1");
/*     */ 
/* 378 */     if (item == null)
/* 379 */       throw new HiException("231407", "1");
/*     */   }
/*     */ 
/*     */   private void checkItemCfg(Element cfgRoot, boolean isPutTlvList)
/*     */     throws HiException
/*     */   {
/* 386 */     Iterator it = cfgRoot.elementIterator("Item");
/*     */ 
/* 389 */     int i = 0;
/*     */ 
/* 391 */     while (it.hasNext())
/*     */     {
/*     */       int id;
/* 392 */       Element item = (Element)it.next();
/* 393 */       String itemAttr = item.attributeValue("field_id");
/*     */       try
/*     */       {
/* 396 */         id = Integer.parseInt(itemAttr);
/*     */       } catch (NumberFormatException ne) {
/* 398 */         throw new HiException("231424", String.valueOf(i));
/*     */       }
/*     */ 
/* 402 */       if ((isPutTlvList) && (item.elements("Tlv").size() > 0))
/*     */       {
/* 404 */         this.tlvFldList = ArrayUtils.add(this.tlvFldList, id);
/*     */       }
/*     */ 
/* 407 */       if (StringUtils.isBlank(item.attributeValue("etf_name")))
/*     */       {
/* 409 */         throw new HiException("231401", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 413 */       itemAttr = item.attributeValue("length_type");
/*     */ 
/* 415 */       if (StringUtils.isBlank(itemAttr)) {
/* 416 */         throw new HiException("231402", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 420 */       if (itemAttr.equals("CONST")) {
/* 421 */         String lenAttr = item.attributeValue("length");
/*     */ 
/* 423 */         if ((StringUtils.isBlank(lenAttr)) || (!(StringUtils.isNumeric(lenAttr))))
/*     */         {
/* 425 */           throw new HiException("231403", item.attributeValue("field_id"));
/*     */         }
/*     */       }
/* 428 */       else if ((itemAttr.equals("VAR2")) || (itemAttr.equals("VAR3")))
/*     */       {
/* 430 */         String len_type = item.attributeValue("var_type");
/*     */ 
/* 432 */         if ((!(StringUtils.isEmpty(len_type))) && (!(StringUtils.equals(len_type, "bin")))) if (!(StringUtils.equals(len_type, "char")))
/*     */           {
/* 439 */             throw new HiException("231429", item.attributeValue("field_id"));
/*     */           }
/*     */       }
/*     */       else
/*     */       {
/* 444 */         throw new HiException("231404", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 449 */       itemAttr = item.attributeValue("data_type");
/* 450 */       if (StringUtils.isBlank(itemAttr)) {
/* 451 */         throw new HiException("231405", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 455 */       if ((!(itemAttr.equals("CharASCII"))) && (!(itemAttr.equals("NumASCII"))) && (!(itemAttr.equals("ASCBCD"))) && (!(itemAttr.equals("NumBCD")))) if (!(itemAttr.equals("BIT")))
/*     */         {
/* 462 */           throw new HiException("231406", item.attributeValue("field_id"));
/*     */         }
/*     */ 
/*     */ 
/* 467 */       itemAttr = item.attributeValue("align_mode");
/* 468 */       if ((!(StringUtils.isEmpty(itemAttr))) && (!(StringUtils.equals(itemAttr, "left")))) if (!(StringUtils.equals(itemAttr, "right")))
/*     */         {
/* 475 */           throw new HiException("231428", item.attributeValue("field_id"));
/*     */         }
/*     */ 
/*     */ 
/* 480 */       ++i;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parserTlv(HiETF etfBody, Element itemNode, byte[] dataBytes, String field_id)
/*     */     throws HiException
/*     */   {
/* 499 */     if (dataBytes == null) {
/* 500 */       return;
/*     */     }
/* 502 */     if (this.log.isInfoEnabled()) {
/* 503 */       this.log.info(this.sm.getString("HiParser8583Handler.parserTlv0", field_id, new String(dataBytes)));
/*     */     }
/*     */ 
/* 509 */     ByteArrayInputStream bIn = new ByteArrayInputStream(dataBytes);
/* 510 */     while (bIn.available() > 0) {
/* 511 */       parserTlvItem(etfBody, itemNode, bIn);
/*     */     }
/* 513 */     bIn = null;
/*     */ 
/* 515 */     if (this.log.isInfoEnabled())
/* 516 */       this.log.info(this.sm.getString("HiParser8583Handler.parserTlvOk", field_id));
/*     */   }
/*     */ 
/*     */   private void parserTlvItem(HiETF etfBody, Element itemNode, ByteArrayInputStream bIn)
/*     */     throws HiException
/*     */   {
/* 522 */     if (this.log.isDebugEnabled()) {
/* 523 */       this.log.debug("parserTlvItem() start");
/*     */     }
/*     */ 
/* 527 */     byte[] bytes = new byte[1];
/* 528 */     if (bIn.read(bytes, 0, 1) < 1) {
/* 529 */       return;
/*     */     }
/*     */ 
/* 532 */     String tag = HiConvHelper.bcd2AscStr(bytes);
/* 533 */     if ((tag.endsWith("F")) || (tag.endsWith("f"))) {
/* 534 */       if (bIn.read(bytes, 0, 1) < 1) {
/* 535 */         throw new HiException("231410", "TLV: 取tag标签有误");
/*     */       }
/*     */ 
/* 539 */       tag = tag + HiConvHelper.bcd2AscStr(bytes);
/*     */     }
/*     */ 
/* 542 */     if (this.log.isInfoEnabled()) {
/* 543 */       this.log.info("Tlv: tag=[" + tag + "]");
/*     */     }
/*     */ 
/* 547 */     int length = 0;
/* 548 */     if (bIn.read(bytes, 0, 1) < 1) {
/* 549 */       throw new HiException("231410", "TLV: 取子域长度有误");
/*     */     }
/*     */ 
/* 553 */     if (bytes[0] >= 0)
/*     */     {
/* 555 */       length = bytes[0];
/*     */     } else {
/* 557 */       int preLen = 0;
/* 558 */       if (bytes[0] == -127)
/*     */       {
/* 560 */         preLen = 1;
/* 561 */       } else if (bytes[0] == -126)
/*     */       {
/* 563 */         preLen = 2;
/*     */       }
/*     */       else throw new HiException("231426", String.valueOf(bytes[0]));
/*     */ 
/* 570 */       for (int i = 0; i < preLen; ++i) {
/* 571 */         int len = bIn.read();
/* 572 */         if (len == -1) {
/* 573 */           throw new HiException("231410", "TLV: 取子域长度有误");
/*     */         }
/*     */ 
/* 577 */         length = length * 256 + len;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 582 */     if (length == 0) {
/* 583 */       return;
/*     */     }
/*     */ 
/* 586 */     if (this.log.isDebugEnabled()) {
/* 587 */       this.log.debug("Tlv: length=[" + length + "]");
/*     */     }
/*     */ 
/* 590 */     bytes = new byte[length];
/* 591 */     if (bIn.read(bytes, 0, length) < length) {
/* 592 */       throw new HiException("231410", "TLV: 取子域值有误, 需长度[" + length + "]");
/*     */     }
/*     */ 
/* 597 */     Element tlvNode = HiXmlHelper.selectSingleNode(itemNode, "Tlv", "tag", tag);
/*     */ 
/* 599 */     if (tlvNode == null)
/*     */     {
/* 601 */       throw new HiException("231427", itemNode.attributeValue("field_id"), tag);
/*     */     }
/*     */ 
/* 604 */     String data_type = tlvNode.attributeValue("data_type");
/*     */ 
/* 606 */     String data_value = "";
/*     */ 
/* 608 */     if ((data_type.equals("CharASCII")) || (data_type.equals("NumASCII")))
/*     */     {
/* 610 */       data_value = new String(bytes);
/* 611 */     } else if ((data_type.equals("BIT")) || (data_type.equals("ASCBCD")) || (data_type.equals("NumBCD")))
/*     */     {
/* 614 */       data_value = HiConvHelper.bcd2AscStr(bytes);
/*     */ 
/* 616 */       length *= 2;
/*     */     } else {
/* 618 */       new HiException("231425", tag);
/*     */     }
/*     */ 
/* 622 */     String itemName = tlvNode.attributeValue("etf_name");
/*     */ 
/* 624 */     etfBody.setGrandChildNode(itemName, data_value);
/*     */ 
/* 626 */     if (this.log.isInfoEnabled())
/* 627 */       this.log.info(this.sm.getString("HiParser8583Handler.parserTlv1", tag, itemName, String.valueOf(length), data_value));
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/* 635 */     if (this.log.isInfoEnabled()) {
/* 636 */       this.log.info(this.sm.getString("HiParser8583Handler.init1", this.cfgFile));
/*     */     }
/*     */ 
/* 639 */     Element pcfgRoot = null;
/*     */ 
/* 641 */     URL fileUrl = HiResource.getResource(this.cfgFile);
/* 642 */     SAXReader saxReader = new SAXReader();
/*     */     try
/*     */     {
/* 645 */       this.cfgRoot = saxReader.read(fileUrl).getRootElement();
/* 646 */       cacheItem(this.cfgRoot);
/*     */ 
/* 648 */       if (!(StringUtils.isEmpty(this.pcfgFile))) {
/* 649 */         fileUrl = HiResource.getResource(this.pcfgFile);
/*     */ 
/* 651 */         pcfgRoot = saxReader.read(fileUrl).getRootElement();
/*     */       }
/*     */     }
/*     */     catch (DocumentException e) {
/* 655 */       throw new HiException("213319", fileUrl.getFile(), e);
/*     */     }
/*     */ 
/* 660 */     checkCfg(this.cfgRoot, true);
/*     */ 
/* 662 */     if (pcfgRoot != null)
/*     */     {
/* 664 */       checkCfg(pcfgRoot, false);
/*     */ 
/* 667 */       HiContext.getCurrentContext().setProperty("8583_CFG_NODE", pcfgRoot);
/*     */     }
/*     */     else
/*     */     {
/* 671 */       HiContext.getCurrentContext().setProperty("8583_CFG_NODE", this.cfgRoot);
/*     */     }
/*     */ 
/* 674 */     if (this.log.isInfoEnabled())
/* 675 */       this.log.info(this.sm.getString("HiParser8583Handler.init2", this.cfgFile));
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 681 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String version) {
/* 685 */     this.version = version;
/*     */   }
/*     */ 
/*     */   public byte[] specProc(Element element, byte[] values) throws HiException {
/* 689 */     if (element == null)
/* 690 */       return values;
/* 691 */     String pro_dll1 = element.attributeValue("pro_dll");
/* 692 */     String pro_func1 = element.attributeValue("pro_func");
/*     */ 
/* 694 */     if ((StringUtils.isNotBlank(pro_dll1)) && (StringUtils.isNotBlank(pro_func1))) {
/* 695 */       HiMethodItem pro_method1 = HiItemHelper.getMethod(pro_dll1, pro_func1);
/* 696 */       values = HiItemHelper.execMethod(pro_method1, values, this.log);
/*     */     }
/* 698 */     return values;
/*     */   }
/*     */ 
/*     */   public void cacheItem(Element root) {
/* 702 */     Iterator iter = root.elementIterator();
/* 703 */     while (iter.hasNext()) {
/* 704 */       Element item = (Element)iter.next();
/* 705 */       this.cfgRootMap.put(item.attributeValue("field_id"), item);
/*     */     }
/* 707 */     Element header = root.element("Header");
/* 708 */     if (header == null) {
/* 709 */       return;
/*     */     }
/*     */ 
/* 712 */     iter = header.elementIterator();
/* 713 */     while (iter.hasNext()) {
/* 714 */       Element item = (Element)iter.next();
/* 715 */       this.cfgRootMap.put(item.attributeValue("field_id"), item);
/*     */     }
/*     */   }
/*     */ }