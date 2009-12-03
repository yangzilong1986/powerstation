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
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts2.ServletActionContext;
/*     */ import org.dom4j.Attribute;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class SetRoleAction extends BaseAction
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final String IMAGE0 = "books_close.gif";
/*     */   private static final String IMAGE1 = "tombs.gif";
/*     */   private static final String IMAGE2 = "tombs.gif";
/*     */ 
/*     */   protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log)
/*     */   {
/*  37 */     Set set = getSelectedItem(rspetf);
/*  38 */     createTreeXmlFile("pageprv.xml", set, request);
/*     */ 
/*  41 */     return true;
/*     */   }
/*     */ 
/*     */   private void createTreeXmlFile(String filename, Set set, HttpServletRequest request)
/*     */   {
/*     */     Element item;
/*     */     Iterator i;
/*  51 */     if (set == null) {
/*  52 */       set = new HashSet();
/*     */     }
/*     */ 
/*  57 */     Map pages = (Map)ServletActionContext.getContext().getApplication().get("pages");
/*  58 */     Element menus = (Element)ServletActionContext.getContext().getApplication().get("menu");
/*     */ 
/*  60 */     Iterator menuIter = menus.elementIterator();
/*     */ 
/*  78 */     Element el = DocumentHelper.createElement("tree");
/*  79 */     el.addAttribute("id", "0");
/*     */ 
/*  82 */     Element tree = el.addElement("item");
/*  83 */     tree.addAttribute("text", "root");
/*  84 */     tree.addAttribute("id", "1");
/*  85 */     tree.addAttribute("open", "1");
/*  86 */     tree.addAttribute("im0", "books_close.gif");
/*  87 */     tree.addAttribute("im1", "tombs.gif");
/*  88 */     tree.addAttribute("im2", "tombs.gif");
/*  89 */     while (menuIter.hasNext())
/*     */     {
/*  92 */       Element e = (Element)menuIter.next();
/*     */ 
/*  94 */       String name = e.attributeValue("name");
/*  95 */       String id = name;
/*     */ 
/*  98 */       item = tree.addElement("item");
/*  99 */       item.addAttribute("text", name);
/* 100 */       item.addAttribute("id", id);
/*     */ 
/* 104 */       item.addAttribute("im0", "books_close.gif");
/* 105 */       item.addAttribute("im1", "tombs.gif");
/* 106 */       item.addAttribute("im2", "tombs.gif");
/*     */ 
/* 109 */       for (i = e.elementIterator(); i.hasNext(); )
/*     */       {
/*     */         Iterator it;
/* 110 */         Element child = (Element)i.next();
/* 111 */         String c_id = child.attributeValue("id");
/*     */ 
/* 113 */         Element page = (Element)pages.get(c_id);
/* 114 */         if (page == null) {
/*     */           continue;
/*     */         }
/* 117 */         String c_name = page.attributeValue("name");
/* 118 */         Element second = item.addElement("item");
/* 119 */         second.addAttribute("text", c_name);
/* 120 */         second.addAttribute("im0", "books_close.gif");
/* 121 */         second.addAttribute("im1", "tombs.gif");
/* 122 */         second.addAttribute("im2", "tombs.gif");
/*     */ 
/* 125 */         List list = page.selectNodes("Action");
/* 126 */         if (list.size() > 0)
/*     */         {
/* 128 */           second.addAttribute("id", "fod_" + c_id);
/* 129 */           for (it = list.iterator(); i.hasNext(); ) {
/* 130 */             Element action = (Element)it.next();
/* 131 */             String actnam = action.attributeValue("name");
/* 132 */             String actid = action.attributeValue("id");
/*     */ 
/* 135 */             Element actitem = second.addElement("item");
/* 136 */             actitem.addAttribute("text", actnam);
/* 137 */             actitem.addAttribute("id", "act_" + c_id + actid);
/*     */ 
/* 139 */             actitem.addAttribute("im0", "books_close.gif");
/* 140 */             actitem.addAttribute("im1", "tombs.gif");
/* 141 */             actitem.addAttribute("im2", "tombs.gif");
/*     */ 
/* 143 */             if (set.contains(actid + ":" + c_id)) {
/* 144 */               actitem.addAttribute("checked", "1");
/*     */             } else {
/* 146 */               Attribute attr = actitem.attribute("checked");
/* 147 */               if (attr != null)
/* 148 */                 actitem.remove(attr);
/*     */             }
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 154 */           second.addAttribute("id", "pag_" + c_id);
/*     */ 
/* 156 */           if (set.contains(c_id))
/*     */           {
/* 158 */             second.addAttribute("checked", "1");
/*     */           } else {
/* 160 */             Attribute attr = second.attribute("checked");
/* 161 */             if (attr != null) {
/* 162 */               second.remove(attr);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 169 */     HttpServletResponse response = ServletActionContext.getResponse();
/* 170 */     response.setContentType("text/xml; charset=gb2312");
/*     */     try
/*     */     {
/* 173 */       response.getWriter().write("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + el.asXML());
/* 174 */       response.getWriter().flush();
/*     */     } catch (IOException e1) {
/* 176 */       e1.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private Set getSelectedItem(HiETF etf)
/*     */   {
/* 182 */     if (etf == null) {
/* 183 */       return null;
/*     */     }
/* 185 */     Set set = new HashSet();
/* 186 */     String[] nodes = StringUtils.split("GROUP.TXN_CD", ".");
/*     */ 
/* 188 */     if ((nodes == null) || (nodes.length < 2)) {
/* 189 */       return null;
/*     */     }
/* 191 */     List lst = etf.getChildFuzzyEnd(nodes[0]);
/* 192 */     for (Iterator i = lst.iterator(); i.hasNext(); ) {
/* 193 */       HiETF child = (HiETF)i.next();
/* 194 */       String pageid = child.getChildValue("PAG_ID");
/* 195 */       String action = child.getChildValue(nodes[1]);
/* 196 */       if ((action != null) && (action.length() > 0)) {
/* 197 */         set.add(action + ":" + pageid);
/*     */       }
/*     */       else {
/* 200 */         set.add(pageid);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 205 */     return set;
/*     */   }
/*     */ }