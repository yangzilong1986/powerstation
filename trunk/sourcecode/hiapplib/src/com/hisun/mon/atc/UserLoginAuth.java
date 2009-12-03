/*     */ package com.hisun.mon.atc;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletContext;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class UserLoginAuth
/*     */ {
/*  31 */   private Logger log = HiLog.getLogger("login.trc");
/*     */ 
/* 101 */   private Map pages = new LinkedHashMap();
/* 102 */   private Element menu = null;
/* 103 */   private Element vmenu = null;
/*     */ 
/* 105 */   private Map pageMap = new LinkedHashMap();
/* 106 */   private Map actionMap = new LinkedHashMap();
/*     */ 
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  34 */     authProc(ctx);
/*  35 */     return 0;
/*     */   }
/*     */ 
/*     */   protected boolean authProc(HiMessageContext ctx) throws HiException {
/*  39 */     HiMessage msg = ctx.getCurrentMsg();
/*  40 */     HiETF root = msg.getETFBody();
/*  41 */     int recNum = NumberUtils.toInt(root.getChildValue("REC_NUM"));
/*  42 */     Logger log = HiLog.getLogger(msg);
/*  43 */     log.info("REC_NUM:[" + recNum + "]");
/*  44 */     if (recNum == 0)
/*     */     {
/*  46 */       return false;
/*     */     }
/*  48 */     Set pageAccSet = new HashSet();
/*  49 */     Set actAccSet = new HashSet();
/*  50 */     for (int i = 1; i <= recNum; ++i) {
/*  51 */       HiETF grpEtf = root.getChildNode("GRP_" + i);
/*  52 */       if (grpEtf != null) {
/*  53 */         pageAccSet.add(grpEtf.getChildValue("PAG_ID"));
/*  54 */         actAccSet.add(grpEtf.getChildValue("TXN_CD"));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  59 */     setPermission(pageAccSet, actAccSet, getUserID());
/*     */ 
/*  62 */     readmenuconfig(ctx);
/*  63 */     String menu = getMenu();
/*     */ 
/*  65 */     msg.getETFBody().setChildValue("MENU", menu);
/*     */ 
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */   private void readmenuconfig(HiMessageContext ctx) throws HiException {
/*  71 */     this.log.info("readmenuconfig");
/*  72 */     SAXReader reader = new SAXReader();
/*  73 */     Document doc = null;
/*  74 */     HiMessage msg = ctx.getCurrentMsg();
/*  75 */     ServletContext servletCtx = (ServletContext)msg
/*  76 */       .getObjectHeadItem("_WEB_APPLICATION");
/*     */     try {
/*  78 */       InputStream in = servletCtx
/*  79 */         .getResourceAsStream("/conf/menuconfig.xml");
/*  80 */       doc = reader.read(in);
/*     */     } catch (DocumentException e) {
/*  82 */       throw new HiException(e);
/*     */     }
/*     */ 
/*  85 */     if (doc == null) {
/*  86 */       return;
/*     */     }
/*  88 */     Element root = doc.getRootElement();
/*     */ 
/*  90 */     Iterator iter = root.element("Pages").elementIterator();
/*  91 */     while (iter.hasNext()) {
/*  92 */       Element el = (Element)iter.next();
/*  93 */       String id = el.attributeValue("id");
/*  94 */       this.pages.put(id, el);
/*     */     }
/*     */ 
/*  97 */     this.menu = root.element("Menu");
/*  98 */     this.vmenu = root.element("MenuShortCut");
/*     */   }
/*     */ 
/*     */   public String getMenu()
/*     */   {
/* 114 */     this.log.info("getMenu");
/* 115 */     StringBuffer buf = new StringBuffer(1024);
/* 116 */     buf.append("<?xml version='1.0' encoding='gb2312'?>");
/* 117 */     buf.append("<tree id='0'>");
/* 118 */     buf.append("<item id='root' text='Console' open='1' im0='tombs.gif' im1='tombs.gif' im2='iconSafe.gif'>");
/*     */ 
/* 121 */     for (Iterator i = this.menu.elementIterator(); i.hasNext(); ) {
/* 122 */       Element element = (Element)i.next();
/* 123 */       appendItem(buf, element);
/*     */     }
/* 125 */     buf.append("</item></tree>");
/* 126 */     String ret = buf.toString();
/* 127 */     return ret;
/*     */   }
/*     */ 
/*     */   private boolean appendItem(StringBuffer buf, Element element)
/*     */   {
/* 132 */     this.log.info("appendItem:[" + buf + "];[" + element + "]");
/*     */ 
/* 134 */     boolean append = false;
/* 135 */     Element itemNode = null;
/* 136 */     Element pageNode = null;
/* 137 */     if (element.getName().equals("Group")) {
/* 138 */       StringBuffer innerbuf = new StringBuffer();
/* 139 */       for (Iterator i = element.elementIterator(); i.hasNext(); ) {
/* 140 */         itemNode = (Element)i.next();
/* 141 */         if (checkMenuAuth(itemNode)) {
/* 142 */           if (!(append))
/*     */           {
/* 144 */             buf.append("<item id='");
/* 145 */             buf.append(element.attributeValue("name"));
/* 146 */             buf.append("' text='");
/* 147 */             buf.append(element.attributeValue("name"));
/* 148 */             buf.append("'");
/* 149 */             append = true;
/*     */           }
/*     */ 
/* 152 */           pageNode = (Element)this.pages.get(itemNode.attributeValue("id"));
/* 153 */           if (pageNode == null) {
/*     */             continue;
/*     */           }
/* 156 */           buf.append("<item id='");
/* 157 */           buf.append(pageNode.attributeValue("id"));
/* 158 */           buf.append("' text='");
/* 159 */           buf.append(pageNode.attributeValue("name"));
/* 160 */           buf.append("' />");
/*     */         }
/*     */       }
/*     */ 
/* 164 */       if (append) {
/* 165 */         buf.append("</item>");
/*     */       }
/*     */     }
/*     */ 
/* 169 */     return append;
/*     */   }
/*     */ 
/*     */   private boolean checkMenuAuth(Element element) {
/* 173 */     if (!(element.getName().equals("Item"))) break label47;
/* 174 */     String id = element.attributeValue("id");
/*     */ 
/* 179 */     label47: return ((!(getPagePermission(id))) || 
/* 177 */       (!(this.pages.containsKey(id))));
/*     */   }
/*     */ 
/*     */   public void setPermission(Set pageAcc, Set actAcc, String userid)
/*     */   {
/* 188 */     this.log.info("setPermission");
/* 189 */     this.pageMap.put(userid, pageAcc);
/* 190 */     this.actionMap.put(userid, actAcc);
/*     */   }
/*     */ 
/*     */   public boolean getPagePermission(String pageid)
/*     */   {
/* 195 */     this.log.info("getPagePermission:[" + pageid + "]");
/* 196 */     Set pageAccess = (Set)this.pageMap.get(getUserID());
/* 197 */     if (pageAccess != null) {
/* 198 */       return pageAccess.contains(pageid);
/*     */     }
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   private String getUserID()
/*     */   {
/* 205 */     return "test";
/*     */   }
/*     */ }