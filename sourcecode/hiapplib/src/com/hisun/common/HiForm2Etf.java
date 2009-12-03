/*     */ package com.hisun.common;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.web.filter.HiDataConvert;
/*     */ import java.util.Enumeration;
/*     */ import javax.servlet.ServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiForm2Etf
/*     */ {
/*     */   private HiDataConvert _dataConvert;
/*  23 */   private Logger log = HiLog.getLogger("form2etf.trc");
/*  24 */   private boolean ignoreCase = true;
/*     */ 
/*     */   public void setIgnoreCase(boolean ignoreCase) { this.ignoreCase = ignoreCase;
/*     */   }
/*     */ 
/*     */   public HiForm2Etf(HiDataConvert dataConvert) {
/*  30 */     this._dataConvert = dataConvert;
/*     */   }
/*     */ 
/*     */   public HiETF process(ServletRequest request, HiETF etf) throws Exception {
/*  34 */     Enumeration en = request.getParameterNames();
/*  35 */     int idx1 = 0;
/*     */ 
/*  37 */     while (en.hasMoreElements()) {
/*  38 */       String name = (String)en.nextElement();
/*  39 */       String[] values = request.getParameterValues(name);
/*  40 */       for (int i = 0; i < values.length; ++i) {
/*  41 */         this.log.info("[" + name + "]:[" + values[i] + "]");
/*     */       }
/*     */ 
/*  44 */       String groupName = "GROUP";
/*  45 */       String varName = name;
/*  46 */       idx1 = name.indexOf(46);
/*     */ 
/*  48 */       if (values.length == 1) {
/*  49 */         if (StringUtils.isBlank(values[0]))
/*     */           continue;
/*  51 */         if (idx1 == -1) {
/*  52 */           setValue(etf, name, values[0]);
/*     */         }
/*  54 */         groupName = name.substring(0, idx1);
/*  55 */         varName = name.substring(idx1 + 1);
/*  56 */         HiETF group = null;
/*  57 */         if (!(this.ignoreCase))
/*  58 */           group = etf.addNodeBase(groupName + "_1", "");
/*     */         else {
/*  60 */           group = etf.addNode(groupName + "_1");
/*     */         }
/*     */ 
/*  63 */         setValue(group, varName, values[0]);
/*     */ 
/*  65 */         if (!(this.ignoreCase))
/*  66 */           etf.setChildValueBase(groupName + "_NUM", "1");
/*     */         else {
/*  68 */           etf.setChildValue(groupName + "_NUM", "1");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  74 */       if (idx1 == -1)
/*     */       {
/*  76 */         setValue(etf, name, values[0]);
/*     */       }
/*     */ 
/*  80 */       if (idx1 != -1) {
/*  81 */         groupName = name.substring(0, idx1);
/*  82 */         varName = name.substring(idx1 + 1);
/*     */       }
/*  84 */       int count = 0;
/*  85 */       for (int i = 0; i < values.length; ++i) {
/*  86 */         if (StringUtils.isBlank(values[i]))
/*     */           continue;
/*  88 */         String tmp = groupName + "_" + (count + 1);
/*  89 */         HiETF group = null;
/*  90 */         if (!(this.ignoreCase))
/*  91 */           group = etf.getChildNodeBase(tmp);
/*     */         else {
/*  93 */           group = etf.getChildNode(tmp);
/*     */         }
/*  95 */         if (group == null) {
/*  96 */           if (!(this.ignoreCase))
/*  97 */             group = etf.addNodeBase(tmp, "");
/*     */           else {
/*  99 */             group = etf.addNode(tmp);
/*     */           }
/*     */         }
/* 102 */         setValue(group, varName, values[i]);
/* 103 */         ++count;
/*     */       }
/*     */ 
/* 106 */       if (count != 0) {
/* 107 */         if (!(this.ignoreCase))
/* 108 */           etf.setChildValueBase(groupName + "_NUM", String.valueOf(count));
/*     */         else {
/* 110 */           etf.setChildValue(groupName + "_NUM", String.valueOf(count));
/*     */         }
/*     */       }
/*     */     }
/* 114 */     return etf;
/*     */   }
/*     */ 
/*     */   private void setValue(HiETF node, String name, String value) throws Exception
/*     */   {
/* 119 */     if (StringUtils.isNotBlank(value)) {
/* 120 */       int idx = name.lastIndexOf("__");
/* 121 */       if (idx != -1) {
/* 122 */         value = this._dataConvert.convert(name.substring(idx + 2), value);
/* 123 */         name = name.substring(0, idx);
/*     */       }
/* 125 */       this.log.info("ignoreCase:[" + this.ignoreCase + "]" + name + ":" + value);
/* 126 */       if (!(this.ignoreCase))
/* 127 */         node.setChildValueBase(name, value);
/*     */       else
/* 129 */         node.setChildValue(name, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setValue01(HiETF node, String name, String value)
/*     */     throws Exception
/*     */   {
/* 136 */     if (value.indexOf(38) == -1) {
/* 137 */       if (StringUtils.isNotBlank(value)) {
/* 138 */         int idx = name.lastIndexOf("__");
/* 139 */         if (idx != -1) {
/* 140 */           value = this._dataConvert.convert(name.substring(idx + 2), value);
/*     */ 
/* 142 */           name = name.substring(0, idx);
/*     */         }
/* 144 */         if (!(this.ignoreCase))
/* 145 */           node.setChildValueBase(name, value);
/*     */         else {
/* 147 */           node.setChildValue(name, value);
/*     */         }
/*     */       }
/* 150 */       return;
/*     */     }
/*     */ 
/* 153 */     String[] nameValuePairs = value.split("&");
/* 154 */     int count = 0;
/* 155 */     for (int j = 0; j < nameValuePairs.length; ++j) {
/* 156 */       String tmp = name + "_" + (j + 1);
/* 157 */       HiETF group = null;
/* 158 */       if (!(this.ignoreCase))
/* 159 */         group = node.getChildNodeBase(tmp);
/*     */       else {
/* 161 */         group = node.getChildNode(tmp);
/*     */       }
/*     */ 
/* 164 */       if (group == null) {
/* 165 */         if (!(this.ignoreCase))
/* 166 */           group = node.addNodeBase(tmp, "");
/*     */         else {
/* 168 */           group = node.addNode(tmp);
/*     */         }
/*     */       }
/*     */ 
/* 172 */       String[] nameValuesPair = nameValuePairs[j].split("=");
/* 173 */       if (nameValuesPair.length == 2) {
/* 174 */         int idx = name.lastIndexOf("__");
/* 175 */         if (idx != -1) {
/* 176 */           tmp = this._dataConvert.convert(name.substring(idx + 2), value);
/* 177 */           name = name.substring(0, idx);
/*     */         }
/* 179 */         if (!(this.ignoreCase))
/* 180 */           group.setChildValueBase(nameValuesPair[0], tmp);
/*     */         else {
/* 182 */           group.setChildValue(nameValuesPair[0], tmp);
/*     */         }
/* 184 */         ++count;
/*     */       }
/*     */     }
/*     */ 
/* 188 */     if (count != 0)
/* 189 */       if (!(this.ignoreCase))
/* 190 */         node.setChildValueBase(name + "_NUM", String.valueOf(count));
/*     */       else
/* 192 */         node.setChildValue(name + "_NUM", String.valueOf(count));
/*     */   }
/*     */ }