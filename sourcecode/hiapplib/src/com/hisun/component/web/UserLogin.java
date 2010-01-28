 package com.hisun.component.web;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.LinkedHashMap;
 import java.util.Map;
 import java.util.Set;
 import javax.servlet.ServletContext;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class UserLogin
 {
   private Logger log;
   private Map pages;
   private Element menu;
   private Element vmenu;
   private Map pageMap;
   private Map actionMap;
   private ArrayList gids;
 
   public UserLogin()
   {
     this.log = HiLog.getLogger("login.trc");
 
     this.pages = new LinkedHashMap();
     this.menu = null;
     this.vmenu = null;
 
     this.pageMap = new LinkedHashMap();
     this.actionMap = new LinkedHashMap();
 
     this.gids = new ArrayList();
   }
 
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     authProc(ctx);
     return 0;
   }
 
   protected boolean authProc(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     int recNum = NumberUtils.toInt(root.getChildValue("REC_NUM"));
     Logger log = HiLog.getLogger(msg);
     log.info("REC_NUM:[" + recNum + "]");
     if (recNum == 0)
     {
       return false;
     }
     Set pageAccSet = new HashSet();
     Set actAccSet = new HashSet();
     for (int i = 1; i <= recNum; ++i) {
       HiETF grpEtf = root.getChildNode("GRP_" + i);
       if (grpEtf != null) {
         pageAccSet.add(grpEtf.getChildValue("PAG_ID"));
         actAccSet.add(grpEtf.getChildValue("TXN_CD"));
       }
 
     }
 
     setPermission(pageAccSet, actAccSet, getUserID());
 
     readmenuconfig(ctx);
     String menu = getMenu();
 
     String vmenu = getVmenu();
     msg.getETFBody().setChildValue("MENU", menu);
     msg.getETFBody().setChildValue("VMENU", vmenu);
     return true;
   }
 
   private void readmenuconfig(HiMessageContext ctx) throws HiException {
     this.log.info("readmenuconfig");
     SAXReader reader = new SAXReader();
     Document doc = null;
     HiMessage msg = ctx.getCurrentMsg();
     ServletContext servletCtx = (ServletContext)msg.getObjectHeadItem("_WEB_APPLICATION");
     try
     {
       InputStream in = servletCtx.getResourceAsStream("/conf/menuconfig.xml");
 
       doc = reader.read(in);
     } catch (DocumentException e) {
       throw new HiException(e);
     }
 
     if (doc == null) {
       return;
     }
     Element root = doc.getRootElement();
 
     Iterator iter = root.element("Pages").elementIterator();
     while (iter.hasNext()) {
       Element el = (Element)iter.next();
       String id = el.attributeValue("id");
       this.pages.put(id, el);
     }
 
     this.menu = root.element("Menu");
     this.vmenu = root.element("MenuShortCut");
   }
 
   public String getMenu()
   {
     this.log.info("getMenu");
     StringBuffer buf = new StringBuffer(1024);
     buf.append("<ul id=\"menu\">");
     for (Iterator i = this.menu.elementIterator(); i.hasNext(); ) {
       Element element = (Element)i.next();
       appendItem(buf, element);
     }
     buf.append("</ul>");
     String ret = buf.toString();
     return ret;
   }
 
   private boolean appendItem(StringBuffer buf, Element element)
   {
     this.log.info("appendItem:[" + buf + "];[" + element + "]");
 
     boolean append = false;
     if (element.getName().equals("Group")) {
       StringBuffer innerbuf = new StringBuffer();
       for (Iterator i = element.elementIterator(); i.hasNext(); ) {
         Element e = (Element)i.next();
         append |= appendItem(innerbuf, e);
       }
       if (append) {
         buf.append("<li>");
         buf.append("<a href=\"");
         buf.append("javascript:dochange('" + element.attributeValue("name") + "')");
 
         buf.append("\">");
         buf.append(element.attributeValue("name"));
         buf.append("</a>");
 
         buf.append("</li>");
 
         buf.append("\r\n");
 
         String s = element.attributeValue("name");
         if (!(this.gids.contains(s)))
           this.gids.add(s);
       }
     } else if (element.getName().equals("Item")) {
       String id = element.attributeValue("id");
       if ((getPagePermission(id)) && 
         (this.pages.containsKey(id)))
       {
         append = true;
       }
 
     }
 
     return append;
   }
 
   public String getVmenu()
   {
     this.log.info("getVmenu");
     StringBuffer ret = new StringBuffer();
     for (int i = 0; i < this.gids.size(); ++i) {
       if (i == 0)
         ret.append(showVMenu((String)this.gids.get(i), true));
       else
         ret.append(showVMenu((String)this.gids.get(i), false));
       ret.append("\r\n");
     }
 
     return ret.toString();
   }
 
   private String showVMenu(String gid, boolean show) {
     this.log.info("showVMenu, gid:[" + gid + "]");
 
     Element vmenu = getElementByName(gid);
     if (vmenu == null) {
       return "";
     }
 
     StringBuffer buf = new StringBuffer(1024);
     if (show) {
       buf.append("<ul id=\"vmenu\" name=\"" + gid + "\" style=\"display:block\">");
     }
     else {
       buf.append("<ul id=\"vmenu\" name=\"" + gid + "\" style=\"display:none\">");
     }
 
     buf.append("\r\n");
     for (Iterator i = vmenu.elementIterator(); i.hasNext(); ) {
       Element element = (Element)i.next();
 
       if (element.getName().equals("Item")) {
         String id = element.attributeValue("id");
         if ((getPagePermission(id)) && 
           (this.pages.containsKey(id))) {
           Element e = (Element)this.pages.get(id);
           buf.append("<li>");
           buf.append("<a href=\"");
           String url = e.attributeValue("url");
 
           if ((!(StringUtils.isEmpty(url))) && (!("#".equals(url))))
             buf.append(url);
           else {
             buf.append(e.attributeValue("url"));
           }
           buf.append("\" target=mainWorkArea>");
           buf.append(e.attributeValue("name"));
           buf.append("</a>");
           buf.append("</li>");
 
           buf.append("\r\n");
         }
       }
 
     }
 
     buf.append("</ul>");
     buf.append("\r\n");
     String ret = buf.toString();
     return ret;
   }
 
   private Element getElementByName(String name) {
     this.log.info("getElementByName:[" + name + "]");
     Element root = this.menu;
     for (Iterator i = root.elementIterator(); i.hasNext(); ) {
       Element el = (Element)i.next();
       if (name.equals(el.attributeValue("name"))) {
         return el;
       }
     }
     return null;
   }
 
   public void setPermission(Set pageAcc, Set actAcc, String userid) {
     this.log.info("setPermission");
     this.pageMap.put(userid, pageAcc);
     this.actionMap.put(userid, actAcc);
   }
 
   public boolean getPagePermission(String pageid)
   {
     this.log.info("getPagePermission:[" + pageid + "]");
     Set pageAccess = (Set)this.pageMap.get(getUserID());
     if (pageAccess != null) {
       return pageAccess.contains(pageid);
     }
     return false;
   }
 
   private String getUserID()
   {
     return "test";
   }
 }