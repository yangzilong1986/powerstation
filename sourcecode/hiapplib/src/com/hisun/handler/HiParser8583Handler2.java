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
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiParser8583Handler2
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*     */   private static final String HEADER = "Header";
/*     */   private String version;
/*     */   private String cfgFile;
/*     */   private String pcfgFile;
/*     */   private Map heads;
/*     */   private Map items;
/*     */   Logger log;
/*  48 */   static final HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public HiParser8583Handler2()
/*     */   {
/*  37 */     this.version = "1";
/*  38 */     this.cfgFile = null;
/*     */ 
/*  40 */     this.pcfgFile = null;
/*     */ 
/*  42 */     this.heads = new HashMap();
/*  43 */     this.items = new HashMap();
/*     */ 
/*  45 */     this.log = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */   }
/*     */ 
/*     */   public void setCFG(String cfgFile)
/*     */   {
/*  51 */     this.cfgFile = cfgFile;
/*     */   }
/*     */ 
/*     */   public void setPCFG(String pcfgFile) {
/*  55 */     this.pcfgFile = pcfgFile;
/*     */   }
/*     */ 
/*     */   public String getVersion() {
/*  59 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String version) {
/*  63 */     this.version = version;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/*  67 */     HiMessage msg = ctx.getCurrentMsg();
/*  68 */     HiByteBuffer plainBytes = (HiByteBuffer)msg.getBody();
/*     */ 
/*  70 */     if (this.log.isInfoEnabled()) {
/*  71 */       this.log.info(sm.getString("HiParser8583Handler.parser0"));
/*  72 */       this.log.info(sm.getString("HiParser8583Handler.parser1", plainBytes.toString()));
/*     */ 
/*  74 */       this.log.info(sm.getString("HiParser8583Handler.parser2", HiConvHelper.bcd2AscStr(plainBytes.getBytes())));
/*     */     }
/*     */ 
/*  78 */     HiETF etfBody = HiETFFactory.createXmlETF();
/*     */ 
/*  80 */     ByteArrayInputStream in = new ByteArrayInputStream(plainBytes.getBytes());
/*     */ 
/*  83 */     ParseContext pctx = new ParseContext(in, etfBody, this.log);
/*     */ 
/*  86 */     if (StringUtils.equals(this.version, "2"))
/*     */     {
/*  88 */       if ((this.heads.size() > 0) && (plainBytes.charAt(0) != 48))
/*     */       {
/*  90 */         convertHeaderToETF(pctx); }
/*     */     }
/*  92 */     else if (this.heads.size() > 0)
/*     */     {
/*  94 */       convertHeaderToETF(pctx);
/*     */     }
/*     */ 
/*  98 */     convertToETF(pctx);
/*     */ 
/* 100 */     msg.setBody(etfBody);
/*     */ 
/* 102 */     if (this.log.isInfoEnabled())
/* 103 */       this.log.info(sm.getString("HiParser8583Handler.parsered"));
/*     */   }
/*     */ 
/*     */   public void convertHeaderToETF(ParseContext ctx)
/*     */     throws HiException
/*     */   {
/* 109 */     if (this.log.isInfoEnabled()) {
/* 110 */       this.log.info("Parser 8583 Header－Start");
/*     */     }
/* 112 */     String value = makeEtfItem(ctx, this.heads, 0);
/* 113 */     int size = Integer.parseInt(value, 16);
/*     */ 
/* 117 */     if (size != 0) {
/* 118 */       int hsize = this.heads.size();
/* 119 */       for (int i = 1; i < hsize; ++i) {
/* 120 */         makeEtfItem(ctx, this.heads, i);
/*     */       }
/*     */     }
/*     */ 
/* 124 */     if (this.log.isInfoEnabled())
/* 125 */       this.log.info("Parser 8583 Header－End");
/*     */   }
/*     */ 
/*     */   public void convertToETF(ParseContext ctx)
/*     */     throws HiException
/*     */   {
/* 131 */     makeEtfItem(ctx, this.items, 0);
/*     */ 
/* 133 */     String bitMap = getBitMap(ctx);
/*     */ 
/* 136 */     for (int i = 1; i < bitMap.length(); ++i)
/* 137 */       if (bitMap.charAt(i) == '1')
/* 138 */         makeEtfItem(ctx, this.items, i + 1);
/*     */   }
/*     */ 
/*     */   private String getBitMap(ParseContext ctx)
/*     */     throws HiException
/*     */   {
/* 144 */     if (this.log.isInfoEnabled()) {
/* 145 */       this.log.info(sm.getString("HiParser8583Handler.getBitMap0"));
/*     */     }
/*     */ 
/* 148 */     byte[] retBytes = readBytes(ctx.in, 8);
/*     */ 
/* 151 */     String hexStr = HiConvHelper.bcd2AscStr(retBytes);
/*     */ 
/* 154 */     String bitMap = HiConvHelper.hex2Binary(hexStr);
/*     */ 
/* 157 */     if (bitMap.charAt(0) == '1')
/*     */     {
/* 159 */       retBytes = readBytes(ctx.in, 8);
/* 160 */       String extHexStr = HiConvHelper.bcd2AscStr(retBytes);
/*     */ 
/* 162 */       bitMap = bitMap + HiConvHelper.hex2Binary(extHexStr);
/* 163 */       hexStr = hexStr + extHexStr;
/*     */     }
/*     */ 
/* 166 */     if (this.log.isInfoEnabled()) {
/* 167 */       this.log.info(sm.getString("HiParser8583Handler.getBitMap1", hexStr));
/*     */     }
/* 169 */     return bitMap;
/*     */   }
/*     */ 
/*     */   private String makeEtfItem(ParseContext ctx, Map ps, int i) throws HiException
/*     */   {
/* 174 */     int idx = i;
/* 175 */     if (this.log.isDebugEnabled()) {
/* 176 */       this.log.debug(sm.getString("HiParser8583Handler.parserItem0", String.valueOf(idx)));
/*     */     }
/*     */ 
/* 179 */     Parser p = (Parser)ps.get(Integer.valueOf(i));
/* 180 */     if (p == null) {
/* 181 */       throw new HiException("231409", String.valueOf(idx));
/*     */     }
/*     */ 
/* 185 */     p.parse(ctx);
/* 186 */     return ctx.retString;
/*     */   }
/*     */ 
/*     */   private void checkCfg(Element cfgRoot, boolean isPutTlvList)
/*     */     throws HiException
/*     */   {
/* 194 */     if ((cfgRoot == null) || (cfgRoot.elements().size() < 65)) {
/* 195 */       throw new HiException("231400", this.cfgFile);
/*     */     }
/*     */ 
/* 199 */     Element item = cfgRoot.element("Header");
/* 200 */     if (item != null) {
/* 201 */       if (isPutTlvList)
/* 202 */         checkItemCfg(item, false, this.heads);
/*     */       else {
/* 204 */         checkItemCfg(item, false, null);
/*     */       }
/*     */     }
/*     */ 
/* 208 */     if (isPutTlvList)
/* 209 */       checkItemCfg(cfgRoot, isPutTlvList, this.items);
/*     */     else {
/* 211 */       checkItemCfg(cfgRoot, isPutTlvList, null);
/*     */     }
/*     */ 
/* 214 */     item = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "0");
/*     */ 
/* 217 */     if (item == null) {
/* 218 */       throw new HiException("231407", "0");
/*     */     }
/*     */ 
/* 221 */     item = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "1");
/*     */ 
/* 224 */     if (item == null)
/* 225 */       throw new HiException("231407", "1");
/*     */   }
/*     */ 
/*     */   private void checkItemCfg(Element cfgRoot, boolean isPutTlvList, Map items)
/*     */     throws HiException
/*     */   {
/* 232 */     Iterator it = cfgRoot.elementIterator("Item");
/*     */ 
/* 235 */     int i = 0;
/*     */ 
/* 237 */     while (it.hasNext())
/*     */     {
/*     */       int id;
/* 238 */       Element item = (Element)it.next();
/* 239 */       String itemAttr = item.attributeValue("field_id");
/*     */       try
/*     */       {
/* 242 */         id = Integer.parseInt(itemAttr);
/*     */       } catch (NumberFormatException ne) {
/* 244 */         throw new HiException("231424", String.valueOf(i));
/*     */       }
/*     */ 
/* 249 */       if (StringUtils.isBlank(item.attributeValue("etf_name")))
/*     */       {
/* 251 */         throw new HiException("231401", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 255 */       itemAttr = item.attributeValue("length_type");
/*     */ 
/* 257 */       if (StringUtils.isBlank(itemAttr)) {
/* 258 */         throw new HiException("231402", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 262 */       if (itemAttr.equals("CONST")) {
/* 263 */         String lenAttr = item.attributeValue("length");
/*     */ 
/* 265 */         if ((StringUtils.isBlank(lenAttr)) || (!(StringUtils.isNumeric(lenAttr))))
/*     */         {
/* 267 */           throw new HiException("231403", item.attributeValue("field_id"));
/*     */         }
/*     */       }
/* 270 */       else if ((itemAttr.equals("VAR2")) || (itemAttr.equals("VAR3")))
/*     */       {
/* 272 */         String len_type = item.attributeValue("var_type");
/*     */ 
/* 274 */         if ((!(StringUtils.isEmpty(len_type))) && (!(StringUtils.equals(len_type, "bin")))) if (!(StringUtils.equals(len_type, "char")))
/*     */           {
/* 281 */             throw new HiException("231429", item.attributeValue("field_id"));
/*     */           }
/*     */       }
/*     */       else
/*     */       {
/* 286 */         throw new HiException("231404", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 291 */       itemAttr = item.attributeValue("data_type");
/* 292 */       if (StringUtils.isBlank(itemAttr)) {
/* 293 */         throw new HiException("231405", item.attributeValue("field_id"));
/*     */       }
/*     */ 
/* 297 */       if ((!(itemAttr.equals("CharASCII"))) && (!(itemAttr.equals("NumASCII"))) && (!(itemAttr.equals("ASCBCD"))) && (!(itemAttr.equals("NumBCD")))) if (!(itemAttr.equals("BIT")))
/*     */         {
/* 304 */           throw new HiException("231406", item.attributeValue("field_id"));
/*     */         }
/*     */ 
/*     */ 
/* 309 */       itemAttr = item.attributeValue("align_mode");
/* 310 */       if ((!(StringUtils.isEmpty(itemAttr))) && (!(StringUtils.equals(itemAttr, "left")))) if (!(StringUtils.equals(itemAttr, "right")))
/*     */         {
/* 317 */           throw new HiException("231428", item.attributeValue("field_id"));
/*     */         }
/*     */ 
/*     */ 
/* 323 */       if (items != null) {
/* 324 */         items.put(Integer.valueOf(id), Parsers.itemParser(item, isPutTlvList));
/*     */       }
/* 326 */       ++i;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/* 331 */     if (this.log.isInfoEnabled()) {
/* 332 */       this.log.info(sm.getString("HiParser8583Handler.init1", this.cfgFile));
/*     */     }
/*     */ 
/* 335 */     Element cfgRoot = null;
/* 336 */     Element pcfgRoot = null;
/*     */ 
/* 338 */     URL fileUrl = HiResource.getResource(this.cfgFile);
/* 339 */     SAXReader saxReader = new SAXReader();
/*     */     try
/*     */     {
/* 342 */       cfgRoot = saxReader.read(fileUrl).getRootElement();
/*     */ 
/* 345 */       if (!(StringUtils.isEmpty(this.pcfgFile))) {
/* 346 */         fileUrl = HiResource.getResource(this.pcfgFile);
/*     */ 
/* 348 */         pcfgRoot = saxReader.read(fileUrl).getRootElement();
/*     */       }
/*     */     }
/*     */     catch (DocumentException e) {
/* 352 */       throw new HiException("213319", fileUrl.getFile(), e);
/*     */     }
/*     */ 
/* 357 */     checkCfg(cfgRoot, true);
/*     */ 
/* 359 */     if (pcfgRoot != null)
/*     */     {
/* 361 */       checkCfg(pcfgRoot, false);
/*     */ 
/* 364 */       HiContext.getCurrentContext().setProperty("8583_CFG_NODE", pcfgRoot);
/*     */     }
/*     */     else
/*     */     {
/* 368 */       HiContext.getCurrentContext().setProperty("8583_CFG_NODE", cfgRoot);
/*     */     }
/*     */ 
/* 371 */     if (this.log.isInfoEnabled())
/* 372 */       this.log.info(sm.getString("HiParser8583Handler.init2", this.cfgFile));
/*     */   }
/*     */ 
/*     */   private static byte[] readBytes(ByteArrayInputStream in, int length)
/*     */     throws HiException
/*     */   {
/* 380 */     byte[] retBytes = new byte[length];
/* 381 */     if (in.read(retBytes, 0, length) < length) {
/* 382 */       throw new HiException("231410", "报文长度不足");
/*     */     }
/*     */ 
/* 385 */     return retBytes;
/*     */   }
/*     */ 
/*     */   public static class Parsers
/*     */   {
/*     */     public static HiParser8583Handler2.Parser seq(HiParser8583Handler2.Parser[] ps)
/*     */     {
/* 410 */       return new HiParser8583Handler2.Parser(ps) { private final HiParser8583Handler2.Parser[] val$ps;
/*     */ 
/*     */         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { for (int i = 0; i < this.val$ps.length; ++i)
/* 413 */             this.val$ps[i].parse(ctx);
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     private static int getHeadLen(ByteArrayInputStream in, int varLen, boolean bvar_bin)
/*     */       throws HiException
/*     */     {
/* 433 */       if (bvar_bin)
/*     */       {
/*     */         String len;
/* 434 */         int rsvLen = varLen % 2;
/* 435 */         int bcdLen = varLen / 2 + rsvLen;
/* 436 */         byte[] retBytes = HiParser8583Handler2.access$000(in, bcdLen);
/*     */ 
/* 439 */         if (rsvLen != 0)
/* 440 */           len = StringUtils.substring(bcd2Str(retBytes), 1);
/*     */         else
/* 442 */           len = bcd2Str(retBytes);
/* 443 */         return Integer.parseInt(len);
/*     */       }
/* 445 */       byte[] len = HiParser8583Handler2.access$000(in, varLen);
/* 446 */       return Integer.parseInt(new String(len));
/*     */     }
/*     */ 
/*     */     private static String bcd2Str(byte[] retBytes)
/*     */     {
/* 461 */       return HiConvHelper.bcd2AscStr(retBytes);
/*     */     }
/*     */ 
/*     */     public static HiParser8583Handler2.Parser varlen(int l, boolean bvar_bin)
/*     */     {
/* 476 */       return new HiParser8583Handler2.Parser(l, bvar_bin) { private final int val$l;
/*     */         private final boolean val$bvar_bin;
/*     */ 
/*     */         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { ctx.len = HiParser8583Handler2.Parsers.access$100(ctx.in, this.val$l, this.val$bvar_bin);
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public static HiParser8583Handler2.Parser lenParser(String field_id, Element item)
/*     */       throws HiException
/*     */     {
/* 496 */       String length_type = item.attributeValue("length_type");
/*     */ 
/* 499 */       String slen = item.attributeValue("length");
/*     */ 
/* 501 */       boolean bvar_bin = StringUtils.equals(item.attributeValue("var_type"), "bin");
/*     */ 
/* 505 */       if (length_type.equals("CONST")) {
/* 506 */         int length = Integer.parseInt(slen);
/* 507 */         return constlen(length); }
/* 508 */       if (length_type.equals("VAR2"))
/* 509 */         return varlen(2, bvar_bin);
/* 510 */       if (length_type.equals("VAR3")) {
/* 511 */         return varlen(3, bvar_bin);
/*     */       }
/* 513 */       throw new HiException("231404", field_id);
/*     */     }
/*     */ 
/*     */     public static HiParser8583Handler2.Parser constlen(int len)
/*     */     {
/* 520 */       return new HiParser8583Handler2.Parser(len) { private final int val$len;
/*     */ 
/*     */         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { ctx.len = this.val$len;
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public static HiParser8583Handler2.Parser itemParser(Element item, boolean isPutTlvList)
/*     */       throws HiException
/*     */     {
/*     */       HiParser8583Handler2.Parser n;
/* 531 */       String field_id = item.attributeValue("field_id");
/* 532 */       boolean bTlv = (isPutTlvList) && (item.elements("Tlv").size() > 0);
/*     */ 
/* 535 */       HiParser8583Handler2.Parser lenParser = lenParser(field_id, item);
/* 536 */       HiParser8583Handler2.Parser dataParser = dataParser(field_id, item);
/*     */ 
/* 538 */       if (bTlv)
/* 539 */         n = tlvParser(field_id, item);
/*     */       else {
/* 541 */         n = saveParser(field_id, item);
/*     */       }
/* 543 */       return seq(new HiParser8583Handler2.Parser[] { lenParser, dataParser, n });
/*     */     }
/*     */ 
/*     */     private static void parserTlv(HiETF etfBody, byte[] dataBytes, String field_id, HiParser8583Handler2.Parser tlvParser, Logger log)
/*     */       throws HiException
/*     */     {
/* 560 */       if (dataBytes == null) {
/* 561 */         return;
/*     */       }
/* 563 */       if (log.isInfoEnabled()) {
/* 564 */         log.info(HiParser8583Handler2.sm.getString("HiParser8583Handler.parserTlv0", field_id, new String(dataBytes)));
/*     */       }
/*     */ 
/* 568 */       ByteArrayInputStream in = new ByteArrayInputStream(dataBytes);
/* 569 */       HiParser8583Handler2.ParseContext innerctx = new HiParser8583Handler2.ParseContext(in, etfBody, log);
/* 570 */       while (in.available() > 0)
/*     */       {
/* 572 */         tlvParser.parse(innerctx);
/*     */       }
/* 574 */       in = null;
/*     */ 
/* 576 */       if (log.isInfoEnabled())
/* 577 */         log.info(HiParser8583Handler2.sm.getString("HiParser8583Handler.parserTlvOk", field_id));
/*     */     }
/*     */ 
/*     */     public static HiParser8583Handler2.Parser tlvParser(String field_id, Element item)
/*     */     {
/* 583 */       HiParser8583Handler2.Parser tlvParser = tlv(item);
/*     */ 
/* 585 */       return new HiParser8583Handler2.Parser(field_id, tlvParser) { private final String val$field_id;
/*     */         private final HiParser8583Handler2.Parser val$tlvParser;
/*     */ 
/*     */         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { HiParser8583Handler2.Parsers.access$200(ctx.etf, ctx.retString.getBytes(), this.val$field_id, this.val$tlvParser, ctx.log);
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public static HiParser8583Handler2.Parser saveParser(String field_id, Element item)
/*     */     {
/* 595 */       String itemName = item.attributeValue("etf_name");
/*     */ 
/* 598 */       return new HiParser8583Handler2.Parser(itemName, field_id) { private final String val$itemName;
/*     */         private final String val$field_id;
/*     */ 
/*     */         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { ctx.etf.setGrandChildNode(this.val$itemName, ctx.retString);
/*     */ 
/* 602 */           if (ctx.log.isInfoEnabled())
/* 603 */             ctx.log.info(HiParser8583Handler2.sm.getString("HiParser8583Handler.parserItem1", this.val$field_id, this.val$itemName, String.valueOf(ctx.len), ctx.retString));
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public static HiParser8583Handler2.Parser dataParser(String field_id, Element item)
/*     */       throws HiException
/*     */     {
/* 614 */       String data_type = item.attributeValue("data_type");
/*     */ 
/* 617 */       String pro_dll1 = item.attributeValue("pro_dll");
/* 618 */       String pro_func1 = item.attributeValue("pro_func");
/*     */ 
/* 620 */       boolean bconvert = StringUtils.equals(item.attributeValue("convert"), "hex");
/*     */ 
/* 623 */       boolean bSpec = (StringUtils.isNotBlank(pro_dll1)) && (StringUtils.isNotBlank(pro_func1));
/*     */ 
/* 627 */       boolean balign_left = StringUtils.equals(item.attributeValue("align_mode"), "left");
/*     */ 
/* 631 */       if ((data_type.equals("CharASCII")) || (data_type.equals("NumASCII")))
/*     */       {
/* 633 */         return ascii(bconvert, bSpec, pro_dll1, pro_func1); }
/* 634 */       if ((data_type.equals("ASCBCD")) || (data_type.equals("NumBCD")) || (data_type.equals("BIT")))
/*     */       {
/* 637 */         return bcd(balign_left, bSpec, pro_dll1, pro_func1);
/*     */       }
/* 639 */       throw new HiException("231406", field_id);
/*     */     }
/*     */ 
/*     */     private static String readBcd(ByteArrayInputStream in, int length, int radix, boolean balign_left, boolean bSpec, String pro_dll1, String pro_func1, Logger log)
/*     */       throws HiException
/*     */     {
/* 662 */       int bcdLen = length / radix;
/* 663 */       int rsvLen = length % radix;
/* 664 */       byte[] retBytes = null;
/*     */ 
/* 666 */       if (rsvLen != 0) {
/* 667 */         retBytes = HiParser8583Handler2.access$000(in, bcdLen + 1);
/*     */ 
/* 669 */         if (bSpec) {
/* 670 */           retBytes = specProc(log, pro_dll1, pro_func1, retBytes);
/*     */         }
/*     */ 
/* 673 */         if (balign_left) {
/* 674 */           return StringUtils.substring(bcd2Str(retBytes), 0, length);
/*     */         }
/* 676 */         return StringUtils.substring(bcd2Str(retBytes), radix - rsvLen);
/*     */       }
/*     */ 
/* 680 */       retBytes = HiParser8583Handler2.access$000(in, bcdLen);
/*     */ 
/* 682 */       if (bSpec) {
/* 683 */         retBytes = specProc(log, pro_dll1, pro_func1, retBytes);
/*     */       }
/*     */ 
/* 686 */       return bcd2Str(retBytes);
/*     */     }
/*     */ 
/*     */     private static byte[] specProc(Logger log, String pro_dll1, String pro_func1, byte[] values)
/*     */       throws HiException
/*     */     {
/* 693 */       HiMethodItem pro_method1 = HiItemHelper.getMethod(pro_dll1, pro_func1);
/*     */ 
/* 695 */       values = HiItemHelper.execMethod(pro_method1, values, log);
/* 696 */       return values;
/*     */     }
/*     */ 
/*     */     public static HiParser8583Handler2.Parser bcd(boolean balign_left, boolean bSpec, String pro_dll1, String pro_func1)
/*     */     {
/* 703 */       return new HiParser8583Handler2.Parser(balign_left, bSpec, pro_dll1, pro_func1) { private final boolean val$balign_left;
/*     */         private final boolean val$bSpec;
/*     */         private final String val$pro_dll1;
/*     */         private final String val$pro_func1;
/*     */ 
/*     */         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { ctx.retString = HiParser8583Handler2.Parsers.access$300(ctx.in, ctx.len, 2, this.val$balign_left, this.val$bSpec, this.val$pro_dll1, this.val$pro_func1, ctx.log);
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public static HiParser8583Handler2.Parser ascii(boolean bconvert, boolean bSpec, String pro_dll1, String pro_func1)
/*     */     {
/* 714 */       return new HiParser8583Handler2.Parser(bSpec, pro_dll1, pro_func1, bconvert)
/*     */       {
/*     */         private final boolean val$bSpec;
/*     */         private final String val$pro_dll1;
/*     */         private final String val$pro_func1;
/*     */         private final boolean val$bconvert;
/*     */ 
/*     */         public void parse(HiParser8583Handler2.ParseContext ctx)
/*     */           throws HiException
/*     */         {
/*     */           String data_value;
/* 716 */           int length = ctx.len;
/*     */ 
/* 718 */           byte[] retBytes = HiParser8583Handler2.access$000(ctx.in, length);
/*     */ 
/* 720 */           if (this.val$bSpec) {
/* 721 */             retBytes = HiParser8583Handler2.Parsers.access$400(ctx.log, this.val$pro_dll1, this.val$pro_func1, retBytes);
/*     */           }
/*     */ 
/* 726 */           if (this.val$bconvert)
/* 727 */             data_value = HiParser8583Handler2.Parsers.access$500(retBytes);
/*     */           else {
/* 729 */             data_value = new String(retBytes);
/*     */           }
/* 731 */           ctx.retString = data_value;
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public static HiParser8583Handler2.Parser tlv(Element item)
/*     */     {
/* 738 */       String field_id = item.attributeValue("field_id");
/* 739 */       Map tags = new HashMap();
/*     */ 
/* 741 */       Iterator it = item.elements("Tlv").iterator();
/* 742 */       while (it.hasNext()) {
/* 743 */         Element tagItem = (Element)it.next();
/* 744 */         String tag = tagItem.attributeValue("tag");
/*     */ 
/* 746 */         String data_type = tagItem.attributeValue("data_type");
/*     */ 
/* 748 */         String etf_Name = tagItem.attributeValue("etf_name");
/*     */ 
/* 751 */         tags.put(tag, tlvValue(tag, data_type, etf_Name));
/*     */       }
/*     */ 
/* 755 */       if (tags.size() == 0) {
/* 756 */         return null;
/*     */       }
/* 758 */       return new HiParser8583Handler2.Parser(tags, field_id) { private final Map val$tags;
/*     */         private final String val$field_id;
/*     */ 
/*     */         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { parserTlvItem(ctx, ctx.etf, ctx.in);
/*     */         }
/*     */ 
/*     */         private void parserTlvItem(HiParser8583Handler2.ParseContext ctx, HiETF etfBody, ByteArrayInputStream in) throws HiException
/*     */         {
/* 765 */           if (ctx.log.isDebugEnabled()) {
/* 766 */             ctx.log.debug("parserTlvItem() start");
/*     */           }
/*     */ 
/* 770 */           byte[] bytes = new byte[1];
/* 771 */           String tag = getTlvTag(ctx.log, in);
/*     */ 
/* 774 */           int length = getTlvlen(in);
/*     */ 
/* 776 */           if (length == 0) {
/* 777 */             return;
/*     */           }
/*     */ 
/* 780 */           if (ctx.log.isDebugEnabled()) {
/* 781 */             ctx.log.debug("Tlv: length=[" + length + "]");
/*     */           }
/*     */ 
/* 784 */           bytes = new byte[length];
/* 785 */           if (in.read(bytes, 0, length) < length) {
/* 786 */             throw new HiException("231410", "TLV: 取子域值有误, 需长度[" + length + "]");
/*     */           }
/*     */ 
/* 791 */           HiParser8583Handler2.Parser p = (HiParser8583Handler2.Parser)this.val$tags.get(tag);
/*     */ 
/* 793 */           if (p == null) {
/* 794 */             throw new HiException("231427", this.val$field_id, tag);
/*     */           }
/*     */ 
/* 798 */           ctx.retBytes = bytes;
/* 799 */           p.parse(ctx);
/*     */         }
/*     */ 
/*     */         private int getTlvlen(ByteArrayInputStream in)
/*     */           throws HiException
/*     */         {
/* 805 */           int length = 0;
/* 806 */           byte[] bytes = new byte[1];
/* 807 */           if (in.read(bytes, 0, 1) < 1) {
/* 808 */             throw new HiException("231410", "TLV: 取子域长度有误");
/*     */           }
/*     */ 
/* 812 */           if (bytes[0] >= 0)
/*     */           {
/* 814 */             length = bytes[0];
/*     */           } else {
/* 816 */             int preLen = 0;
/* 817 */             if (bytes[0] == -127)
/*     */             {
/* 819 */               preLen = 1;
/* 820 */             } else if (bytes[0] == -126)
/*     */             {
/* 822 */               preLen = 2;
/*     */             }
/*     */             else throw new HiException("231426", String.valueOf(bytes[0]));
/*     */ 
/* 830 */             for (int i = 0; i < preLen; ++i) {
/* 831 */               int len = in.read();
/* 832 */               if (len == -1) {
/* 833 */                 throw new HiException("231410", "TLV: 取子域长度有误");
/*     */               }
/*     */ 
/* 838 */               length = length * 256 + len;
/*     */             }
/*     */           }
/*     */ 
/* 842 */           return length;
/*     */         }
/*     */ 
/*     */         private String getTlvTag(Logger log, ByteArrayInputStream in) throws HiException
/*     */         {
/* 847 */           byte[] bytes = new byte[1];
/* 848 */           if (in.read(bytes, 0, 1) < 1) {
/* 849 */             throw new HiException("231410", "TLV: 取tag标签有误");
/*     */           }
/*     */ 
/* 853 */           String tag = HiParser8583Handler2.Parsers.access$500(bytes);
/* 854 */           if ((tag.endsWith("F")) || (tag.endsWith("f"))) {
/* 855 */             if (in.read(bytes, 0, 1) < 1) {
/* 856 */               throw new HiException("231410", "TLV: 取tag标签有误");
/*     */             }
/*     */ 
/* 861 */             tag = tag + HiParser8583Handler2.Parsers.access$500(bytes);
/*     */           }
/*     */ 
/* 864 */           if (log.isInfoEnabled()) {
/* 865 */             log.info("Tlv: tag=[" + tag + "]");
/*     */           }
/* 867 */           return tag;
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public static HiParser8583Handler2.Parser tlvValue(String tag, String data_type, String etf_name)
/*     */     {
/* 876 */       boolean isasci = (data_type.equals("CharASCII")) || (data_type.equals("NumASCII"));
/*     */ 
/* 880 */       boolean isbcd = (data_type.equals("BIT")) || (data_type.equals("ASCBCD")) || (data_type.equals("NumBCD"));
/*     */ 
/* 885 */       return new HiParser8583Handler2.Parser(isasci, isbcd, tag, etf_name) { private final boolean val$isasci;
/*     */         private final boolean val$isbcd;
/*     */         private final String val$tag;
/*     */         private final String val$etf_name;
/*     */ 
/*     */         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { byte[] bytes = ctx.retBytes;
/* 888 */           int length = ctx.len;
/* 889 */           String data_value = "";
/*     */ 
/* 891 */           if (this.val$isasci) {
/* 892 */             data_value = new String(bytes);
/* 893 */           } else if (this.val$isbcd) {
/* 894 */             data_value = HiParser8583Handler2.Parsers.access$500(bytes);
/* 895 */             length *= 2;
/*     */           } else {
/* 897 */             new HiException("231425", this.val$tag);
/*     */           }
/*     */ 
/* 902 */           ctx.etf.setGrandChildNode(this.val$etf_name, data_value);
/*     */ 
/* 904 */           if (ctx.log.isInfoEnabled())
/* 905 */             ctx.log.info(HiParser8583Handler2.sm.getString("HiParser8583Handler.parserTlv1", this.val$tag, this.val$etf_name, String.valueOf(length), data_value));
/*     */         }
/*     */       };
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface Parser
/*     */   {
/*     */     public abstract void parse(HiParser8583Handler2.ParseContext paramParseContext)
/*     */       throws HiException;
/*     */   }
/*     */ 
/*     */   public static class ParseContext
/*     */   {
/*     */     final Logger log;
/*     */     final ByteArrayInputStream in;
/*     */     final HiETF etf;
/*     */     public int len;
/*     */     public byte[] retBytes;
/*     */     public String retString;
/*     */ 
/*     */     public ParseContext(ByteArrayInputStream in, HiETF etfBody, Logger log)
/*     */     {
/* 398 */       this.in = in;
/* 399 */       this.etf = etfBody;
/* 400 */       this.log = log;
/*     */     }
/*     */   }
/*     */ }