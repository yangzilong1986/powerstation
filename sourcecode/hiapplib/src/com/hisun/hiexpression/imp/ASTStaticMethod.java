 package com.hisun.hiexpression.imp;
 
 import com.hisun.hiexpression.HiExpUtil;
 import java.lang.reflect.Method;
 
 public class ASTStaticMethod extends SimpleNode
 {
   public String className;
   public String methodName;
   public Method method;
 
   public ASTStaticMethod(int id)
   {
     super(id);
   }
 
   public ASTStaticMethod(ICSExpParser p, int id) {
     super(p, id);
   }
 
   void init(String className, String methodName)
     throws ParseException
   {
     this.className = className;
     this.methodName = methodName;
     try
     {
       this.method = HiExpUtil.getStaticMethod(className, methodName, (this.children == null) ? 0 : this.children.length);
     }
     catch (Exception e)
     {
       throw new ParseException("找不到函数:" + methodName);
     }
   }
 
   public String toString() {
     return super.toString() + ",class:" + this.className + ",method:" + this.methodName;
   }
 
   public Object jjtAccept(ICSExpParserVisitor visitor, Object data)
     throws Exception
   {
     return visitor.visit(this, data);
   }
 
   public Object childrenAccept(ICSExpParserVisitor visitor, Object data)
     throws Exception
   {
     Object[] ret = null;
 
     if (this.children != null) {
       ret = new Object[this.children.length];
       for (int i = 0; i < this.children.length; ++i) {
         ret[i] = this.children[i].jjtAccept(visitor, data);
       }
     }
     return ret;
   }
 }