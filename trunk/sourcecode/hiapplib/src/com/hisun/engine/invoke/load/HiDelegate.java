/*    */ package com.hisun.engine.invoke.load;
/*    */ 
/*    */ import com.hisun.engine.invoke.HiIEngineModel;
/*    */ import com.hisun.engine.invoke.impl.HiAbstractApplication;
/*    */ import com.hisun.exception.HiException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.HashMap;
/*    */ import org.apache.commons.beanutils.MethodUtils;
/*    */ 
/*    */ public class HiDelegate extends HiAbstractApplication
/*    */ {
/* 27 */   private String strNodeName = null;
/*    */ 
/* 42 */   private HashMap childMaps = null;
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 31 */     if (this.strNodeName == null) {
/* 32 */       return super.getNodeName();
/*    */     }
/* 34 */     return this.strNodeName;
/*    */   }
/*    */ 
/*    */   public void setNodeName(String strNodeName)
/*    */   {
/* 39 */     this.strNodeName = strNodeName;
/*    */   }
/*    */ 
/*    */   public void addChilds(HiIEngineModel child)
/*    */     throws HiException
/*    */   {
/* 54 */     Method method = MethodUtils.getAccessibleMethod(child.getClass(), "getName", new Class[0]);
/*    */ 
/* 56 */     if (method != null)
/*    */     {
/* 58 */       if (this.childMaps == null) {
/* 59 */         this.childMaps = new HashMap();
/*    */       }
/*    */       try
/*    */       {
/* 63 */         Object name = method.invoke(child, new Object[0]);
/* 64 */         this.childMaps.put(name, child);
/*    */       }
/*    */       catch (Throwable t)
/*    */       {
/*    */       }
/*    */     }
/*    */ 
/* 71 */     super.addChilds(child);
/*    */   }
/*    */ 
/*    */   public HashMap getChildsMap()
/*    */   {
/* 76 */     return this.childMaps;
/*    */   }
/*    */ }