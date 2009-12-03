/*     */ package com.hisun.hiexpression;
/*     */ 
/*     */ import com.hisun.exception.HiException;
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
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.message.HiMessageHelper;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
/*     */ import java.io.StringReader;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiExpression
/*     */   implements ICSExpParserVisitor
/*     */ {
/*     */   private String exp;
/*  49 */   private SimpleNode root = null;
/*     */ 
/*  52 */   private static Logger log = HiLog.getLogger("expression.trc");
/*  53 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public String getExp() {
/*  56 */     return this.exp;
/*     */   }
/*     */ 
/*     */   protected HiExpression(String exp)
/*     */   {
/*  70 */     if (StringUtils.isEmpty(exp)) {
/*  71 */       throw new IllegalArgumentException("表达式不能为空!");
/*     */     }
/*     */ 
/*  74 */     this.exp = exp;
/*  75 */     ICSExpParser parser = new ICSExpParser(new StringReader(exp));
/*     */     try
/*     */     {
/*  78 */       this.root = ((SimpleNode)parser.topLevelExpression());
/*     */     } catch (Throwable e) {
/*  80 */       log.error("表达式(" + exp + ")解析失败:", e);
/*  81 */       throw new IllegalArgumentException("表达式解析失败:" + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getValue(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 100 */       return ((String)this.root.jjtAccept(this, ctx));
/*     */     }
/*     */     catch (Exception e) {
/* 103 */       throw HiException.makeException("215113", this.exp, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isReturnTrue(HiMessageContext ctx) throws HiException
/*     */   {
/*     */     Object retValue;
/*     */     try
/*     */     {
/* 112 */       retValue = this.root.jjtAccept(this, ctx);
/*     */     } catch (Exception e) {
/* 114 */       throw new HiException("215113", this.exp, e);
/*     */     }
/* 116 */     if (retValue instanceof Boolean)
/* 117 */       return ((Boolean)retValue).booleanValue();
/* 118 */     if (retValue instanceof String) {
/* 119 */       if (StringUtils.equals((String)retValue, "1"))
/* 120 */         return true;
/* 121 */       if (StringUtils.equals((String)retValue, "0")) {
/* 122 */         return false;
/*     */       }
/*     */     }
/* 125 */     throw new HiException("215119", this.exp);
/*     */   }
/*     */ 
/*     */   public Object visit(SimpleNode node, Object data) {
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTEq node, Object data) throws Exception
/*     */   {
/* 134 */     Object var1 = node.jjtGetChild(0).jjtAccept(this, data);
/* 135 */     Object var2 = node.jjtGetChild(1).jjtAccept(this, data);
/*     */ 
/* 137 */     if (StringUtils.equals((String)var1, (String)var2)) {
/* 138 */       return Boolean.TRUE;
/*     */     }
/* 140 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTNotEq node, Object data) throws Exception
/*     */   {
/* 145 */     Object var1 = node.jjtGetChild(0).jjtAccept(this, data);
/* 146 */     Object var2 = node.jjtGetChild(1).jjtAccept(this, data);
/*     */ 
/* 148 */     if (StringUtils.equals((String)var1, (String)var2)) {
/* 149 */       return Boolean.FALSE;
/*     */     }
/* 151 */     return Boolean.TRUE;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTIcsVarRef node, Object data)
/*     */   {
/* 157 */     HiMessageContext ctx = (HiMessageContext)data;
/* 158 */     HiMessage msg = ctx.getCurrentMsg();
/*     */ 
/* 160 */     Object result = null;
/*     */ 
/* 162 */     switch (node.type) {
/*     */     case 0:
/* 164 */       result = ctx.getBaseSource(node.item);
/* 165 */       break;
/*     */     case 1:
/*     */       try {
/* 168 */         result = ctx.getBCFG(node.item); } catch (HiException e) {
/*     */       }
/* 170 */       break;
/*     */     case 2:
/* 172 */       Object o = msg.getObjectHeadItem(node.item);
/* 173 */       if (o != null)
/* 174 */         result = o.toString();
/* 175 */       break;
/*     */     case 3:
/* 178 */       if (node.item.startsWith("ROOT."))
/*     */       {
/* 180 */         result = ((HiETF)msg.getBody()).getGrandChildValue(node.item);
/*     */       }
/*     */       else
/*     */       {
/* 184 */         result = ((HiETF)msg.getBody()).getGrandChildValue(HiMessageHelper.getCurEtfLevel(msg) + node.item);
/*     */       }
/* 186 */       break;
/*     */     case 4:
/* 188 */       result = ctx.getPara(node.item);
/* 189 */       break;
/*     */     case 5:
/* 191 */       result = getValueFromDS(ctx, node);
/* 192 */       break;
/*     */     case 6:
/* 194 */       result = ctx.getStrProp("@SYS", node.item);
/*     */     }
/*     */ 
/* 201 */     if (result == null) {
/* 202 */       return "";
/*     */     }
/* 204 */     return result;
/*     */   }
/*     */ 
/*     */   private Object getValueFromDS(HiMessageContext ctx, ASTIcsVarRef node) {
/* 208 */     String item = node.item;
/* 209 */     String key = null;
/*     */ 
/* 211 */     int idx = item.indexOf(".");
/* 212 */     if (idx == -1) {
/* 213 */       return null;
/*     */     }
/* 215 */     Object o = ctx.getBaseSource(item.substring(0, idx));
/* 216 */     key = item.substring(idx + 1);
/* 217 */     if (o instanceof HiETF)
/* 218 */       return ((HiETF)o).getGrandChildValue(key);
/* 219 */     if (o instanceof HiMessage)
/* 220 */       return ((HiMessage)o).getHeadItem(key);
/* 221 */     if (o instanceof ConcurrentHashMap) {
/* 222 */       return ((ConcurrentHashMap)o).get(key);
/*     */     }
/* 224 */     log.warn(sm.getString("220320", item.substring(0, idx)));
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTConst node, Object data)
/*     */   {
/* 230 */     return node.getValue();
/*     */   }
/*     */ 
/*     */   public Object visit(ASTStaticMethod node, Object data) throws Exception {
/* 234 */     Object[] args = (Object[])(Object[])node.childrenAccept(this, data);
/* 235 */     Object returnValue = null;
/* 236 */     if (node.method != null) {
/* 237 */       returnValue = HiExpUtil.invokeStaticMethod(node.method, data, args);
/*     */     }
/*     */ 
/* 242 */     return returnValue;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTLess node, Object data) throws Exception {
/* 246 */     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
/* 247 */     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
/* 248 */     int i1 = 0; int i2 = 0;
/*     */ 
/* 250 */     if (var1 != null) {
/* 251 */       i1 = NumberUtils.toInt(var1.trim(), 0);
/*     */     }
/* 253 */     if (var2 != null) {
/* 254 */       i2 = NumberUtils.toInt(var2.trim(), 0);
/*     */     }
/* 256 */     if (i1 < i2) {
/* 257 */       return Boolean.TRUE;
/*     */     }
/* 259 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTGreater node, Object data) throws Exception {
/* 263 */     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
/* 264 */     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
/*     */ 
/* 266 */     int i1 = 0; int i2 = 0;
/*     */ 
/* 268 */     if (var1 != null) {
/* 269 */       i1 = NumberUtils.toInt(var1.trim(), 0);
/*     */     }
/* 271 */     if (var2 != null) {
/* 272 */       i2 = NumberUtils.toInt(var2.trim(), 0);
/*     */     }
/* 274 */     if (i1 > i2) {
/* 275 */       return Boolean.TRUE;
/*     */     }
/* 277 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTLessEq node, Object data) throws Exception {
/* 281 */     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
/* 282 */     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
/*     */ 
/* 284 */     int i1 = 0; int i2 = 0;
/*     */ 
/* 286 */     if (var1 != null) {
/* 287 */       i1 = NumberUtils.toInt(var1.trim(), 0);
/*     */     }
/* 289 */     if (var2 != null) {
/* 290 */       i2 = NumberUtils.toInt(var2.trim(), 0);
/*     */     }
/* 292 */     if (i1 <= i2) {
/* 293 */       return Boolean.TRUE;
/*     */     }
/* 295 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   public Object visit(ASTGreaterEq node, Object data) throws Exception {
/* 299 */     String var1 = (String)node.jjtGetChild(0).jjtAccept(this, data);
/* 300 */     String var2 = (String)node.jjtGetChild(1).jjtAccept(this, data);
/*     */ 
/* 302 */     int i1 = 0; int i2 = 0;
/*     */ 
/* 304 */     if (var1 != null) {
/* 305 */       i1 = NumberUtils.toInt(var1.trim(), 0);
/*     */     }
/* 307 */     if (var2 != null) {
/* 308 */       i2 = NumberUtils.toInt(var2.trim(), 0);
/*     */     }
/* 310 */     if (i1 >= i2) {
/* 311 */       return Boolean.TRUE;
/*     */     }
/* 313 */     return Boolean.FALSE;
/*     */   }
/*     */ }