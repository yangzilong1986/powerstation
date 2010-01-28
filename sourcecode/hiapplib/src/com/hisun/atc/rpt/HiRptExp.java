 package com.hisun.atc.rpt;
 
 import com.hisun.atc.rpt.xml.HiMsgConvertNode;
 import com.hisun.hiexpression.HiExpUtil;
 import com.hisun.hiexpression.imp.ASTConst;
 import com.hisun.hiexpression.imp.ASTEq;
 import com.hisun.hiexpression.imp.ASTGreater;
 import com.hisun.hiexpression.imp.ASTGreaterEq;
 import com.hisun.hiexpression.imp.ASTIcsVarRef;
 import com.hisun.hiexpression.imp.ASTLess;
 import com.hisun.hiexpression.imp.ASTLessEq;
 import com.hisun.hiexpression.imp.ASTNotEq;
 import com.hisun.hiexpression.imp.ASTStaticMethod;
 import com.hisun.hiexpression.imp.ICSExpParser;
 import com.hisun.hiexpression.imp.ICSExpParserVisitor;
 import com.hisun.hiexpression.imp.Node;
 import com.hisun.hiexpression.imp.SimpleNode;
 import java.io.StringReader;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiRptExp
   implements ICSExpParserVisitor
 {
   private String exp;
   private SimpleNode root = null;
   private HiMsgConvertNode convert;
 
   public SimpleNode getRoot()
   {
     return this.root;
   }
 
   public HiRptExp(String exp) {
     if (StringUtils.isEmpty(exp)) {
       throw new IllegalArgumentException("表达式不能为空!");
     }
 
     this.exp = exp;
     ICSExpParser parser = new ICSExpParser(new StringReader(exp));
     try
     {
       this.root = ((SimpleNode)parser.topLevelExpression());
     }
     catch (Throwable e)
     {
       throw new IllegalArgumentException("表达式(" + exp + ")解析失败:" + e.getMessage());
     }
   }
 
   public String getValue(Object env) throws Exception
   {
     return ((String)this.root.jjtAccept(this, env));
   }
 
   public Object visit(SimpleNode node, Object data) {
     return null;
   }
 
   public Object visit(ASTEq node, Object data) throws Exception
   {
     Object var1 = node.jjtGetChild(0).jjtAccept(this, data);
     Object var2 = node.jjtGetChild(1).jjtAccept(this, data);
 
     if (StringUtils.equals((String)var1, (String)var2)) {
       return Boolean.TRUE;
     }
     return Boolean.FALSE;
   }
 
   public Object visit(ASTNotEq node, Object data) throws Exception
   {
     Object var1 = node.jjtGetChild(0).jjtAccept(this, data);
     Object var2 = node.jjtGetChild(1).jjtAccept(this, data);
 
     if (StringUtils.equals((String)var1, (String)var2)) {
       return Boolean.FALSE;
     }
     return Boolean.TRUE;
   }
 
   public Object visit(ASTIcsVarRef node, Object data) {
     Object result = null;
 
     switch (node.type)
     {
     case 0:
       break;
     case 1:
       break;
     case 2:
       break;
     case 3:
       result = ((Map)data).get(node.item.toUpperCase());
       if (result == null)
       {
         return null;
       }
 
       if (this.convert != null) {
         result = this.convert.convert(node.item, (String)result);
       }
 
       break;
     case 4:
     }
 
     if (result != null)
       result = result.toString();
     return result;
   }
 
   public Object visit(ASTConst node, Object data) {
     return node.getValue();
   }
 
   public Object visit(ASTStaticMethod node, Object data) throws Exception {
     Object[] args = (Object[])(Object[])node.childrenAccept(this, data);
     Object returnValue = null;
     if (node.method != null) {
       returnValue = HiExpUtil.invokeStaticMethod(node.method, data, args);
     }
 
     return returnValue;
   }
 
   public Object visit(ASTLess node, Object data) throws Exception {
     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
     int i1 = 0; int i2 = 0;
 
     if (var1 != null) {
       i1 = NumberUtils.toInt(var1.trim(), 0);
     }
     if (var2 != null) {
       i2 = NumberUtils.toInt(var2.trim(), 0);
     }
     if (i1 < i2) {
       return Boolean.TRUE;
     }
     return Boolean.FALSE;
   }
 
   public Object visit(ASTGreater node, Object data) throws Exception {
     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
 
     int i1 = 0; int i2 = 0;
 
     if (var1 != null) {
       i1 = NumberUtils.toInt(var1.trim(), 0);
     }
     if (var2 != null) {
       i2 = NumberUtils.toInt(var2.trim(), 0);
     }
     if (i1 > i2) {
       return Boolean.TRUE;
     }
     return Boolean.FALSE;
   }
 
   public Object visit(ASTLessEq node, Object data) throws Exception {
     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
 
     int i1 = 0; int i2 = 0;
 
     if (var1 != null) {
       i1 = NumberUtils.toInt(var1.trim(), 0);
     }
     if (var2 != null) {
       i2 = NumberUtils.toInt(var2.trim(), 0);
     }
     if (i1 <= i2) {
       return Boolean.TRUE;
     }
     return Boolean.FALSE;
   }
 
   public Object visit(ASTGreaterEq node, Object data) throws Exception {
     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
 
     int i1 = 0; int i2 = 0;
 
     if (var1 != null) {
       i1 = NumberUtils.toInt(var1.trim(), 0);
     }
     if (var2 != null) {
       i2 = NumberUtils.toInt(var2.trim(), 0);
     }
     if (i1 >= i2) {
       return Boolean.TRUE;
     }
     return Boolean.FALSE;
   }
 
   public void setConvert(HiMsgConvertNode convert) {
     this.convert = convert;
   }
 }