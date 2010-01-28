 package com.hisun.hiexpression;
 
 import com.hisun.exception.HiException;
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
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.message.HiMessageHelper;
 import com.hisun.util.HiStringManager;
 import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
 import java.io.StringReader;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiExpression
   implements ICSExpParserVisitor
 {
   private String exp;
   private SimpleNode root = null;
 
   private static Logger log = HiLog.getLogger("expression.trc");
   private static HiStringManager sm = HiStringManager.getManager();
 
   public String getExp() {
     return this.exp;
   }
 
   protected HiExpression(String exp)
   {
     if (StringUtils.isEmpty(exp)) {
       throw new IllegalArgumentException("表达式不能为空!");
     }
 
     this.exp = exp;
     ICSExpParser parser = new ICSExpParser(new StringReader(exp));
     try
     {
       this.root = ((SimpleNode)parser.topLevelExpression());
     } catch (Throwable e) {
       log.error("表达式(" + exp + ")解析失败:", e);
       throw new IllegalArgumentException("表达式解析失败:" + e.getMessage());
     }
   }
 
   public String getValue(HiMessageContext ctx)
     throws HiException
   {
     try
     {
       return ((String)this.root.jjtAccept(this, ctx));
     }
     catch (Exception e) {
       throw HiException.makeException("215113", this.exp, e);
     }
   }
 
   public boolean isReturnTrue(HiMessageContext ctx) throws HiException
   {
     Object retValue;
     try
     {
       retValue = this.root.jjtAccept(this, ctx);
     } catch (Exception e) {
       throw new HiException("215113", this.exp, e);
     }
     if (retValue instanceof Boolean)
       return ((Boolean)retValue).booleanValue();
     if (retValue instanceof String) {
       if (StringUtils.equals((String)retValue, "1"))
         return true;
       if (StringUtils.equals((String)retValue, "0")) {
         return false;
       }
     }
     throw new HiException("215119", this.exp);
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
 
   public Object visit(ASTIcsVarRef node, Object data)
   {
     HiMessageContext ctx = (HiMessageContext)data;
     HiMessage msg = ctx.getCurrentMsg();
 
     Object result = null;
 
     switch (node.type) {
     case 0:
       result = ctx.getBaseSource(node.item);
       break;
     case 1:
       try {
         result = ctx.getBCFG(node.item); } catch (HiException e) {
       }
       break;
     case 2:
       Object o = msg.getObjectHeadItem(node.item);
       if (o != null)
         result = o.toString();
       break;
     case 3:
       if (node.item.startsWith("ROOT."))
       {
         result = ((HiETF)msg.getBody()).getGrandChildValue(node.item);
       }
       else
       {
         result = ((HiETF)msg.getBody()).getGrandChildValue(HiMessageHelper.getCurEtfLevel(msg) + node.item);
       }
       break;
     case 4:
       result = ctx.getPara(node.item);
       break;
     case 5:
       result = getValueFromDS(ctx, node);
       break;
     case 6:
       result = ctx.getStrProp("@SYS", node.item);
     }
 
     if (result == null) {
       return "";
     }
     return result;
   }
 
   private Object getValueFromDS(HiMessageContext ctx, ASTIcsVarRef node) {
     String item = node.item;
     String key = null;
 
     int idx = item.indexOf(".");
     if (idx == -1) {
       return null;
     }
     Object o = ctx.getBaseSource(item.substring(0, idx));
     key = item.substring(idx + 1);
     if (o instanceof HiETF)
       return ((HiETF)o).getGrandChildValue(key);
     if (o instanceof HiMessage)
       return ((HiMessage)o).getHeadItem(key);
     if (o instanceof ConcurrentHashMap) {
       return ((ConcurrentHashMap)o).get(key);
     }
     log.warn(sm.getString("220320", item.substring(0, idx)));
     return null;
   }
 
   public Object visit(ASTConst node, Object data)
   {
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
 }