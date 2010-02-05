 package com.hisun.client;
 
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import java.net.URL;
 import javax.xml.namespace.QName;
 import javax.xml.rpc.ParameterMode;
 import org.apache.axis.client.Call;
 import org.apache.axis.client.Service;
 
 public class HiWebServiceProcessor
   implements WebServiceProcessor
 {
   private String endpoint = "";
 
   private String operator = "";
 
   private String namespace = "";
 
   private String actionURI = "";
 
   private static Logger log = HiLog.getLogger("webservice.trc");
 
   private static int SUCCEE = 100;
 
   private static int ERROR = 500;
 
   public Object send(WebServiceContext context)
   {
     Object o = null;
     try {
       this.endpoint = context.getEndpoint();
       log.debug("endpoint:" + this.endpoint);
       this.operator = context.getOperationName();
       log.debug("operator:" + this.operator);
       this.namespace = context.getNamespace();
       if (this.namespace == null) {
         this.namespace = "";
       }
       log.debug("namespace:" + this.namespace);
       this.actionURI = context.getActionURL();
       log.debug("actionURI:" + this.actionURI);
       Service service = new Service();
 
       Call call = (Call)service.createCall();
       call.setTargetEndpointAddress(new URL(this.endpoint));
       if ((this.actionURI != null) && (this.actionURI.length() != 0)) {
         call.setUseSOAPAction(true);
         call.setSOAPActionURI(this.actionURI);
       }
       RequestParam rp = null;
       int paramSize = context.requestParamSize();
       Object[] params = new Object[paramSize];
       for (int i = 0; i < context.requestParamSize(); ++i) {
         rp = context.getRequestParam(i);
         call.addParameter(new QName(this.namespace, rp.getName()), new QName("http://www.w3.org/2001/XMLSchema", "string"), ParameterMode.IN);
 
         params[(rp.getIndex() - 1)] = rp.getValue();
       }
       call.setOperationName(new QName(this.namespace, this.operator));
       call.setTimeout(new Integer(20000));
       o = call.invoke(params);
       log.info("收到回复报文[" + o + "]");
     } catch (Exception e) {
       return createResponse(ERROR, e.getMessage());
     }
     return createResponse(SUCCEE, o);
   }
 
   private Object createResponse(int status, Object o) {
     if (o instanceof String) {
       String os = (String)o;
       StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
 
       sb.append("<Root>\n");
 
       sb.append("<namespace>" + this.namespace + "</namespace>\n");
       sb.append("<endpoint>" + this.endpoint + "</endpoint>\n");
       sb.append("<actionURL>" + this.actionURI + "</actionURL>\n");
       sb.append("<operationName>" + this.operator + "</operationName>\n");
       sb.append("<status>" + status + "</status>\n");
       int bint = -1;
       int eint = -1;
       bint = os.indexOf("<?");
       eint = os.indexOf("?>");
       if ((bint != -1) && (eint != -1)) {
         os = os.substring(eint + 2);
       }
       sb.append("<param>" + os + "</param>\n");
       sb.append("</Root>\n");
       return sb.toString();
     }
     return createResponse(ERROR, "不支持的返回类型");
   }
 }