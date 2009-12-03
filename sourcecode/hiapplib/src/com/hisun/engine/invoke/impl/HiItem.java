/*      */ package com.hisun.engine.invoke.impl;
/*      */ 
/*      */ import com.hisun.engine.HiEngineUtilities;
/*      */ import com.hisun.engine.HiITFEngineModel;
/*      */ import com.hisun.engine.invoke.HiStrategyFactory;
/*      */ import com.hisun.engine.invoke.load.HiDict;
/*      */ import com.hisun.engine.invoke.load.HiDictItem;
/*      */ import com.hisun.exception.HiException;
/*      */ import com.hisun.exception.HiSysException;
/*      */ import com.hisun.hiexpression.HiExpFactory;
/*      */ import com.hisun.hiexpression.HiExpression;
/*      */ import com.hisun.hilog4j.HiLog;
/*      */ import com.hisun.hilog4j.Logger;
/*      */ import com.hisun.message.HiContext;
/*      */ import com.hisun.message.HiMessage;
/*      */ import com.hisun.message.HiMessageContext;
/*      */ import com.hisun.util.HiByteBuffer;
/*      */ import com.hisun.util.HiConvHelper;
/*      */ import com.hisun.util.HiStringManager;
/*      */ import java.io.PrintStream;
/*      */ import org.apache.commons.lang.ArrayUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.lang.math.NumberUtils;
/*      */ 
/*      */ public class HiItem extends HiITFEngineModel
/*      */ {
/*   39 */   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*      */ 
/*   42 */   private final HiStringManager sm = HiStringManager.getManager();
/*      */   private String name;
/*   50 */   private String _dict_name = null;
/*   51 */   private int length = -1;
/*   52 */   private HiExpression lengthExp = null;
/*      */ 
/*   54 */   private byte fill_asc = 32;
/*      */ 
/*   56 */   private int offset = 0;
/*      */ 
/*   58 */   private HiExpression exp = null;
/*      */ 
/*   60 */   private String value = null;
/*      */   private String pro_dll;
/*      */   private String pro_func;
/*   65 */   protected HiMethodItem pro_method = null;
/*      */ 
/*   67 */   private Parser itemParser = null;
/*      */ 
/*   71 */   private String _data_type = null;
/*      */ 
/*   75 */   private boolean _must_input = false;
/*      */ 
/*   84 */   private byte _data_fmt = 0;
/*      */ 
/*      */   public HiItem() throws HiException {
/*   87 */     initAtt();
/*      */   }
/*      */ 
/*      */   public void initAtt()
/*      */     throws HiException
/*      */   {
/*   99 */     if (this.logger.isDebugEnabled()) {
/*  100 */       this.logger.debug("initAtt() - start");
/*      */     }
/*  102 */     HiContext context = HiContext.getCurrentContext();
/*  103 */     HiITFEngineModel itemAttribute = HiITFEngineModel.getItemAttribute(context);
/*      */ 
/*  105 */     if (itemAttribute != null) {
/*  106 */       super.cloneProperty(itemAttribute);
/*  107 */       if (this.logger.isDebugEnabled()) {
/*  108 */         this.logger.debug("ITEM继承属性:[" + itemAttribute + "]");
/*      */       }
/*      */     }
/*      */ 
/*  112 */     if (this.logger.isDebugEnabled())
/*  113 */       this.logger.debug("initAtt() - end");
/*      */   }
/*      */ 
/*      */   public void setExpression(String expression)
/*      */   {
/*  118 */     this.exp = HiExpFactory.createExp(expression);
/*      */   }
/*      */ 
/*      */   public void setFill_asc(String fill_asc) throws HiException {
/*      */     try {
/*  123 */       Integer deliInt = Integer.valueOf(fill_asc);
/*  124 */       if ((deliInt.intValue() > 255) || (deliInt.intValue() < -128)) {
/*  125 */         throw new HiException("213101", new String[] { this.name, fill_asc });
/*      */       }
/*      */ 
/*  129 */       this.fill_asc = deliInt.byteValue();
/*      */     } catch (Throwable te) {
/*  131 */       this.logger.error("setFill_asc(String)", te);
/*      */ 
/*  133 */       throw new HiSysException("213103", new String[] { this.name, fill_asc }, te);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setLength(String length) throws HiException
/*      */   {
/*  139 */     if (this.logger.isDebugEnabled()) {
/*  140 */       this.logger.debug("setLength(String) - start:input[" + length + "]");
/*      */     }
/*      */ 
/*  143 */     if (NumberUtils.isNumber(length)) {
/*  144 */       this.length = Integer.parseInt(length.trim());
/*      */     } else {
/*  146 */       if (length == null) {
/*  147 */         throw new HiSysException("213104", new String[] { this.name, length });
/*      */       }
/*      */ 
/*  150 */       if (this.logger.isInfoEnabled()) {
/*  151 */         this.logger.info(getNodeName() + ":setLength(String)->lengthExpression[" + length + "]");
/*      */       }
/*      */ 
/*  155 */       this.length = 0;
/*  156 */       this.lengthExp = HiExpFactory.createExp(length);
/*      */     }
/*  158 */     this._type = "fixed";
/*      */   }
/*      */ 
/*      */   public void setName(String name) throws HiException {
/*  162 */     this.name = name;
/*      */   }
/*      */ 
/*      */   public void setDict_name(String name) throws HiException {
/*  166 */     this._dict_name = name;
/*  167 */     cloneDictItem(this._dict_name);
/*      */   }
/*      */ 
/*      */   public void setOffset(String offset) throws HiException {
/*      */     try {
/*  172 */       this.offset = Integer.parseInt(offset.trim());
/*      */     } catch (NumberFormatException e) {
/*  174 */       this.logger.error("setOffset(String)", e);
/*      */ 
/*  177 */       throw new HiException("213106", new String[] { this.name, offset }, e);
/*      */     }
/*      */     catch (Throwable te) {
/*  180 */       this.logger.error("setOffset(String)", te);
/*      */ 
/*  182 */       throw new HiSysException("213106", new String[] { this.name, offset }, te);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setPro_dll(String pro_dll)
/*      */   {
/*  189 */     if (this.logger.isDebugEnabled()) {
/*  190 */       this.logger.debug("setPro_dll(String) - start");
/*      */     }
/*      */ 
/*  193 */     this.pro_dll = pro_dll;
/*      */ 
/*  195 */     if (this.logger.isDebugEnabled())
/*  196 */       this.logger.debug("setPro_dll(String) - end");
/*      */   }
/*      */ 
/*      */   public void setPro_func(String pro_func)
/*      */   {
/*  201 */     if (this.logger.isDebugEnabled()) {
/*  202 */       this.logger.debug("setPro_func(String) - start");
/*      */     }
/*      */ 
/*  205 */     this.pro_func = pro_func;
/*      */ 
/*  207 */     if (this.logger.isDebugEnabled())
/*  208 */       this.logger.debug("setPro_func(String) - end");
/*      */   }
/*      */ 
/*      */   public void setValue(String value)
/*      */   {
/*  213 */     this.value = value;
/*      */   }
/*      */ 
/*      */   protected int getOffset() {
/*  217 */     return this.offset;
/*      */   }
/*      */ 
/*      */   protected String getValue() {
/*  221 */     return this.value;
/*      */   }
/*      */ 
/*      */   public String getNodeName() {
/*  225 */     return "Item";
/*      */   }
/*      */ 
/*      */   public String toString() {
/*  229 */     String returnString = super.toString() + ":name[" + this.name + "],type[" + this._type + "]";
/*      */ 
/*  231 */     return returnString;
/*      */   }
/*      */ 
/*      */   public void process(HiMessageContext ctx) throws HiException {
/*      */     try {
/*  236 */       HiMessage msg = ctx.getCurrentMsg();
/*  237 */       Logger log = HiLog.getLogger(msg);
/*  238 */       if (log.isInfoEnabled()) {
/*  239 */         log.info(this.sm.getString("HiItem.process00", HiEngineUtilities.getCurFlowStep(), this.name));
/*      */       }
/*      */ 
/*  243 */       doProcess(msg, ctx);
/*      */     } catch (HiException e) {
/*  245 */       throw e;
/*      */     } catch (Throwable te) {
/*  247 */       throw new HiSysException("213135", this.name, te);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doProcess(HiMessage mess, HiMessageContext tranData)
/*      */     throws HiException
/*      */   {
/*  260 */     Logger log = HiLog.getLogger(mess);
/*  261 */     if (log.isDebugEnabled()) {
/*  262 */       log.debug("doProcess(HiMessage, HiMessageContext) - start");
/*      */     }
/*      */ 
/*  265 */     String ect = mess.getHeadItem("ECT");
/*      */ 
/*  267 */     if (StringUtils.equals(ect, "text/etf"))
/*      */       try
/*      */       {
/*  270 */         formatEtf(mess);
/*      */       }
/*      */       catch (HiException e)
/*      */       {
/*  274 */         throw e;
/*      */       }
/*  276 */     if (StringUtils.equals(ect, "text/plain")) {
/*      */       try {
/*  278 */         formatPlain(mess);
/*      */ 
/*  281 */         if (this.exp != null)
/*      */         {
/*  284 */           HiItemHelper.addEtfItem(mess, this.name, execExpression(mess));
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (HiException e)
/*      */       {
/*  290 */         throw e;
/*      */       }
/*      */     }
/*  293 */     throw new HiException("213120", ect);
/*      */ 
/*  296 */     if (log.isDebugEnabled())
/*  297 */       log.debug("doProcess(HiMessage, HiMessageContext) - end");
/*      */   }
/*      */ 
/*      */   protected void formatPlain(HiMessage plainMsg)
/*      */     throws HiException
/*      */   {
/*  309 */     Logger log = HiLog.getLogger(plainMsg);
/*  310 */     if (log.isDebugEnabled()) {
/*  311 */       log.debug("formatPlain(HiMessage) - start");
/*      */     }
/*      */ 
/*  318 */     int plain_offset = HiItemHelper.getPlainOffset(plainMsg) + this.offset;
/*      */ 
/*  320 */     if (log.isDebugEnabled()) {
/*  321 */       log.debug(this.sm.getString("HiItem.formatPlain1", String.valueOf(plain_offset)));
/*      */     }
/*      */ 
/*  325 */     int validLen = getValidStrMsgLen(plainMsg, plain_offset);
/*      */ 
/*  327 */     if (log.isDebugEnabled()) {
/*  328 */       log.debug(this.sm.getString("HiItem.formatPlain2", String.valueOf(validLen)));
/*      */     }
/*      */ 
/*  334 */     if (validLen <= 0) {
/*  335 */       HiItemHelper.setPlainOffset(plainMsg, plain_offset);
/*      */ 
/*  337 */       if (!(this._necessary)) {
/*  338 */         if (log.isDebugEnabled()) {
/*  339 */           log.debug("formatPlain(HiMessage) - end");
/*      */         }
/*  341 */         return;
/*      */       }
/*      */ 
/*  344 */       throw new HiException("213121", this.name, String.valueOf(plain_offset));
/*      */     }
/*      */ 
/*  353 */     if (this.itemParser != null)
/*  354 */       this.itemParser.formatPlain(plainMsg, plain_offset, validLen);
/*      */     else
/*  356 */       throw new HiSysException("213136", this.name);
/*      */   }
/*      */ 
/*      */   private void formatEtf(HiMessage etfMsg)
/*      */     throws HiException
/*      */   {
/*  370 */     Logger log = HiLog.getLogger(etfMsg);
/*  371 */     if (log.isDebugEnabled()) {
/*  372 */       log.debug("formatEtf(HiMessage) - start");
/*  373 */       log.debug(this.sm.getString("HiItem.formatEtf1"));
/*      */     }
/*      */ 
/*  378 */     String itemVal = HiItemHelper.getEtfItem(etfMsg, this.name);
/*      */ 
/*  380 */     if (itemVal == null)
/*      */     {
/*  382 */       if (this.value != null)
/*      */       {
/*  385 */         itemVal = this.value;
/*  386 */         if (log.isInfoEnabled())
/*  387 */           log.info(this.sm.getString("HiItem.formatEtf3", this.name, itemVal));
/*      */       }
/*  389 */       else if (!(this._necessary)) {
/*  390 */         if (log.isInfoEnabled()) {
/*  391 */           log.warn(this.sm.getString("HiItem.formatEtf4", this.name));
/*      */         }
/*  393 */         itemVal = "";
/*      */       }
/*      */       else
/*      */       {
/*  397 */         throw new HiException("213122", this.name, HiItemHelper.getCurEtfLevel(etfMsg) + this.name);
/*      */       }
/*      */ 
/*  401 */       if ((this.value != null) && (this.exp != null))
/*      */       {
/*  403 */         HiItemHelper.addEtfItem(etfMsg, this.name, itemVal);
/*      */       }
/*      */     }
/*  406 */     else if (log.isInfoEnabled()) {
/*  407 */       log.info(this.sm.getString("HiItem.formatEtf2", this.name, itemVal));
/*      */     }
/*      */ 
/*  413 */     if (this.exp != null) {
/*      */       try {
/*  415 */         itemVal = execExpression(etfMsg);
/*      */       } catch (HiException e) {
/*  417 */         if (!(this._necessary)) {
/*  418 */           log.warn("exp:" + this.exp + "计算失败!" + e.getMessage());
/*  419 */           itemVal = "";
/*      */         } else {
/*  421 */           throw e;
/*      */         }
/*      */       }
/*      */     }
/*  425 */     byte[] valBytes = itemVal.getBytes();
/*      */ 
/*  427 */     valBytes = fmt2OutData(valBytes, itemVal);
/*      */ 
/*  429 */     if (this.pro_method != null) {
/*  430 */       valBytes = HiItemHelper.execMethod(this.pro_method, valBytes, log);
/*      */     }
/*      */ 
/*  434 */     if (this.itemParser != null)
/*      */     {
/*  436 */       this.itemParser.formatETF(etfMsg, valBytes);
/*  437 */     } else if (this.value != null)
/*      */     {
/*  441 */       addPlainItem(etfMsg, valBytes);
/*      */     }
/*      */     else
/*  444 */       throw new HiSysException("213137", this.name);
/*      */   }
/*      */ 
/*      */   protected void addObjectItem(HiMessage msg, byte[] itemBytes)
/*      */     throws HiException
/*      */   {
/*  458 */     Logger log = HiLog.getLogger(msg);
/*  459 */     if (log.isDebugEnabled()) {
/*  460 */       log.debug("addObjectItem(HiMessage, String) - start");
/*      */     }
/*      */ 
/*  464 */     if (this.pro_method != null) {
/*  465 */       itemBytes = HiItemHelper.execMethod(this.pro_method, itemBytes, log);
/*      */     }
/*      */ 
/*  469 */     String itemVal = fmt2InData(itemBytes);
/*      */ 
/*  472 */     HiItemHelper.addEtfItem(msg, this.name, itemVal);
/*  473 */     HiMessageContext ctx = HiMessageContext.getCurrentMessageContext();
/*  474 */     checkInvalidData(msg, this.name);
/*      */ 
/*  476 */     if (log.isInfoEnabled()) {
/*  477 */       log.info(this.sm.getString("HiItem.addObjectItem", this.name, String.valueOf(itemBytes.length), itemVal));
/*      */     }
/*      */ 
/*  481 */     if (log.isDebugEnabled())
/*  482 */       log.debug("addObjectItem(HiMessage, String) - end");
/*      */   }
/*      */ 
/*      */   private void addPlainItem(HiMessage strMsg, byte[] item)
/*      */     throws HiException
/*      */   {
/*  496 */     Logger log = HiLog.getLogger(strMsg);
/*  497 */     if (log.isDebugEnabled()) {
/*  498 */       log.debug("addPlainItem(HiMessage, String) - start");
/*      */     }
/*      */ 
/*  501 */     HiByteBuffer plainBody = HiItemHelper.getPlainText(strMsg);
/*      */ 
/*  503 */     if (this.offset > 0)
/*      */     {
/*  505 */       plainBody.repeat(32, this.offset);
/*      */     }
/*  507 */     if (log.isDebugEnabled()) {
/*  508 */       log.debug(this.sm.getString("HiItem.addPlainItem1", this.name, item));
/*      */     }
/*      */ 
/*  511 */     plainBody.append(item);
/*      */ 
/*  513 */     if (log.isDebugEnabled()) {
/*  514 */       log.debug(this.sm.getString("HiItem.addPlainItem2", plainBody));
/*      */     }
/*      */ 
/*  518 */     if (log.isDebugEnabled())
/*  519 */       log.debug("addPlainItem(HiMessage, String) - end");
/*      */   }
/*      */ 
/*      */   private int getValidStrMsgLen(HiMessage strMsg, int plainOffset)
/*      */   {
/*  533 */     HiByteBuffer plainBody = HiItemHelper.getPlainText(strMsg);
/*  534 */     return (plainBody.length() - plainOffset);
/*      */   }
/*      */ 
/*      */   private String execExpression(HiMessage mess)
/*      */     throws HiException
/*      */   {
/*  545 */     Logger log = HiLog.getLogger(mess);
/*  546 */     if (log.isDebugEnabled()) {
/*  547 */       log.debug("execExpression(HiMessage) - start");
/*      */     }
/*      */ 
/*  550 */     String val = this.exp.getValue(HiMessageContext.getCurrentMessageContext());
/*      */ 
/*  552 */     if (log.isInfoEnabled()) {
/*  553 */       log.info(this.sm.getString("HiItem.execExpression", this.exp.getExp(), val));
/*      */     }
/*      */ 
/*  556 */     if (val == null) {
/*  557 */       throw new HiException("215113", this.exp.getExp() + " return null, ");
/*      */     }
/*      */ 
/*  562 */     if (log.isDebugEnabled()) {
/*  563 */       log.debug("execExpression(HiMessage) - end");
/*      */     }
/*  565 */     return val;
/*      */   }
/*      */ 
/*      */   public void loadAfter()
/*      */     throws HiException
/*      */   {
/*  572 */     if (this.logger.isDebugEnabled()) {
/*  573 */       this.logger.debug("loadAfter() - start");
/*      */     }
/*      */ 
/*  576 */     if (StringUtils.equals(this._type, "fixed")) {
/*  577 */       if ((this.length < 0) && (this.lengthExp == null))
/*      */       {
/*  579 */         throw new HiException("213107", this.name);
/*      */       }
/*      */ 
/*  582 */       this.itemParser = new FixedParser(null);
/*  583 */     } else if (StringUtils.equals(this._type, "deli"))
/*      */     {
/*  588 */       this.itemParser = new DeliAscParser(null);
/*  589 */     } else if (StringUtils.equals(this._type, "deli_str")) {
/*  590 */       if (StringUtils.isEmpty(this._deli_str))
/*      */       {
/*  592 */         throw new HiException("213108", this.name);
/*      */       }
/*      */ 
/*  595 */       this.itemParser = new DeliStrParser(null);
/*  596 */     } else if (StringUtils.equals(this._type, "ahead_len")) {
/*  597 */       if (this._head_len <= 0)
/*      */       {
/*  599 */         throw new HiException("213109", this.name);
/*      */       }
/*      */ 
/*  602 */       if (StringUtils.equals(this._len_type, "bin"))
/*  603 */         this.itemParser = new AheadBinParser(null);
/*      */       else {
/*  605 */         this.itemParser = new AheadCharParser(null);
/*      */       }
/*      */     }
/*  608 */     else if (this.value == null)
/*      */     {
/*  611 */       throw new HiException("213110", this.name, this._type);
/*      */     }
/*      */ 
/*  615 */     if ((StringUtils.isNotBlank(this.pro_dll)) && (StringUtils.isNotBlank(this.pro_func))) {
/*  616 */       this.pro_method = HiItemHelper.getMethod(this.pro_dll, this.pro_func);
/*      */     }
/*      */ 
/*  620 */     if (this._data_fmt != 0) {
/*  621 */       this.fill_asc = 0;
/*  622 */       this._align_mode = "right";
/*      */     }
/*      */ 
/*  634 */     HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiByteStategy");
/*      */ 
/*  637 */     if (this.logger.isDebugEnabled())
/*  638 */       this.logger.debug("loadAfter() - end");
/*      */   }
/*      */ 
/*      */   private void cloneDictItem(String name)
/*      */     throws HiException
/*      */   {
/* 1171 */     HiDictItem item = HiDict.get(HiContext.getCurrentContext(), name);
/* 1172 */     System.out.println("HiDictItem:" + item);
/* 1173 */     if (item == null) {
/* 1174 */       return;
/*      */     }
/* 1176 */     if (item._align_mode != null) {
/* 1177 */       setAlign_mode(item._align_mode);
/*      */     }
/* 1179 */     if (item._deli_asc != null) {
/* 1180 */       setDeli_asc(item._deli_asc);
/*      */     }
/* 1182 */     if (item._deli_str != null) {
/* 1183 */       setDeli_str(item._deli_str);
/*      */     }
/* 1185 */     if (item._head_len != null) {
/* 1186 */       setHead_len(item._head_len);
/*      */     }
/* 1188 */     if (item._include_len != null) {
/* 1189 */       setInclude_len(item._include_len);
/*      */     }
/* 1191 */     if (item._len_type != null) {
/* 1192 */       setLen_type(item._len_type);
/*      */     }
/* 1194 */     if (item._length != null) {
/* 1195 */       setLength(item._length);
/*      */     }
/* 1197 */     if (item._necessary != null) {
/* 1198 */       setNecessary(item._necessary);
/*      */     }
/* 1200 */     if (item._pass != null) {
/* 1201 */       setPass(item._pass);
/*      */     }
/* 1203 */     if (item._type != null) {
/* 1204 */       setType(item._type);
/*      */     }
/* 1206 */     if (item._seq_no != null) {
/* 1207 */       setSeq_no(item._seq_no);
/*      */     }
/* 1209 */     if (item._etf_name != null) {
/* 1210 */       setName(item._etf_name);
/*      */     }
/* 1212 */     if (item._must_input != null) {
/* 1213 */       setMust_input(item._must_input);
/*      */     }
/* 1215 */     if (item._data_type != null) {
/* 1216 */       setData_type(item._data_type);
/* 1217 */       if (StringUtils.equals(item._data_type, "9")) {
/* 1218 */         if (this._align_mode == null) {
/* 1219 */           setAlign_mode("right");
/* 1220 */           setFill_asc("48");
/*      */         }
/*      */       }
/* 1223 */       else if (this._align_mode == null)
/* 1224 */         setAlign_mode("left");
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean isNumber(String s)
/*      */   {
/* 1231 */     for (int i = 0; i < s.length(); ++i) {
/* 1232 */       char c = s.charAt(i);
/* 1233 */       if ((!(Character.isDigit(c))) && (c != '.') && (c != ' '))
/* 1234 */         return false;
/*      */     }
/* 1236 */     return true;
/*      */   }
/*      */ 
/*      */   private void checkInvalidData(HiMessage msg, String value) {
/* 1240 */     HiMessageContext ctx = HiMessageContext.getCurrentMessageContext();
/* 1241 */     if ((StringUtils.equals(this._data_type, "9")) && 
/* 1242 */       (!(isNumber(value)))) {
/* 1243 */       validatemsg = HiValidateMsg.get(ctx);
/* 1244 */       validatemsg.add("241048", this.name);
/* 1245 */       return;
/*      */     }
/*      */ 
/* 1249 */     if ((!(this._must_input)) || 
/* 1250 */       (!(StringUtils.isEmpty(value)))) return;
/* 1251 */     HiValidateMsg validatemsg = HiValidateMsg.get(ctx);
/* 1252 */     validatemsg.add("241047", this.name);
/* 1253 */     return;
/*      */   }
/*      */ 
/*      */   private String fmt2InData(byte[] bytes)
/*      */     throws HiException
/*      */   {
/* 1268 */     String val = "";
/*      */ 
/* 1270 */     switch (this._data_fmt)
/*      */     {
/*      */     case 0:
/* 1274 */       val = new String(bytes);
/* 1275 */       break;
/*      */     case 1:
/* 1278 */       val = HiConvHelper.bcd2AscStr(bytes);
/* 1279 */       break;
/*      */     case 2:
/* 1282 */       val = Long.valueOf(HiConvHelper.bcd2AscStr(bytes), 16).toString();
/* 1283 */       break;
/*      */     case 3:
/* 1286 */       val = HiConvHelper.hex2Binary(HiConvHelper.bcd2AscStr(bytes));
/* 1287 */       break;
/*      */     default:
/* 1290 */       val = new String(bytes);
/*      */     }
/*      */ 
/* 1293 */     return val;
/*      */   }
/*      */ 
/*      */   private byte[] fmt2OutData(byte[] valBytes, String valStr)
/*      */     throws HiException
/*      */   {
/*      */     byte[] bytes;
/* 1309 */     switch (this._data_fmt)
/*      */     {
/*      */     case 0:
/* 1313 */       bytes = valBytes;
/* 1314 */       break;
/*      */     case 1:
/* 1317 */       bytes = HiConvHelper.ascByte2Bcd(valBytes);
/* 1318 */       break;
/*      */     case 2:
/* 1321 */       String hex = "";
/* 1322 */       if (valBytes.length > 0)
/*      */       {
/* 1324 */         hex = Long.toString(Long.parseLong(valStr), 16);
/* 1325 */         if (hex.length() % 2 != 0)
/*      */         {
/* 1327 */           hex = '0' + hex;
/*      */         }
/*      */       }
/* 1330 */       bytes = HiConvHelper.ascStr2Bcd(hex);
/* 1331 */       break;
/*      */     case 3:
/* 1334 */       String bit = HiConvHelper.binary2hex(valStr);
/* 1335 */       if (bit.length() % 2 != 0)
/*      */       {
/* 1337 */         bit = '0' + bit;
/*      */       }
/* 1339 */       bytes = HiConvHelper.ascStr2Bcd(bit);
/* 1340 */       break;
/*      */     default:
/* 1343 */       bytes = valBytes;
/*      */     }
/*      */ 
/* 1346 */     return bytes;
/*      */   }
/*      */ 
/*      */   public String getData_type() {
/* 1350 */     return this._data_type;
/*      */   }
/*      */ 
/*      */   public void setData_type(String type) {
/* 1354 */     this._data_type = type;
/*      */   }
/*      */ 
/*      */   public boolean isMust_input() {
/* 1358 */     return this._must_input;
/*      */   }
/*      */ 
/*      */   public void setMust_input(String input) {
/* 1362 */     this._must_input = (StringUtils.equalsIgnoreCase(input, "yes"));
/*      */   }
/*      */ 
/*      */   public String getData_fmt() {
/* 1366 */     return String.valueOf(this._data_fmt);
/*      */   }
/*      */ 
/*      */   public void setData_fmt(String type) throws HiException {
/* 1370 */     if ("HEX".equalsIgnoreCase(type)) {
/* 1371 */       this._data_fmt = 1;
/* 1372 */     } else if ("DEC".equalsIgnoreCase(type)) {
/* 1373 */       this._data_fmt = 2;
/* 1374 */     } else if ("BIT".equalsIgnoreCase(type)) {
/* 1375 */       this._data_fmt = 3;
/* 1376 */     } else if ("ASC".equalsIgnoreCase(type)) {
/* 1377 */       this._data_fmt = 0;
/*      */     } else {
/* 1379 */       this.logger.error("setData_fmt(String)", "data_fmt:{ASC,HEX,DEC,BIT}");
/*      */ 
/* 1382 */       throw new HiException("215027", "setData_fmt(String) value exception");
/*      */     }
/*      */   }
/*      */ 
/*      */   private class AheadCharParser extends HiItem.AheadParser
/*      */   {
/*      */     private final HiItem this$0;
/*      */ 
/*      */     private AheadCharParser()
/*      */     {
/* 1147 */       super(???, null); }
/*      */ 
/*      */     protected int getFitLen(byte[] aheadLenStr) throws HiException {
/* 1150 */       int returnint = Integer.parseInt(new String(aheadLenStr).trim());
/* 1151 */       return returnint;
/*      */     }
/*      */ 
/*      */     protected byte[] getFitLen(int itemLen) throws HiException {
/* 1155 */       String strItemLen = String.valueOf(itemLen);
/*      */ 
/* 1157 */       if (strItemLen.length() > this.this$0._head_len)
/*      */       {
/* 1159 */         throw new HiException("213134", this.this$0.name, String.valueOf(strItemLen.length()), String.valueOf(this.this$0._head_len));
/*      */       }
/*      */ 
/* 1162 */       if (strItemLen.length() < this.this$0._head_len) {
/* 1163 */         strItemLen = StringUtils.leftPad(strItemLen, this.this$0._head_len, "0");
/*      */       }
/*      */ 
/* 1166 */       return strItemLen.getBytes();
/*      */     }
/*      */ 
/*      */     AheadCharParser(HiItem.1 x1)
/*      */     {
/* 1147 */       this(x0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private class AheadBinParser extends HiItem.AheadParser
/*      */   {
/*      */     private final HiItem this$0;
/*      */ 
/*      */     private AheadBinParser()
/*      */     {
/* 1099 */       super(???, null); }
/*      */ 
/*      */     protected int getFitLen(byte[] aheadLenStr) throws HiException {
/*      */       int itemLen;
/*      */       try {
/* 1104 */         itemLen = Integer.parseInt(HiConvHelper.bcd2AscStr(aheadLenStr).trim(), 16);
/*      */       }
/*      */       catch (NumberFormatException e)
/*      */       {
/* 1108 */         throw new HiException("213131", new String[] { HiItem.access$700(this.this$0), HiConvHelper.bcd2AscStr(aheadLenStr) }, e);
/*      */       }
/*      */       catch (Throwable te)
/*      */       {
/* 1112 */         throw HiSysException.makeException("213131", new String[] { HiItem.access$700(this.this$0), HiConvHelper.bcd2AscStr(aheadLenStr) }, te);
/*      */       }
/*      */ 
/* 1116 */       return itemLen;
/*      */     }
/*      */ 
/*      */     protected byte[] getFitLen(int itemLen) throws HiException
/*      */     {
/* 1121 */       String strItemLen = Integer.toString(itemLen, 16);
/*      */ 
/* 1123 */       int itemHeadLen = strItemLen.length();
/* 1124 */       if (itemHeadLen % 2 != 0) {
/* 1125 */         strItemLen = "0" + strItemLen;
/*      */       }
/*      */ 
/* 1128 */       if (itemHeadLen > this.this$0._head_len * 2)
/*      */       {
/* 1130 */         throw new HiException("213133", this.this$0.name, String.valueOf(itemHeadLen), String.valueOf(this.this$0._head_len));
/*      */       }
/* 1132 */       if (itemHeadLen < this.this$0._head_len * 2) {
/* 1133 */         strItemLen = StringUtils.leftPad(strItemLen, this.this$0._head_len * 2, "0");
/*      */       }
/*      */ 
/* 1137 */       return HiConvHelper.ascStr2Bcd(strItemLen);
/*      */     }
/*      */ 
/*      */     AheadBinParser(HiItem.1 x1)
/*      */     {
/* 1099 */       this(x0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private abstract class AheadParser extends HiItem.Parser
/*      */   {
/*      */     private final HiItem this$0;
/*      */ 
/*      */     private AheadParser()
/*      */     {
/*  977 */       super(???, null);
/*      */     }
/*      */ 
/*      */     public void formatPlain(HiMessage msg, int plainOffset, int validLen) throws HiException {
/*  981 */       int itemLen = getAheadLength(msg, plainOffset, validLen);
/*      */ 
/*  983 */       plainOffset += this.this$0._head_len;
/*  984 */       buildEtfItem(msg, plainOffset, itemLen);
/*      */     }
/*      */ 
/*      */     public void formatETF(HiMessage msg, byte[] itemValBytes)
/*      */       throws HiException
/*      */     {
/*  990 */       Logger log = HiLog.getLogger(msg);
/*  991 */       if (log.isDebugEnabled()) {
/*  992 */         log.debug("formatETF(HiMessage, String) - start");
/*      */       }
/*      */ 
/*  995 */       int itemLen = itemValBytes.length;
/*      */ 
/*  999 */       if (this.this$0._include_len) {
/* 1000 */         itemLen += this.this$0._head_len;
/*      */       }
/*      */ 
/* 1005 */       itemValBytes = ArrayUtils.addAll(getFitLen(itemLen), itemValBytes);
/* 1006 */       this.this$0.addPlainItem(msg, itemValBytes);
/*      */ 
/* 1008 */       if (log.isDebugEnabled())
/* 1009 */         log.debug("formatETF(HiMessage, String) - end");
/*      */     }
/*      */ 
/*      */     private void buildEtfItem(HiMessage msg, int plain_offset, int itemLen)
/*      */       throws HiException
/*      */     {
/* 1015 */       Logger log = HiLog.getLogger(msg);
/* 1016 */       if (log.isDebugEnabled()) {
/* 1017 */         log.debug("buildEtfItem(HiMessage, int, int) - start");
/*      */       }
/*      */ 
/* 1020 */       HiByteBuffer plainContent = HiItemHelper.getPlainText(msg);
/*      */       try
/*      */       {
/* 1024 */         byte[] item = HiItemHelper.subPlainByte(plainContent, plain_offset, itemLen);
/*      */ 
/* 1026 */         this.this$0.addObjectItem(msg, item);
/*      */       }
/*      */       catch (IndexOutOfBoundsException e) {
/* 1029 */         throw new HiException("213128", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLen) }, e);
/*      */       }
/*      */       catch (HiException he) {
/* 1032 */         throw he;
/*      */       } catch (Throwable te) {
/* 1034 */         throw HiSysException.makeException("213128", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLen), "Sys Exception" }, te);
/*      */       }
/*      */ 
/* 1040 */       HiItemHelper.setPlainOffset(msg, plain_offset + itemLen);
/*      */ 
/* 1042 */       if (log.isDebugEnabled())
/* 1043 */         log.debug("buildEtfItem(HiMessage, int, int) - end");
/*      */     }
/*      */ 
/*      */     private int getAheadLength(HiMessage msg, int plain_offset, int validLen)
/*      */       throws HiException
/*      */     {
/* 1061 */       if (this.this$0._head_len > validLen)
/*      */       {
/* 1063 */         throw new HiException("213129", this.this$0.name, String.valueOf(this.this$0._head_len));
/*      */       }
/*      */ 
/* 1067 */       HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
/*      */ 
/* 1069 */       byte[] aheadLenStr = HiItemHelper.subPlainByte(plainBody, plain_offset, this.this$0._head_len);
/*      */ 
/* 1071 */       int itemLen = getFitLen(aheadLenStr);
/*      */ 
/* 1073 */       validLen -= this.this$0._head_len;
/*      */ 
/* 1075 */       if (this.this$0._include_len) {
/* 1076 */         itemLen -= this.this$0._head_len;
/*      */       }
/*      */ 
/* 1079 */       if (itemLen > validLen)
/*      */       {
/* 1081 */         throw new HiException("213130", this.this$0.name, String.valueOf(itemLen));
/*      */       }
/*      */ 
/* 1085 */       return itemLen;
/*      */     }
/*      */ 
/*      */     protected abstract int getFitLen(byte[] paramArrayOfByte)
/*      */       throws HiException;
/*      */ 
/*      */     protected abstract byte[] getFitLen(int paramInt)
/*      */       throws HiException;
/*      */ 
/*      */     AheadParser(HiItem.1 x1)
/*      */     {
/*  977 */       this(x0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private class DeliStrParser extends HiItem.Parser
/*      */   {
/*      */     private final HiItem this$0;
/*      */ 
/*      */     private DeliStrParser()
/*      */     {
/*  908 */       super(???, null);
/*      */     }
/*      */ 
/*      */     public void formatPlain(HiMessage msg, int plainOffset, int validLen)
/*      */       throws HiException
/*      */     {
/*  914 */       Logger log = HiLog.getLogger(msg);
/*  915 */       if (log.isDebugEnabled()) {
/*  916 */         log.debug("formatPlain(HiMessage, int, int) - start");
/*      */       }
/*      */ 
/*  919 */       HiByteBuffer plainContent = HiItemHelper.getPlainText(msg);
/*      */ 
/*  922 */       int idx = 0;
/*  923 */       int deliLen = this.this$0._deli_str.getBytes().length;
/*      */ 
/*  925 */       idx = HiItemHelper.indexOfBytePlain(plainContent, this.this$0._deli_str, plainOffset);
/*      */ 
/*  927 */       if (idx == -1)
/*      */       {
/*  929 */         idx = HiItemHelper.getPlainByteLen(plainContent);
/*  930 */         deliLen = 0;
/*      */       }
/*      */ 
/*  933 */       byte[] item = HiItemHelper.subPlainByte(plainContent, plainOffset, idx - plainOffset);
/*      */ 
/*  936 */       this.this$0.addObjectItem(msg, item);
/*      */ 
/*  938 */       HiItemHelper.setPlainOffset(msg, idx + deliLen);
/*      */ 
/*  940 */       if (log.isDebugEnabled())
/*  941 */         log.debug("formatPlain(HiMessage, int, int) - end");
/*      */     }
/*      */ 
/*      */     public void formatETF(HiMessage msg, byte[] itemValBytes)
/*      */       throws HiException
/*      */     {
/*  950 */       Logger log = HiLog.getLogger(msg);
/*  951 */       if (log.isDebugEnabled()) {
/*  952 */         log.debug("formatETF(HiMessage, String) - start");
/*      */       }
/*      */ 
/*  958 */       itemValBytes = ArrayUtils.addAll(itemValBytes, this.this$0._deli_str.getBytes());
/*      */ 
/*  960 */       this.this$0.addPlainItem(msg, itemValBytes);
/*      */ 
/*  965 */       if (log.isDebugEnabled())
/*  966 */         log.debug("formatETF(HiMessage, String) - end");
/*      */     }
/*      */ 
/*      */     DeliStrParser(HiItem.1 x1)
/*      */     {
/*  908 */       this(x0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private class DeliAscParser extends HiItem.Parser
/*      */   {
/*      */     private final HiItem this$0;
/*      */ 
/*      */     private DeliAscParser()
/*      */     {
/*  833 */       super(???, null);
/*      */     }
/*      */ 
/*      */     public void formatPlain(HiMessage msg, int plainOffset, int validLen)
/*      */       throws HiException
/*      */     {
/*  839 */       Logger log = HiLog.getLogger(msg);
/*  840 */       if (log.isDebugEnabled()) {
/*  841 */         log.debug("formatPlain(HiMessage, int, int) - start");
/*      */       }
/*      */ 
/*  844 */       HiByteBuffer plainContent = HiItemHelper.getPlainText(msg);
/*      */ 
/*  847 */       int idx = 0;
/*  848 */       short deliLen = 1;
/*      */       try {
/*  850 */         idx = HiItemHelper.indexOfBytePlain(plainContent, this.this$0._deli_asc, plainOffset);
/*      */ 
/*  852 */         if (idx == -1)
/*      */         {
/*  854 */           idx = HiItemHelper.getPlainByteLen(plainContent);
/*  855 */           deliLen = 0;
/*      */         }
/*      */ 
/*  858 */         byte[] item = HiItemHelper.subPlainByte(plainContent, plainOffset, idx - plainOffset);
/*      */ 
/*  861 */         this.this$0.addObjectItem(msg, item);
/*      */       } catch (HiException he) {
/*  863 */         throw he;
/*      */       }
/*      */       catch (Throwable te) {
/*  866 */         throw HiSysException.makeException("213127", new String[] { HiItem.access$700(this.this$0), String.valueOf(HiItem.access$1300(this.this$0)) }, te);
/*      */       }
/*      */ 
/*  871 */       HiItemHelper.setPlainOffset(msg, idx + deliLen);
/*      */ 
/*  873 */       if (log.isDebugEnabled())
/*  874 */         log.debug("formatPlain(HiMessage, int, int) - end");
/*      */     }
/*      */ 
/*      */     public void formatETF(HiMessage msg, byte[] itemValBytes)
/*      */       throws HiException
/*      */     {
/*  883 */       Logger log = HiLog.getLogger(msg);
/*  884 */       if (log.isDebugEnabled()) {
/*  885 */         log.debug("formatETF(HiMessage, String) - start");
/*      */       }
/*      */ 
/*  889 */       if (this.this$0._deli_asc == -1) {
/*  890 */         this.this$0.addPlainItem(msg, itemValBytes);
/*      */       } else {
/*  892 */         itemValBytes = ArrayUtils.add(itemValBytes, this.this$0._deli_asc);
/*  893 */         this.this$0.addPlainItem(msg, itemValBytes);
/*      */       }
/*      */ 
/*  896 */       if (log.isDebugEnabled())
/*  897 */         log.debug("formatETF(HiMessage, String) - end");
/*      */     }
/*      */ 
/*      */     DeliAscParser(HiItem.1 x1)
/*      */     {
/*  833 */       this(x0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private class FixedParser extends HiItem.Parser
/*      */   {
/*      */     private final HiItem this$0;
/*      */ 
/*      */     private FixedParser()
/*      */     {
/*  686 */       super(???, null);
/*      */     }
/*      */ 
/*      */     public void formatPlain(HiMessage msg, int plainOffset, int validLen)
/*      */       throws HiException
/*      */     {
/*  692 */       Logger log = HiLog.getLogger(msg);
/*  693 */       if (log.isDebugEnabled()) {
/*  694 */         log.debug("formatPlain(HiMessage, int, int) - start");
/*      */       }
/*      */ 
/*  697 */       int itemLenAttr = 0;
/*  698 */       if (this.this$0.lengthExp != null) {
/*      */         try {
/*  700 */           itemLenAttr = Integer.parseInt(this.this$0.lengthExp.getValue(HiMessageContext.getCurrentMessageContext()).trim());
/*      */         }
/*      */         catch (NumberFormatException e)
/*      */         {
/*  707 */           throw new HiException("213123", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr) }, e);
/*      */         }
/*      */         catch (Throwable te)
/*      */         {
/*  711 */           throw HiSysException.makeException("213123", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr), "Sys Exception" }, te);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  718 */       itemLenAttr = this.this$0.length;
/*      */ 
/*  721 */       if (validLen < itemLenAttr)
/*      */       {
/*  723 */         throw new HiException("213125", this.this$0.name, String.valueOf(itemLenAttr), String.valueOf(validLen));
/*      */       }
/*      */ 
/*  727 */       buildEtfItem(msg, plainOffset, itemLenAttr);
/*      */ 
/*  729 */       if (log.isDebugEnabled())
/*  730 */         log.debug("formatPlain(HiMessage, int, int) - end");
/*      */     }
/*      */ 
/*      */     public void formatETF(HiMessage msg, byte[] itemValBytes)
/*      */       throws HiException
/*      */     {
/*  739 */       Logger log = HiLog.getLogger(msg);
/*  740 */       if (log.isDebugEnabled()) {
/*  741 */         log.debug("formatETF(HiMessage, String) - start");
/*      */       }
/*      */ 
/*  745 */       int itemLenAttr = 0;
/*  746 */       if (this.this$0.lengthExp != null) {
/*      */         try {
/*  748 */           itemLenAttr = Integer.parseInt(this.this$0.lengthExp.getValue(HiMessageContext.getCurrentMessageContext()).trim());
/*      */         }
/*      */         catch (NumberFormatException e)
/*      */         {
/*  755 */           throw new HiException("213124", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr) }, e);
/*      */         }
/*      */         catch (Throwable te)
/*      */         {
/*  759 */           throw HiSysException.makeException("213124", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr), "Sys Exception" }, te);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  765 */       itemLenAttr = this.this$0.length;
/*      */ 
/*  768 */       buildFixedPlain(msg, itemValBytes, itemLenAttr);
/*      */ 
/*  770 */       if (log.isDebugEnabled())
/*  771 */         log.debug("formatETF(HiMessage, String) - end");
/*      */     }
/*      */ 
/*      */     private void buildEtfItem(HiMessage msg, int plain_offset, int itemLenAttr)
/*      */       throws HiException
/*      */     {
/*  778 */       HiByteBuffer plainContent = HiItemHelper.getPlainText(msg);
/*      */       try
/*      */       {
/*  782 */         byte[] item = HiItemHelper.subPlainByte(plainContent, plain_offset, itemLenAttr);
/*      */ 
/*  784 */         this.this$0.addObjectItem(msg, item);
/*      */       }
/*      */       catch (IndexOutOfBoundsException e) {
/*  787 */         throw new HiException("213126", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr) }, e);
/*      */       }
/*      */       catch (HiException he) {
/*  790 */         throw he;
/*      */       } catch (Throwable te) {
/*  792 */         throw new HiSysException("213126", new String[] { HiItem.access$700(this.this$0), String.valueOf(itemLenAttr), "Sys Exception" }, te);
/*      */       }
/*      */ 
/*  798 */       HiItemHelper.setPlainOffset(msg, plain_offset + itemLenAttr);
/*      */     }
/*      */ 
/*      */     private void buildFixedPlain(HiMessage msg, byte[] itemValBytes, int itemLenAttr)
/*      */       throws HiException
/*      */     {
/*  804 */       int itemLen = itemValBytes.length;
/*  805 */       if (itemLen > itemLenAttr)
/*      */       {
/*  807 */         itemValBytes = HiItemHelper.subPlainByte(itemValBytes, 0, itemLenAttr);
/*      */       }
/*  809 */       else if (itemLen < itemLenAttr) {
/*  810 */         byte[] fillBytes = null;
/*      */ 
/*  812 */         fillBytes = HiItemHelper.repeat(fillBytes, this.this$0.fill_asc, itemLenAttr - itemLen);
/*      */ 
/*  815 */         if (StringUtils.equals(this.this$0._align_mode, "right"))
/*  816 */           itemValBytes = ArrayUtils.addAll(fillBytes, itemValBytes);
/*      */         else {
/*  818 */           itemValBytes = ArrayUtils.addAll(itemValBytes, fillBytes);
/*      */         }
/*      */       }
/*      */ 
/*  822 */       this.this$0.addPlainItem(msg, itemValBytes);
/*      */     }
/*      */ 
/*      */     FixedParser(HiItem.1 x1)
/*      */     {
/*  686 */       this(x0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private abstract class Parser
/*      */   {
/*      */     private final HiItem this$0;
/*      */ 
/*      */     private Parser()
/*      */     {
/*      */     }
/*      */ 
/*      */     public abstract void formatPlain(HiMessage paramHiMessage, int paramInt1, int paramInt2)
/*      */       throws HiException;
/*      */ 
/*      */     public abstract void formatETF(HiMessage paramHiMessage, byte[] paramArrayOfByte)
/*      */       throws HiException;
/*      */ 
/*      */     Parser(HiItem.1 x1)
/*      */     {
/*  648 */       this(x0);
/*      */     }
/*      */   }
/*      */ }