 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import org.apache.commons.lang.StringUtils;
 
 public class HiWriteElement extends HiAbstractDoElement
 {
   public HiWriteElement()
     throws HiException
   {
     super.initAtt();
   }
 
   public String getNodeName()
   {
     return "WriteElement";
   }
 
   public int doElement(HiMessageContext messContext)
     throws HiException
   {
     label204: HiETF xmlNode;
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiAbstractDoElement.process00", HiEngineUtilities.getCurFlowStep(), getNodeName(), this.name, (this.ETF_name != null) ? this.ETF_name : ""));
     }
 
     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
     if (xmlContent == null)
     {
       throw new HiException("213197", this.name);
     }
 
     HiETF etfBody = mess.getETFBody();
 
     if (StringUtils.isNotBlank(this.ETF_name))
     {
       itemVal = getFitValue(mess, etfBody, log);
       if (itemVal != null) {
         break label204;
       }
       if (log.isInfoEnabled())
       {
         log.info("WriteElement:不存在指定ETF[" + this.ETF_name + "],necessary=no,本节点不构造");
       }
       return -2;
     }
 
     String itemVal = getXmlValue(mess, etfBody, log);
     if (log.isDebugEnabled()) {
       log.debug("WriteElement: 构造XML节点[" + this.name + "]");
     }
 
     HiETF xmlParent = HiItemHelper.getCurXmlRoot(mess);
 
     if (this.xml_level == 1)
     {
       xmlParent = xmlContent;
     }
     else if (this.xml_level == 2)
     {
       HiETF xmlGrandParent = xmlParent.getParent();
       if (xmlGrandParent == null)
       {
         throw new HiException("213194", this.name);
       }
       xmlParent = xmlGrandParent;
     }
 
     if (xmlParent == null)
     {
       xmlNode = xmlContent.addNodeBase(this.xml_name, itemVal, this.ns, this.ns_uri);
     }
     else
     {
       xmlNode = xmlParent.addNodeBase(this.xml_name, itemVal, this.ns, this.ns_uri);
     }
 
     messContext.setBaseSource("XML_ROOT", xmlNode);
 
     if (log.isInfoEnabled()) {
       log.info("添加XML节点:[" + this.name + "]=[" + itemVal + "]");
     }
     return 0;
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
         if (log.isDebugEnabled()) {
           log.debug(sm.getString("HiItem.formatEtf3", this.ETF_name, this.value));
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
 
       throw new HiException("213192", this.name, this.ETF_name);
     }
 
     if (!(itemNode.isEndNode()))
     {
       throw new HiException("213193", this.name, this.ETF_name);
     }
 
     String itemVal = itemNode.getValue();
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiItem.formatEtf2", this.ETF_name, itemVal));
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
       throw new HiException("213190", this.name);
     }
 
     if ((this.exp == null) || (!(StringUtils.isBlank(this.ETF_name))))
       return;
     throw new HiException("213191", this.name);
   }
 }