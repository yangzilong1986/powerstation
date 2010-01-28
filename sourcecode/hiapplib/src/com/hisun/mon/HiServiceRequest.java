 package com.hisun.mon;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFUtils;
 import com.hisun.protocol.tcp.HiSocketUtil;
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.net.Socket;
 import java.net.UnknownHostException;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class HiServiceRequest
 {
   public static HiETF callService(String host, int port, HiETF data)
     throws HiException, IOException
   {
     Socket server = null;
     try
     {
       server = new Socket(host, port);
     }
     catch (UnknownHostException e)
     {
       throw new HiException("215027", "UnknownHost", e);
     }
     Document doc = HiETFUtils.convertToDocument(data);
 
     HiSocketUtil.write(server.getOutputStream(), doc.getRootElement().asXML().getBytes(), 8);
 
     ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
     int ret = HiSocketUtil.read(server.getInputStream(), buf, 8);
     server.close();
 
     if (ret == 0)
     {
       return null;
     }
     try {
       doc = DocumentHelper.parseText(buf.toString());
     }
     catch (DocumentException e) {
       throw new HiException("215106", buf.toString(), e);
     }
 
     HiETF retEtf = HiETFUtils.convertToXmlETF(doc);
     return retEtf;
   }
 
   public static byte[] callService(String host, int port, int preLen, byte[] msg)
     throws HiException, IOException
   {
     Socket server = null;
     try
     {
       server = new Socket(host, port);
     }
     catch (UnknownHostException e)
     {
       throw new HiException("215027", "UnknownHost", e);
     }
 
     HiSocketUtil.write(server.getOutputStream(), msg, preLen);
 
     ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
     int ret = HiSocketUtil.read(server.getInputStream(), buf, preLen);
     server.close();
 
     if (ret == 0)
     {
       return null;
     }
 
     return buf.toByteArray();
   }
 }