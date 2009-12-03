/*    */ package com.hisun.mon.atc;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.mon.HiServiceRequest;
/*    */ import java.io.IOException;
/*    */ import java.util.Iterator;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.DocumentHelper;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class GetAppList
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 31 */     String host = HiArgUtils.getStringNotNull(args, "host");
/* 32 */     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
/* 33 */     String brno = args.get("brno");
/* 34 */     HiETF body = ctx.getCurrentMsg().getETFBody();
/*    */ 
/* 37 */     String cmd = "<ROOT><CODE>090808</CODE></ROOT>";
/* 38 */     if (brno != null) {
/* 39 */       cmd = "<ROOT><CODE>090808</CODE><bk_no>" + brno + "</bk_no></ROOT>";
/*    */     }
/* 41 */     byte[] result = (byte[])null;
/* 42 */     Element root = null;
/*    */     try {
/* 44 */       result = HiServiceRequest.callService(host, port, 8, cmd.getBytes());
/* 45 */       root = DocumentHelper.parseText(new String(result)).getRootElement();
/*    */     } catch (IOException e) {
/* 47 */       HiException.makeException(e);
/*    */     } catch (DocumentException e) {
/* 49 */       HiException.makeException(e);
/*    */     }
/*    */ 
/* 53 */     int num = Integer.parseInt(root.elementText("appnum"));
/*    */ 
/* 56 */     Iterator it = null;
/* 57 */     StringBuffer sb = null;
/* 58 */     for (int i = 1; i <= num; ++i) {
/* 59 */       sb = new StringBuffer();
/* 60 */       Element appNode = root.element("app_" + i);
/* 61 */       if (appNode == null) {
/*    */         continue;
/*    */       }
/* 64 */       body.setGrandChildNode("GRP_" + i + ".APP_ID", appNode.elementText("AppID"));
/* 65 */       body.setGrandChildNode("GRP_" + i + ".APP_DESC", appNode.elementText("AppNam"));
/* 66 */       it = appNode.elementIterator("Server");
/* 67 */       while (it.hasNext()) {
/* 68 */         Element svrNode = (Element)it.next();
/* 69 */         sb.append(svrNode.getText());
/* 70 */         sb.append("|");
/*    */       }
/*    */ 
/* 73 */       body.setGrandChildNode("GRP_" + i + ".SVR_LIST", sb.toString());
/*    */     }
/*    */ 
/* 76 */     return 0;
/*    */   }
/*    */ }