/*     */ package com.hisun.engine.invoke.load;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiBusCfgLoad extends HiEngineModel
/*     */ {
/*  33 */   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */ 
/*  46 */   private final String strFileName = "etc/BUSCFG.XML";
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  43 */     return "BUSCFG.XML";
/*     */   }
/*     */ 
/*     */   private void jbInit()
/*     */     throws HiException
/*     */   {
/*  50 */     if (this.logger.isDebugEnabled())
/*     */     {
/*  52 */       this.logger.debug("HiBusCfgLoad:jbInit() - start");
/*     */     }
/*     */ 
/*  59 */     SAXReader saxReader = new SAXReader();
/*  60 */     URL urlFilePath = HiResource.getResource("etc/BUSCFG.XML");
/*  61 */     if (urlFilePath == null)
/*     */     {
/*  63 */       throw new HiException("213313", "etc/BUSCFG.XML");
/*     */     }
/*     */ 
/*  66 */     HashMap map = new HashMap();
/*     */     try
/*     */     {
/*  69 */       Document document = saxReader.read(urlFilePath);
/*  70 */       Element rootNode = document.getRootElement();
/*  71 */       rootNode = rootNode.element("ParaList");
/*  72 */       Iterator iter = rootNode.elementIterator();
/*     */ 
/*  74 */       while (iter.hasNext())
/*     */       {
/*  76 */         Element em = (Element)iter.next();
/*  77 */         String strName = em.getName();
/*  78 */         String strValue = em.getStringValue();
/*  79 */         map.put(strName.toUpperCase(), strValue);
/*     */       }
/*     */     }
/*     */     catch (DocumentException e)
/*     */     {
/*  84 */       throw new HiException("213314", "etc/BUSCFG.XML", e);
/*     */     }
/*     */ 
/*  91 */     HiContext.getCurrentContext().setProperty("ROOT.BCFG", map);
/*     */ 
/* 100 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 102 */     this.logger.debug("HiBusCfgLoad:jbInit() - end");
/*     */   }
/*     */ 
/*     */   public static void loadBusCfg()
/*     */     throws HiException
/*     */   {
/* 108 */     HiBusCfgLoad bus = new HiBusCfgLoad();
/* 109 */     bus.jbInit();
/*     */   }
/*     */ }