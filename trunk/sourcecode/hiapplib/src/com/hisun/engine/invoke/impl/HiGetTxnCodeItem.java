 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.invoke.HiStrategyFactory;
 import com.hisun.exception.HiException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiByteBuffer;
 import org.apache.commons.lang.StringUtils;
 
 public class HiGetTxnCodeItem extends HiItem
 {
   private HiExpression _valueExpr;
   private int pos = 0;
 
   public void setPos(String pos) throws HiException
   {
     try
     {
       this.pos = Integer.parseInt(pos.trim());
     }
     catch (NumberFormatException e)
     {
       throw new HiException("213160", pos, e);
     }
   }
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     String ect = mess.getHeadItem("ECT");
 
     if (StringUtils.equals(ect, "text/plain"))
     {
       if (StringUtils.equals(get_type(), "xml"))
       {
         readXmlNode(mess);
       }
       else if (StringUtils.equals(get_type(), "etf"))
       {
         readXmlNode(mess);
       }
       else if (StringUtils.isNotEmpty(getValue()))
       {
         addObjectItem(mess, this._valueExpr.getValue(messContext));
       }
       else
       {
         if ((StringUtils.equals(get_type(), "deli")) && (this.pos > 0))
         {
           setDeliStartOffset(mess);
         }
         else if ((StringUtils.equals(get_type(), "deli_str")) && (this.pos > 0))
         {
           setDeliStrStartOffset(mess);
         }
         formatPlain(mess);
       }
 
       HiItemHelper.setPlainOffset(mess, 0);
     }
     else if (StringUtils.equals(ect, "text/xml"))
     {
       if (!(StringUtils.equals(get_type(), "xml"))) {
         return;
       }
       readXmlNode(mess);
     }
     else
     {
       throw new HiException("213161", ect);
     }
   }
 
   public void loadAfter()
     throws HiException
   {
     if (StringUtils.equals(get_type(), "xml"))
     {
       if (StringUtils.isBlank(getValue()))
       {
         throw new HiException("213162", "");
       }
 
       HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiXmlStategy");
 
       return;
     }
     if (StringUtils.equals(get_type(), "etf"))
     {
       if (StringUtils.isBlank(getValue()))
       {
         throw new HiException("213162", "");
       }
 
       HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiETFStategy");
 
       return;
     }
     if (StringUtils.isNotEmpty(getValue()))
     {
       HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiByteStategy");
 
       String value = getValue();
       this._valueExpr = HiExpFactory.createExp(value);
       return;
     }
 
     super.loadAfter();
   }
 
   protected void addObjectItem(HiMessage strMsg, byte[] itemBytes)
     throws HiException
   {
     Logger log = HiLog.getLogger(strMsg);
     if (log.isDebugEnabled()) {
       log.debug("HiGetTxnCodItem:addObjectItem(HiMessage, byte[]) - start");
     }
     if (this.pro_method != null) {
       itemBytes = HiItemHelper.execMethod(this.pro_method, itemBytes, log);
     }
 
     addObjectItem(strMsg, new String(itemBytes));
 
     if (log.isDebugEnabled())
       log.debug("HiGetTxnCodItem:addObjectItem(HiMessage, byte[]) - end");
   }
 
   protected void addObjectItem(HiMessage strMsg, String item)
     throws HiException
   {
     strMsg.setHeadItem("STC", item);
   }
 
   private void setDeliStartOffset(HiMessage strMsg)
     throws HiException
   {
     HiByteBuffer plainText = HiItemHelper.getPlainText(strMsg);
     int startIdx = 0;
     for (int i = 1; i < this.pos; ++i)
     {
       startIdx = HiItemHelper.indexOfBytePlain(plainText, get_deli_asc(), startIdx);
 
       if (startIdx < 0)
       {
         throw new HiException("213163", String.valueOf(this.pos));
       }
       ++startIdx;
     }
     HiItemHelper.setPlainOffset(strMsg, startIdx);
   }
 
   private void setDeliStrStartOffset(HiMessage strMsg) throws HiException
   {
     HiByteBuffer plainText = HiItemHelper.getPlainText(strMsg);
     int startIdx = 0;
     for (int i = 1; i < this.pos; ++i)
     {
       startIdx = HiItemHelper.indexOfBytePlain(plainText, get_deli_str(), startIdx);
 
       if (startIdx < 0)
       {
         throw new HiException("213163", String.valueOf(this.pos));
       }
       startIdx += HiItemHelper.getPlainByteLen(get_deli_str());
     }
     HiItemHelper.setPlainOffset(strMsg, startIdx);
   }
 
   private void readXmlNode(HiMessage xmlMsg) throws HiException
   {
     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(xmlMsg);
     if (xmlContent == null)
     {
       throw new HiException("213164", HiItemHelper.getPlainObject(xmlMsg).toString());
     }
 
     String xmlVal = xmlContent.getGrandChildValueBase(getValue());
     if (StringUtils.isEmpty(xmlVal))
     {
       throw new HiException("213165", getValue());
     }
 
     addObjectItem(xmlMsg, xmlVal);
   }
 }