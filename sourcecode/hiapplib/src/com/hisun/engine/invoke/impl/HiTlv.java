 package com.hisun.engine.invoke.impl;
 
 import com.hisun.cfg.HiTlvItem;
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.HiITFEngineModel;
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
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 
 public class HiTlv extends HiEngineModel
 {
   private String tag;
   private boolean necessary = true;
 
   private String value = null;
 
   private HiExpression exp = null;
 
   private boolean isInit = false;
   private Map cfgMap;
   HiTlvItem tlvItem;
   private String pro_dll;
   private String pro_func;
   private HiMethodItem pro_method = null;
 
   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   private final HiStringManager sm = HiStringManager.getManager();
 
   public HiTlv() throws HiException
   {
     initAtt();
   }
 
   public void initAtt() throws HiException {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("initAtt() - start");
     }
     HiContext context = HiContext.getCurrentContext();
     HiITFEngineModel itemAttribute = HiITFEngineModel.getItemAttribute(context);
 
     if (itemAttribute != null) {
       this.necessary = itemAttribute.is_necessary();
 
       if (this.logger.isDebugEnabled()) {
         this.logger.debug("TLV继承属性:necessary[" + this.necessary + "]");
       }
     }
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("initAtt() - end");
   }
 
   public String getNodeName()
   {
     return "Tlv";
   }
 
   public String getTag() {
     return this.tag;
   }
 
   public void setTag(String tag) {
     this.tag = tag;
     if (this.logger.isDebugEnabled())
       this.logger.debug("setTag TAG[" + tag + "]");
   }
 
   public void setNecessary(String necessary)
   {
     if (StringUtils.equalsIgnoreCase(necessary, "no"))
     {
       this.necessary = false;
     }
     if (this.logger.isDebugEnabled())
       this.logger.debug("setNecessary TAG[" + this.tag + "]");
   }
 
   public String getValue()
   {
     return this.value;
   }
 
   public void setValue(String value) {
     this.value = value;
     if (this.logger.isDebugEnabled())
       this.logger.debug("setValue TAG[" + this.tag + "] ");
   }
 
   public void setExpression(String expression)
   {
     this.exp = HiExpFactory.createExp(expression);
     if (this.logger.isDebugEnabled())
       this.logger.debug("setExpression TAG[" + this.tag + "]");
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
 
   public String toString()
   {
     return super.toString() + ":tag[" + this.tag + "]";
   }
 
   public void loadAfter() throws HiException {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("loadAfter() - start");
     }
 
     if (StringUtils.isEmpty(this.tag))
     {
       throw new HiException("231508", this.tag);
     }
 
     if ((StringUtils.isNotBlank(this.pro_dll)) && (StringUtils.isNotBlank(this.pro_func))) {
       this.pro_method = HiItemHelper.getMethod(this.pro_dll, this.pro_func);
     }
 
     HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiByteStategy");
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("loadAfter() - end ");
   }
 
   public void process(HiMessageContext ctx) throws HiException
   {
     try {
       HiMessage msg = ctx.getCurrentMsg();
       Logger log = HiLog.getLogger(msg);
       if (log.isInfoEnabled()) {
         log.info(this.sm.getString("HiTlvProcess.process00", HiEngineUtilities.getCurFlowStep(), this.tag));
       }
 
       doProcess(ctx);
     } catch (HiException e) {
       throw e;
     } catch (Throwable te) {
       throw new HiSysException("231512", this.tag, te);
     }
   }
 
   public void doProcess(HiMessageContext ctx)
     throws HiException
   {
     HiByteBuffer tlvBody;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("doProcess(HiMessageContext) - start.\nTLV process start.");
     }
 
     initCfgNode(ctx);
 
     HiETF etfBody = msg.getETFBody();
     if (this.tlvItem.is_group)
     {
       int repeatNum = getRepeat_num(msg, this.tlvItem.repeat_name);
       if (this.tlvItem.node_type == 0)
       {
         for (int i = 1; i <= repeatNum; ++i)
         {
           if (log.isInfoEnabled()) {
             log.info(this.sm.getString("HiTlvProcess.doProcess1", this.tlvItem.tag, this.tlvItem.etf_name + "_" + i));
           }
 
           formatEtf(ctx, msg, etfBody, this.tlvItem.etf_name + "_" + i);
         }
 
       }
       else
       {
         for (int i = 1; i <= repeatNum; ++i)
         {
           if (log.isInfoEnabled()) {
             log.info(this.sm.getString("HiTlvProcess.doProcess1", this.tlvItem.tag, this.tlvItem.etf_name + "_" + i));
           }
 
           HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
           String curEtfLvl = HiItemHelper.getCurEtfLevel(msg);
 
           resetMsgState(msg, new HiByteBuffer(128));
           HiItemHelper.setCurEtfLevel(msg, curEtfLvl + this.tlvItem.etf_name + "_" + i + ".");
 
           super.process(ctx);
 
           tlvBody = HiItemHelper.getPlainText(msg);
 
           addPlainNode(msg, plainBody, tlvBody);
 
           resetMsgState(msg, plainBody);
           HiItemHelper.setCurEtfLevel(msg, curEtfLvl);
         }
 
       }
 
     }
     else if (this.tlvItem.node_type == 0)
     {
       formatEtf(ctx, msg, etfBody, this.tlvItem.etf_name);
     }
     else
     {
       HiByteBuffer plainBody;
       if (this.tlvItem.node_type == 1)
       {
         if (log.isInfoEnabled()) {
           log.info(this.sm.getString("HiTlvProcess.doProcess0", this.tlvItem.tag));
         }
 
         plainBody = HiItemHelper.getPlainText(msg);
         String curEtfLvl = HiItemHelper.getCurEtfLevel(msg);
 
         resetMsgState(msg, new HiByteBuffer(128));
         HiItemHelper.setCurEtfLevel(msg, curEtfLvl + this.tlvItem.etf_name + ".");
 
         super.process(ctx);
 
         tlvBody = HiItemHelper.getPlainText(msg);
 
         addPlainNode(msg, plainBody, tlvBody);
 
         resetMsgState(msg, plainBody);
         HiItemHelper.setCurEtfLevel(msg, curEtfLvl);
       }
       else if (this.tlvItem.node_type == 2)
       {
         if (log.isInfoEnabled()) {
           log.info(this.sm.getString("HiTlvProcess.doProcess0", this.tlvItem.tag));
         }
 
         plainBody = HiItemHelper.getPlainText(msg);
 
         resetMsgState(msg, new HiByteBuffer(128));
 
         super.process(ctx);
 
         HiByteBuffer tlvBody = HiItemHelper.getPlainText(msg);
 
         addPlainNode(msg, plainBody, tlvBody);
 
         resetMsgState(msg, plainBody);
       }
     }
 
     if (log.isDebugEnabled())
       log.debug("doProcess(HiMessage, HiMessageContext) - end");
   }
 
   private void formatEtf(HiMessageContext ctx, HiMessage msg, HiETF etfBody, String etf_name)
     throws HiException
   {
     Logger log = HiLog.getLogger(msg);
 
     String itemVal = HiItemHelper.getEtfItem(msg, etf_name);
     if (itemVal == null)
     {
       if (this.value != null)
       {
         itemVal = this.value;
         if (log.isInfoEnabled())
           log.info(this.sm.getString("HiItem.formatEtf3", etf_name, itemVal));
       }
       else if (!(this.necessary)) {
         if (log.isInfoEnabled()) {
           log.warn(this.sm.getString("HiTlvProcess.formatEtf1", etf_name));
         }
         itemVal = "";
       }
       else
       {
         throw new HiException("213122", etf_name, HiItemHelper.getCurEtfLevel(msg) + etf_name);
       }
 
       if (this.exp != null)
       {
         HiItemHelper.addEtfItem(msg, etf_name, itemVal);
       }
     }
     else if (log.isInfoEnabled()) {
       log.info(this.sm.getString("HiItem.formatEtf2", etf_name, itemVal));
     }
 
     if (this.exp != null) {
       try {
         itemVal = HiItemHelper.execExpression(msg, this.exp);
       } catch (HiException e) {
         if (!(this.necessary)) {
           log.warn("exp:" + this.exp + "计算失败!" + e.getMessage());
           itemVal = "";
         } else {
           throw e;
         }
       }
     }
     byte[] valBytes = itemVal.getBytes();
 
     if (this.pro_method != null) {
       valBytes = HiItemHelper.execMethod(this.pro_method, valBytes, log);
     }
 
     addPlainItem(msg, valBytes);
   }
 
   private void addPlainNode(HiMessage msg, HiByteBuffer plainBody, HiByteBuffer nodeVal) throws HiException {
     Logger log = HiLog.getLogger(msg);
 
     plainBody.append(getFitTag());
 
     plainBody.append(getFitHeadLen(nodeVal.length()));
 
     byte[] bytes = nodeVal.getBytes();
     plainBody.append(bytes);
 
     if (log.isInfoEnabled())
       log.info(this.sm.getString("HiTlvProcess.addPlainNode0", this.tlvItem.tag, String.valueOf(bytes.length), new String(bytes)));
   }
 
   private void addPlainItem(HiMessage msg, byte[] valBytes)
     throws HiException
   {
     byte[] bytes;
     Logger log = HiLog.getLogger(msg);
     HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
 
     plainBody.append(getFitTag());
 
     switch (this.tlvItem.data_type)
     {
     case 0:
       String hex = Long.toString(Long.parseLong(new String(valBytes)), 16);
       if (hex.length() % 2 != 0)
       {
         hex = '0' + hex;
       }
       bytes = HiConvHelper.ascStr2Bcd(hex);
       break;
     case 1:
       bytes = HiConvHelper.ascByte2Bcd(valBytes);
       break;
     case 2:
       bytes = valBytes;
       break;
     default:
       bytes = valBytes;
     }
 
     plainBody.append(getFitHeadLen(bytes.length));
 
     plainBody.append(bytes);
 
     if (log.isInfoEnabled())
       log.info(this.sm.getString("HiTlvProcess.addPlainItem0", this.tlvItem.tag, String.valueOf(bytes.length), new String(bytes)));
   }
 
   private byte[] getFitHeadLen(int itemLen)
     throws HiException
   {
     if (this.tlvItem.len_type == 0)
     {
       strItemLen = Long.toString(itemLen, 16);
 
       int itemHeadLen = strItemLen.length();
       if (itemHeadLen % 2 != 0) {
         strItemLen = "0" + strItemLen;
       }
 
       if (itemHeadLen > this.tlvItem.head_len * 2)
       {
         throw new HiException("213133", this.tag, String.valueOf(itemHeadLen), String.valueOf(this.tlvItem.head_len));
       }
       if (itemHeadLen < this.tlvItem.head_len * 2) {
         strItemLen = StringUtils.leftPad(strItemLen, this.tlvItem.head_len * 2, "0");
       }
 
       return HiConvHelper.ascStr2Bcd(strItemLen);
     }
 
     String strItemLen = String.valueOf(itemLen);
 
     if (strItemLen.length() > this.tlvItem.head_len)
     {
       throw new HiException("213134", this.tag, String.valueOf(strItemLen.length()), String.valueOf(this.tlvItem.head_len));
     }
 
     if (strItemLen.length() < this.tlvItem.head_len) {
       strItemLen = StringUtils.leftPad(strItemLen, this.tlvItem.head_len, "0");
     }
 
     return strItemLen.getBytes();
   }
 
   private byte[] getFitTag()
   {
     switch (this.tlvItem.tag_type)
     {
     case 1:
       return HiConvHelper.ascStr2Bcd(this.tag);
     case 2:
       return this.tag.getBytes();
     case 0:
       String hex = Integer.toString(Integer.parseInt(this.tag), 16);
       if (hex.length() % 2 != 0)
       {
         hex = '0' + hex;
       }
       return HiConvHelper.ascStr2Bcd(hex);
     }
 
     return HiConvHelper.ascStr2Bcd(this.tag);
   }
 
   private void initCfgNode(HiMessageContext ctx)
     throws HiException
   {
     if (this.isInit) {
       return;
     }
 
     this.cfgMap = ((Map)ctx.getProperty("TLV_CFG_NODE"));
     if (this.cfgMap == null) {
       throw new HiException("231510", "");
     }
 
     this.tlvItem = ((HiTlvItem)this.cfgMap.get(this.tag));
     if (this.tlvItem != null)
       return;
     throw new HiException("231511", this.tag);
   }
 
   protected void resetMsgState(HiMessage msg, HiByteBuffer packValue)
   {
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled())
     {
       log.debug("resetMsgState(HiMessage, String, int) - start");
     }
 
     msg.setHeadItem("PlainText", packValue);
 
     if (!(log.isDebugEnabled()))
       return;
     log.debug("resetMsgState(HiMessage, String, int) - end");
   }
 
   private int getRepeat_num(HiMessage msg, String repeat_name)
   {
     if (repeat_name == null)
     {
       return 0;
     }
     return Integer.parseInt(HiItemHelper.getEtfItem(msg, repeat_name));
   }
 }