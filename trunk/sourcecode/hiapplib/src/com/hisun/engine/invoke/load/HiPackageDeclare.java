/*     */ package com.hisun.engine.invoke.load;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.invoke.impl.HiFunction;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.parse.HiCfgFile;
/*     */ import com.hisun.parse.HiResourceRule;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public class HiPackageDeclare extends HiEngineModel
/*     */ {
/*  32 */   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  42 */     return "PackageDeclare";
/*     */   }
/*     */ 
/*     */   public void setPackageInfo(String strPackageName, String strFileName)
/*     */     throws HiException
/*     */   {
/*  48 */     if (this.logger.isDebugEnabled())
/*     */     {
/*  50 */       this.logger.debug("setPackageInfo(String, String) - start");
/*     */     }
/*     */ 
/*  54 */     String strNodeName = "PACKAGEDECLARE";
/*     */ 
/*  68 */     HiEngineModel rootInstance = (HiEngineModel)loadFile(strFileName);
/*     */ 
/*  75 */     List childs = rootInstance.getChilds();
/*  76 */     HashMap childsMap = new HashMap();
/*  77 */     for (int i = 0; i < childs.size(); ++i)
/*     */     {
/*  79 */       HiFunction child = (HiFunction)childs.get(i);
/*  80 */       childsMap.put(child.getName(), child);
/*     */     }
/*     */ 
/*  85 */     HiContext.getCurrentContext().setProperty(strNodeName + "." + strPackageName.toUpperCase(), childsMap);
/*     */ 
/*  91 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/*  93 */     this.logger.debug("setPackageInfo(String, String) - end");
/*     */   }
/*     */ 
/*     */   public Object loadFile(String strFileName)
/*     */     throws HiException
/*     */   {
/* 105 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 107 */       this.logger.debug("loadFile(String) - start");
/*     */     }
/*     */ 
/* 110 */     URL u = HiResource.getResource(strFileName);
/* 111 */     if (u == null)
/*     */     {
/* 113 */       throw new HiException("213318", strFileName);
/*     */     }
/*     */ 
/* 116 */     Object returnObject = HiResourceRule.getCTLCfgFile(u).getRootInstance();
/* 117 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 119 */       this.logger.debug("loadFile(String) - end");
/*     */     }
/* 121 */     return returnObject;
/*     */   }
/*     */ }