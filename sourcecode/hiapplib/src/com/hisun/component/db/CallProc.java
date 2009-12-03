/*     */ package com.hisun.component.db;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class CallProc
/*     */ {
/*     */   private Logger log1;
/*     */ 
/*     */   public CallProc()
/*     */   {
/*  28 */     this.log1 = HiLog.getLogger("callproc.trc");
/*     */   }
/*     */ 
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  39 */     HiMessage msg = ctx.getCurrentMsg();
/*  40 */     Logger log = HiLog.getLogger(msg);
/*  41 */     String name = HiArgUtils.getStringNotNull(args, "NAME");
/*  42 */     String in = args.get("IN");
/*  43 */     String out = args.get("OUT");
/*  44 */     String[] inArgs = null;
/*  45 */     String[] outArgs = null;
/*  46 */     if (StringUtils.isNotBlank(in)) {
/*  47 */       inArgs = hsplit(in);
/*     */     }
/*  49 */     if (StringUtils.isNotBlank(out)) {
/*  50 */       outArgs = hsplit(out);
/*     */     }
/*     */ 
/*  53 */     if (log.isDebugEnabled()) {
/*  54 */       int tmp = (inArgs != null) ? inArgs.length : 0;
/*  55 */       log.debug(String.format("[%d]", new Object[] { Integer.valueOf(tmp) }));
/*  56 */       for (int i = 0; i < tmp; ++i) {
/*  57 */         log.debug(inArgs[i]);
/*     */       }
/*     */ 
/*  60 */       tmp = (outArgs != null) ? outArgs.length : 0;
/*     */ 
/*  62 */       log.debug(String.format("[%d]", new Object[] { Integer.valueOf(tmp) }));
/*  63 */       for (i = 0; i < tmp; ++i) {
/*  64 */         log.debug(outArgs[i]);
/*     */       }
/*     */     }
/*  67 */     HiDataBaseUtil db = ctx.getDataBaseUtil();
/*  68 */     Map outValue = db.call(name, inArgs);
/*  69 */     if (outValue == null) {
/*  70 */       return 0;
/*     */     }
/*  72 */     HiETF root = msg.getETFBody();
/*  73 */     this.log1.debug("HashMap2ETF: [" + outValue.size() + "]");
/*     */ 
/*  75 */     if (outArgs == null)
/*  76 */       HashMap2ETF(root, outValue);
/*     */     else {
/*  78 */       HashMap2ETF(root, outValue, outArgs);
/*     */     }
/*  80 */     return 0;
/*     */   }
/*     */ 
/*     */   public void HashMap2ETF(HiETF root, Map value) {
/*  84 */     if ((root == null) || (value == null)) {
/*  85 */       return;
/*     */     }
/*  87 */     Iterator iter = value.keySet().iterator();
/*  88 */     while (iter.hasNext()) {
/*  89 */       String tmp1 = (String)iter.next();
/*  90 */       Object tmp2 = value.get(tmp1);
/*  91 */       this.log1.debug("HashMap2ETF0: [" + tmp1 + "][" + tmp2 + "]");
/*  92 */       if (tmp2 instanceof ArrayList) {
/*  93 */         ArrayList list = (ArrayList)tmp2;
/*  94 */         root.setChildValue(tmp1 + "_NUM", String.valueOf(list.size()));
/*  95 */         for (int i = 0; i < list.size(); ++i) {
/*  96 */           HashMap2ETF(root.addNode(tmp1 + "_" + (i + 1)), (HashMap)list.get(i));
/*     */         }
/*     */ 
/*     */       }
/* 100 */       else if (tmp2 != null) {
/* 101 */         root.setChildValue(tmp1, tmp2.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void HashMap2ETF(HiETF root, Map value, String[] args)
/*     */   {
/* 108 */     if ((root == null) || (value == null)) {
/* 109 */       return;
/*     */     }
/* 111 */     Iterator iter = value.keySet().iterator();
/* 112 */     int idx = 0;
/* 113 */     while (iter.hasNext()) {
/* 114 */       String tmp1 = (String)iter.next();
/* 115 */       Object tmp2 = value.get(tmp1);
/* 116 */       this.log1.debug("HashMap2ETF1: [" + tmp1 + "][" + tmp2 + "]");
/* 117 */       if (idx < args.length) {
/* 118 */         tmp1 = args[idx];
/*     */       }
/* 120 */       ++idx;
/* 121 */       if (tmp2 instanceof ArrayList) {
/* 122 */         ArrayList list = (ArrayList)tmp2;
/* 123 */         root.setChildValue(tmp1 + "_NUM", String.valueOf(list.size()));
/* 124 */         for (int i = 0; i < list.size(); ++i) {
/* 125 */           HashMap2ETF(root.addNode(tmp1 + "_" + (i + 1)), (HashMap)list.get(i));
/*     */         }
/*     */ 
/*     */       }
/* 129 */       else if (tmp2 != null) {
/* 130 */         this.log1.debug("HashMap2ETF1: [" + tmp1 + "][" + tmp2 + "]");
/* 131 */         root.setChildValue(tmp1, tmp2.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 138 */     String[] args1 = hsplit("test|| | || | | | | | | | | || | | | | | || | | | | 3");
/* 139 */     for (int i = 0; i < args1.length; ++i)
/* 140 */       System.out.println("[" + args1[i] + "]");
/*     */   }
/*     */ 
/*     */   public static void testProc()
/*     */   {
/* 145 */     CallProc proc = new CallProc();
/* 146 */     HiETF root = HiETFFactory.createETF();
/* 147 */     HashMap map = new HashMap();
/* 148 */     ArrayList list = new ArrayList();
/* 149 */     map.put("hello01", "value01");
/* 150 */     map.put("hello02", "value02");
/* 151 */     map.put("hello03", "value03");
/* 152 */     map.put("LIST", list);
/* 153 */     for (int i = 0; i < 10; ++i) {
/* 154 */       HashMap map1 = new HashMap();
/* 155 */       map1.put("hello01", "value01");
/* 156 */       map1.put("hello02", "value02");
/* 157 */       map1.put("hello03", "value03");
/* 158 */       list.add(map1);
/*     */     }
/*     */ 
/* 161 */     proc.HashMap2ETF(root, map);
/* 162 */     System.out.println(root);
/*     */   }
/*     */ 
/*     */   public static String[] hsplit(String value)
/*     */   {
/* 171 */     if (value == null)
/* 172 */       return null;
/* 173 */     ArrayList list = new ArrayList();
/* 174 */     int idx1 = 0;
/* 175 */     int i = 0;
/* 176 */     for (i = 0; i < value.length(); ++i) {
/* 177 */       if (value.charAt(i) == '|') {
/* 178 */         if (idx1 == i)
/* 179 */           list.add(null);
/*     */         else {
/* 181 */           list.add(value.substring(idx1, i));
/*     */         }
/* 183 */         idx1 = i + 1;
/*     */       }
/*     */     }
/* 186 */     if (idx1 != i) {
/* 187 */       list.add(value.substring(idx1, i));
/*     */     }
/* 189 */     String[] args = new String[list.size()];
/* 190 */     for (int j = 0; j < list.size(); ++j) {
/* 191 */       args[j] = ((String)list.get(j));
/*     */     }
/* 193 */     return args;
/*     */   }
/*     */ }