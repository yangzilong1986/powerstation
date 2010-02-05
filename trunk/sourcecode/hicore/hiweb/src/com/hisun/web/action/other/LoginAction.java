 package com.hisun.web.action.other;

 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.web.action.BaseAction;
 import com.opensymphony.xwork2.ActionContext;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.apache.struts2.ServletActionContext;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;

 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import java.io.InputStream;
 import java.util.*;
 
 public class LoginAction extends BaseAction
 {
   private static final long serialVersionUID = 1L;
   private static final String RSP_CD = "RSP_CD";
   private static final String PAG_ID = "PAG_ID";
   private static final String TXN_CD = "TXN_CD";
   private String userID;
   private String userPwd;
   private String CHKNO;
   private boolean rstPwdFlag;
   private Map pages;
   private Element menu;
   private Element vmenu;
   private Map pageMap;
   private Map actionMap;
   private ArrayList gids;
 
   public LoginAction()
   {
     this.rstPwdFlag = false;
 
     this.pages = new LinkedHashMap();
     this.menu = null;
     this.vmenu = null;
 
     this.pageMap = new LinkedHashMap();
     this.actionMap = new LinkedHashMap();
 
     this.gids = new ArrayList();
   }
 
   public void setUserID(String userID)
   {
     this.userID = userID;
   }
 
   public String getUserID() {
     return this.userID;
   }
 
   public void setUserPwd(String userPwd) {
     this.userPwd = userPwd;
   }
 
   public String getUserPwd() {
     return this.userPwd;
   }
 
   public String getCHKNO() {
     return this.CHKNO;
   }
 
   public void setCHKNO(String chkno) {
     this.CHKNO = chkno;
   }
 
   public String execute()
     throws Exception
   {
     if (this.rstPwdFlag) {
       this.output = "/p_usermng/loginresetpwd.jsp";
     }
     return super.execute();
   }
 
   protected HiETF beforeProcess(HttpServletRequest request, Logger _log) throws HiException {
     HiETF etf = HiETFFactory.createETF();
 
     String ip = request.getRemoteAddr();
 
     etf.setChildValue("UID", this.userID);
     etf.setChildValue("UPWD", this.userPwd);
     etf.setChildValue("U_IP", ip);
 
     return etf;
   }
 
   protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log) {
     String rspCod = rspetf.getChildValue("RSP_CD");
 
     String mecid = rspetf.getChildValue("MEC_ID");
     String mblno = rspetf.getChildValue("MBL_NO");
     if ((StringUtils.isEmpty(mblno)) && (StringUtils.isEmpty(mecid))) {
       request.getSession(true).setAttribute("UID", this.userID);
     }
     else if (StringUtils.isEmpty(mecid)) {
       request.getSession(true).setAttribute("UID", mblno);
     }
     else if (StringUtils.isEmpty(mblno)) {
       request.getSession(true).setAttribute("UID", mecid);
     }
     request.getSession(true).setAttribute("BR_NO", rspetf.getChildValue("BR_NO"));
 
     if (StringUtils.equals(rspCod, "MNG0900204"))
     {
       String brno = rspetf.getChildValue("BR_NO");
 
       this.rstPwdFlag = true;
       return false;
     }
     if (!(StringUtils.equals(rspCod, "000000")))
     {
       return false;
     }
 
     int recNum = NumberUtils.toInt(rspetf.getChildValue(getRecnum()));
     if (recNum == 0)
     {
       return false;
     }
 
     HiETF grpEtf = null;
     Set pageAccSet = new HashSet();
     Set actAccSet = new HashSet();
     for (int i = 1; i <= recNum; ++i)
     {
       grpEtf = rspetf.getChildNode("grp_" + i);
       if (grpEtf == null)
         continue;
       pageAccSet.add(grpEtf.getChildValue("PAG_ID"));
       actAccSet.add(grpEtf.getChildValue("TXN_CD"));
     }
 
     setPermission(pageAccSet, actAccSet, getUserID());
 
     readmenuconfig(_log);
 
     return super.endProcess(request, rspetf, _log);
   }
 
   private void readmenuconfig(Logger _log)
   {
     SAXReader reader = new SAXReader();
     Document doc = null;
     try {
       ServletContext context = (ServletContext)ActionContext.getContext().get("com.opensymphony.xwork2.dispatcher.ServletContext");
       InputStream in = context.getResourceAsStream("/conf/menuconfig.xml");
       doc = reader.read(in);
     } catch (DocumentException e) {
       e.printStackTrace();
       return;
     }
 
     if (doc == null) return;
 
     Element root = doc.getRootElement();
 
     Iterator iter = root.element("Pages").elementIterator();
     while (iter.hasNext()) {
       Element el = (Element)iter.next();
       String id = el.attributeValue("id");
       this.pages.put(id, el);
     }
 
     this.menu = root.element("Menu");
     this.vmenu = root.element("MenuShortCut");
 
     Map appmap = ServletActionContext.getContext().getApplication();
     appmap.put("pages", this.pages);
     appmap.put("menu", this.menu);
     appmap.put("usrpage", this.pageMap);
     appmap.put("usraction", this.actionMap);
   }
 
   public String getMenu()
   {
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
     StringBuffer ret = new StringBuffer();
     for (int i = 0; i < this.gids.size(); ++i) {
       if (i == 0) {
         ret.append(showVMenu((String)this.gids.get(i), true));
       }
       else
         ret.append(showVMenu((String)this.gids.get(i), false));
       ret.append("\r\n");
     }
 
     return ret.toString();
   }
 
   private String showVMenu(String gid, boolean show) {
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
     this.pageMap.put(userid, pageAcc);
     this.actionMap.put(userid, actAcc);
   }
 
   public boolean getPagePermission(String pageid) {
     Set pageAccess = (Set)this.pageMap.get(getUserID());
     if (pageAccess != null) {
       return pageAccess.contains(pageid);
     }
 
     return false;
   }
 }