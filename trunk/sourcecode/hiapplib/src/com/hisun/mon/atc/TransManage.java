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
/*    */ public class TransManage
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 28 */     String host = HiArgUtils.getStringNotNull(args, "host");
/* 29 */     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
/*    */ 
/* 31 */     String transList = HiArgUtils.getStringNotNull(args, "trans");
/* 32 */     String serverList = HiArgUtils.getStringNotNull(args, "servers");
/* 33 */     String mode = HiArgUtils.getStringNotNull(args, "mode");
/*    */ 
/* 35 */     String[] transArr = StringUtils.split(transList, "|");
/* 36 */     String[] serverArr = StringUtils.split(serverList, "|");
/*    */ 
/* 38 */     String cmdPre = "<ROOT><CODE>090811</CODE><server>";
/* 39 */     if ("stop".equalsIgnoreCase(mode)) {
/* 40 */       cmdPre = "<ROOT><CODE>090812</CODE><server>";
/*    */     }
/*    */ 
/* 43 */     byte[] result = (byte[])null;
/* 44 */     Element root = null;
/* 45 */     for (int i = 0; i < transArr.length; ++i) {
/* 46 */       String reqCmd = cmdPre + serverArr[i] + "</server><code>" + transArr[i] + "</code></ROOT>";
/*    */       try
/*    */       {
/* 49 */         result = HiServiceRequest.callService(host, port, 8, reqCmd.getBytes());
/* 50 */         root = DocumentHelper.parseText(new String(result)).getRootElement();
/*    */       } catch (IOException e) {
/* 52 */         HiException.makeException(e);
/*    */       } catch (DocumentException e) {
/* 54 */         HiException.makeException(e);
/*    */       }
/*    */     }
/* 57 */     return 0;
/*    */   }
/*    */ }