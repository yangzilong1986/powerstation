/*     */ package com.hisun.parse;
/*     */ 
/*     */ import com.hisun.message.HiContext;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ import org.apache.commons.beanutils.DynaBean;
/*     */ import org.apache.commons.beanutils.DynaClass;
/*     */ import org.apache.commons.beanutils.DynaProperty;
/*     */ import org.apache.commons.beanutils.PropertyUtils;
/*     */ import org.apache.commons.digester.Digester;
/*     */ import org.apache.commons.digester.Rule;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.xml.sax.Attributes;
/*     */ 
/*     */ public class HiSetPropertyRule extends Rule
/*     */ {
/*     */   protected String name;
/*     */   protected String value;
/*     */ 
/*     */   /** @deprecated */
/*     */   public HiSetPropertyRule(Digester digester, String name, String value)
/*     */   {
/*  61 */     this(name, value);
/*     */   }
/*     */ 
/*     */   public HiSetPropertyRule(String name, String value)
/*     */   {
/*  88 */     this.name = null;
/*     */ 
/*  93 */     this.value = null;
/*     */ 
/*  78 */     this.name = name;
/*  79 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public void begin(Attributes attributes)
/*     */     throws Exception
/*     */   {
/* 110 */     String actualName = null;
/* 111 */     String actualValue = null;
/* 112 */     for (int i = 0; i < attributes.getLength(); ++i) {
/* 113 */       String name = attributes.getLocalName(i);
/* 114 */       if ("".equals(name)) {
/* 115 */         name = attributes.getQName(i);
/*     */       }
/* 117 */       String value = attributes.getValue(i);
/* 118 */       if (name.equals(this.name))
/* 119 */         actualName = value;
/* 120 */       else if (name.equals(this.value)) {
/* 121 */         actualValue = value;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 126 */     Object top = this.digester.peek();
/*     */ 
/* 129 */     if (this.digester.getLogger().isDebugEnabled()) {
/* 130 */       this.digester.getLogger().debug("[SetPropertyRule]{" + this.digester.getMatch() + "} Set " + top.getClass().getName() + " property " + actualName + " to " + actualValue);
/*     */     }
/*     */ 
/* 141 */     if (top instanceof DynaBean) {
/* 142 */       DynaProperty desc = ((DynaBean)top).getDynaClass().getDynaProperty(actualName);
/*     */ 
/* 144 */       if (desc == null)
/* 145 */         throw new NoSuchMethodException("Bean has no property named " + actualName);
/*     */     }
/*     */     else
/*     */     {
/* 149 */       PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(top, actualName);
/*     */ 
/* 151 */       if (desc == null) {
/* 152 */         throw new NoSuchMethodException("Bean has no property named " + actualName);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 158 */     BeanUtils.setProperty(top, actualName, getRealValue(actualValue));
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 167 */     StringBuffer sb = new StringBuffer("HiSetPropertyRule[");
/* 168 */     sb.append("name=");
/* 169 */     sb.append(this.name);
/* 170 */     sb.append(", value=");
/* 171 */     sb.append(this.value);
/* 172 */     sb.append("]");
/* 173 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private String getRealValue(String value)
/*     */   {
/* 178 */     if ((value != null) && (value.indexOf(95) == 0)) {
/* 179 */       return getParamValue(value);
/*     */     }
/* 181 */     return value;
/*     */   }
/*     */ 
/*     */   private String getParamValue(String value)
/*     */   {
/* 186 */     HiContext ctx = HiContext.getCurrentContext();
/* 187 */     if (ctx != null) {
/* 188 */       String newValue = ctx.getStrProp("@PARA", value);
/* 189 */       if (newValue != null)
/* 190 */         return newValue;
/*     */     }
/* 192 */     return value;
/*     */   }
/*     */ }