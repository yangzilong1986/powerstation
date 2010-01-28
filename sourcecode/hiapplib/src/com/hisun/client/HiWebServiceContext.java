 package com.hisun.client;
 
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 import org.dom4j.Node;
 
 public class HiWebServiceContext
   implements WebServiceContext
 {
   private static final String NAMESPACE = "namespace";
   private static final String ENDPOINT = "endpoint";
   private static final String ACTIONURL = "actionURL";
   private static final String OPERATION_NAME = "operationName";
   private static final String PARAM = "param";
   private static final String NAME = "name";
   private String processor;
   private String namespace;
   private String endpoint;
   private String actionURL;
   private String operationName;
   private ArrayList params = new ArrayList();
 
   public void parse(String bd)
   {
   }
 
   public static WebServiceContext createContext(String string) {
     HiWebServiceContext context = new HiWebServiceContext();
     try
     {
       Document document = DocumentHelper.parseText(string);
       Element root = document.getRootElement();
       Iterator elements = root.elements().iterator();
 
       int paramCount = 0;
       while (elements.hasNext()) {
         Element element = (Element)elements.next();
         String name = element.getName();
         if (name.equals("endpoint")) {
           context.setEndpoint(element.getTextTrim());
         } else if (name.equals("namespace")) {
           context.setNamespace(element.getTextTrim());
         } else if (name.equals("actionURL")) {
           context.setActionURL(element.getTextTrim());
         } else if (name.equals("operationName")) {
           context.setOperationName(element.getTextTrim());
         } else if (name.equals("param")) {
           ++paramCount;
           HiRquestParam rp = new HiRquestParam();
           rp.setName(element.attributeValue("name"));
           rp.setIndex(paramCount);
           if (element.isTextOnly()) {
             rp.setValue(element.getTextTrim());
           } else {
             Node node = element.node(0);
             String encoding = document.getXMLEncoding();
             if ((encoding == null) || (encoding.length() == 0)) {
               encoding = "UTF-8";
             }
             String value = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>" + node.asXML();
 
             rp.setValue(value);
           }
 
           context.addRequestParam(rp);
         }
       }
     }
     catch (DocumentException e)
     {
       e.printStackTrace();
     }
 
     return context;
   }
 
   private void addRequestParam(HiRquestParam param) {
     this.params.add(param);
   }
 
   public String getProcessor()
   {
     return this.processor;
   }
 
   public void setProcessor(String processor) {
     this.processor = processor;
   }
 
   public String getNamespace() {
     return this.namespace;
   }
 
   public void setNamespace(String namespace) {
     this.namespace = namespace;
   }
 
   public String getEndpoint() {
     return this.endpoint;
   }
 
   public void setEndpoint(String endpoint) {
     this.endpoint = endpoint;
   }
 
   public String getActionURL() {
     return this.actionURL;
   }
 
   public void setActionURL(String actionURL) {
     this.actionURL = actionURL;
   }
 
   public String getOperationName() {
     return this.operationName;
   }
 
   public void setOperationName(String operationName) {
     this.operationName = operationName;
   }
 
   public String toString() {
     StringBuilder sb = new StringBuilder(" [WebServiceContext :");
     sb.append("processor=" + this.processor);
     sb.append(";namespace=" + this.namespace);
     sb.append(";endpoint=" + this.endpoint);
     sb.append(";actionURL=" + this.actionURL);
     sb.append(";operationName=" + this.operationName);
     if (this.params.size() != 0) {
       sb.append(";request =");
     }
     for (int x = 0; x < this.params.size(); ++x) {
       sb.append(this.params.get(x));
     }
     return " ]";
   }
 
   public RequestParam getRequestParam(int index)
   {
     return ((RequestParam)this.params.get(index));
   }
 
   public int requestParamSize() {
     return this.params.size();
   }
 
   public static void main(String[] ss)
   {
     String s2 = "<?xml version ='1.0' encoding='UTF-8'?><Root><namespace>www.hisun.com</namespace><endpoint>www.hisun.com/endpoint.jws</endpoint><actionURL>www.hisun.com/test</actionURL>\t<operationName>forTest</operationName><param name =\"path\">PATHPATH</param>\t<param name =\"dataXmlStr\"><DBSET><R><C N=\"DSDW\">01</C><C N=\"JYLSH\">02</C>\t<C N=\"HSDW\">02</C><C N=\"DZWJM\">DZ50B10520080920103000.txt</C><C N=\"JYBS\">10</C><C N=\"JYJE\">3543546.49</C><C N=\"DZLB\">0</C></R></DBSET></param></Root>";
 
     HiWebServiceContext hws = (HiWebServiceContext)createContext(s2);
 
     System.out.println(hws);
   }
 }