 package com.hisun.protocol.http;
 
 import com.hisun.exception.HiException;
 import com.hisun.util.HiByteBuffer;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.httpclient.NameValuePair;
 import org.apache.commons.httpclient.methods.GetMethod;
 import org.apache.commons.httpclient.methods.PostMethod;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class HiHttpConnectorFormImpl extends HiHttpConnector
 {
   protected void setGetParams(GetMethod method, Object request)
     throws HiException
   {
     Element root;
     try
     {
       root = DocumentHelper.parseText(((HiByteBuffer)request).toString()).getRootElement();
     }
     catch (DocumentException e)
     {
       throw new HiException(e);
     }
 
     NameValuePair[] pairs = new NameValuePair[root.elements().size()];
     int i = 0;
     Iterator iter = root.elementIterator();
     while (iter.hasNext()) {
       Element node = (Element)iter.next();
       pairs[(i++)] = new NameValuePair(node.getName(), node.getText());
     }
     method.setQueryString(pairs);
   }
 
   protected void setPostParams(PostMethod method, Object request) throws HiException
   {
     Element root;
     try {
       root = DocumentHelper.parseText(((HiByteBuffer)request).toString()).getRootElement();
     }
     catch (DocumentException e)
     {
       throw new HiException(e);
     }
     Iterator iter = root.elementIterator();
     while (iter.hasNext()) {
       Element node = (Element)iter.next();
       method.setParameter(node.getName(), node.getText());
     }
   }
 
   protected Object getResponseObject(byte[] responseBody) throws HiException {
     Element root = DocumentHelper.createElement("ROOT");
     String[] pairs = StringUtils.split(new String(responseBody), '&');
     for (int i = 0; i < pairs.length; ++i) {
       String[] names = StringUtils.split(pairs[i], '=');
 
       if (names.length == 2)
       {
         Element node;
         if (this._ignoreCase) {
           node = root.addElement(names[0].toUpperCase());
           node.setText(names[1]);
         } else {
           node = root.addElement(names[0]);
           node.setText(names[1]);
         }
       }
     }
     return new HiByteBuffer(root.asXML().getBytes());
   }
 }