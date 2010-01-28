 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSysException;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import org.apache.commons.lang.StringUtils;
 
 public class HiReadAttr extends HiAbstractDoElement
 {
   public HiReadAttr()
     throws HiException
   {
     super.initAtt();
   }
 
   public String getNodeName()
   {
     return "ReadAttr";
   }
 
   public void process(HiMessageContext messContext) throws HiException
   {
     Logger log = HiLog.getLogger(messContext.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiReadAttr.process00", HiEngineUtilities.getCurFlowStep(), this.name, this.ETF_name));
     }
     try
     {
       doElement(messContext);
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
 
   public int doElement(HiMessageContext messContext) throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
     if (xmlContent == null)
     {
       throw new HiException("213179", this.name);
     }
     Logger log = HiLog.getLogger(mess);
 
     HiETF xmlParent = HiItemHelper.getCurXmlRoot(mess);
     if (xmlParent == null)
     {
       throw new HiException("213180", this.name);
     }
 
     String curXmlLevel = xmlParent.getName();
 
     String attrVal = xmlParent.getAttrValue(this.name, this.ns, this.ns_uri);
 
     if (attrVal == null)
     {
       if (!(this.necessary))
       {
         if (log.isInfoEnabled())
         {
           log.info("ReadAttr:从XML报文定位不到指定属性[" + this.name + "],necessary=no,本属性值不添加到ETF");
         }
         return -2;
       }
 
       throw new HiException("213181", curXmlLevel, this.name);
     }
 
     if (log.isInfoEnabled())
       log.info("ReadAttr: 从XML报文取得该节点[" + curXmlLevel + "] 属性名[" + this.name + "] 属性值[" + attrVal + "]");
     if (StringUtils.isBlank(this.ETF_name))
     {
       if (log.isInfoEnabled())
         log.info("ReadAttr: 没有配置ETF_name");
       return -2;
     }
 
     HiETF etfBody = mess.getETFBody();
 
     etfBody.setGrandChildNode(HiItemHelper.getCurEtfLevel(mess) + this.ETF_name, attrVal);
     if (log.isInfoEnabled()) {
       log.info("ReadAttr: 更新到ETF 域名[" + this.ETF_name + "] 值[" + attrVal + "]");
     }
 
     if (this.exp != null)
     {
       try
       {
         HiItemHelper.execExpression(mess, this.exp, this.ETF_name);
       }
       catch (Throwable te)
       {
         throw HiException.makeException("213182", this.name, this.exp.getExp(), te);
       }
     }
 
     return -2;
   }
 
   public void loadCheck()
     throws HiException
   {
     if (!(StringUtils.isBlank(this.name)))
       return;
     throw new HiException("213178", this.name);
   }
 }