/*     */ package com.hisun.atc.common;
/*     */ 
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.SystemUtils;
/*     */ 
/*     */ public class HiArgUtils
/*     */ {
/*  34 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public static String getString(HiETF root, String name) {
/*  37 */     String value = root.getChildValue(name);
/*  38 */     if (value != null) {
/*  39 */       return value.trim();
/*     */     }
/*     */ 
/*  42 */     return value;
/*     */   }
/*     */ 
/*     */   public static String getStringNull(HiETF root, String name)
/*     */   {
/*  52 */     return root.getChildValue(name);
/*     */   }
/*     */ 
/*     */   public static String getStringNotNull(HiATLParam args, String name) throws HiException
/*     */   {
/*  57 */     String value = args.get(name);
/*  58 */     if (StringUtils.isEmpty(value)) {
/*  59 */       throw new HiException("220026", name);
/*     */     }
/*  61 */     return value.trim();
/*     */   }
/*     */ 
/*     */   public static String getStringNotNull(HiATLParam args, String name, int retcode) throws HiException
/*     */   {
/*  66 */     String value = args.get(name.toUpperCase());
/*  67 */     if (StringUtils.isEmpty(value)) {
/*  68 */       throw new HiAppException(retcode, "220026", name);
/*     */     }
/*  70 */     return value.trim();
/*     */   }
/*     */ 
/*     */   public static String getStringNotNull(HiETF root, String name) throws HiException
/*     */   {
/*  75 */     String value = root.getChildValue(name);
/*  76 */     if (StringUtils.isEmpty(value)) {
/*  77 */       throw new HiException("220082", name);
/*     */     }
/*  79 */     return value.trim();
/*     */   }
/*     */ 
/*     */   public static String getStringNotNull(HiATLParam args, int argNum) throws HiException
/*     */   {
/*  84 */     if (args.size() < argNum) {
/*  85 */       throw new HiException("220026", "参数个数不足.");
/*     */     }
/*     */ 
/*  88 */     String value = null;
/*     */ 
/*  90 */     Iterator argIt = args.values().iterator();
/*     */ 
/*  92 */     for (int i = 0; i < argNum; ++i) {
/*  93 */       if (i == argNum - 1)
/*  94 */         value = (String)argIt.next();
/*     */       else {
/*  96 */         argIt.next();
/*     */       }
/*     */     }
/*  99 */     return value;
/*     */   }
/*     */ 
/*     */   public static String getString(HiMessageContext ctx, String name)
/*     */     throws HiException
/*     */   {
/* 123 */     return ctx.getStrProp(name);
/*     */   }
/*     */ 
/*     */   public static String getStringNotNull(HiMessageContext ctx, String name) throws HiException
/*     */   {
/* 128 */     String value = (String)ctx.getProperty(name);
/* 129 */     if (StringUtils.isEmpty(value)) {
/* 130 */       throw new HiException("220060", "临时区找不到指定的存贮域 " + name + ", 或不可为null.");
/*     */     }
/*     */ 
/* 133 */     return value;
/*     */   }
/*     */ 
/*     */   public static String getFirstNotNull(HiATLParam args) throws HiException {
/* 137 */     String value = (String)args.values().iterator().next();
/* 138 */     if (StringUtils.isEmpty(value)) {
/* 139 */       throw new HiException("220026", "参数值不可为空或为null");
/*     */     }
/* 141 */     return value;
/*     */   }
/*     */ 
/*     */   public static void judgeArgsEnough(HiATLParam args, int num, int retcode)
/*     */     throws HiException
/*     */   {
/* 165 */     if (args.size() <= num)
/* 166 */       throw new HiAppException(retcode, "220026", "参数个数不正确");
/*     */   }
/*     */ 
/*     */   public static void judgeArgsEnough(HiATLParam args, int num)
/*     */     throws HiException
/*     */   {
/* 173 */     if (args.size() <= num)
/* 174 */       throw new HiException("220026", "参数个数不正确");
/*     */   }
/*     */ 
/*     */   public static Map getEtfFields(HiETF etfBody, String fldLst, String deli)
/*     */   {
/* 190 */     String[] fields = StringUtils.split(fldLst, deli);
/* 191 */     Map etfFld = new HashMap();
/*     */ 
/* 193 */     for (int i = 0; i < fields.length; ++i)
/*     */     {
/* 195 */       etfFld.put(fields[i], etfBody.getChildValue(fields[i]));
/*     */     }
/*     */ 
/* 198 */     return etfFld;
/*     */   }
/*     */ 
/*     */   public static List getFldValues(HiETF etfBody, String fldLst, String deli)
/*     */     throws HiException
/*     */   {
/* 212 */     String[] fields = StringUtils.split(fldLst, deli);
/* 213 */     List etfFld = new ArrayList();
/*     */ 
/* 215 */     for (int i = 0; i < fields.length; ++i) {
/* 216 */       String Value = etfBody.getChildValue(fields[i]);
/* 217 */       if (Value != null)
/*     */       {
/* 219 */         etfFld.add(Value);
/*     */       }
/*     */       else throw new HiException("220044", "ETF树上无此节点" + fields[i]);
/*     */ 
/*     */     }
/*     */ 
/* 226 */     return etfFld;
/*     */   }
/*     */ 
/*     */   public static String getRealValue(String VarName, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 236 */     String realValue = VarName;
/*     */ 
/* 238 */     HiExpression exp = HiExpFactory.createExp(VarName);
/* 239 */     if (exp != null) {
/* 240 */       realValue = exp.getValue(ctx);
/* 241 */       if (realValue == null) {
/* 242 */         throw new HiException("", "参数值不正确：" + VarName);
/*     */       }
/*     */     }
/*     */ 
/* 246 */     return realValue;
/*     */   }
/*     */ 
/*     */   public static String absoutePath(String name) throws HiException
/*     */   {
/* 251 */     if (!(name.startsWith(SystemUtils.FILE_SEPARATOR))) {
/* 252 */       name = HiICSProperty.getWorkDir() + SystemUtils.FILE_SEPARATOR + name;
/*     */     }
/*     */ 
/* 255 */     return name;
/*     */   }
/*     */ }