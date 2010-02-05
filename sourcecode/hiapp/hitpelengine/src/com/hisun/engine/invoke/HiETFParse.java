 package com.hisun.engine.invoke;
 
 import com.hisun.message.HiETF;
 import com.hisun.message.HiXmlETF;
 import java.util.Stack;
 import org.dom4j.Document;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 class HiETFParse
 {
   public static HiETF parseTextETF(String data)
   {
     return new HiXmlETF(parseText(data));
   }
 
   public static Element parseText(String data) {
     Document doc = DocumentHelper.createDocument();
     Element root = parse(data);
     doc.setRootElement(root);
     return root;
   }
 
   private static Element parse(String data) {
     int state = 0;
     StringBuffer tmp = new StringBuffer();
     Stack nodeStack = new Stack();
     Element root = null;
 
     for (int i = 0; i < data.length(); )
     {
       Element tmpNod;
       if (state == 0) {
         if (Character.isWhitespace(data.charAt(i))) {
           ++i;
         }
 
         state = 1; }
       if (state == 1)
       {
         if (data.charAt(i) == '<') {
           if (data.charAt(i + 1) == '/')
           {
             state = 3;
           }
           ++i;
         }
 
         if ((data.charAt(i) == '/') && 
           (data.charAt(i + 1) == '>'))
         {
           tmpNod = DocumentHelper.createElement(tmp.toString());
           tmp.setLength(0);
 
           if (nodeStack.isEmpty()) {
             root = tmpNod;
           }
           else if (!(nodeStack.isEmpty())) {
             ((Element)nodeStack.peek()).add(tmpNod);
           }
 
           state = 0;
           i += 2;
         }
 
         if (data.charAt(i) == '>')
         {
           tmpNod = DocumentHelper.createElement(tmp.toString());
           if (nodeStack.isEmpty()) {
             root = tmpNod;
           }
           nodeStack.push(tmpNod);
           state = 2;
           tmp.setLength(0);
           ++i;
         }
 
         tmp.append(data.charAt(i));
         ++i; }
       if (state == 2)
       {
         if (data.charAt(i) == '<') {
           if (data.charAt(i + 1) == '/')
           {
             if (nodeStack.isEmpty()) {
               throw new RuntimeException("invalid:{" + data + "}");
             }
             Element node = (Element)nodeStack.peek();
             node.setText(tmp.toString());
             tmp.setLength(0);
             state = 3;
           }
 
           state = 0;
         }
 
         tmp.append(data.charAt(i));
         ++i; }
       if (state != 3)
         continue;
       if (data.charAt(i) == '>') {
         if (nodeStack.isEmpty()) {
           throw new RuntimeException("invalid:{" + data + "}");
         }
         tmpNod = (Element)nodeStack.pop();
         if (!(nodeStack.isEmpty())) {
           ((Element)nodeStack.peek()).add(tmpNod);
         }
         state = 0;
       }
       ++i;
     }
 
     return root;
   }
 }