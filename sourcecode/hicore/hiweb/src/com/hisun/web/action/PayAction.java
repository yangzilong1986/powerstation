 package com.hisun.web.action;

 import com.hisun.common.HiETF2HashMapList;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.web.service.HiCallHostService;
 import com.hisun.web.service.HiLogFactory;
 import com.opensymphony.xwork2.ActionSupport;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.apache.struts2.ServletActionContext;

 import javax.servlet.RequestDispatcher;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import java.util.*;
 
 public class PayAction extends ActionSupport
 {
   private static final long serialVersionUID = 1L;
   protected HiCallHostService callHostService;
   protected HiLogFactory logFactory;
   protected String recnum;
   protected String txncode;
   protected String output;
   public Map source;
   public List group;
 
   public PayAction()
   {
     this.callHostService = null;
     this.logFactory = null;
 
     this.recnum = "REC_NUM";
     this.txncode = null;
     this.output = null;
 
     this.source = new LinkedHashMap();
     this.group = new ArrayList();
   }
 
   public void setRecnum(String recnum)
   {
     this.recnum = recnum;
   }
 
   public String getRecnum() {
     return this.recnum;
   }
 
   public void setTxncode(String txncode) {
     this.txncode = txncode;
   }
 
   public String getTxncode() {
     return this.txncode;
   }
 
   public void setOutput(String output) {
     this.output = output;
   }
 
   public String getOutput() {
     return this.output;
   }
 
   public void setCallHostService(HiCallHostService callHostService) {
     this.callHostService = callHostService;
   }
 
   public HiCallHostService getCallHostService() {
     return this.callHostService;
   }
 
   public void setLogFactory(HiLogFactory logFactory) {
     this.logFactory = logFactory;
   }
 
   public HiLogFactory getLogFactory() {
     return this.logFactory;
   }
 
   public String execute()
     throws Exception
   {
     Logger _log = this.logFactory.getLogger();
     if (_log.isDebugEnabled())
       _log.debug("BaseAction execute starting");
     HttpServletRequest request = ServletActionContext.getRequest();
     HttpServletResponse response = ServletActionContext.getResponse();
     ServletContext context = ServletActionContext.getServletContext();
 
     HiETF etf = beforeProcess(request, _log);
 
     storeReqMsg(request, etf);
 
     if (_log.isDebugEnabled()) {
       _log.debug("txncode : " + this.txncode + " output: " + this.output);
     }
 
     HiETF rspetf = null;
     _log.info("start call host");
     if (!(StringUtils.isEmpty(this.txncode))) {
       try {
         rspetf = getCallHostService().callhost(this.txncode, etf, context);
       } catch (Throwable t) {
         _log.error(t, t);
       }
     }
     else
     {
       rspetf = etf;
       rspetf.setChildValue("RSP_CD", "000000");
     }
     _log.error("end call host");
 
     boolean flag = endProcess(request, rspetf, _log);
     request.setAttribute("msg", rspetf.getChildValue("RSP_MSG"));
 
     if (flag)
     {
       storeResult(request, rspetf);
 
       if (this.output != null) {
         int index = StringUtils.indexOf(this.output, "action");
         if (index >= 0) {
           response.sendRedirect(this.output);
         }
         else {
           ServletActionContext.getServletContext().getRequestDispatcher(this.output).forward(request, response);
         }
         return "";
       }
       return "success";
     }
 
     storeResult(request, rspetf);
     String error = request.getParameter("error");
     if (error != null) {
       request.getRequestDispatcher(error).forward(request, response);
       return "";
     }
     return "error";
   }
 
   protected HiETF beforeProcess(HttpServletRequest request, Logger _log)
     throws HiException
   {
     Map map = request.getParameterMap();
 
     HiETF etf = HiETFFactory.createETF();
     HttpSession session = request.getSession(true);
 
     String userID = (String)session.getAttribute("UID");
     String brno = (String)session.getAttribute("BR_NO");
 
     etf.setChildValue("USR_ID", userID);
     etf.setChildValue("U_BR_NO", brno);
 
     for (Iterator i = map.keySet().iterator(); i.hasNext(); ) {
       String key = (String)i.next();
       String[] vals = (String[])(String[])map.get(key);
       for (int j = 0; j < vals.length; ++j) {
         if (!(vals[0].equals(""))) {
           etf.setGrandChildNode(key, vals[0]);
         }
       }
 
     }
 
     String[] values = request.getParameterValues("checklist");
     if (values != null) {
       etf.setChildValue("PARANUM", Integer.toString(values.length));
 
       for (int i = 0; i < values.length; ++i) {
         String val = values[i];
         String[] tmp = StringUtils.split(val, ":");
         etf.addNode("PARAMS_" + (i + 1)).setChildValue(tmp[0], tmp[1]);
       }
 
     }
 
     return etf;
   }
 
   protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log)
   {
     if (_log.isDebugEnabled()) _log.debug("process End rspetf: " + rspetf);
     if (rspetf == null) return false;
 
     int num = NumberUtils.toInt(rspetf.getChildValue(this.recnum));
 
     for (int j = 0; j < num; ++j) {
       HiETF node = rspetf.getChildNode("REC_" + (j + 1));
       if (node == null)
         break;
       Map m = new LinkedHashMap();
       for (Iterator i = node.getChildNodes().iterator(); i.hasNext(); ) {
         HiETF child = (HiETF)i.next();
         String key = child.getName();
         m.put(child.getName(), child.getValue());
       }
       this.group.add(m);
     }
 
     for (Iterator i = rspetf.getChildNodes().iterator(); i.hasNext(); ) {
       HiETF child = (HiETF)i.next();
       String key = child.getName();
 
       if (child.getChildNodes().isEmpty()) {
         this.source.put(key, child.getValue());
       }
 
     }
 
     String rspCd = rspetf.getChildValue("RSP_CD");
 
     return (!(StringUtils.equalsIgnoreCase(rspCd, "000000")));
   }
 
   protected void storeReqMsg(HttpServletRequest request, HiETF reqetf)
   {
   }
 
   protected void storeResult(HttpServletRequest request, HiETF rspetf)
   {
     request.setAttribute("etfsrc", this.source);
     request.setAttribute("group", this.group);
     request.setAttribute("etf", rspetf);
     request.setAttribute("ETF", new HiETF2HashMapList(rspetf).map());
   }
 }