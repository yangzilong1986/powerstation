 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
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
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public abstract class HiAbstractDoElement extends HiEngineModel
 {
   protected final Logger logger;
   protected String name;
   protected String ETF_name;
   protected boolean necessary;
   protected HiExpression exp;
   protected String value;
   protected String ns;
   protected String ns_uri;
   protected int repeat_num;
   protected String repeat_name;
   protected String xml_name;
   protected boolean ignoreCase;
   protected int xml_level;
 
   public HiAbstractDoElement()
   {
     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
 
     this.ETF_name = null;
 
     this.necessary = true;
 
     this.exp = null;
 
     this.value = null;
 
     this.ns = null;
 
     this.repeat_num = 0;
 
     this.repeat_name = null;
 
     this.xml_name = null;
 
     this.ignoreCase = false;
 
     this.xml_level = 0;
   }
 
   public void setETF_name(String etf_name) {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setETF_name(String) - start");
     }
 
     this.ETF_name = etf_name;
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setETF_name(String) - end");
   }
 
   public void setExpression(String expression)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setExpresion(String) - start");
     }
     this.exp = HiExpFactory.createExp(expression);
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setExpresion(String) - end");
   }
 
   public void setName(String name)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setName(String) - start");
     }
 
     this.name = name;
 
     if (name.startsWith("@ROOT"))
     {
       this.xml_name = StringUtils.substringAfter(name, ".");
       this.xml_level = 1;
     }
     else if (name.startsWith("@PARENT"))
     {
       this.xml_name = StringUtils.substringAfter(name, ".");
       this.xml_level = 2;
     }
     else if (name.startsWith("@XPATH")) {
       this.xml_name = StringUtils.substringAfter(name, "@XPATH");
       this.xml_level = 3;
     }
     else
     {
       this.xml_name = name;
       this.xml_level = 0;
     }
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setName(String) - end");
   }
 
   public void setValue(String value)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setValue(String) - start");
     }
 
     this.value = value;
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setValue(String) - end");
   }
 
   public void setNecessary(String necessary)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setNecessary(String) - start");
     }
 
     if (StringUtils.equalsIgnoreCase(necessary, "no"))
     {
       this.necessary = false;
     }
     else
     {
       this.necessary = true;
     }
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setNecessary(String) - end");
   }
 
   public void setNs(String ns)
     throws HiException
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setNs(String) - start");
     }
 
     this.ns = ns;
 
     if (ns != null)
     {
       this.ns_uri = HiContext.getCurrentContext().getStrProp("NSDECLARE." + ns);
 
       if (this.ns_uri == null)
       {
         throw new HiException("213171", ns);
       }
     }
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setNs(String) - end");
   }
 
   public void setRepeat_num(String repeat_num)
     throws HiException
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setRepeat_num(String) - start");
     }
 
     try
     {
       this.repeat_num = Integer.parseInt(repeat_num.trim());
     }
     catch (Exception e)
     {
       this.logger.error("setRepeate_num(String)", e);
 
       throw new HiException("213172", new String[] { this.name, repeat_num }, e);
     }
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setRepeat_num(String) - end");
   }
 
   public void setRepeat_name(String repeate_name)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setRepeate_name(String) - start");
     }
 
     this.repeat_name = repeate_name;
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setRepeate_name(String) - end");
   }
 
   public void setIgnoreCase(String ignoreCase)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setIgnoreCase(String) - start");
     }
 
     if (StringUtils.equalsIgnoreCase(ignoreCase, "yes"))
     {
       this.ignoreCase = true;
     }
     else
     {
       this.ignoreCase = false;
     }
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setIgnoreCase(String) - end:" + this.ignoreCase);
   }
 
   public void initAtt()
     throws HiException
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("initAtt() - start");
     }
     HiContext context = HiContext.getCurrentContext();
     HiITFEngineModel itemAttribute = HiITFEngineModel.getItemAttribute(context);
 
     if (itemAttribute != null) {
       this.necessary = itemAttribute.is_necessary();
       this.ignoreCase = itemAttribute.is_ignoreCase();
     }
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("->initAtt() - end ");
   }
 
   public void process(HiMessageContext messContext) throws HiException
   {
     Logger log = HiLog.getLogger(messContext.getCurrentMsg());
     if (log.isDebugEnabled())
     {
       log.debug("process(HiMessageContext) - start");
     }
 
     HiMessage mess = messContext.getCurrentMsg();
     try
     {
       if (doElement(messContext) >= 0)
       {
         List child = super.getChilds();
         if ((child == null) || (child.isEmpty()))
         {
           if (log.isDebugEnabled())
           {
             log.debug("process(HiMessageContext) - end");
           }
           return;
         }
 
         HiETF curRoot = HiItemHelper.getCurXmlRoot(mess);
         HiItemHelper.setCurXmlRoot(mess, (HiETF)messContext.getBaseSource("XML_ROOT"));
 
         super.process(messContext);
 
         HiItemHelper.setCurXmlRoot(mess, curRoot);
       }
     }
     catch (HiException e)
     {
       throw e;
     }
     catch (Throwable te)
     {
       throw new HiSysException("213170", this.name, te);
     }
 
     if (!(log.isDebugEnabled()))
       return;
     log.debug("process(HiMessageContext) - end");
   }
 
   protected void doProcess(HiMessageContext messContext)
     throws HiException
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("doProcess(HiMessageContext) - start");
     }
 
     super.process(messContext);
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("doProcess(HiMessageContext) - end");
   }
 
   public String toString()
   {
     return super.toString() + ":name[" + this.name + "]";
   }
 
   public void loadAfter() throws HiException
   {
     loadCheck();
 
     if (this.ignoreCase)
     {
       this.xml_name = this.xml_name.toUpperCase();
     }
 
     HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiXmlStategy");
   }
 
   protected abstract int doElement(HiMessageContext paramHiMessageContext)
     throws HiException;
 
   protected abstract void loadCheck()
     throws HiException;
 }