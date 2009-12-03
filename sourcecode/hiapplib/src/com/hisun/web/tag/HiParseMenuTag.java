/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.TagSupport;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiParseMenuTag extends TagSupport
/*     */ {
/*     */   private static final String CONTEXT_ROOT = "${context_root}";
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  37 */       parseMenu();
/*     */     } catch (Exception e) {
/*  39 */       throw new JspException(e);
/*     */     }
/*  41 */     return 6;
/*     */   }
/*     */ 
/*     */   private void parseMenu() throws Exception {
/*  45 */     HashMap menuMap = new HashMap();
/*  46 */     ArrayList authList = (ArrayList)this.pageContext.getSession().getAttribute("__AUTH_LIST");
/*     */ 
/*  49 */     HashMap pagesMap = new HashMap();
/*  50 */     SAXReader reader = new SAXReader();
/*  51 */     ServletContext context = this.pageContext.getServletContext();
/*     */ 
/*  53 */     Document doc = reader.read(context.getRealPath("/conf/menuconfig.xml"));
/*  54 */     Element root = doc.getRootElement();
/*  55 */     Iterator menuIter = root.elementIterator("Menu");
/*     */ 
/*  57 */     while (menuIter.hasNext()) {
/*  58 */       parseMenuNode((Element)menuIter.next(), authList, menuMap);
/*     */     }
/*  60 */     this.pageContext.getSession().setAttribute("MENU", menuMap);
/*     */   }
/*     */ 
/*     */   private HashMap parseMenuNode(Element menu, ArrayList authList, HashMap menuMap) throws Exception {
/*  64 */     menuMap.put("name", menu.attributeValue("name"));
/*  65 */     Iterator groupIter = menu.elementIterator("Group");
/*  66 */     ArrayList menuList = null;
/*  67 */     if (menuMap.containsKey("IDX_MENU")) {
/*  68 */       menuList = (ArrayList)menuMap.get("IDX_MENU");
/*     */     } else {
/*  70 */       menuList = new ArrayList();
/*  71 */       menuMap.put("IDX_MENU", menuList);
/*     */     }
/*     */ 
/*  74 */     while (groupIter.hasNext()) {
/*  75 */       HashMap groupMap = new HashMap();
/*  76 */       Element group = (Element)groupIter.next();
/*  77 */       groupMap.put("name", group.attributeValue("name"));
/*  78 */       ArrayList groupList = new ArrayList();
/*  79 */       groupMap.put("SEC_MENU", groupList);
/*  80 */       Iterator itemIter = group.elementIterator("Item");
/*  81 */       while (itemIter.hasNext()) {
/*  82 */         Element item = (Element)itemIter.next();
/*  83 */         String id = item.attributeValue("id");
/*  84 */         String url = item.attributeValue("url");
/*  85 */         String name = item.attributeValue("name");
/*  86 */         if (id == null) {
/*  87 */           throw new IllegalArgumentException("item's attribute id is null");
/*     */         }
/*  89 */         if (url == null) {
/*  90 */           throw new IllegalArgumentException("item id:[" + id + "]'s attribute url is null");
/*     */         }
/*  92 */         HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
/*  93 */         url = StringUtils.replace(url, "${context_root}", request.getContextPath());
/*  94 */         if (name == null) {
/*  95 */           throw new Exception("item id:[" + id + "]'s attribute name is null");
/*     */         }
/*     */ 
/*  98 */         if ((authList != null) && (!(authList.contains(id)))) {
/*     */           continue;
/*     */         }
/* 101 */         HashMap tmpMap = new HashMap();
/* 102 */         tmpMap.put("id", id);
/* 103 */         tmpMap.put("name", name);
/* 104 */         tmpMap.put("url", url);
/* 105 */         groupList.add(tmpMap);
/*     */       }
/* 107 */       menuList.add(groupMap);
/*     */     }
/* 109 */     return menuMap;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 113 */     String url = "${context_ro3ot}/ddd";
/* 114 */     System.out.println(StringUtils.replace(url, "${context_root}", "hello"));
/*     */   }
/*     */ }