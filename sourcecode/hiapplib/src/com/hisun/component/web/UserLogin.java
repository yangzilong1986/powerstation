/*     */ package com.hisun.component.web;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class UserLogin
/*     */ {
/*     */   private Logger log;
/*     */   private Map pages;
/*     */   private Element menu;
/*     */   private Element vmenu;
/*     */   private Map pageMap;
/*     */   private Map actionMap;
/*     */   private ArrayList gids;
/*     */ 
/*     */   public UserLogin()
/*     */   {
/*  36 */     this.log = HiLog.getLogger("login.trc");
/*     */ 
/* 107 */     this.pages = new LinkedHashMap();
/* 108 */     this.menu = null;
/* 109 */     this.vmenu = null;
/*     */ 
/* 111 */     this.pageMap = new LinkedHashMap();
/* 112 */     this.actionMap = new LinkedHashMap();
/*     */ 
/* 181 */     this.gids = new ArrayList();
/*     */   }
/*     */ 
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  39 */     authProc(ctx);
/*  40 */     return 0;
/*     */   }
/*     */ 
/*     */   protected boolean authProc(HiMessageContext ctx) throws HiException {
/*  44 */     HiMessage msg = ctx.getCurrentMsg();
/*  45 */     HiETF root = msg.getETFBody();
/*  46 */     int recNum = NumberUtils.toInt(root.getChildValue("REC_NUM"));
/*  47 */     Logger log = HiLog.getLogger(msg);
/*  48 */     log.info("REC_NUM:[" + recNum + "]");
/*  49 */     if (recNum == 0)
/*     */     {
/*  51 */       return false;
/*     */     }
/*  53 */     Set pageAccSet = new HashSet();
/*  54 */     Set actAccSet = new HashSet();
/*  55 */     for (int i = 1; i <= recNum; ++i) {
/*  56 */       HiETF grpEtf = root.getChildNode("GRP_" + i);
/*  57 */       if (grpEtf != null) {
/*  58 */         pageAccSet.add(grpEtf.getChildValue("PAG_ID"));
/*  59 */         actAccSet.add(grpEtf.getChildValue("TXN_CD"));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  64 */     setPermission(pageAccSet, actAccSet, getUserID());
/*     */ 
/*  67 */     readmenuconfig(ctx);
/*  68 */     String menu = getMenu();
/*     */ 
/*  70 */     String vmenu = getVmenu();
/*  71 */     msg.getETFBody().setChildValue("MENU", menu);
/*  72 */     msg.getETFBody().setChildValue("VMENU", vmenu);
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */   private void readmenuconfig(HiMessageContext ctx) throws HiException {
/*  77 */     this.log.info("readmenuconfig");
/*  78 */     SAXReader reader = new SAXReader();
/*  79 */     Document doc = null;
/*  80 */     HiMessage msg = ctx.getCurrentMsg();
/*  81 */     ServletContext servletCtx = (ServletContext)msg.getObjectHeadItem("_WEB_APPLICATION");
/*     */     try
/*     */     {
/*  84 */       InputStream in = servletCtx.getResourceAsStream("/conf/menuconfig.xml");
/*     */ 
/*  86 */       doc = reader.read(in);
/*     */     } catch (DocumentException e) {
/*  88 */       throw new HiException(e);
/*     */     }
/*     */ 
/*  91 */     if (doc == null) {
/*  92 */       return;
/*     */     }
/*  94 */     Element root = doc.getRootElement();
/*     */ 
/*  96 */     Iterator iter = root.element("Pages").elementIterator();
/*  97 */     while (iter.hasNext()) {
/*  98 */       Element el = (Element)iter.next();
/*  99 */       String id = el.attributeValue("id");
/* 100 */       this.pages.put(id, el);
/*     */     }
/*     */ 
/* 103 */     this.menu = root.element("Menu");
/* 104 */     this.vmenu = root.element("MenuShortCut");
/*     */   }
/*     */ 
/*     */   public String getMenu()
/*     */   {
/* 120 */     this.log.info("getMenu");
/* 121 */     StringBuffer buf = new StringBuffer(1024);
/* 122 */     buf.append("<ul id=\"menu\">");
/* 123 */     for (Iterator i = this.menu.elementIterator(); i.hasNext(); ) {
/* 124 */       Element element = (Element)i.next();
/* 125 */       appendItem(buf, element);
/*     */     }
/* 127 */     buf.append("</ul>");
/* 128 */     String ret = buf.toString();
/* 129 */     return ret;
/*     */   }
/*     */ 
/*     */   private boolean appendItem(StringBuffer buf, Element element)
/*     */   {
/* 134 */     this.log.info("appendItem:[" + buf + "];[" + element + "]");
/*     */ 
/* 136 */     boolean append = false;
/* 137 */     if (element.getName().equals("Group")) {
/* 138 */       StringBuffer innerbuf = new StringBuffer();
/* 139 */       for (Iterator i = element.elementIterator(); i.hasNext(); ) {
/* 140 */         Element e = (Element)i.next();
/* 141 */         append |= appendItem(innerbuf, e);
/*     */       }
/* 143 */       if (append) {
/* 144 */         buf.append("<li>");
/* 145 */         buf.append("<a href=\"");
/* 146 */         buf.append("javascript:dochange('" + element.attributeValue("name") + "')");
/*     */ 
/* 148 */         buf.append("\">");
/* 149 */         buf.append(element.attributeValue("name"));
/* 150 */         buf.append("</a>");
/*     */ 
/* 159 */         buf.append("</li>");
/*     */ 
/* 161 */         buf.append("\r\n");
/*     */ 
/* 163 */         String s = element.attributeValue("name");
/* 164 */         if (!(this.gids.contains(s)))
/* 165 */           this.gids.add(s);
/*     */       }
/* 167 */     } else if (element.getName().equals("Item")) {
/* 168 */       String id = element.attributeValue("id");
/* 169 */       if ((getPagePermission(id)) && 
/* 171 */         (this.pages.containsKey(id)))
/*     */       {
/* 173 */         append = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 178 */     return append;
/*     */   }
/*     */ 
/*     */   public String getVmenu()
/*     */   {
/* 184 */     this.log.info("getVmenu");
/* 185 */     StringBuffer ret = new StringBuffer();
/* 186 */     for (int i = 0; i < this.gids.size(); ++i) {
/* 187 */       if (i == 0)
/* 188 */         ret.append(showVMenu((String)this.gids.get(i), true));
/*     */       else
/* 190 */         ret.append(showVMenu((String)this.gids.get(i), false));
/* 191 */       ret.append("\r\n");
/*     */     }
/*     */ 
/* 194 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   private String showVMenu(String gid, boolean show) {
/* 198 */     this.log.info("showVMenu, gid:[" + gid + "]");
/*     */ 
/* 200 */     Element vmenu = getElementByName(gid);
/* 201 */     if (vmenu == null) {
/* 202 */       return "";
/*     */     }
/*     */ 
/* 205 */     StringBuffer buf = new StringBuffer(1024);
/* 206 */     if (show) {
/* 207 */       buf.append("<ul id=\"vmenu\" name=\"" + gid + "\" style=\"display:block\">");
/*     */     }
/*     */     else {
/* 210 */       buf.append("<ul id=\"vmenu\" name=\"" + gid + "\" style=\"display:none\">");
/*     */     }
/*     */ 
/* 213 */     buf.append("\r\n");
/* 214 */     for (Iterator i = vmenu.elementIterator(); i.hasNext(); ) {
/* 215 */       Element element = (Element)i.next();
/*     */ 
/* 217 */       if (element.getName().equals("Item")) {
/* 218 */         String id = element.attributeValue("id");
/* 219 */         if ((getPagePermission(id)) && 
/* 220 */           (this.pages.containsKey(id))) {
/* 221 */           Element e = (Element)this.pages.get(id);
/* 222 */           buf.append("<li>");
/* 223 */           buf.append("<a href=\"");
/* 224 */           String url = e.attributeValue("url");
/*     */ 
/* 226 */           if ((!(StringUtils.isEmpty(url))) && (!("#".equals(url))))
/* 227 */             buf.append(url);
/*     */           else {
/* 229 */             buf.append(e.attributeValue("url"));
/*     */           }
/* 231 */           buf.append("\" target=mainWorkArea>");
/* 232 */           buf.append(e.attributeValue("name"));
/* 233 */           buf.append("</a>");
/* 234 */           buf.append("</li>");
/*     */ 
/* 236 */           buf.append("\r\n");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 242 */     buf.append("</ul>");
/* 243 */     buf.append("\r\n");
/* 244 */     String ret = buf.toString();
/* 245 */     return ret;
/*     */   }
/*     */ 
/*     */   private Element getElementByName(String name) {
/* 249 */     this.log.info("getElementByName:[" + name + "]");
/* 250 */     Element root = this.menu;
/* 251 */     for (Iterator i = root.elementIterator(); i.hasNext(); ) {
/* 252 */       Element el = (Element)i.next();
/* 253 */       if (name.equals(el.attributeValue("name"))) {
/* 254 */         return el;
/*     */       }
/*     */     }
/* 257 */     return null;
/*     */   }
/*     */ 
/*     */   public void setPermission(Set pageAcc, Set actAcc, String userid) {
/* 261 */     this.log.info("setPermission");
/* 262 */     this.pageMap.put(userid, pageAcc);
/* 263 */     this.actionMap.put(userid, actAcc);
/*     */   }
/*     */ 
/*     */   public boolean getPagePermission(String pageid)
/*     */   {
/* 268 */     this.log.info("getPagePermission:[" + pageid + "]");
/* 269 */     Set pageAccess = (Set)this.pageMap.get(getUserID());
/* 270 */     if (pageAccess != null) {
/* 271 */       return pageAccess.contains(pageid);
/*     */     }
/* 273 */     return false;
/*     */   }
/*     */ 
/*     */   private String getUserID()
/*     */   {
/* 278 */     return "test";
/*     */   }
/*     */ }