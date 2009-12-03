/*    */ package com.hisun.component.web;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.common.HiETF2HashMapList;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.template.TemplateEngine;
/*    */ import com.hisun.template.TemplateFactory;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class FormateDataByTemplate
/*    */ {
/*    */   private TemplateEngine freemarkerEngine;
/*    */ 
/*    */   public FormateDataByTemplate()
/*    */   {
/* 28 */     this.freemarkerEngine = ((TemplateEngine)TemplateFactory.getInstance().getBean("freemarker"));
/*    */   }
/*    */ 
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException, Exception
/*    */   {
/* 34 */     String template = HiArgUtils.getStringNotNull(args, "template");
/*    */ 
/* 37 */     String objEtf = args.get("objEtf");
/*    */ 
/* 40 */     String objFile = args.get("objFile");
/*    */ 
/* 43 */     if ((StringUtils.isBlank(objEtf)) && (StringUtils.isBlank(objFile)))
/*    */     {
/* 45 */       objEtf = "OBJ_DAT";
/*    */     }
/*    */ 
/* 48 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 50 */     HiETF2HashMapList etf2HashMap = new HiETF2HashMapList(msg.getETFBody());
/* 51 */     StringBuffer result = this.freemarkerEngine.run(etf2HashMap.map(), template);
/*    */ 
/* 53 */     if (StringUtils.isNotBlank(objFile))
/*    */     {
/* 57 */       DataOutputStream dout = new DataOutputStream(new FileOutputStream(objFile));
/* 58 */       dout.write(result.toString().getBytes());
/*    */     }
/*    */     else
/*    */     {
/* 62 */       msg.getETFBody().setChildValue(objEtf, result.toString());
/*    */     }
/*    */ 
/* 65 */     return 0;
/*    */   }
/*    */ }