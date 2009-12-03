/*    */ package com.hisun.common.util;
/*    */ 
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFUtils;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.protocol.tcp.HiSocketUtil;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.net.Socket;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class HiSendInnerMsg
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws Exception
/*    */   {
/* 32 */     if (args.length != 3) {
/* 33 */       System.out.println("args error");
/* 34 */       return;
/*    */     }
/* 36 */     String ip = args[0];
/* 37 */     int port = NumberUtils.toInt(args[1]);
/* 38 */     HiETF root = HiETFUtils.fromJSONStr(args[2]);
/* 39 */     String serverName = root.getChildValue("SVR_NM");
/* 40 */     String txnCd = root.getChildValue("TXN_CD");
/* 41 */     String msgType = root.getChildValue("MSG_TYP");
/* 42 */     if (StringUtils.isBlank(serverName)) {
/* 43 */       System.out.println("not contains SVR_NM node");
/* 44 */       return;
/*    */     }
/*    */ 
/* 47 */     if (msgType == null) {
/* 48 */       msgType = "PLTIN0";
/*    */     }
/*    */ 
/* 51 */     HiMessage msg = new HiMessage(serverName, msgType);
/* 52 */     msg.setHeadItem("STF", "1");
/* 53 */     msg.setHeadItem("SDT", serverName);
/* 54 */     if (StringUtils.isNotBlank(txnCd)) {
/* 55 */       msg.setHeadItem("STC", txnCd);
/*    */     }
/*    */ 
/* 58 */     msg.setHeadItem("SCH", "rq");
/* 59 */     msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
/* 60 */     msg.setHeadItem("ECT", "text/etf");
/* 61 */     msg.setBody(root);
/* 62 */     Socket socket = new Socket(ip, port);
/* 63 */     InputStream is = socket.getInputStream();
/* 64 */     OutputStream os = socket.getOutputStream();
/*    */ 
/* 66 */     HiSocketUtil.write(os, msg.toString().getBytes(), 8);
/*    */ 
/* 68 */     ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
/* 69 */     if (HiSocketUtil.read(is, buf, 8) == 0) {
/* 70 */       System.out.println("receive data error");
/* 71 */       return;
/*    */     }
/* 73 */     msg = new HiMessage(buf.toString());
/* 74 */     String rspCd = msg.getETFBody().getChildValue("RSP_CD");
/* 75 */     String rspMsg = msg.getETFBody().getChildValue("RSP_MSG");
/* 76 */     if ("000000".equals(rspCd))
/* 77 */       System.out.println("success");
/*    */     else
/* 79 */       System.out.println(rspCd + ":" + rspMsg);
/*    */   }
/*    */ }