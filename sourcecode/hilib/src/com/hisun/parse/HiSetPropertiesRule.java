/*     */ package com.hisun.parse;
/*     */ 
/*     */ import com.hisun.message.HiContext;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ import org.apache.commons.beanutils.PropertyUtils;
/*     */ import org.apache.commons.digester.Digester;
/*     */ import org.apache.commons.digester.Rule;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.xml.sax.Attributes;
/*     */ 
/*     */ public class HiSetPropertiesRule extends Rule
/*     */ {
/*     */   private String[] attributeNames;
/*     */   private String[] propertyNames;
/*     */   private boolean ignoreMissingProperty;
/*     */ 
/*     */   /** @deprecated */
/*     */   public HiSetPropertiesRule(Digester digester)
/*     */   {
/*     */   }
/*     */ 
/*     */   public HiSetPropertiesRule()
/*     */   {
/* 156 */     this.ignoreMissingProperty = true; } 
/*     */   public HiSetPropertiesRule(String attributeName, String propertyName) { this.ignoreMissingProperty = true;
/*     */ 
/*  85 */     this.attributeNames = new String[1];
/*  86 */     this.attributeNames[0] = attributeName;
/*  87 */     this.propertyNames = new String[1];
/*  88 */     this.propertyNames[0] = propertyName;
/*     */   }
/*     */ 
/*     */   public HiSetPropertiesRule(String[] attributeNames, String[] propertyNames)
/*     */   {
/* 156 */     this.ignoreMissingProperty = true;
/*     */ 
/* 130 */     this.attributeNames = new String[attributeNames.length];
/* 131 */     int i = 0; for (int size = attributeNames.length; i < size; ++i) {
/* 132 */       this.attributeNames[i] = attributeNames[i];
/*     */     }
/*     */ 
/* 135 */     this.propertyNames = new String[propertyNames.length];
/* 136 */     i = 0; for (size = propertyNames.length; i < size; ++i)
/* 137 */       this.propertyNames[i] = propertyNames[i];
/*     */   }
/*     */ 
/*     */   public void begin(Attributes attributes)
/*     */     throws Exception
/*     */   {
/* 170 */     HashMap values = new HashMap();
/*     */ 
/* 173 */     int attNamesLength = 0;
/* 174 */     if (this.attributeNames != null) {
/* 175 */       attNamesLength = this.attributeNames.length;
/*     */     }
/* 177 */     int propNamesLength = 0;
/* 178 */     if (this.propertyNames != null) {
/* 179 */       propNamesLength = this.propertyNames.length;
/*     */     }
/*     */ 
/* 183 */     for (int i = 0; i < attributes.getLength(); ++i) {
/* 184 */       String name = attributes.getLocalName(i);
/* 185 */       if ("".equals(name)) {
/* 186 */         name = attributes.getQName(i);
/*     */       }
/* 188 */       String value = attributes.getValue(i);
/*     */ 
/* 191 */       for (int n = 0; n < attNamesLength; ++n) {
/* 192 */         if (name.equals(this.attributeNames[n])) {
/* 193 */           if (n < propNamesLength)
/*     */           {
/* 195 */             name = this.propertyNames[n]; break;
/*     */           }
/*     */ 
/* 200 */           name = null;
/*     */ 
/* 202 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 206 */       if (this.digester.getLogger().isDebugEnabled()) {
/* 207 */         this.digester.getLogger().debug("[SetPropertiesRule]{" + this.digester.getMatch() + "} Setting property '" + name + "' to '" + value + "'");
/*     */       }
/*     */ 
/* 212 */       if ((!(this.ignoreMissingProperty)) && (name != null))
/*     */       {
/* 232 */         Object top = this.digester.peek();
/* 233 */         boolean test = PropertyUtils.isWriteable(top, name);
/* 234 */         if (!(test)) {
/* 235 */           throw new NoSuchMethodException("Property " + name + " can't be set");
/*     */         }
/*     */       }
/* 238 */       if (name != null) {
/* 239 */         values.put(name, getRealValue(value));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 244 */     Object top = this.digester.peek();
/* 245 */     if (this.digester.getLogger().isDebugEnabled()) {
/* 246 */       if (top != null) {
/* 247 */         this.digester.getLogger().debug("[SetPropertiesRule]{" + this.digester.getMatch() + "} Set " + top.getClass().getName() + " properties");
/*     */       }
/*     */       else
/*     */       {
/* 251 */         this.digester.getLogger().debug("[SetPropertiesRule]{" + this.digester.getMatch() + "} Set NULL properties");
/*     */       }
/*     */     }
/*     */ 
/* 255 */     BeanUtils.populate(top, values);
/*     */   }
/*     */ 
/*     */   public void addAlias(String attributeName, String propertyName)
/*     */   {
/* 270 */     if (this.attributeNames == null)
/*     */     {
/* 272 */       this.attributeNames = new String[1];
/* 273 */       this.attributeNames[0] = attributeName;
/* 274 */       this.propertyNames = new String[1];
/* 275 */       this.propertyNames[0] = propertyName;
/*     */     }
/*     */     else {
/* 278 */       int length = this.attributeNames.length;
/* 279 */       String[] tempAttributes = new String[length + 1];
/* 280 */       for (int i = 0; i < length; ++i) {
/* 281 */         tempAttributes[i] = this.attributeNames[i];
/*     */       }
/* 283 */       tempAttributes[length] = attributeName;
/*     */ 
/* 285 */       String[] tempProperties = new String[length + 1];
/* 286 */       for (int i = 0; (i < length) && (i < this.propertyNames.length); ++i) {
/* 287 */         tempProperties[i] = this.propertyNames[i];
/*     */       }
/* 289 */       tempProperties[length] = propertyName;
/*     */ 
/* 291 */       this.propertyNames = tempProperties;
/* 292 */       this.attributeNames = tempAttributes;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 302 */     StringBuffer sb = new StringBuffer("SetPropertiesRule[");
/* 303 */     sb.append("]");
/* 304 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public boolean isIgnoreMissingProperty()
/*     */   {
/* 318 */     return this.ignoreMissingProperty;
/*     */   }
/*     */ 
/*     */   public void setIgnoreMissingProperty(boolean ignoreMissingProperty)
/*     */   {
/* 331 */     this.ignoreMissingProperty = ignoreMissingProperty;
/*     */   }
/*     */ 
/*     */   private String getRealValue(String value) {
/* 335 */     if ((value != null) && (value.indexOf(95) == 0)) {
/* 336 */       return getParamValue(value);
/*     */     }
/* 338 */     return value;
/*     */   }
/*     */ 
/*     */   private String getParamValue(String value)
/*     */   {
/* 343 */     HiContext ctx = HiContext.getCurrentContext();
/* 344 */     if (ctx != null) {
/* 345 */       String newValue = ctx.getStrProp("@PARA", value);
/* 346 */       if (newValue != null)
/* 347 */         return newValue;
/*     */     }
/* 349 */     return value;
/*     */   }
/*     */ }