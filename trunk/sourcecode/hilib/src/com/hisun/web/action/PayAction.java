/*     */ package com.hisun.web.action;
/*     */ 
/*     */ import com.hisun.common.HiETF2HashMapList;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.web.service.HiCallHostService;
/*     */ import com.hisun.web.service.HiLogFactory;
/*     */ import com.opensymphony.xwork2.ActionSupport;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.apache.struts2.ServletActionContext;
/*     */ 
/*     */ public class PayAction extends ActionSupport
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected HiCallHostService callHostService;
/*     */   protected HiLogFactory logFactory;
/*     */   protected String recnum;
/*     */   protected String txncode;
/*     */   protected String output;
/*     */   public Map source;
/*     */   public List group;
/*     */ 
/*     */   public PayAction()
/*     */   {
/*  38 */     this.callHostService = null;
/*  39 */     this.logFactory = null;
/*     */ 
/*  41 */     this.recnum = "REC_NUM";
/*  42 */     this.txncode = null;
/*  43 */     this.output = null;
/*     */ 
/*  85 */     this.source = new LinkedHashMap();
/*  86 */     this.group = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void setRecnum(String recnum)
/*     */   {
/*  46 */     this.recnum = recnum;
/*     */   }
/*     */ 
/*     */   public String getRecnum() {
/*  50 */     return this.recnum;
/*     */   }
/*     */ 
/*     */   public void setTxncode(String txncode) {
/*  54 */     this.txncode = txncode;
/*     */   }
/*     */ 
/*     */   public String getTxncode() {
/*  58 */     return this.txncode;
/*     */   }
/*     */ 
/*     */   public void setOutput(String output) {
/*  62 */     this.output = output;
/*     */   }
/*     */ 
/*     */   public String getOutput() {
/*  66 */     return this.output;
/*     */   }
/*     */ 
/*     */   public void setCallHostService(HiCallHostService callHostService) {
/*  70 */     this.callHostService = callHostService;
/*     */   }
/*     */ 
/*     */   public HiCallHostService getCallHostService() {
/*  74 */     return this.callHostService;
/*     */   }
/*     */ 
/*     */   public void setLogFactory(HiLogFactory logFactory) {
/*  78 */     this.logFactory = logFactory;
/*     */   }
/*     */ 
/*     */   public HiLogFactory getLogFactory() {
/*  82 */     return this.logFactory;
/*     */   }
/*     */ 
/*     */   public String execute()
/*     */     throws Exception
/*     */   {
/*  92 */     Logger _log = this.logFactory.getLogger();
/*  93 */     if (_log.isDebugEnabled())
/*  94 */       _log.debug("BaseAction execute starting");
/*  95 */     HttpServletRequest request = ServletActionContext.getRequest();
/*  96 */     HttpServletResponse response = ServletActionContext.getResponse();
/*  97 */     ServletContext context = ServletActionContext.getServletContext();
/*     */ 
/* 100 */     HiETF etf = beforeProcess(request, _log);
/*     */ 
/* 102 */     storeReqMsg(request, etf);
/*     */ 
/* 104 */     if (_log.isDebugEnabled()) {
/* 105 */       _log.debug("txncode : " + this.txncode + " output: " + this.output);
/*     */     }
/*     */ 
/* 108 */     HiETF rspetf = null;
/* 109 */     _log.info("start call host");
/* 110 */     if (!(StringUtils.isEmpty(this.txncode))) {
/*     */       try {
/* 112 */         rspetf = getCallHostService().callhost(this.txncode, etf, context);
/*     */       } catch (Throwable t) {
/* 114 */         _log.error(t, t);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 119 */       rspetf = etf;
/* 120 */       rspetf.setChildValue("RSP_CD", "000000");
/*     */     }
/* 122 */     _log.error("end call host");
/*     */ 
/* 125 */     boolean flag = endProcess(request, rspetf, _log);
/* 126 */     request.setAttribute("msg", rspetf.getChildValue("RSP_MSG"));
/*     */ 
/* 128 */     if (flag)
/*     */     {
/* 131 */       storeResult(request, rspetf);
/*     */ 
/* 134 */       if (this.output != null) {
/* 135 */         int index = StringUtils.indexOf(this.output, "action");
/* 136 */         if (index >= 0) {
/* 137 */           response.sendRedirect(this.output);
/*     */         }
/*     */         else {
/* 140 */           ServletActionContext.getServletContext().getRequestDispatcher(this.output).forward(request, response);
/*     */         }
/* 142 */         return "";
/*     */       }
/* 144 */       return "success";
/*     */     }
/*     */ 
/* 147 */     storeResult(request, rspetf);
/* 148 */     String error = request.getParameter("error");
/* 149 */     if (error != null) {
/* 150 */       request.getRequestDispatcher(error).forward(request, response);
/* 151 */       return "";
/*     */     }
/* 153 */     return "error";
/*     */   }
/*     */ 
/*     */   protected HiETF beforeProcess(HttpServletRequest request, Logger _log)
/*     */     throws HiException
/*     */   {
/* 162 */     Map map = request.getParameterMap();
/*     */ 
/* 164 */     HiETF etf = HiETFFactory.createETF();
/* 165 */     HttpSession session = request.getSession(true);
/*     */ 
/* 167 */     String userID = (String)session.getAttribute("UID");
/* 168 */     String brno = (String)session.getAttribute("BR_NO");
/*     */ 
/* 170 */     etf.setChildValue("USR_ID", userID);
/* 171 */     etf.setChildValue("U_BR_NO", brno);
/*     */ 
/* 173 */     for (Iterator i = map.keySet().iterator(); i.hasNext(); ) {
/* 174 */       String key = (String)i.next();
/* 175 */       String[] vals = (String[])(String[])map.get(key);
/* 176 */       for (int j = 0; j < vals.length; ++j) {
/* 177 */         if (!(vals[0].equals(""))) {
/* 178 */           etf.setGrandChildNode(key, vals[0]);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 184 */     String[] values = request.getParameterValues("checklist");
/* 185 */     if (values != null) {
/* 186 */       etf.setChildValue("PARANUM", Integer.toString(values.length));
/*     */ 
/* 188 */       for (int i = 0; i < values.length; ++i) {
/* 189 */         String val = values[i];
/* 190 */         String[] tmp = StringUtils.split(val, ":");
/* 191 */         etf.addNode("PARAMS_" + (i + 1)).setChildValue(tmp[0], tmp[1]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 196 */     return etf;
/*     */   }
/*     */ 
/*     */   protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log)
/*     */   {
/* 205 */     if (_log.isDebugEnabled()) _log.debug("process End rspetf: " + rspetf);
/* 206 */     if (rspetf == null) return false;
/*     */ 
/* 209 */     int num = NumberUtils.toInt(rspetf.getChildValue(this.recnum));
/*     */ 
/* 211 */     for (int j = 0; j < num; ++j) {
/* 212 */       HiETF node = rspetf.getChildNode("REC_" + (j + 1));
/* 213 */       if (node == null)
/*     */         break;
/* 215 */       Map m = new LinkedHashMap();
/* 216 */       for (Iterator i = node.getChildNodes().iterator(); i.hasNext(); ) {
/* 217 */         HiETF child = (HiETF)i.next();
/* 218 */         String key = child.getName();
/* 219 */         m.put(child.getName(), child.getValue());
/*     */       }
/* 221 */       this.group.add(m);
/*     */     }
/*     */ 
/* 225 */     for (Iterator i = rspetf.getChildNodes().iterator(); i.hasNext(); ) {
/* 226 */       HiETF child = (HiETF)i.next();
/* 227 */       String key = child.getName();
/*     */ 
/* 230 */       if (child.getChildNodes().isEmpty()) {
/* 231 */         this.source.put(key, child.getValue());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 236 */     String rspCd = rspetf.getChildValue("RSP_CD");
/*     */ 
/* 238 */     return (!(StringUtils.equalsIgnoreCase(rspCd, "000000")));
/*     */   }
/*     */ 
/*     */   protected void storeReqMsg(HttpServletRequest request, HiETF reqetf)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void storeResult(HttpServletRequest request, HiETF rspetf)
/*     */   {
/* 249 */     request.setAttribute("etfsrc", this.source);
/* 250 */     request.setAttribute("group", this.group);
/* 251 */     request.setAttribute("etf", rspetf);
/* 252 */     request.setAttribute("ETF", new HiETF2HashMapList(rspetf).map());
/*     */   }
/*     */ }