 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSysException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import org.apache.commons.lang.StringUtils;
 
 public class HiWriteAttr extends HiAbstractDoElement
 {
   public HiWriteAttr()
     throws HiException
   {
     super.initAtt();
   }
 
   public String getNodeName()
   {
     return "WriteAttr";
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiWriteAttr.process00", HiEngineUtilities.getCurFlowStep(), this.name, (this.ETF_name != null) ? this.ETF_name : ""));
     }
 
     try
     {
       doElement(ctx);
     }
     catch (HiException e)
     {
       throw e;
     }
     catch (Throwable te)
     {
       throw new HiSysException("213207", this.name, te);
     }
   }
 
   public int doElement(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
 
     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
     if (xmlContent == null)
     {
       throw new HiException("213197", this.name);
     }
 
     HiETF xmlParent = HiItemHelper.getCurXmlRoot(mess);
     if (xmlParent == null)
     {
       xmlParent = xmlContent;
     }
     String curXmlLevel = xmlParent.getName();
 
     Logger log = HiLog.getLogger(mess);
 
     HiETF etfBody = mess.getETFBody();
 
     if (StringUtils.isNotBlank(this.ETF_name))
     {
       itemVal = getFitValue(mess, etfBody, log);
       if (itemVal != null) {
         break label146;
       }
       if (log.isInfoEnabled())
       {
         log.info("WriteAttr:不存在指定ETF[" + this.ETF_name + "],necessary=no,本属性不构造");
       }
       return -2;
     }
 
     String itemVal = getXmlValue(mess, etfBody, log);
 
     label146: xmlParent.setAttrValue(this.name, itemVal, this.ns, this.ns_uri);
 
     if (log.isInfoEnabled()) {
       log.info("添加属性:[" + this.name + "]=[" + itemVal + "]");
     }
     return -2;
   }
 
   protected String getFitValue(HiMessage mess, HiETF etfBody, Logger log)
     throws HiException
   {
     HiETF itemNode = HiItemHelper.getEtfItemNode(mess, this.ETF_name);
 
     if (itemNode == null)
     {
       if (this.value != null)
       {
         itemVal = this.value;
         if (log.isInfoEnabled()) {
           log.info(sm.getString("HiItem.formatEtf3", this.ETF_name, this.value));
         }
         if (this.exp == null) {
           break label164;
         }
         HiItemHelper.addEtfItem(mess, this.ETF_name, itemVal); break label164:
       }
 
       if (!(this.necessary))
       {
         return null;
       }
 
       throw new HiException("213199", this.name, this.ETF_name);
     }
 
     if (!(itemNode.isEndNode()))
     {
       throw new HiException("213200", this.name, this.ETF_name);
     }
 
     String itemVal = itemNode.getValue();
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiItem.formatEtf2", this.ETF_name, itemVal));
     }
 
     if (this.exp != null)
     {
       label164: itemVal = HiItemHelper.execExpression(mess, this.exp);
     }
     return itemVal;
   }
 
   protected String getXmlValue(HiMessage mess, HiETF etfBody, Logger log)
     throws HiException
   {
     String itemVal = null;
     if (this.value != null)
     {
       itemVal = this.value;
       if (log.isDebugEnabled()) {
         log.debug(sm.getString("HiItem.formatEtf3", "", this.value));
       }
     }
     else if (this.exp != null)
     {
       itemVal = HiItemHelper.execExpression(mess, this.exp);
     }
     return itemVal;
   }
 
   public void loadCheck()
     throws HiException
   {
     if (StringUtils.isBlank(this.name))
     {
       throw new HiException("213195", this.name);
     }
 
     if ((this.exp == null) || (!(StringUtils.isBlank(this.ETF_name))))
       return;
     throw new HiException("213195", this.name);
   }
 }