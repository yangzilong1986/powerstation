/*     */ package com.hisun.client;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.Node;
/*     */ 
/*     */ public class HiWebServiceContext
/*     */   implements WebServiceContext
/*     */ {
/*     */   private static final String NAMESPACE = "namespace";
/*     */   private static final String ENDPOINT = "endpoint";
/*     */   private static final String ACTIONURL = "actionURL";
/*     */   private static final String OPERATION_NAME = "operationName";
/*     */   private static final String PARAM = "param";
/*     */   private static final String NAME = "name";
/*     */   private String processor;
/*     */   private String namespace;
/*     */   private String endpoint;
/*     */   private String actionURL;
/*     */   private String operationName;
/*  40 */   private ArrayList params = new ArrayList();
/*     */ 
/*     */   public void parse(String bd)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static WebServiceContext createContext(String string) {
/*  47 */     HiWebServiceContext context = new HiWebServiceContext();
/*     */     try
/*     */     {
/*  50 */       Document document = DocumentHelper.parseText(string);
/*  51 */       Element root = document.getRootElement();
/*  52 */       Iterator elements = root.elements().iterator();
/*     */ 
/*  54 */       int paramCount = 0;
/*  55 */       while (elements.hasNext()) {
/*  56 */         Element element = (Element)elements.next();
/*  57 */         String name = element.getName();
/*  58 */         if (name.equals("endpoint")) {
/*  59 */           context.setEndpoint(element.getTextTrim());
/*  60 */         } else if (name.equals("namespace")) {
/*  61 */           context.setNamespace(element.getTextTrim());
/*  62 */         } else if (name.equals("actionURL")) {
/*  63 */           context.setActionURL(element.getTextTrim());
/*  64 */         } else if (name.equals("operationName")) {
/*  65 */           context.setOperationName(element.getTextTrim());
/*  66 */         } else if (name.equals("param")) {
/*  67 */           ++paramCount;
/*  68 */           HiRquestParam rp = new HiRquestParam();
/*  69 */           rp.setName(element.attributeValue("name"));
/*  70 */           rp.setIndex(paramCount);
/*  71 */           if (element.isTextOnly()) {
/*  72 */             rp.setValue(element.getTextTrim());
/*     */           } else {
/*  74 */             Node node = element.node(0);
/*  75 */             String encoding = document.getXMLEncoding();
/*  76 */             if ((encoding == null) || (encoding.length() == 0)) {
/*  77 */               encoding = "UTF-8";
/*     */             }
/*  79 */             String value = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>" + node.asXML();
/*     */ 
/*  81 */             rp.setValue(value);
/*     */           }
/*     */ 
/*  84 */           context.addRequestParam(rp);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (DocumentException e)
/*     */     {
/*  90 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  93 */     return context;
/*     */   }
/*     */ 
/*     */   private void addRequestParam(HiRquestParam param) {
/*  97 */     this.params.add(param);
/*     */   }
/*     */ 
/*     */   public String getProcessor()
/*     */   {
/* 102 */     return this.processor;
/*     */   }
/*     */ 
/*     */   public void setProcessor(String processor) {
/* 106 */     this.processor = processor;
/*     */   }
/*     */ 
/*     */   public String getNamespace() {
/* 110 */     return this.namespace;
/*     */   }
/*     */ 
/*     */   public void setNamespace(String namespace) {
/* 114 */     this.namespace = namespace;
/*     */   }
/*     */ 
/*     */   public String getEndpoint() {
/* 118 */     return this.endpoint;
/*     */   }
/*     */ 
/*     */   public void setEndpoint(String endpoint) {
/* 122 */     this.endpoint = endpoint;
/*     */   }
/*     */ 
/*     */   public String getActionURL() {
/* 126 */     return this.actionURL;
/*     */   }
/*     */ 
/*     */   public void setActionURL(String actionURL) {
/* 130 */     this.actionURL = actionURL;
/*     */   }
/*     */ 
/*     */   public String getOperationName() {
/* 134 */     return this.operationName;
/*     */   }
/*     */ 
/*     */   public void setOperationName(String operationName) {
/* 138 */     this.operationName = operationName;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 142 */     StringBuilder sb = new StringBuilder(" [WebServiceContext :");
/* 143 */     sb.append("processor=" + this.processor);
/* 144 */     sb.append(";namespace=" + this.namespace);
/* 145 */     sb.append(";endpoint=" + this.endpoint);
/* 146 */     sb.append(";actionURL=" + this.actionURL);
/* 147 */     sb.append(";operationName=" + this.operationName);
/* 148 */     if (this.params.size() != 0) {
/* 149 */       sb.append(";request =");
/*     */     }
/* 151 */     for (int x = 0; x < this.params.size(); ++x) {
/* 152 */       sb.append(this.params.get(x));
/*     */     }
/* 154 */     return " ]";
/*     */   }
/*     */ 
/*     */   public RequestParam getRequestParam(int index)
/*     */   {
/* 159 */     return ((RequestParam)this.params.get(index));
/*     */   }
/*     */ 
/*     */   public int requestParamSize() {
/* 163 */     return this.params.size();
/*     */   }
/*     */ 
/*     */   public static void main(String[] ss)
/*     */   {
/* 168 */     String s2 = "<?xml version ='1.0' encoding='UTF-8'?><Root><namespace>www.hisun.com</namespace><endpoint>www.hisun.com/endpoint.jws</endpoint><actionURL>www.hisun.com/test</actionURL>\t<operationName>forTest</operationName><param name =\"path\">PATHPATH</param>\t<param name =\"dataXmlStr\"><DBSET><R><C N=\"DSDW\">01</C><C N=\"JYLSH\">02</C>\t<C N=\"HSDW\">02</C><C N=\"DZWJM\">DZ50B10520080920103000.txt</C><C N=\"JYBS\">10</C><C N=\"JYJE\">3543546.49</C><C N=\"DZLB\">0</C></R></DBSET></param></Root>";
/*     */ 
/* 177 */     HiWebServiceContext hws = (HiWebServiceContext)createContext(s2);
/*     */ 
/* 179 */     System.out.println(hws);
/*     */   }
/*     */ }