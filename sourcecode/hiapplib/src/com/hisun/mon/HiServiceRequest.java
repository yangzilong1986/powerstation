/*    */ package com.hisun.mon;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFUtils;
/*    */ import com.hisun.protocol.tcp.HiSocketUtil;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ import java.net.UnknownHostException;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.DocumentHelper;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiServiceRequest
/*    */ {
/*    */   public static HiETF callService(String host, int port, HiETF data)
/*    */     throws HiException, IOException
/*    */   {
/* 32 */     Socket server = null;
/*    */     try
/*    */     {
/* 35 */       server = new Socket(host, port);
/*    */     }
/*    */     catch (UnknownHostException e)
/*    */     {
/* 39 */       throw new HiException("215027", "UnknownHost", e);
/*    */     }
/* 41 */     Document doc = HiETFUtils.convertToDocument(data);
/*    */ 
/* 43 */     HiSocketUtil.write(server.getOutputStream(), doc.getRootElement().asXML().getBytes(), 8);
/*    */ 
/* 45 */     ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
/* 46 */     int ret = HiSocketUtil.read(server.getInputStream(), buf, 8);
/* 47 */     server.close();
/*    */ 
/* 49 */     if (ret == 0)
/*    */     {
/* 52 */       return null;
/*    */     }
/*    */     try {
/* 55 */       doc = DocumentHelper.parseText(buf.toString());
/*    */     }
/*    */     catch (DocumentException e) {
/* 58 */       throw new HiException("215106", buf.toString(), e);
/*    */     }
/*    */ 
/* 61 */     HiETF retEtf = HiETFUtils.convertToXmlETF(doc);
/* 62 */     return retEtf;
/*    */   }
/*    */ 
/*    */   public static byte[] callService(String host, int port, int preLen, byte[] msg)
/*    */     throws HiException, IOException
/*    */   {
/* 76 */     Socket server = null;
/*    */     try
/*    */     {
/* 79 */       server = new Socket(host, port);
/*    */     }
/*    */     catch (UnknownHostException e)
/*    */     {
/* 83 */       throw new HiException("215027", "UnknownHost", e);
/*    */     }
/*    */ 
/* 86 */     HiSocketUtil.write(server.getOutputStream(), msg, preLen);
/*    */ 
/* 88 */     ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
/* 89 */     int ret = HiSocketUtil.read(server.getInputStream(), buf, preLen);
/* 90 */     server.close();
/*    */ 
/* 92 */     if (ret == 0)
/*    */     {
/* 95 */       return null;
/*    */     }
/*    */ 
/* 98 */     return buf.toByteArray();
/*    */   }
/*    */ }