 package com.hisun.web.tag;

 import com.hisun.common.HiCallhostUtil;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.web.filter.HiConfig;
 import com.hisun.web.util.HiPropertiesUtils;
 import org.apache.commons.lang.math.NumberUtils;

 import javax.servlet.ServletContext;
 import javax.servlet.ServletRequest;
 import javax.servlet.http.HttpSession;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 import java.util.Iterator;
 import java.util.List;
 
 public class HiIterator extends BodyTagSupport
 {
   private String id;
   private String name;
   private String displaynode;
   private String scope;
   private String txncode;
   protected String offset;
   protected String indexId;
   protected String length;
   protected int lengthCount;
   protected int lengthValue;
   protected int offsetValue;
   private Iterator iter;
   protected boolean started;
 
   public HiIterator()
   {
     this.offset = null;
 
     this.indexId = null;
 
     this.length = null;
 
     this.lengthCount = 0;
 
     this.lengthValue = 0;
 
     this.offsetValue = 0;
 
     this.iter = null;
 
     this.started = false;
   }
 
   public int getIndex()
   {
     if (this.started) {
       return (this.offsetValue + this.lengthCount - 1);
     }
     return 0;
   }
 
   public int doStartTag()
     throws JspException
   {
     HiETF etfRoot = null;
     List nodes = null;
 
     if (this.scope == null)
     {
       HiConfig config = new HiConfig();
       ServletContext context = this.pageContext.getServletContext();
 
       HiETF etf = HiETFFactory.createETF();
       try {
         String IP = HiPropertiesUtils.getProperties("IP", context);
         String Port = HiPropertiesUtils.getProperties("PORT", context);
         config.setIp(IP);
         config.setPort(NumberUtils.toInt(Port));
         etfRoot = HiCallhostUtil.callhost(config, this.txncode, etf);
       }
       catch (Exception e) {
         e.printStackTrace();
       }
     }
     else if ("page".equalsIgnoreCase(this.scope)) {
       etfRoot = (HiETF)this.pageContext.getAttribute(getName());
     }
     else if ("request".equalsIgnoreCase(this.scope)) {
       etfRoot = (HiETF)this.pageContext.getRequest().getAttribute(getName());
     }
     else if ("session".equalsIgnoreCase(this.scope)) {
       etfRoot = (HiETF)this.pageContext.getSession().getAttribute(getName());
     }
 
     if (etfRoot == null) {
       etfRoot = HiETFFactory.createETF();
     }
 
     if (this.offset == null)
       this.offsetValue = 0;
     else {
       try {
         this.offsetValue = Integer.parseInt(this.offset);
       } catch (NumberFormatException e) {
         Integer offsetObject = (Integer)this.pageContext.findAttribute(this.offset);
 
         if (offsetObject == null)
           this.offsetValue = 0;
         else {
           this.offsetValue = offsetObject.intValue();
         }
       }
     }
 
     if (this.offsetValue < 0) {
       this.offsetValue = 0;
     }
 
     if (this.length == null)
       this.lengthValue = 0;
     else {
       try {
         this.lengthValue = Integer.parseInt(this.length);
       } catch (NumberFormatException e) {
         Integer lengthObject = (Integer)this.pageContext.findAttribute(this.length);
 
         if (lengthObject == null)
           this.lengthValue = 0;
         else {
           this.lengthValue = lengthObject.intValue();
         }
       }
     }
 
     if (this.lengthValue < 0) {
       this.lengthValue = 0;
     }
 
     nodes = etfRoot.getChildFuzzyEndBase(getDisplaynode());
     if (nodes == null) {
       return 6;
     }
 
     this.iter = nodes.iterator();
     this.lengthCount = 0;
 
     for (int i = 0; i < this.offsetValue; ++i) {
       if (this.iter.hasNext()) {
         this.iter.next();
       }
     }
     try
     {
       if (this.iter.hasNext()) {
         HiETF node = (HiETF)this.iter.next();
         this.pageContext.setAttribute(getId(), node);
 
         this.lengthCount += 1;
         this.started = true;
 
         if (this.indexId != null) {
           this.pageContext.setAttribute(this.indexId, new Integer(getIndex()));
         }
 
         return 1;
       }
 
       return 0;
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
 
     return 1;
   }
 
   public int doEndTag() throws JspException
   {
     return super.doEndTag();
   }
 
   public int doAfterBody()
     throws JspException
   {
     if ((this.lengthValue > 0) && (this.lengthCount >= this.lengthValue)) {
       return 0;
     }
 
     if (this.iter.hasNext()) {
       HiETF node = (HiETF)this.iter.next();
       if (node.getChildNodes().size() == 0)
       {
         return 6;
       }
       this.pageContext.setAttribute(getId(), node);
 
       this.lengthCount += 1;
 
       if (this.indexId != null) {
         this.pageContext.setAttribute(this.indexId, new Integer(getIndex()));
       }
 
       return 2;
     }
 
     return 6;
   }
 
   public String getDisplaynode()
   {
     return this.displaynode;
   }
 
   public void setDisplaynode(String displaynode) {
     this.displaynode = displaynode;
   }
 
   public String getId() {
     return this.id;
   }
 
   public void setId(String id) {
     this.id = id;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getScope() {
     return this.scope;
   }
 
   public void setScope(String scope) {
     this.scope = scope;
   }
 
   public String getOffset() {
     return this.offset;
   }
 
   public void setOffset(String offset) {
     this.offset = offset;
   }
 
   public String getIndexId() {
     return this.indexId;
   }
 
   public void setIndexId(String indexId) {
     this.indexId = indexId;
   }
 
   public String getLength() {
     return this.length;
   }
 
   public void setLength(String length) {
     this.length = length;
   }
 
   public String getTxncode() {
     return this.txncode;
   }
 
   public void setTxncode(String txncode) {
     this.txncode = txncode;
   }
 }