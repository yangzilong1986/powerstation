/*    */ package com.hisun.mon.atc;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.mon.HiServiceRequest;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.DocumentHelper;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class ServerManage
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 31 */     String host = HiArgUtils.getStringNotNull(args, "host");
/* 32 */     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
/*    */ 
/* 35 */     String mode = HiArgUtils.getStringNotNull(args, "mode");
/*    */ 
/* 37 */     String name = HiArgUtils.getStringNotNull(args, "name");
/*    */ 
/* 39 */     StringBuffer reqCmd = new StringBuffer("<ROOT>");
/* 40 */     if ("start".equalsIgnoreCase(mode))
/* 41 */       reqCmd.append("<CODE>090801</CODE>");
/*    */     else {
/* 43 */       reqCmd.append("<CODE>090802</CODE>");
/*    */     }
/* 45 */     String[] nameArr = StringUtils.split(name, '|');
/* 46 */     for (int i = 0; i < nameArr.length; ++i) {
/* 47 */       reqCmd.append("<server>");
/* 48 */       reqCmd.append(nameArr[i]);
/* 49 */       reqCmd.append("</server>");
/*    */     }
/* 51 */     reqCmd.append("</ROOT>");
/*    */ 
/* 53 */     byte[] result = (byte[])null;
/* 54 */     Element root = null;
/*    */     try {
/* 56 */       result = HiServiceRequest.callService(host, port, 8, reqCmd.toString().getBytes());
/* 57 */       root = DocumentHelper.parseText(new String(result)).getRootElement();
/*    */     } catch (IOException e) {
/* 59 */       HiException.makeException(e);
/*    */     } catch (DocumentException e) {
/* 61 */       HiException.makeException(e);
/*    */     }
/* 63 */     if (!("000000".equals(root.elementText("RspCod")))) {
/* 64 */       return 1;
/*    */     }
/*    */ 
/* 67 */     return 0;
/*    */   }
/*    */ }