 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.HiITFEngineModel;
 import com.hisun.engine.invoke.HiStrategyFactory;
 import com.hisun.engine.invoke.load.HiDict;
 import com.hisun.engine.invoke.load.HiDictItem;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSysException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiConvHelper;
 import com.hisun.util.HiStringManager;
 import java.io.PrintStream;
 import org.apache.commons.lang.ArrayUtils;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiItem extends HiITFEngineModel
 {
   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   private final HiStringManager sm = HiStringManager.getManager();
   private String name;
   private String _dict_name = null;
   private int length = -1;
   private HiExpression lengthExp = null;
 
   private byte fill_asc = 32;
 
   private int offset = 0;
 
   private HiExpression exp = null;
 
   private String value = null;
   private String pro_dll;
   private String pro_func;
   protected HiMethodItem pro_method = null;
 
   private Parser itemParser = null;
 
   private String _data_type = null;
 
   private boolean _must_input = false;
 
   private byte _data_fmt = 0;
 
   public HiItem() throws HiException {
     initAtt();
   }
 
   public void initAtt()
     throws HiException
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("initAtt() - start");
     }
     HiContext context = HiContext.getCurrentContext();
     HiITFEngineModel itemAttribute = HiITFEngineModel.getItemAttribute(context);
 
     if (itemAttribute != null) {
       super.cloneProperty(itemAttribute);
       if (this.logger.isDebugEnabled()) {
         this.logger.debug("ITEM继承属性:[" + itemAttribute + "]");
       }
     }
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("initAtt() - end");
   }
 
   public void setExpression(String expression)
   {
     this.exp = HiExpFactory.createExp(expression);
   }
 
   public void setFill_asc(String fill_asc) throws HiException {
     try {
       Integer deliInt = Integer.valueOf(fill_asc);
       if ((deliInt.intValue() > 255) || (deliInt.intValue() < -128)) {
         throw new HiException("213101", new String[] { this.name, fill_asc });
       }
 
       this.fill_asc = deliInt.byteValue();
     } catch (Throwable te) {
       this.logger.error("setFill_asc(String)", te);
 
       throw new HiSysException("213103", new String[] { this.name, fill_asc }, te);
     }
   }
 
   public void setLength(String length) throws HiException
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setLength(String) - start:input[" + length + "]");
     }
 
     if (NumberUtils.isNumber(length)) {
       this.length = Integer.parseInt(length.trim());
     } else {
       if (length == null) {
         throw new HiSysException("213104", new String[] { this.name, length });
       }
 
       if (this.logger.isInfoEnabled()) {
         this.logger.info(getNodeName() + ":setLength(String)->lengthExpression[" + length + "]");
       }
 
       this.length = 0;
       this.lengthExp = HiExpFactory.createExp(length);
     }
     this._type = "fixed";
   }
 
   public void setName(String name) throws HiException {
     this.name = name;
   }
 
   public void setDict_name(String name) throws HiException {
     this._dict_name = name;
     cloneDictItem(this._dict_name);
   }
 
   public void setOffset(String offset) throws HiException {
     try {
       this.offset = Integer.parseInt(offset.trim());
     } catch (NumberFormatException e) {
       this.logger.error("setOffset(String)", e);
 
       throw new HiException("213106", new String[] { this.name, offset }, e);
     }
     catch (Throwable te) {
       this.logger.error("setOffset(String)", te);
 
       throw new HiSysException("213106", new String[] { this.name, offset }, te);
     }
   }
 
   public void setPro_dll(String pro_dll)
   {
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
 
   public void setValue(String value)
   {
     this.value = value;
   }
 
   protected int getOffset() {
     return this.offset;
   }
 
   protected String getValue() {
     return this.value;
   }
 
   public String getNodeName() {
     return "Item";
   }
 
   public String toString() {
     String returnString = super.toString() + ":name[" + this.name + "],type[" + this._type + "]";
 
     return returnString;
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     try {
       HiMessage msg = ctx.getCurrentMsg();
       Logger log = HiLog.getLogger(msg);
       if (log.isInfoEnabled()) {
         log.info(this.sm.getString("HiItem.process00", HiEngineUtilities.getCurFlowStep(), this.name));
       }
 
       doProcess(msg, ctx);
     } catch (HiException e) {
       throw e;
     } catch (Throwable te) {
       throw new HiSysException("213135", this.name, te);
     }
   }
 
   public void doProcess(HiMessage mess, HiMessageContext tranData)
     throws HiException
   {
     Logger log = HiLog.getLogger(mess);
     if (log.isDebugEnabled()) {
       log.debug("doProcess(HiMessage, HiMessageContext) - start");
     }
 
     String ect = mess.getHeadItem("ECT");
 
     if (StringUtils.equals(ect, "text/etf"))
       try
       {
         formatEtf(mess);
       }
       catch (HiException e)
       {
         throw e;
       }
     if (StringUtils.equals(ect, "text/plain")) {
       try {
         formatPlain(mess);
 
         if (this.exp != null)
         {
           HiItemHelper.addEtfItem(mess, this.name, execExpression(mess));
         }
 
       }
       catch (HiException e)
       {
         throw e;
       }
     }
     throw new HiException("213120", ect);
 
     if (log.isDebugEnabled())
       log.debug("doProcess(HiMessage, HiMessageContext) - end");
   }
 
   protected void formatPlain(HiMessage plainMsg)
     throws HiException
   {
     Logger log = HiLog.getLogger(plainMsg);
     if (log.isDebugEnabled()) {
       log.debug("formatPlain(HiMessage) - start");
     }
 
     int plain_offset = HiItemHelper.getPlainOffset(plainMsg) + this.offset;
 
     if (log.isDebugEnabled()) {
       log.debug(this.sm.getString("HiItem.formatPlain1", String.valueOf(plain_offset)));
     }
 
     int validLen = getValidStrMsgLen(plainMsg, plain_offset);
 
     if (log.isDebugEnabled()) {
       log.debug(this.sm.getString("HiItem.formatPlain2", String.valueOf(validLen)));
     }
 
     if (validLen <= 0) {
       HiItemHelper.setPlainOffset(plainMsg, plain_offset);
 
       if (!(this._necessary)) {
         if (log.isDebugEnabled()) {
           log.debug("formatPlain(HiMessage) - end");
         }
         return;
       }
 
       throw new HiException("213121", this.name, String.valueOf(plain_offset));
     }
 
     if (this.itemParser != null)
       this.itemParser.formatPlain(plainMsg, plain_offset, validLen);
     else
       throw new HiSysException("213136", this.name);
   }
 
   private void formatEtf(HiMessage etfMsg)
     throws HiException
   {
     Logger log = HiLog.getLogger(etfMsg);
     if (log.isDebugEnabled()) {
       log.debug("formatEtf(HiMessage) - start");
       log.debug(this.sm.getString("HiItem.formatEtf1"));
     }
 
     String itemVal = HiItemHelper.getEtfItem(etfMsg, this.name);
 
     if (itemVal == null)
     {
       if (this.value != null)
       {
         itemVal = this.value;
         if (log.isInfoEnabled())
           log.info(this.sm.getString("HiItem.formatEtf3", this.name, itemVal));
       }
       else if (!(this._necessary)) {
         if (log.isInfoEnabled()) {
           log.warn(this.sm.getString("HiItem.formatEtf4", this.name));
         }
         itemVal = "";
       }
       else
       {
         throw new HiException("213122", this.name, HiItemHelper.getCurEtfLevel(etfMsg) + this.name);
       }
 
       if ((this.value != null) && (this.exp != null))
       {
         HiItemHelper.addEtfItem(etfMsg, this.name, itemVal);
       }
     }
     else if (log.isInfoEnabled()) {
       log.info(this.sm.getString("HiItem.formatEtf2", this.name, itemVal));
     }
 
     if (this.exp != null) {
       try {
         itemVal = execExpression(etfMsg);
       } catch (HiException e) {
         if (!(this._necessary)) {
           log.warn("exp:" + this.exp + "计算失败!" + e.getMessage());
           itemVal = "";
         } else {
           throw e;
         }
       }
     }
     byte[] valBytes = itemVal.getBytes();
 
     valBytes = fmt2OutData(valBytes, itemVal);
 
     if (this.pro_method != null) {
       valBytes = HiItemHelper.execMethod(this.pro_method, valBytes, log);
     }
 
     if (this.itemParser != null)
     {
       this.itemParser.formatETF(etfMsg, valBytes);
     } else if (this.value != null)
     {
       addPlainItem(etfMsg, valBytes);
     }
     else
       throw new HiSysException("213137", this.name);
   }
 
   protected void addObjectItem(HiMessage msg, byte[] itemBytes)
     throws HiException
   {
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("addObjectItem(HiMessage, String) - start");
     }
 
     if (this.pro_method != null) {
       itemBytes = HiItemHelper.execMethod(this.pro_method, itemBytes, log);
     }
 
     String itemVal = fmt2InData(itemBytes);
 
     HiItemHelper.addEtfItem(msg, this.name, itemVal);
     HiMessageContext ctx = HiMessageContext.getCurrentMessageContext();
     checkInvalidData(msg, this.name);
 
     if (log.isInfoEnabled()) {
       log.info(this.sm.getString("HiItem.addObjectItem", this.name, String.valueOf(itemBytes.length), itemVal));
     }
 
     if (log.isDebugEnabled())
       log.debug("addObjectItem(HiMessage, String) - end");
   }
 
   private void addPlainItem(HiMessage strMsg, byte[] item)
     throws HiException
   {
     Logger log = HiLog.getLogger(strMsg);
     if (log.isDebugEnabled()) {
       log.debug("addPlainItem(HiMessage, String) - start");
     }
 
     HiByteBuffer plainBody = HiItemHelper.getPlainText(strMsg);
 
     if (this.offset > 0)
     {
       plainBody.repeat(32, this.offset);
     }
     if (log.isDebugEnabled()) {
       log.debug(this.sm.getString("HiItem.addPlainItem1", this.name, item));
     }
 
     plainBody.append(item);
 
     if (log.isDebugEnabled()) {
       log.debug(this.sm.getString("HiItem.addPlainItem2", plainBody));
     }
 
     if (log.isDebugEnabled())
       log.debug("addPlainItem(HiMessage, String) - end");
   }
 
   private int getValidStrMsgLen(HiMessage strMsg, int plainOffset)
   {
     HiByteBuffer plainBody = HiItemHelper.getPlainText(strMsg);
     return (plainBody.length() - plainOffset);
   }
 
   private String execExpression(HiMessage mess)
     throws HiException
   {
     Logger log = HiLog.getLogger(mess);
     if (log.isDebugEnabled()) {
       log.debug("execExpression(HiMessage) - start");
     }
 
     String val = this.exp.getValue(HiMessageContext.getCurrentMessageContext());
 
     if (log.isInfoEnabled()) {
       log.info(this.sm.getString("HiItem.execExpression", this.exp.getExp(), val));
     }
 
     if (val == null) {
       throw new HiException("215113", this.exp.getExp() + " return null, ");
     }
 
     if (log.isDebugEnabled()) {
       log.debug("execExpression(HiMessage) - end");
     }
     return val;
   }
 
   public void loadAfter()
     throws HiException
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("loadAfter() - start");
     }
 
     if (StringUtils.equals(this._type, "fixed")) {
       if ((this.length < 0) && (this.lengthExp == null))
       {
         throw new HiException("213107", this.name);
       }
 
       this.itemParser = new FixedParser(null);
     } else if (StringUtils.equals(this._type, "deli"))
     {
       this.itemParser = new DeliAscParser(null);
     } else if (StringUtils.equals(this._type, "deli_str")) {
       if (StringUtils.isEmpty(this._deli_str))
       {
         throw new HiException("213108", this.name);
       }
 
       this.itemParser = new DeliStrParser(null);
     } else if (StringUtils.equals(this._type, "ahead_len")) {
       if (this._head_len <= 0)
       {
         throw new HiException("213109", this.name);
       }
 
       if (StringUtils.equals(this._len_type, "bin"))
         this.itemParser = new AheadBinParser(null);
       else {
         this.itemParser = new AheadCharParser(null);
       }
     }
     else if (this.value == null)
     {
       throw new HiException("213110", this.name, this._type);
     }
 
     if ((StringUtils.isNotBlank(this.pro_dll)) && (StringUtils.isNotBlank(this.pro_func))) {
       this.pro_method = HiItemHelper.getMethod(this.pro_dll, this.pro_func);
     }
 
     if (this._data_fmt != 0) {
       this.fill_asc = 0;
       this._align_mode = "right";
     }
 
     HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiByteStategy");
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("loadAfter() - end");
   }
 
   private void cloneDictItem(String name)
     throws HiException
   {
     HiDictItem item = HiDict.get(HiContext.getCurrentContext(), name);
     System.out.println("HiDictItem:" + item);
     if (item == null) {
       return;
     }
     if (item._align_mode != null) {
       setAlign_mode(item._align_mode);
     }
     if (item._deli_asc != null) {
       setDeli_asc(item._deli_asc);
     }
     if (item._deli_str != null) {
       setDeli_str(item._deli_str);
     }
     if (item._head_len != null) {
       setHead_len(item._head_len);
     }
     if (item._include_len != null) {
       setInclude_len(item._include_len);
     }
     if (item._len_type != null) {
       setLen_type(item._len_type);
     }
     if (item._length != null) {
       setLength(item._length);
     }
     if (item._necessary != null) {
       setNecessary(item._necessary);
     }
     if (item._pass != null) {
       setPass(item._pass);
     }
     if (item._type != null) {
       setType(item._type);
     }
     if (item._seq_no != null) {
       setSeq_no(item._seq_no);
     }
     if (item._etf_name != null) {
       setName(item._etf_name);
     }
     if (item._must_input != null) {
       setMust_input(item._must_input);
     }
     if (item._data_type != null) {
       setData_type(item._data_type);
       if (StringUtils.equals(item._data_type, "9")) {
         if (this._align_mode == null) {
           setAlign_mode("right");
           setFill_asc("48");
         }
       }
       else if (this._align_mode == null)
         setAlign_mode("left");
     }
   }
 
   private boolean isNumber(String s)
   {
     for (int i = 0; i < s.length(); ++i) {
       char c = s.charAt(i);
       if ((!(Character.isDigit(c))) && (c != '.') && (c != ' '))
         return false;
     }
     return true;
   }
 
   private void checkInvalidData(HiMessage msg, String value) {
     HiMessageContext ctx = HiMessageContext.getCurrentMessageContext();
     if ((StringUtils.equals(this._data_type, "9")) && 
       (!(isNumber(value)))) {
       validatemsg = HiValidateMsg.get(ctx);
       validatemsg.add("241048", this.name);
       return;
     }
 
     if ((!(this._must_input)) || 
       (!(StringUtils.isEmpty(value)))) return;
     HiValidateMsg validatemsg = HiValidateMsg.get(ctx);
     validatemsg.add("241047", this.name);
     return;
   }
 
   private String fmt2InData(byte[] bytes)
     throws HiException
   {
     String val = "";
 
     switch (this._data_fmt)
     {
     case 0:
       val = new String(bytes);
       break;
     case 1:
       val = HiConvHelper.bcd2AscStr(bytes);
       break;
     case 2:
       val = Long.valueOf(HiConvHelper.bcd2AscStr(bytes), 16).toString();
       break;
     case 3:
       val = HiConvHelper.hex2Binary(HiConvHelper.bcd2AscStr(bytes));
       break;
     default:
       val = new String(bytes);
     }
 
     return val;
   }
 
   private byte[] fmt2OutData(byte[] valBytes, String valStr)
     throws HiException
   {
     byte[] bytes;
     switch (this._data_fmt)
     {
     case 0:
       bytes = valBytes;
       break;
     case 1:
       bytes = HiConvHelper.ascByte2Bcd(valBytes);
       break;
     case 2:
       String hex = "";
       if (valBytes.length > 0)
       {
         hex = Long.toString(Long.parseLong(valStr), 16);
         if (hex.length() % 2 != 0)
         {
           hex = '0' + hex;
         }
       }
       bytes = HiConvHelper.ascStr2Bcd(hex);
       break;
     case 3:
       String bit = HiConvHelper.binary2hex(valStr);
       if (bit.length() % 2 != 0)
       {
         bit = '0' + bit;
       }
       bytes = HiConvHelper.ascStr2Bcd(bit);
       break;
     default:
       bytes = valBytes;
     }
 
     return bytes;
   }
 
   public String getData_type() {
     return this._data_type;
   }
 
   public void setData_type(String type) {
     this._data_type = type;
   }
 
   public boolean isMust_input() {
     return this._must_input;
   }
 
   public void setMust_input(String input) {
     this._must_input = (StringUtils.equalsIgnoreCase(input, "yes"));
   }
 
   public String getData_fmt() {
     return String.valueOf(this._data_fmt);
   }
 
   public void setData_fmt(String type) throws HiException {
     if ("HEX".equalsIgnoreCase(type)) {
       this._data_fmt = 1;
     } else if ("DEC".equalsIgnoreCase(type)) {
       this._data_fmt = 2;
     } else if ("BIT".equalsIgnoreCase(type)) {
       this._data_fmt = 3;
     } else if ("ASC".equalsIgnoreCase(type)) {
       this._data_fmt = 0;
     } else {
       this.logger.error("setData_fmt(String)", "data_fmt:{ASC,HEX,DEC,BIT}");
 
       throw new HiException("215027", "setData_fmt(String) value exception");
     }
   }
 
   private class AheadCharParser extends HiItem.AheadParser
   {
     private final HiItem this$0;
 
     private AheadCharParser()
     {
       super(???, null); }
 
     protected int getFitLen(byte[] aheadLenStr) throws HiException {
       int returnint = Integer.parseInt(new String(aheadLenStr).trim());
       return returnint;
     }
 
     protected byte[] getFitLen(int itemLen) throws HiException {
       String strItemLen = String.valueOf(itemLen);
 
       if (strItemLen.length() > this.this$0._head_len)
       {
         throw new HiException("213134", this.this$0.name, String.valueOf(strItemLen.length()), String.valueOf(this.this$0._head_len));
       }
 
       if (strItemLen.length() < this.this$0._head_len) {
         strItemLen = StringUtils.leftPad(strItemLen, this.this$0._head_len, "0");
       }
 
       return strItemLen.getBytes();
     }
 
     AheadCharParser(HiItem.1 x1)
     {
       this(x0);
     }
   }
 
   private class AheadBinParser extends HiItem.AheadParser
   {
     private final HiItem this$0;
 
     private AheadBinParser()
     {
       super(???, null); }
 
     protected int getFitLen(byte[] aheadLenStr) throws HiException {
       int itemLen;
       try {
         itemLen = Integer.parseInt(HiConvHelper.bcd2AscStr(aheadLenStr).trim(), 16);
       }
       catch (NumberFormatException e)
       {
         throw new HiException("213131", new String[] { HiItem.access$700(this.this$0), HiConvHelper.bcd2AscStr(aheadLenStr) }, e);
       }
       catch (Throwable te)
       {
         throw HiSysException.makeException("213131", new String[] { HiItem.access$700(this.this$0), HiConvHelper.bcd2AscStr(aheadLenStr) }, te);
       }
 
       return itemLen;
     }
 
     protected byte[] getFitLen(int itemLen) throws HiException
     {
       String strItemLen = Integer.toString(itemLen, 16);
 
       int itemHeadLen = strItemLen.length();
       if (itemHeadLen % 2 != 0) {
         strItemLen = "0" + strItemLen;
       }
 
       if (itemHeadLen > this.this$0._head_len * 2)
       {
         throw new HiException("213133", this.this$0.name, String.valueOf(itemHeadLen), String.valueOf(this.this$0._head_len));
       }
       if (itemHeadLen < this.this$0._head_len * 2) {
         strItemLen = StringUtils.leftPad(strItemLen, this.this$0._head_len * 2, "0");
       }
 
       return HiConvHelper.ascStr2Bcd(strItemLen);
     }
 
     AheadBinParser(HiItem.1 x1)
     {
       this(x0);
     }
   }
 
   private abstract class AheadParser extends HiItem.Parser
   {
     private final HiItem this$0;
 
     private AheadParser()
     {
       super(???, null);
     }
 
     public void formatPlain(HiMessage msg, int plainOffset, int validLen) throws HiException {
       int itemLen = getAheadLength(msg, plainOffset, validLen);
 
       plainOffset += this.this$0._head_len;
       buildEtfItem(msg, plainOffset, itemLen);
     }
 
     public void formatETF(HiMessage msg, byte[] itemValBytes)
       throws HiException
     {
       Logger log = HiLog.getLogger(msg);
       if (log.isDebugEnabled()) {
         log.debug("formatETF(HiMessage, String) - start");
       }
 
       int itemLen = itemValBytes.length;
 
       if (this.this$0._include_len) {
         itemLen += this.this$0._head_len;
       }
 
       itemValBytes = ArrayUtils.addAll(getFitLen(itemLen), itemValBytes);
       this.this$0.addPlainItem(msg, itemValBytes);
 
       if (log.isDebugEnabled())
         log.debug("formatETF(HiMessage, String) - end");
     }
 
     private void buildEtfItem(HiMessage msg, int plain_offset, int itemLen)
       throws HiException
     {
       Logger log = HiLog.getLogger(msg);
       if (log.isDebugEnabled()) {
         log.debug("buildEtfItem(HiMessage, int, int) - start");
       }
 
       HiByteBuffer plainContent = HiItemHelper.getPlainText(msg);
       try
       {
         byte[] item = HiItemHelper.subPlainByte(plainContent, plain_offset, itemLen);
 
         this.this$0.addObjectItem(msg, item);
       }
       catch (IndexOutOfBoundsException e) {
         throw new HiException("213128", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLen) }, e);
       }
       catch (HiException he) {
         throw he;
       } catch (Throwable te) {
         throw HiSysException.makeException("213128", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLen), "Sys Exception" }, te);
       }
 
       HiItemHelper.setPlainOffset(msg, plain_offset + itemLen);
 
       if (log.isDebugEnabled())
         log.debug("buildEtfItem(HiMessage, int, int) - end");
     }
 
     private int getAheadLength(HiMessage msg, int plain_offset, int validLen)
       throws HiException
     {
       if (this.this$0._head_len > validLen)
       {
         throw new HiException("213129", this.this$0.name, String.valueOf(this.this$0._head_len));
       }
 
       HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
 
       byte[] aheadLenStr = HiItemHelper.subPlainByte(plainBody, plain_offset, this.this$0._head_len);
 
       int itemLen = getFitLen(aheadLenStr);
 
       validLen -= this.this$0._head_len;
 
       if (this.this$0._include_len) {
         itemLen -= this.this$0._head_len;
       }
 
       if (itemLen > validLen)
       {
         throw new HiException("213130", this.this$0.name, String.valueOf(itemLen));
       }
 
       return itemLen;
     }
 
     protected abstract int getFitLen(byte[] paramArrayOfByte)
       throws HiException;
 
     protected abstract byte[] getFitLen(int paramInt)
       throws HiException;
 
     AheadParser(HiItem.1 x1)
     {
       this(x0);
     }
   }
 
   private class DeliStrParser extends HiItem.Parser
   {
     private final HiItem this$0;
 
     private DeliStrParser()
     {
       super(???, null);
     }
 
     public void formatPlain(HiMessage msg, int plainOffset, int validLen)
       throws HiException
     {
       Logger log = HiLog.getLogger(msg);
       if (log.isDebugEnabled()) {
         log.debug("formatPlain(HiMessage, int, int) - start");
       }
 
       HiByteBuffer plainContent = HiItemHelper.getPlainText(msg);
 
       int idx = 0;
       int deliLen = this.this$0._deli_str.getBytes().length;
 
       idx = HiItemHelper.indexOfBytePlain(plainContent, this.this$0._deli_str, plainOffset);
 
       if (idx == -1)
       {
         idx = HiItemHelper.getPlainByteLen(plainContent);
         deliLen = 0;
       }
 
       byte[] item = HiItemHelper.subPlainByte(plainContent, plainOffset, idx - plainOffset);
 
       this.this$0.addObjectItem(msg, item);
 
       HiItemHelper.setPlainOffset(msg, idx + deliLen);
 
       if (log.isDebugEnabled())
         log.debug("formatPlain(HiMessage, int, int) - end");
     }
 
     public void formatETF(HiMessage msg, byte[] itemValBytes)
       throws HiException
     {
       Logger log = HiLog.getLogger(msg);
       if (log.isDebugEnabled()) {
         log.debug("formatETF(HiMessage, String) - start");
       }
 
       itemValBytes = ArrayUtils.addAll(itemValBytes, this.this$0._deli_str.getBytes());
 
       this.this$0.addPlainItem(msg, itemValBytes);
 
       if (log.isDebugEnabled())
         log.debug("formatETF(HiMessage, String) - end");
     }
 
     DeliStrParser(HiItem.1 x1)
     {
       this(x0);
     }
   }
 
   private class DeliAscParser extends HiItem.Parser
   {
     private final HiItem this$0;
 
     private DeliAscParser()
     {
       super(???, null);
     }
 
     public void formatPlain(HiMessage msg, int plainOffset, int validLen)
       throws HiException
     {
       Logger log = HiLog.getLogger(msg);
       if (log.isDebugEnabled()) {
         log.debug("formatPlain(HiMessage, int, int) - start");
       }
 
       HiByteBuffer plainContent = HiItemHelper.getPlainText(msg);
 
       int idx = 0;
       short deliLen = 1;
       try {
         idx = HiItemHelper.indexOfBytePlain(plainContent, this.this$0._deli_asc, plainOffset);
 
         if (idx == -1)
         {
           idx = HiItemHelper.getPlainByteLen(plainContent);
           deliLen = 0;
         }
 
         byte[] item = HiItemHelper.subPlainByte(plainContent, plainOffset, idx - plainOffset);
 
         this.this$0.addObjectItem(msg, item);
       } catch (HiException he) {
         throw he;
       }
       catch (Throwable te) {
         throw HiSysException.makeException("213127", new String[] { HiItem.access$700(this.this$0), String.valueOf(HiItem.access$1300(this.this$0)) }, te);
       }
 
       HiItemHelper.setPlainOffset(msg, idx + deliLen);
 
       if (log.isDebugEnabled())
         log.debug("formatPlain(HiMessage, int, int) - end");
     }
 
     public void formatETF(HiMessage msg, byte[] itemValBytes)
       throws HiException
     {
       Logger log = HiLog.getLogger(msg);
       if (log.isDebugEnabled()) {
         log.debug("formatETF(HiMessage, String) - start");
       }
 
       if (this.this$0._deli_asc == -1) {
         this.this$0.addPlainItem(msg, itemValBytes);
       } else {
         itemValBytes = ArrayUtils.add(itemValBytes, this.this$0._deli_asc);
         this.this$0.addPlainItem(msg, itemValBytes);
       }
 
       if (log.isDebugEnabled())
         log.debug("formatETF(HiMessage, String) - end");
     }
 
     DeliAscParser(HiItem.1 x1)
     {
       this(x0);
     }
   }
 
   private class FixedParser extends HiItem.Parser
   {
     private final HiItem this$0;
 
     private FixedParser()
     {
       super(???, null);
     }
 
     public void formatPlain(HiMessage msg, int plainOffset, int validLen)
       throws HiException
     {
       Logger log = HiLog.getLogger(msg);
       if (log.isDebugEnabled()) {
         log.debug("formatPlain(HiMessage, int, int) - start");
       }
 
       int itemLenAttr = 0;
       if (this.this$0.lengthExp != null) {
         try {
           itemLenAttr = Integer.parseInt(this.this$0.lengthExp.getValue(HiMessageContext.getCurrentMessageContext()).trim());
         }
         catch (NumberFormatException e)
         {
           throw new HiException("213123", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr) }, e);
         }
         catch (Throwable te)
         {
           throw HiSysException.makeException("213123", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr), "Sys Exception" }, te);
         }
 
       }
 
       itemLenAttr = this.this$0.length;
 
       if (validLen < itemLenAttr)
       {
         throw new HiException("213125", this.this$0.name, String.valueOf(itemLenAttr), String.valueOf(validLen));
       }
 
       buildEtfItem(msg, plainOffset, itemLenAttr);
 
       if (log.isDebugEnabled())
         log.debug("formatPlain(HiMessage, int, int) - end");
     }
 
     public void formatETF(HiMessage msg, byte[] itemValBytes)
       throws HiException
     {
       Logger log = HiLog.getLogger(msg);
       if (log.isDebugEnabled()) {
         log.debug("formatETF(HiMessage, String) - start");
       }
 
       int itemLenAttr = 0;
       if (this.this$0.lengthExp != null) {
         try {
           itemLenAttr = Integer.parseInt(this.this$0.lengthExp.getValue(HiMessageContext.getCurrentMessageContext()).trim());
         }
         catch (NumberFormatException e)
         {
           throw new HiException("213124", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr) }, e);
         }
         catch (Throwable te)
         {
           throw HiSysException.makeException("213124", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr), "Sys Exception" }, te);
         }
 
       }
 
       itemLenAttr = this.this$0.length;
 
       buildFixedPlain(msg, itemValBytes, itemLenAttr);
 
       if (log.isDebugEnabled())
         log.debug("formatETF(HiMessage, String) - end");
     }
 
     private void buildEtfItem(HiMessage msg, int plain_offset, int itemLenAttr)
       throws HiException
     {
       HiByteBuffer plainContent = HiItemHelper.getPlainText(msg);
       try
       {
         byte[] item = HiItemHelper.subPlainByte(plainContent, plain_offset, itemLenAttr);
 
         this.this$0.addObjectItem(msg, item);
       }
       catch (IndexOutOfBoundsException e) {
         throw new HiException("213126", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr) }, e);
       }
       catch (HiException he) {
         throw he;
       } catch (Throwable te) {
         throw new HiSysException("213126", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr), "Sys Exception" }, te);
       }
 
       HiItemHelper.setPlainOffset(msg, plain_offset + itemLenAttr);
     }
 
     private void buildFixedPlain(HiMessage msg, byte[] itemValBytes, int itemLenAttr)
       throws HiException
     {
       int itemLen = itemValBytes.length;
       if (itemLen > itemLenAttr)
       {
         itemValBytes = HiItemHelper.subPlainByte(itemValBytes, 0, itemLenAttr);
       }
       else if (itemLen < itemLenAttr) {
         byte[] fillBytes = null;
 
         fillBytes = HiItemHelper.repeat(fillBytes, this.this$0.fill_asc, itemLenAttr - itemLen);
 
         if (StringUtils.equals(this.this$0._align_mode, "right"))
           itemValBytes = ArrayUtils.addAll(fillBytes, itemValBytes);
         else {
           itemValBytes = ArrayUtils.addAll(itemValBytes, fillBytes);
         }
       }
 
       this.this$0.addPlainItem(msg, itemValBytes);
     }
 
     FixedParser(HiItem.1 x1)
     {
       this(x0);
     }
   }
 
   private abstract class Parser
   {
     private final HiItem this$0;
 
     private Parser()
     {
     }
 
     public abstract void formatPlain(HiMessage paramHiMessage, int paramInt1, int paramInt2)
       throws HiException;
 
     public abstract void formatETF(HiMessage paramHiMessage, byte[] paramArrayOfByte)
       throws HiException;
 
     Parser(HiItem.1 x1)
     {
       this(x0);
     }
   }
 }