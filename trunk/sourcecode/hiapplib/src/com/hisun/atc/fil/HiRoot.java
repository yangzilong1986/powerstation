/*    */ package com.hisun.atc.fil;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.HiITFEngineModel;
/*    */ import com.hisun.engine.invoke.HiIEngineModel;
/*    */ import com.hisun.engine.invoke.impl.HiProcess;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiRoot extends HiITFEngineModel
/*    */ {
/*    */   String debug_file;
/*    */   String err_file;
/*    */   private HiInDesc inNode;
/*    */   private HiOutDesc outNode;
/*    */ 
/*    */   public HiRoot()
/*    */   {
/* 33 */     this.inNode = null;
/*    */ 
/* 35 */     this.outNode = null;
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 18 */     return "root";
/*    */   }
/*    */ 
/*    */   public void setDebug_file(String debug_file)
/*    */   {
/* 24 */     this.debug_file = debug_file;
/*    */   }
/*    */ 
/*    */   public void setErr_file(String err_file)
/*    */   {
/* 30 */     this.err_file = err_file;
/*    */   }
/*    */ 
/*    */   public void addChilds(HiIEngineModel child)
/*    */     throws HiException
/*    */   {
/* 38 */     if (child instanceof HiInDesc)
/* 39 */       this.inNode = ((HiInDesc)child);
/* 40 */     if (child instanceof HiOutDesc)
/* 41 */       this.outNode = ((HiOutDesc)child);
/* 42 */     super.addChilds(child);
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 46 */     processInNode(ctx);
/* 47 */     processOutNode(ctx);
/*    */   }
/*    */ 
/*    */   public void processInNode(HiMessageContext ctx) throws HiException {
/* 51 */     if (this.inNode != null)
/* 52 */       HiProcess.process(this.inNode, ctx);
/*    */   }
/*    */ 
/*    */   public void processOutNode(HiMessageContext ctx) throws HiException {
/* 56 */     if (this.outNode != null)
/* 57 */       HiProcess.process(this.outNode, ctx);
/*    */   }
/*    */ 
/*    */   public HiEngineModel getInNode() {
/* 61 */     return this.inNode;
/*    */   }
/*    */ 
/*    */   public HiEngineModel getOutNode() {
/* 65 */     return this.outNode;
/*    */   }
/*    */ 
/*    */   public HiEngineModel getNode(String nodeName, String attrName, String attrValue)
/*    */     throws HiException
/*    */   {
/* 77 */     List childs = super.getChilds();
/* 78 */     for (int i = 0; i < childs.size(); ++i) {
/* 79 */       HiEngineModel model = (HiEngineModel)childs.get(i);
/* 80 */       if (StringUtils.equalsIgnoreCase(model.getNodeName(), nodeName)) {
/* 81 */         return model;
/*    */       }
/*    */     }
/* 84 */     return null;
/*    */   }
/*    */ }