/*    */ package com.hisun.teller.action;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import com.hisun.web.action.BaseAction;
/*    */ import com.hisun.web.service.HiCallHostService;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ import org.apache.struts2.ServletActionContext;
/*    */ 
/*    */ public class SetUsrAction extends BaseAction
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Set selset;
/*    */ 
/*    */   public SetUsrAction()
/*    */   {
/* 25 */     this.selset = new HashSet();
/*    */   }
/*    */ 
/*    */   protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log)
/*    */   {
/* 30 */     ServletContext context = ServletActionContext.getServletContext();
/* 31 */     HiETF etf = HiETFFactory.createETF();
/* 32 */     etf.setChildValue("ID", rspetf.getChildValue("ID"));
/* 33 */     HiETF etf2 = null;
/* 34 */     String txncode = "MNG090938";
/*    */     try {
/* 36 */       etf2 = getCallHostService().callhost(txncode, etf, context);
/*    */ 
/* 38 */       int num = NumberUtils.toInt(etf2.getChildValue("REC_NUM"));
/*    */ 
/* 40 */       for (int j = 0; j < num; ++j) {
/* 41 */         HiETF node = etf2.getChildNode("GROUP_" + (j + 1));
/* 42 */         if (node == null)
/*    */           break;
/* 44 */         String grpid = node.getChildValue("GRP_ID");
/* 45 */         this.selset.add(grpid);
/*    */       }
/*    */ 
/* 48 */       request.setAttribute("ROLESET", this.selset);
/*    */     }
/*    */     catch (HiException e) {
/* 51 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 54 */     return super.endProcess(request, rspetf, _log);
/*    */   }
/*    */ }