/*     */ package com.hisun.parse;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiResourceRule
/*     */ {
/*  19 */   private static URL ruleCTLRul = null;
/*     */ 
/*  24 */   private static URL ruleITFRul = null;
/*     */ 
/*  26 */   private static HashMap rules = new HashMap();
/*     */ 
/*  82 */   private static Properties pro = null;
/*     */ 
/*     */   public static URL getCTLRule()
/*     */   {
/*  35 */     if (ruleCTLRul == null)
/*     */     {
/*  37 */       ruleCTLRul = HiResource.getResource("config/CTL_RULES.XML");
/*  38 */       rules.put("config/CTL_RULES.XML", ruleCTLRul);
/*     */     }
/*  40 */     return ruleCTLRul;
/*     */   }
/*     */ 
/*     */   public static URL getITFRule()
/*     */   {
/*  50 */     if (ruleITFRul == null)
/*     */     {
/*  52 */       ruleITFRul = HiResource.getResource("config/ITF_RULES.XML");
/*  53 */       rules.put("config/ITF_RULES.XML", ruleITFRul);
/*     */     }
/*  55 */     return ruleITFRul;
/*     */   }
/*     */ 
/*     */   public static URL getRule(String strRule)
/*     */   {
/*  60 */     URL rule = null;
/*  61 */     if (strRule == null)
/*  62 */       return null;
/*  63 */     if (rules.containsKey(strRule))
/*     */     {
/*  65 */       rule = (URL)rules.get(strRule);
/*     */     }
/*     */     else
/*     */     {
/*  69 */       rule = HiResource.getResource(strRule);
/*  70 */       if (rule == null)
/*  71 */         return null;
/*  72 */       rules.put(strRule, rule);
/*     */     }
/*  74 */     return rule;
/*     */   }
/*     */ 
/*     */   public static URL getRuleForFile(String strFileName) throws HiException
/*     */   {
/*  79 */     return getRule(getRuleFile(strFileName));
/*     */   }
/*     */ 
/*     */   private static Properties getProperties()
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  88 */       if (pro == null)
/*     */       {
/*  90 */         pro = new Properties();
/*     */ 
/*  93 */         InputStream in = HiResource.getResourceAsStream("config/rules.properties");
/*     */ 
/*  95 */         if (in == null) {
/*  96 */           throw new HiException("215028", "config/rules.properties");
/*     */         }
/*  98 */         pro.load(in);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 103 */       throw new HiException("215028", "config/rules.properties", e);
/*     */     }
/*     */ 
/* 106 */     return pro;
/*     */   }
/*     */ 
/*     */   public static HiCfgFile getCTLCfgFile(URL urlFilePath)
/*     */     throws HiException
/*     */   {
/* 112 */     HiCfgFile cfg = HiCfgFile.getDefaultCfgFile(urlFilePath, getCTLRule(), "Attribute");
/*     */ 
/* 114 */     return cfg;
/*     */   }
/*     */ 
/*     */   public static HiCfgFile getITFCfgFile(URL urlFilePath)
/*     */     throws HiException
/*     */   {
/* 120 */     HiCfgFile cfg = HiCfgFile.getDefaultCfgFile(urlFilePath, getITFRule(), null);
/*     */ 
/* 122 */     return cfg;
/*     */   }
/*     */ 
/*     */   private static String getRuleFile(String strFileName) throws HiException
/*     */   {
/* 127 */     Properties pro = getProperties();
/* 128 */     String key = StringUtils.substring(strFileName, -7);
/*     */ 
/* 130 */     String rule = pro.getProperty(key.toUpperCase());
/*     */ 
/* 132 */     return rule;
/*     */   }
/*     */ }