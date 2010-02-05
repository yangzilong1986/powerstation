 package com.hisun.mon.atc;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.io.InputStream;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.LinkedHashMap;
 import java.util.Map;
 import java.util.Set;
 import javax.servlet.ServletContext;
 import org.apache.commons.lang.math.NumberUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class UserLoginAuth
 {
   private Logger log = HiLog.getLogger("login.trc");
 
   private Map pages = new LinkedHashMap();
   private Element menu = null;
   private Element vmenu = null;
 
   private Map pageMap = new LinkedHashMap();
   private Map actionMap = new LinkedHashMap();
 
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
 
     msg.getETFBody().setChildValue("MENU", menu);
 
     return true;
   }
 
   private void readmenuconfig(HiMessageContext ctx) throws HiException {
     this.log.info("readmenuconfig");
     SAXReader reader = new SAXReader();
     Document doc = null;
     HiMessage msg = ctx.getCurrentMsg();
     ServletContext servletCtx = (ServletContext)msg
       .getObjectHeadItem("_WEB_APPLICATION");
     try {
       InputStream in = servletCtx
         .getResourceAsStream("/conf/menuconfig.xml");
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
     buf.append("<?xml version='1.0' encoding='gb2312'?>");
     buf.append("<tree id='0'>");
     buf.append("<item id='root' text='Console' open='1' im0='tombs.gif' im1='tombs.gif' im2='iconSafe.gif'>");
 
     for (Iterator i = this.menu.elementIterator(); i.hasNext(); ) {
       Element element = (Element)i.next();
       appendItem(buf, element);
     }
     buf.append("</item></tree>");
     String ret = buf.toString();
     return ret;
   }
 
   private boolean appendItem(StringBuffer buf, Element element)
   {
     this.log.info("appendItem:[" + buf + "];[" + element + "]");
 
     boolean append = false;
     Element itemNode = null;
     Element pageNode = null;
     if (element.getName().equals("Group")) {
       StringBuffer innerbuf = new StringBuffer();
       for (Iterator i = element.elementIterator(); i.hasNext(); ) {
         itemNode = (Element)i.next();
         if (checkMenuAuth(itemNode)) {
           if (!(append))
           {
             buf.append("<item id='");
             buf.append(element.attributeValue("name"));
             buf.append("' text='");
             buf.append(element.attributeValue("name"));
             buf.append("'");
             append = true;
           }
 
           pageNode = (Element)this.pages.get(itemNode.attributeValue("id"));
           if (pageNode == null) {
             continue;
           }
           buf.append("<item id='");
           buf.append(pageNode.attributeValue("id"));
           buf.append("' text='");
           buf.append(pageNode.attributeValue("name"));
           buf.append("' />");
         }
       }
 
       if (append) {
         buf.append("</item>");
       }
     }
 
     return append;
   }
 
   private boolean checkMenuAuth(Element element) {
     if (!(element.getName().equals("Item"))) break label47;
     String id = element.attributeValue("id");
 
     label47: return ((!(getPagePermission(id))) || 
       (!(this.pages.containsKey(id))));
   }
 
   public void setPermission(Set pageAcc, Set actAcc, String userid)
   {
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