 package com.hisun.teller.action;

 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.web.action.BaseAction;
 import com.opensymphony.xwork2.ActionContext;
 import org.apache.commons.lang.StringUtils;
 import org.apache.struts2.ServletActionContext;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;

 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import java.io.IOException;
 import java.util.*;
 
 public class InitMenuAction extends BaseAction
 {
   private static final long serialVersionUID = 1L;
   private static final String IMAGE0 = "books_close.gif";
   private static final String IMAGE1 = "tombs.gif";
   private static final String IMAGE2 = "tombs.gif";
   private Map pages;
   private Element menus;
   private Map pageMap;
   private String usrid;
 
   protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log)
   {
     Set set = getSelectedItem(rspetf);
     createTreeXmlFile("menutree.xml", set, request);
 
     return true;
   }
 
   private void createTreeXmlFile(String filename, Set set, HttpServletRequest request)
   {
     Element item;
     Iterator i;
     if (set == null) {
       set = new HashSet();
     }
 
     this.pages = ((Map)ServletActionContext.getContext().getApplication().get("pages"));
 
     this.menus = ((Element)ServletActionContext.getContext().getApplication().get("menu"));
 
     this.pageMap = ((Map)ServletActionContext.getContext().getApplication().get("usrpage"));
 
     this.usrid = ((String)ServletActionContext.getRequest().getSession(true).getAttribute("UID"));
 
     Iterator menuIter = this.menus.elementIterator();
 
     Element el = DocumentHelper.createElement("tree");
     el.addAttribute("id", "0");
 
     Element tree = el.addElement("item");
     tree.addAttribute("text", "交易集合");
     tree.addAttribute("id", "1");
     tree.addAttribute("open", "1");
     tree.addAttribute("im0", "books_close.gif");
     tree.addAttribute("im1", "tombs.gif");
     tree.addAttribute("im2", "tombs.gif");
     while (menuIter.hasNext())
     {
       Element e = (Element)menuIter.next();
       if (isAuthChild(e)) {
         String name = e.attributeValue("name");
         String id = "_" + name;
 
         item = tree.addElement("item");
         item.addAttribute("text", name);
         item.addAttribute("id", id);
 
         item.addAttribute("im0", "books_close.gif");
         item.addAttribute("im1", "tombs.gif");
         item.addAttribute("im2", "tombs.gif");
 
         for (i = e.elementIterator(); i.hasNext(); ) {
           Element child = (Element)i.next();
           String c_id = child.attributeValue("id");
 
           Element page = (Element)this.pages.get(c_id);
           if (page == null) {
             continue;
           }
           if (getPagePermission(c_id)) {
             String c_name = page.attributeValue("name");
             Element second = item.addElement("item");
             second.addAttribute("text", c_name);
             second.addAttribute("im0", "books_close.gif");
             second.addAttribute("im1", "tombs.gif");
             second.addAttribute("im2", "tombs.gif");
 
             String url = page.attributeValue("url");
 
             second.addAttribute("id", url);
           }
         }
       }
     }
 
     HttpServletResponse response = ServletActionContext.getResponse();
     response.setContentType("text/xml; charset=gb2312");
     try
     {
       response.getWriter().write("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + el.asXML());
       response.getWriter().flush();
     } catch (IOException e1) {
       e1.printStackTrace();
     }
   }
 
   private boolean isAuthChild(Element el)
   {
     for (Iterator i = el.elementIterator(); i.hasNext(); ) {
       Element child = (Element)i.next();
       String c_id = child.attributeValue("id");
 
       if (getPagePermission(c_id)) {
         return true;
       }
     }
 
     return false;
   }
 
   private Set getSelectedItem(HiETF etf) {
     if (etf == null) return null;
 
     Set set = new HashSet();
     String[] nodes = StringUtils.split("GROUP.TXN_CD", ".");
 
     if ((nodes == null) || (nodes.length < 2)) {
       return null;
     }
     List lst = etf.getChildFuzzyEnd(nodes[0]);
     for (Iterator i = lst.iterator(); i.hasNext(); ) {
       HiETF child = (HiETF)i.next();
       String pageid = child.getChildValue("PAG_ID");
       String action = child.getChildValue(nodes[1]);
       if ((action != null) && (action.length() > 0)) {
         set.add(action + ":" + pageid);
       }
       else {
         set.add(pageid);
       }
     }
 
     return set;
   }
 
   public boolean getPagePermission(String pageid) {
     Set pageAccess = (Set)this.pageMap.get(this.usrid);
     if (pageAccess != null) {
       return pageAccess.contains(pageid);
     }
 
     return false;
   }
 }