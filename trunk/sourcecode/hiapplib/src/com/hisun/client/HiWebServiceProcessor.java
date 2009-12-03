/*     */ package com.hisun.client;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.net.URL;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.ParameterMode;
/*     */ import org.apache.axis.client.Call;
/*     */ import org.apache.axis.client.Service;
/*     */ 
/*     */ public class HiWebServiceProcessor
/*     */   implements WebServiceProcessor
/*     */ {
/*  15 */   private String endpoint = "";
/*     */ 
/*  17 */   private String operator = "";
/*     */ 
/*  19 */   private String namespace = "";
/*     */ 
/*  21 */   private String actionURI = "";
/*     */ 
/*  23 */   private static Logger log = HiLog.getLogger("webservice.trc");
/*     */ 
/*  25 */   private static int SUCCEE = 100;
/*     */ 
/*  27 */   private static int ERROR = 500;
/*     */ 
/*     */   public Object send(WebServiceContext context)
/*     */   {
/*  34 */     Object o = null;
/*     */     try {
/*  36 */       this.endpoint = context.getEndpoint();
/*  37 */       log.debug("endpoint:" + this.endpoint);
/*  38 */       this.operator = context.getOperationName();
/*  39 */       log.debug("operator:" + this.operator);
/*  40 */       this.namespace = context.getNamespace();
/*  41 */       if (this.namespace == null) {
/*  42 */         this.namespace = "";
/*     */       }
/*  44 */       log.debug("namespace:" + this.namespace);
/*  45 */       this.actionURI = context.getActionURL();
/*  46 */       log.debug("actionURI:" + this.actionURI);
/*  47 */       Service service = new Service();
/*     */ 
/*  50 */       Call call = (Call)service.createCall();
/*  51 */       call.setTargetEndpointAddress(new URL(this.endpoint));
/*  52 */       if ((this.actionURI != null) && (this.actionURI.length() != 0)) {
/*  53 */         call.setUseSOAPAction(true);
/*  54 */         call.setSOAPActionURI(this.actionURI);
/*     */       }
/*  56 */       RequestParam rp = null;
/*  57 */       int paramSize = context.requestParamSize();
/*  58 */       Object[] params = new Object[paramSize];
/*  59 */       for (int i = 0; i < context.requestParamSize(); ++i) {
/*  60 */         rp = context.getRequestParam(i);
/*  61 */         call.addParameter(new QName(this.namespace, rp.getName()), new QName("http://www.w3.org/2001/XMLSchema", "string"), ParameterMode.IN);
/*     */ 
/*  66 */         params[(rp.getIndex() - 1)] = rp.getValue();
/*     */       }
/*  68 */       call.setOperationName(new QName(this.namespace, this.operator));
/*  69 */       call.setTimeout(new Integer(20000));
/*  70 */       o = call.invoke(params);
/*  71 */       log.info("收到回复报文[" + o + "]");
/*     */     } catch (Exception e) {
/*  73 */       return createResponse(ERROR, e.getMessage());
/*     */     }
/*  75 */     return createResponse(SUCCEE, o);
/*     */   }
/*     */ 
/*     */   private Object createResponse(int status, Object o) {
/*  79 */     if (o instanceof String) {
/*  80 */       String os = (String)o;
/*  81 */       StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
/*     */ 
/*  83 */       sb.append("<Root>\n");
/*     */ 
/*  85 */       sb.append("<namespace>" + this.namespace + "</namespace>\n");
/*  86 */       sb.append("<endpoint>" + this.endpoint + "</endpoint>\n");
/*  87 */       sb.append("<actionURL>" + this.actionURI + "</actionURL>\n");
/*  88 */       sb.append("<operationName>" + this.operator + "</operationName>\n");
/*  89 */       sb.append("<status>" + status + "</status>\n");
/*  90 */       int bint = -1;
/*  91 */       int eint = -1;
/*  92 */       bint = os.indexOf("<?");
/*  93 */       eint = os.indexOf("?>");
/*  94 */       if ((bint != -1) && (eint != -1)) {
/*  95 */         os = os.substring(eint + 2);
/*     */       }
/*  97 */       sb.append("<param>" + os + "</param>\n");
/*  98 */       sb.append("</Root>\n");
/*  99 */       return sb.toString();
/*     */     }
/* 101 */     return createResponse(ERROR, "不支持的返回类型");
/*     */   }
/*     */ }