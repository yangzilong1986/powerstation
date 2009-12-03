/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiITFEngineModel;
/*     */ import com.hisun.engine.invoke.HiStrategyFactory;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSysException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public abstract class HiAbstractDoElement extends HiEngineModel
/*     */ {
/*     */   protected final Logger logger;
/*     */   protected String name;
/*     */   protected String ETF_name;
/*     */   protected boolean necessary;
/*     */   protected HiExpression exp;
/*     */   protected String value;
/*     */   protected String ns;
/*     */   protected String ns_uri;
/*     */   protected int repeat_num;
/*     */   protected String repeat_name;
/*     */   protected String xml_name;
/*     */   protected boolean ignoreCase;
/*     */   protected int xml_level;
/*     */ 
/*     */   public HiAbstractDoElement()
/*     */   {
/*  36 */     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */ 
/*  43 */     this.ETF_name = null;
/*     */ 
/*  45 */     this.necessary = true;
/*     */ 
/*  47 */     this.exp = null;
/*     */ 
/*  49 */     this.value = null;
/*     */ 
/*  51 */     this.ns = null;
/*     */ 
/*  55 */     this.repeat_num = 0;
/*     */ 
/*  57 */     this.repeat_name = null;
/*     */ 
/*  59 */     this.xml_name = null;
/*     */ 
/*  61 */     this.ignoreCase = false;
/*     */ 
/*  64 */     this.xml_level = 0;
/*     */   }
/*     */ 
/*     */   public void setETF_name(String etf_name) {
/*  68 */     if (this.logger.isDebugEnabled())
/*     */     {
/*  70 */       this.logger.debug("setETF_name(String) - start");
/*     */     }
/*     */ 
/*  73 */     this.ETF_name = etf_name;
/*     */ 
/*  75 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/*  77 */     this.logger.debug("setETF_name(String) - end");
/*     */   }
/*     */ 
/*     */   public void setExpression(String expression)
/*     */   {
/*  82 */     if (this.logger.isDebugEnabled())
/*     */     {
/*  84 */       this.logger.debug("setExpresion(String) - start");
/*     */     }
/*  86 */     this.exp = HiExpFactory.createExp(expression);
/*     */ 
/*  88 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/*  90 */     this.logger.debug("setExpresion(String) - end");
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  96 */     if (this.logger.isDebugEnabled())
/*     */     {
/*  98 */       this.logger.debug("setName(String) - start");
/*     */     }
/*     */ 
/* 101 */     this.name = name;
/*     */ 
/* 103 */     if (name.startsWith("@ROOT"))
/*     */     {
/* 105 */       this.xml_name = StringUtils.substringAfter(name, ".");
/* 106 */       this.xml_level = 1;
/*     */     }
/* 108 */     else if (name.startsWith("@PARENT"))
/*     */     {
/* 110 */       this.xml_name = StringUtils.substringAfter(name, ".");
/* 111 */       this.xml_level = 2;
/*     */     }
/* 113 */     else if (name.startsWith("@XPATH")) {
/* 114 */       this.xml_name = StringUtils.substringAfter(name, "@XPATH");
/* 115 */       this.xml_level = 3;
/*     */     }
/*     */     else
/*     */     {
/* 119 */       this.xml_name = name;
/* 120 */       this.xml_level = 0;
/*     */     }
/*     */ 
/* 123 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 125 */     this.logger.debug("setName(String) - end");
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/* 131 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 133 */       this.logger.debug("setValue(String) - start");
/*     */     }
/*     */ 
/* 136 */     this.value = value;
/*     */ 
/* 138 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 140 */     this.logger.debug("setValue(String) - end");
/*     */   }
/*     */ 
/*     */   public void setNecessary(String necessary)
/*     */   {
/* 146 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 148 */       this.logger.debug("setNecessary(String) - start");
/*     */     }
/*     */ 
/* 151 */     if (StringUtils.equalsIgnoreCase(necessary, "no"))
/*     */     {
/* 153 */       this.necessary = false;
/*     */     }
/*     */     else
/*     */     {
/* 157 */       this.necessary = true;
/*     */     }
/*     */ 
/* 160 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 162 */     this.logger.debug("setNecessary(String) - end");
/*     */   }
/*     */ 
/*     */   public void setNs(String ns)
/*     */     throws HiException
/*     */   {
/* 168 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 170 */       this.logger.debug("setNs(String) - start");
/*     */     }
/*     */ 
/* 173 */     this.ns = ns;
/*     */ 
/* 176 */     if (ns != null)
/*     */     {
/* 178 */       this.ns_uri = HiContext.getCurrentContext().getStrProp("NSDECLARE." + ns);
/*     */ 
/* 180 */       if (this.ns_uri == null)
/*     */       {
/* 182 */         throw new HiException("213171", ns);
/*     */       }
/*     */     }
/*     */ 
/* 186 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 188 */     this.logger.debug("setNs(String) - end");
/*     */   }
/*     */ 
/*     */   public void setRepeat_num(String repeat_num)
/*     */     throws HiException
/*     */   {
/* 194 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 196 */       this.logger.debug("setRepeat_num(String) - start");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 201 */       this.repeat_num = Integer.parseInt(repeat_num.trim());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 205 */       this.logger.error("setRepeate_num(String)", e);
/*     */ 
/* 208 */       throw new HiException("213172", new String[] { this.name, repeat_num }, e);
/*     */     }
/*     */ 
/* 213 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 215 */     this.logger.debug("setRepeat_num(String) - end");
/*     */   }
/*     */ 
/*     */   public void setRepeat_name(String repeate_name)
/*     */   {
/* 221 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 223 */       this.logger.debug("setRepeate_name(String) - start");
/*     */     }
/*     */ 
/* 226 */     this.repeat_name = repeate_name;
/*     */ 
/* 228 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 230 */     this.logger.debug("setRepeate_name(String) - end");
/*     */   }
/*     */ 
/*     */   public void setIgnoreCase(String ignoreCase)
/*     */   {
/* 236 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 238 */       this.logger.debug("setIgnoreCase(String) - start");
/*     */     }
/*     */ 
/* 241 */     if (StringUtils.equalsIgnoreCase(ignoreCase, "yes"))
/*     */     {
/* 243 */       this.ignoreCase = true;
/*     */     }
/*     */     else
/*     */     {
/* 247 */       this.ignoreCase = false;
/*     */     }
/*     */ 
/* 250 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 252 */     this.logger.debug("setIgnoreCase(String) - end:" + this.ignoreCase);
/*     */   }
/*     */ 
/*     */   public void initAtt()
/*     */     throws HiException
/*     */   {
/* 263 */     if (this.logger.isDebugEnabled()) {
/* 264 */       this.logger.debug("initAtt() - start");
/*     */     }
/* 266 */     HiContext context = HiContext.getCurrentContext();
/* 267 */     HiITFEngineModel itemAttribute = HiITFEngineModel.getItemAttribute(context);
/*     */ 
/* 270 */     if (itemAttribute != null) {
/* 271 */       this.necessary = itemAttribute.is_necessary();
/* 272 */       this.ignoreCase = itemAttribute.is_ignoreCase();
/*     */     }
/*     */ 
/* 275 */     if (this.logger.isDebugEnabled())
/* 276 */       this.logger.debug("->initAtt() - end ");
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext messContext) throws HiException
/*     */   {
/* 281 */     Logger log = HiLog.getLogger(messContext.getCurrentMsg());
/* 282 */     if (log.isDebugEnabled())
/*     */     {
/* 284 */       log.debug("process(HiMessageContext) - start");
/*     */     }
/*     */ 
/* 288 */     HiMessage mess = messContext.getCurrentMsg();
/*     */     try
/*     */     {
/* 291 */       if (doElement(messContext) >= 0)
/*     */       {
/* 293 */         List child = super.getChilds();
/* 294 */         if ((child == null) || (child.isEmpty()))
/*     */         {
/* 296 */           if (log.isDebugEnabled())
/*     */           {
/* 298 */             log.debug("process(HiMessageContext) - end");
/*     */           }
/* 300 */           return;
/*     */         }
/*     */ 
/* 314 */         HiETF curRoot = HiItemHelper.getCurXmlRoot(mess);
/* 315 */         HiItemHelper.setCurXmlRoot(mess, (HiETF)messContext.getBaseSource("XML_ROOT"));
/*     */ 
/* 317 */         super.process(messContext);
/*     */ 
/* 319 */         HiItemHelper.setCurXmlRoot(mess, curRoot);
/*     */       }
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/* 324 */       throw e;
/*     */     }
/*     */     catch (Throwable te)
/*     */     {
/* 328 */       throw new HiSysException("213170", this.name, te);
/*     */     }
/*     */ 
/* 332 */     if (!(log.isDebugEnabled()))
/*     */       return;
/* 334 */     log.debug("process(HiMessageContext) - end");
/*     */   }
/*     */ 
/*     */   protected void doProcess(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/* 341 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 343 */       this.logger.debug("doProcess(HiMessageContext) - start");
/*     */     }
/*     */ 
/* 346 */     super.process(messContext);
/*     */ 
/* 348 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 350 */     this.logger.debug("doProcess(HiMessageContext) - end");
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 357 */     return super.toString() + ":name[" + this.name + "]";
/*     */   }
/*     */ 
/*     */   public void loadAfter() throws HiException
/*     */   {
/* 362 */     loadCheck();
/*     */ 
/* 364 */     if (this.ignoreCase)
/*     */     {
/* 366 */       this.xml_name = this.xml_name.toUpperCase();
/*     */     }
/*     */ 
/* 377 */     HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiXmlStategy");
/*     */   }
/*     */ 
/*     */   protected abstract int doElement(HiMessageContext paramHiMessageContext)
/*     */     throws HiException;
/*     */ 
/*     */   protected abstract void loadCheck()
/*     */     throws HiException;
/*     */ }