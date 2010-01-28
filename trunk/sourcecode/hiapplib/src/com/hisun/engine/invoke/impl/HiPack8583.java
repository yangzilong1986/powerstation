 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.invoke.HiStrategyFactory;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSysException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiConvHelper;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiXmlHelper;
 import java.io.ByteArrayOutputStream;
 import java.nio.ByteBuffer;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import java.util.StringTokenizer;
 import java.util.TreeMap;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 
 public class HiPack8583 extends HiEngineModel
 {
   private String type_code;
   private HiExpression type_code_expr;
   private String must_fields;
   private String opt_fields;
   private int bitmap_len;
   private boolean pack_header;
   private String hLen_pos;
   private int header_len;
   private String hLen_name;
   private String mLen_pos;
   private String mLen_name;
   private String pro_dll;
   private String pro_func;
   private HiMethodItem pro_method;
   private String pro_fields;
   private boolean isInit;
   private Element msgTypCodNode;
   private Element headerRoot;
   private Map itemNodeMap;
   private HashSet mustSet;
   private HashSet optSet;
   private HashSet proSet;
   private final int bitMapLen = 128;
   private final Logger logger;
 
   public HiPack8583()
   {
     this.bitmap_len = 0;
 
     this.pack_header = false;
 
     this.hLen_pos = null;
     this.header_len = -1;
     this.hLen_name = null;
 
     this.mLen_pos = null;
     this.mLen_name = null;
 
     this.pro_method = null;
 
     this.isInit = false;
 
     this.headerRoot = null;
 
     this.itemNodeMap = new TreeMap();
     this.mustSet = new HashSet();
 
     this.optSet = new HashSet();
     this.proSet = new HashSet();
     this.bitMapLen = 128;
 
     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
   }
 
   public String getNodeName() {
     return "Pack8583";
   }
 
   public void setMust_fields(String must_fields) {
     this.must_fields = must_fields;
   }
 
   public void setOpt_fields(String opt_fields) {
     this.opt_fields = opt_fields;
   }
 
   public void setPack_header(String pack_header) {
     if (StringUtils.equals(pack_header, "1"))
       this.pack_header = true;
   }
 
   public void setHlen_pos(String hLen_pos)
   {
     this.hLen_pos = hLen_pos;
   }
 
   public void setHeader_len(String header_len) {
     this.header_len = Integer.parseInt(header_len);
   }
 
   public void setMlen_pos(String mLen_pos) {
     this.mLen_pos = mLen_pos;
   }
 
   public void setType_code(String type_code) {
     this.type_code = type_code;
     this.type_code_expr = HiExpFactory.createExp(type_code);
   }
 
   public void setBitmap_len(String bit_len) throws HiException {
     try {
       this.bitmap_len = Integer.parseInt(bit_len.trim());
     } catch (Exception e) {
       this.logger.error("Pack8583 setBitmap_len(String)", e);
       throw new HiException("213237", bit_len, e);
     }
     if ((this.bitmap_len != 128) && (this.bitmap_len != 64)) {
       this.logger.error("Pack8583 setBitmap_len(String) 只允许64或128");
 
       throw new HiException("213237", bit_len);
     }
   }
 
   public void setPro_dll(String pro_dll) {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setPro_dll(String) - start");
     }
 
     this.pro_dll = pro_dll;
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("setPro_dll(String) - end");
   }
 
   public void setPro_func(String pro_func)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setPro_func(String) - start");
     }
 
     this.pro_func = pro_func;
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("setPro_func(String) - end");
   }
 
   public void setPro_fields(String pro_fields)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setPro_fields(String) - start");
     }
 
     this.pro_fields = pro_fields;
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("setPro_fields(String) - end");
   }
 
   public String toString()
   {
     return super.toString() + ":type_code[" + this.type_code + "]";
   }
 
   public void loadAfter()
     throws HiException
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("loadAfter() - start");
     }
     if (StringUtils.isEmpty(this.type_code)) {
       throw new HiException("213220", "消息类型域 type_code,不允许为空.");
     }
 
     if (StringUtils.isBlank(this.must_fields)) {
       throw new HiException("213221", "必需组包的域号列表 must_fields,不允许为空.");
     }
 
     checkValidField(this.must_fields, this.mustSet);
 
     if (StringUtils.isNotBlank(this.opt_fields)) {
       checkValidField(this.opt_fields, this.optSet);
     }
 
     if (StringUtils.isNotBlank(this.pro_fields)) {
       checkValidField(this.pro_fields, this.proSet);
     }
 
     if ((StringUtils.isNotBlank(this.pro_dll)) && (StringUtils.isNotBlank(this.pro_func))) {
       this.pro_method = HiItemHelper.getMethod(this.pro_dll, this.pro_func);
     }
 
     HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiByteStategy");
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("loadAfter() - end");
   }
 
   public void process(HiMessageContext ctx) throws HiException
   {
     try
     {
       super.process(ctx);
 
       HiMessage msg = ctx.getCurrentMsg();
       Logger log = HiLog.getLogger(msg);
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiPack8583.process00", HiEngineUtilities.getCurFlowStep(), this.type_code));
       }
 
       doProcess(ctx);
     } catch (HiException e) {
       throw e;
     } catch (Throwable te) {
       throw new HiSysException("213229", this.type_code, te);
     }
   }
 
   public void doProcess(HiMessageContext ctx) throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("doProcess(HiMessageContext) - start.\nPack8583 process start.");
     }
 
     initCfgNode(ctx);
 
     char[] bitMapByte = StringUtils.repeat("0", 128).toCharArray();
 
     ByteArrayOutputStream out = new ByteArrayOutputStream(128);
 
     convertToPlain(ctx, out, bitMapByte);
 
     HiByteBuffer plainOut = null;
     Object plainBody = HiItemHelper.getPlainObject(msg);
     if (plainBody instanceof HiByteBuffer)
       plainOut = (HiByteBuffer)plainBody;
     else if (plainBody instanceof byte[])
       plainOut = new HiByteBuffer((byte[])(byte[])plainBody);
     else {
       throw new HiException("213230", this.type_code);
     }
 
     int bodyLen = 0;
 
     byte[] msgTypByte = HiPack8583Helper.fitPlain(this.type_code_expr.getValue(ctx).getBytes(), this.msgTypCodNode, log);
     bodyLen = msgTypByte.length;
 
     String bitMap = null;
     if (this.bitmap_len == 128) {
       bitMapByte[0] = '1';
       bitMap = HiConvHelper.binary2hex(new String(bitMapByte));
     } else if (this.bitmap_len == 64) {
       bitMapByte[0] = '0';
       bitMap = HiConvHelper.binary2hex(new String(bitMapByte, 0, 64));
     }
     else if (bitMapByte[0] == '0') {
       bitMap = HiConvHelper.binary2hex(new String(bitMapByte, 0, 64));
     } else {
       bitMap = HiConvHelper.binary2hex(new String(bitMapByte));
     }
 
     byte[] outMapByte = HiConvHelper.ascStr2Bcd(bitMap);
     bodyLen += outMapByte.length;
 
     byte[] outByte = out.toByteArray();
     bodyLen += outByte.length;
 
     if (this.pack_header) {
       ByteArrayOutputStream hOut = new ByteArrayOutputStream(64);
       packHeader(ctx, hOut, bodyLen, log);
       byte[] hOutByte = hOut.toByteArray();
 
       plainOut.append(hOutByte, 0, hOutByte.length);
     }
 
     plainOut.append(msgTypByte, 0, msgTypByte.length);
     plainOut.append(outMapByte, 0, outMapByte.length);
     plainOut.append(outByte, 0, outByte.length);
 
     msg.setHeadItem("PlainText", plainOut);
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiPack8583.processOk", bitMap, plainOut.toString()));
     }
 
     if (log.isDebugEnabled()) {
       log.debug("Pack8583: OK =======================");
       log.debug("Pack8583: bitMap [" + bitMap + "]");
       log.debug("Pack8583: pack [" + plainOut.toString() + "]");
       log.debug("doProcess(HiMessageContext) - end.");
     }
   }
 
   private void convertToPlain(HiMessageContext ctx, ByteArrayOutputStream out, char[] bitMapByte) throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etfBody = msg.getETFBody();
     Logger log = HiLog.getLogger(msg);
 
     Map tlvMap = (Map)ctx.getProperty("8583_PACKTLV_VAL");
 
     Iterator it = this.itemNodeMap.entrySet().iterator();
 
     String type = "ETF";
 
     int idx = 0;
 
     while (it.hasNext())
     {
       String fldVal;
       Map.Entry itemEntry = (Map.Entry)it.next();
       Integer bitIdx = (Integer)itemEntry.getKey();
       idx = bitIdx.intValue() - 1;
 
       if ((this.bitmap_len == 64) && (idx >= 64)) {
         break;
       }
 
       Element itemNode = (Element)itemEntry.getValue();
 
       String fldName = bitIdx.toString();
       byte[] valByte = getTLV(tlvMap, fldName);
       if (valByte != null) {
         fldVal = new String(valByte);
         type = "TLV";
       }
       else {
         fldName = itemNode.attributeValue("etf_name");
 
         fldVal = etfBody.getGrandChildValue(fldName);
         type = "ETF";
 
         if (fldVal == null) {
           if (this.mustSet.contains(bitIdx));
           throw new HiException("213226", bitIdx.toString(), fldName);
         }
 
         if (StringUtils.equals(itemNode.attributeValue("convert"), "hex"))
         {
           valByte = HiConvHelper.ascStr2Bcd(fldVal);
         }
         else valByte = fldVal.getBytes();
       }
 
       valByte = specProc(itemNode, valByte, log);
       if ((this.pro_method != null) && (this.proSet.contains(bitIdx))) {
         valByte = HiItemHelper.execMethod(this.pro_method, valByte, log);
       }
       valByte = HiPack8583Helper.fitPlain(valByte, itemNode, log);
 
       out.write(valByte, 0, valByte.length);
 
       bitMapByte[idx] = '1';
 
       if (log.isInfoEnabled());
       log.info(sm.getString("HiPack8583.packItemOk", fldName, String.valueOf(idx + 1), String.valueOf(valByte.length), new String(valByte), type));
     }
 
     if ((idx >= 64) && (bitMapByte[0] == '0'))
       bitMapByte[0] = '1';
   }
 
   private void packHeader(HiMessageContext ctx, ByteArrayOutputStream out, int bodyLen, Logger log)
     throws HiException
   {
     if (log.isInfoEnabled()) {
       log.info("Pack 8583 Header Start==============>");
     }
 
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etfBody = msg.getETFBody();
     int msgLen = bodyLen;
 
     if (this.header_len == 0) {
       out.write(0);
       return; }
     if (this.header_len > 0) {
       HiItemHelper.addEtfItem(msg, this.hLen_name, Integer.toHexString(this.header_len));
 
       msgLen += this.header_len;
     }
 
     HiItemHelper.addEtfItem(msg, this.mLen_name, String.valueOf(msgLen));
 
     Iterator it = this.headerRoot.elementIterator();
     Element itemNode = null;
 
     while (it.hasNext()) {
       itemNode = (Element)it.next();
 
       String fldName = itemNode.attributeValue("etf_name");
 
       String fldVal = etfBody.getGrandChildValue(fldName);
 
       byte[] valByte = HiPack8583Helper.fitPlain(fldVal.getBytes(), itemNode, log);
 
       out.write(valByte, 0, valByte.length);
 
       if (log.isInfoEnabled());
       log.info(sm.getString("HiPack8583.packItemOk", fldName, String.valueOf(valByte.length), new String(valByte), "ETF"));
     }
 
     if (log.isInfoEnabled())
       log.info("<==============Pack 8583 Header End");
   }
 
   private byte[] fitConstPlain(Element itemNode, String field_id, String value, String data_type, int length, Logger log)
   {
     byte[] valBytes;
     int valLen = value.getBytes().length;
 
     if (valLen > length) {
       value = value.substring(0, length);
     }
 
     if ((!(data_type.endsWith("ASCII"))) && (length % 2 != 0)) {
       ++length;
     }
 
     if (valLen < length)
     {
       String fill_asc = itemNode.attributeValue("fill_asc");
 
       if (data_type.equals("CharASCII"))
       {
         value = fillCharRight(value, fill_asc, itemNode.attributeValue("align_mode"), length - valLen);
       }
       else
       {
         value = fillCharLeft(value, fill_asc, itemNode.attributeValue("align_mode"), length - valLen);
       }
 
     }
 
     if (!(data_type.endsWith("ASCII")))
       valBytes = HiConvHelper.ascStr2Bcd(value);
     else {
       valBytes = value.getBytes();
     }
 
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiPack8583.packItem0", field_id, new String(valBytes)));
     }
 
     return valBytes;
   }
 
   private byte[] fitVarPlain(Element itemNode, String field_id, String value, String data_type, int varLen, Logger log)
     throws HiException
   {
     byte[] valLenBytes;
     byte[] valBytes = value.getBytes();
     int valueLen = valBytes.length;
 
     int valueAllocLen = valueLen;
     int varAllocLen = varLen;
 
     if (!(data_type.endsWith("ASCII"))) {
       if (valueLen % 2 != 0)
       {
         value = fillCharLeft(value, itemNode.attributeValue("fill_asc"), itemNode.attributeValue("align_mode"), 1);
 
         ++valueAllocLen;
       }
 
       valBytes = HiConvHelper.ascStr2Bcd(value);
 
       valueAllocLen /= 2;
     }
 
     String valLenStr = String.valueOf(valueLen);
     if (valLenStr.length() > varLen) {
       throw new HiException("213228", String.valueOf(varLen), String.valueOf(field_id));
     }
 
     if (StringUtils.equals(itemNode.attributeValue("var_type"), "bin"))
     {
       if (varLen % 2 != 0) {
         ++varAllocLen;
       }
 
       if (valLenStr.length() < varAllocLen) {
         valLenStr = StringUtils.leftPad(valLenStr, varAllocLen, '0');
       }
 
       valLenBytes = HiConvHelper.ascStr2Bcd(valLenStr);
 
       varAllocLen /= 2;
     } else {
       if (valLenStr.length() < varLen) {
         valLenStr = StringUtils.leftPad(valLenStr, varLen, '0');
       }
 
       valLenBytes = valLenStr.getBytes();
     }
 
     ByteBuffer bb = ByteBuffer.allocate(varAllocLen + valueAllocLen);
     bb.put(valLenBytes);
     bb.put(valBytes);
 
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiPack8583.packItem1", String.valueOf(varLen), field_id, new String(bb.array())));
     }
 
     return bb.array();
   }
 
   private String fillChar(String value, String fill_char, String align_mode, int repeat)
   {
     if (repeat <= 0) {
       return value;
     }
     if (StringUtils.isEmpty(fill_char)) {
       fill_char = "0";
     }
 
     if (repeat > 1) {
       fill_char = StringUtils.repeat(fill_char, repeat);
     }
 
     if (StringUtils.equals(align_mode, "left"))
       value = value + fill_char;
     else {
       value = fill_char + value;
     }
     return value;
   }
 
   private String fillCharLeft(String value, String fill_asc, String align_mode, int repeat)
   {
     if (repeat <= 0) {
       return value;
     }
     if (StringUtils.isEmpty(fill_asc)) {
       fill_asc = "48";
     }
 
     String fill_char = "0";
     if (StringUtils.equals(fill_asc, "48"))
     {
       if (repeat > 1)
         fill_char = StringUtils.repeat(fill_char, repeat);
     }
     else {
       byte b = Integer.valueOf(fill_asc).byteValue();
       byte[] fillBytes = null;
       fillBytes = HiItemHelper.repeat(fillBytes, b, repeat);
       fill_char = new String(fillBytes);
     }
 
     if (StringUtils.equals(align_mode, "left"))
       value = value + fill_char;
     else {
       value = fill_char + value;
     }
     return value;
   }
 
   private String fillCharRight(String value, String fill_asc, String align_mode, int repeat)
   {
     if (repeat <= 0) {
       return value;
     }
     if (StringUtils.isEmpty(fill_asc)) {
       fill_asc = "32";
     }
 
     String fill_char = " ";
     if (StringUtils.equals(fill_asc, "32"))
     {
       if (repeat > 1)
         fill_char = StringUtils.repeat(fill_char, repeat);
     }
     else {
       byte b = Integer.valueOf(fill_asc).byteValue();
       byte[] fillBytes = null;
       fillBytes = HiItemHelper.repeat(fillBytes, b, repeat);
       fill_char = new String(fillBytes);
     }
 
     if (StringUtils.equals(align_mode, "right"))
       value = fill_char + value;
     else {
       value = value + fill_char;
     }
     return value;
   }
 
   private byte[] getTLV(Map tlvMap, String field_id)
   {
     if (tlvMap == null) {
       return null;
     }
 
     if (tlvMap.containsKey(field_id)) {
       return ((byte[])(byte[])tlvMap.get(field_id));
     }
 
     return null;
   }
 
   private synchronized void initCfgNode(HiMessageContext ctx) throws HiException
   {
     if (this.isInit) {
       return;
     }
 
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("first initCfgNode - start.");
     }
 
     Element cfgRoot = (Element)ctx.getProperty("8583_CFG_NODE");
     if (cfgRoot == null) {
       throw new HiException("213222", "");
     }
 
     this.msgTypCodNode = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "0");
 
     this.itemNodeMap.clear();
 
     putMapNode(cfgRoot, this.mustSet);
     putMapNode(cfgRoot, this.optSet);
 
     checkHeaderCfg(cfgRoot);
 
     this.isInit = true;
 
     if (log.isDebugEnabled()) {
       log.debug("must_fields [" + this.must_fields + "]");
       log.debug("opt_fields [" + this.opt_fields + "]");
       log.debug("first initCfgNode - end.");
     }
   }
 
   private void putMapNode(Element cfgRoot, Set fieldSet)
     throws HiException
   {
     Iterator it = fieldSet.iterator();
     while (it.hasNext()) {
       Integer fieldIdx = (Integer)it.next();
 
       Element itemNode = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", fieldIdx.toString());
 
       if (itemNode == null) {
         throw new HiException("213223", String.valueOf(fieldIdx));
       }
 
       this.itemNodeMap.put(fieldIdx, itemNode);
     }
   }
 
   private void checkValidField(String fields, HashSet fldSet)
     throws HiException
   {
     Integer idx;
     StringTokenizer tokenizer = new StringTokenizer(fields, "|");
     do { if (!(tokenizer.hasMoreElements())) return;
       try {
         idx = Integer.valueOf(tokenizer.nextToken());
 
         fldSet.add(idx);
       }
       catch (NumberFormatException ne) {
         throw new HiException("213225", getNodeName(), fields);
       }
     }
     while ((idx.intValue() > 1) && (idx.intValue() <= 128));
     throw new HiException("213224", fields);
   }
 
   private void checkHeaderCfg(Element cfgRoot)
     throws HiException
   {
     if (this.pack_header) {
       this.headerRoot = HiXmlHelper.selectSingleNode(cfgRoot, "Header");
       if (this.headerRoot == null) {
         throw new HiException("", "8583配置文件没有Header配置节点");
       }
 
       if (this.hLen_pos != null)
       {
         item = HiXmlHelper.selectSingleNode(this.headerRoot, "Item", "field_id", this.hLen_pos);
 
         if (item == null) {
           throw new HiException("", "8583配置文件没有该配置节点 field_id:" + this.hLen_pos);
         }
 
         this.hLen_name = item.attributeValue("etf_name");
 
         if (this.header_len == -1) {
           this.header_len = countHeaderLen();
         }
       }
 
       if (this.mLen_pos == null) {
         return;
       }
 
       Element item = HiXmlHelper.selectSingleNode(this.headerRoot, "Item", "field_id", this.mLen_pos);
 
       if (item == null) {
         throw new HiException("", "8583配置文件没有该配置节点 field_id:" + this.mLen_pos);
       }
 
       this.mLen_name = item.attributeValue("etf_name");
     }
   }
 
   private int countHeaderLen()
     throws HiException
   {
     if (this.headerRoot == null) {
       return 0;
     }
 
     return 46;
   }
 
   public byte[] specProc(Element element, byte[] values, Logger log) throws HiException
   {
     if (element == null)
       return values;
     String pro_dll1 = element.attributeValue("pro_dll");
     String pro_func1 = element.attributeValue("pro_func");
 
     if ((StringUtils.isNotBlank(pro_dll1)) && (StringUtils.isNotBlank(pro_func1)))
     {
       HiMethodItem pro_method1 = HiItemHelper.getMethod(pro_dll1, pro_func1);
 
       values = HiItemHelper.execMethod(pro_method1, values, log);
     }
     return values;
   }
 }