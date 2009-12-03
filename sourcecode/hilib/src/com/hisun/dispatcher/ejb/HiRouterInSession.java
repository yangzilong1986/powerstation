/*    */ package com.hisun.dispatcher.ejb;
/*    */ 
/*    */ import java.rmi.RemoteException;
/*    */ import javax.ejb.EJBException;
/*    */ import javax.ejb.SessionBean;
/*    */ import javax.ejb.SessionContext;
/*    */ 
/*    */ public class HiRouterInSession extends HiRouterInBean
/*    */   implements SessionBean
/*    */ {
/*    */   public void ejbActivate()
/*    */     throws EJBException, RemoteException
/*    */   {
/* 20 */     super.ejbActivate();
/*    */   }
/*    */ 
/*    */   public void ejbPassivate() throws EJBException, RemoteException
/*    */   {
/* 25 */     super.ejbPassivate();
/*    */   }
/*    */ 
/*    */   public void setSessionContext(SessionContext ctx) throws EJBException
/*    */   {
/* 30 */     super.setSessionContext(ctx);
/*    */   }
/*    */ 
/*    */   public void unsetSessionContext()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void ejbRemove() throws EJBException, RemoteException
/*    */   {
/* 39 */     super.ejbRemove();
/*    */   }
/*    */ }