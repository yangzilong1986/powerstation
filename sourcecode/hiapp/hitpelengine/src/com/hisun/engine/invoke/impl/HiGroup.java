 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.HiITFEngineModel;
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
 import com.hisun.util.HiStringManager;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiGroup extends HiITFEngineModel
 {
   protected final Logger logger;
   private String name;
   private int offset;
   private int repeat_num;
   private String repeat_name;
   private int length;
   private HiExpression lengthExp;
   private String record_name;
   private DeliParser deliParser;
 
   public HiGroup()
   {
     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
 
     this.offset = 0;
 
     this.repeat_num = -1;
 
     this.length = -1;
     this.lengthExp = null;
 
     this.deliParser = null;
   }
 
   public void setName(String name)
   {
     this.name = name;
   }
 
   public void setOffset(String offset)
     throws HiException
   {
     try
     {
       this.offset = Integer.parseInt(offset.trim());
     }
     catch (Exception e)
     {
       throw new HiException("213152", new String[] { this.name, offset }, e);
     }
   }
 
   public void setRepeat_name(String repeat_name)
   {
     this.repeat_name = repeat_name;
   }
 
   public void setRecord_name(String record_name)
   {
     this.record_name = record_name;
   }
 
   public void setLength(String length) throws HiException {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setLength(String) - start:input[" + length + "]");
     }
 
     if (NumberUtils.isNumber(length))
     {
       this.length = Integer.parseInt(length.trim());
     }
     else
     {
       if (length == null)
       {
         throw new HiSysException("213104", new String[] { this.name, length });
       }
 
       if (this.logger.isInfoEnabled())
       {
         this.logger.info(getNodeName() + ":setLength(String)->lengthExpression[" + length + "]");
       }
       this.length = 0;
       this.lengthExp = HiExpFactory.createExp(length);
     }
     this._type = "fixed";
   }
 
   public String getNodeName()
   {
     return "Group";
   }
 
   public String toString()
   {
     return super.toString() + ":name[" + this.name + "]";
   }
 
   public void setRepeat_num(String repeat_num) throws HiException
   {
     try
     {
       this.repeat_num = Integer.parseInt(repeat_num.trim());
     }
     catch (Exception e)
     {
       throw new HiException("213153", new String[] { this.name, repeat_num }, e);
     }
   }
 
   public void loadAfter()
     throws HiException
   {
     if (StringUtils.isBlank(this.name))
     {
       throw new HiException("213151");
     }
 
     if (StringUtils.equals(this._type, "deli"))
     {
       this.deliParser = new DeliAscParser(null);
     } else {
       if (!(StringUtils.equals(this._type, "deli_str")))
         return;
       this.deliParser = new DeliStrParser(null);
     }
   }
 
   public void process(HiMessageContext ctx) throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiGroup.process00", HiEngineUtilities.getCurFlowStep(), this.name));
     }
 
     try
     {
       HiMessage mess = ctx.getCurrentMsg();
       doProcess(mess, ctx);
     }
     catch (HiException e)
     {
       throw e;
     }
     catch (Throwable te)
     {
       throw new HiSysException("213159", this.name, te);
     }
   }
 
   public void doProcess(HiMessage mess, HiMessageContext tranData)
     throws HiException
   {
     String ect = mess.getHeadItem("ECT");
 
     if (StringUtils.equals(ect, "text/etf"))
     {
       formatEtf(mess, tranData);
     }
     else if (StringUtils.equals(ect, "text/plain"))
     {
       formatPlain(mess, tranData);
     }
     else
     {
       throw new HiException("213154", ect);
     }
   }
 
   private void formatEtf(HiMessage etfMsg, HiMessageContext tranData)
     throws HiException
   {
     int repeatNum;
     HiETF groupNode;
     int i;
     Iterator iter;
     Logger log = HiLog.getLogger(etfMsg);
     String curEtfLevel = HiItemHelper.getCurEtfLevel(etfMsg);
     if (log.isDebugEnabled())
     {
       log.debug(sm.getString("HiGroup.formatEtf0", curEtfLevel));
     }
 
     HiETF etfBody = etfMsg.getETFBody();
 
     if (this.offset > 0)
     {
       fitGroupPlain(etfMsg, this.offset);
     }
 
     if (StringUtils.isNotBlank(this.repeat_name))
     {
       repeatNum = getRepeat_num(etfBody);
     }
     else
     {
       repeatNum = this.repeat_num;
     }
 
     if (repeatNum >= 0)
     {
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiGroup.showRepeatNum", String.valueOf(repeatNum)));
       }
       for (int groupIdx = 1; groupIdx <= repeatNum; ++groupIdx)
       {
         if (log.isInfoEnabled())
           log.info(sm.getString("HiGroup.formatEtfRun", this.name + "_" + groupIdx));
         HiEngineUtilities.setCurFlowStep(groupIdx);
         doGroupItem(etfMsg, this.name + "_" + groupIdx, curEtfLevel, tranData);
       }
 
     }
     else
     {
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiGroup.formatEtf1", this.name));
       }
       groupNode = null;
       i = 0;
       for (iter = getGroups(etfBody).iterator(); iter.hasNext(); )
       {
         groupNode = (HiETF)iter.next();
 
         HiEngineUtilities.setCurFlowStep(i);
         ++i;
         if (log.isInfoEnabled())
           log.info(sm.getString("HiGroup.formatEtfRun", groupNode.getName()));
         doGroupItem(etfMsg, groupNode.getName(), curEtfLevel, tranData);
       }
 
     }
 
     if (this.deliParser == null)
       return;
     this.deliParser.fitGroupPlain(etfMsg);
   }
 
   private void formatPlain(HiMessage plainMsg, HiMessageContext tranData)
     throws HiException
   {
     int repeatNum;
     HiByteBuffer plainBuf;
     int plainLen;
     Logger log = HiLog.getLogger(plainMsg);
     String curEtfLevel = HiItemHelper.getCurEtfLevel(plainMsg);
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiGroup.formatPlain0", curEtfLevel));
     }
     HiETF etfBody = plainMsg.getETFBody();
 
     int groupIdx = 1;
 
     int plain_offset = HiItemHelper.getPlainOffset(plainMsg) + this.offset;
 
     if (this.offset > 0)
     {
       HiItemHelper.setPlainOffset(plainMsg, plain_offset);
     }
 
     if (StringUtils.isNotBlank(this.repeat_name))
     {
       repeatNum = getRepeat_num(etfBody);
     }
     else
     {
       repeatNum = this.repeat_num;
     }
 
     if (repeatNum >= 0)
     {
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiGroup.showRepeatNum", String.valueOf(repeatNum)));
       }
       for (groupIdx = 1; ; ++groupIdx) { if (groupIdx > repeatNum)
           return;
         addGroupEtfItem(etfBody, groupIdx, curEtfLevel);
 
         if (log.isInfoEnabled()) {
           log.info(sm.getString("HiGroup.formatPlainRun", this.name + "_" + groupIdx));
         }
         doGroupItem(plainMsg, this.name + "_" + groupIdx, curEtfLevel, tranData);
       }
     }
 
     if (this.deliParser != null)
     {
       if (log.isDebugEnabled()) {
         log.debug(sm.getString("HiGroup.formatPlain1", this.deliParser.getDeli()));
       }
 
       plainBuf = HiItemHelper.getPlainText(plainMsg);
       plainLen = HiItemHelper.getPlainByteLen(plainBuf);
 
       while (this.deliParser.isNotGroupEnd(plainMsg, plainBuf, plainLen))
       {
         addGroupEtfItem(etfBody, groupIdx, curEtfLevel);
 
         if (log.isInfoEnabled())
           log.info(sm.getString("HiGroup.formatPlainRun", this.name + "_" + groupIdx));
         doGroupItem(plainMsg, this.name + "_" + groupIdx, curEtfLevel, tranData);
 
         ++groupIdx;
       }
 
       if (StringUtils.isNotBlank(this.record_name))
       {
         etfBody.setGrandChildNode(curEtfLevel + this.record_name, String.valueOf(groupIdx - 1));
       }
 
       HiItemHelper.addPlainOffset(plainMsg, this.deliParser.getDeliLen());
     }
     else if (this.length >= 0)
     {
       int curPos = HiItemHelper.getPlainOffset(plainMsg);
       int endPos = getLengthExpValue() + curPos;
       while (curPos < endPos)
       {
         addGroupEtfItem(etfBody, groupIdx, curEtfLevel);
 
         if (log.isInfoEnabled())
           log.info(sm.getString("HiGroup.formatPlainRun", this.name + "_" + groupIdx));
         doGroupItem(plainMsg, this.name + "_" + groupIdx, curEtfLevel, tranData);
 
         ++groupIdx;
         curPos = HiItemHelper.getPlainOffset(plainMsg);
       }
 
       if (StringUtils.isNotBlank(this.record_name))
       {
         etfBody.setGrandChildNode(curEtfLevel + this.record_name, String.valueOf(groupIdx - 1));
       }
 
     }
     else
     {
       plainBuf = HiItemHelper.getPlainText(plainMsg);
       plainLen = HiItemHelper.getPlainByteLen(plainBuf);
 
       int curPos = HiItemHelper.getPlainOffset(plainMsg);
       while (curPos < plainLen)
       {
         addGroupEtfItem(etfBody, groupIdx, curEtfLevel);
 
         if (log.isInfoEnabled())
           log.info(sm.getString("HiGroup.formatPlainRun", this.name + "_" + groupIdx));
         doGroupItem(plainMsg, this.name + "_" + groupIdx, curEtfLevel, tranData);
 
         ++groupIdx;
         curPos = HiItemHelper.getPlainOffset(plainMsg);
       }
 
       if (!(StringUtils.isNotBlank(this.record_name)))
         return;
       etfBody.setGrandChildNode(curEtfLevel + this.record_name, String.valueOf(groupIdx - 1));
     }
   }
 
   private void doGroupItem(HiMessage msg, String curGroup, String curEtfLevel, HiMessageContext tranData)
     throws HiException
   {
     addEtfLevel(msg, curGroup, curEtfLevel);
 
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiGroup.showEtfLevel", curEtfLevel + curGroup));
     }
     try
     {
       super.process(tranData);
     }
     catch (HiException e)
     {
       throw e;
     }
     finally
     {
       removeEtfLevel(msg, curEtfLevel);
     }
   }
 
   private void addEtfLevel(HiMessage msg, String curGroup, String curEtfLevel)
   {
     msg.setHeadItem("ETF_LEVEL", curEtfLevel + curGroup + ".");
   }
 
   private void removeEtfLevel(HiMessage msg, String curEtfLevel)
   {
     msg.setHeadItem("ETF_LEVEL", curEtfLevel);
   }
 
   private void addGroupEtfItem(HiETF etfBody, int groupIdx, String curEtfLevel)
   {
     etfBody.setGrandChildNode(curEtfLevel + this.name + "_" + groupIdx, "");
   }
 
   private void fitGroupPlain(HiMessage msg, int offset)
   {
     HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
 
     plainBody.append(StringUtils.repeat(" ", offset));
   }
 
   private List getGroups(HiETF etfBody)
   {
     return etfBody.getGrandChildFuzzyEnd(this.name + "_");
   }
 
   private int getRepeat_num(HiETF etfBody)
   {
     String strNum = etfBody.getGrandChildValue(this.repeat_name);
 
     if (strNum == null)
     {
       return 0;
     }
 
     try
     {
       return Integer.parseInt(strNum.trim());
     }
     catch (NumberFormatException ne)
     {
     }
     catch (Exception e)
     {
     }
 
     return 0;
   }
 
   private int getLengthExpValue() throws HiException
   {
     if (this.lengthExp != null)
     {
       String expVal = null;
       try {
         expVal = this.lengthExp.getValue(HiMessageContext.getCurrentMessageContext());
         return Integer.parseInt(expVal.trim());
       } catch (NumberFormatException e) {
         throw new HiException("213124", new String[] { this.name, expVal }, e);
       }
       catch (Throwable te)
       {
         throw HiSysException.makeException("213124", new String[] { this.name, expVal, "Sys Exception" }, te);
       }
 
     }
 
     return this.length;
   }
 
   private int getGroupEndPos(HiMessage msg, int groupStart)
   {
     HiByteBuffer plainBuf = HiItemHelper.getPlainText(msg);
 
     return HiItemHelper.indexOfBytePlain(plainBuf, this._deli_str, groupStart);
   }
 
   private boolean isNotGroupEnd(HiMessage msg, int endPos)
     throws HiException
   {
     int curPos = HiItemHelper.getPlainOffset(msg);
 
     if (curPos < endPos)
     {
       return true;
     }
     if (curPos == endPos)
     {
       return false;
     }
 
     throw new HiException("213157", this.name, this._deli_str);
   }
 
   private class DeliStrParser extends HiGroup.DeliParser
   {
     private int deliStrLen;
     private final HiGroup this$0;
 
     private DeliStrParser()
     {
       super(???, null);
 
       this.deliStrLen = HiItemHelper.getPlainByteLen(this.this$0._deli_str); }
 
     public boolean isNotGroupEnd(HiMessage msg, HiByteBuffer plainBuf, int plainBufLen) {
       int curPos = HiItemHelper.getPlainOffset(msg);
       if (curPos >= plainBufLen)
       {
         return false;
       }
 
       return (this.this$0._deli_str.equals(plainBuf.substr(curPos, this.this$0._deli_str.length())));
     }
 
     public void fitGroupPlain(HiMessage msg)
     {
       HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
       plainBody.append(this.this$0._deli_str);
     }
 
     public int getDeliLen()
     {
       return this.deliStrLen;
     }
 
     public String getDeli()
     {
       return "deli_str->" + this.this$0._deli_str;
     }
 
     DeliStrParser(HiGroup.1 x1)
     {
       this(x0);
     }
   }
 
   private class DeliAscParser extends HiGroup.DeliParser
   {
     private final HiGroup this$0;
 
     private DeliAscParser()
     {
       super(???, null);
     }
 
     public boolean isNotGroupEnd(HiMessage msg, HiByteBuffer plainBuf, int plainBufLen)
     {
       int curPos = HiItemHelper.getPlainOffset(msg);
       if (curPos >= plainBufLen)
       {
         return false;
       }
 
       return (this.this$0._deli_asc == plainBuf.charAt(curPos));
     }
 
     public void fitGroupPlain(HiMessage msg)
     {
       HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
       plainBody.append(this.this$0._deli_asc);
     }
 
     public int getDeliLen()
     {
       return 1;
     }
 
     public String getDeli()
     {
       return "deli_asc->" + Byte.toString(this.this$0._deli_asc);
     }
 
     DeliAscParser(HiGroup.1 x1)
     {
       this(x0);
     }
   }
 
   private abstract class DeliParser
   {
     private final HiGroup this$0;
 
     private DeliParser()
     {
     }
 
     public abstract boolean isNotGroupEnd(HiMessage paramHiMessage, HiByteBuffer paramHiByteBuffer, int paramInt);
 
     public abstract void fitGroupPlain(HiMessage paramHiMessage);
 
     public abstract int getDeliLen();
 
     public abstract String getDeli();
 
     DeliParser(HiGroup.1 x1)
     {
       this(x0);
     }
   }
 }