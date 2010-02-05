 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiReadGroupElement extends HiAbstractDoElement
 {
   public HiReadGroupElement()
     throws HiException
   {
     super.initAtt();
   }
 
   public String getNodeName()
   {
     return "ReadGroupElement";
   }
 
   protected int doElement(HiMessageContext messContext) throws HiException {
     HiMessage mess = messContext.getCurrentMsg();
     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
     if (xmlContent == null)
     {
       throw new HiException("213186", this.name);
     }
     Logger log = HiLog.getLogger(mess);
 
     List childNodes = null;
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
         throw new HiException("213187", this.name);
       }
       xmlParent = xmlGrandParent;
     }
     if (xmlParent == null)
     {
       childNodes = xmlContent.getGrandChildNodesBase(this.xml_name, this.ns, this.ns_uri);
     }
     else
     {
       childNodes = xmlParent.getGrandChildNodesBase(this.xml_name, this.ns, this.ns_uri);
     }
 
     if ((childNodes == null) || (childNodes.isEmpty()))
     {
       if (!(this.necessary))
       {
         if (log.isInfoEnabled())
         {
           log.info("ReadGroupElement:不存在XML节点名[" + this.name + "],necessary=no,本节点及子节点不解析");
         }
         return -2;
       }
       throw new HiException("213188", this.name);
     }
 
     int repeatNum = 0;
     if (StringUtils.isNotBlank(this.repeat_name))
     {
       repeatNum = getRepeat_num(mess);
     }
 
     if (repeatNum == 0)
     {
       repeatNum = this.repeat_num;
     }
 
     if (repeatNum > 0)
     {
       if (log.isInfoEnabled()) {
         log.info("Group: 获取RepeateNum=" + repeatNum);
       }
       if (repeatNum != childNodes.size())
       {
         throw new HiException("213189", this.name, String.valueOf(repeatNum));
       }
 
     }
 
     int i = 1;
     HiETF etfBody = mess.getETFBody();
     String curEtfLevel = HiItemHelper.getCurEtfLevel(mess);
     for (Iterator iter = childNodes.iterator(); iter.hasNext(); )
     {
       HiETF xmlNode = (HiETF)iter.next();
       HiEngineUtilities.setCurFlowStep(i);
 
       HiItemHelper.addEtfItem(mess, this.ETF_name + "_" + i, xmlNode.getValue());
 
       if (log.isInfoEnabled()) {
         log.info("ReadGroupElement:构造ETF Group域[" + this.ETF_name + "]");
       }
       List child = super.getChilds();
       if ((child == null) || (child.isEmpty()))
       {
         ++i;
       }
 
       HiItemHelper.setCurEtfLevel(mess, curEtfLevel + this.ETF_name + "_" + i + ".");
 
       HiItemHelper.setCurXmlRoot(mess, xmlNode);
       super.doProcess(messContext);
 
       ++i;
     }
     HiItemHelper.setCurEtfLevel(mess, curEtfLevel);
     HiItemHelper.setCurXmlRoot(mess, xmlParent);
 
     return -2;
   }
 
   public void loadCheck()
     throws HiException
   {
     if (StringUtils.isBlank(this.name))
     {
       throw new HiException("213184", this.name);
     }
 
     if (!(StringUtils.isBlank(this.ETF_name)))
       return;
     throw new HiException("213185", this.name);
   }
 
   private int getRepeat_num(HiMessage msg)
   {
     String strNum = HiItemHelper.getEtfItem(msg, this.repeat_name);
 
     return NumberUtils.toInt(strNum);
   }
 }