 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 
 public class HiSet extends HiEngineModel
 {
   private String strName;
   private HiExpression exp;
   private HiExpression strNameExpr;
   private String statement;
   private HiStringManager sm;
 
   public HiSet()
   {
     this.exp = null;
 
     this.strNameExpr = null;
 
     this.statement = null;
 
     this.sm = HiStringManager.getManager();
   }
 
   public void setName(String strName)
     throws HiException
   {
     int idx;
     this.statement = strName;
     if ((idx = strName.indexOf(61)) == -1) {
       throw new HiException("213329", strName);
     }
 
     this.strName = strName.substring(0, idx);
     if (isArrayExpr(this.strName))
       this.strNameExpr = HiExpFactory.createExp(getArrayExpr(this.strName.trim()));
     else {
       switch (this.strName.charAt(0))
       {
       case '#':
       case '%':
       case '@':
       case '~':
         break;
       default:
         this.strNameExpr = HiExpFactory.createExp(this.strName.trim());
       }
 
     }
 
     if (idx != strName.length() - 1) {
       String tmp = strName.substring(idx + 1);
       if (isArrayExpr(tmp))
         this.exp = HiExpFactory.createExp(getArrayExpr(tmp.trim()));
       else
         this.exp = HiExpFactory.createExp(tmp);
     }
   }
 
   protected String getArrayExpr(String expr)
     throws HiException
   {
     int i = 0;
     StringBuffer result = new StringBuffer();
     if (expr.charAt(0) == '$') {
       ++i;
       result.append("ETF(STRCAT(");
     } else {
       result.append("STRCAT(");
     }
 
     for (; i < expr.length(); ++i)
       if (expr.charAt(i) != '[') {
         result.append(expr.charAt(i));
       }
       else {
         ++i;
         result.append("_,");
         for (; i < expr.length(); ++i) {
           if (expr.charAt(i) == ']') {
             result.append(',');
             break;
           }
           result.append(expr.charAt(i));
         }
       }
     if (expr.charAt(0) == '$')
       result.append("))");
     else {
       result.append(")");
     }
     return result.toString();
   }
 
   protected boolean isArrayExpr(String expr)
   {
     int idx1 = expr.indexOf(91);
     int idx2 = expr.indexOf(93);
 
     return ((idx1 == -1) || (idx1 == 0) || (idx2 == -1) || (Character.isWhitespace(expr.charAt(idx1 - 1))) || (expr.charAt(idx2 + 1) != '.'));
   }
 
   public String getNodeName()
   {
     return "Set";
   }
 
   public String toString()
   {
     return super.toString() + ":name[" + this.statement + "]";
   }
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
     try
     {
       HiMessage mess = messContext.getCurrentMsg();
       Logger log = HiLog.getLogger(mess);
 
       String strNewValue = "";
       if (this.exp != null)
         strNewValue = this.exp.getValue(messContext);
       String name = this.strName;
       if (this.strNameExpr != null) {
         name = this.strNameExpr.getValue(messContext);
       }
       HiEngineUtilities.processFlow(name, strNewValue, true, messContext);
 
       if (log.isInfoEnabled())
         log.info(this.sm.getString("HiSet.process00", HiEngineUtilities.getCurFlowStep(), this.statement, name, strNewValue));
     }
     catch (Exception e)
     {
       if (e instanceof HiException) {
         throw ((HiException)e);
       }
       throw new HiException(e);
     }
   }
 }