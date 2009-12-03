/*    */ package com.hisun.junit;
/*    */ 
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import junit.framework.TestCase;
/*    */ 
/*    */ public class HiTestCase extends TestCase
/*    */ {
/*    */   protected HiMessage msg;
/*    */   protected HiMessageContext ctx;
/*    */   protected HiETF root;
/*    */   protected HiATLParam args;
/*    */ 
/*    */   public HiTestCase()
/*    */   {
/* 16 */     this.msg = null;
/* 17 */     this.ctx = null;
/* 18 */     this.root = null;
/* 19 */     this.args = null; }
/*    */ 
/*    */   protected void setUp() throws Exception {
/* 22 */     super.setUp();
/* 23 */     System.setProperty("_ICS_ENV", "_ICS_JUNIT_ENV");
/* 24 */     System.setProperty("work.dir", "./");
/*    */ 
/* 26 */     HiICSProperty.getBakDir();
/* 27 */     HiContext.getRootContext().setProperty("SVR.log", HiLog.getLogger("server.trc"));
/*    */ 
/* 29 */     this.msg = new HiMessage("test", "plain");
/* 30 */     this.msg.setHeadItem("ECT", "text/plain");
/* 31 */     this.msg.setHeadItem("SCH", "rq");
/* 32 */     this.msg.setHeadItem("STF", "INFO");
/* 33 */     HiContext.pushCurrentContext(HiContext.getRootContext());
/*    */ 
/* 35 */     this.root = HiETFFactory.createETF();
/* 36 */     this.msg.setBody(this.root);
/*    */ 
/* 38 */     this.ctx = new HiMessageContext();
/* 39 */     this.ctx.pushParent(HiContext.getCurrentContext());
/* 40 */     HiMessageContext.setCurrentMessageContext(this.ctx);
/* 41 */     this.ctx.setCurrentMsg(this.msg);
/* 42 */     this.args = new HiATLParam();
/* 43 */     HiContext.getCurrentContext();
/*    */   }
/*    */ }