 package com.hisun.web.tag;
 
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.TagSupport;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class HiParseMenuTag extends TagSupport
 {
   private static final String CONTEXT_ROOT = "${context_root}";
 
   public int doEndTag()
     throws JspException
   {
     try
     {
       parseMenu();
     } catch (Exception e) {
       throw new JspException(e);
     }
     return 6;
   }
 
   private void parseMenu() throws Exception {
     HashMap menuMap = new HashMap();
     ArrayList authList = (ArrayList)this.pageContext.getSession().getAttribute("__AUTH_LIST");
 
     HashMap pagesMap = new HashMap();
     SAXReader reader = new SAXReader();
     ServletContext context = this.pageContext.getServletContext();
 
     Document doc = reader.read(context.getRealPath("/conf/menuconfig.xml"));
     Element root = doc.getRootElement();
     Iterator menuIter = root.elementIterator("Menu");
 
     while (menuIter.hasNext()) {
       parseMenuNode((Element)menuIter.next(), authList, menuMap);
     }
     this.pageContext.getSession().setAttribute("MENU", menuMap);
   }
 
   private HashMap parseMenuNode(Element menu, ArrayList authList, HashMap menuMap) throws Exception {
     menuMap.put("name", menu.attributeValue("name"));
     Iterator groupIter = menu.elementIterator("Group");
     ArrayList menuList = null;
     if (menuMap.containsKey("IDX_MENU")) {
       menuList = (ArrayList)menuMap.get("IDX_MENU");
     } else {
       menuList = new ArrayList();
       menuMap.put("IDX_MENU", menuList);
     }
 
     while (groupIter.hasNext()) {
       HashMap groupMap = new HashMap();
       Element group = (Element)groupIter.next();
       groupMap.put("name", group.attributeValue("name"));
       ArrayList groupList = new ArrayList();
       groupMap.put("SEC_MENU", groupList);
       Iterator itemIter = group.elementIterator("Item");
       while (itemIter.hasNext()) {
         Element item = (Element)itemIter.next();
         String id = item.attributeValue("id");
         String url = item.attributeValue("url");
         String name = item.attributeValue("name");
         if (id == null) {
           throw new IllegalArgumentException("item's attribute id is null");
         }
         if (url == null) {
           throw new IllegalArgumentException("item id:[" + id + "]'s attribute url is null");
         }
         HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
         url = StringUtils.replace(url, "${context_root}", request.getContextPath());
         if (name == null) {
           throw new Exception("item id:[" + id + "]'s attribute name is null");
         }
 
         if ((authList != null) && (!(authList.contains(id)))) {
           continue;
         }
         HashMap tmpMap = new HashMap();
         tmpMap.put("id", id);
         tmpMap.put("name", name);
         tmpMap.put("url", url);
         groupList.add(tmpMap);
       }
       menuList.add(groupMap);
     }
     return menuMap;
   }
 
   public static void main(String[] args) {
     String url = "${context_ro3ot}/ddd";
     System.out.println(StringUtils.replace(url, "${context_root}", "hello"));
   }
 }