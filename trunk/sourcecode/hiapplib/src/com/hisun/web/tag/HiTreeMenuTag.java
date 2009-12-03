/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.TagSupport;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiTreeMenuTag extends TagSupport
/*     */ {
/*     */   private static final String IM0 = "dhtmlxtree_icon.gif";
/*     */   private static final String IM1 = "dhtmlxtree_icon.gif";
/*     */   private static final String IM2 = "dhtmlxtree_icon.gif";
/*     */   private String encoding;
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*  28 */     HashMap menuMap = (HashMap)this.pageContext.getSession().getAttribute("MENU");
/*  29 */     Document doc = process(menuMap);
/*     */     try {
/*  31 */       this.pageContext.getOut().write(doc.asXML().replace("\"", "'").replace("\n", ""));
/*     */     } catch (IOException e) {
/*  33 */       throw new JspException(e);
/*     */     }
/*  35 */     return 6;
/*     */   }
/*     */ 
/*     */   public Document process(HashMap menuMap) throws JspException {
/*  39 */     Document doc = DocumentHelper.createDocument();
/*  40 */     if (StringUtils.isNotEmpty(this.encoding)) {
/*  41 */       doc.setXMLEncoding(this.encoding);
/*     */     }
/*  43 */     Element root = doc.addElement("tree");
/*  44 */     root.addAttribute("id", "0");
/*  45 */     root = root.addElement("item");
/*  46 */     root.addAttribute("id", "root");
/*  47 */     root.addAttribute("text", (String)menuMap.get("name"));
/*  48 */     root.addAttribute("open", "1");
/*  49 */     root.addAttribute("im0", "dhtmlxtree_icon.gif");
/*  50 */     root.addAttribute("im1", "dhtmlxtree_icon.gif");
/*  51 */     root.addAttribute("im2", "dhtmlxtree_icon.gif");
/*     */ 
/*  53 */     ArrayList menuList = (ArrayList)menuMap.get("IDX_MENU");
/*  54 */     for (int i = 0; i < menuList.size(); ++i) {
/*  55 */       HashMap groupMap = (HashMap)menuList.get(i);
/*  56 */       ArrayList groupList = (ArrayList)groupMap.get("SEC_MENU");
/*  57 */       Element parent = root.addElement("item");
/*  58 */       parent.addAttribute("id", "GRP_" + String.valueOf(i));
/*  59 */       parent.addAttribute("open", "1");
/*  60 */       parent.addAttribute("text", (String)groupMap.get("name"));
/*  61 */       parent.addAttribute("im0", "dhtmlxtree_icon.gif");
/*  62 */       parent.addAttribute("im1", "dhtmlxtree_icon.gif");
/*  63 */       parent.addAttribute("im2", "dhtmlxtree_icon.gif");
/*     */ 
/*  65 */       for (int j = 0; j < groupList.size(); ++j) {
/*  66 */         HashMap tmp = (HashMap)groupList.get(j);
/*     */ 
/*  68 */         Element element = parent.addElement("item");
/*  69 */         element.addAttribute("id", (String)tmp.get("id"));
/*  70 */         element.addAttribute("text", (String)tmp.get("name"));
/*  71 */         Element userdata = element.addElement("userdata");
/*  72 */         userdata.addAttribute("name", "action");
/*  73 */         userdata.addText((String)tmp.get("url"));
/*     */       }
/*     */     }
/*  76 */     return doc;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws JspException {
/*  80 */     HashMap menuMap = new HashMap();
/*  81 */     menuMap.put("name", "百富卡管理平台");
/*  82 */     ArrayList menuList = new ArrayList();
/*  83 */     menuMap.put("IDX_MENU", menuList);
/*  84 */     for (int i = 0; i < 10; ++i) {
/*  85 */       HashMap groupMap = new HashMap();
/*  86 */       groupMap.put("name", "GRP_" + i);
/*  87 */       ArrayList groupList = new ArrayList();
/*  88 */       groupMap.put("SEC_MENU", groupList);
/*  89 */       for (int j = 0; j < 10; ++j) {
/*  90 */         HashMap itemMap = new HashMap();
/*  91 */         itemMap.put("id", "id_" + j);
/*  92 */         itemMap.put("name", "name_" + j);
/*  93 */         itemMap.put("url", "url_" + j);
/*  94 */         groupList.add(itemMap);
/*     */       }
/*  96 */       menuList.add(groupMap);
/*     */     }
/*  98 */     HiTreeMenuTag treeMenu = new HiTreeMenuTag();
/*  99 */     treeMenu.setEncoding("gbk");
/* 100 */     Document doc = treeMenu.process(menuMap);
/* 101 */     System.out.println(doc.asXML());
/*     */   }
/*     */ 
/*     */   public String getEncoding()
/*     */   {
/* 109 */     return this.encoding;
/*     */   }
/*     */ 
/*     */   public void setEncoding(String encoding)
/*     */   {
/* 116 */     this.encoding = encoding;
/*     */   }
/*     */ }