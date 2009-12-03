/*     */ package com.hisun.web.action.other;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.web.action.BaseAction;
/*     */ import com.opensymphony.xwork2.ActionContext;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.apache.struts2.ServletActionContext;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class LoginAction extends BaseAction
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final String RSP_CD = "RSP_CD";
/*     */   private static final String PAG_ID = "PAG_ID";
/*     */   private static final String TXN_CD = "TXN_CD";
/*     */   private String userID;
/*     */   private String userPwd;
/*     */   private String CHKNO;
/*     */   private boolean rstPwdFlag;
/*     */   private Map pages;
/*     */   private Element menu;
/*     */   private Element vmenu;
/*     */   private Map pageMap;
/*     */   private Map actionMap;
/*     */   private ArrayList gids;
/*     */ 
/*     */   public LoginAction()
/*     */   {
/*  78 */     this.rstPwdFlag = false;
/*     */ 
/* 208 */     this.pages = new LinkedHashMap();
/* 209 */     this.menu = null;
/* 210 */     this.vmenu = null;
/*     */ 
/* 213 */     this.pageMap = new LinkedHashMap();
/* 214 */     this.actionMap = new LinkedHashMap();
/*     */ 
/* 278 */     this.gids = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void setUserID(String userID)
/*     */   {
/*  55 */     this.userID = userID;
/*     */   }
/*     */ 
/*     */   public String getUserID() {
/*  59 */     return this.userID;
/*     */   }
/*     */ 
/*     */   public void setUserPwd(String userPwd) {
/*  63 */     this.userPwd = userPwd;
/*     */   }
/*     */ 
/*     */   public String getUserPwd() {
/*  67 */     return this.userPwd;
/*     */   }
/*     */ 
/*     */   public String getCHKNO() {
/*  71 */     return this.CHKNO;
/*     */   }
/*     */ 
/*     */   public void setCHKNO(String chkno) {
/*  75 */     this.CHKNO = chkno;
/*     */   }
/*     */ 
/*     */   public String execute()
/*     */     throws Exception
/*     */   {
/*  88 */     if (this.rstPwdFlag) {
/*  89 */       this.output = "/p_usermng/loginresetpwd.jsp";
/*     */     }
/*  91 */     return super.execute();
/*     */   }
/*     */ 
/*     */   protected HiETF beforeProcess(HttpServletRequest request, Logger _log) throws HiException {
/*  95 */     HiETF etf = HiETFFactory.createETF();
/*     */ 
/*  97 */     String ip = request.getRemoteAddr();
/*     */ 
/*  99 */     etf.setChildValue("UID", this.userID);
/* 100 */     etf.setChildValue("UPWD", this.userPwd);
/* 101 */     etf.setChildValue("U_IP", ip);
/*     */ 
/* 103 */     return etf;
/*     */   }
/*     */ 
/*     */   protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log) {
/* 107 */     String rspCod = rspetf.getChildValue("RSP_CD");
/*     */ 
/* 109 */     String mecid = rspetf.getChildValue("MEC_ID");
/* 110 */     String mblno = rspetf.getChildValue("MBL_NO");
/* 111 */     if ((StringUtils.isEmpty(mblno)) && (StringUtils.isEmpty(mecid))) {
/* 112 */       request.getSession(true).setAttribute("UID", this.userID);
/*     */     }
/* 114 */     else if (StringUtils.isEmpty(mecid)) {
/* 115 */       request.getSession(true).setAttribute("UID", mblno);
/*     */     }
/* 117 */     else if (StringUtils.isEmpty(mblno)) {
/* 118 */       request.getSession(true).setAttribute("UID", mecid);
/*     */     }
/* 120 */     request.getSession(true).setAttribute("BR_NO", rspetf.getChildValue("BR_NO"));
/*     */ 
/* 122 */     if (StringUtils.equals(rspCod, "MNG0900204"))
/*     */     {
/* 125 */       String brno = rspetf.getChildValue("BR_NO");
/*     */ 
/* 127 */       this.rstPwdFlag = true;
/* 128 */       return false;
/*     */     }
/* 130 */     if (!(StringUtils.equals(rspCod, "000000")))
/*     */     {
/* 133 */       return false;
/*     */     }
/*     */ 
/* 136 */     int recNum = NumberUtils.toInt(rspetf.getChildValue(getRecnum()));
/* 137 */     if (recNum == 0)
/*     */     {
/* 141 */       return false;
/*     */     }
/*     */ 
/* 146 */     HiETF grpEtf = null;
/* 147 */     Set pageAccSet = new HashSet();
/* 148 */     Set actAccSet = new HashSet();
/* 149 */     for (int i = 1; i <= recNum; ++i)
/*     */     {
/* 151 */       grpEtf = rspetf.getChildNode("grp_" + i);
/* 152 */       if (grpEtf == null)
/*     */         continue;
/* 154 */       pageAccSet.add(grpEtf.getChildValue("PAG_ID"));
/* 155 */       actAccSet.add(grpEtf.getChildValue("TXN_CD"));
/*     */     }
/*     */ 
/* 159 */     setPermission(pageAccSet, actAccSet, getUserID());
/*     */ 
/* 162 */     readmenuconfig(_log);
/*     */ 
/* 171 */     return super.endProcess(request, rspetf, _log);
/*     */   }
/*     */ 
/*     */   private void readmenuconfig(Logger _log)
/*     */   {
/* 176 */     SAXReader reader = new SAXReader();
/* 177 */     Document doc = null;
/*     */     try {
/* 179 */       ServletContext context = (ServletContext)ActionContext.getContext().get("com.opensymphony.xwork2.dispatcher.ServletContext");
/* 180 */       InputStream in = context.getResourceAsStream("/conf/menuconfig.xml");
/* 181 */       doc = reader.read(in);
/*     */     } catch (DocumentException e) {
/* 183 */       e.printStackTrace();
/* 184 */       return;
/*     */     }
/*     */ 
/* 187 */     if (doc == null) return;
/*     */ 
/* 189 */     Element root = doc.getRootElement();
/*     */ 
/* 191 */     Iterator iter = root.element("Pages").elementIterator();
/* 192 */     while (iter.hasNext()) {
/* 193 */       Element el = (Element)iter.next();
/* 194 */       String id = el.attributeValue("id");
/* 195 */       this.pages.put(id, el);
/*     */     }
/*     */ 
/* 198 */     this.menu = root.element("Menu");
/* 199 */     this.vmenu = root.element("MenuShortCut");
/*     */ 
/* 201 */     Map appmap = ServletActionContext.getContext().getApplication();
/* 202 */     appmap.put("pages", this.pages);
/* 203 */     appmap.put("menu", this.menu);
/* 204 */     appmap.put("usrpage", this.pageMap);
/* 205 */     appmap.put("usraction", this.actionMap);
/*     */   }
/*     */ 
/*     */   public String getMenu()
/*     */   {
/* 223 */     StringBuffer buf = new StringBuffer(1024);
/* 224 */     buf.append("<ul id=\"menu\">");
/* 225 */     for (Iterator i = this.menu.elementIterator(); i.hasNext(); ) {
/* 226 */       Element element = (Element)i.next();
/* 227 */       appendItem(buf, element);
/*     */     }
/* 229 */     buf.append("</ul>");
/* 230 */     String ret = buf.toString();
/* 231 */     return ret;
/*     */   }
/*     */ 
/*     */   private boolean appendItem(StringBuffer buf, Element element)
/*     */   {
/* 236 */     boolean append = false;
/* 237 */     if (element.getName().equals("Group")) {
/* 238 */       StringBuffer innerbuf = new StringBuffer();
/* 239 */       for (Iterator i = element.elementIterator(); i.hasNext(); ) {
/* 240 */         Element e = (Element)i.next();
/* 241 */         append |= appendItem(innerbuf, e);
/*     */       }
/* 243 */       if (append) {
/* 244 */         buf.append("<li>");
/* 245 */         buf.append("<a href=\"");
/* 246 */         buf.append("javascript:dochange('" + element.attributeValue("name") + "')");
/* 247 */         buf.append("\">");
/* 248 */         buf.append(element.attributeValue("name"));
/* 249 */         buf.append("</a>");
/*     */ 
/* 256 */         buf.append("</li>");
/*     */ 
/* 258 */         buf.append("\r\n");
/*     */ 
/* 260 */         String s = element.attributeValue("name");
/* 261 */         if (!(this.gids.contains(s)))
/* 262 */           this.gids.add(s);
/*     */       }
/* 264 */     } else if (element.getName().equals("Item")) {
/* 265 */       String id = element.attributeValue("id");
/* 266 */       if ((getPagePermission(id)) && 
/* 268 */         (this.pages.containsKey(id)))
/*     */       {
/* 270 */         append = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 275 */     return append;
/*     */   }
/*     */ 
/*     */   public String getVmenu()
/*     */   {
/* 280 */     StringBuffer ret = new StringBuffer();
/* 281 */     for (int i = 0; i < this.gids.size(); ++i) {
/* 282 */       if (i == 0) {
/* 283 */         ret.append(showVMenu((String)this.gids.get(i), true));
/*     */       }
/*     */       else
/* 286 */         ret.append(showVMenu((String)this.gids.get(i), false));
/* 287 */       ret.append("\r\n");
/*     */     }
/*     */ 
/* 290 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   private String showVMenu(String gid, boolean show) {
/* 294 */     Element vmenu = getElementByName(gid);
/* 295 */     if (vmenu == null) {
/* 296 */       return "";
/*     */     }
/*     */ 
/* 299 */     StringBuffer buf = new StringBuffer(1024);
/* 300 */     if (show) {
/* 301 */       buf.append("<ul id=\"vmenu\" name=\"" + gid + "\" style=\"display:block\">");
/*     */     }
/*     */     else {
/* 304 */       buf.append("<ul id=\"vmenu\" name=\"" + gid + "\" style=\"display:none\">");
/*     */     }
/* 306 */     buf.append("\r\n");
/* 307 */     for (Iterator i = vmenu.elementIterator(); i.hasNext(); ) {
/* 308 */       Element element = (Element)i.next();
/*     */ 
/* 310 */       if (element.getName().equals("Item")) {
/* 311 */         String id = element.attributeValue("id");
/* 312 */         if ((getPagePermission(id)) && 
/* 313 */           (this.pages.containsKey(id))) {
/* 314 */           Element e = (Element)this.pages.get(id);
/* 315 */           buf.append("<li>");
/* 316 */           buf.append("<a href=\"");
/* 317 */           String url = e.attributeValue("url");
/*     */ 
/* 319 */           if ((!(StringUtils.isEmpty(url))) && (!("#".equals(url))))
/* 320 */             buf.append(url);
/*     */           else {
/* 322 */             buf.append(e.attributeValue("url"));
/*     */           }
/* 324 */           buf.append("\" target=mainWorkArea>");
/* 325 */           buf.append(e.attributeValue("name"));
/* 326 */           buf.append("</a>");
/* 327 */           buf.append("</li>");
/*     */ 
/* 329 */           buf.append("\r\n");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 335 */     buf.append("</ul>");
/* 336 */     buf.append("\r\n");
/* 337 */     String ret = buf.toString();
/* 338 */     return ret;
/*     */   }
/*     */ 
/*     */   private Element getElementByName(String name) {
/* 342 */     Element root = this.menu;
/* 343 */     for (Iterator i = root.elementIterator(); i.hasNext(); ) {
/* 344 */       Element el = (Element)i.next();
/* 345 */       if (name.equals(el.attributeValue("name"))) {
/* 346 */         return el;
/*     */       }
/*     */     }
/* 349 */     return null;
/*     */   }
/*     */ 
/*     */   public void setPermission(Set pageAcc, Set actAcc, String userid) {
/* 353 */     this.pageMap.put(userid, pageAcc);
/* 354 */     this.actionMap.put(userid, actAcc);
/*     */   }
/*     */ 
/*     */   public boolean getPagePermission(String pageid) {
/* 358 */     Set pageAccess = (Set)this.pageMap.get(getUserID());
/* 359 */     if (pageAccess != null) {
/* 360 */       return pageAccess.contains(pageid);
/*     */     }
/*     */ 
/* 363 */     return false;
/*     */   }
/*     */ }