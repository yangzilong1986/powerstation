/*    */ package com.hisun.hiexpression.imp;
/*    */ 
/*    */ import com.hisun.hiexpression.HiExpUtil;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ public class ASTStaticMethod extends SimpleNode
/*    */ {
/*    */   public String className;
/*    */   public String methodName;
/*    */   public Method method;
/*    */ 
/*    */   public ASTStaticMethod(int id)
/*    */   {
/* 17 */     super(id);
/*    */   }
/*    */ 
/*    */   public ASTStaticMethod(ICSExpParser p, int id) {
/* 21 */     super(p, id);
/*    */   }
/*    */ 
/*    */   void init(String className, String methodName)
/*    */     throws ParseException
/*    */   {
/* 28 */     this.className = className;
/* 29 */     this.methodName = methodName;
/*    */     try
/*    */     {
/* 32 */       this.method = HiExpUtil.getStaticMethod(className, methodName, (this.children == null) ? 0 : this.children.length);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 36 */       throw new ParseException("找不到函数:" + methodName);
/*    */     }
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 41 */     return super.toString() + ",class:" + this.className + ",method:" + this.methodName;
/*    */   }
/*    */ 
/*    */   public Object jjtAccept(ICSExpParserVisitor visitor, Object data)
/*    */     throws Exception
/*    */   {
/* 48 */     return visitor.visit(this, data);
/*    */   }
/*    */ 
/*    */   public Object childrenAccept(ICSExpParserVisitor visitor, Object data)
/*    */     throws Exception
/*    */   {
/* 54 */     Object[] ret = null;
/*    */ 
/* 56 */     if (this.children != null) {
/* 57 */       ret = new Object[this.children.length];
/* 58 */       for (int i = 0; i < this.children.length; ++i) {
/* 59 */         ret[i] = this.children[i].jjtAccept(visitor, data);
/*    */       }
/*    */     }
/* 62 */     return ret;
/*    */   }
/*    */ }