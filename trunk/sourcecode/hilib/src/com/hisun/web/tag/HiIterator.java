/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.common.HiCallhostUtil;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.web.filter.HiConfig;
/*     */ import com.hisun.web.util.HiPropertiesUtils;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiIterator extends BodyTagSupport
/*     */ {
/*     */   private String id;
/*     */   private String name;
/*     */   private String displaynode;
/*     */   private String scope;
/*     */   private String txncode;
/*     */   protected String offset;
/*     */   protected String indexId;
/*     */   protected String length;
/*     */   protected int lengthCount;
/*     */   protected int lengthValue;
/*     */   protected int offsetValue;
/*     */   private Iterator iter;
/*     */   protected boolean started;
/*     */ 
/*     */   public HiIterator()
/*     */   {
/*  35 */     this.offset = null;
/*     */ 
/*  39 */     this.indexId = null;
/*     */ 
/*  44 */     this.length = null;
/*     */ 
/*  49 */     this.lengthCount = 0;
/*     */ 
/*  54 */     this.lengthValue = 0;
/*     */ 
/*  59 */     this.offsetValue = 0;
/*     */ 
/*  61 */     this.iter = null;
/*     */ 
/*  66 */     this.started = false;
/*     */   }
/*     */ 
/*     */   public int getIndex()
/*     */   {
/*  80 */     if (this.started) {
/*  81 */       return (this.offsetValue + this.lengthCount - 1);
/*     */     }
/*  83 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*  90 */     HiETF etfRoot = null;
/*  91 */     List nodes = null;
/*     */ 
/*  93 */     if (this.scope == null)
/*     */     {
/*  97 */       HiConfig config = new HiConfig();
/*  98 */       ServletContext context = this.pageContext.getServletContext();
/*     */ 
/* 100 */       HiETF etf = HiETFFactory.createETF();
/*     */       try {
/* 102 */         String IP = HiPropertiesUtils.getProperties("IP", context);
/* 103 */         String Port = HiPropertiesUtils.getProperties("PORT", context);
/* 104 */         config.setIp(IP);
/* 105 */         config.setPort(NumberUtils.toInt(Port));
/* 106 */         etfRoot = HiCallhostUtil.callhost(config, this.txncode, etf);
/*     */       }
/*     */       catch (Exception e) {
/* 109 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 112 */     else if ("page".equalsIgnoreCase(this.scope)) {
/* 113 */       etfRoot = (HiETF)this.pageContext.getAttribute(getName());
/*     */     }
/* 115 */     else if ("request".equalsIgnoreCase(this.scope)) {
/* 116 */       etfRoot = (HiETF)this.pageContext.getRequest().getAttribute(getName());
/*     */     }
/* 118 */     else if ("session".equalsIgnoreCase(this.scope)) {
/* 119 */       etfRoot = (HiETF)this.pageContext.getSession().getAttribute(getName());
/*     */     }
/*     */ 
/* 122 */     if (etfRoot == null) {
/* 123 */       etfRoot = HiETFFactory.createETF();
/*     */     }
/*     */ 
/* 127 */     if (this.offset == null)
/* 128 */       this.offsetValue = 0;
/*     */     else {
/*     */       try {
/* 131 */         this.offsetValue = Integer.parseInt(this.offset);
/*     */       } catch (NumberFormatException e) {
/* 133 */         Integer offsetObject = (Integer)this.pageContext.findAttribute(this.offset);
/*     */ 
/* 136 */         if (offsetObject == null)
/* 137 */           this.offsetValue = 0;
/*     */         else {
/* 139 */           this.offsetValue = offsetObject.intValue();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 144 */     if (this.offsetValue < 0) {
/* 145 */       this.offsetValue = 0;
/*     */     }
/*     */ 
/* 149 */     if (this.length == null)
/* 150 */       this.lengthValue = 0;
/*     */     else {
/*     */       try {
/* 153 */         this.lengthValue = Integer.parseInt(this.length);
/*     */       } catch (NumberFormatException e) {
/* 155 */         Integer lengthObject = (Integer)this.pageContext.findAttribute(this.length);
/*     */ 
/* 158 */         if (lengthObject == null)
/* 159 */           this.lengthValue = 0;
/*     */         else {
/* 161 */           this.lengthValue = lengthObject.intValue();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 166 */     if (this.lengthValue < 0) {
/* 167 */       this.lengthValue = 0;
/*     */     }
/*     */ 
/* 170 */     nodes = etfRoot.getChildFuzzyEndBase(getDisplaynode());
/* 171 */     if (nodes == null) {
/* 172 */       return 6;
/*     */     }
/*     */ 
/* 175 */     this.iter = nodes.iterator();
/* 176 */     this.lengthCount = 0;
/*     */ 
/* 179 */     for (int i = 0; i < this.offsetValue; ++i) {
/* 180 */       if (this.iter.hasNext()) {
/* 181 */         this.iter.next();
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 186 */       if (this.iter.hasNext()) {
/* 187 */         HiETF node = (HiETF)this.iter.next();
/* 188 */         this.pageContext.setAttribute(getId(), node);
/*     */ 
/* 190 */         this.lengthCount += 1;
/* 191 */         this.started = true;
/*     */ 
/* 193 */         if (this.indexId != null) {
/* 194 */           this.pageContext.setAttribute(this.indexId, new Integer(getIndex()));
/*     */         }
/*     */ 
/* 197 */         return 1;
/*     */       }
/*     */ 
/* 200 */       return 0;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 204 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 207 */     return 1;
/*     */   }
/*     */ 
/*     */   public int doEndTag() throws JspException
/*     */   {
/* 212 */     return super.doEndTag();
/*     */   }
/*     */ 
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/* 218 */     if ((this.lengthValue > 0) && (this.lengthCount >= this.lengthValue)) {
/* 219 */       return 0;
/*     */     }
/*     */ 
/* 222 */     if (this.iter.hasNext()) {
/* 223 */       HiETF node = (HiETF)this.iter.next();
/* 224 */       if (node.getChildNodes().size() == 0)
/*     */       {
/* 226 */         return 6;
/*     */       }
/* 228 */       this.pageContext.setAttribute(getId(), node);
/*     */ 
/* 230 */       this.lengthCount += 1;
/*     */ 
/* 232 */       if (this.indexId != null) {
/* 233 */         this.pageContext.setAttribute(this.indexId, new Integer(getIndex()));
/*     */       }
/*     */ 
/* 236 */       return 2;
/*     */     }
/*     */ 
/* 239 */     return 6;
/*     */   }
/*     */ 
/*     */   public String getDisplaynode()
/*     */   {
/* 244 */     return this.displaynode;
/*     */   }
/*     */ 
/*     */   public void setDisplaynode(String displaynode) {
/* 248 */     this.displaynode = displaynode;
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 252 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/* 256 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 260 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 264 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getScope() {
/* 268 */     return this.scope;
/*     */   }
/*     */ 
/*     */   public void setScope(String scope) {
/* 272 */     this.scope = scope;
/*     */   }
/*     */ 
/*     */   public String getOffset() {
/* 276 */     return this.offset;
/*     */   }
/*     */ 
/*     */   public void setOffset(String offset) {
/* 280 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */   public String getIndexId() {
/* 284 */     return this.indexId;
/*     */   }
/*     */ 
/*     */   public void setIndexId(String indexId) {
/* 288 */     this.indexId = indexId;
/*     */   }
/*     */ 
/*     */   public String getLength() {
/* 292 */     return this.length;
/*     */   }
/*     */ 
/*     */   public void setLength(String length) {
/* 296 */     this.length = length;
/*     */   }
/*     */ 
/*     */   public String getTxncode() {
/* 300 */     return this.txncode;
/*     */   }
/*     */ 
/*     */   public void setTxncode(String txncode) {
/* 304 */     this.txncode = txncode;
/*     */   }
/*     */ }