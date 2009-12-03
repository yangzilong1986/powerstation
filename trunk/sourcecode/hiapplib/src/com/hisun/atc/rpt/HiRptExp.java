/*     */ package com.hisun.atc.rpt;
/*     */ 
/*     */ import com.hisun.atc.rpt.xml.HiMsgConvertNode;
/*     */ import com.hisun.hiexpression.HiExpUtil;
/*     */ import com.hisun.hiexpression.imp.ASTConst;
/*     */ import com.hisun.hiexpression.imp.ASTEq;
/*     */ import com.hisun.hiexpression.imp.ASTGreater;
/*     */ import com.hisun.hiexpression.imp.ASTGreaterEq;
/*     */ import com.hisun.hiexpression.imp.ASTIcsVarRef;
/*     */ import com.hisun.hiexpression.imp.ASTLess;
/*     */ import com.hisun.hiexpression.imp.ASTLessEq;
/*     */ import com.hisun.hiexpression.imp.ASTNotEq;
/*     */ import com.hisun.hiexpression.imp.ASTStaticMethod;
/*     */ import com.hisun.hiexpression.imp.ICSExpParser;
/*     */ import com.hisun.hiexpression.imp.ICSExpParserVisitor;
/*     */ import com.hisun.hiexpression.imp.Node;
/*     */ import com.hisun.hiexpression.imp.SimpleNode;
/*     */ import java.io.StringReader;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiRptExp
/*     */   implements ICSExpParserVisitor
/*     */ {
/*     */   private String exp;
/*  27 */   private SimpleNode root = null;
/*     */   private HiMsgConvertNode convert;
/*     */ 
/*     */   public SimpleNode getRoot()
/*     */   {
/*  32 */     return this.root;
/*     */   }
/*     */ 
/*     */   public HiRptExp(String exp) {
/*  36 */     if (StringUtils.isEmpty(exp)) {
/*  37 */       throw new IllegalArgumentException("表达式不能为空!");
/*     */     }
/*     */ 
/*  40 */     this.exp = exp;
/*  41 */     ICSExpParser parser = new ICSExpParser(new StringReader(exp));
/*     */     try
/*     */     {
/*  45 */       this.root = ((SimpleNode)parser.topLevelExpression());
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/*  50 */       throw new IllegalArgumentException("表达式(" + exp + ")解析失败:" + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getValue(Object env) throws Exception
/*     */   {
/*  56 */     return ((String)this.root.jjtAccept(this, env));
/*     */   }
/*     */ 
/*     */   public Object visit(SimpleNode node, Object data) {
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTEq node, Object data) throws Exception
/*     */   {
/*  65 */     Object var1 = node.jjtGetChild(0).jjtAccept(this, data);
/*  66 */     Object var2 = node.jjtGetChild(1).jjtAccept(this, data);
/*     */ 
/*  68 */     if (StringUtils.equals((String)var1, (String)var2)) {
/*  69 */       return Boolean.TRUE;
/*     */     }
/*  71 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTNotEq node, Object data) throws Exception
/*     */   {
/*  76 */     Object var1 = node.jjtGetChild(0).jjtAccept(this, data);
/*  77 */     Object var2 = node.jjtGetChild(1).jjtAccept(this, data);
/*     */ 
/*  79 */     if (StringUtils.equals((String)var1, (String)var2)) {
/*  80 */       return Boolean.FALSE;
/*     */     }
/*  82 */     return Boolean.TRUE;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTIcsVarRef node, Object data) {
/*  86 */     Object result = null;
/*     */ 
/*  88 */     switch (node.type)
/*     */     {
/*     */     case 0:
/*  90 */       break;
/*     */     case 1:
/*  92 */       break;
/*     */     case 2:
/*  94 */       break;
/*     */     case 3:
/*  97 */       result = ((Map)data).get(node.item.toUpperCase());
/*  98 */       if (result == null)
/*     */       {
/* 101 */         return null;
/*     */       }
/*     */ 
/* 104 */       if (this.convert != null) {
/* 105 */         result = this.convert.convert(node.item, (String)result);
/*     */       }
/*     */ 
/* 106 */       break;
/*     */     case 4:
/*     */     }
/*     */ 
/* 114 */     if (result != null)
/* 115 */       result = result.toString();
/* 116 */     return result;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTConst node, Object data) {
/* 120 */     return node.getValue();
/*     */   }
/*     */ 
/*     */   public Object visit(ASTStaticMethod node, Object data) throws Exception {
/* 124 */     Object[] args = (Object[])(Object[])node.childrenAccept(this, data);
/* 125 */     Object returnValue = null;
/* 126 */     if (node.method != null) {
/* 127 */       returnValue = HiExpUtil.invokeStaticMethod(node.method, data, args);
/*     */     }
/*     */ 
/* 132 */     return returnValue;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTLess node, Object data) throws Exception {
/* 136 */     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
/* 137 */     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
/* 138 */     int i1 = 0; int i2 = 0;
/*     */ 
/* 140 */     if (var1 != null) {
/* 141 */       i1 = NumberUtils.toInt(var1.trim(), 0);
/*     */     }
/* 143 */     if (var2 != null) {
/* 144 */       i2 = NumberUtils.toInt(var2.trim(), 0);
/*     */     }
/* 146 */     if (i1 < i2) {
/* 147 */       return Boolean.TRUE;
/*     */     }
/* 149 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTGreater node, Object data) throws Exception {
/* 153 */     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
/* 154 */     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
/*     */ 
/* 156 */     int i1 = 0; int i2 = 0;
/*     */ 
/* 158 */     if (var1 != null) {
/* 159 */       i1 = NumberUtils.toInt(var1.trim(), 0);
/*     */     }
/* 161 */     if (var2 != null) {
/* 162 */       i2 = NumberUtils.toInt(var2.trim(), 0);
/*     */     }
/* 164 */     if (i1 > i2) {
/* 165 */       return Boolean.TRUE;
/*     */     }
/* 167 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTLessEq node, Object data) throws Exception {
/* 171 */     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
/* 172 */     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
/*     */ 
/* 174 */     int i1 = 0; int i2 = 0;
/*     */ 
/* 176 */     if (var1 != null) {
/* 177 */       i1 = NumberUtils.toInt(var1.trim(), 0);
/*     */     }
/* 179 */     if (var2 != null) {
/* 180 */       i2 = NumberUtils.toInt(var2.trim(), 0);
/*     */     }
/* 182 */     if (i1 <= i2) {
/* 183 */       return Boolean.TRUE;
/*     */     }
/* 185 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTGreaterEq node, Object data) throws Exception {
/* 189 */     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
/* 190 */     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
/*     */ 
/* 192 */     int i1 = 0; int i2 = 0;
/*     */ 
/* 194 */     if (var1 != null) {
/* 195 */       i1 = NumberUtils.toInt(var1.trim(), 0);
/*     */     }
/* 197 */     if (var2 != null) {
/* 198 */       i2 = NumberUtils.toInt(var2.trim(), 0);
/*     */     }
/* 200 */     if (i1 >= i2) {
/* 201 */       return Boolean.TRUE;
/*     */     }
/* 203 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   public void setConvert(HiMsgConvertNode convert) {
/* 207 */     this.convert = convert;
/*     */   }
/*     */ }