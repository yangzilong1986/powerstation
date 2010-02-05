 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.message.HiXmlETF;
 import com.hisun.util.HiStringManager;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 import org.dom4j.Node;
 
 public class HiReadElement extends HiAbstractDoElement
 {
   public String getNodeName()
   {
     return "ReadElement";
   }
 
   public HiReadElement() throws HiException {
     super.initAtt();
   }
 
   public int doElement(HiMessageContext messContext)
     throws HiException
   {
     HiETF xmlNode;
     HiMessage mess = messContext.getCurrentMsg();
     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
     if (xmlContent == null)
     {
       throw new HiException("213174", this.name);
     }
     Logger log = HiLog.getLogger(mess);
 
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
         throw new HiException("213175", this.name);
       }
 
       xmlParent = xmlGrandParent;
     }
 
     if (xmlParent == null)
     {
       xmlNode = xmlContent.getGrandChildNodeBase(this.xml_name, this.ns, this.ns_uri);
     }
     else
     {
       xmlNode = xmlParent.getGrandChildNodeBase(this.xml_name, this.ns, this.ns_uri);
     }
 
     if (this.xml_level == 3)
     {
       Element element = ((HiXmlETF)xmlParent).getNode();
       Node currNode = ((HiXmlETF)xmlParent).getNode().selectSingleNode(this.xml_name);
       if (currNode != null) {
         xmlNode = new HiXmlETF((Element)(Element)currNode);
       }
 
     }
 
     if (xmlNode == null)
     {
       if (!(this.necessary))
       {
         if (log.isInfoEnabled())
         {
           log.info("ReadElement:从XML报文定位不到指定节点名[" + this.name + "],necessary=no,本节点及子节点不解析");
         }
         return -2;
       }
 
       throw new HiException("213176", this.name);
     }
 
     if (StringUtils.isBlank(this.ETF_name))
     {
       if (log.isDebugEnabled()) {
         log.debug("ReadElement:从XML报文定位节点名[" + this.name + "]");
       }
       messContext.setBaseSource("XML_ROOT", xmlNode);
       return 1;
     }
     if (!(xmlNode.isEndNode()))
     {
       throw new HiException("213177", this.name);
     }
 
     String xmlVal = xmlNode.getValue();
     if (log.isDebugEnabled()) {
       log.debug("ReadElement: 从XML报文读取节点名[" + this.name + "] 节点值[" + xmlVal + "]");
     }
 
     HiETF etfBody = mess.getETFBody();
 
     etfBody.setGrandChildNode(HiItemHelper.getCurEtfLevel(mess) + this.ETF_name, xmlVal);
 
     if (log.isDebugEnabled()) {
       log.debug("ReadElement: 更新到ETF 域名[" + this.ETF_name + "] 值[" + xmlVal + "]");
     }
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiAbstractDoElement.process00", HiEngineUtilities.getCurFlowStep(), getNodeName(), this.name, this.ETF_name, xmlVal));
     }
 
     messContext.setBaseSource("XML_ROOT", xmlNode);
 
     if (this.exp != null)
     {
       try
       {
         HiItemHelper.execExpression(mess, this.exp, this.ETF_name);
       }
       catch (Throwable te)
       {
         throw HiException.makeException("213183", this.name, this.exp.getExp(), te);
       }
 
     }
 
     return 0;
   }
 
   public void loadCheck()
     throws HiException
   {
     if (!(StringUtils.isBlank(this.name)))
       return;
     throw new HiException("213173", this.name);
   }
 }