 package com.hisun.engine.invoke.impl;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public class HiWriteGroupElement extends HiAbstractDoElement
 {
   public HiWriteGroupElement()
     throws HiException
   {
     super.initAtt();
   }
 
   public String getNodeName()
   {
     return "WriteGroupElement";
   }
 
   protected int doElement(HiMessageContext messContext) throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
     if (xmlContent == null)
     {
       throw new HiException("213197", this.name);
     }
 
     Logger log = HiLog.getLogger(mess);
 
     List childNodes = new ArrayList();
 
     HiETF etfBody = mess.getETFBody();
     for (int i = 1; ; ++i) {
       String groupName = this.ETF_name + "_" + i;
 
       HiETF tmpEtf = HiItemHelper.getEtfItemNode(mess, groupName);
       if (tmpEtf == null) {
         break;
       }
       childNodes.add(tmpEtf);
     }
 
     if ((childNodes == null) || (childNodes.isEmpty()))
     {
       if (!(this.necessary))
       {
         if (log.isInfoEnabled())
         {
           log.info("WriteGroupElement:不存在指定ETF[" + this.ETF_name + "],necessary=no,本节点不构造");
         }
         return -2;
       }
       throw new HiException("213203", this.name, this.ETF_name);
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
         throw new HiException("213204", this.name, String.valueOf(repeatNum));
       }
 
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
         throw new HiException("213205", this.name);
       }
       xmlParent = xmlGrandParent;
     }
 
     if (xmlParent == null)
     {
       xmlParent = xmlContent;
     }
     String curEtfLevel = HiItemHelper.getCurEtfLevel(mess);
 
     for (Iterator iter = childNodes.iterator(); iter.hasNext(); )
     {
       HiETF recNode = (HiETF)iter.next();
 
       HiETF xmlNode = xmlParent.addNodeBase(this.xml_name, recNode.getValue(), this.ns, this.ns_uri);
 
       if (log.isInfoEnabled()) {
         log.info("WriteGroupElement:构造XML节点[" + this.ETF_name + "]");
       }
       List child = super.getChilds();
       if (child == null) continue; if (child.isEmpty())
       {
         continue;
       }
 
       HiItemHelper.setCurEtfLevel(mess, curEtfLevel + recNode.getName() + ".");
 
       HiItemHelper.setCurXmlRoot(mess, xmlNode);
       super.doProcess(messContext);
     }
 
     HiItemHelper.setCurEtfLevel(mess, curEtfLevel);
     HiItemHelper.setCurXmlRoot(mess, xmlParent);
 
     return -2;
   }
 
   private int getRepeat_num(HiMessage msg)
   {
     String strNum = HiItemHelper.getEtfItem(msg, this.repeat_name);
     try
     {
       return Integer.parseInt(strNum.trim());
     }
     catch (NumberFormatException e)
     {
     }
 
     return 0;
   }
 
   public void loadCheck()
     throws HiException
   {
     if (StringUtils.isBlank(this.name))
     {
       throw new HiException("213201", this.name);
     }
 
     if (!(StringUtils.isBlank(this.ETF_name)))
       return;
     throw new HiException("213202", this.name);
   }
 }