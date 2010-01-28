 package com.hisun.handler;
 
 import com.hisun.engine.invoke.impl.HiItemHelper;
 import com.hisun.engine.invoke.impl.HiMethodItem;
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiConvHelper;
 import com.hisun.util.HiResource;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiXmlHelper;
 import java.io.ByteArrayInputStream;
 import java.net.URL;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class HiParser8583Handler2
   implements IHandler, IServerInitListener
 {
   private static final String HEADER = "Header";
   private String version;
   private String cfgFile;
   private String pcfgFile;
   private Map heads;
   private Map items;
   Logger log;
   static final HiStringManager sm = HiStringManager.getManager();
 
   public HiParser8583Handler2()
   {
     this.version = "1";
     this.cfgFile = null;
 
     this.pcfgFile = null;
 
     this.heads = new HashMap();
     this.items = new HashMap();
 
     this.log = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
   }
 
   public void setCFG(String cfgFile)
   {
     this.cfgFile = cfgFile;
   }
 
   public void setPCFG(String pcfgFile) {
     this.pcfgFile = pcfgFile;
   }
 
   public String getVersion() {
     return this.version;
   }
 
   public void setVersion(String version) {
     this.version = version;
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     HiByteBuffer plainBytes = (HiByteBuffer)msg.getBody();
 
     if (this.log.isInfoEnabled()) {
       this.log.info(sm.getString("HiParser8583Handler.parser0"));
       this.log.info(sm.getString("HiParser8583Handler.parser1", plainBytes.toString()));
 
       this.log.info(sm.getString("HiParser8583Handler.parser2", HiConvHelper.bcd2AscStr(plainBytes.getBytes())));
     }
 
     HiETF etfBody = HiETFFactory.createXmlETF();
 
     ByteArrayInputStream in = new ByteArrayInputStream(plainBytes.getBytes());
 
     ParseContext pctx = new ParseContext(in, etfBody, this.log);
 
     if (StringUtils.equals(this.version, "2"))
     {
       if ((this.heads.size() > 0) && (plainBytes.charAt(0) != 48))
       {
         convertHeaderToETF(pctx); }
     }
     else if (this.heads.size() > 0)
     {
       convertHeaderToETF(pctx);
     }
 
     convertToETF(pctx);
 
     msg.setBody(etfBody);
 
     if (this.log.isInfoEnabled())
       this.log.info(sm.getString("HiParser8583Handler.parsered"));
   }
 
   public void convertHeaderToETF(ParseContext ctx)
     throws HiException
   {
     if (this.log.isInfoEnabled()) {
       this.log.info("Parser 8583 Header－Start");
     }
     String value = makeEtfItem(ctx, this.heads, 0);
     int size = Integer.parseInt(value, 16);
 
     if (size != 0) {
       int hsize = this.heads.size();
       for (int i = 1; i < hsize; ++i) {
         makeEtfItem(ctx, this.heads, i);
       }
     }
 
     if (this.log.isInfoEnabled())
       this.log.info("Parser 8583 Header－End");
   }
 
   public void convertToETF(ParseContext ctx)
     throws HiException
   {
     makeEtfItem(ctx, this.items, 0);
 
     String bitMap = getBitMap(ctx);
 
     for (int i = 1; i < bitMap.length(); ++i)
       if (bitMap.charAt(i) == '1')
         makeEtfItem(ctx, this.items, i + 1);
   }
 
   private String getBitMap(ParseContext ctx)
     throws HiException
   {
     if (this.log.isInfoEnabled()) {
       this.log.info(sm.getString("HiParser8583Handler.getBitMap0"));
     }
 
     byte[] retBytes = readBytes(ctx.in, 8);
 
     String hexStr = HiConvHelper.bcd2AscStr(retBytes);
 
     String bitMap = HiConvHelper.hex2Binary(hexStr);
 
     if (bitMap.charAt(0) == '1')
     {
       retBytes = readBytes(ctx.in, 8);
       String extHexStr = HiConvHelper.bcd2AscStr(retBytes);
 
       bitMap = bitMap + HiConvHelper.hex2Binary(extHexStr);
       hexStr = hexStr + extHexStr;
     }
 
     if (this.log.isInfoEnabled()) {
       this.log.info(sm.getString("HiParser8583Handler.getBitMap1", hexStr));
     }
     return bitMap;
   }
 
   private String makeEtfItem(ParseContext ctx, Map ps, int i) throws HiException
   {
     int idx = i;
     if (this.log.isDebugEnabled()) {
       this.log.debug(sm.getString("HiParser8583Handler.parserItem0", String.valueOf(idx)));
     }
 
     Parser p = (Parser)ps.get(Integer.valueOf(i));
     if (p == null) {
       throw new HiException("231409", String.valueOf(idx));
     }
 
     p.parse(ctx);
     return ctx.retString;
   }
 
   private void checkCfg(Element cfgRoot, boolean isPutTlvList)
     throws HiException
   {
     if ((cfgRoot == null) || (cfgRoot.elements().size() < 65)) {
       throw new HiException("231400", this.cfgFile);
     }
 
     Element item = cfgRoot.element("Header");
     if (item != null) {
       if (isPutTlvList)
         checkItemCfg(item, false, this.heads);
       else {
         checkItemCfg(item, false, null);
       }
     }
 
     if (isPutTlvList)
       checkItemCfg(cfgRoot, isPutTlvList, this.items);
     else {
       checkItemCfg(cfgRoot, isPutTlvList, null);
     }
 
     item = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "0");
 
     if (item == null) {
       throw new HiException("231407", "0");
     }
 
     item = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "1");
 
     if (item == null)
       throw new HiException("231407", "1");
   }
 
   private void checkItemCfg(Element cfgRoot, boolean isPutTlvList, Map items)
     throws HiException
   {
     Iterator it = cfgRoot.elementIterator("Item");
 
     int i = 0;
 
     while (it.hasNext())
     {
       int id;
       Element item = (Element)it.next();
       String itemAttr = item.attributeValue("field_id");
       try
       {
         id = Integer.parseInt(itemAttr);
       } catch (NumberFormatException ne) {
         throw new HiException("231424", String.valueOf(i));
       }
 
       if (StringUtils.isBlank(item.attributeValue("etf_name")))
       {
         throw new HiException("231401", item.attributeValue("field_id"));
       }
 
       itemAttr = item.attributeValue("length_type");
 
       if (StringUtils.isBlank(itemAttr)) {
         throw new HiException("231402", item.attributeValue("field_id"));
       }
 
       if (itemAttr.equals("CONST")) {
         String lenAttr = item.attributeValue("length");
 
         if ((StringUtils.isBlank(lenAttr)) || (!(StringUtils.isNumeric(lenAttr))))
         {
           throw new HiException("231403", item.attributeValue("field_id"));
         }
       }
       else if ((itemAttr.equals("VAR2")) || (itemAttr.equals("VAR3")))
       {
         String len_type = item.attributeValue("var_type");
 
         if ((!(StringUtils.isEmpty(len_type))) && (!(StringUtils.equals(len_type, "bin")))) if (!(StringUtils.equals(len_type, "char")))
           {
             throw new HiException("231429", item.attributeValue("field_id"));
           }
       }
       else
       {
         throw new HiException("231404", item.attributeValue("field_id"));
       }
 
       itemAttr = item.attributeValue("data_type");
       if (StringUtils.isBlank(itemAttr)) {
         throw new HiException("231405", item.attributeValue("field_id"));
       }
 
       if ((!(itemAttr.equals("CharASCII"))) && (!(itemAttr.equals("NumASCII"))) && (!(itemAttr.equals("ASCBCD"))) && (!(itemAttr.equals("NumBCD")))) if (!(itemAttr.equals("BIT")))
         {
           throw new HiException("231406", item.attributeValue("field_id"));
         }
 
 
       itemAttr = item.attributeValue("align_mode");
       if ((!(StringUtils.isEmpty(itemAttr))) && (!(StringUtils.equals(itemAttr, "left")))) if (!(StringUtils.equals(itemAttr, "right")))
         {
           throw new HiException("231428", item.attributeValue("field_id"));
         }
 
 
       if (items != null) {
         items.put(Integer.valueOf(id), Parsers.itemParser(item, isPutTlvList));
       }
       ++i;
     }
   }
 
   public void serverInit(ServerEvent arg0) throws HiException {
     if (this.log.isInfoEnabled()) {
       this.log.info(sm.getString("HiParser8583Handler.init1", this.cfgFile));
     }
 
     Element cfgRoot = null;
     Element pcfgRoot = null;
 
     URL fileUrl = HiResource.getResource(this.cfgFile);
     SAXReader saxReader = new SAXReader();
     try
     {
       cfgRoot = saxReader.read(fileUrl).getRootElement();
 
       if (!(StringUtils.isEmpty(this.pcfgFile))) {
         fileUrl = HiResource.getResource(this.pcfgFile);
 
         pcfgRoot = saxReader.read(fileUrl).getRootElement();
       }
     }
     catch (DocumentException e) {
       throw new HiException("213319", fileUrl.getFile(), e);
     }
 
     checkCfg(cfgRoot, true);
 
     if (pcfgRoot != null)
     {
       checkCfg(pcfgRoot, false);
 
       HiContext.getCurrentContext().setProperty("8583_CFG_NODE", pcfgRoot);
     }
     else
     {
       HiContext.getCurrentContext().setProperty("8583_CFG_NODE", cfgRoot);
     }
 
     if (this.log.isInfoEnabled())
       this.log.info(sm.getString("HiParser8583Handler.init2", this.cfgFile));
   }
 
   private static byte[] readBytes(ByteArrayInputStream in, int length)
     throws HiException
   {
     byte[] retBytes = new byte[length];
     if (in.read(retBytes, 0, length) < length) {
       throw new HiException("231410", "报文长度不足");
     }
 
     return retBytes;
   }
 
   public static class Parsers
   {
     public static HiParser8583Handler2.Parser seq(HiParser8583Handler2.Parser[] ps)
     {
       return new HiParser8583Handler2.Parser(ps) { private final HiParser8583Handler2.Parser[] val$ps;
 
         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { for (int i = 0; i < this.val$ps.length; ++i)
             this.val$ps[i].parse(ctx);
         }
       };
     }
 
     private static int getHeadLen(ByteArrayInputStream in, int varLen, boolean bvar_bin)
       throws HiException
     {
       if (bvar_bin)
       {
         String len;
         int rsvLen = varLen % 2;
         int bcdLen = varLen / 2 + rsvLen;
         byte[] retBytes = HiParser8583Handler2.access$000(in, bcdLen);
 
         if (rsvLen != 0)
           len = StringUtils.substring(bcd2Str(retBytes), 1);
         else
           len = bcd2Str(retBytes);
         return Integer.parseInt(len);
       }
       byte[] len = HiParser8583Handler2.access$000(in, varLen);
       return Integer.parseInt(new String(len));
     }
 
     private static String bcd2Str(byte[] retBytes)
     {
       return HiConvHelper.bcd2AscStr(retBytes);
     }
 
     public static HiParser8583Handler2.Parser varlen(int l, boolean bvar_bin)
     {
       return new HiParser8583Handler2.Parser(l, bvar_bin) { private final int val$l;
         private final boolean val$bvar_bin;
 
         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { ctx.len = HiParser8583Handler2.Parsers.access$100(ctx.in, this.val$l, this.val$bvar_bin);
         }
       };
     }
 
     public static HiParser8583Handler2.Parser lenParser(String field_id, Element item)
       throws HiException
     {
       String length_type = item.attributeValue("length_type");
 
       String slen = item.attributeValue("length");
 
       boolean bvar_bin = StringUtils.equals(item.attributeValue("var_type"), "bin");
 
       if (length_type.equals("CONST")) {
         int length = Integer.parseInt(slen);
         return constlen(length); }
       if (length_type.equals("VAR2"))
         return varlen(2, bvar_bin);
       if (length_type.equals("VAR3")) {
         return varlen(3, bvar_bin);
       }
       throw new HiException("231404", field_id);
     }
 
     public static HiParser8583Handler2.Parser constlen(int len)
     {
       return new HiParser8583Handler2.Parser(len) { private final int val$len;
 
         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { ctx.len = this.val$len;
         }
       };
     }
 
     public static HiParser8583Handler2.Parser itemParser(Element item, boolean isPutTlvList)
       throws HiException
     {
       HiParser8583Handler2.Parser n;
       String field_id = item.attributeValue("field_id");
       boolean bTlv = (isPutTlvList) && (item.elements("Tlv").size() > 0);
 
       HiParser8583Handler2.Parser lenParser = lenParser(field_id, item);
       HiParser8583Handler2.Parser dataParser = dataParser(field_id, item);
 
       if (bTlv)
         n = tlvParser(field_id, item);
       else {
         n = saveParser(field_id, item);
       }
       return seq(new HiParser8583Handler2.Parser[] { lenParser, dataParser, n });
     }
 
     private static void parserTlv(HiETF etfBody, byte[] dataBytes, String field_id, HiParser8583Handler2.Parser tlvParser, Logger log)
       throws HiException
     {
       if (dataBytes == null) {
         return;
       }
       if (log.isInfoEnabled()) {
         log.info(HiParser8583Handler2.sm.getString("HiParser8583Handler.parserTlv0", field_id, new String(dataBytes)));
       }
 
       ByteArrayInputStream in = new ByteArrayInputStream(dataBytes);
       HiParser8583Handler2.ParseContext innerctx = new HiParser8583Handler2.ParseContext(in, etfBody, log);
       while (in.available() > 0)
       {
         tlvParser.parse(innerctx);
       }
       in = null;
 
       if (log.isInfoEnabled())
         log.info(HiParser8583Handler2.sm.getString("HiParser8583Handler.parserTlvOk", field_id));
     }
 
     public static HiParser8583Handler2.Parser tlvParser(String field_id, Element item)
     {
       HiParser8583Handler2.Parser tlvParser = tlv(item);
 
       return new HiParser8583Handler2.Parser(field_id, tlvParser) { private final String val$field_id;
         private final HiParser8583Handler2.Parser val$tlvParser;
 
         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { HiParser8583Handler2.Parsers.access$200(ctx.etf, ctx.retString.getBytes(), this.val$field_id, this.val$tlvParser, ctx.log);
         }
       };
     }
 
     public static HiParser8583Handler2.Parser saveParser(String field_id, Element item)
     {
       String itemName = item.attributeValue("etf_name");
 
       return new HiParser8583Handler2.Parser(itemName, field_id) { private final String val$itemName;
         private final String val$field_id;
 
         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { ctx.etf.setGrandChildNode(this.val$itemName, ctx.retString);
 
           if (ctx.log.isInfoEnabled())
             ctx.log.info(HiParser8583Handler2.sm.getString("HiParser8583Handler.parserItem1", this.val$field_id, this.val$itemName, String.valueOf(ctx.len), ctx.retString));
         }
       };
     }
 
     public static HiParser8583Handler2.Parser dataParser(String field_id, Element item)
       throws HiException
     {
       String data_type = item.attributeValue("data_type");
 
       String pro_dll1 = item.attributeValue("pro_dll");
       String pro_func1 = item.attributeValue("pro_func");
 
       boolean bconvert = StringUtils.equals(item.attributeValue("convert"), "hex");
 
       boolean bSpec = (StringUtils.isNotBlank(pro_dll1)) && (StringUtils.isNotBlank(pro_func1));
 
       boolean balign_left = StringUtils.equals(item.attributeValue("align_mode"), "left");
 
       if ((data_type.equals("CharASCII")) || (data_type.equals("NumASCII")))
       {
         return ascii(bconvert, bSpec, pro_dll1, pro_func1); }
       if ((data_type.equals("ASCBCD")) || (data_type.equals("NumBCD")) || (data_type.equals("BIT")))
       {
         return bcd(balign_left, bSpec, pro_dll1, pro_func1);
       }
       throw new HiException("231406", field_id);
     }
 
     private static String readBcd(ByteArrayInputStream in, int length, int radix, boolean balign_left, boolean bSpec, String pro_dll1, String pro_func1, Logger log)
       throws HiException
     {
       int bcdLen = length / radix;
       int rsvLen = length % radix;
       byte[] retBytes = null;
 
       if (rsvLen != 0) {
         retBytes = HiParser8583Handler2.access$000(in, bcdLen + 1);
 
         if (bSpec) {
           retBytes = specProc(log, pro_dll1, pro_func1, retBytes);
         }
 
         if (balign_left) {
           return StringUtils.substring(bcd2Str(retBytes), 0, length);
         }
         return StringUtils.substring(bcd2Str(retBytes), radix - rsvLen);
       }
 
       retBytes = HiParser8583Handler2.access$000(in, bcdLen);
 
       if (bSpec) {
         retBytes = specProc(log, pro_dll1, pro_func1, retBytes);
       }
 
       return bcd2Str(retBytes);
     }
 
     private static byte[] specProc(Logger log, String pro_dll1, String pro_func1, byte[] values)
       throws HiException
     {
       HiMethodItem pro_method1 = HiItemHelper.getMethod(pro_dll1, pro_func1);
 
       values = HiItemHelper.execMethod(pro_method1, values, log);
       return values;
     }
 
     public static HiParser8583Handler2.Parser bcd(boolean balign_left, boolean bSpec, String pro_dll1, String pro_func1)
     {
       return new HiParser8583Handler2.Parser(balign_left, bSpec, pro_dll1, pro_func1) { private final boolean val$balign_left;
         private final boolean val$bSpec;
         private final String val$pro_dll1;
         private final String val$pro_func1;
 
         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { ctx.retString = HiParser8583Handler2.Parsers.access$300(ctx.in, ctx.len, 2, this.val$balign_left, this.val$bSpec, this.val$pro_dll1, this.val$pro_func1, ctx.log);
         }
       };
     }
 
     public static HiParser8583Handler2.Parser ascii(boolean bconvert, boolean bSpec, String pro_dll1, String pro_func1)
     {
       return new HiParser8583Handler2.Parser(bSpec, pro_dll1, pro_func1, bconvert)
       {
         private final boolean val$bSpec;
         private final String val$pro_dll1;
         private final String val$pro_func1;
         private final boolean val$bconvert;
 
         public void parse(HiParser8583Handler2.ParseContext ctx)
           throws HiException
         {
           String data_value;
           int length = ctx.len;
 
           byte[] retBytes = HiParser8583Handler2.access$000(ctx.in, length);
 
           if (this.val$bSpec) {
             retBytes = HiParser8583Handler2.Parsers.access$400(ctx.log, this.val$pro_dll1, this.val$pro_func1, retBytes);
           }
 
           if (this.val$bconvert)
             data_value = HiParser8583Handler2.Parsers.access$500(retBytes);
           else {
             data_value = new String(retBytes);
           }
           ctx.retString = data_value;
         }
       };
     }
 
     public static HiParser8583Handler2.Parser tlv(Element item)
     {
       String field_id = item.attributeValue("field_id");
       Map tags = new HashMap();
 
       Iterator it = item.elements("Tlv").iterator();
       while (it.hasNext()) {
         Element tagItem = (Element)it.next();
         String tag = tagItem.attributeValue("tag");
 
         String data_type = tagItem.attributeValue("data_type");
 
         String etf_Name = tagItem.attributeValue("etf_name");
 
         tags.put(tag, tlvValue(tag, data_type, etf_Name));
       }
 
       if (tags.size() == 0) {
         return null;
       }
       return new HiParser8583Handler2.Parser(tags, field_id) { private final Map val$tags;
         private final String val$field_id;
 
         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { parserTlvItem(ctx, ctx.etf, ctx.in);
         }
 
         private void parserTlvItem(HiParser8583Handler2.ParseContext ctx, HiETF etfBody, ByteArrayInputStream in) throws HiException
         {
           if (ctx.log.isDebugEnabled()) {
             ctx.log.debug("parserTlvItem() start");
           }
 
           byte[] bytes = new byte[1];
           String tag = getTlvTag(ctx.log, in);
 
           int length = getTlvlen(in);
 
           if (length == 0) {
             return;
           }
 
           if (ctx.log.isDebugEnabled()) {
             ctx.log.debug("Tlv: length=[" + length + "]");
           }
 
           bytes = new byte[length];
           if (in.read(bytes, 0, length) < length) {
             throw new HiException("231410", "TLV: 取子域值有误, 需长度[" + length + "]");
           }
 
           HiParser8583Handler2.Parser p = (HiParser8583Handler2.Parser)this.val$tags.get(tag);
 
           if (p == null) {
             throw new HiException("231427", this.val$field_id, tag);
           }
 
           ctx.retBytes = bytes;
           p.parse(ctx);
         }
 
         private int getTlvlen(ByteArrayInputStream in)
           throws HiException
         {
           int length = 0;
           byte[] bytes = new byte[1];
           if (in.read(bytes, 0, 1) < 1) {
             throw new HiException("231410", "TLV: 取子域长度有误");
           }
 
           if (bytes[0] >= 0)
           {
             length = bytes[0];
           } else {
             int preLen = 0;
             if (bytes[0] == -127)
             {
               preLen = 1;
             } else if (bytes[0] == -126)
             {
               preLen = 2;
             }
             else throw new HiException("231426", String.valueOf(bytes[0]));
 
             for (int i = 0; i < preLen; ++i) {
               int len = in.read();
               if (len == -1) {
                 throw new HiException("231410", "TLV: 取子域长度有误");
               }
 
               length = length * 256 + len;
             }
           }
 
           return length;
         }
 
         private String getTlvTag(Logger log, ByteArrayInputStream in) throws HiException
         {
           byte[] bytes = new byte[1];
           if (in.read(bytes, 0, 1) < 1) {
             throw new HiException("231410", "TLV: 取tag标签有误");
           }
 
           String tag = HiParser8583Handler2.Parsers.access$500(bytes);
           if ((tag.endsWith("F")) || (tag.endsWith("f"))) {
             if (in.read(bytes, 0, 1) < 1) {
               throw new HiException("231410", "TLV: 取tag标签有误");
             }
 
             tag = tag + HiParser8583Handler2.Parsers.access$500(bytes);
           }
 
           if (log.isInfoEnabled()) {
             log.info("Tlv: tag=[" + tag + "]");
           }
           return tag;
         }
       };
     }
 
     public static HiParser8583Handler2.Parser tlvValue(String tag, String data_type, String etf_name)
     {
       boolean isasci = (data_type.equals("CharASCII")) || (data_type.equals("NumASCII"));
 
       boolean isbcd = (data_type.equals("BIT")) || (data_type.equals("ASCBCD")) || (data_type.equals("NumBCD"));
 
       return new HiParser8583Handler2.Parser(isasci, isbcd, tag, etf_name) { private final boolean val$isasci;
         private final boolean val$isbcd;
         private final String val$tag;
         private final String val$etf_name;
 
         public void parse(HiParser8583Handler2.ParseContext ctx) throws HiException { byte[] bytes = ctx.retBytes;
           int length = ctx.len;
           String data_value = "";
 
           if (this.val$isasci) {
             data_value = new String(bytes);
           } else if (this.val$isbcd) {
             data_value = HiParser8583Handler2.Parsers.access$500(bytes);
             length *= 2;
           } else {
             new HiException("231425", this.val$tag);
           }
 
           ctx.etf.setGrandChildNode(this.val$etf_name, data_value);
 
           if (ctx.log.isInfoEnabled())
             ctx.log.info(HiParser8583Handler2.sm.getString("HiParser8583Handler.parserTlv1", this.val$tag, this.val$etf_name, String.valueOf(length), data_value));
         }
       };
     }
   }
 
   public static abstract interface Parser
   {
     public abstract void parse(HiParser8583Handler2.ParseContext paramParseContext)
       throws HiException;
   }
 
   public static class ParseContext
   {
     final Logger log;
     final ByteArrayInputStream in;
     final HiETF etf;
     public int len;
     public byte[] retBytes;
     public String retString;
 
     public ParseContext(ByteArrayInputStream in, HiETF etfBody, Logger log)
     {
       this.in = in;
       this.etf = etfBody;
       this.log = log;
     }
   }
 }