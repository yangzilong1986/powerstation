 package com.hisun.common.util;
 
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFUtils;
 import com.hisun.message.HiMessage;
 import com.hisun.protocol.tcp.HiSocketUtil;
 import java.io.ByteArrayOutputStream;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.io.PrintStream;
 import java.net.Socket;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiSendInnerMsg
 {
   public static void main(String[] args)
     throws Exception
   {
     if (args.length != 3) {
       System.out.println("args error");
       return;
     }
     String ip = args[0];
     int port = NumberUtils.toInt(args[1]);
     HiETF root = HiETFUtils.fromJSONStr(args[2]);
     String serverName = root.getChildValue("SVR_NM");
     String txnCd = root.getChildValue("TXN_CD");
     String msgType = root.getChildValue("MSG_TYP");
     if (StringUtils.isBlank(serverName)) {
       System.out.println("not contains SVR_NM node");
       return;
     }
 
     if (msgType == null) {
       msgType = "PLTIN0";
     }
 
     HiMessage msg = new HiMessage(serverName, msgType);
     msg.setHeadItem("STF", "1");
     msg.setHeadItem("SDT", serverName);
     if (StringUtils.isNotBlank(txnCd)) {
       msg.setHeadItem("STC", txnCd);
     }
 
     msg.setHeadItem("SCH", "rq");
     msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
     msg.setHeadItem("ECT", "text/etf");
     msg.setBody(root);
     Socket socket = new Socket(ip, port);
     InputStream is = socket.getInputStream();
     OutputStream os = socket.getOutputStream();
 
     HiSocketUtil.write(os, msg.toString().getBytes(), 8);
 
     ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
     if (HiSocketUtil.read(is, buf, 8) == 0) {
       System.out.println("receive data error");
       return;
     }
     msg = new HiMessage(buf.toString());
     String rspCd = msg.getETFBody().getChildValue("RSP_CD");
     String rspMsg = msg.getETFBody().getChildValue("RSP_MSG");
     if ("000000".equals(rspCd))
       System.out.println("success");
     else
       System.out.println(rspCd + ":" + rspMsg);
   }
 }