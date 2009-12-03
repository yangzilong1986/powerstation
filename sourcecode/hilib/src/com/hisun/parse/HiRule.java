/*     */ package com.hisun.parse;
/*     */ 
/*     */ import org.apache.commons.beanutils.MethodUtils;
/*     */ import org.apache.commons.digester.Digester;
/*     */ import org.apache.commons.digester.Rule;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class HiRule extends Rule
/*     */ {
/*     */   protected String methodName;
/*     */   protected String paramType;
/*     */   protected boolean useExactMatch;
/*     */ 
/*     */   /** @deprecated */
/*     */   public HiRule(Digester digester, String methodName)
/*     */   {
/*  34 */     this(methodName);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public HiRule(Digester digester, String methodName, String paramType)
/*     */   {
/*  73 */     this(methodName, paramType);
/*     */   }
/*     */ 
/*     */   public HiRule(String methodName)
/*     */   {
/*  92 */     this(methodName, null);
/*     */   }
/*     */ 
/*     */   public HiRule(String methodName, String paramType)
/*     */   {
/* 133 */     this.methodName = null;
/*     */ 
/* 140 */     this.paramType = null;
/*     */ 
/* 146 */     this.useExactMatch = false;
/*     */ 
/* 121 */     this.methodName = methodName;
/* 122 */     this.paramType = paramType;
/*     */   }
/*     */ 
/*     */   public boolean isExactMatch()
/*     */   {
/* 195 */     return this.useExactMatch;
/*     */   }
/*     */ 
/*     */   public void setExactMatch(boolean useExactMatch)
/*     */   {
/* 216 */     this.useExactMatch = useExactMatch;
/*     */   }
/*     */ 
/*     */   public void end()
/*     */     throws Exception
/*     */   {
/* 227 */     Object child = this.digester.peek(0);
/* 228 */     Object parent = this.digester.peek(this.digester.getCount() - 1);
/*     */ 
/* 230 */     if (super.getDigester().getLogger().isDebugEnabled())
/*     */     {
/* 232 */       if (child == null)
/*     */       {
/* 234 */         this.digester.getLogger().debug("[HiRule]{" + this.digester.getMatch() + "} Call [NULL CHILD]." + this.methodName + "(" + parent + ")");
/*     */       }
/*     */       else
/*     */       {
/* 240 */         this.digester.getLogger().debug("[HiRule]{" + this.digester.getMatch() + "} Call " + child.getClass().getName() + "." + this.methodName + "(" + parent + ")");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 248 */     Class[] paramTypes = new Class[1];
/* 249 */     if (this.paramType != null)
/*     */     {
/* 251 */       Class paramClass = this.digester.getClassLoader().loadClass(this.paramType);
/* 252 */       paramTypes[0] = paramClass;
/*     */ 
/* 254 */       Class parentClass = parent.getClass();
/* 255 */       if (!(paramClass.getName().equals(parentClass.getName())))
/*     */       {
/* 257 */         for (int i = 1; i < this.digester.getCount(); ++i)
/*     */         {
/* 260 */           parent = this.digester.peek(i);
/* 261 */           if (paramClass.getName().equals(parent.getClass().getName())) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 269 */       paramTypes[0] = parent.getClass();
/*     */     }
/*     */ 
/* 272 */     if (this.useExactMatch)
/*     */     {
/* 275 */       MethodUtils.invokeExactMethod(child, this.methodName, new Object[] { parent }, paramTypes);
/*     */     }
/*     */     else
/*     */     {
/* 282 */       MethodUtils.invokeMethod(child, this.methodName, new Object[] { parent }, paramTypes);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 295 */     StringBuffer sb = new StringBuffer("HiRule[");
/* 296 */     sb.append("methodName=");
/* 297 */     sb.append(this.methodName);
/* 298 */     sb.append(", paramType=");
/* 299 */     sb.append(this.paramType);
/* 300 */     sb.append("]");
/* 301 */     return sb.toString();
/*     */   }
/*     */ }