 package com.hisun.web.tag;
 
 import java.io.IOException;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.HashMap;
 import javax.servlet.http.HttpSession;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.TagSupport;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class HiTreeMenuTag extends TagSupport
 {
   private static final String IM0 = "dhtmlxtree_icon.gif";
   private static final String IM1 = "dhtmlxtree_icon.gif";
   private static final String IM2 = "dhtmlxtree_icon.gif";
   private String encoding;
 
   public int doEndTag()
     throws JspException
   {
     HashMap menuMap = (HashMap)this.pageContext.getSession().getAttribute("MENU");
     Document doc = process(menuMap);
     try {
       this.pageContext.getOut().write(doc.asXML().replace("\"", "'").replace("\n", ""));
     } catch (IOException e) {
       throw new JspException(e);
     }
     return 6;
   }
 
   public Document process(HashMap menuMap) throws JspException {
     Document doc = DocumentHelper.createDocument();
     if (StringUtils.isNotEmpty(this.encoding)) {
       doc.setXMLEncoding(this.encoding);
     }
     Element root = doc.addElement("tree");
     root.addAttribute("id", "0");
     root = root.addElement("item");
     root.addAttribute("id", "root");
     root.addAttribute("text", (String)menuMap.get("name"));
     root.addAttribute("open", "1");
     root.addAttribute("im0", "dhtmlxtree_icon.gif");
     root.addAttribute("im1", "dhtmlxtree_icon.gif");
     root.addAttribute("im2", "dhtmlxtree_icon.gif");
 
     ArrayList menuList = (ArrayList)menuMap.get("IDX_MENU");
     for (int i = 0; i < menuList.size(); ++i) {
       HashMap groupMap = (HashMap)menuList.get(i);
       ArrayList groupList = (ArrayList)groupMap.get("SEC_MENU");
       Element parent = root.addElement("item");
       parent.addAttribute("id", "GRP_" + String.valueOf(i));
       parent.addAttribute("open", "1");
       parent.addAttribute("text", (String)groupMap.get("name"));
       parent.addAttribute("im0", "dhtmlxtree_icon.gif");
       parent.addAttribute("im1", "dhtmlxtree_icon.gif");
       parent.addAttribute("im2", "dhtmlxtree_icon.gif");
 
       for (int j = 0; j < groupList.size(); ++j) {
         HashMap tmp = (HashMap)groupList.get(j);
 
         Element element = parent.addElement("item");
         element.addAttribute("id", (String)tmp.get("id"));
         element.addAttribute("text", (String)tmp.get("name"));
         Element userdata = element.addElement("userdata");
         userdata.addAttribute("name", "action");
         userdata.addText((String)tmp.get("url"));
       }
     }
     return doc;
   }
 
   public static void main(String[] args) throws JspException {
     HashMap menuMap = new HashMap();
     menuMap.put("name", "百富卡管理平台");
     ArrayList menuList = new ArrayList();
     menuMap.put("IDX_MENU", menuList);
     for (int i = 0; i < 10; ++i) {
       HashMap groupMap = new HashMap();
       groupMap.put("name", "GRP_" + i);
       ArrayList groupList = new ArrayList();
       groupMap.put("SEC_MENU", groupList);
       for (int j = 0; j < 10; ++j) {
         HashMap itemMap = new HashMap();
         itemMap.put("id", "id_" + j);
         itemMap.put("name", "name_" + j);
         itemMap.put("url", "url_" + j);
         groupList.add(itemMap);
       }
       menuList.add(groupMap);
     }
     HiTreeMenuTag treeMenu = new HiTreeMenuTag();
     treeMenu.setEncoding("gbk");
     Document doc = treeMenu.process(menuMap);
     System.out.println(doc.asXML());
   }
 
   public String getEncoding()
   {
     return this.encoding;
   }
 
   public void setEncoding(String encoding)
   {
     this.encoding = encoding;
   }
 }