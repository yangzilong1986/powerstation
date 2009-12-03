/*     */ package com.hisun.teller.action;
/*     */ 
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.web.action.BaseAction;
/*     */ import com.opensymphony.xwork2.ActionContext;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts2.ServletActionContext;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class InitMenuAction extends BaseAction
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final String IMAGE0 = "books_close.gif";
/*     */   private static final String IMAGE1 = "tombs.gif";
/*     */   private static final String IMAGE2 = "tombs.gif";
/*     */   private Map pages;
/*     */   private Element menus;
/*     */   private Map pageMap;
/*     */   private String usrid;
/*     */ 
/*     */   protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log)
/*     */   {
/*  41 */     Set set = getSelectedItem(rspetf);
/*  42 */     createTreeXmlFile("menutree.xml", set, request);
/*     */ 
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */   private void createTreeXmlFile(String filename, Set set, HttpServletRequest request)
/*     */   {
/*     */     Element item;
/*     */     Iterator i;
/*  55 */     if (set == null) {
/*  56 */       set = new HashSet();
/*     */     }
/*     */ 
/*  62 */     this.pages = ((Map)ServletActionContext.getContext().getApplication().get("pages"));
/*     */ 
/*  64 */     this.menus = ((Element)ServletActionContext.getContext().getApplication().get("menu"));
/*     */ 
/*  66 */     this.pageMap = ((Map)ServletActionContext.getContext().getApplication().get("usrpage"));
/*     */ 
/*  68 */     this.usrid = ((String)ServletActionContext.getRequest().getSession(true).getAttribute("UID"));
/*     */ 
/*  71 */     Iterator menuIter = this.menus.elementIterator();
/*     */ 
/*  89 */     Element el = DocumentHelper.createElement("tree");
/*  90 */     el.addAttribute("id", "0");
/*     */ 
/*  93 */     Element tree = el.addElement("item");
/*  94 */     tree.addAttribute("text", "交易集合");
/*  95 */     tree.addAttribute("id", "1");
/*  96 */     tree.addAttribute("open", "1");
/*  97 */     tree.addAttribute("im0", "books_close.gif");
/*  98 */     tree.addAttribute("im1", "tombs.gif");
/*  99 */     tree.addAttribute("im2", "tombs.gif");
/* 100 */     while (menuIter.hasNext())
/*     */     {
/* 104 */       Element e = (Element)menuIter.next();
/* 105 */       if (isAuthChild(e)) {
/* 106 */         String name = e.attributeValue("name");
/* 107 */         String id = "_" + name;
/*     */ 
/* 110 */         item = tree.addElement("item");
/* 111 */         item.addAttribute("text", name);
/* 112 */         item.addAttribute("id", id);
/*     */ 
/* 116 */         item.addAttribute("im0", "books_close.gif");
/* 117 */         item.addAttribute("im1", "tombs.gif");
/* 118 */         item.addAttribute("im2", "tombs.gif");
/*     */ 
/* 121 */         for (i = e.elementIterator(); i.hasNext(); ) {
/* 122 */           Element child = (Element)i.next();
/* 123 */           String c_id = child.attributeValue("id");
/*     */ 
/* 125 */           Element page = (Element)this.pages.get(c_id);
/* 126 */           if (page == null) {
/*     */             continue;
/*     */           }
/* 129 */           if (getPagePermission(c_id)) {
/* 130 */             String c_name = page.attributeValue("name");
/* 131 */             Element second = item.addElement("item");
/* 132 */             second.addAttribute("text", c_name);
/* 133 */             second.addAttribute("im0", "books_close.gif");
/* 134 */             second.addAttribute("im1", "tombs.gif");
/* 135 */             second.addAttribute("im2", "tombs.gif");
/*     */ 
/* 139 */             String url = page.attributeValue("url");
/*     */ 
/* 141 */             second.addAttribute("id", url);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 147 */     HttpServletResponse response = ServletActionContext.getResponse();
/* 148 */     response.setContentType("text/xml; charset=gb2312");
/*     */     try
/*     */     {
/* 151 */       response.getWriter().write("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + el.asXML());
/* 152 */       response.getWriter().flush();
/*     */     } catch (IOException e1) {
/* 154 */       e1.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isAuthChild(Element el)
/*     */   {
/* 161 */     for (Iterator i = el.elementIterator(); i.hasNext(); ) {
/* 162 */       Element child = (Element)i.next();
/* 163 */       String c_id = child.attributeValue("id");
/*     */ 
/* 165 */       if (getPagePermission(c_id)) {
/* 166 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */   private Set getSelectedItem(HiETF etf) {
/* 174 */     if (etf == null) return null;
/*     */ 
/* 176 */     Set set = new HashSet();
/* 177 */     String[] nodes = StringUtils.split("GROUP.TXN_CD", ".");
/*     */ 
/* 179 */     if ((nodes == null) || (nodes.length < 2)) {
/* 180 */       return null;
/*     */     }
/* 182 */     List lst = etf.getChildFuzzyEnd(nodes[0]);
/* 183 */     for (Iterator i = lst.iterator(); i.hasNext(); ) {
/* 184 */       HiETF child = (HiETF)i.next();
/* 185 */       String pageid = child.getChildValue("PAG_ID");
/* 186 */       String action = child.getChildValue(nodes[1]);
/* 187 */       if ((action != null) && (action.length() > 0)) {
/* 188 */         set.add(action + ":" + pageid);
/*     */       }
/*     */       else {
/* 191 */         set.add(pageid);
/*     */       }
/*     */     }
/*     */ 
/* 195 */     return set;
/*     */   }
/*     */ 
/*     */   public boolean getPagePermission(String pageid) {
/* 199 */     Set pageAccess = (Set)this.pageMap.get(this.usrid);
/* 200 */     if (pageAccess != null) {
/* 201 */       return pageAccess.contains(pageid);
/*     */     }
/*     */ 
/* 204 */     return false;
/*     */   }
/*     */ }